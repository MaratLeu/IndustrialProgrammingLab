package org.example.web_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public class DemoController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @PostMapping("/add")
    public String addCustomer(@RequestParam String first, @RequestParam String last) {
        Customer customer = new Customer();
        customer.setFirstName(first);
        customer.setLastName(last);
        customerRepository.save(customer);
        return "Added new customer to repo!";
    }

    @GetMapping("/list")
    public Iterable<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public Customer findCustomerById(@PathVariable Integer id) {
        return customerRepository.findCustomerById(id);
    }

    @PostMapping("/archive")
    public String archive(@RequestParam("inputFormat") String inputFormat,
                          @RequestParam("outputFormat") String outputFormat,
                          @RequestParam("archiveFormat") String archiveFormat) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String result;
        ArrayList<String> filenames = new ArrayList<>();
        filenames.add("input." + inputFormat);
        filenames.add("output." + outputFormat);
        Method method = Archive.class.getDeclaredMethod(archiveFormat, ArrayList.class, String.class);
        method.invoke(null, filenames, "archive." + archiveFormat);
        result = "Файлы успешно заархивированы";
        return result;
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam("inputFormat") String inputFormat,
                          @RequestParam("outputFormat") String outputFormat) throws NoSuchAlgorithmException
    {
        String result;
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        Encryption.encrypt("input." + inputFormat, "input_encrypted." + inputFormat, secretKey);
        Encryption.encrypt("output." + outputFormat, "output_encrypted." + outputFormat, secretKey);
        result = "Файлы успешно зашифрованы";
        return result;
    }

    @PostMapping("/archiveThenEncrypt")
    public String archiveThenEncrypt(@RequestParam("inputFormat") String inputFormat,
                                     @RequestParam("outputFormat") String outputFormat,
                                     @RequestParam("archiveFormat") String archiveFormat) throws NoSuchAlgorithmException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String result;
        ArrayList<String> filenames = new ArrayList<>();
        filenames.add("input." + inputFormat);
        filenames.add("output." + outputFormat);
        Method method = Archive.class.getDeclaredMethod(archiveFormat, ArrayList.class, String.class);
        method.invoke(null, filenames, "archive." + archiveFormat);

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        Encryption.encrypt("archive." + archiveFormat, "archive_encrypted." + "aes", secretKey);
        result = "Файлы успешно заархивированы/зашифрованы";
        return result;
    }

    @PostMapping("/encryptThenArchive")
    public String encryptThenArchive(@RequestParam("inputFormat") String inputFormat,
                                     @RequestParam("outputFormat") String outputFormat,
                                     @RequestParam("archiveFormat") String archiveFormat) throws NoSuchAlgorithmException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String result;
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        Encryption.encrypt("input." + inputFormat, "encryptedInput." + "aes", secretKey);
        Encryption.encrypt("output." + outputFormat, "encryptedOutput." + "aes", secretKey);

        ArrayList<String> filenames = new ArrayList<>();
        filenames.add("encryptedInput.aes");
        filenames.add("encryptedOutput.aes");
        Method method = Archive.class.getDeclaredMethod(archiveFormat, ArrayList.class, String.class);
        method.invoke(null, filenames, "archive." + archiveFormat);
        result = "Файлы успешно зашифрованы/заархивированы";
        return result;
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam String expression) {
        return Expression.evaluate_line(expression);
    }

    @PostMapping("/calculate_expressions")
    public ResponseEntity<ArrayList<String>> calculate(@RequestBody ExpressionRequest request) {
        if (request == null || request.getExpressions() == null || request.getExpressions().isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>(Collections.singletonList("Input expressions cannot be null or empty")));
        }

        try {
            ArrayList<String> arithmeticExpressions = Expression.transform_to_arithmetic(request.getExpressions());
            ArrayList<String> results = Expression.evaluateLines(arithmeticExpressions);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            System.err.println("Error processing expressions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>(Collections.singletonList("Error processing expressions: " + e.getMessage())));
        }
    }

    @PostMapping("/readFromInputFile")
    public ResponseEntity<Map<String, Object>> readFromInputFile(@RequestBody ExpressionRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String inputFormat = request.getInputFormat();
            String encryptionKey = request.getEncryptionKey();
            String fileContent = request.getFileContent();

            SecretKey key = Encryption.getSecretKey(encryptionKey);

            // Создаем временный файл из содержимого файла
            String tempFilePath = System.getProperty("java.io.tmpdir") + "/input_temp" + System.currentTimeMillis() + "." + inputFormat;
            Files.write(Paths.get(tempFilePath), fileContent.getBytes());
            File selectedFile = new File(tempFilePath);

            // Создаем временную директорию
            String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
            File tempDir = new File(tempDirPath);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            ArrayList<String> expressions = new ArrayList<>();

            if (selectedFile.getName().endsWith(".zip") || selectedFile.getName().endsWith(".rar")) {
                if (selectedFile.getName().endsWith(".zip")) {
                    Archive.unzip(selectedFile.getAbsolutePath(), tempDirPath);
                } else if (selectedFile.getName().endsWith(".rar")) {
                    Archive.unrar(selectedFile.getAbsolutePath(), tempDirPath);
                }

                File[] extractedFiles = tempDir.listFiles();
                if (extractedFiles != null && extractedFiles.length > 0) {
                    File extractedFile = extractedFiles[0];
                    expressions = (ArrayList<String>) Files.readAllLines(extractedFile.toPath());
                }
            } else if (selectedFile.getName().endsWith(".aes")) {
                String decryptFilePath = tempDirPath + "/decrypted_" + selectedFile.getName().replace(".aes", ".txt");
                Encryption.decrypt(decryptFilePath, selectedFile.getAbsolutePath(), key);
                expressions = (ArrayList<String>) Files.readAllLines(Paths.get(decryptFilePath));
            } else {
                expressions = (ArrayList<String>) Files.readAllLines(selectedFile.toPath());
            }

            response.put("expressions", expressions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Ошибка работы с файлами: " + e.getMessage());
            response.put("error", "Ошибка работы с файлами: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/readFromOutputFile")
    public ResponseEntity<Map<String, Object>> readFromOutputFile(@RequestBody ExpressionRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String outputFormat = request.getOutputFormat();
            String encryptionKey = request.getEncryptionKey();
            String fileContent = request.getFileContent();

            SecretKey key = Encryption.getSecretKey(encryptionKey);

            // Создаем временный файл из содержимого файла
            String tempFilePath = System.getProperty("java.io.tmpdir") + "/input_temp" + System.currentTimeMillis() + "." + outputFormat;
            Files.write(Paths.get(tempFilePath), fileContent.getBytes());
            File selectedFile = new File(tempFilePath);

            // Создаем временную директорию
            String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
            File tempDir = new File(tempDirPath);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            ArrayList<String> expressions = new ArrayList<>();

            if (selectedFile.getName().endsWith(".zip") || selectedFile.getName().endsWith(".rar")) {
                if (selectedFile.getName().endsWith(".zip")) {
                    Archive.unzip(selectedFile.getAbsolutePath(), tempDirPath);
                } else if (selectedFile.getName().endsWith(".rar")) {
                    Archive.unrar(selectedFile.getAbsolutePath(), tempDirPath);
                }

                File[] extractedFiles = tempDir.listFiles();
                if (extractedFiles != null && extractedFiles.length > 0) {
                    File extractedFile = extractedFiles[0];
                    expressions = (ArrayList<String>) Files.readAllLines(extractedFile.toPath());
                }
            } else if (selectedFile.getName().endsWith(".aes")) {
                String decryptFilePath = tempDirPath + "/decrypted_" + selectedFile.getName().replace(".aes", ".txt");
                Encryption.decrypt(decryptFilePath, selectedFile.getAbsolutePath(), key);
                expressions = (ArrayList<String>) Files.readAllLines(Paths.get(decryptFilePath));
            } else {
                expressions = (ArrayList<String>) Files.readAllLines(selectedFile.toPath());
            }

            response.put("expressions", expressions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Ошибка работы с файлами: " + e.getMessage());
            response.put("error", "Ошибка работы с файлами: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public static class ExpressionRequest {
        private String inputFormat;
        private String outputFormat;
        private String encryptionKey;
        private String fileContent;
        private ArrayList<String> expressions;

        public String getInputFormat() {
            return inputFormat;
        }

        public void setInputFormat(String inputFormat) {
            this.inputFormat = inputFormat;
        }

        public String getOutputFormat() {
            return outputFormat;
        }

        public void setOutputFormat(String outputFormat) {
            this.outputFormat = outputFormat;
        }

        public String getEncryptionKey() {
            return encryptionKey;
        }

        public void setEncryptionKey(String encryptionKey) {
            this.encryptionKey = encryptionKey;
        }

        public String getFileContent() {
            return fileContent;
        }

        public void setFileContent(String fileContent) {
            this.fileContent = fileContent;
        }

        public ArrayList<String> getExpressions() {
            return expressions;
        }

        public void setExpressions(ArrayList<String> expressions) {
            this.expressions = expressions;
        }
    }
}

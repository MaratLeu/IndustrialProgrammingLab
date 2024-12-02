package org.example.web_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

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

    @PostMapping("/calculate")
    public String calculate(@RequestParam String expression) {
        return Expression.evaluate_line(expression);
    }

    @PostMapping("/calculate_expressions")
    public ResponseEntity<ArrayList<String>> calculateExpressions(@RequestBody ExpressionRequest request) {
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

    @PostMapping("/writeToFile")
    public String writeToFile(@RequestBody ExpressionRequest request) {
        String result;
        try {
            ArrayList<String> expressions = request.getExpressions();
            Method method = ReadWrite.class.getDeclaredMethod("write_" + request.getInputFormat(), String.class, ArrayList.class, boolean.class);
            method.invoke(null, "input." + request.getInputFormat(), expressions, true);

            ArrayList<String> results = request.getResults();
            Method method1 = ReadWrite.class.getDeclaredMethod("write_" + request.getOutputFormat(), String.class, ArrayList.class, boolean.class);
            method1.invoke(null, "output." + request.getOutputFormat(), results, false);

            result = "Выражения и результаты успешно записаны в файлы";
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.err.println("Error processing expressions: " + e.getMessage());
            result = "Ошибка записи в файл: " + e.getMessage();
        }
        return result;
    }

    @PostMapping("/readFromFile")
    public ResponseEntity<ExpressionRequest> readFromFile(@RequestBody ExpressionRequest request) {
        try {
            String inputFormat = request.getInputFormat();
            String outputFormat = request.getOutputFormat();

            Method method = ReadWrite.class.getDeclaredMethod("read_" + inputFormat, String.class, boolean.class);
            ArrayList<String> expressions = (ArrayList<String>) method.invoke(null, "input." + inputFormat, true);

            Method method1 = ReadWrite.class.getDeclaredMethod("read_" + outputFormat, String.class, boolean.class);
            ArrayList<String> results = (ArrayList<String>) method1.invoke(null, "output." + outputFormat, false);

            request.setExpressions(expressions);
            request.setResults(results);
            return ResponseEntity.ok(request);
        }
        catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    public static class ExpressionRequest {
        private String inputFormat;
        private String outputFormat;
        private String archiveFormat;
        private ArrayList<String> expressions;
        private ArrayList<String> results;

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

        public String getArchiveFormat() {
            return archiveFormat;
        }
        public void setArchiveFormat(String archiveFormat) {
            this.archiveFormat = archiveFormat;
        }

        public ArrayList<String> getExpressions() {
            return expressions;
        }
        public void setExpressions(ArrayList<String> expressions) {
            this.expressions = expressions;
        }

        public ArrayList<String> getResults() {
            return results;
        }
        public void setResults(ArrayList<String> results) {
            this.results = results;
        }
    }
}

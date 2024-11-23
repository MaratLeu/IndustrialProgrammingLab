package org.example.web_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
    public ArrayList<String> calculateExpressions(@RequestBody ExpressionRequest request) {
        ArrayList<String> arithmeticExpressions = Expression.transform_to_arithmetic(request.getExpressions());
        return Expression.evaluateLines(arithmeticExpressions);
    }

    @PostMapping("/archive")
    public String archive(@RequestParam("inputFormat") String inputFormat,
                          @RequestParam("outputFormat") String outputFormat,
                          @RequestParam("archiveFormat") String archiveFormat) throws IOException, InterruptedException {
        String result;
        if ("zip".equalsIgnoreCase(archiveFormat)) {
            Archive.zip("input." + inputFormat, "archive.zip");
            Archive.zip("output." + outputFormat, "archive.zip");
            result = "Файлы успешно заархивированы в формат zip.";
        } else if ("rar".equalsIgnoreCase(archiveFormat)) {
            Archive.rar("input." + inputFormat, "archive.rar");
            Archive.rar("output." + outputFormat, "archive.rar");
            result = "Файлы успешно заархивированы в формат rar.";
        } else {
            result = "Неверный формат архива.";
        }
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
                                     @RequestParam("archiveFormat") String archiveFormat) throws NoSuchAlgorithmException, IOException, InterruptedException {
        String result;
        if ("zip".equalsIgnoreCase(archiveFormat)) {
            Archive.zip("input." + inputFormat, "archive.zip");
            Archive.zip("output." + outputFormat, "archive.zip");
        } else if ("rar".equalsIgnoreCase(archiveFormat)) {
            Archive.rar("input." + inputFormat, "archive.rar");
            Archive.rar("output." + outputFormat, "archive.rar");
        } else {
            result = "Неверный формат архива.";
            return result;
        }
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
                                     @RequestParam("archiveFormat") String archiveFormat) throws NoSuchAlgorithmException, IOException, InterruptedException {
        String result;
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        Encryption.encrypt("input." + inputFormat, "encryptedInput." + "aes", secretKey);
        Encryption.encrypt("output." + inputFormat, "encryptedOutput." + "aes", secretKey);
        if ("zip".equalsIgnoreCase(archiveFormat)) {
            Archive.zip("encryptedInput.aes", "archive.zip");
            Archive.zip("encryptedOutput.aes" + outputFormat, "archive.zip");
        } else if ("rar".equalsIgnoreCase(archiveFormat)) {
            Archive.rar("encryptedInput.aes", "archive.rar");
            Archive.rar("encryptedOutput.aes", "archive.rar");
        } else {
            result = "Неверный формат архива.";
            return result;
        }
        result = "Файлы успешно зашифрованы/заархивированы";
        return result;
    }

    public static class ExpressionRequest {
        private ArrayList<String> expressions;

        public ArrayList<String> getExpressions() {
            return expressions;
        }

        public void setExpressions(ArrayList<String> expressions) {
            this.expressions = expressions;
        }
    }
}

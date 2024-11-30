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

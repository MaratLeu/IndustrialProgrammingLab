package org.example.web_rest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.crypto.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class EncryptionTest {
    @TempDir
    Path temporaryFolder;

    @Test
    void testEncryptionTXT() throws Exception {
        Path testFile = Files.createFile(temporaryFolder.resolve("test.txt"));
        Files.writeString(testFile, "TestEncryptContent");

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] expectedContent = cipher.doFinal(Files.readAllBytes(testFile));
        Path encryptFile = temporaryFolder.resolve("test.aes");
        Encryption.encrypt(testFile.toString(), encryptFile.toString(), secretKey);
        byte[] fileContent = Files.readAllBytes(encryptFile);

        byte[] decodedContent = Base64.getDecoder().decode(fileContent);
        assertArrayEquals(expectedContent, decodedContent);
    }

    @Test
    void testEncryptionJSON() throws Exception {
        Path testFile = Files.createFile(temporaryFolder.resolve("test.json"));
        Files.writeString(testFile, "TestEncryptContent");

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] expectedContent = cipher.doFinal(Files.readAllBytes(testFile));
        Path encryptFile = temporaryFolder.resolve("test.aes");
        Encryption.encrypt(testFile.toString(), encryptFile.toString(), secretKey);
        byte[] fileContent = Files.readAllBytes(encryptFile);

        byte[] decodedContent = Base64.getDecoder().decode(fileContent);
        assertArrayEquals(expectedContent, decodedContent);
    }
}

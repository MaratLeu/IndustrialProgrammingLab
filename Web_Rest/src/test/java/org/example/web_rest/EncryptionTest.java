package org.example.web_rest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.crypto.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncryptionTest {
    @TempDir
    Path temporaryFolder;
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    SecretKey secretKey = keyGenerator.generateKey();

    public EncryptionTest() throws NoSuchAlgorithmException {
    }

    @Test
    void testEncryptionTXT() throws Exception {
        Path testFile = Files.createFile(temporaryFolder.resolve("test.txt"));
        Files.writeString(testFile, "TestEncryptContent");

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
    void testEmptyEncryptionTXT() throws Exception {
        Path testFile = Files.createFile(temporaryFolder.resolve("test.txt"));

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
    void testDecryptionTXT() throws Exception {
        String originalMessage = "TestDecryptContent";

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(originalMessage.getBytes());

        String decryptFile = "test.txt";

        Encryption.decrypt(decryptFile, encrypted, secretKey);

        String decryptedMessage = new String(Files.readAllBytes(Paths.get(decryptFile)));
        assertEquals(originalMessage, decryptedMessage);
    }
}

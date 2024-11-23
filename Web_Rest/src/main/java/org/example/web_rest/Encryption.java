package org.example.web_rest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Encryption {
    public static byte[] encrypt(String filename, String encrypt_file, SecretKey key) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(filename));
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(fileContent);
            FileOutputStream outputStream = new FileOutputStream(encrypt_file);
            outputStream.write(Base64.getEncoder().encode(encrypted));
            outputStream.close();

            return encrypted;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void decrypt(String decrypt_file, byte[] encrypted, SecretKey key) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(decrypt_file));
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encrypted);
            writer.write(new String(decrypted));
            writer.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
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

    /* public static byte[] decrypt(String decrypt_file, byte[] encrypted, SecretKey key) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(decrypt_file));
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encrypted);
            writer.write(new String(decrypted));
            writer.close();
            return decrypted;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/

    public static void decrypt(String decryptFile, String encryptedFilePath, SecretKey key) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(encryptedFilePath));
            byte[] encrypted = Base64.getDecoder().decode(fileContent);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encrypted);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(decryptFile))) {
                writer.write(new String(decrypted)); }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static SecretKey getSecretKey(String password) {
        try {
            // Хешируем пароль с использованием SHA-256
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(password.getBytes("UTF-8"));
            // Берем первые 16 байт (128 бит) для ключа AES
            key = Arrays.copyOf(key, 16);
            // Создаем и возвращаем SecretKey
            return new SecretKeySpec(key, "AES");
        }
        catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации ключа: " + e.getMessage(), e); }
    }
}


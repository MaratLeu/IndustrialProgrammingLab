package org.example.web_rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadWriteTest {
    @Test
    void testWrite_BIN() throws IOException {
        Path testFile1 = Files.createTempFile("test", ".bin");
        ArrayList<String> data = new ArrayList<>();
        data.add("5 + 4 * 8");
        data.add("(5 + 4) * 8");

        ReadWrite.write_bin(testFile1.toString(), data, true);

        try (var inputStream = Files.newInputStream(testFile1)) {
            Input.InputProto inputProto = Input.InputProto.parseDelimitedFrom(inputStream);
            List<String> readData = inputProto.getExpressionsList();
            assertEquals(data, readData);
        }
    }


    @Test
    void testRead_bin_emptyFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".bin");
        ArrayList<String> result = ReadWrite.read_bin(tempFile.toString(), true);
        assertEquals(0, result.size());
    }


    @Test
    void testRead_bin_expressions() throws IOException {
        Path tempFile = Files.createTempFile("test", ".bin");
        ArrayList<String> data = new ArrayList<>();
        data.add("5 + 4 * 8");
        data.add("(5 + 4) * 8");

        ReadWrite.write_bin(tempFile.toString(), data, true); // Запись данных для чтения

        ArrayList<String> readData = ReadWrite.read_bin(tempFile.toString(), true);
        assertEquals(data, readData);
    }

    @Test
    void testRead_bin_results() throws IOException {
        Path tempFile = Files.createTempFile("test", ".bin");
        ArrayList<String> data = new ArrayList<>();
        data.add("Result 1");
        data.add("Result 2");

        ReadWrite.write_bin(tempFile.toString(), data, false); // Запись данных для чтения

        ArrayList<String> readData = ReadWrite.read_bin(tempFile.toString(), false);
        assertEquals(data, readData);
    }

    @Test
    void testRead_bin_invalidFile() {
        assertThrows(IOException.class, () -> ReadWrite.read_bin("nonexistent_file.bin", true));
    }


    @Test
    void testRead_bin_corruptedFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".bin");
        //Запишем что-то не Protobuf в файл, чтобы вызвать ошибку
        Files.writeString(tempFile,"This is not a Protobuf file");

        assertThrows(IOException.class, () -> ReadWrite.read_bin(tempFile.toString(), true));
    }

}

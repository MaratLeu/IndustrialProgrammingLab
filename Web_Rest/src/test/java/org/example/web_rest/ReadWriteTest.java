package org.example.web_rest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadWriteTest {
    // Testing PROTOBUF
    @Test
    void testWrite_BIN_expressions() throws IOException {
        Path testFile1 = Files.createTempFile("test", ".bin");
        ArrayList<String> data = new ArrayList<>();
        data.add("5 + 4 * a");
        data.add("a = 5");
        data.add("(5 + 4) * 8");

        ReadWrite.write_bin(testFile1.toString(), data, true);

        try (var inputStream = Files.newInputStream(testFile1)) {
            Input.InputProto inputProto = Input.InputProto.parseDelimitedFrom(inputStream);
            List<String> readData = inputProto.getExpressionsList();
            assertEquals(data, readData);
        }
    }

    @Test
    void test_Write_BIN_results() throws IOException {
        Path testFile1 = Files.createTempFile("test", ".bin");
        ArrayList<String> data = new ArrayList<>();
        data.add("25");
        data.add("72");

        ReadWrite.write_bin(testFile1.toString(), data, true);

        try (var inputStream = Files.newInputStream(testFile1)) {
            Input.InputProto inputProto = Input.InputProto.parseDelimitedFrom(inputStream);
            List<String> readData = inputProto.getExpressionsList();
            assertEquals(data, readData);
        }
    }


    @Test
    void testRead_BIN_emptyFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".bin");
        ArrayList<String> result = ReadWrite.read_bin(tempFile.toString(), true);
        assertEquals(0, result.size());
    }


    @Test
    void testRead_BIN_expressions() throws IOException {
        Path tempFile = Files.createTempFile("test", ".bin");
        ArrayList<String> data = new ArrayList<>();
        data.add("5 + 4 * 8");
        data.add("(5 + 4) * 8");

        ReadWrite.write_bin(tempFile.toString(), data, true); // Запись данных для чтения

        ArrayList<String> readData = ReadWrite.read_bin(tempFile.toString(), true);
        assertEquals(data, readData);
    }

    @Test
    void testRead_BIN_results() throws IOException {
        Path tempFile = Files.createTempFile("test", ".bin");
        ArrayList<String> data = new ArrayList<>();
        data.add("Result 1");
        data.add("Result 2");

        ReadWrite.write_bin(tempFile.toString(), data, false); // Запись данных для чтения

        ArrayList<String> readData = ReadWrite.read_bin(tempFile.toString(), false);
        assertEquals(data, readData);
    }

    @Test
    void testRead_BIN_invalidFile() {
        assertThrows(IOException.class, () -> ReadWrite.read_bin("nonexistent_file.bin", true));
    }


    @Test
    void testRead_BIN_corruptedFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".bin");
        Files.writeString(tempFile,"This is not a Protobuf file");

        assertThrows(IOException.class, () -> ReadWrite.read_bin(tempFile.toString(), true));
    }


    // TESTING HTML
    @Test
    void testWrite_HTML_expressions() throws IOException {
        Path testFile1 = Files.createTempFile("test", ".html");
        ArrayList<String> data = new ArrayList<>();
        data.add("5 + 4 * a");
        data.add("a = 5");
        data.add("(5 + 4) * 8");
        ReadWrite.write_html(testFile1.toString(), data, true);
        String htmlContent = Files.readString(testFile1);
        Document doc = Jsoup.parse(htmlContent);
        assertEquals(3, doc.select("table tr").size());
        assertEquals("5 + 4 * a", doc.select("table tr:nth-of-type(1) td").first().text());
        assertEquals("a = 5", doc.select("table tr:nth-of-type(2) td").first().text());
        assertEquals("(5 + 4) * 8", doc.select("table tr:nth-of-type(3) td").first().text());
    }

    @Test
    void testWrite_html_results() throws IOException {
        Path tempFile = Files.createTempFile("test", ".html");
        ArrayList<String> data = new ArrayList<>();
        data.add("Result 1");
        data.add("Result 2");

        ReadWrite.write_html(tempFile.toString(), data, false);

        String htmlContent = Files.readString(tempFile);
        Document doc = Jsoup.parse(htmlContent);
        assertEquals(2, doc.select("table tr").size());
        assertEquals("Result 1", doc.select("table tr:nth-of-type(1) td").first().text());
        assertEquals("Result 2", doc.select("table tr:nth-of-type(2) td").first().text());
    }

    @Test
    void testWrite_html_emptyData() throws IOException {
        Path tempFile = Files.createTempFile("test", ".html");
        ArrayList<String> data = new ArrayList<>();
        ReadWrite.write_html(tempFile.toString(), data, true);
        String htmlContent = Files.readString(tempFile);
        Document doc = Jsoup.parse(htmlContent);
        assertEquals(0, doc.select("table tr").size());
    }
}

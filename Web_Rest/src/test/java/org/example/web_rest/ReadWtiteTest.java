package org.example.web_rest;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadWtiteTest {
    @TempDir
    Path temporaryFolder;

    @Test
    void testWriteHTMLWithEmptyData() throws IOException {
        Path filePath = temporaryFolder.resolve("test1.html");
        ReadWrite.write_html(filePath.toString(), new ArrayList<>());
        String htmlContent = Files.readString(filePath);
        assertTrue(htmlContent.contains("<table></table>"));
    }

    @Test
    void testWriteHTMLWithNonEmptyData() throws IOException {
        Path filePath = temporaryFolder.resolve("test2.html");
        List<String> data = List.of("3 + 3 * a", "a = 10", "2 * 3 / 6");
        ReadWrite.write_html(filePath.toString(), new ArrayList<>(data));
        String htmlContent = Files.readString(filePath);

        org.jsoup.nodes.Document document = Jsoup.parse(htmlContent);
        Elements tables = document.select("table tr");
        assertEquals(3, tables.size());
        assertEquals("3 + 3 * a", tables.get(0).select("td").first().text());
        assertEquals("a = 10", tables.get(1).select("td").first().text());
        assertEquals("2 * 3 / 6", tables.get(2).select("td").first().text());
    }

}

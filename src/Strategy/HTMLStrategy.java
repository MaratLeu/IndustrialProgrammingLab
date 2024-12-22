package Strategy;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;

public class HTMLStrategy implements ReadWriteStrategy {
    @Override
    public ArrayList<String> read(String htmlFile, boolean isExpression) throws IOException {
        try {
            ArrayList<String> data = new ArrayList<>();
            org.jsoup.nodes.Document doc = Jsoup.parse(new File(htmlFile), "UTF-8");
            org.jsoup.nodes.Element table = doc.select("table").first();
            if (table == null) {
                System.out.println("Table is empty");
                return data;
            }

            Elements rows = table.select("tr");
            for (org.jsoup.nodes.Element row : rows) {
                Elements cells = row.select("td");
                if (!cells.isEmpty()) {
                    String content = cells.getFirst().text();
                    data.add(content);
                }
            }
            return data;
        } catch (IOException e) {
            throw new IOException("Ошибка чтения HTML: " + e.getMessage(), e);
        }
    }

    @Override
    public void write(String filename, ArrayList<String> data, boolean isExpression) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            org.jsoup.nodes.Document doc = Jsoup.parse("<!DOCTYPE html><html><head></head><body></body></html>", filename);
            org.jsoup.nodes.Element table = doc.select("table").first();
            if (table == null) {
                table = doc.body().appendElement("table");
            }

            for (String item : data) {
                org.jsoup.nodes.Element row = table.appendElement("tr");
                row.appendElement("td").text(item);
            }

            writer.write(doc.outerHtml());
        } catch (IOException e) {
            throw new IOException("Ошибка записи HTML: " + e.getMessage(), e);
        }
    }
}


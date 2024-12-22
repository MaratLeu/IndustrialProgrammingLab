package Strategy;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class TestStrategy {
    public static void main(String[] args) {
        ReadWrite readWrite = new ReadWrite();
        ArrayList<String> data = new ArrayList<>();
        data.add("Example data");

        try {
            // Пример использования с JSON
            readWrite.setStrategy(new JSONStrategy());
            readWrite.write("example.json", data, true);
            ArrayList<String> jsonData = readWrite.read("example.json", true);
            System.out.println("Read JSON data: " + jsonData);

            // Пример использования с YAML
            readWrite.setStrategy(new YAMLStrategy());
            readWrite.write("example.yaml", data, true);
            ArrayList<String> yamlData = readWrite.read("example.json", true);
            System.out.println("Read JSON data: " + yamlData);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
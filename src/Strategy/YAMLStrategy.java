package Strategy;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.*;

public class YAMLStrategy implements ReadWriteStrategy {
    @Override
    public ArrayList<String> read(String yamlFile, boolean isExpression) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(yamlFile))) {
            ArrayList<String> data = new ArrayList<>();
            Yaml yaml = new Yaml();
            Object dataObject = yaml.load(reader);

            if (dataObject instanceof List) {
                List<?> list = (List<?>) dataObject;
                for (Object item : list) {
                    if (item instanceof Map) {
                        Map<?, ?> map = (Map<?, ?>) item;
                        String content = map.get(isExpression ? "expression" : "result").toString();
                        data.add(content);
                    }
                }
            }
            return data;
        } catch (IOException e) {
            throw new IOException("Ошибка чтения YAML: " + e.getMessage(), e);
        }
    }

    @Override
    public void write(String filename, ArrayList<String> data, boolean isExpression) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Yaml yaml = new Yaml(new Constructor(), new Representer());
            List<Map<String, String>> yamlList = new ArrayList<>();

            for (String item : data) {
                Map<String, String> yamlMap = new HashMap<>();
                yamlMap.put(isExpression ? "expression" : "result", item);
                yamlList.add(yamlMap);
            }
            yaml.dump(yamlList, writer);
        } catch (IOException e) {
            throw new IOException("Ошибка записи YAML: " + e.getMessage(), e);
        }
    }
}


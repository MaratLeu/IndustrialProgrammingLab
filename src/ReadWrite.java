import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.protobuf.InvalidProtocolBufferException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.constructor.Constructor;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class ReadWrite {
    // Чтение и запись protobuf
    public static ArrayList<String> read_bin(String protobuf_file, boolean isExpression) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(protobuf_file)) {
            if (isExpression) {
                Input.InputProto message;
                while ((message = Input.InputProto.parseDelimitedFrom(inputStream)) != null) {
                    data.addAll(message.getExpressionsList());
                }
            } else {
                Output.OutputProto message;
                while ((message = Output.OutputProto.parseDelimitedFrom(inputStream)) != null) {
                    data.addAll(message.getResultsList());
                }
            }
        }
        catch (InvalidProtocolBufferException e) {
            throw new IOException("Ошибка парсинга Protobuf: " + e.getMessage(), e);
        }
        catch (IOException e) {
            throw new IOException("Ошибка чтения Protobuf: " + e.getMessage(), e);
        }
        return data;
    }


    public static void write_bin(String filename, ArrayList<String> data, boolean isExpression) throws InvalidProtocolBufferException {
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            if (isExpression) {
                Input.InputProto.Builder inputProtoBuilder = Input.InputProto.newBuilder();
                inputProtoBuilder.addAllExpressions(data);
                Input.InputProto inputProto = inputProtoBuilder.build();
                inputProto.writeDelimitedTo(outputStream);
            }
            else {
                Output.OutputProto.Builder outputProtoBuilder = Output.OutputProto.newBuilder();
                outputProtoBuilder.addAllResults(data);
                Output.OutputProto outputProto = outputProtoBuilder.build();
                outputProto.writeDelimitedTo(outputStream);
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка записи Protobuf: " + e.getMessage());
        }
    }

    // Чтение и запись HTML
    public static ArrayList<String> read_html(String html_file, boolean isExpression) {
        try {
            ArrayList<String> data = new ArrayList<>();
            org.jsoup.nodes.Document doc = Jsoup.parse(new File(html_file), "UTF-8");
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
        }
        catch (IOException e) {
            System.out.println("Ошибка чтения HTML: " + e.getMessage());
            return null;
        }
    }

    public static void write_html(String filename, ArrayList<String> data, boolean isExpression) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            org.jsoup.nodes.Document doc = Jsoup.parse("<!DOCTYPE html><html><head></head><body></body></html>", filename);
            org.jsoup.nodes.Element table = doc.select("table").first();
            if (table == null) {
                table = doc.body().appendElement("table");
            }

            for (String item : data) {
                org.jsoup.nodes.Element row = table.appendElement("tr");
                if (isExpression) {
                    row.appendElement("td").text(item);
                }
                else {
                    row.appendElement("td").text(item);
                }
            }

            writer.write(doc.outerHtml());
        }
        catch (IOException e) {
            System.out.println("Ошибка записи HTML: " + e.getMessage());
        }
    }


    // Чтение и запись yaml
    public static ArrayList<String> read_yaml(String yaml_file, boolean isExpression) {
        try (BufferedReader reader = new BufferedReader(new FileReader(yaml_file))) {
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
        }
        catch (IOException e) {
            System.out.println("Ошибка чтения YAML: " + e.getMessage());
            return null;
        }
    }
    public static void write_yaml(String filename, ArrayList<String> data, boolean isExpression) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Yaml yaml = new Yaml(new Constructor(), new Representer());
            List<Map<String, String>> yamlList = new ArrayList<>();

            for (String item : data) {
                Map<String, String> yamlMap = new HashMap<>();
                if (isExpression) {
                    yamlMap.put("expression", item);
                }
                else {
                    yamlMap.put("result", item);
                }
                yamlList.add(yamlMap);
            }
            yaml.dump(yamlList, writer);
        }
        catch (IOException e) {
            System.out.println("Ошибка записи YAML: " + e.getMessage());
        }
    }

    // Чтение и запись json
    public static ArrayList<String> read_json(String json_file, boolean isExpression) {
        try (BufferedReader reader = new BufferedReader(new FileReader(json_file))) {
            ArrayList<String> data = new ArrayList<>();
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonString.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String content = jsonObject.getString(isExpression ? "expression" : "result");
                data.add(content);
            }
            return data;
        }
        catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void write_json(String filename, ArrayList<String> data, boolean isExpression) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            JSONArray array = new JSONArray();
            for (String item : data) {
                JSONObject object = new JSONObject();
                if (isExpression) {
                    object.put("expression", item);
                } else {
                    object.put("result", item);
                }
                array.put(object);
            }
            writer.write(array.toString(2));
        }
        catch (IOException | JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    // Чтение и запись XML
    public static ArrayList<String> read_xml(String xml_file, boolean isExpression) throws ParserConfigurationException, SAXException, IOException {
        ArrayList<String> data = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xml_file);

        String tagName = isExpression ? "expression" : "result";
        NodeList elements = doc.getElementsByTagName(tagName);
        for (int i = 0; i < elements.getLength(); i++) {
            Element element = (Element) elements.item(i);
            String content = element.getTextContent();
            data.add(content);
        }
        return data;
    }

    public static void write_xml(String filename, ArrayList<String> data, boolean is_expression) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement(is_expression ? "expressions" : "results");
            doc.appendChild(rootElement);

            for (String item : data) {
                Element element = doc.createElement(is_expression ? "expression" : "result");
                element.setTextContent(item);
                rootElement.appendChild(element);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException | TransformerException e) {
            System.err.println(e.getMessage());
        }
    }

    // Чтение и запись обычный текст
    public static ArrayList<String> read_txt(String filename, boolean is_expression) throws IOException{
        ArrayList<String> results = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                results.add(line);
            }
            reader.close();
            return results;
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return results;
        }
    }

    public static void write_txt(String outputFile, ArrayList<String> results, boolean is_expression) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        for (String result : results) {
            writer.write(result);
            writer.newLine();
        }
        writer.close();
    }
}


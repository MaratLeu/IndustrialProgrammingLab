package org.example.web_rest;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String expression;
            ArrayList<String> expressions = new ArrayList<>();
            do {
                System.out.print("Input arithmetic expression: ");
                expression = scanner.nextLine();
                if (!expression.isEmpty()) {
                    expressions.add(expression);
                }
                Set<String> vars = Expression.extractVariables(expression);
                if (!vars.isEmpty()) {
                    Map<String, Integer> values = new HashMap<>();
                    for (String var : vars) {
                        System.out.print("Enter value for " + var + ": ");
                        int value = scanner.nextInt();
                        expressions.add(var + " = " + value);
                        values.put(var, value);
                    }
                    for (Map.Entry<String, Integer> entry : values.entrySet()) {
                        expression = expression.replace(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                    scanner.nextLine();
                }
            } while (!expression.isEmpty());

            // Посчитать выражение
            ReadWrite.write_txt("input.txt", expressions, true);
            ArrayList<String> expressions_input = ReadWrite.read_txt("input.txt", true);
            ArrayList<String> arithmetic_expressions = Expression.transform_to_arithmetic(expressions_input);
            ArrayList<String> results = Expression.evaluateLines(arithmetic_expressions);
            ReadWrite.write_txt("output.txt", results, false);

            // Посчитать с использованием регулярных выражений
            ArrayList<String> results_regex = Expression.evaluate_with_regex(arithmetic_expressions);
            ReadWrite.write_txt("output_2.txt", results_regex, false);

            // Посчитать с использованием библиотеки
            ArrayList<String> results_library = Expression.evaluate_with_library(arithmetic_expressions);
            ReadWrite.write_txt("output_3.txt", results_library, false);

            // Архивировать и разархивировать в zip
            Archive.zip("input.txt", "archive.zip");
            Archive.unzip("archive.zip", "zip");

            // Архивировать и разархивировать в rar
            Archive.rar("input.txt", "archive.rar");
            Archive.unrar("archive.rar", "rar");

            // Шифрование расшифрование
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey key = keyGen.generateKey();
            byte[] encrypted = Encryption.encrypt("input.txt", "encrypt.txt", key);
            Encryption.decrypt("decrypt.txt", encrypted, key);

            // Чтение и запись XML-файл, JSON-файл, YAML-файл, HTML-файл, Protobuf-файл
            ReadWrite.write_xml("input.xml", expressions_input, true);
            ArrayList<String> expresions_xml = ReadWrite.read_xml("input.xml", true);
            ArrayList<String> arithmetic_xml = Expression.transform_to_arithmetic(expresions_xml);
            ArrayList<String> results_xml = Expression.evaluateLines(arithmetic_xml);
            ReadWrite.write_xml("output.xml", results_xml, false);

            ReadWrite.write_json("input.json", expressions_input, true);
            ArrayList<String> expressions_json = ReadWrite.read_json("input.json", true);
            assert expressions_json != null;
            ArrayList<String> arithmetic_json = Expression.transform_to_arithmetic(expressions_json);
            ArrayList<String> results_json = Expression.evaluateLines(arithmetic_json);
            ReadWrite.write_json("output.json", results_json, false);

            ReadWrite.write_yaml("input.yaml", expressions_input, true);
            ArrayList<String> expressions_yaml = ReadWrite.read_yaml("input.yaml", true);
            assert expressions_yaml != null;
            ArrayList<String> arithmetic_yaml = Expression.transform_to_arithmetic(expressions_yaml);
            ArrayList<String> results_yaml = Expression.evaluateLines(arithmetic_yaml);
            ReadWrite.write_yaml("output.yaml", results_yaml, false);

            ReadWrite.write_html("input.html", expressions_input, true);
            ArrayList<String> expressions_html = ReadWrite.read_html("input.html", true);
            assert expressions_html != null;
            ArrayList<String> arithmetic_html = Expression.transform_to_arithmetic(expressions_html);
            ArrayList<String> results_html = Expression.evaluateLines(arithmetic_html);
            ReadWrite.write_html("output.html", results_html, false);

            ReadWrite.write_proto("input.proto", expressions_input, true);
            ArrayList<String> expressions_protobuf = ReadWrite.read_proto("input.proto", true);
            ArrayList<String> arithmetic_protobuf = Expression.transform_to_arithmetic(expressions_protobuf);
            ArrayList<String> results_protobuf = Expression.evaluateLines(arithmetic_protobuf);
            ReadWrite.write_proto("output.proto", results_protobuf, false);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
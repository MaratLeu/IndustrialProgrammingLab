package org.example.web_rest;

import javax.crypto.SecretKey;
import java.io.File;
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

            ArrayList<String> filenames = new ArrayList<>();
            filenames.add("input.txt");
            filenames.add("output.txt");

            // Шифрование расшифрование
            System.out.print("Enter password key: ");
            Scanner in = new Scanner(System.in);
            String password = in.nextLine();
            SecretKey key = Encryption.getSecretKey(password);
            Encryption.encrypt("input.txt", "encrypt.aes", key);
            File encrypted_file = new File("encrypt.aes");
            String filepath = encrypted_file.getAbsolutePath();
            Encryption.decrypt("decrypt.txt", filepath, key);
            filenames.add("encrypt.aes");
            filenames.add("decrypt.txt");

            // Чтение и запись XML-файл, JSON-файл, YAML-файл, HTML-файл, Protobuf-файл
            ReadWrite.write_xml("input.xml", expressions_input, true);
            ArrayList<String> expresions_xml = ReadWrite.read_xml("input.xml", true);
            ArrayList<String> arithmetic_xml = Expression.transform_to_arithmetic(expresions_xml);
            ArrayList<String> results_xml = Expression.evaluateLines(arithmetic_xml);
            ReadWrite.write_xml("output.xml", results_xml, false);
            filenames.add("input.xml");
            filenames.add("output.xml");

            ReadWrite.write_json("input.json", expressions_input, true);
            ArrayList<String> expressions_json = ReadWrite.read_json("input.json", true);
            assert expressions_json != null;
            ArrayList<String> arithmetic_json = Expression.transform_to_arithmetic(expressions_json);
            ArrayList<String> results_json = Expression.evaluateLines(arithmetic_json);
            ReadWrite.write_json("output.json", results_json, false);
            filenames.add("input.json");
            filenames.add("output.json");

            ReadWrite.write_yaml("input.yaml", expressions_input, true);
            ArrayList<String> expressions_yaml = ReadWrite.read_yaml("input.yaml", true);
            assert expressions_yaml != null;
            ArrayList<String> arithmetic_yaml = Expression.transform_to_arithmetic(expressions_yaml);
            ArrayList<String> results_yaml = Expression.evaluateLines(arithmetic_yaml);
            ReadWrite.write_yaml("output.yaml", results_yaml, false);
            filenames.add("input.yaml");
            filenames.add("output.yaml");

            ReadWrite.write_html("input.html", expressions_input, true);
            ArrayList<String> expressions_html = ReadWrite.read_html("input.html", true);
            assert expressions_html != null;
            ArrayList<String> arithmetic_html = Expression.transform_to_arithmetic(expressions_html);
            ArrayList<String> results_html = Expression.evaluateLines(arithmetic_html);
            ReadWrite.write_html("output.html", results_html, false);
            filenames.add("input.html");
            filenames.add("output.html");

            ReadWrite.write_bin("input.bin", expressions_input, true);
            ArrayList<String> expressions_protobuf = ReadWrite.read_bin("input.bin", true);
            ArrayList<String> arithmetic_protobuf = Expression.transform_to_arithmetic(expressions_protobuf);
            ArrayList<String> results_protobuf = Expression.evaluateLines(arithmetic_protobuf);
            ReadWrite.write_bin("output.bin", results_protobuf, false);
            filenames.add("input.bin");
            filenames.add("output.bin");

            // Архивировать и разархивировать в zip
            Archive.zip(filenames, "archive.zip");
            Archive.unzip("archive.zip", "zip");

            // Архивировать и разархивировать в rar
            Archive.rar(filenames, "archive.rar");
            Archive.unrar("archive.rar", "rar");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
import org.xml.sax.SAXException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class UI {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.setLayout(new BorderLayout());

        JLabel expressionsLabel = new JLabel("Enter expressions:");
        expressionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        expressionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        expressionsLabel.setForeground(new Color(0, 102, 104));

        JTextArea expressions = new JTextArea(5, 30);
        expressions.setLineWrap(true);
        expressions.setWrapStyleWord(true);
        JScrollPane expressionsScroll = new JScrollPane(expressions);

        JLabel resultsLabel = new JLabel("Results for expressions:");
        resultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultsLabel.setForeground(new Color(34, 139, 134));

        JTextArea results = new JTextArea(10, 30);
        results.setEditable(false);
        results.setLineWrap(true);
        results.setWrapStyleWord(true);
        JScrollPane resultsScroll = new JScrollPane(results);

        JButton calculate = new JButton("Calculate");
        calculate.setForeground(Color.DARK_GRAY);
        calculate.setFont(new Font("Arial", Font.BOLD, 14));
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                results.setText("");
                String exp = expressions.getText();
                ArrayList<String> lines = new ArrayList<>(Arrays.asList(exp.split("\n")));
                ArrayList<String> arithmetic_lines = Expression.transform_to_arithmetic(lines);
                ArrayList<String> res = Expression.evaluateLines(arithmetic_lines);
                for (String line : res) {
                    results.append(line + "\n");
                }
            }
        });

        JPanel formatPanel = new JPanel(new FlowLayout());
        String[] formats = {"TXT", "JSON", "XML", "YAML", "HTML", "BIN"};
        JComboBox<String> inputFormat = new JComboBox<>(formats);
        JComboBox<String> outputFormat = new JComboBox<>(formats);
        formatPanel.add(new JLabel("Input format:"));
        formatPanel.add(inputFormat);
        formatPanel.add(new JLabel("Output format:"));
        formatPanel.add(outputFormat);
        formatPanel.setBackground(new Color(173, 216, 230));

        JButton writeButton = new JButton("Write to file");
        writeButton.setBackground(new Color(144, 238, 144));
        writeButton.setForeground(Color.BLACK);
        writeButton.setFont(new Font("Arial", Font.BOLD, 12));
        writeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                        ? inputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                        ? outputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";

                String methodName = "write_" + selectedInputFormat;
                String exp = expressions.getText();
                ArrayList<String> lines = new ArrayList<>(Arrays.asList(exp.split("\n")));
                String res = results.getText();
                ArrayList<String> arithmetic_lines = new ArrayList<>(Arrays.asList(res.split("\n")));

                try {
                    Method method = ReadWrite.class.getDeclaredMethod(methodName, String.class, ArrayList.class, boolean.class);
                    method.invoke(null, "input." + selectedInputFormat, lines, true);

                    String outputMethodName = "write_" + selectedOutputFormat;
                    Method outputMethod = ReadWrite.class.getDeclaredMethod(outputMethodName, String.class, ArrayList.class, boolean.class);
                    outputMethod.invoke(null, "output." + selectedOutputFormat, arithmetic_lines, false);
                } catch (NoSuchMethodException ex) {
                    System.out.println("Метод не найден: " + ex.getMessage());
                } catch (InvocationTargetException ex) {
                    System.out.println("Ошибка во время вызова метода: " + ex.getCause().getMessage());
                } catch (IllegalAccessException ex) {
                    System.out.println("Нет доступа к методу: " + ex.getMessage());
                } catch (Exception ex) {
                    System.out.println("Произошла ошибка: " + ex.getMessage());
                }
            }});

        JButton readButton = new JButton("Read from file");
        readButton.setBackground(new Color(144, 238, 144)); // Lighter green
        readButton.setForeground(Color.DARK_GRAY);
        readButton.setFont(new Font("Arial", Font.BOLD, 12));
        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                        ? inputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                        ? outputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";

                String input_methodName = "read_" + selectedInputFormat;
                String output_methodName = "read_" + selectedOutputFormat;
                try {
                    Method input_method = ReadWrite.class.getDeclaredMethod(input_methodName, String.class, boolean.class);
                    ArrayList<String> input_data = (ArrayList<String>) input_method.invoke(null, "input." + selectedInputFormat, true);
                    expressions.setText("");
                    for (String line : input_data) {
                        expressions.append(line + "\n");
                    }

                    Method output_method = ReadWrite.class.getDeclaredMethod(output_methodName, String.class, boolean.class);
                    ArrayList<String> output_data = (ArrayList<String>) output_method.invoke(null, "output." + selectedOutputFormat, false);
                    results.setText("");
                    for (String line : output_data) {
                        results.append(line + "\n");
                    }
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        JPanel archivePanel = new JPanel(new FlowLayout());
        archivePanel.setBackground(new Color(255, 228, 196));
        String[] archiveFormats = {"ZIP", "RAR"};
        JComboBox<String> archiveFormat = new JComboBox<>(archiveFormats);
        archivePanel.add(new JLabel("Archive format:"));
        archivePanel.add(archiveFormat);

        JButton archiveButton = new JButton("Archive");
        archiveButton.setFont(new Font("Arial", Font.BOLD, 14));
        archiveButton.setForeground(new Color(140, 80, 250));
        archiveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Логика архивирования
                String selectedArchiveFormat = (archiveFormat.getSelectedItem() != null)
                        ? archiveFormat.getSelectedItem().toString().toLowerCase()
                        : "zip";

                String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                        ? inputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                        ? outputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                try {
                    ArrayList<String> filenames = new ArrayList<>();
                    filenames.add("input." + selectedInputFormat);
                    filenames.add("output." + selectedOutputFormat);
                    Method method = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                    method.invoke(null, filenames, "archive." + selectedArchiveFormat);
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        // Создаем панель для кнопок действий с использованием BoxLayout
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBackground(new Color(100, 180, 190));

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        encryptButton.setForeground(new Color(140, 80, 250));
        JButton archiveThenEncryptButton = new JButton("Archive then Encrypt");
        archiveThenEncryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        archiveThenEncryptButton.setForeground(new Color(140, 80, 250));
        JButton encryptThenArchiveButton = new JButton("Encrypt then Archive");
        encryptThenArchiveButton.setFont(new Font("Arial", Font.BOLD, 14));
        encryptThenArchiveButton.setForeground(new Color(140, 80, 250));
        JButton doNothingButton = new JButton("Do Nothing");
        doNothingButton.setFont(new Font("Arial", Font.BOLD, 14));
        doNothingButton.setForeground(new Color(140, 80, 250));

        // Установка одинакового размера для всех кнопок
        Dimension buttonSize = new Dimension(200, 95);
        encryptButton.setMaximumSize(buttonSize);
        archiveThenEncryptButton.setMaximumSize(buttonSize);
        encryptThenArchiveButton.setMaximumSize(buttonSize);
        doNothingButton.setMaximumSize(buttonSize);
        archiveButton.setMaximumSize(buttonSize);
        calculate.setMaximumSize(buttonSize);
        writeButton.setMaximumSize(buttonSize);
        readButton.setMaximumSize(buttonSize);

        // Добавление кнопок в actionPanel
        actionPanel.add(encryptButton);
        actionPanel.add(archiveThenEncryptButton);
        actionPanel.add(encryptThenArchiveButton);
        actionPanel.add(doNothingButton);

        // Действия для кнопок
        encryptButton.addActionListener(e -> {
            // Логика шифрования
            try {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                SecretKey key = keyGen.generateKey();
                String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                        ? inputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                        ? outputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                Encryption.encrypt("input." + selectedInputFormat, "input_encrypt." + selectedInputFormat, key);
                Encryption.encrypt(selectedOutputFormat, "output_encrypt." + selectedOutputFormat, key);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        archiveThenEncryptButton.addActionListener(e -> {
            // Логика архивирования, потом шифрования
            String selectedArchiveFormat = (archiveFormat.getSelectedItem() != null)
                    ? archiveFormat.getSelectedItem().toString().toLowerCase()
                    : "zip";

            String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                    ? inputFormat.getSelectedItem().toString().toLowerCase()
                    : "txt";
            String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                    ? outputFormat.getSelectedItem().toString().toLowerCase()
                    : "txt";
            try {
                ArrayList<String> filenames = new ArrayList<>();
                filenames.add("input." + selectedInputFormat);
                filenames.add("output." + selectedOutputFormat);
                Method method = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                method.invoke(null, filenames, "archive." + selectedArchiveFormat);

                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                SecretKey key = keyGen.generateKey();
                Encryption.encrypt("archive." + selectedArchiveFormat, "encrypt." + "aes", key);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        encryptThenArchiveButton.addActionListener(e -> {
            // Логика шифрования, потом архивирования
            String selectedArchiveFormat = (archiveFormat.getSelectedItem() != null)
                    ? archiveFormat.getSelectedItem().toString().toLowerCase()
                    : "zip";

            String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                    ? inputFormat.getSelectedItem().toString().toLowerCase()
                    : "txt";
            String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                    ? outputFormat.getSelectedItem().toString().toLowerCase()
                    : "txt";
            try {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                SecretKey key = keyGen.generateKey();
                Encryption.encrypt("input." + selectedInputFormat, "input_encrypt." + selectedInputFormat, key);
                Encryption.encrypt(selectedOutputFormat, "output_encrypt." + selectedOutputFormat, key);

                ArrayList<String> filenames = new ArrayList<>();
                filenames.add("input_encrypt." + selectedInputFormat);
                filenames.add("output_encrypt." + selectedOutputFormat);
                Method method = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                method.invoke(null, filenames, "archive." + selectedArchiveFormat);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        doNothingButton.addActionListener(e -> {
            // Ничего не делать
        });

        // Создаем главную панель для размещения всех элементов
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(120, 240, 240));

        mainPanel.add(expressionsLabel, BorderLayout.NORTH);
        mainPanel.add(expressionsScroll, BorderLayout.CENTER);

        // Панель для результатов
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(resultsLabel, BorderLayout.NORTH);
        resultsPanel.add(resultsScroll, BorderLayout.CENTER);
        resultsPanel.setBackground(new Color(120, 240, 150));

        mainPanel.add(resultsPanel, BorderLayout.SOUTH);

        // Добавление всех компонентов в окно
        frame.add(mainPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(); // Создаем JPanel
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // Устанавливаем BoxLayout

        // Создаем JPanel для каждой кнопки
        JPanel writePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        writePanel.add(writeButton);
        writePanel.setBackground(new Color(240, 255, 240));

        JPanel readPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        readPanel.add(readButton);
        readPanel.setBackground(new Color(173, 216, 230));

        // Добавляем панели в bottomPanel
        bottomPanel.add(writePanel);
        bottomPanel.add(readPanel);
        bottomPanel.add(formatPanel);
        bottomPanel.add(archivePanel);

        JPanel actionBottomPanel = new JPanel();
        actionBottomPanel.setLayout(new BoxLayout(actionBottomPanel, BoxLayout.Y_AXIS));
        actionBottomPanel.setBackground(new Color(100, 180, 190));
        actionBottomPanel.add(archiveButton);
        actionBottomPanel.add(actionPanel);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(actionBottomPanel, BorderLayout.EAST);

        frame.add(calculate, BorderLayout.WEST);

        frame.setVisible(true);
    }
}
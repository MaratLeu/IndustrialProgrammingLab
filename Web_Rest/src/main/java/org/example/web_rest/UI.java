package org.example.web_rest;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

public class UI {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Arithmetic Evaluator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.setLayout(new BorderLayout());

        // Панель для размещения компонентов
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel expressionsLabel = new JLabel("Enter expressions:");
        expressionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        expressionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        expressionsLabel.setForeground(new Color(0, 102, 104));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(expressionsLabel, gbc);

        JTextArea expressions = new JTextArea(10, 30);
        expressions.setLineWrap(true);
        expressions.setWrapStyleWord(true);
        JScrollPane expressionsScroll = new JScrollPane(expressions);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(expressionsScroll, gbc);

        JLabel resultsLabel = new JLabel("Results for expressions:");
        resultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultsLabel.setForeground(new Color(34, 139, 134));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(resultsLabel, gbc);

        JTextArea results = new JTextArea(10, 30);
        results.setEditable(false);
        results.setLineWrap(true);
        results.setWrapStyleWord(true);
        JScrollPane resultsScroll = new JScrollPane(results);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(resultsScroll, gbc);

        // JComboBox для выбора форматов
        String[] formats = {"TXT", "JSON", "XML", "YAML", "HTML", "BIN", "AES"};
        JComboBox<String> inputFormat = new JComboBox<>(formats);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(inputFormat, gbc);

        JComboBox<String> outputFormat = new JComboBox<>(formats);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(outputFormat, gbc);

        // Кнопки для чтения и записи
        JButton readInput = new JButton("Read Input");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(readInput, gbc);

        JButton writeInput = new JButton("Write Input");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(writeInput, gbc);

        JButton readOutput = new JButton("Read Output");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(readOutput, gbc);

        JButton writeOutput = new JButton("Write Output");
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(writeOutput, gbc);

        // JComboBox для видов обработки
        String[] engines = {"Archive", "Encrypt", "Archive then Encrypt", "Encrypt then Archive"};
        JComboBox<String> inputEngine = new JComboBox<>(engines);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(inputEngine, gbc);

        JComboBox<String> outputEngine = new JComboBox<>(engines);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(outputEngine, gbc);

        // Кнопки для применения видов обработки
        JButton applyInputEngine = new JButton("Apply Input Engine");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(applyInputEngine, gbc);

        JButton applyOutputEngine = new JButton("Apply Output Engine");
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(applyOutputEngine, gbc);

        // Поля пароля и JComboBox для ключей шифрования и форматов сжатия
        JLabel inputKeyLabel = new JLabel("Encryption Key (Input):");
        inputKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputKeyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputKeyLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel.add(inputKeyLabel, gbc);

        JPasswordField inputEncryptionKey = new JPasswordField(30);
        inputEncryptionKey.setEchoChar('*');
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(inputEncryptionKey, gbc);

        JLabel outputKeyLabel = new JLabel("Decryption Key (Output):");
        outputKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        outputKeyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputKeyLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(outputKeyLabel, gbc);

        JPasswordField outputEncryptionKey = new JPasswordField(30);
        outputEncryptionKey.setEchoChar('*');
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(outputEncryptionKey, gbc);

        JLabel inputCompressionLabel = new JLabel("Compression Format (Input):");
        inputCompressionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputCompressionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputCompressionLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(inputCompressionLabel, gbc);

        String[] compressionFormats = {"RAR", "ZIP"};
        JComboBox<String> inputCompressionFormat = new JComboBox<>(compressionFormats);
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(inputCompressionFormat, gbc);

        JLabel outputCompressionLabel = new JLabel("Compression Format (Output):");
        outputCompressionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        outputCompressionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputCompressionLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(outputCompressionLabel, gbc);

        JComboBox<String> outputCompressionFormat = new JComboBox<>(compressionFormats);
        gbc.gridx = 1;
        gbc.gridy = 10;
        panel.add(outputCompressionFormat, gbc);

        // Кнопка для вычисления
        JButton calculate = new JButton("Calculate");
        calculate.setForeground(Color.DARK_GRAY);
        calculate.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calculate, gbc);

        // Вычисление выражения
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

        // Чтение файла входного
        readInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                        ? inputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                String encryptionKey = new String(inputEncryptionKey.getPassword());
                SecretKey key = Encryption.getSecretKey(encryptionKey);

                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        selectedInputFormat.toUpperCase() + " files, ZIP and RAR files",
                        selectedInputFormat, "zip", "rar"));
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
                    File tempDir = new File(tempDirPath);

                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                    }

                    try {
                        if ((selectedFile.getName().endsWith(".zip") || selectedFile.getName().endsWith(".rar"))) {
                            if (selectedFile.getName().endsWith(".zip")) {
                                Archive.unzip(selectedFile.getAbsolutePath(), tempDirPath);
                            } else if (selectedFile.getName().endsWith(".rar")) {
                                Archive.unrar(selectedFile.getAbsolutePath(), tempDirPath);
                            }
                            JFileChooser tempFileChooser = new JFileChooser(tempDir);
                            tempFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(selectedInputFormat.toUpperCase() + " files", selectedInputFormat));
                            int fileChooserReturnValue = tempFileChooser.showOpenDialog(null);
                            if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                                File chosenFile = tempFileChooser.getSelectedFile();
                                ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(chosenFile.toPath());
                                expressions.setText("");
                                for (String line : lines) {
                                    expressions.append(line + "\n");
                                }
                            }
                        }

                        else if (selectedFile.getName().endsWith(".aes")) {
                            String decryptFilePath = tempDirPath + "/decrypted_" + selectedFile.getName().replace(".aes", ".txt");
                            Encryption.decrypt(decryptFilePath, selectedFile.getAbsolutePath(), key);
                            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(decryptFilePath));
                            expressions.setText("");
                            for (String line : lines) {
                                expressions.append(line + "\n");
                            }
                        }

                        else {
                            ArrayList<String> input_data = (ArrayList<String>) Files.readAllLines(Path.of(selectedFile.getAbsolutePath()));
                            expressions.setText("");
                            for (String line : input_data) {
                                expressions.append(line + "\n");
                            }
                        }
                    }

                    catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        // Чтение файла выходного
        readOutput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                        ? outputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                String encryptionKey = new String(inputEncryptionKey.getPassword());
                SecretKey key = Encryption.getSecretKey(encryptionKey);

                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        selectedOutputFormat.toUpperCase() + " files, ZIP and RAR files",
                        selectedOutputFormat, "zip", "rar"));
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
                    File tempDir = new File(tempDirPath);

                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                    }

                    try {
                        if ((selectedFile.getName().endsWith(".zip") || selectedFile.getName().endsWith(".rar"))) {
                            if (selectedFile.getName().endsWith(".zip")) {
                                Archive.unzip(selectedFile.getAbsolutePath(), tempDirPath);
                            } else if (selectedFile.getName().endsWith(".rar")) {
                                Archive.unrar(selectedFile.getAbsolutePath(), tempDirPath);
                            }
                            JFileChooser tempFileChooser = new JFileChooser(tempDir);
                            tempFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(selectedOutputFormat.toUpperCase() + " files", selectedOutputFormat));
                            int fileChooserReturnValue = tempFileChooser.showOpenDialog(null);
                            if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                                File chosenFile = tempFileChooser.getSelectedFile();
                                ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(chosenFile.toPath());
                                results.setText("");
                                for (String line : lines) {
                                    results.append(line + "\n");
                                }
                            }
                        }

                        else if (selectedFile.getName().endsWith(".aes")) {
                            String decryptFilePath = tempDirPath + "/decrypted_" + selectedFile.getName().replace(".aes", ".txt");
                            Encryption.decrypt(decryptFilePath, selectedFile.getAbsolutePath(), key);
                            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(decryptFilePath));
                            results.setText("");
                            for (String line : lines) {
                                results.append(line + "\n");
                            }
                        }

                        else {
                            ArrayList<String> output_data = (ArrayList<String>) Files.readAllLines(Path.of(selectedFile.getAbsolutePath()));
                            results.setText("");
                            for (String line : output_data) {
                                results.append(line + "\n");
                            }
                        }
                    }
                    catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        // Запись во входной файл
        writeInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                        ? inputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        selectedInputFormat.toUpperCase() + " files",
                        selectedInputFormat));
                int returnValue = fileChooser.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getAbsolutePath();

                    // Добавляем расширение, если его нет
                    if (!fileName.endsWith("." + selectedInputFormat)) {
                        fileName += "." + selectedInputFormat;
                        selectedFile = new File(fileName);
                    }

                    String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
                    File tempDir = new File(tempDirPath);

                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                    }

                    try {
                        Files.write(selectedFile.toPath(), expressions.getText().lines().toList());
                    }
                    catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        // Запись в выходной файл
        writeOutput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                        ? outputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        selectedOutputFormat.toUpperCase() + " files",
                        selectedOutputFormat));
                int returnValue = fileChooser.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getAbsolutePath();

                    // Добавляем расширение, если его нет
                    if (!fileName.endsWith("." + selectedOutputFormat)) {
                        fileName += "." + selectedOutputFormat;
                        selectedFile = new File(fileName);
                    }

                    String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
                    File tempDir = new File(tempDirPath);

                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                    }

                    try {
                        Files.write(selectedFile.toPath(), results.getText().lines().toList());
                    }
                    catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        applyInputEngine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedEngineFormat = (inputEngine.getSelectedItem() != null)
                        ? inputEngine.getSelectedItem().toString().toLowerCase()
                        : "archive";
                String selectedInputFormat = (inputFormat.getSelectedItem() != null)
                        ? inputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                String selectedArchiveFormat = (inputCompressionFormat.getSelectedItem() != null)
                        ? inputCompressionFormat.getSelectedItem().toString().toLowerCase()
                        : "rar";
                String encryptionKey = new String(inputEncryptionKey.getPassword());
                SecretKey key = Encryption.getSecretKey(encryptionKey);

                switch (selectedEngineFormat) {
                    case "archive":
                        // Выбор файла для архивации
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                selectedInputFormat.toUpperCase() + " files",
                                selectedInputFormat));
                        int returnValue = fileChooser.showOpenDialog(null);

                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File fileToArchive = fileChooser.getSelectedFile();

                            // Выбор архива, в который будет добавлен файл
                            JFileChooser archiveChooser = new JFileChooser();
                            archiveChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                    selectedArchiveFormat.toUpperCase() + " files" ,selectedArchiveFormat));
                            int archiveReturnValue = archiveChooser.showSaveDialog(null);

                            if (archiveReturnValue == JFileChooser.APPROVE_OPTION) {
                                File archiveFile = archiveChooser.getSelectedFile();
                                String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
                                File tempDir = new File(tempDirPath);

                                if (!tempDir.exists()) {
                                    tempDir.mkdirs();
                                }

                                try {
                                    // Создаем временный файл, если выбрано сохранение в архив
                                    File tempFile = new File(tempDirPath, fileToArchive.getName());
                                    Files.copy(fileToArchive.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    ArrayList<String> filenames = new ArrayList<>();
                                    filenames.add(tempFile.getAbsolutePath());
                                    Method archiveMethod = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                                    archiveMethod.setAccessible(true);
                                    archiveMethod.invoke(Archive.class, filenames, archiveFile.getAbsolutePath());
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                        break;

                    case "encrypt":
                        JFileChooser encryptfilechooser = new JFileChooser();
                        encryptfilechooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                selectedInputFormat.toUpperCase() + " files", selectedInputFormat)
                        );
                        int encryptReturnValue = encryptfilechooser.showOpenDialog(null);

                        if (encryptReturnValue == JFileChooser.APPROVE_OPTION) {
                            File fileToEncrypt = encryptfilechooser.getSelectedFile();

                            JFileChooser SaveFileChooser = new JFileChooser();
                            SaveFileChooser.setFileFilter((new javax.swing.filechooser.FileNameExtensionFilter(
                                    "Encrypted files", "aes")));
                            int saveReturnValue = SaveFileChooser.showSaveDialog(null);

                            if (saveReturnValue == JFileChooser.APPROVE_OPTION) {
                                File saveFile = SaveFileChooser.getSelectedFile();
                                String saveFilePath = saveFile.getAbsolutePath();

                                // Добавляем расширение .aes, если его нет
                                if (!saveFilePath.endsWith(".aes")) {
                                    saveFilePath += ".aes";
                                    saveFile = new File(saveFilePath);
                                }

                                Encryption.encrypt(fileToEncrypt.getAbsolutePath(), saveFile.getAbsolutePath(), key);
                            }
                        }
                        break;

                    case "archive then encrypt":
                        // Выбор файла для архивации
                        JFileChooser ArchiveEncryptFileChooser = new JFileChooser();
                        ArchiveEncryptFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                selectedInputFormat.toUpperCase() + " files",
                                selectedInputFormat));
                        int ArchiveEncryptReturnValue = ArchiveEncryptFileChooser.showOpenDialog(null);

                        if (ArchiveEncryptReturnValue == JFileChooser.APPROVE_OPTION) {
                            File fileToArchive = ArchiveEncryptFileChooser.getSelectedFile();

                            String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
                            File tempDir = new File(tempDirPath);
                            if (!tempDir.exists()) {
                                tempDir.mkdirs();
                            }

                            try {
                                // Создаем временный файл, если выбрано сохранение в архив
                                File tempFile = new File(tempDirPath, fileToArchive.getName());
                                Files.copy(fileToArchive.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                ArrayList<String> filenames = new ArrayList<>();
                                filenames.add(tempFile.getAbsolutePath());
                                String archivePath = tempDirPath + "/archive." + selectedArchiveFormat;
                                Method archiveMethod = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                                archiveMethod.setAccessible(true);
                                archiveMethod.invoke(Archive.class, filenames, archivePath);

                                JFileChooser SaveFileChooser = new JFileChooser();
                                SaveFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                        "Encrypted files", "aes")
                                );
                                int SaveArchiveReturnValue = SaveFileChooser.showSaveDialog(null);
                                if (SaveArchiveReturnValue == JFileChooser.APPROVE_OPTION) {
                                    File SaveFile = SaveFileChooser.getSelectedFile();
                                    String saveFilePath = SaveFile.getAbsolutePath();
                                    if (!saveFilePath.endsWith(".aes")) {
                                        saveFilePath += ".aes";
                                        SaveFile = new File(saveFilePath);
                                    }

                                    Encryption.encrypt(archivePath, SaveFile.getAbsolutePath(), key);
                                }

                            }
                            catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                        break;

                    case "encrypt then archive":
                        // Выбор файла для шифрования и архивации
                        JFileChooser EncryptArchiveFileChooser = new JFileChooser();
                        EncryptArchiveFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                selectedInputFormat.toUpperCase() + " files", selectedInputFormat));
                        int EncryptArchiveReturnValue = EncryptArchiveFileChooser.showOpenDialog(null);

                        if (EncryptArchiveReturnValue == JFileChooser.APPROVE_OPTION) {
                            File fileToEncrypt = EncryptArchiveFileChooser.getSelectedFile();

                            JFileChooser saveFileChooser = new JFileChooser();
                            saveFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                    selectedArchiveFormat.toUpperCase() + " files", selectedArchiveFormat));
                            int saveReturnValue = saveFileChooser.showSaveDialog(null);

                            if (saveReturnValue == JFileChooser.APPROVE_OPTION) {
                                File saveFile = saveFileChooser.getSelectedFile();
                                String saveFilePath = saveFile.getAbsolutePath();

                                if (!saveFilePath.endsWith("." + selectedArchiveFormat)) {
                                    saveFilePath += "." + selectedArchiveFormat;
                                    saveFile = new File(saveFilePath);
                                }

                                String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp_" + System.currentTimeMillis();
                                File tempDir = new File(tempDirPath);

                                if (!tempDir.exists()) {
                                    tempDir.mkdirs();
                                }

                                try {
                                    File tempEncryptedFile = new File(tempDirPath, fileToEncrypt.getName() + ".aes");
                                    Encryption.encrypt(fileToEncrypt.getAbsolutePath(), tempEncryptedFile.getAbsolutePath(), key);

                                    ArrayList<String> filenames = new ArrayList<>();
                                    filenames.add(tempEncryptedFile.getAbsolutePath());

                                    Method archiveMethod = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                                    archiveMethod.setAccessible(true);
                                    archiveMethod.invoke(Archive.class, filenames, saveFilePath);
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                        break;

                    default:
                        System.out.println("No action selected");
                        break;
                }
            }
        });


        applyOutputEngine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedEngineFormat = (outputEngine.getSelectedItem() != null)
                        ? outputEngine.getSelectedItem().toString().toLowerCase()
                        : "archive";
                String selectedOutputFormat = (outputFormat.getSelectedItem() != null)
                        ? outputFormat.getSelectedItem().toString().toLowerCase()
                        : "txt";
                String selectedArchiveFormat = (outputCompressionFormat.getSelectedItem() != null)
                        ? outputCompressionFormat.getSelectedItem().toString().toLowerCase()
                        : "rar";
                String encryptionKey = new String(outputEncryptionKey.getPassword());
                SecretKey key = Encryption.getSecretKey(encryptionKey);

                switch (selectedEngineFormat) {
                    case "archive":
                        // Выбор файла для архивации
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                selectedOutputFormat.toUpperCase() + " files",
                                selectedOutputFormat));
                        int returnValue = fileChooser.showOpenDialog(null);

                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File fileToArchive = fileChooser.getSelectedFile();

                            // Выбор архива, в который будет добавлен файл
                            JFileChooser archiveChooser = new JFileChooser();
                            archiveChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                    selectedArchiveFormat.toUpperCase() + " files" ,selectedArchiveFormat));
                            int archiveReturnValue = archiveChooser.showSaveDialog(null);

                            if (archiveReturnValue == JFileChooser.APPROVE_OPTION) {
                                File archiveFile = archiveChooser.getSelectedFile();
                                String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
                                File tempDir = new File(tempDirPath);

                                if (!tempDir.exists()) {
                                    tempDir.mkdirs();
                                }

                                try {
                                    // Создаем временный файл, если выбрано сохранение в архив
                                    File tempFile = new File(tempDirPath, fileToArchive.getName());
                                    Files.copy(fileToArchive.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    ArrayList<String> filenames = new ArrayList<>();
                                    filenames.add(tempFile.getAbsolutePath());
                                    Method archiveMethod = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                                    archiveMethod.setAccessible(true);
                                    archiveMethod.invoke(Archive.class, filenames, archiveFile.getAbsolutePath());
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                        break;

                    case "encrypt":
                        JFileChooser encryptfilechooser = new JFileChooser();
                        encryptfilechooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                selectedOutputFormat.toUpperCase() + " files", selectedOutputFormat)
                        );
                        int encryptReturnValue = encryptfilechooser.showOpenDialog(null);

                        if (encryptReturnValue == JFileChooser.APPROVE_OPTION) {
                            File fileToEncrypt = encryptfilechooser.getSelectedFile();

                            JFileChooser SaveFileChooser = new JFileChooser();
                            SaveFileChooser.setFileFilter((new javax.swing.filechooser.FileNameExtensionFilter(
                                    "Encrypted files", "aes")));
                            int saveReturnValue = SaveFileChooser.showSaveDialog(null);

                            if (saveReturnValue == JFileChooser.APPROVE_OPTION) {
                                File saveFile = SaveFileChooser.getSelectedFile();
                                String saveFilePath = saveFile.getAbsolutePath();

                                // Добавляем расширение .aes, если его нет
                                if (!saveFilePath.endsWith(".aes")) {
                                    saveFilePath += ".aes";
                                    saveFile = new File(saveFilePath);
                                }

                                Encryption.encrypt(fileToEncrypt.getAbsolutePath(), saveFile.getAbsolutePath(), key);
                            }
                        }
                        break;

                    case "archive then encrypt":
                        // Выбор файла для архивации
                        JFileChooser ArchiveEncryptFileChooser = new JFileChooser();
                        ArchiveEncryptFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                selectedOutputFormat.toUpperCase() + " files",
                                selectedOutputFormat));
                        int ArchiveEncryptReturnValue = ArchiveEncryptFileChooser.showOpenDialog(null);

                        if (ArchiveEncryptReturnValue == JFileChooser.APPROVE_OPTION) {
                            File fileToArchive = ArchiveEncryptFileChooser.getSelectedFile();

                            String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp" + System.currentTimeMillis();
                            File tempDir = new File(tempDirPath);
                            if (!tempDir.exists()) {
                                tempDir.mkdirs();
                            }

                            try {
                                // Создаем временный файл, если выбрано сохранение в архив
                                File tempFile = new File(tempDirPath, fileToArchive.getName());
                                Files.copy(fileToArchive.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                ArrayList<String> filenames = new ArrayList<>();
                                filenames.add(tempFile.getAbsolutePath());
                                String archivePath = tempDirPath + "/archive." + selectedArchiveFormat;
                                Method archiveMethod = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                                archiveMethod.setAccessible(true);
                                archiveMethod.invoke(Archive.class, filenames, archivePath);

                                JFileChooser SaveFileChooser = new JFileChooser();
                                SaveFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                        "Encrypted files", "aes")
                                );
                                int SaveArchiveReturnValue = SaveFileChooser.showSaveDialog(null);
                                if (SaveArchiveReturnValue == JFileChooser.APPROVE_OPTION) {
                                    File SaveFile = SaveFileChooser.getSelectedFile();
                                    String saveFilePath = SaveFile.getAbsolutePath();
                                    if (!saveFilePath.endsWith(".aes")) {
                                        saveFilePath += ".aes";
                                        SaveFile = new File(saveFilePath);
                                    }

                                    Encryption.encrypt(archivePath, SaveFile.getAbsolutePath(), key);
                                }

                            }
                            catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                        break;

                    case "encrypt then archive":
                        // Выбор файла для шифрования и архивации
                        JFileChooser EncryptArchiveFileChooser = new JFileChooser();
                        EncryptArchiveFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                selectedOutputFormat.toUpperCase() + " files", selectedOutputFormat));
                        int EncryptArchiveReturnValue = EncryptArchiveFileChooser.showOpenDialog(null);

                        if (EncryptArchiveReturnValue == JFileChooser.APPROVE_OPTION) {
                            File fileToEncrypt = EncryptArchiveFileChooser.getSelectedFile();

                            JFileChooser saveFileChooser = new JFileChooser();
                            saveFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                    selectedArchiveFormat.toUpperCase() + " files", selectedArchiveFormat));
                            int saveReturnValue = saveFileChooser.showSaveDialog(null);

                            if (saveReturnValue == JFileChooser.APPROVE_OPTION) {
                                File saveFile = saveFileChooser.getSelectedFile();
                                String saveFilePath = saveFile.getAbsolutePath();

                                if (!saveFilePath.endsWith("." + selectedArchiveFormat)) {
                                    saveFilePath += "." + selectedArchiveFormat;
                                    saveFile = new File(saveFilePath);
                                }

                                String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_temp_" + System.currentTimeMillis();
                                File tempDir = new File(tempDirPath);

                                if (!tempDir.exists()) {
                                    tempDir.mkdirs();
                                }

                                try {
                                    File tempEncryptedFile = new File(tempDirPath, fileToEncrypt.getName() + ".aes");
                                    Encryption.encrypt(fileToEncrypt.getAbsolutePath(), tempEncryptedFile.getAbsolutePath(), key);

                                    ArrayList<String> filenames = new ArrayList<>();
                                    filenames.add(tempEncryptedFile.getAbsolutePath());

                                    Method archiveMethod = Archive.class.getDeclaredMethod(selectedArchiveFormat, ArrayList.class, String.class);
                                    archiveMethod.setAccessible(true);
                                    archiveMethod.invoke(Archive.class, filenames, saveFilePath);
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                        break;

                    default:
                        System.out.println("No action selected");
                        break;
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calculate, gbc);

        // Добавляем панель к фрейму
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
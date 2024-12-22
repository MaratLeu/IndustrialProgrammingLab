package FactoryMethod;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RarArchiver implements Archiver {
    @Override
    public void archive(ArrayList<String> filenames, String rarArchiveName) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add("rar");
        command.add("a");
        command.add(rarArchiveName);
        command.addAll(filenames);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new java.io.File("."));
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Error creating rar archive: return code " + exitCode);
        }

        File file = new File(rarArchiveName);
        if (file.exists()) {
            long lastModified = file.lastModified();
            if (!file.setLastModified(lastModified + 1)) {
                System.err.println("Failed to refresh file metadata: " + rarArchiveName);
            }
        }
    }

    @Override
    public void extract(String rarArchiveName, String outputDirectory) throws IOException {
        try {
            File outputFolder = new File(outputDirectory);
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }
            String currentDirectory = outputFolder.getCanonicalPath();
            Process process = Runtime.getRuntime().exec(new String[]{"unrar", "e", rarArchiveName, currentDirectory});

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
            process.waitFor();
        } catch (Exception e) {
            throw new IOException("Error extracting rar archive: " + e.getMessage(), e);
        }
    }
}


package org.example.web_rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archive {
    public static void zip(String filename, String zip_archive_name) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip_archive_name));
             FileInputStream in = new FileInputStream(filename))
        {
            ZipEntry entry = new ZipEntry(new File(filename).getName());
            zout.putNextEntry(entry);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void unzip(String zipArchiveName, String outputDirectory) throws IOException {
        File zipFile = new File(zipArchiveName);
        if (!zipFile.exists()) {
            throw new IOException("ZIP-файл не существует: " + zipArchiveName);
        }

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipArchiveName))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                String filename = entry.getName();

                File outputFolder = new File(outputDirectory);
                if (!outputFolder.exists()) {
                    outputFolder.mkdirs();
                }
                String fullPath = new File(outputFolder, filename).getAbsolutePath();

                if (entry.isDirectory()) {
                    new File(fullPath).mkdirs();
                }
                else {
                    FileOutputStream fout = new FileOutputStream(fullPath);
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    fout.close();
                }
                zin.closeEntry();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public static void rar(String filename, String rarArchiveName) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("rar", "a", rarArchiveName, filename);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();  // ждем завершения процесса
        if (exitCode != 0) {
            throw new IOException("Ошибка при создании RAR архива: " + rarArchiveName);
        }
    }

    public static void unrar(String rarArchiveName, String outputDirectory) throws IOException, InterruptedException {
        File outputFolder = new File(outputDirectory);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        ProcessBuilder processBuilder = new ProcessBuilder("unrar", "e", rarArchiveName, outputFolder.getCanonicalPath());
        Process process = processBuilder.start();
        int exitCode = process.waitFor();  // ждем завершения процесса
        if (exitCode != 0) {
            throw new IOException("Ошибка при распаковке RAR архива: " + rarArchiveName);
        }
    }
}

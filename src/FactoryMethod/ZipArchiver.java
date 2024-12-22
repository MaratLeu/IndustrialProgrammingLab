package FactoryMethod;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.*;

public class ZipArchiver implements Archiver {
    @Override
    public void archive(ArrayList<String> filenames, String zipArchiveName) throws IOException {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipArchiveName))) {
            for (String filename : filenames) {
                Path filePath = Paths.get(filename);
                if (Files.exists(filePath)) { // check that file exists
                    try (FileInputStream in = new FileInputStream(filename)) {
                        ZipEntry entry = new ZipEntry(filePath.getFileName().toString());
                        zout.putNextEntry(entry);
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            zout.write(buffer, 0, len);
                        }
                        zout.closeEntry();
                    }
                } else {
                    System.err.println("File not found: " + filename);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error creating zip archive: " + e.getMessage(), e);
        }
    }

    @Override
    public void extract(String zipArchiveName, String outputDirectory) throws IOException {
        Path outputDirPath = Paths.get(outputDirectory);
        Files.createDirectories(outputDirPath); // create directories if they do not exist

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipArchiveName))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                String filename = entry.getName();
                Path fullPath = outputDirPath.resolve(filename);

                if (entry.isDirectory()) {
                    Files.createDirectories(fullPath);
                } else {
                    try (FileOutputStream fout = new FileOutputStream(fullPath.toFile())) {
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = zin.read(buffer)) > 0) {
                            fout.write(buffer, 0, len);
                        }
                    } catch (IOException e) {
                        throw new IOException("Error extracting file '" + filename + "': " + e.getMessage(), e);
                    }
                }
                zin.closeEntry();
            }
        } catch (IOException e) {
            throw new IOException("Error unzipping archive '" + zipArchiveName + "': " + e.getMessage(), e);
        }
    }
}


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class Archive {
    public static void zip (ArrayList<String> filenames, String zip_archive_name) throws IOException {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip_archive_name))) {
            for (String filename : filenames) {
                Path filePath = Paths.get(filename);
                if (Files.exists(filePath)) { //check that file exists
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

    public static void unzip(String zipArchiveName, String outputDirectory) throws IOException {
        Path outputDirPath = Paths.get(outputDirectory);
        Files.createDirectories(outputDirPath); //create directories if they do not exist

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

    public static void rar (ArrayList<String> filenames, String rar_archive_name) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add("rar");
        command.add("a");
        command.add(rar_archive_name);
        command.addAll(filenames);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new java.io.File("."));
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Error creating rar archive: return code " + exitCode);
        }

        File file = new File(rar_archive_name);
        if(file.exists()){
            long lastModified = file.lastModified();
            if (!file.setLastModified(lastModified + 1)){
                System.err.println("Failed to refresh file metadata: " + rar_archive_name);
            }
        }

    }

    public static void unrar(String rar_archive_name, String outputDirectory) {
        try {
            File outputFolder = new File(outputDirectory);
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }
            String currentDirectory = outputFolder.getCanonicalPath();
            Runtime.getRuntime().exec(new String[] {"unrar", "e", rar_archive_name, currentDirectory});
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

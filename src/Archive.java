import java.io.*;
import java.util.zip.*;

public class Archive {
    public static void zip(String filename, String zip_archive_name) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip_archive_name));
             FileInputStream in = new FileInputStream(filename))
        {
            ZipEntry entry = new ZipEntry(filename);
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

    public static void unzip(String zipArchiveName, String outputDirectory) {
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



    public static void rar(String filename, String rar_archive_name) {
        try {
            Runtime.getRuntime().exec(new String[] {"rar", "a", rar_archive_name, filename});
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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

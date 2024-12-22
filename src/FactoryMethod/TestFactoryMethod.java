package FactoryMethod;

import java.io.IOException;
import java.util.ArrayList;

public class TestFactoryMethod {
    public static void main(String[] args) {
        ArrayList<String> filenames = new ArrayList<>();
        filenames.add("input.txt");
        filenames.add("output.txt");

        String archiveType = "zip";
        Archiver archiver = ArchiverFactory.getArchiver(archiveType);

        try {
            archiver.archive(filenames, "output." + archiveType);
            archiver.extract("output." + archiveType, "outputDir");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


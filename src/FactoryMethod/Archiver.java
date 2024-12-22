package FactoryMethod;

import java.io.IOException;
import java.util.ArrayList;

public interface Archiver {
    void archive(ArrayList<String> filenames, String archiveName) throws IOException, InterruptedException;
    void extract(String archiveName, String outputDirectory) throws IOException, InterruptedException;
}

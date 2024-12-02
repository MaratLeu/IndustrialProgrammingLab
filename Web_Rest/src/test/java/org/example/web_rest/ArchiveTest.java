package org.example.web_rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiveTest {
    @TempDir
    Path temporaryFolder;

    @Test
    void testZip() throws IOException {
        Path testFile1 = Files.createFile(temporaryFolder.resolve("test1.txt"));
        Files.writeString(testFile1, "TestArchiveContent1");
        Path testFile2 = Files.createFile(temporaryFolder.resolve("test2.txt"));
        Files.writeString(testFile2, "TestArchiveContent2");

        ArrayList<String> filenames = new ArrayList<>();
        filenames.add(testFile1.toString());
        filenames.add(testFile2.toString());
        String zipArchiveName = temporaryFolder.resolve("test.zip").toString();

        Archive.zip(filenames, zipArchiveName);

        assertTrue(Files.exists(temporaryFolder.resolve("test.zip")));
    }


    @Test
    void testUnzip() throws IOException {
        // Create files for zipping
        Path testFile1 = Files.createFile(temporaryFolder.resolve("test1.txt"));
        Files.writeString(testFile1, "TestArchiveContent1");
        Path testFile2 = Files.createFile(temporaryFolder.resolve("test2.txt"));
        Files.writeString(testFile2, "TestArchiveContent2");

        ArrayList<String> filenames = new ArrayList<>();
        filenames.add(testFile1.toString());
        filenames.add(testFile2.toString());

        String zipArchiveName = temporaryFolder.resolve("test.zip").toString();
        Archive.zip(filenames, zipArchiveName);

        String outputDirectory = temporaryFolder.resolve("unzip_output").toString();
        Archive.unzip(zipArchiveName, outputDirectory);

        Path unzippedFile1 = Paths.get(outputDirectory, "test1.txt");
        Path unzippedFile2 = Paths.get(outputDirectory, "test2.txt");

        assertTrue(Files.exists(unzippedFile1), "Распакованный файл test1.txt отсутствует");
        assertTrue(Files.exists(unzippedFile2), "Распакованный файл test2.txt отсутствует");
        assertEquals("TestArchiveContent1", Files.readString(unzippedFile1), "Содержимое распакованного файла test1.txt неверно");
        assertEquals("TestArchiveContent2", Files.readString(unzippedFile2), "Содержимое распакованного файла test2.txt неверно");
    }


    @Test
    void testZipWithEmptyFile() throws IOException {
        Path emptyFile1 = Files.createFile(temporaryFolder.resolve("empty1.txt"));
        Path emptyFile2 = Files.createFile(temporaryFolder.resolve("empty2.txt"));
        ArrayList<String> filenames = new ArrayList<>();
        filenames.add(emptyFile1.toString());
        filenames.add(emptyFile2.toString());
        String zipArchiveName = temporaryFolder.resolve("empty.zip").toString();

        Archive.zip(filenames, zipArchiveName);

        assertTrue(Files.exists(temporaryFolder.resolve("empty.zip")));
    }


    @Test
    void testRar() throws IOException, InterruptedException {
        Path testFile1 = Files.createFile(temporaryFolder.resolve("test1.txt"));
        Files.writeString(testFile1, "TestRarContent1");
        Path testFile2 = Files.createFile(temporaryFolder.resolve("test2.txt"));
        Files.writeString(testFile2, "TestRarContent2");

        ArrayList<String> filenames = new ArrayList<>();
        filenames.add(testFile1.toString());
        filenames.add(testFile2.toString());
        String rarArchiveName = temporaryFolder.resolve("test.rar").toString();
        Archive.rar(filenames, rarArchiveName);
        assertTrue(Files.exists(temporaryFolder.resolve("test.rar")), "RAR файл не был создан");
    }

    @Test
    void testUnrar() throws IOException, InterruptedException {
        Path testFile1 = Files.createFile(temporaryFolder.resolve("test1.txt"));
        Files.writeString(testFile1, "TestRarContent1");
        Path testFile2 = Files.createFile(temporaryFolder.resolve("test2.txt"));
        Files.writeString(testFile2, "TestRarContent2");

        ArrayList<String> filenames = new ArrayList<>();
        filenames.add(testFile1.toString());
        filenames.add(testFile2.toString());

        String rarArchiveName = temporaryFolder.resolve("test.rar").toString();
        Archive.rar(filenames, rarArchiveName);
        String outputDirectory = temporaryFolder.resolve("unrar_output").toString();
        Archive.unrar(rarArchiveName, outputDirectory);

        Path unrarredFile1 = Paths.get(outputDirectory, "test1.txt");
        Path unrarredFile2 = Paths.get(outputDirectory, "test2.txt");

        assertTrue(Files.exists(unrarredFile1), "Распакованный файл test1.txt отсутствует");
        assertTrue(Files.exists(unrarredFile2), "Распакованный файл test2.txt отсутствует");
        assertEquals("TestRarContent1", Files.readString(unrarredFile1), "Содержимое распакованного файла test1.txt неверно");
        assertEquals("TestRarContent2", Files.readString(unrarredFile2), "Содержимое распакованного файла test2.txt неверно");

    }
}
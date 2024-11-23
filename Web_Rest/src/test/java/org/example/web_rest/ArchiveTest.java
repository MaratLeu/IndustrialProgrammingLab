package org.example.web_rest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiveTest {
    @TempDir
    Path temporaryFolder;

    @Test
    void testZip() throws IOException {
        Path testFile = Files.createFile(temporaryFolder.resolve("test.txt"));
        Files.writeString(testFile, "TestArchiveContent");
        String zipArchiveName = temporaryFolder.resolve("test.zip").toString();

        Archive.zip(testFile.toString(), zipArchiveName);

        assertTrue(Files.exists(temporaryFolder.resolve("test.zip")));
    }

    @Test
    void testUnzip() throws IOException {
        Path testFile = Files.createFile(temporaryFolder.resolve("test.txt"));
        Files.writeString(testFile, "TestArchiveContent");

        String zipArchiveName = temporaryFolder.resolve("test.zip").toString();
        Archive.zip(testFile.toString(), zipArchiveName);

        String outputDirectory = temporaryFolder.resolve("unzip_output").toString();

        Archive.unzip(zipArchiveName, outputDirectory);

        Path unzippedFile = Paths.get(outputDirectory, "test.txt");
        assertTrue(Files.exists(unzippedFile), "Распакованный файл отсутствует");

        assertEquals("TestArchiveContent", Files.readString(unzippedFile), "Содержимое распакованного файла неверно");
    }

    @Test
    void testZipWithEmptyFile() throws IOException {
        Path emptyFile = Files.createFile(temporaryFolder.resolve("empty.txt"));
        String zipArchiveName = temporaryFolder.resolve("empty.zip").toString();

        Archive.zip(emptyFile.toString(), zipArchiveName);

        assertTrue(Files.exists(temporaryFolder.resolve("empty.zip")));
    }

    @Test
    void testUnzipNonExistentZip() {
        String outputDirectory = temporaryFolder.resolve("non_existent_output").toString();
        assertThrows(IOException.class, () -> {
            Archive.unzip("non_existent.zip", outputDirectory);
        });
    }

    @Test
    void testRar() throws IOException, InterruptedException {
        Path testFile = Files.createFile(temporaryFolder.resolve("test.txt"));
        Files.writeString(testFile, "TestRarContent");
        String rarArchiveName = temporaryFolder.resolve("test.rar").toString();

        Archive.rar(testFile.toString(), rarArchiveName);

        assertTrue(Files.exists(temporaryFolder.resolve("test.rar")), "RAR файл не был создан");
    }

    @Test
    void testUnrar() throws IOException, InterruptedException {
        Path testFile = Files.createFile(temporaryFolder.resolve("test.txt"));
        Files.writeString(testFile, "TestRarContent");
        String rarArchiveName = temporaryFolder.resolve("test.rar").toString();

        Archive.rar(testFile.toString(), rarArchiveName);

        String outputDirectory = temporaryFolder.resolve("unrar_output").toString();
        Archive.unrar(rarArchiveName, outputDirectory);

        Path unrarredFile = Paths.get(outputDirectory, "test.txt");
        assertTrue(Files.exists(unrarredFile), "Распакованный файл отсутствует");
        assertEquals("TestRarContent", Files.readString(unrarredFile), "Содержимое распакованного файла неверно");
    }
}

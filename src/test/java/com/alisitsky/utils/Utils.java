package com.alisitsky.utils;

import com.alisitsky.tests.ReadingFileFromZipTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;



public class Utils {
    ClassLoader cl = ReadingFileFromZipTest.class.getClassLoader();

    public static InputStream getInputStreamForFileFromZip(String zipFilePath, String fileName) throws IOException {
        ClassLoader cl = ReadingFileFromZipTest.class.getClassLoader();

        ZipInputStream zipInputStream = new ZipInputStream(cl.getResourceAsStream(zipFilePath));
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            if (entry.getName().equals(fileName)) {
                return zipInputStream;
            }
        }
        throw new IOException("Файл " + fileName + " не найден в указанном архиве");
    }
}

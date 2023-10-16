package com.alisitsky.tests;

import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReadingFromZipTest {

//– Запаковать в zip архив несколько разных файлов - pdf, xlsx, csv
//– Положить его в ресурсы
//– Реализовать чтение и проверку содержимого каждого файла из архива

    ClassLoader cl = ReadingFromZipTest.class.getClassLoader();

    @Test
    void zipTest() throws Exception {

        boolean fileIsFound = false;

        try (InputStream inputStream = cl.getResourceAsStream("hw/hw.zip");
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            checkZipInputStreamContainsFile(zipInputStream, "shell gamedesign.pdf");

            File extractedFile = extractFileFromZipInputStream(zipInputStream, "shell gamedesign.pdf");
            System.out.println(1);
            File testFile = new File("hw/shell.pdf");
            PDF pdf = new PDF(testFile);
            checkPdfContainsText(pdf, "Джесси Шелл – один из известнейших геймдизайнеров, который работал на\r\nWalt Disney Company");



//            ZipEntry zipEntry;
//            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
//                String zeName = zipEntry.getName();
//                if (zipEntry.getName().equals("shell gamedesign.pdf")) {
//                    fileIsFound = true;
//                    System.out.println(zipEntry.getName());
//
//
//
//                    PDF pdf = new PDF(zipInputStream);
//                    String expectedString = "Джесси Шелл – один из известнейших геймдизайнеров, который работал на\r\nWalt Disney Company";
//                    Assertions.assertTrue(pdf.text.contains(expectedString));
//                }
//            }
//            if (!fileIsFound)
//                System.out.println("File Not Found in zip");
        }

//
    }

    public ZipInputStream checkZipInputStreamContainsFile(ZipInputStream zipInputStream, String fileName) throws Exception  {

        boolean fileIsFound = false;
        ZipEntry zipEntry;

        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
           String zeName = zipEntry.getName();
           if (zeName.equals(fileName)) {
               fileIsFound = true;
               System.out.println("File found: " + zeName);
              }
        }
        if (!fileIsFound)
               System.out.println("File Not Found in zip");

        return zipInputStream;
    }



    public static File extractFileFromZipInputStream(ZipInputStream zipInputStream, String targetFileName) throws IOException {
//
//        File tempFile = File.createTempFile("tempfile", ".tmp");
//
//        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = zipInputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//        }
//
//        File targetFile = new File(targetFileName);
//
//        if (tempFile.renameTo(targetFile)) {
//            return targetFile;
//        } else {
//            throw new IOException("Не удалось переименовать временный файл.");
//        }
        File resultFile = File.createTempFile("resultFile", ".pdf");
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            if (entry.getName().equals(targetFileName)) {
                // Найден искомый файл, создаем временный файл
                File tempFile = File.createTempFile("tempfile", ".tmp");

                // Записываем данные из ZipEntry во временный файл
                try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                }

                // Теперь у вас есть временный файл, представляющий извлеченный файл
                System.out.println("Файл извлечен во временный файл: " + tempFile.getAbsolutePath());
                resultFile = tempFile;
                break; // Выход из цикла после нахождения файла
            }
        }
        return resultFile;

    }

    public void checkPdfContainsText(PDF pdf, String expectedString) {
        Assertions.assertTrue(pdf.text.contains(expectedString));
    }
}


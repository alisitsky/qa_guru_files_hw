package com.alisitsky.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.alisitsky.utils.Utils.*;

public class ReadingFileFromZipTest {

    private static String   zipFilePath = "hw/archive.zip",
                            pdfFileName = "shell gamedesign.pdf",
                            csvFileName = "table.csv",
                            xlsxFileName = "table.xlsx",
                            pdfFileExpectedData = "Джесси Шелл – один из известнейших геймдизайнеров, который работал на\r\nWalt Disney Company",
                            csvFileExpectedData = "Bob",
                            xlsxFileExpectedData = "Bob";

    @Test
    @DisplayName("PDF файл из ZIP архива содержит текст")
    void pdfFileInZipHasText() throws Exception {
        try (InputStream inputStream = getInputStreamForFileFromZip(zipFilePath ,pdfFileName)) {
            PDF pdf = new PDF(inputStream);
            assertTrue(pdf.text.contains(pdfFileExpectedData));
        }
    }

    @Test
    @DisplayName("CSV файл из ZIP архива содержит текст")
    void csvFileInZipHasText() throws Exception {
        try (InputStream inputStream = getInputStreamForFileFromZip(zipFilePath ,csvFileName);
             CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            assertTrue(csvReader.readNext()[0].contains(csvFileExpectedData));
           }
    }

    @Test
    @DisplayName("XLSX файл из ZIP архива содержит текст")
    void xlsxFileInZipHasText() throws Exception {
        try (InputStream inputStream = getInputStreamForFileFromZip(zipFilePath ,xlsxFileName)) {
             XLS xlsx = new XLS(inputStream);
             String firstCell = xlsx.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
             assertTrue(firstCell.contains(xlsxFileExpectedData));
        }
    }
}
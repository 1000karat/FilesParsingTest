import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import model.JsonRoot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesParsingTest {
    ClassLoader cl = FilesParsingTest.class.getClassLoader();
    Gson gson = new Gson();

    @Test
    @DisplayName("Тест файла *.csv из архива")
    public void csvParseFromZipTest() throws IOException, CsvException {
        String content = ZipHelper.readFileContentFromZip("SampleCSVFile_11kb.csv");

        CSVReader csvReader = new CSVReader(new StringReader(content));
        List<String[]> contentCsv = csvReader.readAll();

        assertThat(contentCsv.get(4)[2]).contains("Carlos Soltero");
    }

    @Test
    @DisplayName("Тест файла *.xlsx из архива")
    public void xlsxParseFromZipTest() throws IOException {
        byte[] fileContent = ZipHelper.readFileContentByteFromZip("Free_Test_Data_XLSX.xlsx");
        XLS xls = new XLS(fileContent);

        assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).isEqualTo("Dett");
        assertThat(xls.excel.getSheetAt(0).getRow(6).getCell(5).getStringCellValue()).isEqualTo("France");
    }

    @Test
    @DisplayName("Тест файла *.pdf из архива")
    public void pdfParseFromZipTest() throws IOException {
        byte[] fileContent = ZipHelper.readFileContentByteFromZip("junit5.pdf");

        PDF pdf = new PDF(fileContent);

        assertThat(pdf.title).isEqualTo("JUnit 5 User Guide");
        assertThat(pdf.author).contains("Sam Brannen");
    }

    @Test
    @DisplayName("Тест файла *.json из zip архива с помощью Gson")
    public void jsonParseGsonFromZipTest() throws IOException {
        String fileContent = ZipHelper.readFileContentFromZip("jsonArrayTest.json");

        JsonRoot jr = gson.fromJson(fileContent, JsonRoot.class);

        assertThat(jr.getMembers().get(0).getName()).startsWith("Mol");
        assertThat(jr.getMembers().get(1).getAge()).isEqualTo(39);
        assertThat(jr.getMembers().get(2).getPowers()).containsSequence("Inferno", "Teleportation");
    }

    @Test
    @DisplayName("Тест файла *.json без архива с помощью Gson")
    public void jsonParseGsonTest() throws IOException {

        try (InputStream is = cl.getResourceAsStream("jsonArrayTestNoZip.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            JsonRoot jr = gson.fromJson(isr, JsonRoot.class);

            assertThat(jr.getMembers().get(0).getName()).startsWith("Mol");
            assertThat(jr.getMembers().get(1).getAge()).isEqualTo(39);
            assertThat(jr.getMembers().get(2).getPowers()).containsSequence("Inferno", "Teleportation");
        }
    }

    @Test
    @DisplayName("Тест файла *.json без архива с помощью Jakson")
    public void jsonParseJacksonTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream is = cl.getResourceAsStream("jsonArrayTestNoZip.json")) {
            JsonRoot jr = objectMapper.readValue(is, JsonRoot.class);

            assertThat(jr.getMembers().get(0).getName()).endsWith("Man");
            assertThat(jr.getMembers().get(1).getSecretIdentity()).hasToString("Jane Wilson");
            assertThat(jr.isActive()).isTrue();
        }
    }
}

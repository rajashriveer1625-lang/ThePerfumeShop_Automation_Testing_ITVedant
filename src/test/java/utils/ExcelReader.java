package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Reads test data from Excel files using Apache POI.
 */
public final class ExcelReader {

    private static final Logger LOGGER = LogManager.getLogger(ExcelReader.class);
    private static final DataFormatter FORMATTER = new DataFormatter();

    private ExcelReader() {
    }

    public static List<Map<String, String>> readSheet(String resourcePath, String sheetName) {
        List<Map<String, String>> rows = new ArrayList<>();

        try (InputStream input = ExcelReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("Excel file not found on classpath: " + resourcePath);
            }

            try (Workbook workbook = new XSSFWorkbook(input)) {
                Sheet sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    throw new IllegalStateException("Sheet not found: " + sheetName);
                }

                Row headerRow = sheet.getRow(0);
                if (headerRow == null) {
                    return rows;
                }

                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(FORMATTER.formatCellValue(cell).trim());
                }

                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null) {
                        continue;
                    }

                    Map<String, String> rowData = new LinkedHashMap<>();
                    boolean hasData = false;
                    for (int col = 0; col < headers.size(); col++) {
                        Cell cell = row.getCell(col);
                        String value = cell == null ? "" : FORMATTER.formatCellValue(cell).trim();
                        if (!value.isEmpty()) {
                            hasData = true;
                        }
                        rowData.put(headers.get(col), value);
                    }

                    if (hasData) {
                        rows.add(rowData);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read Excel file: " + resourcePath, e);
        }

        LOGGER.info("Loaded {} rows from {}/{}", rows.size(), resourcePath, sheetName);
        return rows;
    }

    public static Map<String, String> readFirstRow(String resourcePath, String sheetName) {
        List<Map<String, String>> rows = readSheet(resourcePath, sheetName);
        if (rows.isEmpty()) {
            throw new IllegalStateException("No data rows found in " + resourcePath + " sheet " + sheetName);
        }
        return rows.get(0);
    }
}

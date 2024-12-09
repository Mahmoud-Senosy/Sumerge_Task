package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {

    public static List<String[]> readExcel(String filePath, String sheetName) throws IOException {
        List<String[]> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filePath);

        // Create Workbook instance
        Workbook workbook = new XSSFWorkbook(fis);

        // Get the desired sheet
        Sheet sheet = workbook.getSheet(sheetName);

        // Iterate over rows
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int cellCount = row.getPhysicalNumberOfCells();
            String[] rowData = new String[cellCount];

            // Iterate over columns in each row
            for (int i = 0; i < cellCount; i++) {
                Cell cell = row.getCell(i);
                rowData[i] = cell.toString(); // Convert cell data to string
            }
            data.add(rowData);
        }

        workbook.close();
        fis.close();

        return data;
    }
}

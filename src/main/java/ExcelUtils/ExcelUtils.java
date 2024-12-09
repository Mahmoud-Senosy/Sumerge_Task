package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    private Workbook workbook;

    public ExcelUtils(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInputStream);
    }

    public List<String[]> getData(String sheetName) {
        List<String[]> data = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);

        for (Row row : sheet) {
            int lastCellNum = row.getLastCellNum();
            String[] rowData = new String[lastCellNum];
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = row.getCell(i);
                rowData[i] = cell != null ? cell.toString() : "";
            }
            data.add(rowData);
        }

        return data;
    }

    public void close() throws IOException {
        workbook.close();
    }
}

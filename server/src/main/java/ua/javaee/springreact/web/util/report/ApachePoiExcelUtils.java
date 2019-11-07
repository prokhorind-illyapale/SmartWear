package ua.javaee.springreact.web.util.report;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class ApachePoiExcelUtils {

    public static byte[] convertReport(XSSFWorkbook report) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            report.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static void createHeader(XSSFSheet sheet, Class objectClass) {
        Field[] allFields = objectClass.getDeclaredFields();
        Row row = createRow(sheet, 0);
        int i = 0;
        for (Field field : allFields) {
            setCell(row, i, field.getName());
            i++;
        }
    }

    public static CellStyle createDateCellStyle(XSSFWorkbook wb) {
        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("d/m/yy h:mm"));
        return cellStyle;
    }

    public static void setCell(Row row, int cellNumber, Object value) {
        Cell cell = createCell(row, cellNumber);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        }
    }

    public static XSSFWorkbook createExcelFile() {
        return new XSSFWorkbook();
    }

    public static XSSFSheet createSheet(XSSFWorkbook workbook, String name) {
        return workbook.createSheet(name);
    }

    public static Row createRow(XSSFSheet sheet, int rowNumber) {
        return sheet.createRow(rowNumber);
    }

    public static Cell createCell(Row row, int cellNumber) {
        return row.createCell(cellNumber);
    }

}

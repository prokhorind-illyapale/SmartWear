package ua.javaee.springreact.web.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.data.Indicator4GraphData;
import ua.javaee.springreact.web.service.IndicatorReportService;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import static ua.javaee.springreact.web.util.report.ApachePoiExcelUtils.*;

@Service
public class DefaultIndicatorReportService implements IndicatorReportService {

    private static final String FULL_REPORT = "full_report";


    public void createReport(XSSFWorkbook book, List<Indicator4GraphData> data, String sheetName) {
        XSSFSheet sheet = createSheet(book, sheetName);
        int rowNum = 0;
        createHeader(sheet, Indicator4GraphData.class);
        for (Indicator4GraphData indicator : data) {
            rowNum++;
            try {
                writeIndicator(book, indicator, sheet, rowNum);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeIndicator(XSSFWorkbook wb, Indicator4GraphData indicator, XSSFSheet sheet, int rowNum) throws IllegalAccessException {
        Row row = createRow(sheet, rowNum);
        Field[] allFields = Indicator4GraphData.class.getDeclaredFields();
        int cellNumber = 0;
        for (Field field : allFields) {
            processField(indicator, row, cellNumber, field, wb);
            cellNumber++;
        }
    }

    private void processField(Indicator4GraphData indicator, Row row, int cellNumber, Field field, XSSFWorkbook wb) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(indicator);
        setCell(wb, row, cellNumber, value);
    }

    private void setCell(XSSFWorkbook workbook, Row row, int cellNumber, Object value) {
        Cell cell = createCell(row, cellNumber);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
            cell.setCellStyle(createDateCellStyle(workbook));

        }
    }
}
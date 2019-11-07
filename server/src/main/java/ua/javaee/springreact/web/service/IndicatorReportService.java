package ua.javaee.springreact.web.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.javaee.springreact.web.data.Indicator4GraphData;

import java.util.List;

public interface IndicatorReportService {
    void createReport(XSSFWorkbook book, List<Indicator4GraphData> data, String sheetName);
}

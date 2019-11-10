package ua.javaee.springreact.web.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.*;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.data.Indicator4GraphData;
import ua.javaee.springreact.web.service.IndicatorReportService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static ua.javaee.springreact.web.util.report.ApachePoiExcelUtils.*;

@Service
public class DefaultIndicatorReportService implements IndicatorReportService {

    private static final String FULL_REPORT = "full_report";


    public void createReport(XSSFWorkbook book, List<Indicator4GraphData> data, String sheetName) {
        XSSFSheet sheet = createSheet(book, sheetName);
        generateEmptyChart(sheet);
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
        buildChart(sheet, rowNum);
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
        if ("value".equals(field.getName())) {
            value = new BigDecimal((String) value).doubleValue();
        }
        setCell(wb, row, cellNumber, value);
    }

    private void setCell(XSSFWorkbook workbook, Row row, int cellNumber, Object value) {
        Cell cell = createCell(row, cellNumber);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Date) {
            cell.setCellValue(value.toString());
        }
    }

    private void buildChart(XSSFSheet sheet, int rowNum) {

        XSSFChart lineChart = sheet.getDrawingPatriarch().getCharts().get(0);
        // Use a category axis for the bottom axis.
        //XDDFValueAxis bottomAxis = lineChart.createValueAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.BOTTOM);
        XDDFDateAxis bottomAxis = lineChart.createDateAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("time"); // https://stackoverflow.com/questions/32010765
        XDDFValueAxis leftAxis = buildLeftAxis(lineChart);

        XDDFNumericalDataSource<Double> xs = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, (rowNum), 0, 0));
        XDDFDataSource<String> ys1 = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, (rowNum), 3, 3));
        XDDFLineChartData data = (XDDFLineChartData) lineChart.createData(ChartTypes.LINE, bottomAxis, leftAxis);

        populateSeries(xs, ys1, data);
        lineChart.plot(data);
        solidLineSeries(data, 0, PresetColor.CHARTREUSE);
    }

    private XDDFValueAxis buildLeftAxis(XSSFChart lineChart) {
        XDDFValueAxis leftAxis = lineChart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("value");
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        return leftAxis;
    }

    private void populateSeries(XDDFNumericalDataSource<Double> xs, XDDFDataSource<String> ys1, XDDFLineChartData data) {
        XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(ys1, xs);
        series1.setTitle("2x", null);
        series1.setSmooth(false);
        series1.setMarkerStyle(MarkerStyle.STAR);
    }

    private void generateEmptyChart(XSSFSheet sheet) {
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);
        XSSFChart lineChart = drawing.createChart(anchor);
        XDDFChartLegend legend = lineChart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);
    }

    private static void solidLineSeries(XDDFChartData data, int index, PresetColor color) {
        XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
        XDDFLineProperties line = new XDDFLineProperties();
        line.setFillProperties(fill);
        XDDFChartData.Series series = data.getSeries().get(index);
        XDDFShapeProperties properties = series.getShapeProperties();
        if (properties == null) {
            properties = new XDDFShapeProperties();
        }
        properties.setLineProperties(line);
        series.setShapeProperties(properties);
    }
}
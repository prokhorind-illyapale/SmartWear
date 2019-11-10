package ua.javaee.springreact.web.controller;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.data.Indicator4GraphData;
import ua.javaee.springreact.web.facade.impl.DefaultIndicatorFacade;
import ua.javaee.springreact.web.facade.impl.DefaultUserDeviceFacade;
import ua.javaee.springreact.web.service.IndicatorReportService;
import ua.javaee.springreact.web.util.report.ApachePoiExcelUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static ua.javaee.springreact.web.util.DateUtils.parseDate;

@Controller
@RequestMapping("/report")
public class ReportController {
    private static final String APPLICATION_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Autowired
    private DefaultIndicatorFacade defaultIndicatorFacade;
    @Autowired
    private DefaultUserDeviceFacade defaultUserDeviceFacade;
    @Autowired
    private IndicatorReportService defaultIndicatorReportService;

    @GetMapping
    @ResponseBody
    public ResponseEntity getValuesBetweenDates(Principal principal, @RequestParam(value = "name") List<String> names,
                                                @RequestParam(required = false) String from,
                                                @RequestParam(required = false) String to) {
        List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceName(principal.getName(), names);
        return ResponseEntity.ok(defaultIndicatorFacade.findBetweenDates(ids, parseDate(from), parseDate(to)));
    }

    @GetMapping("/type/{type}")
    @ResponseBody
    public ResponseEntity getValuesBetweenDatesAndByValueType(Principal principal,
                                                              @PathVariable String type,
                                                              @RequestParam(required = false) String from,
                                                              @RequestParam(required = false) String to) {
        List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceType(principal.getName(), type);
        return ResponseEntity.ok(defaultIndicatorFacade.findBetweenDates(ids, parseDate(from), parseDate(to)));
    }

    @GetMapping("/excel")
    public ResponseEntity getFileBetweenDates(HttpServletResponse response, Principal principal, @RequestParam(value = "name") List<String> names,
                                              @RequestParam(required = false) String from,
                                              @RequestParam(required = false) String to) throws IOException {
        List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceName(principal.getName(), names);
        List<Indicator4GraphData> data = defaultIndicatorFacade.findBetweenDates(ids, parseDate(from), parseDate(to));
        XSSFWorkbook workbook = new XSSFWorkbook();
        defaultIndicatorReportService.createReport(workbook, data, "full");
        byte[] report = ApachePoiExcelUtils.convertReport(workbook);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + "onepage.xlsx")
                .contentLength(report.length)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(report);
    }

    @GetMapping("/type/{type}/excel")
    @ResponseBody
    public ResponseEntity getFileBetweenDatesAndByValueType(Principal principal,
                                                            @PathVariable String type,
                                                            @RequestParam(required = false) String from,
                                                            @RequestParam(required = false) String to) {
        List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceType(principal.getName(), type);
        if (ids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Indicator4GraphData> data = defaultIndicatorFacade.findBetweenDates(ids, parseDate(from), parseDate(to));
        XSSFWorkbook workbook = new XSSFWorkbook();
        defaultIndicatorReportService.createReport(workbook, data, type);
        byte[] report = ApachePoiExcelUtils.convertReport(workbook);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + "type.xlsx")
                .contentLength(report.length)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(report);
    }

    @GetMapping("/full/excel")
    @ResponseBody
    public ResponseEntity getFullReport(Principal principal,
                                        @RequestParam(required = false) String from,
                                        @RequestParam(required = false) String to) {
        List<String> types = defaultUserDeviceFacade.findDeviceTypes(principal.getName());
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (String type : types) {
            List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceType(principal.getName(), type);
            List<Indicator4GraphData> data = defaultIndicatorFacade.findBetweenDates(ids, parseDate(from), parseDate(to));
            defaultIndicatorReportService.createReport(workbook, data, type);
        }
        byte[] report = ApachePoiExcelUtils.convertReport(workbook);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + "full.xls")
                .contentLength(report.length)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(report);
    }
}

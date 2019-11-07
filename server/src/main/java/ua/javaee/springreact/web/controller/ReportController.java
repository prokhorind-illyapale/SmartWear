package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.facade.impl.DefaultIndicatorFacade;
import ua.javaee.springreact.web.facade.impl.DefaultUserDeviceFacade;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private DefaultIndicatorFacade defaultIndicatorFacade;
    @Autowired
    private DefaultUserDeviceFacade defaultUserDeviceFacade;

    @GetMapping
    public ResponseEntity getValuesBetweenDates(Principal principal, @RequestParam(value = "name") List<String> names,
                                                @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date from,
                                                @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date to) {
        List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceName(principal.getName(), names);
        return ResponseEntity.ok(defaultIndicatorFacade.findBetweenDates(ids, from, to));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity getValuesBetweenDatesAndByValueType(Principal principal,
                                                              @PathVariable String type,
                                                              @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date from,
                                                              @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date to) {
        List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceType(principal.getName(), type);
        return ResponseEntity.ok(defaultIndicatorFacade.findBetweenDates(ids, from, to));
    }
}

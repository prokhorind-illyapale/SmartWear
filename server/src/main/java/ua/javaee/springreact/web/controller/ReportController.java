package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.facade.impl.DefaultIndicatorFacade;
import ua.javaee.springreact.web.facade.impl.DefaultUserDeviceFacade;

import java.security.Principal;
import java.util.List;

import static ua.javaee.springreact.web.util.DateUtils.parseDate;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private DefaultIndicatorFacade defaultIndicatorFacade;
    @Autowired
    private DefaultUserDeviceFacade defaultUserDeviceFacade;

    @GetMapping
    public ResponseEntity getValuesBetweenDates(Principal principal, @RequestParam(value = "name") List<String> names,
                                                @RequestParam(required = false) String from,
                                                @RequestParam(required = false) String to) {
        List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceName(principal.getName(), names);
        return ResponseEntity.ok(defaultIndicatorFacade.findBetweenDates(ids, parseDate(from), parseDate(to)));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity getValuesBetweenDatesAndByValueType(Principal principal,
                                                              @PathVariable String type,
                                                              @RequestParam(required = false) String from,
                                                              @RequestParam(required = false) String to) {
        List<Long> ids = defaultUserDeviceFacade.findIdsByDeviceType(principal.getName(), type);
        return ResponseEntity.ok(defaultIndicatorFacade.findBetweenDates(ids, parseDate(from), parseDate(to)));
    }
}

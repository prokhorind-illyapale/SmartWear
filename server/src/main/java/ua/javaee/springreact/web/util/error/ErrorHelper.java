package ua.javaee.springreact.web.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by kleba on 11.02.2019.
 */
public class ErrorHelper {

    private static final String VALIDATION_TYPE_ERROR = "validationType";

    public static ResponseEntity<List<Error>> processingErrors(BindingResult bindingResult) {
        List<Error> errors = bindingResult.getAllErrors().stream()
                .map(err -> buildError((FieldError) err))
                .collect(toList());
        return new ResponseEntity<List<Error>>(errors, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<List<Error>> processingErrors(String message, String type) {
        List<Error> errors = Collections.singletonList(buildError(message, type));
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    private static Error buildError(FieldError fieldError) {
        String message = fieldError.getField() + "shouldn't be" + fieldError.getRejectedValue();
        return buildError(message, VALIDATION_TYPE_ERROR);
    }

    private static Error buildError(String message, String type) {
        Error error = new Error();
        error.setMessage(message);
        error.setType(type);
        return error;
    }
}

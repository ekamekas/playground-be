package id.maseka.playground.service.common.error;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Map;

public class ValidationErrorException extends GenericErrorException {

    public ValidationErrorException() {
        super(HttpStatus.BAD_REQUEST);
        setTitle("validation error");
        setType(URI.create("/errors/validation-error"));
    }

    public ValidationErrorException(String detail) {
        this();
        setDetail(detail);
    }

    public ValidationErrorException(String detail, String title) {
        this(detail);
        setTitle(title);
    }

    public ValidationErrorException(String detail, Map<String, String> invalidParam) {
        this(detail);
        if(null != invalidParam) {
            getBody().setProperty("parameter", invalidParam);
        }
    }

    public ValidationErrorException(String detail, String title, Map<String, String> invalidParam) {
        this(detail, title);
        if(null != invalidParam) {
            getBody().setProperty("parameter", invalidParam);
        }
    }

}

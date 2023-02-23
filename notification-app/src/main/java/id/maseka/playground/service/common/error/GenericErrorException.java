package id.maseka.playground.service.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.util.Map;

public class GenericErrorException extends ErrorResponseException {

    public GenericErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        setTitle("internal server error");
        setType(URI.create("/errors/generic-error"));
    }

    public GenericErrorException(HttpStatusCode status) {
        super(status);
        setTitle("internal server error");
        setType(URI.create("/errors/generic-error"));
    }

    public GenericErrorException(String detail) {
        this();
        setDetail(detail);
    }

    public GenericErrorException(HttpStatusCode status, String detail) {
        this(status);
        setDetail(detail);
    }

    public GenericErrorException(String detail, String title) {
        this(detail);
        setTitle(title);
    }

    public GenericErrorException(HttpStatusCode status, String detail, String title) {
        this(status, detail);
        setTitle(title);
    }

    public GenericErrorException(String detail, Map<String, Object> parameter) {
        this(detail);
        if(null != parameter) {
            parameter.forEach(getBody()::setProperty);
        }
    }

    public GenericErrorException(HttpStatusCode status, String detail, Map<String, Object> parameter) {
        this(status, detail);
        if(null != parameter) {
            parameter.forEach(getBody()::setProperty);
        }
    }

    public GenericErrorException(String detail, String title, Map<String, Object> parameter) {
        this(detail, title);
        if(null != parameter) {
            parameter.forEach(getBody()::setProperty);
        }
    }

    public GenericErrorException(HttpStatusCode status, String detail, String title, Map<String, Object> parameter) {
        this(status, detail, title);
        if(null != parameter) {
            parameter.forEach(getBody()::setProperty);
        }
    }

}

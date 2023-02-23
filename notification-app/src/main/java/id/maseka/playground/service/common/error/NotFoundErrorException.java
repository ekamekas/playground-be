package id.maseka.playground.service.common.error;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

public class NotFoundErrorException extends GenericErrorException {

    public NotFoundErrorException() {
        super(HttpStatus.NOT_FOUND);
        setTitle("resource is not found");
        setType(URI.create("/errors/not-found-error"));
    }

    public NotFoundErrorException(String detail) {
        this();
        setDetail(detail);
    }

    public NotFoundErrorException(String detail, String title) {
        this(detail);
        setTitle(title);
    }

    public NotFoundErrorException(String detail, Map<Class<?>, String> invalidEntityId) {
        this(detail);
        if(null != invalidEntityId) {
            var mappedInvalidEntityId = invalidEntityId.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().getSimpleName(), Map.Entry::getValue));
            getBody().setProperty("entity", mappedInvalidEntityId);
        }
    }

    public NotFoundErrorException(String detail, String title, Map<Class<?>, String> invalidEntityId) {
        this(detail, title);
        if(null != invalidEntityId) {
            var mappedInvalidEntityId = invalidEntityId.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().getSimpleName(), Map.Entry::getValue));
            getBody().setProperty("entity", mappedInvalidEntityId);
        }
    }

}

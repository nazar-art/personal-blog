package net.lelyak.edu.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotPresentedInDbException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static final String USER_NOT_FOUND_EXCEPTION = "Object is NOT presented in DB, with id: {0}";

    public NotPresentedInDbException() {
        super();
    }

    public NotPresentedInDbException(String description, Throwable reason) {
        super(MessageFormat.format(USER_NOT_FOUND_EXCEPTION, description, reason.getMessage()), reason);
    }

    public NotPresentedInDbException(String id) {
        super(MessageFormat.format(USER_NOT_FOUND_EXCEPTION, id));
    }

    public NotPresentedInDbException(Throwable exceptionMessage) {
        super(exceptionMessage);
    }
}

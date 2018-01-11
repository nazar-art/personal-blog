package net.lelyak.edu.utils.exception;

import net.lelyak.edu.utils.common.StringUtilities;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotPresentedInDbException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String USER_NOT_FOUND_EXCEPTION = "Object Not presented in DB, with id: [%0$s] is not found in DB.";

    public NotPresentedInDbException() {
        super();
    }

    public NotPresentedInDbException(String description, Throwable reason) {
        super(StringUtilities.appendStrings(USER_NOT_FOUND_EXCEPTION, description, reason.getMessage()), reason);
    }

    public NotPresentedInDbException(String id) {
        super(StringUtilities.appendStrings(USER_NOT_FOUND_EXCEPTION, id));
    }

    public NotPresentedInDbException(Throwable exceptionMessage) {
        super(exceptionMessage);
    }
}

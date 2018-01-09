package net.lelyak.edu.utils.exception;

import net.lelyak.edu.utils.common.StringUtilities;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents when weather station with defined id is not presented at DB.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String STATION_NOT_FOUND_EXCEPTION = "User, with id: [%0$s] is not found in DB.";

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String description, Throwable reason) {
        super(StringUtilities.appendStrings(STATION_NOT_FOUND_EXCEPTION, description, reason.getMessage()), reason);
    }

    public UserNotFoundException(String description) {
        super(StringUtilities.appendStrings(STATION_NOT_FOUND_EXCEPTION, description));
    }

    public UserNotFoundException(Throwable exceptionMessage) {
        super(exceptionMessage);
    }
}

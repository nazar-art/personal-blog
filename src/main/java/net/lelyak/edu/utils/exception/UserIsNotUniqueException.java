package net.lelyak.edu.utils.exception;


import net.lelyak.edu.utils.common.StringUtilities;

/**
 * Exception class which defines when station id is not unique.
 * Also, that it is already stored in DB.
 */
public class UserIsNotUniqueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String CONTROL_IS_NOT_UNIQUE_EXCEPTION = "User with the same id: [%0$s] is already defined in DB";

    public UserIsNotUniqueException(String id) {
        super(StringUtilities.appendStrings(CONTROL_IS_NOT_UNIQUE_EXCEPTION, id));
    }
}

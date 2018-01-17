package net.lelyak.edu.utils.exception;

import java.text.MessageFormat;

/**
 * @author Nazar Lelyak.
 */
public class DuplicateEmailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String DUPLICATE_EMAIL_EXCEPTION = "This email: {0} is already stored in DB.";

    public DuplicateEmailException(String email) {
        super(MessageFormat.format(DUPLICATE_EMAIL_EXCEPTION, email));
    }
}

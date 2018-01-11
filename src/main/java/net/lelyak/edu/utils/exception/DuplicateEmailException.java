package net.lelyak.edu.utils.exception;

import net.lelyak.edu.utils.common.StringUtilities;

/**
 * @author Nazar Lelyak.
 */
public class DuplicateEmailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String DUPLICATE_EMAIL_EXCEPTION = "This email: [%0$s] is already stored in DB.";

    public DuplicateEmailException(String email) {
        super(StringUtilities.appendStrings(DUPLICATE_EMAIL_EXCEPTION, email));
    }
}

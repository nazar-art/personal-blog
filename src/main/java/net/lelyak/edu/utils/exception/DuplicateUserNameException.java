package net.lelyak.edu.utils.exception;


import java.text.MessageFormat;

public class DuplicateUserNameException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static final String USER_NAME_IS_NOT_UNIQUE = "User with the same user_name: {0} is already defined in DB";

    public DuplicateUserNameException(String userName) {
        super(MessageFormat.format(USER_NAME_IS_NOT_UNIQUE, userName));
    }
}

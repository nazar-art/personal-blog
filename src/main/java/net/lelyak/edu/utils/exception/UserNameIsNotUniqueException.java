package net.lelyak.edu.utils.exception;


import net.lelyak.edu.utils.common.StringUtilities;


public class UserNameIsNotUniqueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String USER_NAME_IS_NOT_UNIQUE = "User with the same user_name: [%0$s] is already defined in DB";

    public UserNameIsNotUniqueException(String userName) {
        super(StringUtilities.appendStrings(USER_NAME_IS_NOT_UNIQUE, userName));
    }
}

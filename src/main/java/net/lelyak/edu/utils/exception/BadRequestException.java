package net.lelyak.edu.utils.exception;

import net.lelyak.edu.utils.common.StringUtilities;

import java.text.MessageFormat;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String BAD_REQUEST_DATA = "Request data is not correct: [%0$s]";

    public BadRequestException(String name) {
        super(MessageFormat.format(BAD_REQUEST_DATA, name));
//        super(StringUtilities.appendStrings(BAD_REQUEST_DATA, name));
    }
}

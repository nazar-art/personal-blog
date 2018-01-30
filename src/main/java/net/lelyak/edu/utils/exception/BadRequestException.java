package net.lelyak.edu.utils.exception;

import java.text.MessageFormat;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String BAD_REQUEST_DATA = "Request data is not correct! Dear, {0} you made some mistake";

    public BadRequestException(String name) {
        super(MessageFormat.format(BAD_REQUEST_DATA, name));
    }

}

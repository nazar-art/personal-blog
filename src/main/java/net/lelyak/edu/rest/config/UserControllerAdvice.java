package net.lelyak.edu.rest.config;

import net.lelyak.edu.utils.exception.DuplicateEmailException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import net.lelyak.edu.utils.exception.DuplicateUserNameException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {

    @ResponseBody
    @ExceptionHandler(NotPresentedInDbException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors notFoundExceptionHandler(NotPresentedInDbException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors duplicateEmailExceptionHandler(DuplicateEmailException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(DuplicateUserNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors duplicateUserNameExceptionHandler(DuplicateUserNameException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}

package net.lelyak.edu.rest.config;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.utils.exception.DuplicateEmailException;
import net.lelyak.edu.utils.exception.DuplicateUserNameException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
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

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error/500";
    }

}

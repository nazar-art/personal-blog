package net.lelyak.edu.rest.config;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.utils.exception.BadRequestException;
import net.lelyak.edu.utils.exception.DuplicateEmailException;
import net.lelyak.edu.utils.exception.DuplicateUserNameException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class UserControllerAdvice {

    @ResponseBody
    @SuppressWarnings("unchecked")
    @ExceptionHandler(NotPresentedInDbException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity notFoundExceptionHandler(NotPresentedInDbException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity duplicateEmailExceptionHandler(DuplicateEmailException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @ExceptionHandler(DuplicateUserNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity duplicateUserNameExceptionHandler(DuplicateUserNameException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity badRequestExceptionHandler(BadRequestException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error/403";
    }

}

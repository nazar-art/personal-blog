package net.lelyak.edu.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.rest.service.impl.UserServiceImpl;
import net.lelyak.edu.utils.exception.DuplicateEmailException;
import net.lelyak.edu.utils.exception.DuplicateUserNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
@AllArgsConstructor
public class AuthController {

    private final UserServiceImpl userServiceImpl;


    @GetMapping("/registration")
    public String openRegisterPage(Model model) {
        model.addAttribute("newUser", new BlogUser());
        return "/registration";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/")
    public String mainUrl() {
        return "redirect:/posts";
    }


    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid BlogUser user, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        log.info("User details from UI form: {}", user);

        try {
            userServiceImpl.createUser(user);

        } catch (DuplicateUserNameException e) {
            log.error(e.getMessage());
            modelAndView.addObject("nameError", "User name is already registered.");
            e.getCause();

        } catch (DuplicateEmailException e) {
            log.error(e.getMessage());
            modelAndView.addObject("emailError", "Email is already registered.");
        }

        if (modelAndView.isEmpty()) {
            modelAndView.addObject("successMessage", "User has been registered successfully. Go to Login page.");
            modelAndView.setViewName("registration");
        }

        return modelAndView;
    }


    /**
     * Handling errors.
     */
    @GetMapping("/error")
    public String error(Throwable ex) {
        log.error("Error happen: {}", ex.getMessage());
        return "/error";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}

package net.lelyak.edu.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.rest.service.impl.UserServiceImpl;
import net.lelyak.edu.utils.exception.DuplicateUserNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid BlogUser user, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        log.info("User details from UI form: {}", user);

        userServiceImpl.createUser(user);
        modelAndView.addObject("successMessage", "User has been registered successfully. Go to Login page.");
        modelAndView.setViewName("registration");

        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}

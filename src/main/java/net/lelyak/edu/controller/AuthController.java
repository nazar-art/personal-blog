package net.lelyak.edu.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Role;
import net.lelyak.edu.rest.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String createNewUser(@ModelAttribute("newUser") BlogUser user, BindingResult bindingResult) {
//    public String createNewUser(@Valid BlogUser user, BindingResult bindingResult) {
        log.info("User details from UI form: {}", user);
        userServiceImpl.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @GetMapping("/500")
    public String error500() {
        return "/error/500";
    }

    @GetMapping("/error")
    public String error() {
        return "/error/500";
    }

}

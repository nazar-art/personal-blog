package net.lelyak.edu.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.rest.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/registration")
    public String openRegisterPage(Model model) {
        model.addAttribute("newUser", new BlogUser());
        return "/registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@ModelAttribute("newUser") BlogUser user) {
        log.info("User details from UI form: {}", user);
        userService.createUser(user);
        // todo you need to set user role for new user here
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
}

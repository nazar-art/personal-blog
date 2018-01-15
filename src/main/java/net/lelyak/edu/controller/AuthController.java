package net.lelyak.edu.controller;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.rest.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        userService.createUser(user);
        return "redirect:/home";
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

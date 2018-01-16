package net.lelyak.edu.controller;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.impl.PostServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomePageController {

    private final PostServiceImpl postServiceImpl;

    @GetMapping("/")
    public String mainUrl() {
//        ModelAndView homeModel = new ModelAndView("home");
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String userHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Post> postsByUserName = postServiceImpl.findAllPostsByUserName(currentPrincipalName);
        model.addAttribute("posts", postsByUserName);

        return "/home";
    }
}

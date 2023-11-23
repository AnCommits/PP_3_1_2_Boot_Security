package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class TemporalControllers {

    private final UserService userService;

    public TemporalControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(ModelMap model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Role> roles = new ArrayList<>(user.getRoles());
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "test";
    }

    @PostMapping("/test2")
    public String test2(@ModelAttribute("user") User user,
                        @ModelAttribute("USER") String u0,
                        @ModelAttribute("SUPER_USER") String u1,
                        @ModelAttribute("ADMIN") String u2,
                        @ModelAttribute("SUPER_ADMIN") String u3) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(u0));

        System.out.println("---------------------------------------------------- test2");
        return "redirect:/test";
    }
}

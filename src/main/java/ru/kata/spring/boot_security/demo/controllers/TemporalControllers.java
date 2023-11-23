package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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
//        model.addAttribute("roles", roles);
        return "test";
    }

    @PostMapping("/test2")
    public String test2(@ModelAttribute("user") User user,
                        @ModelAttribute("USER") String role1,
                        @ModelAttribute("SUPER_USER") String role2,
                        @ModelAttribute("ADMIN") String role3,
                        @ModelAttribute("SUPER_ADMIN") String role4) {
        LinkedHashSet<Role> roles = new LinkedHashSet<>();
        if (!role1.equals("")) {
            roles.add(new Role(role1));
        }
        if (!role2.equals("")) {
            roles.add(new Role(role2));
        }
        if (!role3.equals("")) {
            roles.add(new Role(role3));
        }
        if (!role4.equals("")) {
            roles.add(new Role(role4));
        }
        user.getRoles().clear();
        user.setRoles(roles);
        System.out.println("---------------------------------------------------- test2");
        return "redirect:/test";
    }
}

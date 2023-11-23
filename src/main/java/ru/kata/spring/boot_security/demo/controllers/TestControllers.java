package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TestControllers {

    private final UserService userService;

    public TestControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(ModelMap model, Authentication authentication) {
//        User u = (User) authentication.getPrincipal();
        Set<Role> r = Role.getSetOfRoles(4);
//        Set<Role> roles1 = u.getRoles();
        User user = new User(null, null,
                "1", "123",
                null, r, false);
        List<Role> roles = new ArrayList<>(r);
//        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "test";
    }

    @PostMapping("/test2")
    public String test2(@ModelAttribute("user") User user,
                        @ModelAttribute("USER") String role1,
                        @ModelAttribute("SUPER_USER") String role2,
                        @ModelAttribute("ADMIN") String role3,
                        @ModelAttribute("SUPER_ADMIN") String role4) {
        Set<Role> roles = new HashSet<>();
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
        user.setRoles(roles);
        System.out.println("---------------------------------------------------- test2");
        return "redirect:/test";
    }
}

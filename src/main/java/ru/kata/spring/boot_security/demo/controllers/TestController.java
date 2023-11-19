package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class TestController {
    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("test")
    public String test(ModelMap model) {
        User u1 = userService.getUserById(1L);
        String e1 = u1.getEmail();
        User u2 = userService.getUserByEmail("qwerty");
        System.out.println(u2);
        model.addAttribute("test", u2.getEmail());
        return "test";
    }
}

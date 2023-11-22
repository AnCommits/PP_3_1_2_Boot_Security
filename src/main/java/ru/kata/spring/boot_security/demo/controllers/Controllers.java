package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;

import java.security.Principal;

@Controller
public class Controllers {

    @GetMapping("/test")
    public String test(Principal principal, Authentication authentication) {
        if (principal != null) {
            String n = principal.getName();
            System.out.println("test page ---------- username: " + n);
        } else {
            System.out.println("test page ---------- no username");
        }
        return "test";
    }
}

package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class Controllers {

//    @GetMapping("/logout")
//    public String logout() {
//        return "logout";
//    }

    @GetMapping("/test")
    public String test(Principal principal) {
        return "test";
    }
}

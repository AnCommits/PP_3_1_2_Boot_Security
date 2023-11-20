package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/guest")
public class GuestController {

    // Как передать объект между контроллерами? @ModelAttribute не работает.

    // Эти поля для передачи между контроллерами
    private User user;
    private boolean emailError;

    private final UserService userService;

    public GuestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("register")
    public String showAddUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Регистрация пользователя");
        model.addAttribute("title2", "Новый пользователь");
        return "user-edit";
    }

    @GetMapping("/show-repeat-add-user")
    public String showRepeatAddUser(ModelMap model) {
        model.addAttribute("user", this.user);
        model.addAttribute("title", "Регистрация пользователя");
        model.addAttribute("title2", "Новый пользователь");
        model.addAttribute("email_err", emailError);
        return "user-edit";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user, ModelMap model) {
        String email = user.getEmail();
        User userFromDb = userService.getUserByEmail(email);
        emailError = userFromDb != null;
        this.user = user;
        if (emailError) {
            return "redirect:/guest/show-repeat-add-user";
        } else {
            user.setRoles(Role.getSetOfRoles(1));
            userService.saveUser(user);
        }
        return "redirect:/user";
    }
}

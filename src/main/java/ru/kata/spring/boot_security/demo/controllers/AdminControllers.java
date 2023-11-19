package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminControllers {
    private final UserService userService;

    public AdminControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("userSave", new User());
        return "admin";
    }

    @GetMapping("show-edit-user")
    public String showEditUser(@RequestParam long id,
                               @RequestParam(required = false) boolean emailErr,
                               @RequestParam(required = false) boolean passwordsDiff,
                               @RequestParam(required = false) String email,
                               ModelMap model) {
        User user = userService.getUserById(id);
        model.addAttribute("title", "Редактирование пользователя");
        model.addAttribute("user", user);
        model.addAttribute("email_err", emailErr);
        if (emailErr) {
            user.setEmail(email);
        }
        model.addAttribute("passwords_diff", passwordsDiff);
        return "user-edit-user";
    }

    @GetMapping("show-add-user")
    public String showAddUser(@RequestParam(required = false) boolean emailErr,
                              @RequestParam(required = false) boolean passwordsDiff,
                              @RequestParam(required = false) String email,
                              ModelMap model) {
        model.addAttribute("title", "Новый пользователь");
        User user = new User();
        if (emailErr) {
            user.setEmail(email);
        }
        model.addAttribute("user", user);
        model.addAttribute("email_err", emailErr);
        model.addAttribute("passwords_diff", passwordsDiff);
        return "user-edit-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
        return "forward:/user/save-user";
    }

    @PostMapping("change-ban/{id}")
    public String changeUserBan(@PathVariable long id) {
        User user = userService.getUserById(id);
        user.setBlocked(!user.isBlocked());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("remove-user/{id}")
    public String removeUser(@PathVariable long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}

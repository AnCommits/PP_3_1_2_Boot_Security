package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminControllers {
    private User userToRepeatEdit;
    private Set<Role> roles;
    private boolean emailError;

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

    @GetMapping("/show-edit-user")
    public String showEditUser(@RequestParam long id, ModelMap model) {
        User user = userService.getUserById(id);
        roles = user.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Редактирование пользователя");
        return "user-edit";
    }

    @GetMapping("/show-repeat-edit-user")
    public String showRepeatEditUser(ModelMap model) {
        model.addAttribute("user", userToRepeatEdit);
        model.addAttribute("email_err", emailError);
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Редактирование пользователя");
        return "user-edit";
    }

    @PutMapping("/save-user")
    public String updateUser(@ModelAttribute("user") User user) {
        long idFromForm = user.getId();
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        emailError = userFromDb != null && idFromForm != userFromDb.getId();
        if (emailError) {
            userToRepeatEdit = user;
            return "redirect:/admin/show-repeat-edit-user";
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/show-add-user")
    public String showAddUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Новый пользователь");
        return "user-edit";
    }

    @GetMapping("/show-repeat-add-user")
    public String showRepeatAddUser(ModelMap model) {
        model.addAttribute("user", userToRepeatEdit);
        model.addAttribute("email_err", emailError);
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Новый пользователь");
        return "user-edit";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        emailError = userFromDb != null;
        if (emailError) {
            userToRepeatEdit = user;
            return "redirect:/admin/show-repeat-add-user";
        }
        user.setRoles(Role.getSetOfRoles(1));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    // Что возвращать из метода, чтобы страница не перезагружалась?
    @PutMapping("/change-ban/{id}")
    public String changeUserBan(@PathVariable long id) {
        User user = userService.getUserById(id);
        user.setLocked(!user.isLocked());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/remove-user/{id}")
    public String removeUser(@PathVariable long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}

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

    // Как передать объект между контроллерами? @ModelAttribute не работает.

    // Эти поля для передачи между контроллерами
    private User user;
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
        model.addAttribute("user", this.user);
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Редактирование пользователя");
        model.addAttribute("email_err", emailError);
        return "user-edit";
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
        model.addAttribute("user", this.user);
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Новый пользователь");
        model.addAttribute("email_err", emailError);
        return "user-edit";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user, ModelMap model) {
        long id = user.getId();
        String email = user.getEmail();
        User userFromDb = userService.getUserByEmail(email);
        emailError = userFromDb != null && id != userFromDb.getId();
        this.user = user;
        if (id == 0) {      // new user
            if (emailError) {
                return "redirect:/admin/show-repeat-add-user";
            } else {
                user.setRoles(roles);
                userService.saveUser(user);
            }
        } else {            // edit user
            if (emailError) {
                return "redirect:/admin/show-repeat-edit-user";
            } else {
                user.setRoles(roles);
                userService.updateUser(user);
            }
        }
        return "redirect:/admin";
    }

    // Что возвращать из метода, если нужно остаться на странице без перезагрузки?
    @PostMapping("/change-ban/{id}")
    public String changeUserBan(@PathVariable long id) {
        User user = userService.getUserById(id);
        user.setBlocked(!user.isBlocked());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/remove-user/{id}")
    public String removeUser(@PathVariable long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}

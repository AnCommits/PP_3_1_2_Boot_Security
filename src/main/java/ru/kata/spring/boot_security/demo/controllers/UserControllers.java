package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserControllers {
    private User userToRepeatEdit;
    private boolean emailError;

    private final UserService userService;

    public UserControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(ModelMap model, Principal principal) {
//        String n = principal.getName();
        User user = userService.getUserById(2L);
        model.addAttribute("title", "Моя страница");
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/show-edit-user")
    public String showEditUser(@RequestParam long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("title", "Моя страница");
        model.addAttribute("title2", "Редактирование моих данных");
        return "user-edit";
    }

    @GetMapping("/show-repeat-edit-user")
    public String showRepeatEditUser(ModelMap model) {
        model.addAttribute("user", userToRepeatEdit);
        model.addAttribute("email_err", emailError);
        model.addAttribute("title", "Моя страница");
        model.addAttribute("title2", "Редактирование моих данных");
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
            return "redirect:/user/show-repeat-edit-user";
        }
        user.setRoles(Role.getSetOfRoles(1));
        userService.updateUser(user);
        return "redirect:/user";
    }

    @DeleteMapping("/remove-user/{id}")
    public String removeUser(@PathVariable long id) {
        userService.removeUserById(id);
        return "redirect:/";
    }
}

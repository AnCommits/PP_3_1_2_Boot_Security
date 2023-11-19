package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
@RequestMapping("user")
public class UserControllers {
    private final UserService userService;

    public UserControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(ModelMap model) {
        User user = userService.getUserById(2L);
        model.addAttribute("title", "Моя страница");
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("show-edit-user")
    public String showEditUser(@RequestParam long id,
                               @RequestParam(required = false) boolean emailErr,
                               @RequestParam(required = false) boolean passwordsDiff,
                               @RequestParam(required = false) String email,
                               ModelMap model) {
        User user = userService.getUserById(id);
        model.addAttribute("title", "Редактирование моих данных");
        model.addAttribute("user", user);
        model.addAttribute("email_err", emailErr);
        if (emailErr) {
            user.setEmail(email);
        }
        model.addAttribute("passwords_diff", passwordsDiff);
        return "user-edit-user";
    }

    @PostMapping("save-user")
    public String saveUser(@ModelAttribute("user") User user, HttpServletRequest request) throws MalformedURLException {
        String urlPart = new URL(request.getHeader("referer")).getPath().split("/")[1];
        long id = user.getId();
        String email = user.getEmail();
        User u = userService.getUserByEmail(email);
        boolean emailErr = u != null && id != u.getId();
        boolean passwordsDiff = !user.getPassword().equals(user.getPasswordConf());
        if (id == 0) {      // new user
            if (emailErr || passwordsDiff) {
                return String.format("redirect:/%s/show-add-user?emailErr=%s&passwordsDiff=%s&email=%s",
                        urlPart, emailErr, passwordsDiff, email);
            } else {
                user.setRoles(Role.getSetOfRoles(1));
                userService.saveUser(user);
            }
        } else {            // edit user
            if (emailErr || passwordsDiff) {
                return String.format("redirect:/%s/show-edit-user?id=%d&emailErr=%s&passwordsDiff=%s&email=%s",
                        urlPart, id, emailErr, passwordsDiff, email);
            }   else {
                user.setRoles(user.getRoles());
                userService.updateUser(user);
            }

        }
        return "redirect:/" + urlPart;
    }
}

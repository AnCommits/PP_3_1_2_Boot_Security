package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

@Component
public class InitDataBase {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitDataBase(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method creates and puts in the table an admin in case there are no entries in the table.
     */
    @PostConstruct
    public void initSuperAdmin() {
        if (userService.countUsers() == 0) {
            Set<Role> adminRoles = Role.getSetOfRoles(2);
            User superAdmin = new User(null, null,
                    "1", passwordEncoder.encode("1"),
                    null, adminRoles, false);
            userService.saveUser(superAdmin);
            putInitUsers();
        }
    }

    /**
     * Method puts a few users in the table for testing the application.
     */
    public void putInitUsers() {
        Set<Role> userRoleSet1 = Role.getSetOfRoles(1);
        User user1 = new User("Альберт", "Эйнштейн",
                "2",  passwordEncoder.encode("2"),
                new GregorianCalendar(1879, Calendar.MARCH, 14), userRoleSet1, false);
        userService.saveUser(user1);

        Set<Role> userRoleSet2 = Role.getSetOfRoles(1);
        User user2 = new User("Мария", "Кюри",
                "3",  passwordEncoder.encode("3"),
                new GregorianCalendar(1867, Calendar.NOVEMBER, 7), userRoleSet2, true);
        userService.saveUser(user2);
    }
}

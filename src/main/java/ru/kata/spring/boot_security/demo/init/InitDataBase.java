package ru.kata.spring.boot_security.demo.init;

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

    public InitDataBase(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method creates an admin in case there are no entries in the table.
     */
    @PostConstruct
    public void initSuperAdmin() {
        if (userService.countUsers() == 0) {
            Set<Role> adminRoles = Role.getSetOfRoles(2);
            User superAdmin = new User(null, null, "superadmin@kata.ru", "123",
                    null, adminRoles, false);
            userService.saveUser(superAdmin);
        }

//        initTestUsers();
    }

    /**
     * Method puts a few users in the table for testing the application.
     */
    public void initTestUsers() {
        Set<Role> userRoleSet1 = Role.getSetOfRoles(1);
        Set<Role> userRoleSet2 = Role.getSetOfRoles(1);

        User user1 = new User("Альберт", "Эйнштейн", "einstein@gmail.com", "123",
                new GregorianCalendar(1879, Calendar.MARCH, 14), userRoleSet1, false);
        User user2 = new User("Мария", "Кюри", "curie@gmail.com", "123",
                new GregorianCalendar(1867, Calendar.NOVEMBER, 7), userRoleSet2, true);

        userService.saveUser(user1);
        userService.saveUser(user2);
    }

}

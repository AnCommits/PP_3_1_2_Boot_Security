package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

//    void saveUsers(List<User> users);

    User getUserById(Long id);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    void updateUser(User user);

    void removeUserById(Long id);

//    void removeAllUsers();

    long countUsers();
}

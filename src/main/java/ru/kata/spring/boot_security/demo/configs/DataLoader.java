package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    private final UserService userService;

    @Autowired
    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userService.getUsers().isEmpty()) {
            Set<Role> set1 = new HashSet<>();
            set1.add(new Role("ROLE_ADMIN"));
            userService.addUser(new User("Иван", "Иванов", "admin", "admin", set1));

            Set<Role> set2 = new HashSet<>();
            set2.add(new Role("ROLE_USER"));
            userService.addUser(new User("Петр", "Петров", "user", "user", set2));
        }
    }
}

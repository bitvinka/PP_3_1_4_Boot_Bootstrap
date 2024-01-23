package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUser(Model model, Principal principal) {
        Optional<User> user = userService.findByUserName(principal.getName());
        if (user.isEmpty()) {
            return "login";
        }
        model.addAttribute("user", user.get());
        model.addAttribute("principal_name", principal.getName());
        //здесь значение не может быть пустым
        model.addAttribute("principal_roles", userService.findByUserName(principal.getName()).get().getRoles());
        return "user";
    }


}

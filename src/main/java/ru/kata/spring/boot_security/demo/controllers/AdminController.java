package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
       private final PasswordEncoder passwordEncoder;
    private static final String REDIRECT_ADMIN = "redirect:/admin";

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String adminMain (Model model, Principal principal) {
        model.addAttribute("principal_name", principal.getName());
        model.addAttribute("principal_roles", userService.findByUserName(principal.getName()).get().getRoles());
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("new_user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/newUser")
    public String addUser(@ModelAttribute("new_user") User user,  @RequestParam("roles")  Set<Role> checked, Model model ) {
        model.addAttribute("roles", roleService.getRoles());
        Set<Role> set = checked.stream()
                .map(Role::getName)
                .flatMap(name -> roleService.getRoleByName(name).stream())
                .collect(Collectors.toSet());
        user.setRoles(set);
        userService.addUser(user);
        return REDIRECT_ADMIN;
    }

    @PutMapping("/edit")
    public String editUserForm(@ModelAttribute("userEdit") User user, @RequestParam("roles") Set<Role> checked, @RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("roles", roleService.getRoles());
        Optional<User> optUser = userService.getUserById(user.getId());

        if (optUser.isPresent() && (!user.getPassword().equals(optUser.get().getPassword()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        Set<Role> set = checked.stream()
                .map(Role::getName)
                .flatMap(name -> roleService.getRoleByName(name).stream())
                .collect(Collectors.toSet());
        user.setRoles(set);
        userService.updateUser(user);
        return REDIRECT_ADMIN;
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.removeUser(id);
        return REDIRECT_ADMIN;
    }
}

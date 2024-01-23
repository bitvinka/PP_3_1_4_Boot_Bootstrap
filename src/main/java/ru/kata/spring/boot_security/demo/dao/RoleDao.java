package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    void addRole(Role role);
    Optional<Role> getRoleByName(String name);
    List<Role> getRoles();
    Optional<Role> getRoleById(Long id);
}

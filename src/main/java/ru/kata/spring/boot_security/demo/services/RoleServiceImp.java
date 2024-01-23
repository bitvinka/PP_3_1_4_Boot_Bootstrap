package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }


    @Override
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }
}

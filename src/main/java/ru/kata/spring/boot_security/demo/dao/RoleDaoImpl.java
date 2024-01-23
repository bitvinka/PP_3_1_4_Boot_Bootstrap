package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager em;
    @Override
    public void addRole(Role role) {
        em.persist(role);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return em.createQuery("select u from Role u where u.name =:name", Role.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
    }
    @Override
    public Optional<Role> getRoleById(Long id) {
        return em.createQuery("select u from Role u where u.id =:id", Role.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Role> getRoles() {
        return em.createQuery("from Role", Role.class).getResultList();
    }
}

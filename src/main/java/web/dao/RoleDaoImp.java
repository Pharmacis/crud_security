package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRole(Role role) {
        entityManager.persist (role);
    }

    @Override
    public Role getRoleByName(String name) {
        return (Role) entityManager.createQuery ("from Role where role =:name")
                .setParameter ("name", name)
                .getSingleResult ();
    }

    @Override
    public Role update(Role role) {
        return entityManager.merge (role);
    }

    @Override
    public Long countRoles(String name) {
        return (Long) entityManager.createQuery ("SELECT count (*) from Role where role=:name")
                .setParameter ("name", name)
                .getSingleResult ();
    }

    @Override
    public List<String> getRoles() {
        return entityManager.createQuery ("select role from Role ")
                .getResultList ();
    }

    public List<Role> listRolesByUser(Long userId) {
        List<Role> roles = new ArrayList<> ();
        roles = entityManager.createQuery ("select u.roles from User u where u.id = :userId")
                .setParameter ("userId", userId)
                .getResultList ();
        return roles;

    }
}

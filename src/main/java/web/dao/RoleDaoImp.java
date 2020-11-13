package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void addRole(Role role) {
        entityManager.persist (role);
       // entityManager.refresh (role);
    }

    @Transactional
    @Override
    public Role getRoleByName(String name) {
        return (Role) entityManager.createQuery("from Role where role =:name")
                .setParameter("name", name)
                .getSingleResult();
    }

    @Transactional
    @Override
    public Role update(Role role) {
        return entityManager.merge(role);
    }

    @Override
    public Long countRoles(String name) {
        return (Long) entityManager.createQuery ("SELECT count (*) from Role where role=:name")
                .setParameter ("name",name)
                .getSingleResult ();
    }

    @Transactional
    @Override
    public List<String> getRoles() {
        return (List <String>) entityManager.createQuery ("select role from Role ")
                .getResultList ();

    }


}

package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    
    @Override
    public void addRole(Role role) {
        entityManager.persist (role);
    }

    
    @Override
    public Role getRoleByName(String name) {
        return (Role) entityManager.createQuery("from Role where role =:name")
                .setParameter("role", name)
                .getSingleResult();
    }

    
    @Override
    public Role update(Role role) {
        return entityManager.merge(role);
    }
    
    @Override
    public Long countRoles(String name) {
        return (Long) entityManager.createQuery("select count(*) from Role where name = :name")
                .setParameter("role", name)
                .getSingleResult();
    }


}

package web.dao;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class UserDaoImp implements UserDao {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public User getUserWithRoles(String login) {
      return (User) entityManager.createQuery ("select distinct u from User u   join fetch u.roles where u.login = :login ")
              .setParameter ("login", login)
              .getSingleResult ();
   }

   @Override
   public void add(User user) {
      entityManager.persist (user);
   }

   @Override
   public List<User> listUsersWithRoles() {
      return entityManager.createQuery ("select distinct u from User u   join fetch u.roles ").getResultList ();
   }

   @Override
   public void deleteById(Long id) {
      entityManager.remove (getUserById (id));
   }

   @Override
   public User update(User user) {
      return entityManager.merge (user);
   }

   @Override
   public User getUserById(Long id) {
      return entityManager.find (User.class, id);
   }
}

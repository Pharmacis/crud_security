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
   public User getUserByLogin(String login) {
      User user = new User ();
      user = (User) entityManager.createQuery ("from User where login =:login")
              .setParameter ("login", login)
              .getSingleResult ();

      return user;
   }

   @Override
   public void add(User user) {
      entityManager.persist (user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      return entityManager.createQuery (" from User ", User.class).getResultList ();
   }

   @Override
   public List<User> listUsersWithRoles() {
      return entityManager.createQuery ("select distinct u from User u   join fetch u.roles ").getResultList ();
   }

   @Override
   public Set<User> setUsers() {
      return null;
   }


   @Override
   public void delete(User user) {
      entityManager.remove (user);
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
      return (User) entityManager.createQuery ("from User where id =:longID")
              .setParameter ("longID", id)
              .getSingleResult ();
   }

   @Override
   public boolean tableIsEmpty() {
      return entityManager.createQuery ("select 1 from user")
              .getResultList ()
              .isEmpty ();
   }

   @Override
   public User getUserByName(String name) {
      return (User) entityManager.createQuery ("from User where userName =:name")
              .setParameter ("name", name)
              .getSingleResult ();
   }

}

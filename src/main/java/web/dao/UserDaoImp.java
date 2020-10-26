package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.*;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public User getUserByLogin(String login) {
     return (User) entityManager.createQuery("from User where login =:login")
              .setParameter("login", login)
              .getSingleResult();
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
   public void delete(User user) {
     entityManager.remove (entityManager.createQuery(" from User where id =:id")
              .setParameter("id", user.getId ())
              .getSingleResult());

   }
    
   @Override
   public void deleteById(Long id) {
      entityManager.remove(entityManager.createQuery("from User where id =:id")
              .setParameter("id", id)
              .getSingleResult());
   }
    
   @Override
   public User update(User user) {
      return entityManager.merge(user);
   }
   
   @Override
   public User getUserById(Long id) {
      return (User) entityManager.createQuery("from User where id =:longID")
              .setParameter("longID", id)
              .getSingleResult();
   }
   
   @Override
   public boolean tableIsEmpty() {
      try {
         entityManager.createQuery ("select 1 from user")
                 .getResultList ()
                 .isEmpty ();
      }catch (java.lang.NullPointerException e){
         return true;
      }
        return false;
   }
   
   @Override
   public User getUserByName(String name) {
      return (User) entityManager.createQuery("from User where userName =:name")
              .setParameter("name", name)
              .getSingleResult();
   }
}
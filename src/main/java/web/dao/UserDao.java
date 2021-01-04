package web.dao;

import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

   public User getUserByLogin(String login);

   public void add(User user);

   public List<User> listUsers();

   List<User> listUsersWithRoles();

   public Set<User> setUsers();

   public void delete(User user);

   public void deleteById(Long id);

   public User update(User user);

   public User getUserById(Long id);

   public boolean tableIsEmpty();

   public User getUserByName(String name);
}




package web.dao;

import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
   public User getUserWithRoles(String login);
   public void add(User user);
   List<User> listUsersWithRoles();
   public void deleteById(Long id);
   public User update(User user);
   public User getUserById(Long id);
   public void setRoleByListNameRole(User user,List <String>role);
}




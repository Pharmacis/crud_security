package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;

public interface UserService  {

     void addUserAndHisRoles(User user,List<String> rolesValues);

     List<User> listUsers();

     void delete(User user);

     void deleteById(Long id);

     User updateUserAndHisRoles(User user,List<String> rolesValues);

     boolean tableIsEmpty();

     User getUserById(Long id);

     void addListOfRolesForUser(User user, List<String> rolesValues);

     List <String> getRoles();

     List<User> listUsersWithRoles();

     List<User> listUsersWithRoles2();

}

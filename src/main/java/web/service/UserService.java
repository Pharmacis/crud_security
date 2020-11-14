package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;

public interface UserService  {

     void add(User user,List<String> rolesValues);

     List<User> listUsers();

     void delete(User user);

     void deleteById(Long id);

     User update(User user,List<String> rolesValues);
     boolean tableIsEmpty();

     User getUserById(Long id);

     void addListOfRolesForUser(User user, List<String> rolesValues);

     List <String> getRoles();

}

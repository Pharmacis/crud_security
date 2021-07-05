package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface RoleDao {
    void addRole(Role role);
    Role getRoleByName(String name);
    Role update(Role role);
    List<String> getRoles();
    void setRoleByListNameRole(User user, List <String>role);




}

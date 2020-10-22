package web.dao;

import web.model.Role;

public interface RoleDao {
    void addRole(Role role);

   public Role getRoleByName(String name);
   public Role update(Role role);
    Long countRoles(String name);

}

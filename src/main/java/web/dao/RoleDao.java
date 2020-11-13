package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao {
    public void addRole(Role role);
   public Role getRoleByName(String name);
   public Role update(Role role);
   public Long countRoles(String name);
   public List<String> getRoles();



}

package web.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service()
@Transactional
public class UserServiceImp implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Transactional
    @Override
    public void addUserAndHisRoles (User user,List<String> rolesValues){
        user.setPassword (bCryptPasswordEncoder.encode (user.getPassword ()));
        addListOfRolesForUser (user,rolesValues);
        userDao.add (user);
    }

     @Transactional
     @Override
     public void addListOfRolesForUser(User user, List<String> rolesValues) {
        Set<Role> roles = new HashSet<> ();
        for (String role: rolesValues) {
               if (roleDao.countRoles (role)!=0) {
                   roles.add(roleDao.getRoleByName(role));
               } else {
                   Role newRole = new Role();
                   newRole.setRole (role);
                   roleDao.addRole(newRole);
                   roles.add(newRole);
               }
        }
        user.setRoles(roles);
    }

    @Transactional
    @Override
    public List<String> getRoles() {
        return roleDao.getRoles ();
    }

    @Transactional
    @Override
    public List<User> listUsersWithRoles() {
        return userDao.listUsersWithRoles ();
    }

    @Transactional
    @Override
    public List<User> listUsers () {
            return userDao.listUsers ();
        }

    @Transactional
    @Override
    public void delete (User user){
        userDao.delete (user);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userDao.deleteById (id);
    }


    @Transactional
    @Override
    public User updateUserAndHisRoles(User user, List<String> rolesValues) {
        addListOfRolesForUser (user, rolesValues);
        user.setPassword (bCryptPasswordEncoder.encode (user.getPassword ()));
        return userDao.update (user);
    }

    @Transactional
    @Override
    public boolean tableIsEmpty() {
        try {
            userDao.tableIsEmpty ();
        } catch (java.lang.NullPointerException e) {
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById (id);
    }

    @Transactional
    @Override
    public List<User> listUsersWithRoles2() {
        List<User> users = userDao.listUsers ();
        for (User user : users) {
            if (user.getRoles ().iterator ().hasNext ()) {
                user.getRoles ().iterator ().next ();
            }
        }
        return users;
    }

}

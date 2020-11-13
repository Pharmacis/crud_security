package web.service;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service()
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
    public void add (User user){
           /* Set<Role> roleSet = user.getRoles ();
            Role role = new Role (1l, "USER");
            roleDao.addRole (role);
            if (roleSet.isEmpty () || roleSet == null) {
                user.getRoles ().add (role);
                role.getUsers ().add (user);
            } else {
                for (Role role1 : roleSet) {
                    try {
                        Role roleDB = roleDao.getRoleByName (role1.getRole ());
                        roleDB.getUsers ().add (user);
                        roleDao.update (roleDB);
                    } catch (java.lang.NullPointerException e) {
                        role1.getUsers ().add (user);
                        roleDao.addRole (role1);
                    }
                }
                userDao.add (user);
          */
        user.setPassword (bCryptPasswordEncoder.encode (user.getPassword ()));
        userDao.add (user);
    }

    // @Transactional
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
       // user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }
 @Transactional
    @Override
    public List<String> getRoles() {
        return roleDao.getRoles ();
    }

    // @Transactional(readOnly = true)
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
        public void deleteById (Long id){
            userDao.deleteById (id);
        }


        @Transactional
        @Override
        public User update (User user){
            return userDao.update (user);
        }
        @Transactional
        @Override
        public boolean tableIsEmpty () {
            try {
                userDao.tableIsEmpty ();
            } catch (java.lang.NullPointerException e) {
                return false;
            }
            return true;
        }



        @Transactional
        @Override
        public User getUserById (Long id){
            return userDao.getUserById (id);
        }
}

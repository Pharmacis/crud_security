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
    public void deleteById(Long id) {
        userDao.deleteById (id);
    }

    @Transactional
    @Override
    public User updateUserAndHisRoles(User user, List<String> rolesValues) {
        roleDao.setRoleByListNameRole (user,rolesValues);
        user.setPassword (bCryptPasswordEncoder.encode (user.getPassword ()));
        return userDao.update (user);
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById (id);
    }

    @Transactional
    @Override
    public User addUserWithRoles(User user, List<String> roles) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        roleDao.setRoleByListNameRole (user,roles);
        userDao.add (user);
        return user;
    }

}

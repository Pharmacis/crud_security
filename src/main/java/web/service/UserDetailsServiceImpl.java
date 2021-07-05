package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
   void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
    @Autowired
    void setRoleDao(RoleDao roleDao){
        this.roleDao = roleDao;
    }
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDao.getUserWithRoles (name);
        return (UserDetails) user;
    }
}

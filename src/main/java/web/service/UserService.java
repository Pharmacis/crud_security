package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    public void add(User user);

    public List<User> listUsers();

    public void delete(User user);

    public void deleteById(Long id);

    public User update(User user);
    public boolean tableIsEmpty();

    public User getUserById(Long id);

}

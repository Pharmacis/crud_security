package web.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_cs")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column
    private String userName;

    @Column
    private String profession;

    @Column
    @Min (value = 0, message = "Invalid value")
    @Max (value =120)
    private int age;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY,
          cascade = {CascadeType.PERSIST,CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH})
    private Set<Role> roles;

    public User() {
    }

    public User( Long id,String login,String userName,String profession,int age, String password,Set<Role> roles) {
        this.id= id;
        this.login = login;
        this.userName = userName;
        this.profession = profession;
        this.age = age;
        this.roles =roles;
        this.password = password;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getAge () == user.getAge () &&
                getId ().equals (user.getId ()) &&
                Objects.equals (getLogin (), user.getLogin ()) &&
                Objects.equals (getUserName (), user.getUserName ()) &&
                Objects.equals (getProfession (), user.getProfession ()) &&
                Objects.equals (getPassword (), user.getPassword ());
    }

    @Override
    public int hashCode() {
        return Objects.hash (getId (), getLogin (), getUserName (), getProfession (), getAge (), getPassword ());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return getRoles ();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", userName='" + userName + '\'' +
                ", profession='" + profession + '\'' +
                ", age=" + age +
                ", roles=" + roles.toArray ().length +
                '}';
    }
}

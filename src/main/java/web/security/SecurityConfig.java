package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import web.config.HiberConfig;
import web.service.UserService;
import web.service.UserServiceImp;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final UserService userService; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    @Autowired
    public UserService getUserService() {
        return userService;
    }

    //@EnableAspectJAutoProxy(proxyTargetClass=true)
    public SecurityConfig(DataSource dataSource, UserService userServiceImp, SuccessUserHandler successUserHandler) {
        this.userService = userServiceImp;
        this.successUserHandler = successUserHandler;
        this.dataSource = dataSource;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService (userService).passwordEncoder (passwordEncoder ());
        // auth.jdbcAuthentication ();
        auth.jdbcAuthentication ().dataSource (dataSource);// конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {//метод для настройки формы аунтефикации(закоментирован
        // вход в собственноручнонаписанную форму) активный код использует форму входа по-умолчанию.Уровень доступа для страниц.
        // http.csrf().disable(); - попробуйте выяснить сами, что это даёт
        http.authorizeRequests ()//ограничить доступ на основе поступающего запроса.
                // .anyRequest ().authenticated ()  //любые запросы.аутентифицирован,т.е. для просмотра любой страницы пользователь д.б
                // аутентифицирован
                // .and ()
                //.formLogin ()//вход в систему
                // .loginPage("/login")//страница входа. Пишем контроллер  и отображение  для этой страницы
                // .loginProcessingUrl("/autenticateTheUser")//форма вхлда в систему отправит данные на данный адрес
                // именно здесь спринг секьюрити будет проверять логин и пароль пользователя.
                // Контроллер и отображение для этой страницы писать не нало!!!!
                // .permitAll()// разрешить всем видеть страницу входа в систему.
                .antMatchers ("/").permitAll ()// доступность всем
                .antMatchers ("/user").hasRole ("USER")
                .antMatchers ("/admin/**").hasRole ("ADMIN")// разрешаем входить на /user пользователям с ролью User
                .and ().formLogin ()  // Spring сам подставит свою логин форму
                .successHandler (successUserHandler)
                //// подключаем наш SuccessHandler для перенеправления по ролям
                .and ().exceptionHandling ().accessDeniedPage ("/access-denied")// переход на страницу, если ошибка доступа, на основании роли.
                .and ().logout ().permitAll ();
    }

    // Необходимо для шифрования паролей
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    }


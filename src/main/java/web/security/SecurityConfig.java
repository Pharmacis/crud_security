package web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.service.UserDetailsServiceImpl;

import javax.activation.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   // private final DataSource dataSource;
    private final UserDetailsService userDetails; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям
    //@EnableAspectJAutoProxy(proxyTargetClass=true)
    public SecurityConfig( UserDetailsServiceImpl userDetails, SuccessUserHandler successUserHandler) {
        this.userDetails = userDetails;
        this.successUserHandler = successUserHandler;
    }
/*
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService (userService).passwordEncoder (passwordEncoder ());
        // auth.jdbcAuthentication ();
      //  auth.jdbcAuthentication ().dataSource (dataSource);// конфигурация для прохождения аутентификации
    }
 */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
        //auth.userDetailsService (userDetails);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests ().antMatchers ("/").permitAll ();
        http.formLogin ()
               // .loginProcessingUrl("/autenticateTheUser")//.loginProcessingUrl("/autenticateTheUser")//
                .successHandler (successUserHandler)
                .permitAll ();
        http.logout ()//URL выхода из системы безопасности Spring - только POST. Вы можете поддержать выход из системы без POST, изменив конфигурацию Java
                .logoutRequestMatcher (new AntPathRequestMatcher ("/logout"))//выход из системы гет запрос на /logout
                .logoutSuccessUrl ("/login")//успешный выход из системы
                .and().csrf().disable();

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                //страницы аутентификаци доступна всем
                .antMatchers("/login").anonymous()
                // защищенные URL
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and().formLogin()// Spring сам подставит свою логин форму
                .successHandler(successUserHandler);


    }

    // Необходимо для шифрования паролей
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    }


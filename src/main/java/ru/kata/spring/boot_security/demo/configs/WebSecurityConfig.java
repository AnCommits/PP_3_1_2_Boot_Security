package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.kata.spring.boot_security.demo.service.UserService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()
                .authorizeRequests()
//                .anyRequest().authenticated()
//                .antMatchers("/admin/**").authenticated()
//                .antMatchers("/user/**").authenticated()
//                .antMatchers("/test/**").authenticated()
                .antMatchers("/**").permitAll()
                .and().formLogin().loginPage("/login").permitAll()
//                .defaultSuccessUrl("/")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService((UserDetailsService) userService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder(10);
    }
}

//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("u")
//                .password("u")
//                .roles("USER").build();
//
//        UserDetails admin = User.builder()
//                .username("a")
//                .password("a")
//                .roles("ADMIN", "USER").build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("u")
//                .password("$2a$10$sA36/xfwJgmFrEK3nlqVEO7prx6SyC5eoKoWdOUDtSDkOUTdSqKsu")
//                .roles("USER").build();
//        UserDetails admin = User.builder()
//                .username("a")
//                .password("$2a$10$FGN926QXAu0YKN.ikN/CHeSOr3JQapaNPkxekK3w7xfW.tMS9UEi6")
//                .roles("ADMIN", "USER").build();
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        if (jdbcUserDetailsManager.userExists(user.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(user.getUsername());
//        }
//        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(admin.getUsername());
//        }
//        jdbcUserDetailsManager.createUser(user);
//        jdbcUserDetailsManager.createUser(admin);
//        return jdbcUserDetailsManager;
//    }

//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("u")
//                .password("$2a$10$sA36/xfwJgmFrEK3nlqVEO7prx6SyC5eoKoWdOUDtSDkOUTdSqKsu")
//                .roles("USER").build();
//
//        UserDetails admin = User.builder()
//                .username("a")
//                .password("$2a$10$FGN926QXAu0YKN.ikN/CHeSOr3JQapaNPkxekK3w7xfW.tMS9UEi6")
//                .roles("ADMIN", "USER").build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("u")
//                .password("u")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("a")
//                .password("a")
//                .authorities("ROLE_ADMIN");
//    }

//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/**").permitAll()
//                .loginProcessingUrl("/my-login");
//                .successHandler()
//                .and().logout().logoutSuccessUrl("/");
//                .permitAll()
//                .loginPage("/login").loginProcessingUrl("/perform-login")
//                .defaultSuccessUrl("/");
//                .antMatchers("/test/**").authenticated()

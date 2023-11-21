package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                .authorizeRequests()
                .antMatchers("/guest/sign-up").not().authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/test/**").authenticated()
                .antMatchers("/").permitAll()
                .and().formLogin().permitAll()
                    .loginPage("/login")
                .defaultSuccessUrl("/")
                .and().logout().permitAll();
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("u")
                .password("u")
                .roles("USER").build();
        UserDetails admin = User.builder()
                .username("a")
                .password("a")
                .roles("ADMIN", "USER").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}

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


//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService((UserDetailsService) userService);
//        return authenticationProvider;
//    }
package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").authenticated()
                .and().formLogin();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("u")
                .password("$2a$10$sA36/xfwJgmFrEK3nlqVEO7prx6SyC5eoKoWdOUDtSDkOUTdSqKsu")
                .roles("USER").build();

        UserDetails admin = User.builder()
                .username("a")
                .password("$2a$10$FGN926QXAu0YKN.ikN/CHeSOr3JQapaNPkxekK3w7xfW.tMS9UEi6")
                .roles("ADMIN", "USER").build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
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

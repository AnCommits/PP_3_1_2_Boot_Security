//package ru.kata.spring.boot_security.demo.configs;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
////                .antMatchers("/**").permitAll();
////                .antMatchers("/admin/**").hasRole("ADMIN")
//////                .antMatchers("/admin-edit-user/**").hasRole("ADMIN")
////                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/**").permitAll()
//            .and().formLogin().permitAll()
//                .loginPage("/login").loginProcessingUrl("/perform-login")
//                .defaultSuccessUrl("/");
//    }
//}
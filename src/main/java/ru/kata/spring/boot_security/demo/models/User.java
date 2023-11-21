package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * Used such as login
     */
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar birthDate;

    @Column(name = "record_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordDateTime;

    //    @ManyToMany(cascade = {CascadeType.ALL})
//    @OneToMany(cascade = {CascadeType.ALL})
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
//    @JoinTable
    @JoinColumn
    private Set<Role> roles;

    private boolean locked;

    public User(String firstName, String lastName, String email, String password,
                Calendar birthDate, Set<Role> roles, boolean locked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.roles = roles;
        this.locked = locked;
    }

    public boolean isAdmin() {
        return roles.stream().anyMatch(r -> r.getRolesType() == Role.RolesType.ADMIN);
    }

    public String birthDateToString() {
        return birthDate != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(birthDate.getTime())
                : "";
    }

    public String recordDateTimeToString() {
        return recordDateTime != null
                ? new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(recordDateTime)
                : "";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                //                   "ROLE_" + r.getRolesType().name() ?
                .map(r -> new SimpleGrantedAuthority(r.getRolesType().name()))
                .collect((Collectors.toList()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

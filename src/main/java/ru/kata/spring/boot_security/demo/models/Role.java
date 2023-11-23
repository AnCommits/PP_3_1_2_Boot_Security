package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.IntStream;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<User> user;

    @Transient
    public final static RolesType[] rolesTypes = RolesType.values();

    public Role(String name) {
        setName(name);
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        String nameInUpperCase = name.toUpperCase();
        boolean correctName = Arrays.stream((rolesTypes))
                .anyMatch(r -> r.name().equals(nameInUpperCase));
        this.name = correctName ? nameInUpperCase : rolesTypes[0].name();
    }

    /**
     * Roles must go from the lowest to the highest.
     * If the role is specified incorrectly in a constructor the first role will be installed.
     * The last role is considered the highest in the view.
     */
    public enum RolesType {
        USER,           // can read
        SUPER_USER,     // + can update
        ADMIN,          // controls users
        SUPER_ADMIN;    // + controls admins, can't be removed or locked
    }

    public static LinkedHashSet<Role> getSetOfRoles(int numberOfRoles) {
        LinkedHashSet<Role> roles = new LinkedHashSet<>();
        IntStream.range(0, numberOfRoles).mapToObj(n -> new Role(rolesTypes[n].name())).forEach(roles::add);
        return roles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

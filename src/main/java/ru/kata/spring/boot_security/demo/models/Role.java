package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    private RolesType rolesType;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<User> user;

//    public Role(RolesType rolesType) {
//        this.rolesType = rolesType;
//    }
    public Role(String name) {
        setName(name);
    }

    @Override
    public String getAuthority() {
//        return rolesType.name();
        return name;
    }

    public void setName(String name) {
        RolesType[] rolesTypes = RolesType.values();
        String nameInUpperCase = name.toUpperCase();
        boolean b = Arrays.stream((rolesTypes))
                .anyMatch(r -> r.name().equals(nameInUpperCase));
        this.name = b ? nameInUpperCase : rolesTypes[0].name();
    }

    /**
     * Roles must go from lowest to highest
     */
    public enum RolesType {
        USER,           // can read
        SUPER_USER,     // + can update
        ADMIN,          // controls users
        SUPER_ADMIN     // + controls admins
    }

    public static Set<Role> getSetOfRoles(int numberOfRoles) {
//        RolesType[] allRolesType = RolesType.values();
        RolesType[] rolesTypes = RolesType.values();
        Set<Role> roles = new HashSet<>();
//        IntStream.range(0, numberOfRoles).mapToObj(n -> new Role(allRolesType[n])).forEach(roles::add);
        IntStream.range(0, numberOfRoles).mapToObj(n -> new Role(rolesTypes[n].name())).forEach(roles::add);
        return roles;
    }

    @Override
    public int hashCode() {
//        return Objects.hash(rolesType);
        return Objects.hash(name);
    }
}

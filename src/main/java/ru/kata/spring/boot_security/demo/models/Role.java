package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
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

    private RolesType rolesType;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<User> user;

    public Role(RolesType rolesType) {
        this.rolesType = rolesType;
    }

    @Override
    public String getAuthority() {
        return rolesType.name();
    }

    /**
     * Roles must go from lowest to highest
     */
    public enum RolesType {
        USER,           // todo can read
//        SUPER_USER,     //      + can write
        ADMIN          //
//        SUPER_ADMIN     //      controls admins
    }

    public static Set<Role> getSetOfRoles(int numberOfSets) {
        RolesType[] allRolesType = RolesType.values();
        Set<Role> roles = new HashSet<>();
        IntStream.range(0, numberOfSets).mapToObj(n -> new Role(allRolesType[n])).forEach(roles::add);
        return roles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolesType);
    }
}

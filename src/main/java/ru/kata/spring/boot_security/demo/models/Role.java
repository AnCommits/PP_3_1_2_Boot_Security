package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private RolesType rolesType;

    //    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;
    @ManyToOne
    private User user;

    public Role(RolesType rolesTypes) {
        this.rolesType = rolesTypes;
    }

    public enum RolesType {
        ADMIN,
        USER
    }

    public static Set<Role> getSetOfRoles(int set) {
        return switch (set) {
            case 1 -> Set.of(new Role(Role.RolesType.USER));
            case 2 -> Set.of(
                    new Role(Role.RolesType.ADMIN),
                    new Role(Role.RolesType.USER));
            default -> new HashSet<>();
        };
    }
}

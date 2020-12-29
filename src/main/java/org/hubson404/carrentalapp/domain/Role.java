//package org.hubson404.carrentalapp.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.Set;
//import java.util.UUID;
//
//@Entity(name = "roles")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Role {
//
//    @Id
//    @GeneratedValue
//    private UUID id;
//
//    @Column(name = "role_name")
//    private String name;
//
//    @ManyToMany
//    @JoinTable(name = "roles_to_users",
//            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//    private Set<User> users;
//
//}

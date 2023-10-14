package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Integer uid;
    @Column(name = "email")
    private String email;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "amount")
    private double amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthentificationProvider authProvider;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "rid")}
    )
    private List<Role> roles = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password, List<Role> roles, AuthentificationProvider authProvider) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.authProvider = authProvider;
    }

}

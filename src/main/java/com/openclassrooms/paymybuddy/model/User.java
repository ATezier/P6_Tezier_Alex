package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name="rid", referencedColumnName = "rid")
    private Role role;

    public User(String firstName, String lastName, String email, String password, Role role, AuthentificationProvider authProvider) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.authProvider = authProvider;
    }

}

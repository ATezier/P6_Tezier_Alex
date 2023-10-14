package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    private Integer aid;

    @Column(name = "uid")
    private Integer uid;
    @Column(name = "amount")
    private double amount;
    @Column(name = "card_number")
    private String cardNumber;
}

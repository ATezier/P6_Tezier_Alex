package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "account")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    private Integer aid;
    @Column(name = "uid")
    private Integer uid;
    @Column(name = "name")
    private String name;
    @Column(name = "card_type")
    private CardTypeProvider cardType;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "exp_month")
    private Integer expMonth;
    @Column(name = "exp_year")
    private Integer expYear;
    @Column(name = "modified_date")
    private Timestamp modifiedDate;
}

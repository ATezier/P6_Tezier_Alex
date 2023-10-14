package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid")
    private Integer tid;
    @Column(name ="payer")
    private Integer payer;
    @Column(name ="paid")
    private Integer paid;
    @Column(name ="price")
    private double price;
    @Column(name = "date")
    private Timestamp date;
    @Column(name ="label")
    private String label;
}

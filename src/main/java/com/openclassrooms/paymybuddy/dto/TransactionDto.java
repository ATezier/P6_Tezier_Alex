package com.openclassrooms.paymybuddy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TransactionDto {
    private Integer uid;
    private String firstName;
    private String lastName;
    private Timestamp date;
    private double amount;
    private String label;

    public TransactionDto(Integer uid, String firstName, String lastName, Timestamp date, double amount, String label) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.amount = amount;
        this.label = label;
    }
}

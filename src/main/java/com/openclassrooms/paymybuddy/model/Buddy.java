package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "buddies")
@NoArgsConstructor
public class Buddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid")
    private Integer bid;
    @Column(name = "uid1")
    private Integer uid1;
    @Column(name = "uid2")
    private Integer uid2;

    public Buddy(Integer uid1, Integer uid2) {
        this.uid1 = uid1;
        this.uid2 = uid2;
    }
}

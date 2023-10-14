package com.openclassrooms.paymybuddy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendDto {
    private Integer uid;
    private String lastName;
    private String firstName;

    public FriendDto(Integer uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

package com.openclassrooms.paymybuddy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;

    @NotEmpty(message = "Please enter valid firstname.")
    private String firstName;

    @NotEmpty(message = "Please enter valid lastname.")
    private String lastName;

    @NotEmpty(message = "Please enter valid email.")
    @Email
    private String email;

    @NotEmpty(message = "Please enter valid password.")
    private String password;
}

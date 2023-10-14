package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.FriendDto;
import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.AuthentificationProvider;
import com.openclassrooms.paymybuddy.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserByUid(Integer uid);

    List<FriendDto> getFriendDtoList(String email);

    boolean addBuddy(String myEmail, String buddyEmail);

    void updateUser(UserDto user);

    boolean deleteUserByEmail(String email);

    boolean addMoney(double amount, Integer uid);

    public boolean addMoney(double amount, String email);

    void saveNewUserAfterOAuthLoginSuccess(String email, String name, AuthentificationProvider authentificationProvider);

    void updateUserAfterOAuthLoginSuccess(User user, String name, AuthentificationProvider authentificationProvider);
}

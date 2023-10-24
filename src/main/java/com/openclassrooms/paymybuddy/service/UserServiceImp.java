package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.FriendDto;
import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.AuthentificationProvider;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final BuddyService buddyService;

    public UserServiceImp(UserRepository userRepository, BuddyService buddyService) {
        this.userRepository = userRepository;
        this.buddyService = buddyService;
    }

    @Autowired
    private com.openclassrooms.paymybuddy.repository.RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(com.openclassrooms.paymybuddy.dto.UserDto userDto) {
        com.openclassrooms.paymybuddy.model.Role role = roleRepository.findByName(com.openclassrooms.paymybuddy.util.TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new com.openclassrooms.paymybuddy.model.Role(com.openclassrooms.paymybuddy.util.TbConstants.Roles.USER));

        User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
                Arrays.asList(role), AuthentificationProvider.LOCAL);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUid(Integer uid) {
        return userRepository.findByUid(uid);
    }

    public User findUserByName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }


    @Override
    public List<FriendDto> getFriendDtoList(String email) {
        List<FriendDto> friendDtoList = null;
        User user = userRepository.findByEmail(email);
        List<Integer> uidList = buddyService.getBuddyList(user.getUid());
        user = null;
        if(!uidList.isEmpty()) {
            friendDtoList = new ArrayList<>();
            for(Integer id : uidList) {
                user = userRepository.findByUid(id);
                if(user != null) {
                    friendDtoList.add(new FriendDto(user.getUid(), user.getFirstName(), user.getLastName()));
                }
            }
        }
        return friendDtoList;
    }
    @Override
    public boolean addBuddy(String myEmail, String buddyEmail) {
        Integer myUid = userRepository.findByEmail(myEmail).getUid();
        Integer buddyId = userRepository.findByEmail(buddyEmail).getUid();
        return buddyService.addBuddy(myUid, buddyId);
    }

    public void updateUser(UserDto dto) {
        User user = userRepository.findByUid(dto.getId());
        if(dto != null) {
            if(!dto.getEmail().isEmpty()) user.setEmail(dto.getEmail());
            if(!dto.getFirstName().isEmpty()) user.setFirstName(dto.getFirstName());
            if (!dto.getLastName().isEmpty()) user.setLastName(dto.getLastName());
            if (!dto.getPassword().isEmpty()) user.setPassword(dto.getPassword());
        }
        userRepository.save(user);
    }

    public boolean addMoney(double amount, Integer uid) {
        boolean res = false;
        User user = userRepository.findByUid(uid);
        double newAmount = user.getAmount() + amount;
        if(newAmount > 0) {
            user.setAmount(newAmount);
            userRepository.save(user);
            res = true;
        }
        return res;
    }
    public boolean addMoney(double amount, String email) {
        User user = userRepository.findByEmail(email);
        return addMoney(amount, user.getUid());
    }

    public void saveNewUserAfterOAuthLoginSuccess(String email, String name, AuthentificationProvider provider) {
        User user = new User(name, null, email, null, null, provider);
        userRepository.save(user);
    }

    public void updateUserAfterOAuthLoginSuccess(User user, String name, AuthentificationProvider provider) {
        user.setFirstName(name);
        user.setAuthProvider(provider);
        userRepository.save(user);
    }

    public boolean deleteUserByEmail(String email) {
        boolean res = false;
        User user = userRepository.findByEmail(email);
        if(user != null) {
            userRepository.delete(user);
            res = true;
        }
        return res;
    }
}

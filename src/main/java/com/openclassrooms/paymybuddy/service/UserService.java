package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.FriendDto;
import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.RoleRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BuddyService buddyService;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BuddyService buddyService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.buddyService = buddyService;
        this.roleRepository = roleRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(com.openclassrooms.paymybuddy.dto.UserDto userDto) {
        com.openclassrooms.paymybuddy.model.Role role = roleRepository.findByName(com.openclassrooms.paymybuddy.util.TbConstants.Roles.USER);


        if (role == null)
            role = roleRepository.save(new com.openclassrooms.paymybuddy.model.Role(com.openclassrooms.paymybuddy.util.TbConstants.Roles.USER));

        User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
                role);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUid(Integer uid) {
        return userRepository.findByUid(uid);
    }

    public List<FriendDto> getFriendDtoList(String email) {
        List<FriendDto> friendDtoList = null;
        User user = userRepository.findByEmail(email);
        List<Integer> uidList = buddyService.getBuddyList(user.getUid());
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
    public boolean addBuddy(String myEmail, String buddyEmail) {
        Integer myUid = userRepository.findByEmail(myEmail).getUid();
        Integer buddyId = userRepository.findByEmail(buddyEmail).getUid();
        return buddyService.addBuddy(myUid, buddyId);
    }

    public void updateUser(UserDto dto) {
        User user = userRepository.findByUid(dto.getId());
        if(!dto.getEmail().isEmpty()) user.setEmail(dto.getEmail());
        if(!dto.getFirstName().isEmpty()) user.setFirstName(dto.getFirstName());
        if (!dto.getLastName().isEmpty()) user.setLastName(dto.getLastName());
        if (!dto.getPassword().isEmpty()) user.setPassword(dto.getPassword());
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
        Integer uid = userRepository.findByEmail(email).getUid();
        return addMoney(amount, uid);
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

    public Integer getUidByEmail(String email) {
        return userRepository.findByEmail(email).getUid();
    }
}

package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringJUnitConfig
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    private User user = null;
    private List<Integer> buddyList = null;
    private UserDetails userDetails = null;

    @MockBean
    private BuddyService buddyService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @BeforeAll
    public void setUp() {
        user = new User();
        user.setUid(1);
        buddyList = List.of(1, 2, 3);
    }

    @Test
    public void findUserByEmailTest() {
        given(userRepository.findByEmail(anyString())).willReturn(user);
        Assertions.assertNotNull(userService.findUserByEmail("email"));
    }

    @Test
    public void findUserByUidTest() {
        given(userRepository.findByUid(anyInt())).willReturn(user);
        Assertions.assertNotNull(userService.findUserByUid(1));
    }

    @Test
    public void getFriendDtoListTest() {
        given(userRepository.findByEmail(anyString())).willReturn(user);
        given(userRepository.findByUid(anyInt())).willReturn(user);
        given(buddyService.getBuddyList(anyInt())).willReturn(buddyList);

        Assertions.assertNotNull(userService.getFriendDtoList("email"));
    }

     @Test
    public void addBuddyTest() {
        given(userRepository.findByEmail(anyString())).willReturn(user);
        given(buddyService.addBuddy(anyInt(), anyInt())).willReturn(true);

        Assertions.assertTrue(userService.addBuddy("email", "anotherMail"));
    }

    @Test
    public void addMoneyTest() {
        given(userRepository.findByUid(anyInt())).willReturn(user);
        given(userRepository.findByEmail(anyString())).willReturn(user);
        given(userRepository.save(user)).willReturn(user);

        Assertions.assertTrue(userService.addMoney(10, 1));
        Assertions.assertTrue(userService.addMoney(10, "email"));
    }

    @Test
    public void deleteUserByEmailTest() {
        given(userRepository.findByEmail(anyString())).willReturn(user);

        Assertions.assertTrue(userService.deleteUserByEmail("email"));
    }



}

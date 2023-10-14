package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Buddy;
import com.openclassrooms.paymybuddy.repository.BuddyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringJUnitConfig
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuddyServiceTest {
    private Integer uid1 = 1;
    private Integer uid2 = 2;
    private Buddy buddy = null;
    private List<Buddy> buddies = null;


    @MockBean
    private BuddyRepository buddyRepository;
    @Autowired
    private BuddyService buddyService;

    @BeforeAll
    public void setUp() {
        buddy = new Buddy();
        buddies = new ArrayList<>();
    }

    @Test
    public void getBuddyListTest() {
        buddies.add(buddy);
        given(buddyRepository.findAllByUid1OrUid2(uid1,uid1)).willReturn(buddies);
        assertTrue(buddyService.getBuddyList(uid1).size() == 1);
    }
    @Test
    public void addBuddyTest() {
        assertTrue(buddyService.addBuddy(uid1,uid2));
    }

    @Test
    public void removeBuddyTest() {
        given(buddyRepository.existsByUid1AndUid2(uid1,uid2)).willReturn(true);
        assertTrue(buddyService.removeBuddy(uid1,uid2));
    }

}

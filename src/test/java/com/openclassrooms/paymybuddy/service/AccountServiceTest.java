package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@SpringJUnitConfig
public class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void getAidsByUidTest() {
        List<Integer> aids = new ArrayList<>();
        given(accountRepository.findAidByUid(1)).willReturn(aids);
        assertTrue(accountService.getAidsByUid(1) != null);
    }

}

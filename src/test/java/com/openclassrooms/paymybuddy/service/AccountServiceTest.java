package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@SpringJUnitConfig
public class AccountServiceTest {

    @MockBean
    UserService userService;
    @MockBean
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @Test
    public void testAddAccount() {
        Account account = new Account();
        account.setCardNumber("4234567890123456");
        given(userService.getUidByEmail(anyString())).willReturn(1);
        accountService.addAccount(account, "email");
        assertTrue(account.getCardType() != null);
    }
    @Test
    public void testGetAccountsByEmail() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());
        accounts.add(new Account());
        given(userService.getUidByEmail(anyString())).willReturn(1);
        given(accountRepository.findAllByUid(anyInt())).willReturn(accounts);
        assertTrue(accountService.getAccountsByEmail("email").size() == 3);
    }

    @Test
    public void testSupplyAccount() {
        Account account = new Account();
        account.setUid(1);
        given(accountRepository.findByAid(anyInt())).willReturn(account);
        accountService.supplyAccount(100.0, 1);
    }
}

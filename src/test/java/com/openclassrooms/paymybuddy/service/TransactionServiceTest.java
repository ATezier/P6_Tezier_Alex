package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionServiceTest {
    private User user = null;
    @MockBean
    private UserService userService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @BeforeAll
    public void setUp() {
        user = new User();
        user.setUid(1);
    }

    @Test
    public void getTransactionsByUidTest() {
        List<Transaction> transactionList = new ArrayList<>();
        given(transactionRepository.findByPayer(anyInt())).willReturn(transactionList);
        assertTrue(transactionService.getTransactionsByUid(1) != null);
    }
    @Test
    public void qddTransactionTest() {
        given(userService.findUserByEmail(anyString())).willReturn(user);
        given(userService.addMoney(anyDouble(), anyInt())).willReturn(true);
        assertTrue(transactionService.addTransaction("email", 1, "label", 1.0));
    }

    @Test
    public void getHistoryTest() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setPayer(1);
        transaction.setDate(new Timestamp(System.currentTimeMillis()));
        transaction.setLabel("label");
        transaction.setPrice(1.0);
        transaction.setPaid(1);
        transactionList.add(transaction);
        user.setFirstName("test");
        user.setLastName("tester");
        given(userService.findUserByEmail(anyString())).willReturn(user);
        given(userService.findUserByUid(anyInt())).willReturn(user);
        given(transactionRepository.findByPayerOrPaidOrderByDateDesc(anyInt(), anyInt())).willReturn(transactionList);
        assertTrue(transactionService.getHistory("email") != null);
    }
}

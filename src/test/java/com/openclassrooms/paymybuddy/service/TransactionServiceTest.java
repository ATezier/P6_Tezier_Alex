package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringJUnitConfig
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionServiceTest {
    private User user = null;
    @MockBean
    private UserService userService;

    @MockBean
    private TransactionRepository transactionRepository;

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
        Assertions.assertNotNull(transactionService.getTransactionsByUid(1));
    }
    @Test
    public void addTransactionTest() {
        given(userService.findUserByEmail(anyString())).willReturn(user);
        given(userService.addMoney(anyDouble(), anyInt())).willReturn(true);
        assertDoesNotThrow(() -> transactionService.addTransaction("email", 1, "label", 1.0));
    }

    @Test
    public void getPageTest() {
        List<Transaction> transactionList = new ArrayList<>();
        Pageable pageSort = PageRequest.of(0, 3);
        Page<Transaction> transactionPage;
        Transaction transaction = new Transaction();
        transaction.setPayer(1);
        transaction.setDate(new Timestamp(System.currentTimeMillis()));
        transaction.setLabel("label");
        transaction.setPrice(1.0);
        transaction.setPaid(1);
        transactionList.add(transaction);
        transactionPage = new PageImpl<>(transactionList, pageSort, transactionList.size());
        user.setFirstName("test");
        user.setLastName("tester");
        given(userService.getUidByEmail(anyString())).willReturn(1);
        given(userService.findUserByUid(anyInt())).willReturn(user);
        given(transactionRepository.findByPayerOrPaid(1, 1, pageSort)).willReturn(transactionPage);
        Assertions.assertNotNull(transactionService.getPage("email", pageSort));
    }
}

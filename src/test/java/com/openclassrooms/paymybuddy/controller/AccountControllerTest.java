package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.Application;
import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.service.AccountService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    @Test
    @WithMockUser(username = "test@example.com")
    public void accountsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/addFunds"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("addFunds"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void accountFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/addAccount"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("addAccount"));
    }

    @Test
    @WithMockUser(username = "test1@example.com")
    public void addAccountTest() throws Exception {
        Account account = new Account();
        account.setCardNumber("523499994444");
        account.setName("Test Tester");
        account.setExpMonth(12);
        account.setExpYear(2028);
        mockMvc.perform(MockMvcRequestBuilders.post("/addAccount")
                        .param("name", account.getName())
                        .param("cardNumber", account.getCardNumber())
                        .param("expMonth", account.getExpMonth().toString())
                        .param("expYear", account.getExpYear().toString()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/addFunds?success"));
        List<Account> accounts = accountService.getAccountsByEmail("test1@example.com");
        accountService.removeAccount(accounts.get(0).getAid());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void addFundsTest() throws Exception {
        List<Account> accounts = accountService.getAccountsByEmail("test@example.com");
        Account account;
        if(accounts.isEmpty()) {
            account = new Account();
            account.setName("Test Tester");
            account.setCardNumber("312344448888");
            account.setExpMonth(9);
            account.setExpYear(2030);
            accountService.addAccount(account, "test@example.com");
            accounts = accountService.getAccountsByEmail("test@example.com");
        }
        account = accounts.get(0);
        mockMvc.perform(MockMvcRequestBuilders.post("/addFunds")
                        .param("amount", "100.0")
                        .param("aid", account.getAid().toString()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home?success"));
    }

}

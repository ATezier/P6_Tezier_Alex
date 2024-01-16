package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @WithMockUser(username = "test@example.com")
    public void testTransferPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transfer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/transfer"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testAddTransfer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/addTransaction")
                .param("paid", "10")
                .param("label", "test")
                .param("price", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/transfer?success"));
    }
}
package com.openclassrooms.paymybuddy.controller;


import com.openclassrooms.paymybuddy.Application;
import com.openclassrooms.paymybuddy.service.BuddyService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuddiesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BuddyService buddyService;
    @Autowired
    UserService userService;

    @AfterAll
    public void cleanUp() {
        Integer uid1 = userService.findUserByEmail("test@example.com").getUid();
        Integer uid2 = userService.findUserByEmail("test1@example.com").getUid();
        buddyService.removeBuddy(uid1, uid2);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void contactTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("contact"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void addBuddyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/addBuddy")
                        .param("buddyEmail", "test1@example.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/contact?success"));
    }
}

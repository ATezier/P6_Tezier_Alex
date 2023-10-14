package com.openclassrooms.paymybuddy.controller;


import com.openclassrooms.paymybuddy.Application;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @AfterAll
    public void cleanUp() {
        userService.deleteUserByEmail("testToDelete@example.com");
    }

    @Test
    public void loginPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void registrationFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registration"));
    }

    @Test
    public void registrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .param("firstName", "test")
                        .param("lastName", "tester")
                        .param("email", "testToDelete@example.com")
                        .param("password", "test"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/registration?success"));
    }

}

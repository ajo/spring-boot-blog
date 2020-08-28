package com.reljicd.controller;

import com.reljicd.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Login page prompts unauthenticated users to login.")
    void loginUnauthenticated() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(content().string(containsString("Please Login")));
    }

    @Test
    @DisplayName("Login page redirects authenticated users.")
    @WithMockUser
    void loginAuthenticated() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Login with valid credentials succeeds.")
    void loginWithValidCredentials() throws Exception {

        User testAccount = new User();
        testAccount.setUsername("admin");
        testAccount.setPassword("admin");

        RequestBuilder requestBuilder = post("/login")
                .param("username", testAccount.getUsername())
                .param("password", testAccount.getPassword());
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("SPRING_SECURITY_CONTEXT", instanceOf(SecurityContextImpl.class)))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("Login with invalid credentials fails.")
    void loginWithInvalidCredentials() throws Exception {

        RequestBuilder requestBuilder = post("/login")
                .param("username", "fail")
                .param("password", "fail");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttributeDoesNotExist("SPRING_SECURITY_CONTEXT"))
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    @DisplayName("Login with blank credentials fails.")
    void loginWithBlankCredentials() throws Exception {

        RequestBuilder requestBuilder = post("/login")
                .param("username", "")
                .param("password", "");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttributeDoesNotExist("SPRING_SECURITY_CONTEXT"))
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    @DisplayName("The logout URL displays the logout message.")
    void logoutMessage() throws Exception {
        this.mockMvc.perform(get("/login?logout"))
                .andExpect(content().string(containsString("You have been logged out.")));
    }

    @Test
    @DisplayName("The login error URL displays the failed login message.")
    void errorMessage() throws Exception {
        this.mockMvc.perform(get("/login?error"))
                .andExpect(content().string(containsString("Invalid username and password.")));
    }
}
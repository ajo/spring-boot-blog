package com.reljicd.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Viewing a individual users blog shows their posts")
    void blogForValidUsername() throws Exception {
        this.mockMvc.perform(get("/blog/admin"))
                .andExpect(content().string(containsString("Test Post 1")));
    }

    @Test
    @DisplayName("Viewing a individual users blog does not show others posts")
    void blogForOtherUsername() throws Exception {
        this.mockMvc.perform(get("/blog/admin"))
                .andExpect(content().string(not(containsString("Test Post 2"))));
    }

    @Test
    @DisplayName("Invalid usernames produce a 404")
    void blogForInvalidUsername() throws Exception {
        this.mockMvc.perform(get("/blog/invalidusername"))
                .andExpect(content().string(containsString("404 NOT_FOUND")));
    }

    @Test
    @DisplayName("Null usernames produce a 404")
    void blogForNullUsername() throws Exception {
        this.mockMvc.perform(get("/blog/"))
                .andExpect(status().isNotFound());
    }
}
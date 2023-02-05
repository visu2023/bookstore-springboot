package com.visulabs.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visulabs.bookstore.service.BookStoreUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;



@SpringBootTest
@AutoConfigureMockMvc
class BookStoreUserLoginControllerTest {


    @Autowired
    MockMvc mockMvc;
    @Test
    public void shouldLogin()  throws Exception {
        BookStoreUserRequest request = new BookStoreUserRequest("admin","password",null,null);

        mockMvc.perform(post("/api/bookstore/v1/login")
                 .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk());

    }
    private String asJsonString(Object object ) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
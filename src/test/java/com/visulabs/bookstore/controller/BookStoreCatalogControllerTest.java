package com.visulabs.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visulabs.bookstore.security.TokenManager;
import com.visulabs.bookstore.service.BookClientDTO;
import com.visulabs.bookstore.service.BookStoreUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookStoreCatalogControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    TokenManager tokenManager;
    @Test
    void testGetBookByIsbn() throws Exception{
        BookClientDTO dto = new BookClientDTO();
        String token = "Bearer" +" " + tokenManager.generateJwtToken("admin");
        mockMvc.perform(get("/api/bookstore/v1/book/getABookByIsbn")
                        .header("Authorization", token)
                        .param("isbn","ABCmmm"))
                .andExpect(status().isOk());
    }
    @Test
    void testSearchBooks() throws Exception {
        String token = "Bearer" +" " + tokenManager.generateJwtToken("admin");
        mockMvc.perform(get("/api/bookstore/v1/book/search")
                        .header("Authorization", token)
                        .param("query","ABCmmm"))
                .andExpect(status().isOk());
    }
    @Test
    void testaddABook() throws  Exception {
        BookClientDTO b1 = new BookClientDTO("Test Book","bond0101",134.5,"test book thi sis ","ammazon.xcom",100.1,null,null);
        String token = "Bearer" +" " + tokenManager.generateJwtToken("admin");
        mockMvc.perform(post("/api/bookstore/v1/book/addABook")
                        .with(csrf())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(b1)))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void testUpdateABook() throws Exception {
        BookClientDTO b1 = new BookClientDTO("Test Book","321xyz",134.5,"test book thi sis ","ammazon.xcom",100.1,null,null);
        b1.setBookId(47L);
        String token = "Bearer" +" " + tokenManager.generateJwtToken("admin");
        mockMvc.perform(put("/api/bookstore/v1/book/uodateABook")
                .with(csrf())
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(b1)))
                .andExpect(status().is2xxSuccessful());
    }
//    @Test
//    void testDeleteABook() throws Exception {
//        BookClientDTO b1 = new BookClientDTO("Test Book","321xyz",134.5,"test book thi sis ","ammazon.xcom",100.1,null,null);
//        b1.setBookId(47L);
//        String token = "Bearer" +" " + tokenManager.generateJwtToken("admin");
//        mockMvc.perform(delete("/api/bookstore/v1/book/deleteABook/47")
//                        .with(csrf())
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is2xxSuccessful());
//    }
    private String asJsonString(Object object ) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
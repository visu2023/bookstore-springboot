package com.visulabs.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visulabs.bookstore.security.TokenManager;
import com.visulabs.bookstore.service.BookClientDTO;
import com.visulabs.bookstore.service.BookStoreShoppingCart;
import com.visulabs.bookstore.service.CartItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.*;

import javax.persistence.EntityManager;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    TokenManager tokenManager;
    @AfterEach
    void tearDown() {
    }

    @Test
    void addItemToCart() throws Exception{

    }

    @Test
    void addItemsToCart() throws Exception{
        BookClientDTO b1 = new BookClientDTO("Test Book","tb123",134.5,"test book thi sis ","ammazon.xcom",100.1,34.1,45L);
        BookClientDTO b2 = new BookClientDTO("Test Book","tb123",134.5,"test book thi sis ","ammazon.xcom",100.1,34.1,45L);
        String token = "Bearer" +" " + tokenManager.generateJwtToken("admin");
        List input = new ArrayList<BookClientDTO>();
        input.add(b1);

        BookStoreShoppingCart cart = new BookStoreShoppingCart();
        List cartItems = new ArrayList<CartItem>();
        cartItems.add(new CartItem(b1,1,null));
        cart.setCartItemList(cartItems);
        cart.setCartTotal(34.1);
        cart.setTotalCartDiscount(100.1);
        String expectedJson = asJsonString(cart);
        mockMvc.perform(post("/api/bookstore/v1/book/cart/addItemsToCart")
                        .with(csrf())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(input)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/api/bookstore/v1/book/cart/viewCart")
                        .with(csrf())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        CartItem item = (CartItem) cartItems.get(0);
        item.setQuantity(2);
         expectedJson = asJsonString(cart);
        mockMvc.perform(post("/api/bookstore/v1/book/cart/updateQuantity")
                        .with(csrf())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cartItems)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/api/bookstore/v1/book/cart/viewCart")
                        .with(csrf())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        System.out.println(content());
    }

    @Test
    void updateItemQuantity() {
    }


    void showCart() throws Exception{
        BookClientDTO b1 = new BookClientDTO("Test Book","tb123",134.5,"test book thi sis ","ammazon.xcom",100.1,34.1,null);
        String expectedJson = asJsonString(b1);
        String token = "Bearer" +" " + tokenManager.generateJwtToken("admin");
        mockMvc.perform(get("/api/bookstore/v1/book/cart/viewCart")
                        .with(csrf())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void removeItemFromCart() {
    }

    @Test
    void removeItemsFromCart() {
    }
    private String asJsonString(Object object ) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
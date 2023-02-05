package com.visulabs.bookstore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookStoreUserRequest {

	String userName;

	String password;

	String jwtToken;

	List<String> roles;

}

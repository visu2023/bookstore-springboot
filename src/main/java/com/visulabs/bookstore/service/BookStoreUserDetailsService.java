package com.visulabs.bookstore.service;

import com.visulabs.bookstore.entity.BookStoreUser;

import com.visulabs.bookstore.entity.BookStoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookStoreUserDetailsService implements UserDetailsService {

	@Autowired
	BookStoreUserRepository repository;

	private String customerId;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String[] roleNames = null;
		UserDetails userDetails = null;
		BookStoreUserDetails bookStoreUserDetails = null;
		BookStoreUser user = repository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("username " + username + " not found");
		}
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		if (user.getRoles() != null) {
			userDetails = User.withUsername(user.getUserName()).password(encoder.encode(user.getPassword()))
					.roles(user.getRoles().stream().map(role -> role.getRoleName()).toArray(String[]::new)).build();
		}
		else {
			userDetails = User.withUsername(user.getUserName()).password(encoder.encode(user.getPassword())).build();
		}
		com.visulabs.bookstore.service.BookStoreUser bookStoreUser = new com.visulabs.bookstore.service.BookStoreUser(
				userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
		bookStoreUser.setCustomerId(user.getCustomerId());
		userDetails = bookStoreUser;
		return userDetails;
	}

}

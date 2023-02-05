package com.visulabs.bookstore.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

public class BookStoreUser extends User implements BookStoreUserDetails {

	private Long customerId;

	private String email;

	public BookStoreUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCustomerId() {
		return this.customerId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

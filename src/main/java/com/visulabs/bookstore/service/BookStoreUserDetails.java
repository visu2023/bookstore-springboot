package com.visulabs.bookstore.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface BookStoreUserDetails extends UserDetails {

	public Long getCustomerId();

}

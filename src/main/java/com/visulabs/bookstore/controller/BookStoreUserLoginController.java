package com.visulabs.bookstore.controller;

import com.visulabs.bookstore.security.TokenManager;
import com.visulabs.bookstore.service.BookStoreUserRequest;
import com.visulabs.bookstore.service.BookStoreUserDetailsService;
import jdk.jfr.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class BookStoreUserLoginController {

	@Autowired
	private BookStoreUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping("/api/bookstore/v1/login")
	public ResponseEntity login(@RequestBody BookStoreUserRequest request) throws Exception {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		List<String> roles = authentication.getAuthorities().stream().map(s -> s.getAuthority())
				.collect(Collectors.toList());
		User userObject = (User) authentication.getPrincipal();
		String user = userObject.getUsername();
		final String jwtToken = tokenManager.generateJwtToken(user);
		BookStoreUserRequest userResponse = new BookStoreUserRequest();
		userResponse.setUserName(authentication.getName());
		userResponse.setJwtToken(jwtToken);
		userResponse.setRoles(roles);
		return ResponseEntity.ok().body(userResponse);
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<String> handleException(Exception exception) {
		System.out.println("Exception handled in controller " + exception.toString());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("timestamp: " + Instant.now() + "\n"
				+ "Response Code: " + HttpStatus.UNAUTHORIZED + "\n" + "Error : " + exception.getMessage());
	}

}

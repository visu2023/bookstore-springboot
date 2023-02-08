package com.visulabs.bookstore.controller;

import com.visulabs.bookstore.entity.Book;
import com.visulabs.bookstore.exception.BookNotFoundException;
import com.visulabs.bookstore.exception.ItemNotFoundException;
import com.visulabs.bookstore.exception.NotAuthrizedException;
import com.visulabs.bookstore.service.BookClientDTO;
import com.visulabs.bookstore.service.BookStoreCartService;
import com.visulabs.bookstore.service.BookStoreShoppingCart;
import com.visulabs.bookstore.service.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/bookstore/v1/book/cart")
public class ShoppingCartController {

	private final BookStoreCartService shoppingCartService;

	public ShoppingCartController(BookStoreCartService service) {
		this.shoppingCartService = service;
	}

	@PostMapping(value = "/addToCart", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity addItemToCart(@RequestBody BookClientDTO book) {
		shoppingCartService.addorUpdateItemToCart(book, 1);
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/addItemsToCart")
	public ResponseEntity addItemsToCart(@RequestBody List<BookClientDTO> books) {
		books.forEach(book -> addItemToCart(book));
		return buildResponseEntity("Items Added", HttpStatus.CREATED);
	}

	@PostMapping(value = "/updateQuantity")
	public ResponseEntity<BookStoreShoppingCart> updateItemQuantity(@RequestBody List<CartItem> cartItems) {
		cartItems.forEach(
				cartItem -> shoppingCartService.addorUpdateItemToCart(cartItem.getABook(), cartItem.getQuantity()));
		return ResponseEntity.ok().body(shoppingCartService.getShoppingCart());
	}

	@GetMapping(value = "/viewCart")
	public ResponseEntity<BookStoreShoppingCart> showCart() {
		BookStoreShoppingCart cart = shoppingCartService.getShoppingCart();
		return ResponseEntity.ok().body(cart);
	}

	@PostMapping(value = "/removeFromCart")
	public ResponseEntity<BookStoreShoppingCart> removeItemFromCart(@RequestBody BookClientDTO book) {
		shoppingCartService.removeItemFromCart(book);
		return buildResponseEntity("Removed from cart", HttpStatus.CREATED);
	}

	@PostMapping(value = "/removeItemsFromCart")
	public ResponseEntity<BookStoreShoppingCart> removeItemsFromCart(@RequestBody List<BookClientDTO> books) {
		books.forEach(this::removeItemFromCart);
		return ResponseEntity.ok().body(shoppingCartService.getShoppingCart());
	}

	@ExceptionHandler(value = BookNotFoundException.class)
	public ResponseEntity handleException(Exception e) {
		return buildResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = NotAuthrizedException.class)
	public ResponseEntity handleNotAuthorizedException(Exception e) {
		return buildResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity handleAllOtherException(Exception e) {
		e.printStackTrace();
		return buildResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity buildResponseEntity(String msg, HttpStatus status) {
		return ResponseEntity.status(status)
				.body("timestamp: " + Instant.now() + "\n" + "Response Code: " + status + "\n" + "Error : " + msg);
	}

}

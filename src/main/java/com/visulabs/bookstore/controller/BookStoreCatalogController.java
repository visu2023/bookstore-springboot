package com.visulabs.bookstore.controller;

import com.google.gson.Gson;
import com.visulabs.bookstore.exception.BookNotFoundException;
import com.visulabs.bookstore.exception.NotAuthrizedException;
import com.visulabs.bookstore.service.BookClientDTO;
import com.visulabs.bookstore.service.BookPromotionDTO;
import com.visulabs.bookstore.service.BookStoreUser;
import com.visulabs.bookstore.service.CatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookstore/v1/book")
public class BookStoreCatalogController {

	private CatalogService service;

	public BookStoreCatalogController(CatalogService service) {
		this.service = service;
	}

	@RequestMapping(value = "/getABookByIsbn", method = RequestMethod.GET)
	public BookClientDTO getBookByIsbn(@RequestParam(name = "isbn") String isbn) throws BookNotFoundException {
		return service.findBookByISBN2(isbn);
	}



	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<BookClientDTO> getBookByIsbn() {
		return service.findAllBooks();
	}

	@RequestMapping(value = "/search")
	public List<BookClientDTO> searchBooks(@RequestParam(name = "query") String query) {
		return service.searchBookBytitleAndDesc(query);
	}

	@RequestMapping(value = "/addABook")
	@PostMapping
	public org.springframework.http.ResponseEntity addBook(@RequestBody BookClientDTO b) throws NotAuthrizedException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().indexOf("ADMIN") != -1);
		if (!isAdmin) {
			throw new NotAuthrizedException("Add New Book");
		}
		BookStoreUser user = (BookStoreUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		service.addABook(b, user.getUsername());
		return buildResponseEntity("Book Added", HttpStatus.CREATED);

	}
	@RequestMapping(value = "/addPromotionForBook")
	@PostMapping
	public org.springframework.http.ResponseEntity addPromotionForBook(@RequestBody BookPromotionDTO promotionDTO) throws NotAuthrizedException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().indexOf("ADMIN") != -1);
		if (!isAdmin) {
			throw new NotAuthrizedException("Add New Book");
		}
		BookStoreUser user = (BookStoreUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		service.addPromotionForBook(promotionDTO, user.getUsername());
		return buildResponseEntity("Book Added", HttpStatus.CREATED);

	}

	@RequestMapping(value = "/uodateABook")
	@PutMapping
	public org.springframework.http.ResponseEntity uodateBook(@RequestBody BookClientDTO b)
			throws NotAuthrizedException, BookNotFoundException {
		BookStoreUser user = (BookStoreUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		service.updateABook(b, user.getUsername());
		return buildResponseEntity("Book Updated", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/deleteABook")
	@DeleteMapping
	public org.springframework.http.ResponseEntity deleteBook(@RequestParam Long bookId)
			throws NotAuthrizedException, BookNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		service.deleteBook(bookId);
		return buildResponseEntity("Book Deleted", HttpStatus.CREATED);
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
		return buildResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity buildResponseEntity(String msg, HttpStatus status) {
		Gson gson=new Gson();
		String json = gson.toJson("timestamp: " + Instant.now() + "\n" + "Response Code: " + status + "\n" + "Message : " + msg);
		return ResponseEntity.status(status)
				.body(json);
	}

}

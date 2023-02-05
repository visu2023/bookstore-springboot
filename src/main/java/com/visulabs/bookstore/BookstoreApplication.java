package com.visulabs.bookstore;

import com.visulabs.bookstore.entity.Book;
import com.visulabs.bookstore.entity.BookStoreUser;
import com.visulabs.bookstore.entity.BookStoreUserRoles;
import com.visulabs.bookstore.exception.BookNotFoundException;
import com.visulabs.bookstore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableWebSecurity

public class BookstoreApplication {

	@Autowired
	private EntityManager em;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	CatalogService service;

	@Autowired
	BookStoreCartService bookStoreCartService;

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(BookstoreApplication.class, args);
		// for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
		// System.out.println("Beans for OnlineshoppingApplication..." +
		// beanDefinitionName);
		// }
		/* Authentication UserService */
		BookstoreApplication b = (BookstoreApplication) applicationContext.getBean("bookstoreApplication");
		EntityManager em = b.getEntityManager();
		AuthenticationManager authenticationManager = b.getAuthenticationManager();
		BookStoreUser user = em.find(BookStoreUser.class, 1L);
		System.out.println(user.getRoles());
		String username = "admin";
		String password = "password";
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		List l = authentication.getAuthorities().stream().collect(Collectors.toList());
		List l2 = authentication.getAuthorities().stream().map(s -> s.getAuthority()).collect(Collectors.toList());
		UserDetails u = b.getUserDetailsService().loadUserByUsername(username);
		System.out.println(authentication.isAuthenticated());
		/* BookService */
		CatalogService cs = b.getService();
		BookStoreCartService service = b.getBookStoreService();
		try {
			List<BookVO> books = cs.findBookByISBN("ABC123");
			Book book = cs.findBookById(1L);
			List<BookVO> booksVO = cs.findBookByISBN("ABC123");
			BookClientDTO b1 = cs.findBookByISBN2("ABC123");
		}
		catch (BookNotFoundException e) {
			e.printStackTrace();
		}

		List<BookClientDTO> b2 = cs.searchBookBytitleAndDesc("Gandhi");
		/* CartService */
		BookClientDTO dto1 = new BookClientDTO("book1", "acb234", 10.0, "book1", "amazon.com", 2.0, 8.0, 3L);
		BookClientDTO dto2 = new BookClientDTO("book2", "acb2342", 10.0, "book2", "amazon.com", 2.0, 8.0, 4L);
		BookClientDTO dto3 = new BookClientDTO("book3", "acb23423", 10.0, "book3", "amazon.com", 2.0, 8.0, 5L);

		b.getBookStoreService().addItemToCart(dto1);
		b.getBookStoreService().addItemToCart(dto2);
		b.getBookStoreService().addItemToCart(dto3);
		b.getBookStoreService().addItemToCart(dto1);
		System.out.println("Before removal " + b.getBookStoreService().getShoppingCart().getCartTotal());
		try {
			b.getBookStoreService().removeItemFromCart(dto1);
			b.getBookStoreService().removeItemFromCart(dto2);
			b.getBookStoreService().removeItemFromCart(dto3);
			b.getBookStoreService().removeItemFromCart(null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(b.getBookStoreService().getShoppingCart().getCartTotal());
		System.out.println(b.getBookStoreService().getShoppingCart().getTotalCartDiscount());

		/* Checkout */
		// CartVO cart = b.checkOut();
		/* OrderCreation */

	}

	@ExceptionHandler({ BookNotFoundException.class })
	public void handleException(String e, Exception e1) {
		System.out.println(e1);
	}

	private AuthenticationManager getAuthenticationManager() {
		return this.authenticationManager;
	}

	public EntityManager getEntityManager() {
		return this.em;
	}

	public CatalogService getService() {
		return this.service;
	}

	public UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}

	public BookStoreCartService getBookStoreService() {
		return this.bookStoreCartService;
	}

}

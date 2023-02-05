package com.visulabs.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.visulabs.bookstore.exception.ItemNotFoundException;

@Service
public class BookStoreCartService {

	@Autowired
	private BookStoreShoppingCart cart;

	public void addItemToCart(BookClientDTO aBook) {
		addorUpdateItemToCart(aBook, 1);
	}

	public BookStoreShoppingCart getShoppingCart() {
		return this.cart;
	}

	public List<CartItem> getCartItems() {
		return getShoppingCart().getCartItemList();
	}

	public Double getCartTotal() {
		return getShoppingCart().getCartTotal() - getShoppingCart().getTotalCartDiscount();
	}

	private CartItem getCartItemIfExisting(BookClientDTO p) {
		return getCartItems().stream().filter(cartItem -> cartItem.getABook().getBookId().equals(p.getBookId()))
				.findAny().orElse(null);
	}

	public void removeItemFromCart(BookClientDTO book) {
		getShoppingCart().removeCartItemMatchingBookId(book.getBookId());

	}

	public void updateItemQuantity(BookClientDTO aBook, int quantity) {
		addorUpdateItemToCart(aBook, quantity);
	}

	public void addorUpdateItemToCart(BookClientDTO aBook, int quantity) {
		CartItem c = getCartItemIfExisting(aBook);
		if (c != null) {
			// c.addMoreQuantities(quantity);
			c.setQuantity(quantity);
		}
		else {
			this.getShoppingCart().addItemToCart(new CartItem(aBook, 1, null));
		}
	}

	public void emptyCart() {
		this.cart = new BookStoreShoppingCart();
	}

}

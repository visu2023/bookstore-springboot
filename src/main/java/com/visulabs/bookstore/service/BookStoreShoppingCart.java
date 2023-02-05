package com.visulabs.bookstore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class BookStoreShoppingCart {

	private List<CartItem> cartItemList;

	private Double cartTotal;

	private Double totalCartDiscount;

	public void addItemToCart(CartItem c) {
		getCartItemList().add(c);
		refreshTotal();
		refreshDiscount();
	}

	public List<CartItem> getCartItemList() {
		if (this.cartItemList != null)
			return cartItemList;
		else {
			cartItemList = new ArrayList<>();
			return cartItemList;
		}
	}

	public void removeCartItemMatchingBookId(Long bookId) {
		setCartItemList(this.getCartItemList().stream()
				.filter(cartItem -> !cartItem.getABook().getBookId().equals(bookId)).collect(Collectors.toList()));
		refreshTotal();
		refreshDiscount();

	}

	private void refreshTotal() {
		this.setCartTotal(getCartItemList().stream()
				.map(cartItem -> CostCalculator.calculateCost(cartItem.getABook(), cartItem.getQuantity()))
				.reduce(0.0, Double::sum));
	}

	private void refreshDiscount() {
		this.setTotalCartDiscount(getCartItemList().stream()
				.map(cartItem -> CostCalculator.calculateDiscount(cartItem.getABook(), cartItem.getQuantity()))
				.reduce(0.0, Double::sum));
	}

}

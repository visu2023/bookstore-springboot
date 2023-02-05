package com.visulabs.bookstore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItem {

	private BookClientDTO aBook;

	private Integer quantity;

	private String selected;

	public void incrementQuantity() {
		this.quantity = quantity + 1;
	}

	public void addMoreQuantities(int quantity) {
		this.quantity = this.quantity + 1;
	}

}

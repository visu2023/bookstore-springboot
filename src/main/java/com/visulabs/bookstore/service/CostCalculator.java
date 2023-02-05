package com.visulabs.bookstore.service;

import com.visulabs.bookstore.service.BookClientDTO;

public class CostCalculator {

	public static Double calculateCost(BookClientDTO p, Integer quantity) {
		return p.getSalePrice() * quantity;
	}

	public static Double calculateDiscount(BookClientDTO p, Integer quantity) {
		return p.getDiscount() * quantity;
	}

}

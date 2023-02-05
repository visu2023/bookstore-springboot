package com.visulabs.bookstore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartVO {

	List<BookClientDTO> books;

	Double cost;

	Double totalDiscount;

	Double cartTotal;

}

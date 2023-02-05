package com.visulabs.bookstore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookClientDTO {

	String bookName;

	String isbn;

	Double cost;

	String bookDescription;

	String imageURL;

	Double discount;

	Double salePrice;

	Long bookId;

}

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
public class BookVO {

	String bookName;

	String isbn;

	Double cost;

	String bookDescription;

	String imageURL;

	Double discount;

	Long bookId;

}

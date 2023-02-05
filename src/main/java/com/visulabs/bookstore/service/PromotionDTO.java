package com.visulabs.bookstore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PromotionDTO {

	Long promotionId;

	Long bookId;

	String promotionName;

	Double discount;

}

package com.visulabs.bookstore.service;

import com.visulabs.bookstore.entity.Book;
import com.visulabs.bookstore.entity.BookPromotion;
import com.visulabs.bookstore.entity.BookPromotionRepository;
import com.visulabs.bookstore.entity.CatalogRepository;
import com.visulabs.bookstore.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromotionService {

	private BookPromotionRepository promotionRepository;

	public PromotionService(BookPromotionRepository promotionRepository) {
		this.promotionRepository = promotionRepository;
	}

	public PromotionDTO findPromotionById(Long id) throws BookNotFoundException {
		PromotionDTO promotion = promotionRepository.findPromotionByPromotionId(id);
		return promotion;
	}

	public void addAPromotion(PromotionDTO promotionDTO, String user) {
		this.promotionRepository.save(new BookPromotion(promotionDTO.getPromotionId(), promotionDTO.getDiscount(),
				promotionDTO.getBookId(), new Date(), user, promotionDTO.getPromotionName()));
	}

}

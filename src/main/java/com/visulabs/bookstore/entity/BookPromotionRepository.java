package com.visulabs.bookstore.entity;

import com.visulabs.bookstore.service.PromotionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookPromotionRepository
		extends JpaRepository<BookPromotion, Long>, JpaSpecificationExecutor<BookPromotion> {

	public PromotionDTO findPromotionByPromotionId(Long pid);

}
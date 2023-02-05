package com.visulabs.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BS_BOOK_PROMOTION")
@NamedQuery(name = "Promotion.findPromotionByPromotionId",
		query = "select b.promotionId,b.bookId,b.discount,b.promotionName from BookPromotion b where b.promotionId=:pid")
public class BookPromotion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PROMOTION_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long promotionId;

	@Column(name = "DISCOUNT")
	private Double discount;

	@Column(name = "BOOK_ID", nullable = false)
	@JoinColumn(name = "BOOK_ID", table = "BS_BOOK")
	private Long bookId;

	@Column(name = "CREATED", nullable = false)
	private Date created;

	@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;

	@Column(name = "PROMOTION_NAME", nullable = false)
	private String promotionName;

}

package com.visulabs.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BS_ORDER_DETAILS")
public class OrderDetails {

	@Id
	@Column(name = "ORDER_DETAIL_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")

	private Long orderDetailId;

	@Column(name = "ORDER_ID")
	@JoinColumn(name = "order_id", table = "bs_order")
	@NotNull
	private Long orderId;

	@Column(name = "BOOK_ID")
	@NotNull
	private Long bookId;

	@Column(name = "quantity")
	private int quantitu;

	@Column(name = "cost")
	private double cost;

}

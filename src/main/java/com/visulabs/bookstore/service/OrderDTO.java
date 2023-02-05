package com.visulabs.bookstore.service;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long orderId;

	private String orderName;

	private LocalDateTime orderDate;

	private Long customerId;

	private String created;

	private Long paymentId;

	private Double orderTotal;

	private Double discountApplied;

	private String orderStatus;

	private Integer totalQty;

	public OrderDTO(Double cartTotal, Integer totalQty, Double discountApplied) {
		this.orderTotal = cartTotal;
		this.totalQty = totalQty;
		this.discountApplied = discountApplied;
	}

	private String address1;

	private String address2;

	private String city;

	private String country;

	private String zip;

	private String paymentcard;

	private Integer cvv;

	private String email;

	private String expirydate;

}

package com.visulabs.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "BS_ORDER")
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ORDER_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long orderId;

	@Column(name = "ORDER_NAME", nullable = false)
	private String orderName;

	@Column(name = "ORDER_DATE", nullable = false)
	private Instant orderDate;

	@Column(name = "CUSTOMER_ID")
	private Long customerId;

	@Column(name = "CREATED", nullable = false)
	private String created;

	@Column(name = "PAYMENT_ID", nullable = false)
	private Long paymentId;

	@Column(name = "ORDER_TOTAL", nullable = false)
	private Double orderTotal;

	@Column(name = "DISCOUNT_APPLIED")
	private Double discountApplied;

	@Column(name = "ORDER_STATUS")
	private String orderStatus;

	@OneToMany(targetEntity = OrderDetails.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")

	List<OrderDetails> orderDetailsList;

}

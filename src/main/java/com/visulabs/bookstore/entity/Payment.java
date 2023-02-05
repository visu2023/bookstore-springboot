package com.visulabs.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "BS_PAYMENT")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Payment {

	@Id
	@Column(name = "PAYMENT_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long paymentId;

	@NotNull
	@Column(name = "CARD_NUMBER")
	private String cardNumber;

	@NotNull
	@Column(name = "EXPIRY_DATE")
	private String expriryDate;

	@NotNull
	@Column(name = "cvv")
	private int cvv;

	@Column(name = "ACTIVE")
	private Boolean active;

	@NotNull
	@Column(name = "CUSTOMER_ID")
	private Long customerId;

}

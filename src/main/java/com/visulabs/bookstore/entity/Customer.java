package com.visulabs.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "BS_CUSTOMER")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Customer {

	@Id
	@Column(name = "CUSTOMER_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long customerId;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "email")
	private String email;

	@Column(name = "address_1")
	private String address1;

	@Column(name = "address_2")
	private String address2;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	@Column(name = "zip")
	private String zip;

}

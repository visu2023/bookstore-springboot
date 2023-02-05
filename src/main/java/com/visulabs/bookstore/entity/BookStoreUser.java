package com.visulabs.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "BS_USER")
@Setter
@Getter
@NamedQuery(name = "BookStoreUser.findUserByUserName",
		query = "select u.userName,u.roles,u.email,u.customerId from BookStoreUser u where user_name=?1")
public class BookStoreUser {

	@Id
	@Column(name = "user_id")
	@SequenceGenerator(name = "user_id_generator", sequenceName = "user_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	private Long userId;

	@Column(name = "user_name")
	@NotNull
	private String userName;

	@Column
	@NotNull
	private String password;

	@Column
	@NotNull
	private Instant created;

	@Column
	@NotNull
	private String createdBy;

	@Column(name = "email")
	private String email;

	@Column(name = "CUSTOMER_ID")
	private Long customerId;

	@OneToMany(targetEntity = BookStoreUserRoles.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<BookStoreUserRoles> roles;

}

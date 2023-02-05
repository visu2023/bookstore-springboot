package com.visulabs.bookstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@Table(name = "BS_USER_ROLES")

public class BookStoreUserRoles {

	@Id
	@SequenceGenerator(name = "role_id_generator", sequenceName = "role_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
	@NotNull
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "role_name")
	@NotNull
	private String roleName;

	@NotNull
	@Column(name = "user_id")
	@JoinColumn(name = "user_id", table = "bs_user")
	private Long userId;

}

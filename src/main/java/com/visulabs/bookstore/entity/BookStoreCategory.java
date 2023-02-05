package com.visulabs.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Columns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "BS_CATEGORY")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookStoreCategory {

	@Id
	@Column(name = "CATEGORY_ID")
	@NotNull
	Long categoryId;

	@Column(name = "CATEGORY_NAME")
	@NotNull
	String categoryName;

	@Column(name = "CATEGORY_DESCRIPTION")
	@NotNull
	String categoryDesc;

	@Column(name = "CREATED")
	@NotNull
	Instant created;

	@Column(name = "CREATED_BY")
	@NotNull
	String createdBy;

	@JoinColumn(name = "CATEGORY_ID")
	@OneToMany(fetch = FetchType.EAGER)
	List<Book> books;

}

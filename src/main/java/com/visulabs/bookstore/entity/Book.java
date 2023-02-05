package com.visulabs.bookstore.entity;

import com.visulabs.bookstore.service.BookVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "BS_BOOK")
/*
 * @NamedQuery(name="Book.findBookByISBN",
 * query="select bookName,isbn,author,cost,bookDesc,imageURL, dicounts from Book b where isbn=?"
 * )
 *
 * @NamedNativeQuery(name="Book.findBooksByTitleOrDescription",
 * query="select book_name,isbn,cost,book_description,image_URL,discount from (bs_book b "
 * +
 * "outer join bs_book_promotion bp using b.book_id=bp.book_id) where (b.nook_name like ? OR b.book_description like ?)"
 * , resultSetMapping = "Mapping.BookVO")
 */

@NamedNativeQuery(name = "Book.findBookByIsbn",
		query = "" + "select book_name as bookName," + "isbn as isbn, " + "cost as cost, "
				+ "book_desc as bookDescription," + "image_URL imageURL ," + "discount  as discount,"
				+ "bs.book_id as bookId  "
				+ "from (bs_book b left join bs_book_promotion bs on b.book_id=bs.book_id) where b.isbn=:isbn",
		resultSetMapping = "Mapping.BookVO")

@NamedQuery(name = "Book.findBookByIsbn2", query = "select b from Book b where b.isbn=:isbn")
@NamedQuery(name = "Book.searchBookBytitleAndDesc", query = "select b from Book b "
		+ "where UPPER(b.bookName) like  CONCAT('%',UPPER(:qry),'%') or upper(b.bookDesc) like CONCAT('%',UPPER(:qry),'%') or isbn=:qry")
@NamedQuery(name = "Book.findBookByIsbn3",
		query = "select b.bookId,b.bookName,b.bookDesc,b.isbn,b.cost,b.discounts from Book b where b.isbn=:isbn")

@SqlResultSetMapping(name = "Mapping.BookVO", classes = @ConstructorResult(targetClass = BookVO.class, columns = {
		@ColumnResult(name = "bookName", type = String.class), @ColumnResult(name = "isbn", type = String.class),
		@ColumnResult(name = "cost", type = Double.class), @ColumnResult(name = "bookDescription", type = String.class),
		@ColumnResult(name = "imageURL", type = String.class), @ColumnResult(name = "discount", type = Double.class),
		@ColumnResult(name = "bookId", type = Long.class) }))
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BOOK_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long bookId;

	@Column(name = "BOOK_NAME", nullable = false)
	private String bookName;

	@Column(name = "ISBN", nullable = false)
	private String isbn;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "COST", nullable = false)
	private Double cost;

	@Column(name = "BOOK_DESC")
	private String bookDesc;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;

	@Column(name = "CREATED", nullable = false)
	private Date created;

	@Column(name = "CURRENCY")
	private String currency;

	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	@JoinColumn(name = "BOOK_ID")
	private List<BookPromotion> discounts;

}

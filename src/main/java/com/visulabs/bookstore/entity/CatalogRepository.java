package com.visulabs.bookstore.entity;

import org.springframework.data.jpa.repository.Query;
import com.visulabs.bookstore.service.BookVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	@Query(nativeQuery = true)
	public List<BookVO> findBookByIsbn(String isbn);

	public Book findBookByBookId(Long bid);

	public Book findBookByBookId(long l);

	public Book findBookByIsbn2(String isbn);

	List<Book> searchBookBytitleAndDesc(String qry);

}
package com.visulabs.bookstore.service;

import com.visulabs.bookstore.entity.Book;
import com.visulabs.bookstore.entity.BookPromotion;
import com.visulabs.bookstore.entity.CatalogRepository;
import com.visulabs.bookstore.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogService {

	private CatalogRepository catalogRepository;

	private BookToBookClientDTOMapper mapper = new BookToBookClientDTOMapper();

	public CatalogService(CatalogRepository catalogRepository) {
		this.catalogRepository = catalogRepository;
	}

	public List<BookVO> findBookByISBN(String isbn) throws BookNotFoundException {
		List<BookVO> books = catalogRepository.findBookByIsbn(isbn);
		if (books == null) {
			throw new BookNotFoundException(isbn);
		}
		System.out.println(books.stream().map(bookVO -> bookVO.getDiscount()).reduce(Double::sum));
		// return new BookVOToClientMapper().map()
		return books;
	}

	public BookClientDTO findBookByISBN2(String isbn) throws BookNotFoundException {
		Book book = catalogRepository.findBookByIsbn2(isbn);
		if (book == null) {
			throw new BookNotFoundException(isbn);
		}
		return new BookToBookClientDTOMapper().mapbookEntity(book);
	}

	public List<BookClientDTO> searchBookBytitleAndDesc(String queryString) {
		List<Book> books = catalogRepository.searchBookBytitleAndDesc(queryString);
		return books.stream().map(book -> mapper.mapbookEntity(book)).collect(Collectors.toList());
	}

	public List<BookClientDTO> findAllBooks() {
		List<Book> books = catalogRepository.findAll();
		return books.stream().map(book -> mapper.mapbookEntity(book)).collect(Collectors.toList());
	}

	public Book findBookById(long l) {
		Book book = catalogRepository.findBookByBookId(l);
		return book;
	}

	public void addABook(BookClientDTO clientRequest, String user) {
		Book bookEntity = new Book(null, clientRequest.getBookName(), clientRequest.getIsbn(), "guest",
				clientRequest.getCost(), clientRequest.getBookDescription(), clientRequest.getImageURL(), user,
				new Date(), null, null);
		this.catalogRepository.save(bookEntity);
	}

	public void updateABook(BookClientDTO clientRequest, String user) {
		Book b = catalogRepository.findBookByBookId(clientRequest.getBookId());
		b.setAuthor("guest");
		b.setBookDesc(clientRequest.getBookDescription());
		b.setCost(clientRequest.getCost());
		b.setBookName(clientRequest.getBookName());
		b.setCreated(new Date());
		b.setIsbn(clientRequest.getIsbn());
		b.setImageUrl(clientRequest.getImageURL());
		b.setCreatedBy(user);
		catalogRepository.save(b);
	}

	public void deleteBook(Long bookId) throws BookNotFoundException {
		try {
			this.catalogRepository.delete(catalogRepository.findBookByBookId(bookId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new BookNotFoundException("bookId", bookId.toString());
		}
	}

	public void addPromotionForBook(BookPromotionDTO b, String username) {
		Book book = this.catalogRepository.findBookByBookId(b.getBookId());
		book.getDiscounts().add(new BookPromotion());
		this.catalogRepository.save(book);

	}

	class BookToBookClientDTOMapper {

		public BookClientDTO mapbookVO(BookVO bookVO, Double totalDiscount) {
			return new BookClientDTO(bookVO.getBookName(), bookVO.getIsbn(), bookVO.getCost(),
					bookVO.getBookDescription(), bookVO.getImageURL(), totalDiscount, bookVO.getCost() - totalDiscount,
					bookVO.getBookId());
		}

		public BookClientDTO mapbookEntity(Book book) {
			Optional<Double> totalDiscount = sumAndGetTotalDiscounts(book);
			return new BookClientDTO(book.getBookName(), book.getIsbn(), book.getCost(), book.getBookDesc(),
					book.getImageUrl(), totalDiscount.orElse(0.0), book.getCost() - totalDiscount.orElse(0.0),
					book.getBookId());
		}

		private Optional<Double> sumAndGetTotalDiscounts(Book book) {
			return book.getDiscounts().stream().map(bookPromotion -> bookPromotion.getDiscount()).reduce(Double::sum);
		}

	}

}

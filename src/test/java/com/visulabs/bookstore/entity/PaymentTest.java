package com.visulabs.bookstore.entity;

import com.visulabs.bookstore.BookstoreApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@SpringBootTest
class PaymentTest {

	@Autowired
	private CatalogRepository paymentRepository;

	@Autowired
	private PaymentRepository rep;

	@Autowired
	private EntityManager em;

	@Test
	public void shouldPersistBookEntitu() {
//		Book book1 = new Book(null, "Sunny Days", "p1008", "G", 12.0, "Autobography of G", null, "admin", new Date(),
//				"USD", null);
//		Book book2 = new Book(null, "My Experiment with Trutg", "p2023", "G", 18.0, "Autobography of Gndhi	", null,
//				"admin", new Date(), "USD", null);
//
//		paymentRepository.save(book1);
//		paymentRepository.save(book2);
//		Payment p1 = new Payment(null, "12", "12", 11, true, 1L);
//		rep.save(p1);
		Iterable tutorials = rep.findAll();

		assertThat(tutorials).asList().hasSize(13);

	}

}
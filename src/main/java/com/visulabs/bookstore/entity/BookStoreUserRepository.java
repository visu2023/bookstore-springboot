package com.visulabs.bookstore.entity;

import com.visulabs.bookstore.entity.BookStoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreUserRepository extends JpaRepository<BookStoreUser, Long> {

	public BookStoreUser findByUserName(String userName);

}

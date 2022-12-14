package com.digitalbook.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalbook.book_service.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select book from Book book where book.active = true and book.category like ?1 and book.author like ?2 and book.price <= ?3 and book.publisher like ?4")
    List<Book> findAllBooksByParams(String category , String author , Double price , String publisher);

	List<Book> findByAuthor(String author);
}

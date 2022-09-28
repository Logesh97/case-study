package com.digitalbook.book_service.repository;

import com.digitalbook.book_service.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select book from Book book where book.active = true and book.category = ?1 and book.author = ?2 and book.price <= ?3 and book.publisher = ?4")
    List<Book> findAllBooksByParams(String category , String author , Double price , String publisher);
}

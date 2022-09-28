package com.digitalbook.book_service.service;

import com.digitalbook.book_service.entity.Book;
import com.digitalbook.book_service.entity.Purchase;
import com.digitalbook.book_service.exception.BookException;
import com.digitalbook.book_service.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<Book> findBooksByParams(String category , String author , Double price , String publisher) throws BookException {
        if(category != null && author != null && price  != null && publisher != null){
            throw new BookException("Check with parameters passed!!");
        }
        return bookRepository.findAllBooksByParams(category, author , price , publisher);
    }

    public Long buyBook(Purchase purchase) throws BookException {
        Optional<Book> book = bookRepository.findById(purchase.getBookId());
        if(!book.isPresent()){
        	throw new BookException("Book is not present");
        }
        purchase.setPurchaseId((long) Math.random());
        return purchase.getPurchaseId();
    }

	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}
	
	public Book save(Book book) {
		bookRepository.save(book);
		return book;
	}
}

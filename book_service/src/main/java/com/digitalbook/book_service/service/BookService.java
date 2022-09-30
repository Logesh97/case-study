package com.digitalbook.book_service.service;

import com.digitalbook.book_service.entity.Book;
import com.digitalbook.book_service.entity.Purchase;
import com.digitalbook.book_service.exception.BookException;
import com.digitalbook.book_service.repository.BookRepository;
import com.digitalbook.book_service.repository.PurchaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    PurchaseRepository purchaseRepository;

    public List<Book> findBooksByParams(String category , String author , Double price , String publisher) throws BookException {
//        if(category != null && author != null && price  != null && publisher != null){
//            throw new BookException("Check with parameters passed!!");
//        }
        return bookRepository.findAllBooksByParams(category, author , price , publisher);
    }

    public Long buyBook(Purchase purchase) throws BookException {
        Optional<Book> book = bookRepository.findById(purchase.getBookId());
        if(!book.isPresent() && !book.get().isActive()){
        	throw new BookException("Book is not present");
        }
        Purchase purchased = purchaseRepository.save(purchase);
        System.out.println(purchased);
//        purchase.setPurchaseId((long) Math.random()*1000);
        return purchased.getPurchaseId();
    }

	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}
	
	public Book save(Book book) {
		bookRepository.save(book);
		return book;
	}

	public List<Book> fetchPurchasedBooks(String mailId) {
		List<Purchase> purchases = purchaseRepository.findByEmail(mailId);
		List<Book> books = purchases.stream().map(purchase -> bookRepository.findById(purchase.getBookId()).get()).collect(Collectors.toList());
		System.out.println(books);
		return books;
	}
}

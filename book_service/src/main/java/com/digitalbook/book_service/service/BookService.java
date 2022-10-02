package com.digitalbook.book_service.service;

import com.digitalbook.book_service.entity.Book;
import com.digitalbook.book_service.entity.Purchase;
import com.digitalbook.book_service.exception.BookException;
import com.digitalbook.book_service.repository.BookRepository;
import com.digitalbook.book_service.repository.PurchaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
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
		List<Book> books = purchases.stream().map(purchase -> bookRepository.findById(purchase.getBookId()).get()).filter(book -> book.isActive() == true).collect(Collectors.toList());
		System.out.println(books);
		return books;
	}

	public List<Book> fetchByMailAndBookId(String mailId, Long bookId) throws BookException {
		List<Purchase> purchases = purchaseRepository.findByEmail(mailId);
		List<Book> books = purchases.stream().map(purchase -> bookRepository.findById(purchase.getBookId()).get())
				.filter(book -> {
					return book.isActive() == true && book.getId().equals(bookId);
				}).collect(Collectors.toList());
		System.out.println(books);
		if(books.isEmpty()) {
			throw new BookException("No active books for the use!!");
		}
		return books;
	}

	public List<Book> fetchByMailAndPaymentId(String mailId, Long paymentId) {
		List<Purchase> purchases = purchaseRepository.findByEmail(mailId);
		List<Book> books = purchases.stream()
				.filter(purchase -> purchase.getPurchaseId().equals(paymentId))
				.map(purchase -> bookRepository.findById(purchase.getBookId()).get())
				.filter(book -> book.isActive()).collect(Collectors.toList());
		System.out.println(books);
		if(books.isEmpty()) {
			System.out.println("No active books for the use!!");
		}
		return books;
	}
 
	public String doRefund(String mailId, Long bookId) {
		Purchase purchased = purchaseRepository.findByEmail(mailId)
				.stream()
				.filter(purchase -> purchase.getBookId().equals(bookId))
				.collect(Collectors.toList()).get(0);
		
		Duration duration = Duration.between(purchased.getPurchasedDate(), LocalDateTime.now());
		if(duration.toHours() <= 24) {
			purchaseRepository.deleteById(purchased.getPurchaseId());
		} else {
			return "Refund time exceeded!!";
		}
		
		return "refunded";
	}
}

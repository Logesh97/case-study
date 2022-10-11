package com.digitalbook.book_service.service;

import com.digitalbook.book_service.entity.Book;
import com.digitalbook.book_service.entity.Purchase;
import com.digitalbook.book_service.exception.BookException;
import com.digitalbook.book_service.repository.BookRepository;
import com.digitalbook.book_service.repository.PurchaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    PurchaseRepository purchaseRepository;
    
    public static final String TOPIC = "digital-books";

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
	
	@KafkaListener(topics = TOPIC, groupId="group_id", containerFactory = "userKafkaListenerFactory")
	public void consumeBook(Book book) {
		System.out.println(book.getTitle() +" yet to be published..");
		save(book);
		System.out.println("Book saved in book service!!");
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
 
	public Map<String, String> doRefund(String mailId, Long bookId) {
		Map<String ,String> resultMap = new HashMap<>();
		List<Purchase> purchased = purchaseRepository.findByEmail(mailId)
				.stream()
				.filter(purchase -> purchase.getBookId().equals(bookId))
				.collect(Collectors.toList());
		
		for(Purchase purchase : purchased) {
			Duration duration = Duration.between(purchase.getPurchasedDate(), LocalDateTime.now());
			if(duration.toHours() <= 24) {
				purchaseRepository.deleteById(purchase.getPurchaseId());
			} else {
				resultMap.put("result", "Refund time limit exceeded!!");
				return resultMap;
			}
		}
		resultMap.put("result", "sucess");
		return resultMap;
	}

	public String editBook(Book book) throws BookException {
		Optional<Book> updatedBookOptional = bookRepository.findById(book.getId());
		Book updatedBook;
		if(updatedBookOptional.isPresent()) {
			updatedBook = updatedBookOptional.get();
			if(book.getTitle() != null) {
				updatedBook.setTitle(book.getTitle());
			}
			if(book.getCategory() != null) {
				updatedBook.setCategory(book.getCategory());
			}
			if(book.getContent() != null) {
				updatedBook.setContent(book.getContent());
			}
			if(book.getLogo() != null) {
				updatedBook.setLogo(book.getLogo());
			}
			if(book.getPrice() != null) {
				updatedBook.setPrice(book.getPrice());
			}
			if(book.getPublisher() != null) {
				updatedBook.setPublisher(book.getPublisher());
			}
			if(book.getPublishedDate() != null) {
				updatedBook.setPublishedDate(book.getPublishedDate());
			}
			if(book.isActive() != updatedBook.isActive()) {
				updatedBook.setActive(book.isActive());
			}
			
			bookRepository.save(updatedBook);
			
		}else {
			throw new BookException("Book not found!!");
		}
		return "Book updated successFully!!";
	}
	
	@KafkaListener(topics = "Notification", groupId="group_id", containerFactory = "userKafkaListenerFactory")
	public void ConsumeNotification(Book book) {
		System.out.println(book.getTitle() +" received from kafka..");
		Book notifyBook = bookRepository.findById(book.getId()).get();
		if(book.isActive() != notifyBook.isActive()) {
			List<Purchase> purchaseList = purchaseRepository.findByBookId(book.getId());
			purchaseList.forEach(purchase -> {
				System.out.println("Notification mail sent to "+purchase.getEmail());
			});
		}
		System.out.println("Notification sent!!");
	}

	public List<Book> fetchByAuthor(String authorId) {
		return bookRepository.findByAuthor(authorId);
	}
	
}

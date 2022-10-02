package com.digitalbook.reader_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.digitalbook.reader_service.model.Book;


@FeignClient("digital-book")
public interface BookServiceClient {
	@GetMapping("/api/v1/digitalbooks/books")
	List<Book> findAll(@RequestHeader("Authorization") String BearerToken);
	
	@GetMapping("/api/v1/digitalbooks/books/fetchByMail")
	List<Book> findPurchasedBooks(@RequestParam String mailId);

	@GetMapping("/api/v1/digitalbooks/books/fetchByMailAndBookId")
	List<Book> getPurchasedBookByID(@RequestParam String mailId, @RequestParam Long bookId);
	
	@GetMapping("/api/v1/digitalbooks/books/fetchByMailAndPaymentId")
	List<Book> getBooksByPurchaseId(@RequestParam String mailId, @RequestParam Long paymentId);

	@GetMapping("/api/v1/digitalbooks/books/refund")
	String doRefund(@RequestParam String mailId, @RequestParam Long bookId);
}

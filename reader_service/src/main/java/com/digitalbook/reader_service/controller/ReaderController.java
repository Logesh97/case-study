package com.digitalbook.reader_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.reader_service.model.Book;
import com.digitalbook.reader_service.servcie.ReaderService;

@RestController
@RequestMapping("/api/v1/digitalbooks/readers")
public class ReaderController {
	
	@Autowired
	ReaderService readerService;
	
	@GetMapping("/{mailId}/books")
	public List<Book> getPurchasedBooks(@PathVariable String mailId){
		return readerService.getPurchasedBooks(mailId);
	}
	
	@GetMapping("/{mailId}/books/{bookId}")
	public List<Book> getPurchasedBookByID(@PathVariable String mailId ,
			@PathVariable Long bookId){
		return readerService.getPurchasedBookByID(mailId,bookId);
	}
	

	@PostMapping("/{mailId}/books")
	public List<Book> getBooksByPurchaseId(@PathVariable String mailId , 
			@RequestBody Map<String,String> paymentMap){
		Long paymentId = Long.parseLong(paymentMap.get("payment_id"));
		return readerService.getBooksByPurchaseId(mailId,paymentId);
	}
	

	@PostMapping("/{mailId}/books/{bookId}/refund")
	public String doRefund(@PathVariable String mailId ,
			@PathVariable Long bookId){
		return readerService.doRefund(mailId,bookId);
	}

}

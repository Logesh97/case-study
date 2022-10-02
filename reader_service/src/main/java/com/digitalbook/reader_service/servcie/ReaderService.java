package com.digitalbook.reader_service.servcie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.reader_service.client.BookServiceClient;
import com.digitalbook.reader_service.model.Book;

@Service
public class ReaderService {
	
	@Autowired
	BookServiceClient bookServiceClient;

	public List<Book> getPurchasedBooks(String mailId) {
		return bookServiceClient.findPurchasedBooks(mailId);
	}

	public List<Book> getPurchasedBookByID(String mailId, Long bookId) {
		return bookServiceClient.getPurchasedBookByID(mailId , bookId);
	}

	public List<Book> getBooksByPurchaseId(String mailId, Long paymentId) {
		return bookServiceClient.getBooksByPurchaseId(mailId , paymentId);
	}

	public String doRefund(String mailId, Long bookId) {
		return bookServiceClient.doRefund(mailId, bookId);
	}


}

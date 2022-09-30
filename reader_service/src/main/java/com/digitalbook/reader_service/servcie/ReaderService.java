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
	

}

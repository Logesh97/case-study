package com.digitalbook.reader_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}

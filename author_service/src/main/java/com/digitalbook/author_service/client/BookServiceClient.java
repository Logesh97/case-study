package com.digitalbook.author_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.digitalbook.book_service.entity.Book;

@FeignClient("digital-book")
public interface BookServiceClient {
	@GetMapping("/api/v1/digitalbooks/books")
	List<Book> findAll(@RequestHeader("Authorization") String BearerToken);
	
	@PostMapping("/api/v1/digitalbooks/books/create-book")
	Book createBook(@RequestBody Book book);
	
	@PutMapping("/api/v1/digitalbooks/books/edit-book")
	String editBook(@RequestBody Book book);
}

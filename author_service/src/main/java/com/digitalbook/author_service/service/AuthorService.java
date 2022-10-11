package com.digitalbook.author_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.digitalbook.author_service.client.BookServiceClient;
import com.digitalbook.author_service.entity.Author;
import com.digitalbook.author_service.repository.AuthorRepository;
import com.digitalbook.author_service.utils.PasswordUtil;
import com.digitalbook.book_service.entity.Book;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;

import io.swagger.v3.core.util.Json;


@Service
public class AuthorService {
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	BookServiceClient bookServiceClient;
	
	@Autowired
	PasswordUtil passwordUtil;
	
	@Autowired
	KafkaTemplate<String, Book> kafkaTemplate;
	
	public static final String TOPIC = "digital-books";

	public Author signup(Author author) throws Exception {
		if(author.getAuthorId()!= null) {
			Optional<Author> authorByID = authorRepository.findById(author.getAuthorId());
			if(authorByID.isPresent()) {
				throw new Exception("Author already registered");
			}
		}
		author.setPassword(passwordUtil.encodePassword(author.getPassword()));
		return authorRepository.save(author);
	}

	public Author findByUsername(String username) {
		return authorRepository.findByUsername(username);
	}

	public List<Author> findAllAuthors() {
		return authorRepository.findAll();
	}

	public Long createBook(Long authorId, Book book) throws Exception {
		try {
			
			if(authorId == null) {
				throw new Exception("author not found");
			}
			if(book == null) {
				throw new Exception("book content must be there!!");
			}
			Optional<Author> author = authorRepository.findById(authorId);
			if(author.isPresent()) {
				book.setAuthor(author.get().getUsername());			
			} else {
				throw new Exception("author not found!!");
			}
			book.setId((long)(Math.floor(Math.random()*100)));
			kafkaTemplate.send(TOPIC, book);
			System.out.println("book created successfully!!");
			return book.getId();
		}catch(Exception exc) {
			throw exc;
		}
	}

	public  Map<String, String> editBook(Long authorId, Long bookId, Book book) {
		try {
			Map<String,String> resultMap = new HashMap<>();
			book.setAuthor(authorRepository.findById(authorId).get().getUsername());
			book.setId(bookId);
			bookServiceClient.editBook(book);
			kafkaTemplate.send("Notification", book);
			resultMap.put("result", "success");
			return resultMap;
		} catch(Exception ex) {
			return null;
		}
	}

}

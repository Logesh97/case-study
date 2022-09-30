package com.digitalbook.author_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.author_service.client.BookServiceClient;
import com.digitalbook.author_service.entity.Author;
import com.digitalbook.author_service.model.Book;
import com.digitalbook.author_service.repository.AuthorRepository;
import com.digitalbook.author_service.utils.PasswordUtil;


@Service
public class AuthorService {
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	BookServiceClient bookServiceClient;
	
	@Autowired
	PasswordUtil passwordUtil;

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
		Book createdBook = bookServiceClient.createBook(book);
		System.out.println(createdBook);
		return createdBook.getId();
	}

}

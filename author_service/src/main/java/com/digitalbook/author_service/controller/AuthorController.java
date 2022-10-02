package com.digitalbook.author_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.author_service.entity.Author;
import com.digitalbook.author_service.model.JwtResponse;
import com.digitalbook.author_service.service.AuthorService;
import com.digitalbook.author_service.utils.JwtTokenUtil;
import com.digitalbook.book_service.entity.Book;

@RestController
@RequestMapping("/api/v1/digitalbooks/author")
@CrossOrigin
public class AuthorController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@GetMapping()
	public List<Author> getAuthors(){
		return authorService.findAllAuthors();
	}
	
	@PostMapping("/{authorId}/books")
	public Long createBook(@PathVariable Long authorId , @RequestBody Book book) throws Exception {
		
		return authorService.createBook(authorId , book);
		
	}
	
	@PutMapping("/{authorId}/books/{bookId}")
	public String editBook(@PathVariable Long authorId ,
			@PathVariable Long bookId , 
			@RequestBody Book book) throws Exception {
		return authorService.editBook(authorId ,bookId, book);	
	}
	
	@PostMapping("/signup")
	public Author signup(@RequestBody Author author) throws Exception {
		System.out.println(author.getUsername());
		return authorService.signup(author);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody Author author) throws Exception {
		authenticate(author.getUsername(), author.getPassword());
		final Author authorByUsername = authorService.findByUsername(author.getUsername());
		final String token = jwtTokenUtil.generateToken(authorByUsername);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}

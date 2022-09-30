package com.digitalbook.author_service.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalbook.author_service.entity.Author;
import com.digitalbook.author_service.repository.AuthorRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// find user from database where user = :username
// userRepo.findByUsername(username);// username, password, roles
		Author author = authorRepository.findByUsername(username);
		return author;
	}
}

package com.digitalbook.author_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.digitalbook.author_service.entity.Author;
import com.digitalbook.author_service.service.AuthorService;

@EnableFeignClients
@SpringBootApplication
public class AuthorServiceApplication implements CommandLineRunner{
	
	@Autowired
	AuthorService authorRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthorServiceApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		authorRepository.signup(new Author("logesh","logesh","logesh@mail.com"));
		authorRepository.signup(new Author("kavitha","kavitha","logesh@mail.com"));
	}

}

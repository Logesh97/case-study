package com.digitalbook.author_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbook.author_service.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

	Author findByUsername(String username);

}

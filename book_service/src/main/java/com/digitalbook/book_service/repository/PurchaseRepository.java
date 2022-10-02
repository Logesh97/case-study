package com.digitalbook.book_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbook.book_service.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	
	public List<Purchase> findByEmail(String mailId);

	public List<Purchase> findByBookId(Long id);
	
//	public void deleteByBookId(Long bookId);

}

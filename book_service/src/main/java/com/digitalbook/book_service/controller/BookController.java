package com.digitalbook.book_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.book_service.entity.Book;
import com.digitalbook.book_service.entity.Purchase;
import com.digitalbook.book_service.exception.BookException;
import com.digitalbook.book_service.service.BookService;

@RestController
@RequestMapping("api/v1/digitalbooks/books")
public class BookController {

    @Autowired
    BookService bookService;
    
    @GetMapping()
    public List<Book> getBooks()  {
    	return bookService.findAllBooks();
    }

    @GetMapping("/search")
    public List<Book> getBooksByParams(@RequestParam String category,
                                       @RequestParam String author,
                                       @RequestParam Double price,
                                       @RequestParam String publisher
    ) throws BookException {
        return bookService.findBooksByParams(category , author ,price, publisher );
    }

    @PostMapping("/buy")
    public Long buyBook(@RequestBody Purchase purchase) throws BookException {
        return bookService.buyBook(purchase);
    }
    
    @PostMapping("/create-book")
    public Book createBook(@RequestBody Book book) {
    	return bookService.save(book);
    }
    @PutMapping("/edit-book")
    public String editBook(@RequestBody Book book) throws BookException {
    	return bookService.editBook(book);
    }
    
    @GetMapping("/fetchByMail")
    public List<Book> fetchByMail(@RequestParam String mailId){
    	return bookService.fetchPurchasedBooks(mailId);
    	
    }
    @GetMapping("/fetchByMailAndBookId")
    public List<Book> fetchByMailAndBookId(@RequestParam String mailId , @RequestParam Long bookId) throws BookException{
    	return bookService.fetchByMailAndBookId(mailId , bookId);
    	
    }
    @GetMapping("/fetchByMailAndPaymentId")
    public List<Book> fetchByMailAndPaymentId(@RequestParam String mailId , @RequestParam Long paymentId) throws BookException{
    	return bookService.fetchByMailAndPaymentId(mailId , paymentId);
    	
    }
    @GetMapping("/refund")
    public String doRefund(@RequestParam String mailId , @RequestParam Long bookId) throws BookException{
    	return bookService.doRefund(mailId , bookId);
    	
    }

}

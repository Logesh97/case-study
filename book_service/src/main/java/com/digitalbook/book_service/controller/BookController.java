package com.digitalbook.book_service.controller;

import com.digitalbook.book_service.entity.Book;
import com.digitalbook.book_service.entity.Purchase;
import com.digitalbook.book_service.exception.BookException;
import com.digitalbook.book_service.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}

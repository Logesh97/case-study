package com.digitalbook.book_service.advices;

import com.digitalbook.book_service.exception.BookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookAdvice {

    @ExceptionHandler(BookException.class)
    public ResponseEntity<?> handleBookException(BookException bookException) {
        return new ResponseEntity<String>("BookException: "+bookException.getMessage(), HttpStatus.OK);
    }
}

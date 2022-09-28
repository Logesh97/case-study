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

//		errorRep...

//		return new ResponseEntity<ErrorMessage>(new ErrorMessage(
//				"MovieException: "+me.getMessage(),
//				me.getClass().toString(),
//				"Something bad happened, please try after some time"
//			), HttpStatus.OK);

        return new ResponseEntity<String>("BookException: "+bookException.getMessage(), HttpStatus.OK);
    }
}

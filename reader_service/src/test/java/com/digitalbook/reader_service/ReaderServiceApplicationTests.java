package com.digitalbook.reader_service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.digitalbook.reader_service.model.Book;
import com.digitalbook.reader_service.servcie.ReaderService;

@SpringBootTest
class ReaderServiceApplicationTests {
	
	@Autowired
	ReaderService readerService;

    @Test
    void testGetPurchasedBooks_case1() {
    	List<Book> expected = new ArrayList<Book>(); 
    	expected.add(
    			new Book("ps2.png","Ponniyin selvan - manimakudam" , "fiction" , 250.5 ,
    					"kavitha" , "Kavithalaya" , LocalDate.of(2022, 9, 29) , 
    					"podsdvzhvcjzvcvzcvzxcdfjdkjfkjsdfiug"
    					+ "adsiufgadsgggggggggggggggggggggggggggggggggggggggggggggg"
    					+ "gggggrtykuifyesidukjfghisduyfsidgfusdfisdafdhasfdh\\nsdf"
    					+ "hgasfdhgfahd", true)
    			);
    	Assertions.assertEquals(1, readerService.getPurchasedBooks("kavi.logesh@kavi.com").get(0).getId());
    	
    }
    
    @Test
    void testGetPurchasedBooks_case2() {
    	List<Book> expected = new ArrayList<Book>(); 
    	Book book = new Book("ps2.png","Ponniyin selvan - manimakudam" , "fiction" , 250.5 ,
				"kavitha" , "Kavithalaya" , LocalDate.of(2022, 9, 29) , 
				"podsdvzhvcjzvcvzcvzxcdfjdkjfkjsdfiug"
				+ "adsiufgadsgggggggggggggggggggggggggggggggggggggggggggggg"
				+ "gggggrtykuifyesidukjfghisduyfsidgfusdfisdafdhasfdh\\nsdf"
				+ "hgasfdhgfahd", true);
    	book.setId(1L);
    	expected.add(
    			book
    			);
    	Assertions.assertEquals(expected, readerService.getPurchasedBooks("kavi.logesh@kavi.com"));
    	
    }
    
    @Test
    void testGetPurchasedBooks_case3() {
    	List<Book> expected = new ArrayList<Book>(); 
    	Book book = new Book("ps2.png","Ponniyin selvan - manimakudam" , "fiction" , 250.5 ,
				"kavitha" , "Kavithalaya" , LocalDate.of(2022, 9, 29) , 
				"podsdvzhvcjzvcvzcvzxcdfjdkjfkjsdfiug"
				+ "adsiufgadsgggggggggggggggggggggggggggggggggggggggggggggg"
				+ "gggggrtykuifyesidukjfghisduyfsidgfusdfisdafdhasfdh\\nsdf"
				+ "hgasfdhgfahd", true);
    	book.setId(1L);
    	expected.add(
    			book
    			);
    	Assertions.assertNotEquals(expected.size(), readerService.getPurchasedBooks("kavi.logesh12@kavi.com").size());
    	
    }

}

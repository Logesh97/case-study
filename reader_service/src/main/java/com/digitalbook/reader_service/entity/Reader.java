package com.digitalbook.reader_service.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reader {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long readerId;
	private String mailId;
	private String name;
//	private List<Long> purchasedBooks;
	
	
	public Long getReaderId() {
		return readerId;
	}
	public Reader(String mailId, String name) {
	super();
	this.mailId = mailId;
	this.name = name;
}
	public void setReaderId(Long readerId) {
		this.readerId = readerId;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

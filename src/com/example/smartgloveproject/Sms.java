package com.example.smartgloveproject;

import java.util.Date;

public class Sms {
	private String message;
	private String number;
	private final Date date;
	private String receiver;
	private String contactName;
	
	public Sms(String phoneNumber, String message, Date date, String contactName) {
		this.number = phoneNumber;
		this.message = message;
		this.date = date;
		this.contactName = contactName;
	}
	
	public Sms(String phoneNumber, String message, Date date, String receiver, String contactName) {
		this.number = phoneNumber;
		this.message = message;
		this.date = date;
		this.receiver = receiver;
		this.contactName = contactName;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getNumber() {
		return number;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public String getContactName() {
		return contactName;
	}
}

package com.example.smartgloveproject;

import java.util.Date;

public class Sms {
	private String message;
	private String number;
	private final Date date;
	private String receiver;
	
	public Sms(String phoneNumber, String message, Date date) {
		this.number = phoneNumber;
		this.message = message;
		this.date = date;
	}
	
	public Sms(String phoneNumber, String message, Date date, String receiver) {
		this.number = phoneNumber;
		this.message = message;
		this.date = date;
		this.receiver = receiver;
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
}

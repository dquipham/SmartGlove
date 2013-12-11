package com.example.smartgloveproject;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Sms implements Parcelable {
	private String message;
	private String number;
	private final Date date;
	private String receiver;
	private String contactName;
	
	// parcelable-specific methods
	// necessary for passing around in intents
	public int describeContents() { return 0; }
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(message);
		out.writeString(number);
		out.writeSerializable(date);
		out.writeString(receiver);
		out.writeString(contactName);
	}
	
	public static final Parcelable.Creator<Sms> CREATOR = new Parcelable.Creator<Sms>() {
		public Sms createFromParcel(Parcel in) { return new Sms(in); }
		public Sms[] newArray(int size) { return new Sms[size]; }
	};
	
	private Sms(Parcel in) {
		message = in.readString();
		number = in.readString();
		date = (Date)in.readSerializable();
		receiver = in.readString();
		contactName = in.readString();
	}
	
	/** start functionality **/
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
	
	static Sms createSmsFromStrings(String phoneNumber, String message, String date, String contact) {
		Date newDate = new Date(Long.parseLong(date));
		return new Sms(phoneNumber, message, newDate, contact);
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

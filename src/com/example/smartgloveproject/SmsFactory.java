package com.example.smartgloveproject;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class SmsFactory {
	
	private final Context _context;
	
	private static final Uri SMS_CONTENT_URI = Uri.parse("content://sms");
	private static final String COLUMNS[] = new String[]{"person", "address", "body", "date", "type"};
	private static final String SORT_ORDER_LIMIT = "date DESC limit 30";
	
	public SmsFactory(Context baseContext) {
		_context = baseContext;
	}
	
	private ArrayList<Sms> getSmsByThreadId(int threadId, String condition) {
		String where = "thread_id = " + threadId;
		if (condition != null) // bad sql injection follows
			where += " and body LIKE '%" + condition + "%'";
		return getAllSms(where);
	}
	
	private ArrayList<Sms> getAllSms(String condition) {
		ArrayList<Sms> ret = new ArrayList<Sms>();
		
		Cursor c = _context.getContentResolver().query(SMS_CONTENT_URI, COLUMNS, condition, null, SORT_ORDER_LIMIT);
		if (c != null) {
			boolean hasData = c.moveToFirst();
			while (hasData) {
				boolean sent = c.getInt(c.getColumnIndex("type")) == 2;
				
				String addr = c.getString(c.getColumnIndex("address"));
				Date timestamp = new Date(Long.parseLong(c.getString(c.getColumnIndex("date"))));
				String message = c.getString(c.getColumnIndex("body"));
				
				Sms msg = new Sms(addr, message, timestamp, "me");
				ret.add(msg);
			}
			c.close();
		}
		
		return ret;
	}
}

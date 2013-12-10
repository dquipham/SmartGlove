package com.example.smartgloveproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List<Sms> smsList = new ArrayList<Sms>();
		Uri uri = Uri.parse("content://sms/inbox");
		Cursor c = getContentResolver().query(uri, null, null, null, null);
		startManagingCursor(c);
		
		if (c.moveToFirst()) {
			for (int i = 0; i < 10 && i < c.getCount(); i++) {
				String message = c.getString(c.getColumnIndexOrThrow("body"));
				String number  = c.getString(c.getColumnIndexOrThrow("address"));
				Date date = new Date(Long.parseLong(c.getString(c.getColumnIndexOrThrow("date"))));
				
				smsList.add(new Sms(number, message, date));
				c.moveToNext();
			}
		}
		
		c.close();
		
		setListAdapter(new ListAdapter(this, smsList));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Sms sms = (Sms)getListAdapter().getItem(position);
		Toast.makeText(getApplicationContext(), sms.getMessage(), Toast.LENGTH_LONG).show();
	}
	
	/* old method
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

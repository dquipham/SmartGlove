package com.example.smartgloveproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract.PhoneLookup;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import at.abraxas.amarino.Amarino;

public class MainActivity extends ListActivity {

	// DEVICE_ADDRESS must be exactly the MAC address of the bluetooth dongle
	private static final String TAG = "SmartGlove";
	private static final String DEVICE_ADDRESS = "00:06:66:64:45:DA";
	
	// avoid sending too much data
	final int DELAY = 1000;
	long lastChange;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// connect to the arduino bluetooth
		Amarino.connect(this, DEVICE_ADDRESS);
		
		List<Sms> smsList = new ArrayList<Sms>();
		Uri uri = Uri.parse("content://sms/inbox");
		Cursor c = getContentResolver().query(uri, null, null, null, null);
		startManagingCursor(c);
		
		if (c.moveToFirst()) {
			for (int i = 0; i < 20 && i < c.getCount(); i++) {
				String message = c.getString(c.getColumnIndexOrThrow("body"));
				String number  = c.getString(c.getColumnIndexOrThrow("address"));
				Date date = new Date(Long.parseLong(c.getString(c.getColumnIndexOrThrow("date"))));
				
				// see if we can't get the contact name
				Uri phoneUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
				Cursor phoneCursor = managedQuery(phoneUri, new String[] {PhoneLookup.DISPLAY_NAME}, null, null, null);
				String displayName = "Unknown";
				if (phoneCursor != null && phoneCursor.moveToFirst())
					displayName = phoneCursor.getString(0);
					
				smsList.add(new Sms(number, message, date, displayName));
				c.moveToNext();
			}
		}
		
		c.close();
		
		setListAdapter(new ListAdapter(this, smsList));
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		// can save state variables here, e.g.
		// PerferenceManager.getDefaultSharedPreferences(this).edit().putInt("red", red).commit();
		
		// close the background service
		Amarino.disconnect(this, DEVICE_ADDRESS);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		processNewTexts(intent);
	}
	
	private void processNewTexts(Intent intent) {
		Bundle b = intent.getExtras();
		ArrayList<Sms> newSms = b.getBundle("txtContainer")
									.getParcelableArrayList("txts");
		
		
		Log.d("new sms", "got messages (" + newSms.size() + ")");
	}

	public void rebuildList() {
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Sms sms = (Sms)getListAdapter().getItem(position);
		Toast.makeText(getApplicationContext(), sms.getMessage(), Toast.LENGTH_LONG).show();
		
		// send text to arduino, less delay
		if (System.currentTimeMillis() - lastChange > DELAY) {
			sendUpdate(sms);
			lastChange = System.currentTimeMillis();
		}
	}
	
	private void sendUpdate(Sms sms) {
		String contact = sms.getContactName();
		if (contact.equalsIgnoreCase("unknown")) contact = sms.getNumber();
		final String message = sms.getMessage();

		Amarino.sendDataToArduino(this, DEVICE_ADDRESS, 'p', contact);
		
		// instead of SystemClock.sleep(300), do the following
		final MainActivity thisAct = this;
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Amarino.sendDataToArduino(thisAct, DEVICE_ADDRESS, 'm', message);		
			}
		}, 300);		
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

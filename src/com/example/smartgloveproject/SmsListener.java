package com.example.smartgloveproject;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("new sms", "entered onRecieve()");
		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle bundle = intent.getExtras();
			ArrayList<Sms> txts = new ArrayList<Sms>();
			
			if (bundle != null) {
				Object[] pdus = (Object[])bundle.get("pdus");
				for (int i = 0; i < pdus.length; i++) {
					SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[i]);
					Sms txt = Sms.createSmsFromStrings(sms.getOriginatingAddress(),
													   sms.getMessageBody(), 
													   Long.toString(sms.getTimestampMillis()), 
													   sms.getOriginatingAddress());
					txts.add(txt);
					Log.d("new sms", "received text number " + (i+1));
				}
			}
			
			// send an intents package to the mainactivity so it knows of 
			// this update
			Log.d("new sms", "txts.size() = " + txts.size());
			if (txts.size() != 0) {
				// make sure MainActivity gets this
				Intent i = new Intent(context, MainActivity.class);
				
				// bundle the Sms objects into the intent
				Bundle b = new Bundle(txts.size());
				b.putParcelableArrayList("txts", txts);
				i.putExtra("txtContainer", b);
				
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				
				// send the message to MainActivity
				Log.d("new sms", "sending new texts to MainActivity");
				context.startActivity(i);
			}
		}
	}
}

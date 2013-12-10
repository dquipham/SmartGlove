package com.example.smartgloveproject;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<Sms> {
	private final Context context;
	private final List<Sms> smsList;

	public ListAdapter(Context context, List<Sms> smsList) {
		super(context, R.layout.activity_main, smsList);
		this.context = context;
		this.smsList = smsList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.activity_main, parent, false);
		TextView senderNumber = (TextView)rowView.findViewById(R.id.smsNumberText);
		// senderNumber.setText(smsList.get(position).getNumber());
		
		Sms thisTxt = smsList.get(position);
		senderNumber.setText(thisTxt.getContactName() + " (" + thisTxt.getNumber() + ")");
		return rowView;
	}
}

package com.thebitisland.locartor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.LinearLayout;

public class SaveLocationActivity extends Activity {

	Calendar cal;
	int minute, hour, day;
	ImageView imgFavorite;
	private static final String PREF_UNIQUE_DATE = "PREF_UNIQUE_DATE";
	private static final String PREF_UNIQUE_NOTES = "PREF_UNIQUE_NOTES";
	private static final String PREF_UNIQUE_LATITUDE = "PREF_UNIQUE_LATITUDE";
	private static final String PREF_UNIQUE_LONGITUDE = "PREF_UNIQUE_LONGITUDE";
	public double latitude;
	public double longitude;
	private Marker mMarker;
	private GoogleMap map;
	SharedPreferences preferences;
	Tools myTool;
	private static Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_location);
		context = getApplicationContext();
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);

		myTool = new Tools(latitude, latitude, mMarker, map);
		myTool.startLocation(context);

		Button Alarm = (Button) findViewById(R.id.alarmbut);
		Button saveButton = (Button) findViewById(R.id.button2);
		Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		Alarm.setTypeface(robotoLight);
		saveButton.setTypeface(robotoLight);
		final EditText mEdit=(EditText)findViewById(R.id.notes);
		
		saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
				String tag_alarm="Locartor";
				String nextAlarm = Settings.System.getString(getContentResolver(),
					    Settings.System.NEXT_ALARM_FORMATTED);
				String notes=mEdit.getText().toString();
				//String nextAlarm2 = android.provider.Settings.System.getString(getContentResolver(), android.provider.Settings.System.NEXT_ALARM_FORMATTED);
				longitude=myTool.getLongitude();
				latitude=myTool.getLatitude();
				Log.i(tag_alarm, nextAlarm);
				Editor editor = preferences.edit();
				editor.putString(PREF_UNIQUE_DATE, nextAlarm);
				editor.putString(PREF_UNIQUE_NOTES, notes);
				editor.putString(PREF_UNIQUE_LATITUDE,String.valueOf(latitude));
				editor.putString(PREF_UNIQUE_LONGITUDE, String.valueOf(longitude));
				editor.commit();				
				finish();
			}
		});
		
		Alarm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cal = new GregorianCalendar();
				cal.setTimeInMillis(System.currentTimeMillis());
				day = cal.get(Calendar.DAY_OF_WEEK);
				hour = cal.get(Calendar.HOUR_OF_DAY) + 1;
				minute = cal.get(Calendar.MINUTE);

				Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
				i.putExtra(AlarmClock.EXTRA_MESSAGE, "Locartor");
				i.putExtra(AlarmClock.EXTRA_HOUR, hour);
				i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
				startActivity(i);
				//finish();
			}
		});

		imgFavorite = (ImageView) findViewById(R.id.imageView1);
		imgFavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				open();
			}
		});

	}

	public void open() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			myTool = new Tools();
			Display display = getWindowManager().getDefaultDisplay();
			myTool.setBitmap(data,imgFavorite,display);
			
		}
	}

}

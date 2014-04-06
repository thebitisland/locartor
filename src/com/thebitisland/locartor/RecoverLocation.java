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
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class RecoverLocation extends Activity {

	Calendar cal;
	int minute, hour, day;
	ImageView imgFavorite;

	public double latitude;
	public double longitude;
	private Marker mMarker;
	private GoogleMap map;
	Tools mytool;
	private static Context context;
	private static final String PREF_UNIQUE_DATE = "PREF_UNIQUE_DATE";
	private static final String PREF_UNIQUE_NOTES = "PREF_UNIQUE_NOTES";
	private static final String PREF_UNIQUE_LATITUDE = "PREF_UNIQUE_LATITUDE";
	private static final String PREF_UNIQUE_LONGITUDE = "PREF_UNIQUE_LONGITUDE";
	SharedPreferences sharedPrefs;
	Tools myTool;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_location);
		context = getApplicationContext();

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);

		mytool = new Tools(latitude, latitude, mMarker, map);
		mytool.startLocation(context);

		String tag_alarm = "recover";
		sharedPrefs = getSharedPreferences(PREF_UNIQUE_DATE,
				Context.MODE_PRIVATE);
		String val = sharedPrefs.getString(PREF_UNIQUE_DATE, null);
		
		sharedPrefs = getSharedPreferences(PREF_UNIQUE_NOTES,
				Context.MODE_PRIVATE);
		String val2 = sharedPrefs.getString(PREF_UNIQUE_NOTES, null);
		
		sharedPrefs = getSharedPreferences(PREF_UNIQUE_LATITUDE,
				Context.MODE_PRIVATE);
		String val3 = sharedPrefs.getString(PREF_UNIQUE_LATITUDE, null);
		
		sharedPrefs = getSharedPreferences(PREF_UNIQUE_LONGITUDE,
				Context.MODE_PRIVATE);
		String val4 = sharedPrefs.getString(PREF_UNIQUE_LONGITUDE, null);
		
		if (val != null) {
			Log.i(tag_alarm, val);
		} else if (val2 != null) {
			Log.i(tag_alarm, val2);
		} else if (val3 != null) {
			Log.i(tag_alarm, val3);
		} else if (val4 != null) {
			Log.i(tag_alarm, val4);
		}

		
		
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

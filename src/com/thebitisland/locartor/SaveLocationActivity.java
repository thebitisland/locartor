package com.thebitisland.locartor;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class SaveLocationActivity extends Activity {

	Calendar cal;
	int minute, hour, day;

	private static final String PREF_UNIQUE_DATE = "PREF_UNIQUE_DATE";
	private static final String PREF_UNIQUE_NOTES = "PREF_UNIQUE_NOTES";
	private static final String PREF_UNIQUE_LATITUDE = "PREF_UNIQUE_LATITUDE";
	private static final String PREF_UNIQUE_LONGITUDE = "PREF_UNIQUE_LONGITUDE";

	ImageView takenImage;
	private GoogleMap map;
	SharedPreferences preferences;
	Tools myTool;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_location);

		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		// Map UI settings
		map.setMyLocationEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(false);

		// get manually adjusted position
		float manualLatitude = preferences.getFloat(PREF_UNIQUE_LATITUDE, 0);
		float manualLongitude = preferences.getFloat(PREF_UNIQUE_LONGITUDE, 0);

		Log.i("SharedPreferences", "outLat" + manualLatitude);
		Log.i("SharedPreferences", "outLon" + manualLongitude);

		// Centre camera on position
		LatLng position = new LatLng(manualLatitude, manualLongitude);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));

		// star location logic
		myTool = new Tools(map);
		myTool.startManualLocation();

		// Buttons and editText view declarations
		final EditText mEdit = (EditText) findViewById(R.id.addNotes);
		Button alarmButton = (Button) findViewById(R.id.alarmButton);
		Button saveLocationButton = (Button) findViewById(R.id.saveLocationButton);

		// custom typography
		Typeface robotoLight = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		alarmButton.setTypeface(robotoLight);
		saveLocationButton.setTypeface(robotoLight);

		/*
		 * saveLocationButton's listener: - save LatLng - save alarm's date&time
		 */
		saveLocationButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// Get Alarm's info
				String tag_alarm = "Locartor";
				String nextAlarm = Settings.System.getString(
						getContentResolver(),
						Settings.System.NEXT_ALARM_FORMATTED);
				Log.i(tag_alarm, nextAlarm);
				
				// Get notes
				String notes = mEdit.getText().toString();
				
				// Get LatLng
				float longitude = myTool.getLongitude();
				float latitude = myTool.getLatitude();

				// Save necessary values
				Editor editor = preferences.edit();
				editor.putString(PREF_UNIQUE_DATE, nextAlarm);
				editor.putString(PREF_UNIQUE_NOTES, notes);
				editor.putFloat(PREF_UNIQUE_LATITUDE, latitude);
				editor.putFloat(PREF_UNIQUE_LONGITUDE, longitude);
				editor.commit();

				finish();
			}
		});

		// alarmButton's listener: set alarm
		alarmButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				// Get current time and date
				cal = new GregorianCalendar();
				cal.setTimeInMillis(System.currentTimeMillis());
				day = cal.get(Calendar.DAY_OF_WEEK);
				hour = cal.get(Calendar.HOUR_OF_DAY) + 1;
				minute = cal.get(Calendar.MINUTE);

				// call alarm application
				Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
				i.putExtra(AlarmClock.EXTRA_MESSAGE, "Locartor");
				i.putExtra(AlarmClock.EXTRA_HOUR, hour);
				i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
				startActivity(i);
				// finish();
			}
		});

		// Image listener: take image and display it
		takenImage = (ImageView) findViewById(R.id.takenImage);
		takenImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// call camera application and wait for image
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});

	}

	// Custom code for displaying the image once is taken
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			// myTool = new Tools();
			Display display = getWindowManager().getDefaultDisplay();
			// display image
			myTool.setBitmap(data, takenImage, display);

		}
	}

}

package com.thebitisland.locartor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ShareActionProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends Activity {

	// Location variables
	public float latitude;
	public float longitude;
	private GoogleMap map;

	// SharedPreferences variables
	private static final String PREF_UNIQUE_LATITUDE = "PREF_UNIQUE_LATITUDE";
	private static final String PREF_UNIQUE_LONGITUDE = "PREF_UNIQUE_LONGITUDE";
	SharedPreferences preferences;

	// General and auxiliary variables
	Tools mytool;
	private static Context context;
	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get sharedPreferences handler
		context = getApplicationContext();
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		// Map UI options (myLocation and Zoom padding)
		map.setMyLocationEnabled(true);
		map.setPadding(0, 0, 0, 100);

		// Start Location and manualLocation process
		mytool = new Tools(map);
		mytool.startLocation(context);
		mytool.startManualLocation();

		// Save and Recover buttons
		Button saveButton = (Button) findViewById(R.id.saveButton);
		Button recoverButton = (Button) findViewById(R.id.recoverButton);
		// Buttons' typography
		Typeface robotoLight = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		saveButton.setTypeface(robotoLight);
		recoverButton.setTypeface(robotoLight);

		// saveButton's listener: save location and start activity
		saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Editor editor = preferences.edit();
				editor.putFloat(PREF_UNIQUE_LATITUDE, mytool.getLatitude());
				editor.putFloat(PREF_UNIQUE_LONGITUDE, mytool.getLongitude());
				editor.commit();

				Log.i("SharedPreferences", "inLat: " + mytool.getLatitude());
				Log.i("SharedPreferences", "inLon: " + mytool.getLongitude());

				Intent i = new Intent(getBaseContext(),
						SaveLocationActivity.class);

				startActivity(i);
				// finish();
			}
		});

		// recoverButton's listener: start activity
		recoverButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(getBaseContext(), RecoverLocation.class);
				startActivity(i);
				// finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu resource file.
		getMenuInflater().inflate(R.menu.main, menu);

		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);

		// Fetch and store ShareActionProvider
		mShareActionProvider = (ShareActionProvider) item.getActionProvider();
		mShareActionProvider
				.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);

		// Share message and location
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Your car is here: ");
		sendIntent.setType("text/plain");
		mShareActionProvider.setShareIntent(sendIntent);

		// Return true to display menu
		return true;
	}

}

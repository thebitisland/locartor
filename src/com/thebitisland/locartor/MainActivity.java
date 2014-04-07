package com.thebitisland.locartor;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends Activity {

	public float latitude;
	public float longitude;
	private Marker mMarker;
	private GoogleMap map;
	Tools mytool;
	private static Context context;
	private static final String PREF_UNIQUE_LATITUDE = "PREF_UNIQUE_LATITUDE";
	private static final String PREF_UNIQUE_LONGITUDE = "PREF_UNIQUE_LONGITUDE";
	SharedPreferences preferences;

	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = getApplicationContext();
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);

		map.setPadding(0, 0, 0, 100);

		mMarker = map.addMarker(new MarkerOptions().position(new LatLng(0, 0))
				.title("Marker"));

		mytool = new Tools(latitude, longitude, mMarker, map);
		mytool.startLocation(context);
		mytool.startManualLocation();

		Button saveButton = (Button) findViewById(R.id.saveButton);
		Typeface robotoLight = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		saveButton.setTypeface(robotoLight);

		Button recoverButton = (Button) findViewById(R.id.recoverButton);
		recoverButton.setTypeface(robotoLight);

		saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(getBaseContext(),
						SaveLocationActivity.class);
				Editor editor = preferences.edit();
				editor.putFloat(PREF_UNIQUE_LATITUDE,
						(float) mytool.getLatitude());
				Log.i("caja", mytool.getLatitude()+"");
				editor.putFloat(PREF_UNIQUE_LONGITUDE,
						(float) mytool.getLongitude());
				Log.i("caja", mytool.getLongitude()+"");
				editor.commit();

				startActivity(i);
				// finish();
			}
		});

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

		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Your car is here: ");
		sendIntent.setType("text/plain");

		mShareActionProvider.setShareIntent(sendIntent);

		// Return true to display menu
		return true;
	}

}

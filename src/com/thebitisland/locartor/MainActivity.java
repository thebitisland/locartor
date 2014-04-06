package com.thebitisland.locartor;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {

	public double latitude;
	public double longitude;
	private Marker mMarker;
	private GoogleMap map;
	Tools mytool;
	private static Context context;
	
	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = getApplicationContext();

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);

		map.setPadding(0, 0, 0, 100);
		
		mMarker = map.addMarker(new MarkerOptions().position(new LatLng(0, 0))
				.title("Marker"));
		
		mytool = new Tools(latitude, latitude, mMarker, map);
		mytool.startLocation(context);

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
				startActivity(i);
				// finish();
			}
		});
		
		recoverButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(getBaseContext(),
						RecoverLocation.class);
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
	    
	    mShareActionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
	    
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Your car is here: ");
		sendIntent.setType("text/plain");
	    
	    mShareActionProvider.setShareIntent(sendIntent);
		
	    // Return true to display menu
	    return true;
	}


	// Call to update the share intent
	private void setShareIntent(Intent shareIntent) {
		
	    if (mShareActionProvider != null) {
	        mShareActionProvider.setShareIntent(shareIntent);
	    }
	}
}

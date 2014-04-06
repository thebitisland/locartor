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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	public double latitude;
	public double longitude;
	private Marker mMarker;
	private GoogleMap map;
	Tools mytool;
	private static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = getApplicationContext();

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);

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

}

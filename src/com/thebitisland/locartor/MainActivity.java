package com.thebitisland.locartor;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	private LocationManager locationManager;

	public double latitude;
	public double longitude;
	private Marker mMarker;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);

		mMarker = map.addMarker(new MarkerOptions().position(new LatLng(0, 0))
				.title("Marker"));

		startLocation();

	}

	public void startLocation() {
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {

				/* Retrieve current position */
				latitude = location.getLatitude();
				longitude = location.getLongitude();

				addLocation(location);
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

		};

		locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
				locationListener, null);
		locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
				locationListener, null);

		map.setOnCameraChangeListener(new OnCameraChangeListener() {
			public void onCameraChange(CameraPosition arg0) {
				map.clear();
				mMarker = map.addMarker(new MarkerOptions().position(
						arg0.target).icon(
						BitmapDescriptorFactory.fromResource(R.drawable.empty)));

				/*
				 * Testing cameraListener Context context =
				 * getApplicationContext(); CharSequence text =
				 * arg0.target.toString(); int duration = Toast.LENGTH_SHORT;
				 * 
				 * Toast toast = Toast.makeText(context, text, duration);
				 * toast.show();
				 */

			}
		});

	}

	public void addLocation(Location location) {

		LatLng position = new LatLng(location.getLatitude(),
				location.getLongitude());

		map.animateCamera(CameraUpdateFactory.newLatLng(position));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));
	}

}

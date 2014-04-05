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

public class MainActivity extends Activity {

	private LocationManager locationManager;

	public double latitude;
	public double longitude;
	private Marker mMarker;
	private Marker mMarkerManual;
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

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
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
			}
		});
		
	}

	public void addLocation(Location location) {

		LatLng position = new LatLng(location.getLatitude(),
				location.getLongitude());
		//mMarker.setPosition(position);
		
		map.animateCamera(CameraUpdateFactory.newLatLng(position));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));
	}

}

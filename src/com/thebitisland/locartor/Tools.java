package com.thebitisland.locartor;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tools  {
	
	
	private static LocationManager locationManager;

	public static double latitude;
	public static double longitude;
	private static Marker mMarker;
	private static GoogleMap map;
	
	public Tools ( double latitude, double longitude, Marker mMarker,GoogleMap map){
		Tools.latitude=latitude;
		Tools.longitude=longitude;
		Tools.mMarker=mMarker;
		Tools.map=map;
	}
	
	public void startLocation(Context ctx) {
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) ctx
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

	public static void addLocation(Location location) {

		LatLng position = new LatLng(location.getLatitude(),
				location.getLongitude());

		map.animateCamera(CameraUpdateFactory.newLatLng(position));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));
	}

}

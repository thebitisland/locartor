package com.thebitisland.locartor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tools {

	private static LocationManager locationManager;

	public static float latitude;
	public static float longitude;
	private static Marker mMarker;
	private static GoogleMap map;

	public Tools(float latitude, float longitude, Marker mMarker,
			GoogleMap map) {
		Tools.latitude = latitude;
		Tools.longitude = longitude;
		Tools.mMarker = mMarker;
		Tools.map = map;
	}

	public Tools() {

	}

	public void startLocation(Context ctx) {
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {

				LatLng position = new LatLng(location.getLatitude(),
						location.getLongitude());

				map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,
						17.0f));
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

		};

		locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
				locationListener, null);
		locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
				locationListener, null);

	}

	public void startManualLocation() {
		map.setOnCameraChangeListener(new OnCameraChangeListener() {
			public void onCameraChange(CameraPosition arg0) {
				map.clear();
				mMarker = map.addMarker(new MarkerOptions().position(
						arg0.target).icon(
						BitmapDescriptorFactory.fromResource(R.drawable.empty)));

				latitude = (float) arg0.target.latitude;
				longitude = (float) arg0.target.longitude;
				
				/*
				 * Testing cameraListener Context context =
				 * getApplicationContext(); CharSequence text =
				 * arg0.target.toString(); int duration = Toast.LENGTH_SHORT;
				 * Toast toast = Toast.makeText(context, text, duration);
				 * toast.show();
				 */

			}
		});
	}

	public float getLatitude() {
		return latitude;

	}

	public float getLongitude() {
		return longitude;

	}

	public void setBitmap(Intent data, ImageView takenImage, Display display) {
		Bitmap bp = (Bitmap) data.getExtras().get("data");
		takenImage.setImageBitmap(bp);
		int width = display.getWidth();
		int heigth = display.getHeight();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				width, heigth);
		takenImage.setLayoutParams(layoutParams);

		// Let's get the root layout and add our ImageView

		File outFile = new File(Environment.getExternalStorageDirectory(),
				"locartor.png");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(outFile);
			bp.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

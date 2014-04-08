package com.thebitisland.locartor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tools {

	// map variables
	private static LocationManager locationManager;
	public static float latitude;
	public static float longitude;
	Marker mMarker;
	private static GoogleMap map;

	public Tools(GoogleMap map) {
		Tools.map = map;
	}

	public Tools() {
	}

	// Basic location process
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

		// Just first location
		locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
				locationListener, null);
		locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
				locationListener, null);

	}

	// Manual location process
	public void startManualLocation() {
		map.setOnCameraChangeListener(new OnCameraChangeListener() {
			public void onCameraChange(CameraPosition arg0) {
				map.clear();
				/*
				 * add marker at new location. hint: empty because of the delay,
				 * the marker shown is a static image
				 */
				mMarker = map.addMarker(new MarkerOptions().position(
						arg0.target).icon(
						BitmapDescriptorFactory.fromResource(R.drawable.empty)));

				// Set current latitude&longitude at center of the camera
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

	public void getStreetName(final Context ctx, final TextView txtView)
			throws IOException {

		map.setOnCameraChangeListener(new OnCameraChangeListener() {
			public void onCameraChange(CameraPosition arg0) {

				// Set current latitude&longitude at center of the camera
				latitude = (float) arg0.target.latitude;
				longitude = (float) arg0.target.longitude;

				Geocoder geoCoder = new Geocoder(ctx);
				List<Address> matches;
				try {
					matches = geoCoder.getFromLocation(latitude, longitude, 1);
					Address bestMatch = (matches.isEmpty() ? null : matches
							.get(0));
					txtView.setText(bestMatch.getAddressLine(0) + ", "
							+ bestMatch.getLocality() + ", "
							+ bestMatch.getSubAdminArea());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	// get picture and save it
	public void setBitmap(Intent data, ImageView takenImage, Display display) {

		Bitmap bp = (Bitmap) data.getExtras().get("data");
		takenImage.setImageBitmap(bp);

		int width = display.getWidth();
		int heigth = display.getHeight();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				width, heigth);
		takenImage.setLayoutParams(layoutParams);

		// output file
		File outFile = new File(Environment.getExternalStorageDirectory(),
				"locartor.png");
		FileOutputStream fos;
		// try to save it
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

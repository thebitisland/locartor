package com.thebitisland.locartor;

import java.io.File;
import java.util.Calendar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecoverLocation extends Activity {

	Calendar cal;
	int minute, hour, day;
	ImageView imgFavorite;

	public double latitude;
	public double longitude;
	private Marker mMarker;
	private GoogleMap map;
	Tools mytool;
	private static Context context;
	private static final String PREF_UNIQUE_DATE = "PREF_UNIQUE_DATE";
	private static final String PREF_UNIQUE_NOTES = "PREF_UNIQUE_NOTES";
	private static final String PREF_UNIQUE_LATITUDE = "PREF_UNIQUE_LATITUDE";
	private static final String PREF_UNIQUE_LONGITUDE = "PREF_UNIQUE_LONGITUDE";
	SharedPreferences sharedPrefs;
	Tools myTool;
	Bitmap myBitmap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recover_location);
		context = getApplicationContext();

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);

		mytool = new Tools(latitude, latitude, mMarker, map);
		mytool.startLocation(context);

		String tag_alarm = "recover";
		sharedPrefs = getSharedPreferences(PREF_UNIQUE_DATE,
				Context.MODE_PRIVATE);
		String val = sharedPrefs.getString(PREF_UNIQUE_DATE, null);
		
		sharedPrefs = getSharedPreferences(PREF_UNIQUE_NOTES,
				Context.MODE_PRIVATE);
		String val2 = sharedPrefs.getString(PREF_UNIQUE_NOTES, null);
		
		sharedPrefs = getSharedPreferences(PREF_UNIQUE_LATITUDE,
				Context.MODE_PRIVATE);
		String val3 = sharedPrefs.getString(PREF_UNIQUE_LATITUDE, null);
		
		sharedPrefs = getSharedPreferences(PREF_UNIQUE_LONGITUDE,
				Context.MODE_PRIVATE);
		String val4 = sharedPrefs.getString(PREF_UNIQUE_LONGITUDE, null);
		
		if (val != null) {
			Log.e(tag_alarm, val);
		} else if (val2 != null) {
			Log.e(tag_alarm, val2);
		} else if (val3 != null) {
			Log.e(tag_alarm, val3);
		} else if (val4 != null) {
			Log.e(tag_alarm, val4);
		}

		open();
		
		
		imgFavorite = (ImageView) findViewById(R.id.imageView1);
		imgFavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CrearAlert(context);
			}
		});

		

	}

	public void open() {
		File outFile = new File(Environment.getExternalStorageDirectory(),
				"locartor.png");
		if(outFile.exists()){

		    myBitmap = BitmapFactory.decodeFile(outFile.getAbsolutePath());
		    ImageView imgFavorite = (ImageView) findViewById(R.id.imageView1);
		    imgFavorite.setImageBitmap(myBitmap);
		    

		}
	}

	
	 public void CrearAlert(Context ctx){
		 
		 final Dialog dialog = new Dialog(RecoverLocation.this);
			dialog.setContentView(R.layout.custom);
			dialog.setTitle("");
		    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// set the custom dialog components - text, image and button
			ImageView image = (ImageView) dialog.findViewById(R.id.image);
			image.setImageBitmap(myBitmap);
			int hei=image.getHeight();
			int lon=image.getWidth();
			
			if(hei>lon){
				Log.e("pepe", "entro aqui");
				Display display = getWindowManager().getDefaultDisplay();
				int width = display.getWidth();
				int heigth = display.getHeight();
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						1800, heigth);
				image.setLayoutParams(layoutParams);
				
			}

			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();
	    }



}

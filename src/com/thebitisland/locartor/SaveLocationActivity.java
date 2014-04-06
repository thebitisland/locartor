package com.thebitisland.locartor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SaveLocationActivity extends Activity {

	Calendar cal;
	int minute, hour, day;
	ImageView imgFavorite;

	public double latitude;
	public double longitude;
	private Marker mMarker;
	private GoogleMap map;
	Tools mytool;
	private static Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_location);
		context = getApplicationContext();

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);

		mytool = new Tools(latitude, latitude, mMarker, map);
		mytool.startLocation(context);

		Button Alarm = (Button) findViewById(R.id.alarmbut);
		Button saveButton = (Button) findViewById(R.id.button2);
		Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		Alarm.setTypeface(robotoLight);
		saveButton.setTypeface(robotoLight);
		
		Alarm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cal = new GregorianCalendar();
				cal.setTimeInMillis(System.currentTimeMillis());
				day = cal.get(Calendar.DAY_OF_WEEK);
				hour = cal.get(Calendar.HOUR_OF_DAY) + 1;
				minute = cal.get(Calendar.MINUTE);

				Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
				i.putExtra(AlarmClock.EXTRA_MESSAGE, "Locartor");
				i.putExtra(AlarmClock.EXTRA_HOUR, hour);
				i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
				startActivity(i);
				// finish();
			}
		});

		imgFavorite = (ImageView) findViewById(R.id.imageView1);
		imgFavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				open();
			}
		});

	}

	public void open() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			Bitmap bp = (Bitmap) data.getExtras().get("data");
			imgFavorite.setImageBitmap(bp);
			// imgFavorite.setScaleType(ImageView.ScaleType.MATRIX);
			// imgFavorite.getLayoutParams().height = 500;
			Display display = getWindowManager().getDefaultDisplay();
			int width = display.getWidth();
			int heigth = display.getHeight();
			// Resources r = getResources();
			// float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			// 80, r.getDisplayMetrics());
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					width, heigth);
			imgFavorite.setLayoutParams(layoutParams);

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

}

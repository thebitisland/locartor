package com.thebitisland.locartor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SaveLocationActivity extends Activity {

	Calendar cal;
	int minute, hour, day;
	ImageView imgFavorite;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondview);

		Button Alarm = (Button) findViewById(R.id.alarmbut);
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
		Bitmap bp = (Bitmap) data.getExtras().get("data");
		imgFavorite.setImageBitmap(bp);
		
		File outFile = new File(Environment.getExternalStorageDirectory(), "locartor.png");
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

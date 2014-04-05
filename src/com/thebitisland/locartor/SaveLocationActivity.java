package com.thebitisland.locartor;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SaveLocationActivity extends Activity {

	Calendar cal;
	int minute, hour, day;

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

		Button Cam = (Button) findViewById(R.id.cambut);
		Cam.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(getBaseContext(), CameraActivity.class);
				startActivity(i);
				// finish();
			}
		});

	}

}

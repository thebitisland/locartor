package com.thebitisland.locartor;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SecondView extends Activity {
	 
	 Calendar cal;
	 int minute, hour, day;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.secondview);
	 
	        Button stopAlarm = (Button) findViewById(R.id.alarmbut);
	        stopAlarm.setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        	cal = new GregorianCalendar();
	            	 cal.setTimeInMillis(System.currentTimeMillis());
	            	 day = cal.get(Calendar.DAY_OF_WEEK);
	            	 hour = cal.get(Calendar.HOUR_OF_DAY)+1;
	            	 minute = cal.get(Calendar.MINUTE);
	            	    
	                Intent i = new Intent(AlarmClock.ACTION_SET_ALARM); 
	                i.putExtra(AlarmClock.EXTRA_MESSAGE, "Locartor"); 
	                i.putExtra(AlarmClock.EXTRA_HOUR, hour); 
	                i.putExtra(AlarmClock.EXTRA_MINUTES, minute); 
	                startActivity(i);
	               //finish();
		        }
		    });  
	       
	       
	    }
	 
	  


}

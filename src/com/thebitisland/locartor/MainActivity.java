package com.thebitisland.locartor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView imgFavorite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button sav = (Button) findViewById(R.id.sav);
        sav.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	
            	    
                Intent i = new Intent(getBaseContext(),SecondView.class);  
                startActivity(i);
               //finish();
	        }
	    });  
       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
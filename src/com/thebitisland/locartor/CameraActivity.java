package com.thebitisland.locartor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CameraActivity extends Activity {
	
	ImageView imgFavorite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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

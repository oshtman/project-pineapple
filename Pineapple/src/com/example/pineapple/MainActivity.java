package com.example.pineapple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity {
	
	private final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
	
		
		
		
		scaleBitmaps();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startGame(View view){
		Intent intent = new Intent(this, GameActivity.class);
		
		startActivity(intent);
	}
	
	public void scaleBitmaps(){
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		int screenHeight = display.getHeight();
		int screenWidth = display.getWidth();
		Log.d(TAG, "Screen Width = " + screenWidth);
		Log.d(TAG, "Screen Height = " + screenHeight);
		
		double scaleX = screenWidth/155.;
		double scaleY = screenHeight/100.;
		Bitmap playBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bird), (int)(50*scaleX), (int)(50*scaleY), true);
		ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
		playButton.setImageBitmap(playBitmap);
	}

}

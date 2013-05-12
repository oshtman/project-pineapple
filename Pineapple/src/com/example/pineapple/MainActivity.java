package com.example.pineapple;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {

	private final String TAG = MainActivity.class.getSimpleName();
	private MenuPanel menuPanel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		menuPanel = new MenuPanel(this);

		setContentView(menuPanel);
	}

	public void onDestroy(){
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	public void onStop(){
		Log.d(TAG, "Stopping...");
		super.onStop();
	}

	public void onPause(){
		Log.d(TAG, "Pausing...");
		menuPanel.pause();
		super.onPause();
	}

	public void onResume(){
		Log.d(TAG, "Resuming...");
		menuPanel.resume();
		super.onResume();
	}

	@Override
	public void onBackPressed() { //Override so that the player cannot go to a previous activity
		menuPanel.back();
		Log.d(TAG, "Back button");		
	}
}

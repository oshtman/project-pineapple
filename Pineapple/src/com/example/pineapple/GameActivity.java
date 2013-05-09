package com.example.pineapple;

import com.example.pineapple.GamePanel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends BaseActivity {
	private final String TAG = GameActivity.class.getSimpleName();
	
	
	
	GamePanel gamePanel;
	int level = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Intent intent = getIntent();
		level = intent.getIntExtra(MenuPanel.LEVEL, 0);
		gamePanel = new GamePanel(this, level);
		setContentView(gamePanel);
		
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
		gamePanel.pause();
		super.onPause();
	}
	
	public void onResume(){
		Log.d(TAG, "Resuming...");
		gamePanel.resume();
		super.onResume();
	}
	
	@Override
	public void onBackPressed() { //Override so that the player cannot go to a previous activity
		Log.d(TAG, "Back button");
	}

}

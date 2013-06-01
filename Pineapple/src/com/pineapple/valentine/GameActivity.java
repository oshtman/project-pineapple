package com.pineapple.valentine;

import com.pineapple.valentine.GamePanel;
import com.pineapple.valentineGen.R;

import android.content.Intent;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends BaseActivity {
	
	
	
	GamePanel gamePanel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game_over);
		Intent intent = getIntent();
		int level = intent.getIntExtra(MenuPanel.LEVEL, 0);
		gamePanel = new GamePanel(this, level);
		setContentView(gamePanel);
	}

	public void onDestroy(){
		super.onDestroy();
	}
	
	public void onStop(){
		super.onStop();
	}
	
	public void onPause(){
		gamePanel.pause();
		super.onPause();
	}
	
	public void onResume(){
		gamePanel.resume();
		overridePendingTransition (0, 0);
		super.onResume();
		
	}
	
	@Override
	public void onBackPressed() { //Override so that the player cannot go to a previous activity
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

}

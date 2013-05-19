package com.pineapple.valentine;

import com.example.pineapple.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameOverActivity extends BaseActivity {
	private final String TAG = GameOverActivity.class.getSimpleName();
	private int failedLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game_over);
		failedLevel = getIntent().getExtras().getInt(GamePanel.LEVEL);
	}
	
	public void goToMain(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

	public void goToSameLevel(View view){
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(MenuPanel.LEVEL, failedLevel);
		startActivity(intent);
		this.finish();
	}
	
	@Override
	public void onBackPressed() { //Override so that the player cannot go to a previous activity
		Log.d(TAG, "Back button");
	}
}

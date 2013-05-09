package com.example.pineapple;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class LevelCompleteActivity extends BaseActivity {

	private final String TAG = LevelCompleteActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_complete);
		SharedPreferences settings = this.getSharedPreferences("gameSettings", Context.MODE_PRIVATE);
		int currentLevel = settings.getInt("currentLevel", 0);
		int completedLevel = getIntent().getExtras().getInt(GamePanel.LEVEL);
		
		if(completedLevel == currentLevel){
			Editor e = settings.edit();
			e.putInt("currentLevel", completedLevel+1);
			e.commit();
			Log.d(TAG, "You unlocked a new level!");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level_complete, menu);
		return true;
	}

	public void goToMain(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() { //Override so that the player cannot go to a previous activity
		Log.d(TAG, "Back button");
	}
}

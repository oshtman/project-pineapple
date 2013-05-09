package com.example.pineapple;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class GameOverActivity extends BaseActivity {
	private final String TAG = GameOverActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
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

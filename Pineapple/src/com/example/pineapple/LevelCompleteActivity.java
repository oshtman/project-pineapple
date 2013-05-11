package com.example.pineapple;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class LevelCompleteActivity extends BaseActivity {

	private final String TAG = LevelCompleteActivity.class.getSimpleName();
	private boolean newLevel;
	private int completedLevel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_level_complete);
		
		SharedPreferences settings = this.getSharedPreferences("gameSettings", Context.MODE_PRIVATE);
		int currentLevel = settings.getInt("currentLevel", 0);
		completedLevel = getIntent().getExtras().getInt(GamePanel.LEVEL);
		int[] scoreKill = getIntent().getExtras().getIntArray(GamePanel.SCORE_KILL);
		int time = getIntent().getExtras().getInt(GamePanel.TIME);
		int health = getIntent().getExtras().getInt(GamePanel.HEALTH);
		
		if(completedLevel == currentLevel){
			newLevel = true;
			Editor e = settings.edit();
			e.putInt("currentLevel", completedLevel+1);
			e.commit();
		}
		
		if(true){
			TextView dataText;
			dataText = (TextView) findViewById(R.id.normalsKilledText);
			dataText.setText(""+scoreKill[0]);
			dataText = (TextView) findViewById(R.id.ninjasKilledText);
			dataText.setText(""+scoreKill[1]);
			dataText = (TextView) findViewById(R.id.tanksKilledText);
			dataText.setText(""+scoreKill[2]);
			
			int milliSecs = time*MainThread.updateInterval;
			String mins = ((milliSecs/1000.)/60 >= 10)?""+(int)(milliSecs/1000.)/60:"0"+(int)(milliSecs/1000.)/60;
			String secs = ((milliSecs/1000.)%60 >= 10)?""+(int)(milliSecs/1000.)%60:"0"+(int)(milliSecs/1000.)%60;
			String millisecs = (milliSecs%1000 < 10)?"00"+(milliSecs%1000):(milliSecs%1000 < 100)?"0"+(milliSecs%1000):""+(milliSecs%1000);
			
			dataText = (TextView) findViewById(R.id.timeText);
			dataText.setText(mins+":"+secs+","+millisecs);
			
			dataText = (TextView) findViewById(R.id.healthText);
			dataText.setText(health + "%");
		}
		TextView newLevelText = (TextView) findViewById(R.id.newLevelText);
		
		if(newLevel){
			Log.d(TAG, "You unlocked a new level!");
			newLevelText.setVisibility(TextView.VISIBLE);
		} else {
			newLevelText.setVisibility(TextView.INVISIBLE);
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
	
	public void goToSameLevel(View view){
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(MenuPanel.LEVEL, completedLevel);
		startActivity(intent);
	}
	
	public void goToNextLevel(View view){
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(MenuPanel.LEVEL, completedLevel+1);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() { //Override so that the player cannot go to a previous activity
		Log.d(TAG, "Back button");
	}
}

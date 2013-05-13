package com.example.pineapple;

import java.util.HashMap;
import java.util.Map;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.scoreloop.client.android.core.controller.RequestController;
import com.scoreloop.client.android.core.controller.RequestControllerObserver;
import com.scoreloop.client.android.core.controller.ScoreController;
import com.scoreloop.client.android.core.model.Score;

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
		SharedPreferences localScores = this.getSharedPreferences("localScores", Context.MODE_PRIVATE);

		int difficulty = settings.getInt("difficulty", 0);
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
		TableLayout stats = (TableLayout) findViewById(R.id.tableLayout1);
		if(settings.getBoolean("scoring", false)){
			TextView dataText;
			dataText = (TextView) findViewById(R.id.normalsKilledText);
			dataText.setText(""+scoreKill[0]);
			dataText = (TextView) findViewById(R.id.ninjasKilledText);
			dataText.setText(""+scoreKill[1]);
			dataText = (TextView) findViewById(R.id.tanksKilledText);
			dataText.setText(""+scoreKill[2]);

			Log.d(TAG, ""+time);
			int seconds = (int)(time*MainThread.updateInterval/1000);
			int centiSecs = time*MainThread.updateInterval/10;
			String mins = (seconds/60 >= 10)?""+seconds/60:"0"+seconds/60;
			String secs = (seconds%60 >= 10)?""+seconds%60:"0"+seconds%60;
			String centisecs = (centiSecs%100 < 10)?"0"+(centiSecs%100):""+(centiSecs%100);

			dataText = (TextView) findViewById(R.id.timeText);
			dataText.setText(mins+":"+secs+","+centisecs);

			dataText = (TextView) findViewById(R.id.healthText);
			dataText.setText(health + "%");

			int score = 100 - centiSecs/100 + 1*scoreKill[0] + 1*scoreKill[1] + 3*scoreKill[2] + health;
			dataText = (TextView) findViewById(R.id.scoreText);
			dataText.setText(""+ score);
			stats.setVisibility(View.VISIBLE);

			Log.d(TAG, "Seconds: " + seconds + " Centisecs: " + centiSecs + " gives " + mins+":"+secs+","+centisecs);

			int localHighscore = localScores.getInt("score_"+difficulty+"_"+completedLevel, 0);
			if(score > localHighscore){ //New local highscore
				Log.d(TAG, "You beat your previous best!");
				
				final ImageButton button1 = (ImageButton)findViewById(R.id.button1);
				final ImageButton button2 = (ImageButton)findViewById(R.id.button2);
				final ImageButton button3 = (ImageButton)findViewById(R.id.button3);
				
				button1.setVisibility(View.INVISIBLE);
				button2.setVisibility(View.INVISIBLE);
				button3.setVisibility(View.INVISIBLE);
				
				
				//Upload highscore to server
				RequestControllerObserver observer = new RequestControllerObserver(){

					@Override
					public void requestControllerDidReceiveResponse(RequestController requestController) {
						ScoreController userController = (ScoreController)requestController;
						Log.d(TAG, "Score upload successful");
						button1.setVisibility(View.VISIBLE);
						button2.setVisibility(View.VISIBLE);
						button3.setVisibility(View.VISIBLE);
						//Upload score
					}

					@Override
					public void requestControllerDidFail(RequestController aRequestController, Exception anException) {
						Log.d(TAG, "Score connect failed");
						button1.setVisibility(View.VISIBLE);
						button2.setVisibility(View.VISIBLE);
						button3.setVisibility(View.VISIBLE);
						
					}
				};
				final ScoreController myScoreController = new ScoreController(observer);
				Score s = new Score((double)score, null);
				Map<String, Object> context = new HashMap<String, Object>();
				context.put("normals", scoreKill[0]);
				context.put("ninjas", scoreKill[1]);
				context.put("tanks", scoreKill[2]);
				context.put("mins", (int)(seconds/60));
				context.put("secs", (int)(seconds%60));
				context.put("cSecs", (int)((time*MainThread.updateInterval/10)%100));
				
				s.setMode(2*completedLevel+difficulty); 
				s.setContext(context);
				myScoreController.submitScore(s);
				
			}
		} else {
			stats.setVisibility(View.INVISIBLE);
		}
		ImageView newLevelText = (ImageView) findViewById(R.id.newLevelText);

		if(newLevel){
			newLevelText.setVisibility(ImageView.VISIBLE);
		} else {
			newLevelText.setVisibility(ImageView.INVISIBLE);
		}
	}
	
	public static void viewButtons(){
		
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

package com.example.pineapple;

import com.example.pineapple.GamePanel;

import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends BaseActivity {

	GamePanel gamePanel;
	int level = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		gamePanel = new GamePanel(this, level);
		setContentView(gamePanel);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	public void onPause(){
		super.onPause();
		gamePanel.pause();
	}

}

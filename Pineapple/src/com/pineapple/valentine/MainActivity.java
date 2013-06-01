package com.pineapple.valentine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;


@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {

	private final String TAG = MainActivity.class.getSimpleName();
	public MenuPanel menuPanel;
	public boolean loading = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		menuPanel = new MenuPanel(this);
		setContentView(menuPanel);
	}

	public void onDestroy(){
		super.onDestroy();
	}

	public void onStop(){
		super.onStop();
	}

	public void onPause(){
		menuPanel.pause();
		super.onPause();
	}

	public void onResume(){
		menuPanel.resume();
		overridePendingTransition (0, 0);
		super.onResume();
	}

	@Override
	public void onBackPressed() { //Override so that the player cannot go to a previous activity
		menuPanel.back();
	}

	public void requestName(String currentName){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Enter your desired name");
		alert.setMessage("This will be displayed to your rivals!");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setText(currentName);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String name = input.getText().toString();
				menuPanel.uploadUserName(name);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();
	}

	public void displayMessage(String title, String message){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(title);
		alert.setMessage(message);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});

		alert.show();
	}

	
}

package com.example.pineapple;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TermsOfServiceActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms_of_service);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.terms_of, menu);
		return true;
	}

}

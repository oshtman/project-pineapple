package com.pineapple.valentine;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.Log;

import com.example.pineapple.R;
import com.scoreloop.client.android.core.model.Client;

public class BaseApplication extends Application{

	@Override
	public void onCreate(){
		// initialize the client using the context and game secret
		Client.init(this, "rytSbvqboE/CABKJRZypE2HrATHBqPBUJQCox6UPpxKJIRGpQhtu1Q==", null);
		super.onCreate();
	}
	
}

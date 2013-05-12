package com.example.pineapple;

import com.scoreloop.client.android.core.model.Client;

import android.app.Application;

public class BaseApplication extends Application{

	@Override
	public void onCreate(){
		// initialize the client using the context and game secret
        Client.init(this, "rytSbvqboE/CABKJRZypE2HrATHBqPBUJQCox6UPpxKJIRGpQhtu1Q==", null);
		super.onCreate();
		
	}
}

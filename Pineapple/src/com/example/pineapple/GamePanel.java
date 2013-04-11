package com.example.pineapple;

import android.content.Context;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
	
	public GamePanel(Context context){
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
}

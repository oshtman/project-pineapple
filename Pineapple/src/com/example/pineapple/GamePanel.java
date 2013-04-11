package com.example.pineapple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
	
	private MainThread thread;
	
	public GamePanel(Context context){
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		
		thread = new MainThread(this.getHolder(), this);
	}
	
	//Method that gets called every frame to update the games state
	public void update(){
		
	}
	
	//Method that gets called to render the graphics
	public void render(Canvas canvas){
		canvas.drawColor(Color.BLUE);
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while(retry){
			try{
				thread.join();
				retry = false;
			} catch(InterruptedException e){
				
			}
		}
	}
}

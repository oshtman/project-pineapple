package com.example.pineapple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
	
	private MainThread thread;
	private Protagonist protagonist;
	
	
	public GamePanel(Context context){
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		protagonist = new Protagonist();
		
		thread = new MainThread(this.getHolder(), this);
	}
	
	//Method that gets called every frame to update the games state
	public void update(){
		
	}
	
	//Method that gets called to render the graphics
	public void render(Canvas canvas){
		canvas.drawColor(Color.BLUE);
		renderProtagonist(canvas);
	}
	
	public void pause(){
		thread.setRunning(false);
	}
	
	public void renderProtagonist(Canvas canvas){
		canvas.drawRect((float)protagonist.getXPos(), (float)protagonist.getYPos(), (float)protagonist.getXPos()+30, (float)protagonist.getYPos()+30, new Paint());
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

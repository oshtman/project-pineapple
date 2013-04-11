package com.example.pineapple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
	
	private MainThread thread;
	private Protagonist protagonist;
	private Ground ground;
	
	
	public GamePanel(Context context){
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		protagonist = new Protagonist();
		//Change this later
		int[] x = {0, 100, 200};
		int[] y = {100, 200, 100};
		ground = new Ground(x, y);
		thread = new MainThread(this.getHolder(), this);
	}
	
	//Method that gets called every frame to update the games state
	public void update(){
		
	}
	
	//Method that gets called to render the graphics
	public void render(Canvas canvas){
		canvas.drawColor(Color.BLUE);
		renderProtagonist(canvas);
		renderGround(canvas);
	}
	
	public void pause(){
		thread.setRunning(false);
	}
	
	public void renderProtagonist(Canvas canvas){
		canvas.drawRect((float)protagonist.getXPos(), (float)protagonist.getYPos(), (float)protagonist.getXPos()+30, (float)protagonist.getYPos()+30, new Paint());
	}
	
	public void renderGround(Canvas canvas){
		int length = ground.getLength();
		for(int i = 0; i < length-1; i++){
			Path p = new Path();
			p.moveTo(ground.getX(i), ground.getY(i));
			p.lineTo(ground.getX(i+1), ground.getY(i+1));
			p.lineTo(ground.getX(i+1), 900);
			p.lineTo(ground.getX(i), 900);
			p.lineTo(ground.getX(i), ground.getY(i));
			canvas.drawPath(p, new Paint());
		}
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

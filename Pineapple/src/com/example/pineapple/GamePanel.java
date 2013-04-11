package com.example.pineapple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
	private final String TAG = GamePanel.class.getSimpleName();
	private final int width = 155;
	private final int height = 100;
	private MainThread thread;
	private Protagonist protagonist;
	private Ground ground;
	private double scaleY, scaleX;
	
	
	public GamePanel(Context context){
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		protagonist = new Protagonist();
		//Change this later
		int[] x = {0, 100, 150};
		int[] y = {100, 80, 100};
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
			p.moveTo((int)(ground.getX(i)*scaleX), (int)(ground.getY(i)*scaleY));
			p.lineTo((int)(ground.getX(i+1)*scaleX), (int)(ground.getY(i+1)*scaleY));
			p.lineTo((int)(ground.getX(i+1)*scaleX), (int)(200*scaleY));
			p.lineTo((int)(ground.getX(i)*scaleX), (int)(200*scaleY));
			p.lineTo((int)(ground.getX(i)*scaleX), (int)(ground.getY(i)*scaleY));
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
		scaleY = (double)getHeight()/height;
		scaleX = (double)getWidth()/width;
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

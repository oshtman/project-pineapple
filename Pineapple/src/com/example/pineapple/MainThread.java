package com.example.pineapple;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.pineapple.GamePanel;

public class MainThread extends Thread{

	public final static String TAG = MainThread.class.getSimpleName();
	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;
	private boolean running;
	
	public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	public void setRunning(boolean flag){
		this.running = flag;
	}
	
	public void run(){
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
			long startTime = System.currentTimeMillis();
			gamePanel.update();
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					this.gamePanel.render(canvas);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			} 
			long endTime = System.currentTimeMillis();
			try {
				if(startTime - endTime > -40){
					Thread.sleep(40 + startTime - endTime);
				}
			} catch(InterruptedException e){}
		}
	}
}



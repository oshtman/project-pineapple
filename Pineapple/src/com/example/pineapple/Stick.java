package com.example.pineapple;

public class Stick {
	public final int LEFT = 1;
	public final int RIGHT = 2;
	private int x, y;
	private int angle;
	private final int radius;
	private boolean pointed;
	
	public Stick(int pos){
		if(pos == LEFT){
			x = 20;
		} else {
			x = 135;
		}
		y = 80;
		pointed = false;
		radius = 10;
	}
	
	//Happens when the player touches the screen
	public void handleTouch(int x, int y){
		int dx = x - this.x;
		int dy = y - this.y;
		double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		if(dist <= radius){
			//Calculate angle of the finger relative to the stick
			angle = (int)(Math.atan2(dy, dx)*180/Math.PI);
			//Set the sticks status to pointed
			pointed = true;
		}
	}
	
	public void release(){
		angle = 0;
		pointed = false;
	}
	
	
}

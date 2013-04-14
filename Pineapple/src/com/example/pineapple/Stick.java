package com.example.pineapple;

public class Stick {
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	private int x, y;
	private int angle;
	private final int radius;
	private boolean pointed;
	
	public Stick(int pos){
		if(pos == LEFT){
			x = 15;
		} else {
			x = 140;
		}
		y = 85;
		pointed = false;
		radius = 10;
	}
	
	//Happens when the player touches the screen
	public void handleTouch(int x, int y){
		int dx = x - this.x;
		int dy = y - this.y;
		double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		if(dist <= 2*radius){
			//Calculate angle of the finger relative to the stick
			angle = (int)(Math.atan2(-dy, dx)*180/Math.PI);
			if(angle < 0){
				angle += 360;
			}
			//Set the sticks status to pointed
			pointed = true;
		} else {
			release();
		}
	}
	
	public void release(){
		angle = 0;
		pointed = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public boolean isPointed() {
		return pointed;
	}

	public void setPointed(boolean pointed) {
		this.pointed = pointed;
	}

	public int getRadius() {
		return radius;
	}
	
	
}

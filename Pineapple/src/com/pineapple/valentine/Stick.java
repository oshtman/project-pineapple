package com.pineapple.valentine;

import android.util.Log;

public class Stick {
	private static final String TAG = Stick.class.getSimpleName();

	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	private int x, y;
	private double angle;
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
	public void handleTouch(double x, double y){
		double dx = x - this.x;
		double dy = y - this.y;
		double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		if(dist <= 3*radius){
			//Calculate angle of the finger relative to the stick
			angle = (Math.atan2(-dy, dx)*180/Math.PI);
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

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
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

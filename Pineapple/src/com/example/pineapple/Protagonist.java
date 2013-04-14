package com.example.pineapple;

import java.util.*;

import android.util.Log;

public class Protagonist {

	private static final String TAG = Protagonist.class.getSimpleName();
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;
	private int health;
	private int angleAim;
	private double jumpVel = -3;
	private double jumpAcc = 0.1;
	private double maxSpeed = 3;
	private double slideCoefficient = 0.8;
	private final int height = 20;
	private final int width = (int)(20/1.42); //Change 1.42 to ratio of bitmap

	// CONSTRUCTOR
	public Protagonist(double i,double j) {
		this.setXPos(i);
		this.setYPos(j);
		health = 100;
	}

	public Protagonist() {
		this.setXPos(75);
		this.setYPos(80);
		health = 100;
	}

	// GET AND SET METHODS
	// getmethods for pos, vel, acc
	public double getXPos() {
		return xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public double getXVel() {
		return xVel;
	}

	public double getYVel() {
		return yVel;
	}

	public double getXAcc() {
		return xAcc;
	}

	public double getYAcc() {
		return yAcc;
	}

	//setmethods for pos, vel, acc
	public void setXPos(double n) {
		xPos = n;
	}

	public void setYPos(double n) {
		yPos = n;
	}

	public void setXVel(double n) {
		xVel = n;
	}

	public void setYVel(double n) {
		yVel = n;
	}

	public void setXAcc(double n) {
		xAcc = n;
	}

	public void setYAcc(double n) {
		yAcc = n;
	}

	// get and setmethods for actionproperties
	public void setAim(int angle) {
		angleAim = angle;
	}

	public double getAim() {
		return angleAim;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	private double getJumpVel() {
		return jumpVel;
	}

	private void setJumpVel(double jumpVel) {
		this.jumpVel = jumpVel;
	}
	private double getJumpAcc() {
		return jumpAcc;
	}

	private void setJumpAcc(double jumpAcc) {
		this.jumpAcc = jumpAcc;
	}
	private double getMaxSpeed() {
		return maxSpeed;
	}

	private void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	private double getSlideCoefficient() {
		return slideCoefficient;
	}

	private void setSlideCoefficient(double slideCoefficient) {
		this.slideCoefficient = slideCoefficient;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	// ACTIONS
	//Protagonist is aiming
	public void aim(double angle) {
		double v[] = new double[360];
		for (int i=0; i < v.length - 1; i++) {
			v[i] = Math.PI/v.length*i;
		}
	}
	//Protagonist is abel to fire
	public void fire() {
	}

	//Protagonist lose health
	public int reduceHealth(int n) {
		setHealth(n-1);
		return health;
	}

	//Protagonist jump
	public void jump() {
		this.setYVel(this.getYVel() + this.getJumpVel() + this.getJumpAcc());
		Log.d(TAG, "Jump!!");
	}
	// ------------- KEEPING FEET ON THE GROUND (just for now)--------------- //
	//Protagonist down
	public void down() {
		this.setYPos(80);
		this.setYVel(0);
		this.setYAcc(0);
		Log.d(TAG, "Come down!!");
	}
	// ---------------------------------------------------------------------- //

	//Accelerating protagonist
	public void accelerate(double acc) { // acc = 0.2?
		this.setXVel(this.getXVel() + acc);
		if(Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() > 0) {
			this.setXVel(this.getMaxSpeed());
		} else if (Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() < 0) {
			this.setXVel(-this.getMaxSpeed());
		}
	}

	//Moving protagonist
	public void move() {
		this.setXPos(this.getXPos() + this.getXVel());
		this.setYPos(this.getYPos() + this.getYVel());
	}

	//Deaccelerate protagonist (if stick is not pointed)
	public void slowDown() {
		this.setXVel(this.getXVel()*slideCoefficient);
	}

	//Make action from stickAngle
	public void handleStick(double angle, double acc) {
		if (angle <= 45 || angle >= 315) {
			this.accelerate(acc);
		} else if (angle >= 135 && angle <= 225) {
			this.accelerate(-acc);
		} else if (angle > 45 && angle < 135) {
			this.jump();
		} else if (angle > 225 && angle < 315)
			this.down();
	}
	
	//Check if the protagonist is under the ground
	//If he is, then set him on top of it
	public void checkGround(Ground g){
		if(this.yPos + height/2 > g.getYFromX(this.xPos)){
			this.yPos = g.getYFromX(this.xPos)-height/2;
			this.yVel = 0;
			this.yAcc = 0;
		}
	}

}

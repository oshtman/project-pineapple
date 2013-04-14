package com.example.pineapple;

public class Bullet {

	private double xPos;
	private double yPos;
	private double xVel = 10;
	private double yVel;
	
	//CONSTRUCTOR
	public Bullet(double x, double y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	//GET and SETmethods
	public double getXPos() {
		return xPos;
	}

	public void setXPos(double xPos) {
		this.xPos = xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public void setYPos(double yPos) {
		this.yPos = yPos;
	}
	
	public double getXVel() {
		return xVel;
	}

	public void setXVel(double xVel) {
		this.xVel = xVel;
	}

	public double getYVel() {
		return yVel;
	}

	public void setYVel(double yVel) {
		this.yVel = yVel;
	}
	
	//Gravity on bullets
	public void gravity(double acc) {
		this.setYVel(this.getYVel() + acc);
	}

	
}

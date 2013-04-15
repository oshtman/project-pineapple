package com.example.pineapple;

public class Bullet {

	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double angle;
	private int radius;
	
	public Bullet(double x, double y, double angle, double bulletSpeed) {
		this.xPos = x;
		this.yPos = y;
		this.xVel = Math.cos(angle/180*Math.PI)*bulletSpeed;
		this.yVel = Math.sin(angle/180*Math.PI)*bulletSpeed;
		this.angle = angle;
		this.radius = 3;
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
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	//Moving bullet
	public void move() {
		this.setXPos(this.getXPos() + this.getXVel());
		this.setYPos(this.getYPos() + this.getYVel());
	}
	//Gravity on bullets
	public void gravity(double acc) {
		this.setYVel(this.getYVel() + acc);
	}
	
}

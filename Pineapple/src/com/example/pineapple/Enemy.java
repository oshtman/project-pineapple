package com.example.pineapple;

public class Enemy {

	private double xPos;
	private double yPos;
	private double height;
	private double length;
	//	private bitmap portrait;
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;
	private int health;

	//CONSTRUCTORS
	public Enemy(double i, double j) {
		this.setXPos(i);
		this.setYPos(j);
		setHealth(10);
	}

	public Enemy(){
		this.setXPos(100);
		this.setYPos(100);
		setHealth(10);
	}

	//GET AND SET
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}

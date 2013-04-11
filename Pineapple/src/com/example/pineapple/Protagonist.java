package com.example.pineapple;

public class Protagonist {

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

	// CONSTRUCTOR
	public Protagonist(double i,double j){
		this.setXPos(i);
		this.setYPos(j);
		health = 100;
	}

	public Protagonist(){
		this.setXPos(75);
		this.setYPos(25);
		health = 100;
	}

	// GET AND SET
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

	public void setAim(int angle) {

	}
	public double getAim() {
		return 8;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	// ACTIONS
	public void aim(){

	}
	public void fire(){

	}
	public int reduceHealth(int n) {
		setHealth(n-1);
		return health;
	}
	
	public void jump(double Vel, double Acc) {
		this.setYVel(Vel);
		this.setYAcc(Acc + this.getYAcc());
	}
	
	//INTE KLAAAR..
	public void move(double angle) {// angle från stickDirectionen!
		if (Math.abs(angle) < 45)
		this.setXAcc(1);
		this.setXVel(this.getXVel() + this.getXAcc());
		this.setXPos(this.getXPos() - this.getXVel());

		this.setYAcc(0); 
		this.setYVel(this.getYVel() + this.getXAcc());
		this.setYPos(this.getYPos() - this.getXVel());
		// ???
		// this.setXPos(this.getXPos() - this.getXVel() + this.getXAcc());
		// this.setYPos(this.getYPos() - this.getYVel() + this.getXAcc());
	}
}

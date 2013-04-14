package com.example.pineapple;

import java.util.*;

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
	private int angleAim;

	// CONSTRUCTOR
	public Protagonist(double i,double j) {
		this.setXPos(i);
		this.setYPos(j);
		health = 100;
	}

	public Protagonist() {
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

	// ACTIONS
	public void aim(double angle) {
		double v[] = new double[360];
		for (int i=0; i < v.length - 1; i++) {
			v[i] = Math.PI/v.length*i;
		}
	}

	public void fire() {
	}

	public int reduceHealth(int n) {
		setHealth(n-1);
		return health;
	}

	//OcksŒ testING
	public void jump(double vel, double acc) {
		if(Math.abs(vel - acc) < vel) {
			this.setYVel(vel + acc);
			this.setYPos(this.getYPos() - this.getYVel());
			jump(vel - acc, acc);
		} else { 
		}
	}


	//INTE KLAAAR..
	public void move(double angle, double vel, double acc) { // angle frŒn stickDirectionen!
		if (Math.abs(angle) < 45) {
			this.setXVel(this.getXVel() + 1);
			this.setXPos(this.getXPos() + this.getXVel());
			this.setYPos(this.getYPos() - this.getYVel());
		} else if (angle > 135 && angle < 225) {
			this.setXVel(this.getXVel() - 1);
			this.setXPos(this.getXPos() - this.getXVel());
			this.setYPos(this.getYPos() - this.getYVel());
		} else if (angle < 135 && angle > 45) {
			this.jump(vel,acc);
		}
	}
}


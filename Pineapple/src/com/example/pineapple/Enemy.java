package com.example.pineapple;

import java.util.ArrayList;

import android.util.Log;

public class Enemy {

	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;
	private double health;
	private GamePanel gp;
	private int height = 20;
	private int width = (int)(20/1.42); //Change 1.42 to ratio of bitmap
	private double scaleNinja;
	private double scaleTank;
	private double baseSpeed = 1;
	private boolean touchingGround;
	private double jumpVel = -6;
	private double jumpAcc = 0.4;
	private double maxSpeed = 3;
	private double slideCoefficient = 0.8;
	private double ninjaAcc;
	private double tankAcc;

	//CONSTRUCTORS
	public Enemy(double i, double j, GamePanel gp, int type) {
		//type 1 is normal
		if (type == 1) {
			setHealth(0.5);
		}
		//type 2 is ninja
		else if (type == 2) {
			this.setHealth(0.1);
			this.height = (int)(this.height*scaleNinja);
			this.width = (int)((20/1.42)*scaleNinja); //Change 1.42 to ratio of bitmap
			this.ninjaSpeed = 2*baseSpeed;
			this.maxSpeed = 2*maxSpeed;
			this.jumpVel = 2*jumpVel;
			this.jumpAcc = 2*jumpAcc;

		}
		//type 3 is tank
		else if (type == 3) {
			this.setHealth(1);
			this.height = (int)(this.height*scaleTank);
			this.width = (int)((20/1.42)*scaleTank); //Change 1.42 to ratio of bitmap

		}
		this.setXPos(i);
		this.setYPos(j);
	}

	//GET AND SET
	//Get and set enemy pos, vel, acc
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
	
	//Get and set enemy properties
	public int getHealth() {
		return health;
	}

	public void setHealth(double d) {
		this.health = d;
	}
	
	private double getMaxSpeed() {
		return maxSpeed;
	}

	//ENEMY ACTION
	//moving
	public void move() {
		this.xPos = this.getXPos() + this.getXVel();
		this.yPos = this.getYPos() + this.getYVel();
	}

	//Accelerating protagonist
	public void accelerate(double acc) { // acc = 0.2?
		this.setXVel(this.getXVel() + acc);
		if(Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() > 0) {
			this.setXVel(this.getMaxSpeed());
		} else if (Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() < 0) {
			this.setXVel(-this.getMaxSpeed());
		}
	}

	//Check if the enemy is under the ground
	//If enemy is, then set enemy on top of it
	public void checkGround(Ground g){
		if(this.yPos + height/2 > g.getYFromX(this.xPos)){
			this.yPos = g.getYFromX(this.xPos)-height/2;
			this.yVel = 0;
			this.yAcc = 0;
			touchingGround = true;
		}
	}

	//Check if enemy hit platform
	public void checkPlatform(ArrayList<Platform> al) {
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).spans(this.getXPos())) {
				//if head is in platform
				//Log.d(TAG, "Warning: Platform, platform!!");
				if (this.getYVel() < 0 && this.getYPos() - this.getHeight()/2 < al.get(i).getLowerYFromX(this.getXPos()) && this.getYPos() - this.getHeight()/2 > al.get(i).getUpperYFromX(this.getXPos())) {
					this.setYVel(-this.getYVel());
					Log.d(TAG, "Headache!!");
				} else {
					//if feet is in platform
					if (this.getYVel() > 0 && this.getYPos() + this.getHeight()/2 > al.get(i).getUpperYFromX(this.getXPos())) {
						if (this.getYPos() + this.getHeight()/2 < al.get(i).getLowerYFromX(this.getXPos())) {
							this.setYPos(al.get(i).getUpperYFromX(this.getXPos()) - this.getHeight()/2);
							this.setYVel(0);
							this.setYAcc(0);
							touchingGround = true;
							Log.d(TAG, "Standing strong!!");					
						}
					}
				}
			} //if making move towards edge of platform
			if (al.get(i).checkSide(this, -1) && getXPos() < al.get(i).getUpperX()[0] && getXPos() + getWidth()/2 > al.get(i).getUpperX()[0] && getXVel() > 0) {
				this.setXVel(0);
				this.setXPos(al.get(i).getUpperX()[0] - getWidth()/2);
			}
			if(al.get(i).checkSide(this, 1) && getXPos() > al.get(i).getUpperX()[al.get(i).getUpperX().length-1] && getXPos() - getWidth()/2 < al.get(i).getUpperX()[al.get(i).getUpperX().length-1] && getXVel() < 0){
				this.setXVel(0);
				this.setXPos(al.get(i).getUpperX()[al.get(i).getUpperX().length-1] + getWidth()/2);
			}
		}
	}

	//Let gravity work on enemy
	public void gravity(){
		this.setYVel(this.getYVel()+this.getJumpAcc());
	}


}

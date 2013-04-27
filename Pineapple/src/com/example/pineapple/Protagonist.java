package com.example.pineapple;

import java.util.*;

import android.util.Log;

public class Protagonist {

	private static final String TAG = Protagonist.class.getSimpleName();
	private final double slopeThreshold = 0.7; //How much slope it takes to move the protagonist
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;
	private double health;
	private double angleAim;
	private double jumpVel = -6;
	private double jumpAcc = 0.4;
	private double maxSpeed = 3;
	private double slideCoefficient = 0.8;
	private final int height = 20;
	private final int width = (int)(20/1.42); //Change 1.42 to ratio of bitmap
	private boolean touchingGround;
	private GamePanel gp;

	// CONSTRUCTOR
	public Protagonist(double i, double j, GamePanel gp) {
		this.setXPos(i);
		this.setYPos(j);
		this.health = 1;
		this.gp = gp;
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

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
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

	public boolean isTouchingGround() {
		return touchingGround;
	}

	public void setTouchingGround(boolean touchingGround) {
		this.touchingGround = touchingGround;
	}

	// ACTIONS
	//Protagonist is aiming
	public void aim(double angle) {
		this.angleAim = angle;
	}

	//Protagonist lose health
	public double reduceHealth(double n) {
		this.setHealth(getHealth()-n);
		return health;
	}

	//Protagonist jump
	public void jump() {
		//Perhaps a steep slope shouldn't allow jumping???
		touchingGround = false;
		this.setYVel(this.getYVel() + this.getJumpVel() + this.getJumpAcc());
		Log.d(TAG, "Jump!!");
	}
	// ------------- KEEPING FEET ON THE GROUND (just for now)--------------- //
	//Protagonist down
	public void down(Ground g) {
		this.setYPos(g.getYFromX(this.getXPos()));
		this.setYVel(0);
		this.setYAcc(0);
		Log.d(TAG, "Come down!!");
	}
	// ---------------------------------------------------------------------- //

	//Accelerating protagonist
	public void accelerate(double acc) { // acc = 0.2?
		this.setXVel(this.getXVel() + acc);
		if(Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() > 0) {//Double code, also in checkSLope
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
	public void handleLeftStick(double angle, double acc) {
		if (angle <= 45 || angle >= 315) {
			this.accelerate(acc);
		} else if (angle >= 135 && angle <= 225) {
			this.accelerate(-acc);
		} else if (angle > 45 && angle < 135 && this.isTouchingGround()) {
			this.jump();
		} else if (angle > 225 && angle < 315)
			this.down(gp.getGround());
	}

	public void checkSlope(Ground ground, ArrayList<Platform> platforms){
		if(touchingGround){ 
			if(getYPos()+getHeight()/2 - ground.getYFromX(getXPos()) > -5){ //On ground
				double slope = ground.getSlope(this.getXPos());
				if(Math.abs(slope) > slopeThreshold)
					setXVel(getXVel()+slope);
				//Log.d(TAG, "Standing on ground");
			} else { //On platform
				for(int i = 0; i < platforms.size(); i++){
					if((platforms.get(i).getUpperX()[0] <= getXPos() && platforms.get(i).getUpperX()[platforms.get(i).getUpperLength()-1] >= getXPos())){
						double slope = platforms.get(i).getSlope(this.getXPos());
						if(Math.abs(slope) > slopeThreshold){
							setXVel(getXVel()+slope);
							Log.d(TAG, "Standing on platform " + (i+1));
						}
					}

				}
			}
			
			//Check if the speed has to be reduced
			//This doesn't look good in game
			if(Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() > 0) { //Double code, also in accelerate
				this.setXVel(this.getMaxSpeed());
			} else if (Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() < 0) {
				this.setXVel(-this.getMaxSpeed());
			}
		}
	}

	//Check if the protagonist is under the ground
	//If he is, then set him on top of it
	public void checkGround(Ground g){
		if(this.yPos + height/2 > g.getYFromX(this.xPos)){
			this.yPos = g.getYFromX(this.xPos)-height/2;
			this.yVel = 0;
			this.yAcc = 0;
			touchingGround = true;
		}
	}

	//Check if protagonist hit platform
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


	//Let gravity work on protagonist
	public void gravity(){
		this.setYVel(this.getYVel()+this.getJumpAcc());
	}

}

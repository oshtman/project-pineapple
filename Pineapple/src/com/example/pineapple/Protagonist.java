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
	private final int height = 15;
	private final int width = (int)(height/1.5); //Change 1.42 to ratio of bitmap
	private boolean touchingGround;
	private GamePanel gp;
	private int stepCount;
	private final int numberOfSteps = 10;
	private int breathCount = 0;
	private final int breathMax = 20;
	private boolean facingRight = true;
	private boolean overPlatform = false;
	private boolean invincible;
	private int invincibilityCount;
	private final int maxInvincibilityCount = 25;
	private boolean readyToJump = true;
	private boolean dashBonus = false;
	private boolean abelToPerformDash = false;
	//------------------------------------------------------------------------------------------------//
	// CONSTRUCTOR
	public Protagonist(double i, double j, GamePanel gp) {
		this.setXPos(i);
		this.setYPos(j);
		this.health = 1;
		this.gp = gp;
		this.stepCount = 0;
	}
	//------------------------------------------------------------------------------------------------//
	//HOW TO MAKE PROTAGONIST MOVE
	//Moving protagonist
	public void move() {
		this.setXPos(this.getXPos() + this.getXVel());
		this.setYPos(this.getYPos() + this.getYVel());
	}

	//Accelerating protagonist
	public void accelerate(double acc) { // acc = 0.2?
		this.setXVel(this.getXVel() + acc);
		if(Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() > 0) {//Double code, also in checkSLope
			this.setXVel(this.getMaxSpeed());
		} else if (Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() < 0) {
			this.setXVel(-this.getMaxSpeed());
		}
	}

	//Deaccelerate protagonist (if stick is not pointed)
	public void slowDown() {
		this.setXVel(this.getXVel()*slideCoefficient);
	}

	//Make action from stickAngle
	public void handleLeftStick(double angle, double acc) {
		if(!readyToJump)
			Log.d(TAG, "Not ready");
		if (angle <= 45 || angle >= 315) {
			this.accelerate(acc);
			step(1);
		} else if (angle >= 135 && angle <= 225) {
			this.accelerate(-acc);
			step(1);
		} else if (angle > 45 && angle < 135 && this.isTouchingGround()) {
			if(readyToJump) //If the protagonist isn't standing in a steep slope
				this.jump();
		} else if (angle > 225 && angle < 315)
			this.down(gp.getGround(), gp.getPlatforms());
	}
	//------------------------------------------------------------------------------------------------//
	//ACTIONS
	//Protagonist is aiming
	public void aim(double angle) {
		this.angleAim = angle;
	}

	//Protagonist lose health
	public double reduceHealth(double damage) {
		this.setHealth(this.getHealth()-damage);
		return this.health;
	}

	//Protagonist jump
	public void jump() {
		touchingGround = false;
		this.setYVel(this.getYVel() + this.getJumpVel() + this.getJumpAcc());
		Log.d(TAG, "Jump!!");
		abelToPerformDash = true;
	}

	//Protagonist dash
	public void down(Ground g, ArrayList<Platform> platforms) {
		double startHeight = this.getYPos();
		this.checkOverPlatform(platforms);
		for(int i = 0; i < platforms.size(); i++){
			if (platforms.get(i).spans(this.getXPos()) && platforms.get(i).getUpperYFromX(this.getXPos()) > this.getYPos() + this.getHeight()/2  && abelToPerformDash && !touchingGround){	

				/////this.setYPos(platforms.get(i).getUpperYFromX(this.getXPos()) - this.getHeight()/2);
				Log.d(TAG, "Coming down 2 u!! #onPlatform");
				overPlatform = true;
				abelToPerformDash = false;
				//Check if protagonist would pass platform in a frame, if yes set protagonist on platform
				Log.d(TAG, "" + this.getYVel());

				if(platforms.get(i).getUpperYFromX(this.getXPos()) - this.getXPos() > this.getYVel()){
					this.setYAcc(15); //Set constant
					this.setYVel(this.getYAcc() + this.getYVel());
					Log.d(TAG, "speeed " + this.getYVel());
				}
				this.setYAcc(0); //Set constant
				this.setYVel(0);
				this.setYPos(platforms.get(i).getUpperYFromX(this.getXPos()) - this.getHeight()/2);

				//Check if protagonist get dash bonus
				if(platforms.get(i).getUpperYFromX(this.getXPos()) - startHeight > 2*this.getHeight()) {
					dashBonus = true;
					invincible = true;
					Log.d(TAG, "DASH!!");
				}
			} else if (!overPlatform  && abelToPerformDash && !touchingGround){
				this.setYAcc(15); //Set constant
				this.setYVel(this.getYAcc() + this.getYVel());
				Log.d(TAG, "Coming down 2 u!! #hitGround");
				abelToPerformDash = false;
				//Check if protagonist get dash bonus
				if(g.getYFromX(this.getXPos()) - startHeight > 2*this.getHeight()) {
					dashBonus = true;
					invincible = true;
					Log.d(TAG, "DASH!!");
				}
			}
		}

	}
	//------------------------------------------------------------------------------------------------//
	//OTHER PROPERTIES
	//Let gravity work on protagonist
	public void gravity(){
		this.setYVel(this.getYVel()+this.getJumpAcc());
	}

	//Keeps track of the protagonist's invincibility when damaged or dashing
	public void invincibility(){
		if(invincible){
			invincibilityCount++;
			if(invincibilityCount >= maxInvincibilityCount ){
				invincible = false;
				invincibilityCount = 0;
			}
		}
	}
	//------------------------------------------------------------------------------------------------//
	//CHECK-METHODS FOR PROTAGONIST AND HIS SURROUNDING
	//Check slope under protagonist
	public void checkSlope(Ground ground, ArrayList<Platform> platforms){
		if(touchingGround){ 
			readyToJump = true;
			if(getYPos()+getHeight()/2 - ground.getYFromX(getXPos()) > -5){ //On ground
				double slope = ground.getSlope(this.getXPos());
				if(Math.abs(slope) > slopeThreshold){
					setXVel(getXVel()+slope);
					readyToJump = false;
				}
			} else { //On platform
				for(int i = 0; i < platforms.size(); i++){
					if((platforms.get(i).getUpperX()[0] <= getXPos() && platforms.get(i).getUpperX()[platforms.get(i).getUpperLength()-1] >= getXPos())){
						double slope = platforms.get(i).getSlope(this.getXPos());
						if(Math.abs(slope) > slopeThreshold){
							setXVel(getXVel()+slope);
							readyToJump = false;
							Log.d(TAG, "HEJ");
							break;
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

	//Check if the protagonist is standing on the ground (make sure he is)
	public void checkGround(Ground g){
		if(this.yPos + height/2 > g.getYFromX(this.xPos)){
			this.yPos = g.getYFromX(this.xPos)-height/2;
			this.yVel = 0;
			this.yAcc = 0;
			touchingGround = true;
			overPlatform = false;
		}
	}

	//Check if protagonist hit platform
	public void checkPlatform(ArrayList<Platform> al) {
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).spans(this.getXPos())) {
				//if head is in platform
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

	//Check if protagonist is over a platform (used in dashing)
	public void checkOverPlatform(ArrayList<Platform> platforms) {
		for (int i = 0; i < platforms.size(); i++) {
			if (platforms.get(i).spans(this.getXPos()) && platforms.get(i).getUpperYFromX(this.getXPos()) >= this.getYPos() + this.getHeight()/2){
				overPlatform = true;
				break;
			} else {
				overPlatform = false;
			}
		}
	}

	//Check collision with enemy
	public boolean collide(Enemy e){
		if(getXPos() - getWidth()/2 < e.getXPos() + e.getWidth()/2 && getXPos() + getWidth()/2 > e.getXPos() - e.getWidth()/2 &&
				getYPos() - getWidth()/2 < e.getYPos() + e.getHeight()/2 && getYPos() + getWidth()/2 > e.getYPos() - e.getHeight()/2){
			abelToPerformDash = true;
			return true;
		} else 
			return false;
	}
	//------------------------------------------------------------------------------------------------//
	//FOR RENDERING PROTAGONIST
	//Keeps track of the protagonist's step (used for rendering)
	public void step(int step){
		stepCount += step;
		if(stepCount >= numberOfSteps){
			stepCount = -numberOfSteps;
		} else if(stepCount <= -numberOfSteps){
			stepCount = numberOfSteps;
		}
	}

	//Which way the protagonist should be rendered
	public void faceDirection(Stick left, Stick right){
		if(right.isPointed()){
			if(right.getAngle() <= 90 || right.getAngle() > 270){
				facingRight = true;
			} else {
				facingRight = false;
			}
		} else {
			if(left.getAngle() <= 90 || left.getAngle() > 270){
				facingRight = true;
				right.setAngle(0);
			} else {
				facingRight = false;
				right.setAngle(180);
			}
		}
	}

	//Keeps track of the protagonist's breathing (used for rendering)
	public void breathe(){
		breathCount++;
		if(breathCount >= breathMax){
			breathCount = 0;
		}
	}
	//------------------------------------------------------------------------------------------------//
	//GET AND SET METHODS
	//Methods for position, velocity, acceleration
	public double getXPos() {
		return xPos;
	}

	public void setXPos(double n) {
		xPos = n;
	}

	public double getYPos() {
		return yPos;
	}

	public void setYPos(double n) {
		yPos = n;
	}

	public double getXVel() {
		return xVel;
	}

	public void setXVel(double n) {
		xVel = n;
	}

	public double getYVel() {
		return yVel;
	}

	public void setYVel(double n) {
		yVel = n;
	}

	public double getXAcc() {
		return xAcc;
	}

	public void setXAcc(double n) {
		xAcc = n;
	}

	public double getYAcc() {
		return yAcc;
	}

	public void setYAcc(double n) {
		yAcc = n;
	}

	//Methods for properties
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

	public void setStepCount(int step){
		stepCount = step;
	}

	public int getStepCount(){
		return stepCount;
	}

	public int getNumberOfSteps() {
		return numberOfSteps;
	}

	public int getBreathCount() {
		return breathCount;
	}

	public void setBreathCount(int breathCount) {
		this.breathCount = breathCount;
	}

	public int getBreathMax() {
		return breathMax;
	}

	//Booleans
	public boolean isTouchingGround() {
		return touchingGround;
	}

	public void setTouchingGround(boolean touchingGround) {
		this.touchingGround = touchingGround;
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public boolean isDashBonus() {
		return dashBonus;
	}

	public void setDashBonus(boolean dashBonus) {
		this.dashBonus = dashBonus;
	}
	//------------------------------------------------------------------------------------------------//
}

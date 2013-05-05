package com.example.pineapple;

import java.util.ArrayList;

import android.util.Log;

public class Enemy {

	private static final String TAG = Enemy.class.getSimpleName();
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;
	private double health = 1;
	private GamePanel gp;
	private static int baseHeight = 14;
	private static int baseWidth = (int)(baseHeight*1.5); //Change 1.5 to ratio of bitmap
	private int height, width;
	private static double scaleNinja = 0.8;
	private static double scaleTank = 1.2;
	private double baseAcc = 1;
	private double jumpVel = -6;
	private double jumpAcc = 0.4;
	private double maxSpeed = 3;
	private double slideCoefficient = 0.8;
	private double typeAcc;
	private boolean touchingGround;
	private boolean onPlatform;
	private final int type;
	private int leftArmAngle, rightArmAngle, armAngleCounter;
	private int pupilAngle;
	private final double spawnX;
	private boolean spawned;
	private final double slopeThreshold = 0.7;
	private double dashDistance;
	private double dashPowerConstant;
	private double healthLostByDashConstant = 0.5;
	private double damageGrade;
	//------------------------------------------------------------------------------------------------//
	//CONSTRUCTORS
	public Enemy(double i, double j, double spawnX, int type, GamePanel gp) {
		this.type = type;
		this.height = baseHeight;
		this.width = baseWidth;
		//type 1 is normal
		if (type == 1) {
			this.damageGrade = 1;
			this.typeAcc = 0.2*baseAcc;
			leftArmAngle = -45;
			rightArmAngle = 45;
		}
		//type 2 is ninja
		else if (type == 2) {
			this.height = (int)(this.height*scaleNinja);
			this.width = (int)(this.width*scaleNinja);
			this.typeAcc = 0.6*baseAcc;
			this.maxSpeed = 1*maxSpeed;
			this.jumpVel = 2*jumpVel;
			this.jumpAcc = 2*jumpAcc;
			this.damageGrade = 1.5;

		}
		//type 3 is tank
		else if (type == 3) {
			this.height = (int)(this.height*scaleTank);
			this.width = (int)(this.width*scaleTank);
			this.typeAcc = 0.1*baseAcc;
			this.maxSpeed = 0.5*maxSpeed;
			this.jumpVel = 0.5*jumpVel;
			this.jumpAcc = 0.5*jumpAcc;
			this.damageGrade = 0.5;

		}
		this.setXPos(i);
		this.setYPos(j);
		this.spawnX = spawnX;
	}
	//------------------------------------------------------------------------------------------------//
	//HOW TO MAKE ENEMY MOVE
	//Move enemy
	public void move() {
		this.xPos = this.getXPos() + this.getXVel();
		this.yPos = this.getYPos() + this.getYVel();
	}

	//Accelerating enemy
	public void accelerate(double acc) { // acc = 0.2?
		this.setXVel(this.getXVel() + acc);
		if(Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() > 0) {
			this.setXVel(this.getMaxSpeed());
		} else if (Math.abs(this.getXVel()) > this.getMaxSpeed() && this.getXVel() < 0) {
			this.setXVel(-this.getMaxSpeed());
		}
	}

	//General method to make the enemy move against the protagonist
	public void accelerate(Protagonist p){
		//Fix constants later...
		this.accelerate(typeAcc*Math.signum(p.getXPos() - this.getXPos()));
	}
	
	//Check the slope and adjust speed accordingly
	public void checkSlope(Ground ground, ArrayList<Platform> platforms){
		if(touchingGround){ 
			if(getYPos()+getHeight()/2 - ground.getYFromX(getXPos()) > -5){ //On ground
				double slope = ground.getSlope(this.getXPos());
				if(Math.abs(slope) > slopeThreshold){
					setXVel(getXVel()+slope);
					setYPos(getYPos()+slope*getXVel());
				}
			} else { //On platform
				for(int i = 0; i < platforms.size(); i++){
					if((platforms.get(i).getUpperX()[0] <= getXPos() && platforms.get(i).getUpperX()[platforms.get(i).getUpperLength()-1] >= getXPos())){
						double slope = platforms.get(i).getSlope(this.getXPos());
						if(Math.abs(slope) > slopeThreshold){
							setXVel(getXVel()+slope);
							setYPos(getYPos()+slope*getXVel());
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
	//------------------------------------------------------------------------------------------------//
	//ENEMY ACTIONS
	//Reduce the enemy's health
	public void takeDamage(double damage){
		this.setHealth(this.getHealth()-damage);
	}

	//Reduce enemy health when dashing and bumps enemy away
	public void takeDashDamage(Protagonist p){
		dashDistance = Math.abs(p.getXPos() - this.getXPos());
		if(dashDistance < p.getWidth())
			dashPowerConstant = 1;
		else if (dashDistance < p.getWidth()*2 && dashDistance > p.getWidth())
			dashPowerConstant = 0.75;
		else
			dashPowerConstant = 0.5;	
		this.setHealth(this.getHealth() - healthLostByDashConstant*dashPowerConstant*damageGrade);
		int sign;
		if (Math.random() > 0.5){
			sign = 1;
		}	else {
			sign = -1;
		}

		setXVel(-getXVel() + sign*getXVel()/10);
		setYVel(jumpVel*dashPowerConstant);
		this.setTouchingGround(false);
	}
	//------------------------------------------------------------------------------------------------//
	//CHECK-METHODS FOR ENEMY AND SURROUNDING
	//Check if the enemy is standing on the ground (make sure enemy is)
	public void checkGround(Ground g){
		if(this.yPos + height/2 > g.getYFromX(this.xPos)){
			this.yPos = g.getYFromX(this.xPos)-height/2;
			this.yVel = 0;
			this.yAcc = 0;
			touchingGround = true;
		}
	}

	//Check if enemy hit platform
	public void checkPlatform(ArrayList<Platform> platforms) {
		for (int i = 0; i < platforms.size(); i++) {
			if (platforms.get(i).spans(this.getXPos())) {
				//if head is in platform
				//Log.d(TAG, "Warning: Platform, platform!!");
				if (this.getYVel() < 0 && this.getYPos() - this.getHeight()/2 < platforms.get(i).getLowerYFromX(this.getXPos()) && this.getYPos() - this.getHeight()/2 > platforms.get(i).getUpperYFromX(this.getXPos())) {
					this.setYVel(-this.getYVel());
					Log.d(TAG, "Enemy hit the head!!");
				} else {
					//if feet is in platform
					if (this.getYVel() > 0 && this.getYPos() + this.getHeight()/2 > platforms.get(i).getUpperYFromX(this.getXPos())) {
						if (this.getYPos() + this.getHeight()/2 < platforms.get(i).getLowerYFromX(this.getXPos())) {
							this.setYPos(platforms.get(i).getUpperYFromX(this.getXPos()) - this.getHeight()/2);
							this.setYVel(0);
							this.setYAcc(0);
							touchingGround = true;
							onPlatform = true;
							Log.d(TAG, "Enemy over, and out!!");					
						}
					}
				}
			} //if making move towards edge of platform
			if (platforms.get(i).checkSide(this, -1) && getXPos() < platforms.get(i).getUpperX()[0] && getXPos() + getWidth()/2 > platforms.get(i).getUpperX()[0] && getXVel() > 0) {
				this.setXVel(0);
				this.setXPos(platforms.get(i).getUpperX()[0] - getWidth()/2);
			}
			if(platforms.get(i).checkSide(this, 1) && getXPos() > platforms.get(i).getUpperX()[platforms.get(i).getUpperX().length-1] && getXPos() - getWidth()/2 < platforms.get(i).getUpperX()[platforms.get(i).getUpperX().length-1] && getXVel() < 0){
				this.setXVel(0);
				this.setXPos(platforms.get(i).getUpperX()[platforms.get(i).getUpperX().length-1] + getWidth()/2);
			}
		}
	}

	public void checkAirborne(Ground g, ArrayList<Platform> platforms){
		if(Math.abs(this.yPos + height/2 - g.getYFromX(this.xPos)) > this.yPos && !onPlatform){
			touchingGround = false;
			onPlatform = false;
		}
	}
	//------------------------------------------------------------------------------------------------//
	//OTHER PROPERTIES
	//Let gravity work on enemy
	public void gravity(){
		this.setYVel(this.getYVel()+this.getJumpAcc());
	}

	//Spawn enemy
	public void spawn(){
		spawned = true;
	}
	//------------------------------------------------------------------------------------------------//
	//RENDER PROPERTIES
	//Look at the protagonist
	public void lookAt(Protagonist p){
		pupilAngle = (int)(180/Math.PI*Math.atan2(p.getYPos()-getYPos(), p.getXPos() - getXPos()));
	}

	//Wave arms
	public void waveArms(){
		armAngleCounter++;
		switch(type){
		case 1:
			leftArmAngle = (int)(60*Math.sin(armAngleCounter/5.));
			rightArmAngle = -leftArmAngle;
			break;

		case 2:
			leftArmAngle = (int)(60*Math.sin(armAngleCounter/5.));
			rightArmAngle = -leftArmAngle;
			break;

		case 3:
			leftArmAngle = (int)(60*Math.sin(armAngleCounter/5.));
			rightArmAngle = -leftArmAngle;
			break;
		}
	}
	//------------------------------------------------------------------------------------------------//
	//GET AND SET METHODS
	//Methods for position, velocity, acceleration
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

	public double getXAcc() {
		return xAcc;
	}	

	public void setXAcc(double xAcc) {
		this.xAcc = xAcc;
	}

	public double getYAcc() {
		return yAcc;
	}

	public void setYAcc(double yAcc) {
		this.yAcc = yAcc;
	}

	//Methods for properties
	public double getHealth() {
		return health;
	}

	public void setHealth(double d) {
		this.health = d;
	}

	public double getDamageGrade() {
		return damageGrade;
	}
	public double getMaxSpeed() {
		return maxSpeed;
	}

	public double getJumpAcc() {
		return jumpAcc;
	}

	public double getTypeAcc() {
		return typeAcc;
	}

	public double getSpawnX(){
		return spawnX;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static double getScaleTank(){
		return scaleTank;
	}

	public static double getScaleNinja(){
		return scaleNinja;
	}

	public static int getBaseHeight() {
		return baseHeight;
	}
	public static int getBaseWidth() {
		return baseWidth;
	}
	public int getType() {
		return type;
	}
	public int getLeftArmAngle() {
		return leftArmAngle;
	}
	public int getRightArmAngle() {
		return rightArmAngle;
	}
	public int getPupilAngle(){
		return pupilAngle;
	}
	//Booleans
	public void setSpawed(boolean flag){
		spawned = flag;
	}
	public boolean hasSpawned(){
		return spawned;
	}
	public boolean isTouchingGround() {
		return touchingGround;
	}
	public void setTouchingGround(boolean touchingGround) {
		this.touchingGround = touchingGround;
	}
	//------------------------------------------------------------------------------------------------//
}

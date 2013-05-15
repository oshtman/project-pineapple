package com.example.valentine;

import java.util.ArrayList;

public class Bird {
	private boolean alive = true;
	private boolean killBird;
	private double x, y, baseY, rotation, xVel, startY;
	private static int width = 10, height = (int)(width/1.33);
	private double yVel;
	private int counter;

	public Bird(int x, int y, boolean killBird){
		this.x = x;
		this.baseY = y;
		this.startY = y;
		this.yVel = 0;
		this.rotation = 0;
		this.killBird = killBird;
	}

	public Bird(int x, int y){
		this(x, y, true);
	}

	//Update the bird's location
	public void update(){
		if(!alive){
			yVel += 0.5;
			y += yVel;
			x += xVel;
			rotation += 10;
		} else {
			counter++;
			y = baseY + Math.sin(counter);
		}
	}

	public boolean collide(ArrayList<Bullet> bullets){
		if(killBird){
			for(int i = 0; i < bullets.size(); i++){
				if(x - width/2 < bullets.get(i).getXPos() + Bullet.getRadius() && x + width/2 > bullets.get(i).getXPos() - Bullet.getRadius() &&
						y - height/2 < bullets.get(i).getYPos() + Bullet.getRadius() && y + height/2 > bullets.get(i).getYPos() - Bullet.getRadius()){
					alive = false;
					yVel = -3;
					xVel = 2*Math.random() - 1;
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public boolean isAlive(){
		return alive;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public double getRotation() {
		return rotation;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public double getStartY() {
		return startY;
	}
}

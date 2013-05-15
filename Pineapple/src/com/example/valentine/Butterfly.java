package com.example.valentine;

public class Butterfly {
	private double x, y, startX, startY;
	private double counter;
	private int rot;
	public Butterfly(double startX, double startY, double multiplier){
		this.startX = startX;//70
		this.startY = startY;//73
		rot = 0;
		counter = -2*Math.PI*multiplier;
	}
	
	public Butterfly(double startX, double startY){
		this(startX, startY, 1);
	}
	
	public void update(){
		counter += Math.PI/180;
		if(counter >= 0){
		this.x = startX + 30*Math.sin(counter) + 20*Math.sin(counter/2) + 20*Math.sin(counter/3) + 20*Math.sin(counter/4);
		this.y = startY - 40 + 10*Math.cos(counter) + 10*Math.cos(counter/2) + 10*Math.cos(counter/3) + 10*Math.cos(counter/4);
		rot = (int)(30*Math.cos(counter) + 10*Math.cos(counter/2) + 7*Math.cos(counter/3) + 5*Math.cos(counter/4))/3;
			if(counter > 24*Math.PI){
				counter = -2*Math.PI;
			}
		} else {
			this.x = startX;
			this.y = startY;
			this.rot = 0;
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getCounter() {
		return counter*180/Math.PI;
	}

	public void setCounter(double counter) {
		this.counter = counter;
	}
	
	public int getRot(){
		return rot;
	}
}

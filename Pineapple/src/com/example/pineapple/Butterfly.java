package com.example.pineapple;

public class Butterfly {
	private double x, y;
	private double counter;
	private int rot;
	public Butterfly(){
		this.x = 70;
		this.y = 80;
		rot = 0;
		counter = -2*Math.PI;
	}
	
	public void update(){
		counter += Math.PI/180;
		if(counter >= 0){
		this.x = 70 + 30*Math.sin(counter) + 20*Math.sin(counter/2) + 20*Math.sin(counter/3) + 20*Math.sin(counter/4);
		this.y = 33 + 10*Math.cos(counter) + 10*Math.cos(counter/2) + 10*Math.cos(counter/3) + 10*Math.cos(counter/4);
		rot = (int)(30*Math.cos(counter) + 10*Math.cos(counter/2) + 7*Math.cos(counter/3) + 5*Math.cos(counter/4))/3;
			if(counter > 24*Math.PI){
				counter = -2*Math.PI;
			}
		} else {
			this.x = 70;
			this.y = 73;
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

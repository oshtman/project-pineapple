package com.example.pineapple;

public class Protagonist {
	
	private double xPos;
	private double yPos;
	private double height;
	private double length;
//	private bitmap portrait;
	

	public Protagonist(double i,double j){
		xPos = this.setXPos(i);
		yPos = this.setYPos(j);
	}
	
	public Protagonist(){
		xPos = this.setXPos(75);
		yPos = this.setYPos(25);
	}
	
	public double getXPos() {
		return xPos;
	}
	
	public double getYPos() {
		return yPos;
	}
	
	public  double setXPos(double n) {
		return n;
	}
	
	public double setYPos(double n) {
		return n;
	}
}

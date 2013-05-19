package com.pineapple.valentine;

public class Slider {
	private double value;
	private double x, y;
	private double width, height;
	private boolean sliding;

	public Slider(double x, double y, double width, double height, double value){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.value = value;
		if(value > 1)
			value = 1;
		if(value < 0)
			value = 0;
	}

	public void handleTouch(int x, int y){
		if(x > this.x && x < this.x + width && y > this.y && y < this.y + height){
			if(!sliding){
				sliding = true;
			} else {
				value = (x-this.x)/width;
			}
		} else {
			sliding = false;
		}
	}
	
	public void release(){
		sliding = false;
	}
	
	public double getValue(){
		return value;
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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public boolean isSliding() {
		return sliding;
	}

	public void setSliding(boolean sliding) {
		this.sliding = sliding;
	}

	public void setValue(double value) {
		this.value = value;
	}

}

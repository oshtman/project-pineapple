package com.example.pineapple;

public class Platform {
	private int[] upperX;
	private int[] upperY;
	private int[] lowerX;
	private int[] lowerY;
	
	public Platform(int[] upperX, int[] upperY, int[] lowerX, int[] lowerY){
		this.upperX = upperX;
		this.upperY = upperY;
		this.lowerX = lowerX;
		this.lowerY = lowerY;
	}

	//Get the y position of the ground relative to the given x
	public double getYFromX(double x){
		//Find the index to the right of the protagonist
		int index = 0;
		while(this.upperX[index] < x){
			index++;
		}

		int diff = this.upperX[index] - this.upperX[index-1];
		double percent = (x-this.upperX[index-1])/(double)diff;
		return this.upperY[index-1]+percent*(this.upperY[index]-this.upperY[index-1]);
	}

	//Get the slope of the ground at given x 
	//A positive slope means that the ground is declining from left to right
	public double getSlope(double x){
		//Find the index to the right of the protagonist
		int index = 0;
		while(this.upperX[index] < x){
			index++;
		}
		return (this.upperY[index] - this.upperY[index-1])/((double)this.upperX[index] - this.upperX[index-1]);
		
	}

	public int[] getUpperX() {
		return upperX;
	}

	public void setUpperX(int[] upperX) {
		this.upperX = upperX;
	}

	public int[] getUpperY() {
		return upperY;
	}

	public void setUpperY(int[] upperY) {
		this.upperY = upperY;
	}

	public int[] getLowerX() {
		return lowerX;
	}

	public void setLowerX(int[] lowerX) {
		this.lowerX = lowerX;
	}

	public int[] getLowerY() {
		return lowerY;
	}

	public void setLowerY(int[] lowerY) {
		this.lowerY = lowerY;
	}
	
	//Get the lengths of the upper and lower arrays
	public int getUpperLength(){
		return upperX.length;
	}
	
	public int getLowerLength(){
		return lowerX.length;
	}
}

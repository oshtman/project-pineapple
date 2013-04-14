package com.example.pineapple;

public class Ground {
	int[] x, y; //x- and y-values for the ground
	int type;
	
	public Ground(int[] x, int[] y){
		this.x = x;
		this.y = y;
	}
	
	//Get the y position of the ground relative to the given x
	public double getYFromX(double x){
		int index = 0;
		while(this.x[index] < x){
			index++;
		}
		
		int diff = this.x[index] - this.x[index-1];
		double percent = (x-this.x[index-1])/(double)diff;
		return this.y[index-1]+percent*(this.y[index]-this.y[index-1]);
	}
	
	//Get the slope of the ground at given x 
	//A positive slope means that the ground is declining from left to right
	public double getSlope(double x){
		int index = 0;
		while(this.x[index] < x){
			index++;
		}
		return (this.y[index] - this.y[index-1])/((double)this.x[index] - this.x[index-1]);
		
	}
	
	//Get the number of points defining the ground
	public int getLength(){
		return x.length;
	}
	
	public int getX(int index){
		return x[index];
	}
	
	public int getY(int index){
		return y[index];
	}
}

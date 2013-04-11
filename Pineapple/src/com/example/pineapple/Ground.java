package com.example.pineapple;

public class Ground {
	int[] x, y; //x- and y-values for the ground
	int type;
	
	public Ground(int[] x, int[] y){
		this.x = x;
		this.y = y;
	}
	
	public double getYFromY(double x){
		int index = 0;
		while(this.x[index] < x){
			index++;
		}
		
		int diff = this.x[index] - this.x[index-1];
		double percent = (x-this.x[index-1])/(double)diff;
		return this.y[index-1]+percent*(this.y[index]-this.y[index-1]);
	}
	
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

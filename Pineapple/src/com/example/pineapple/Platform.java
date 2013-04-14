package com.example.pineapple;

import android.graphics.Matrix;
import android.graphics.Path;

public class Platform {
	private final String TAG = Platform.class.getSimpleName();
	private int[] upperX;
	private int[] upperY;
	private int[] lowerX;
	private int[] lowerY;
	private Path path;
	
	public Platform(int[] upperX, int[] upperY, int[] lowerX, int[] lowerY){
		this.upperX = upperX;
		this.upperY = upperY;
		this.lowerX = lowerX;
		this.lowerY = lowerY;
		
		generatePath();
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
	
	//Create a Path object for the platform, which is used to render it
	public void generatePath(){
		path = new Path();
		path.moveTo(upperX[0], upperY[0]);
		
		for(int i = 1; i < getUpperLength(); i++){
			path.lineTo(upperX[i], upperY[i]);
		}
		for(int i = getLowerLength()-1; i >= 0; i--){
			path.lineTo(lowerX[i], lowerY[i]);
		}
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
	
	public Path getPath(){
		return path;
	}
	
	public void scalePath(double scaleX, double scaleY){
		Matrix m = new Matrix();
		m.postScale((float)scaleX, (float)scaleY);
		path.transform(m);
	}
}

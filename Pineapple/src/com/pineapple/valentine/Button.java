package com.pineapple.valentine;

import android.graphics.Bitmap;

public class Button {
	
	int x, y;
	double height, width;
	private Bitmap bitmap;
	private boolean visible;
	
	public Button(int x, int y, double width, double height, Bitmap bitmap){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bitmap = bitmap;
	}
	
	
	//Check if the given x and y is inside the button area
	public boolean isClicked(int x, int y){
		boolean flag = false;
		if(x > this.x && x < this.x + width){
			if(y > this.y && y < this.y + height){
				flag = true;
			}
		}
		return flag;
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public double getHeight() {
		return height;
	}


	public double getWidth() {
		return width;
	}
}

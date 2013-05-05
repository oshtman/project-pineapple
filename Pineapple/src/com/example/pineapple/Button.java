package com.example.pineapple;

import android.graphics.Bitmap;
import android.util.Log;

public class Button {
	private final String TAG = Button.class.getSimpleName();
	
	int x, y;
	int height, width;
	private Bitmap bitmap;
	private boolean visible;
	
	public Button(int x, int y, Bitmap bitmap){
		this.x = x;
		this.y = y;
		this.bitmap = bitmap;
		height = (int)(Const.menuButtonHeight);
		width = (int)(height*2.863);
	}
	
	
	//Check if the given x and y is inside the button area
	public boolean isClicked(int x, int y){
		boolean flag = false;
		if(x > this.x && x < this.x + width){
			if(y > this.y && y < this.y + height){
				flag = true;
				Log.d(TAG, ""+ this.y + "<" + y +  "<" + (this.y + height)); 
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
}

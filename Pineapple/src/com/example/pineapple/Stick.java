package com.example.pineapple;

import android.graphics.Canvas;

public class Stick {
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private int x, y;
	
	public Stick(int pos){
		if(pos == LEFT){
			x = 135;
		} else {
			x = 80;
		}
		y = 80;
	}
}

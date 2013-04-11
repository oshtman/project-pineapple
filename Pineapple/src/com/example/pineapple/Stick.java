package com.example.pineapple;

public class Stick {
	public final int LEFT = 1;
	public final int RIGHT = 2;
	private int x, y;
	private int angle;
	private boolean pointed;
	
	public Stick(int pos){
		if(pos == LEFT){
			x = 20;
		} else {
			x = 135;
		}
		y = 80;
		pointed = false;
	}
	
	
	
	
}

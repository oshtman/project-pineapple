package com.pineapple.valentine;

import java.util.ArrayList;

import android.util.Log;

public class Hint {
	private int x, y;
	String[] hint;

	public Hint(int x, int y, String hint){
		this.x = x;
		this.y = y;
		ArrayList<String> temp = new ArrayList<String>();

		int lettersPerRow = 50;
		
		while(hint.length() > lettersPerRow || hint.contains("*")){
			String row = hint;
			if(hint.length() > lettersPerRow){
				row = hint.substring(0, lettersPerRow-1);
			}
			Log.d("HEJ", row);
			if(row.contains("*") && row.indexOf("endl") < lettersPerRow){
				Log.d("HEJ", "Här var det slut");
				int stopIndex = row.indexOf("*");
				temp.add(hint.substring(0, stopIndex));
				hint = hint.substring(stopIndex+1, hint.length());
			} else {
				int spaceIndex = row.lastIndexOf(" ");
				temp.add(hint.substring(0, spaceIndex));
				hint = hint.substring(spaceIndex+1, hint.length());
			}
		}
		this.hint = new String[temp.size()+1];
		for(int i = 0; i < temp.size(); i++){
			this.hint[i] = temp.get(i);
		}
		this.hint[this.hint.length-1] = hint;
	}
	
	public boolean inRange(double x, double y){
		return Math.sqrt((Math.pow(x-this.x, 2)+Math.pow(y-this.y, 2))) < Const.hintRange;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String[] getHint(){
		return hint;
	}
}


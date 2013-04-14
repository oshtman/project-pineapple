package com.example.pineapple;

import java.util.ArrayList;

public class LevelLoader {
	private ArrayList<int[]> xArrays = new ArrayList<int[]>();
	private ArrayList<int[]> yArrays = new ArrayList<int[]>();
	private final int[] x1 = {-300, 0, 100, 150, 300};
	private final int[] y1 = {95, 95, 80, 95, 20};
	
	public LevelLoader(){
		xArrays.add(x1);
		yArrays.add(y1);
	}
	
	public int[] getLevelX(int level){
		return xArrays.get(level-1);
	}
	
	public int[] getLevelY(int level){
		return yArrays.get(level-1);
	}
	
	
	
}

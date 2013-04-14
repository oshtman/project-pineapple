package com.example.pineapple;

import java.util.ArrayList;

public class LevelLoader {
	private ArrayList<int[]> xGroundArrays = new ArrayList<int[]>();
	private ArrayList<int[]> yGroundArrays = new ArrayList<int[]>();
	
	//Platform lists for level 1
	private ArrayList<int[]> pUX1 = new ArrayList<int[]>();
	private ArrayList<int[]> pUY1 = new ArrayList<int[]>();
	private ArrayList<int[]> pLX1 = new ArrayList<int[]>();
	private ArrayList<int[]> pLY1 = new ArrayList<int[]>();
	
	
	//Put the ground arrays for all levels here!
	private final int[] x1 = {-300, 0, 100, 150, 300};
	private final int[] y1 = {95, 95, 80, 95, 20};

	
	
	//Put the platforms for each level here!
	//Variable syntax:
	//pUX1_2 means the upper x array for platform 2 on level 1 
	
	//Level 1
	//Platform 1
	private final int[] pUX1_1 = {30, 40, 50};
	private final int[] pUY1_1 = {10, 10, 20};
	private final int[] pLX1_1 = {30, 40, 50};
	private final int[] pLY1_1 = {10, 20, 20};
	
	
	
	public LevelLoader(){
		//level 2 "Valley of whale"
		int[] x2 = new int[100];
		int[] y2 = new int[100];
		for (int i = 0; i<100; i++) {
			x2[i] = i*20;
			y2[i] = 50 + (int)(25*Math.cos((double)x2[i]/200*Math.PI));
		}
		
		//Add the ground arrays to their lists
		xGroundArrays.add(x1);
		yGroundArrays.add(y1);
		xGroundArrays.add(x2);
		yGroundArrays.add(y2);
		
		//Add the platforms to their respective lists
		pUX1.add(pUX1_1);
		pUY1.add(pUY1_1);
		pLX1.add(pLX1_1);
		pLY1.add(pLY1_1);
		
	}
	
	public int[] getLevelX(int level){
		return xGroundArrays.get(level-1);
	}
	
	public int[] getLevelY(int level){
		return yGroundArrays.get(level-1);
	}
	
	/*public int[] getPlatformUpperX(int level, int platform){
		return 
	}
	public int[] getPlatformUpperY(int level, int platform){

	}
	public int[] getPlatformLowerX(int level, int platform){

	}
	public int[] getPlatformLowerY(int level, int platform){

	}*/
	
}

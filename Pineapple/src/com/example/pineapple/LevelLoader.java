package com.example.pineapple;

import java.util.ArrayList;

import android.util.Log;

public class LevelLoader {
	private final String TAG = LevelLoader.class.getSimpleName();
	
	private ArrayList<int[]> xGroundArrays = new ArrayList<int[]>();
	private ArrayList<int[]> yGroundArrays = new ArrayList<int[]>();
	
	//Lists that hold lists for the platforms for each level
	private ArrayList<ArrayList<int[]>> pUX = new ArrayList<ArrayList<int[]>>();
	private ArrayList<ArrayList<int[]>> pUY = new ArrayList<ArrayList<int[]>>();
	private ArrayList<ArrayList<int[]>> pLX = new ArrayList<ArrayList<int[]>>();
	private ArrayList<ArrayList<int[]>> pLY = new ArrayList<ArrayList<int[]>>();
	
	
	//Put the ground arrays for all levels here!
	private final int[] x1 = {-300, 0, 100, 150, 300};
	private final int[] y1 = {95, 95, 80, 95, 20};

	
	
	//Put the platforms for each level here!
	//Variable syntax:
	//pUX1_2 means the upper x array for platform 2 on level 1 
	
	//Level 1
	private ArrayList<int[]> pUX1 = new ArrayList<int[]>();
	private ArrayList<int[]> pUY1 = new ArrayList<int[]>();
	private ArrayList<int[]> pLX1 = new ArrayList<int[]>();
	private ArrayList<int[]> pLY1 = new ArrayList<int[]>();
	
	//Platform 1
	private final int[] pUX1_1 = {0, 10, 50};
	private final int[] pUY1_1 = {20, 30, 30};
	private final int[] pLX1_1 = {0, 10, 50};
	private final int[] pLY1_1 = {20, 40, 30};
	
	//Level 2
	private ArrayList<int[]> pUX2 = new ArrayList<int[]>();
	private ArrayList<int[]> pUY2 = new ArrayList<int[]>();
	private ArrayList<int[]> pLX2 = new ArrayList<int[]>();
	private ArrayList<int[]> pLY2 = new ArrayList<int[]>();
	
	//Level 3
	private ArrayList<int[]> pUX3 = new ArrayList<int[]>();
	private ArrayList<int[]> pUY3 = new ArrayList<int[]>();
	private ArrayList<int[]> pLX3 = new ArrayList<int[]>();
	private ArrayList<int[]> pLY3 = new ArrayList<int[]>();
	
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
		
		//Add the lists of each level to the list of lists
		pUX.add(pUX1);
		pUY.add(pUY1);
		pLX.add(pLX1);
		pLY.add(pLY1);
		
		pUX.add(pUX2);
		pUY.add(pUY2);
		pLX.add(pLX2);
		pLY.add(pLY2);
		
		pUX.add(pUX3);
		pUY.add(pUY3);
		pLX.add(pLX3);
		pLY.add(pLY3);
		
	}
	
	public int[] getLevelX(int level){
		return xGroundArrays.get(level-1);
	}
	
	public int[] getLevelY(int level){
		return yGroundArrays.get(level-1);
	}
	
	public int[] getPlatformUpperX(int level, int platform){
		return pUX.get(level-1).get(platform-1);
	}
	public int[] getPlatformUpperY(int level, int platform){
		return pUY.get(level-1).get(platform-1);
	}
	public int[] getPlatformLowerX(int level, int platform){
		return pLX.get(level-1).get(platform-1);
	}
	public int[] getPlatformLowerY(int level, int platform){
		return pLY.get(level-1).get(platform-1);
	}
	
}

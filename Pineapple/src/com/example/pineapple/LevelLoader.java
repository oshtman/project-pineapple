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
	
	
	
	public LevelLoader(){
		//Add the ground arrays to their lists
		xGroundArrays.add(x1);
		yGroundArrays.add(y1);	
		
		
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

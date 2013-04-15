package com.example.pineapple;

import java.util.ArrayList;

import android.util.Log;

public class LevelLoader {
	private final String TAG = LevelLoader.class.getSimpleName();
	
	private ArrayList<int[]> xGroundArrays = new ArrayList<int[]>();
	private ArrayList<int[]> yGroundArrays = new ArrayList<int[]>();
	
	//Lists that hold lists for the platforms for each level
	private ArrayList<ArrayList<int[][]>> platformArrays = new ArrayList<ArrayList<int[][]>>();
	
	//Put the ground arrays for all levels here!
	private final int[] x1 = {-300, 0,  100, 150, 300};
	private final int[] y1 = {95,   95, 80,  95,  20};

	//Put the platforms for each level here!
	
	//Matrix syntax:
		//p3_1 means platform 1 on level 3
		//The rows of the matrix represents: 
		//UpperX
		//UpperY
		//LowerX
		//LowerY
	
	//Level 1
	private ArrayList<int[][]> platformsLevel1 = new ArrayList<int[][]>();
	
	//Platform 1
	private final int[][] p1_1 = {
			{0, 10, 50},
			{20, 30, 30},
			{0, 10, 50},
			{20, 40, 30}
	};

	//Platform 2
	private final int[][] p1_2 = {
			{150, 250, 260},
			{50, 10, 13},
			{150, 220, 260},
			{50, 25, 13}
	};
	
	//Level 2
	private ArrayList<int[][]> platform2 = new ArrayList<int[][]>();
	
	//Platform 1
	
	//Level 3
	private ArrayList<int[][]> platform3 = new ArrayList<int[][]>();
	
	//Platform 1
	
	
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
		platformsLevel1.add(p1_1);
		platformsLevel1.add(p1_2);
		
		//Add the lists of each level to the list of lists
		platformArrays.add(platformsLevel1);
	}
	
	public int[] getLevelX(int level){
		return xGroundArrays.get(level-1);
	}
	
	public int[] getLevelY(int level){
		return yGroundArrays.get(level-1);
	}
	
	public int[] getPlatformUpperX(int level, int platform){
		return platformArrays.get(level-1).get(platform-1)[0];
	}
	public int[] getPlatformUpperY(int level, int platform){
		return platformArrays.get(level-1).get(platform-1)[1];
	}
	public int[] getPlatformLowerX(int level, int platform){
		return platformArrays.get(level-1).get(platform-1)[2];
	}
	public int[] getPlatformLowerY(int level, int platform){
		return platformArrays.get(level-1).get(platform-1)[3];
	}
	
	public int getNumberOfPlatforms(int level){
		return platformArrays.get(level-1).size();
	}
	
}

package com.example.pineapple;

import java.util.ArrayList;

import android.util.Log;

public class LevelLoader {
	private final String TAG = LevelLoader.class.getSimpleName();
	
	//Ground arrays for level
	private int[][] ground;
	
	private int[][] p1;
	private int[][] p2;
	private int[][] p3;
	private int[][] p4;
	private int[][] p5;
	private int[][] p6;
	
	//Matrix syntax:
		//p1 means platform 1
		//The rows of the matrix represents: 
		//UpperX
		//UpperY
		//LowerX
		//LowerY
	
	//List for platforms
	private ArrayList<int[][]> platforms = new ArrayList<int[][]>();
	
	//List for enemy-info 
	//A row should contain {startX, startY, spawnX?, type}
	//spawnX is the x position the protagonist has to reach in order for the enemy to spawn
	private ArrayList<int[]> enemies = new ArrayList<int[]>();
	
	//List for trees
	private ArrayList<Integer> trees = new ArrayList<Integer>();
	
	
	
	public LevelLoader(int level){
		
		switch(level){
		
		case 1: 
			//
			ground = new int[][]{
					{-350, -300, 0,  100, 150, 300, 400, 420, 480},
					{40,   95,   95, 80,  95,  20,  150, 140, 135}
			};

			//Platform 1
			p1 = new int[][]{
					{0, 10, 50},
					{40, 50, 50},
					{0, 10, 50},
					{40, 60, 50}
			};
			platforms.add(p1);

			//Platform 2
			p2 = new int[][]{
					{150, 250, 260},
					{50, 10, 13},
					{150, 220, 260},
					{50, 25, 13}
			};
			platforms.add(p2);
			
			//Enemies
			enemies.add(new int[]{100, 20, 80, 2});
			enemies.add(new int[]{100, 30, 80, 3});
			
			//Trees
			trees.add(0);
			trees.add(50);
			trees.add(100);
			break;
		case 2:
			//Ground
			ground = new int[2][500];
			
			for (int i = 0; i<500; i++) {
				ground[0][i] = i*20;
				ground[1][i] = 50 + (int)(25*Math.cos((double)ground[0][i]/200*Math.PI));
			}
			//Enemies
			enemies.add(new int[]{200, 0, 100, 3});
			enemies.add(new int[]{200, 0, 150, 3});
			enemies.add(new int[]{200, 0, 200, 3});
			enemies.add(new int[]{200, 0, 250, 3});
			enemies.add(new int[]{200, 0, 300, 3});
			enemies.add(new int[]{200, 0, 350, 3});
			enemies.add(new int[]{200, 20, 100, 2});
			enemies.add(new int[]{200, 20, 150, 2});
			enemies.add(new int[]{200, 20, 200, 2});
			enemies.add(new int[]{200, 20, 250, 2});
			enemies.add(new int[]{200, 20, 300, 2});
			enemies.add(new int[]{200, 20, 350, 2});
			
			break;
		}
	
	}
	
	public int[] getLevelX(int index){
		return ground[0];
	}
	
	public int[] getLevelY(int level){
		return ground[1];
	}
	
	public int[] getPlatformUpperX(int platform){
		return platforms.get(platform)[0];
	}
	public int[] getPlatformUpperY(int platform){
		return platforms.get(platform)[1];
	}
	public int[] getPlatformLowerX(int platform){
		return platforms.get(platform)[2];
	}
	public int[] getPlatformLowerY(int platform){
		return platforms.get(platform)[3];
	}
	
	public int getNumberOfPlatforms(){
		return platforms.size();
	}
	
	public int[] getEnemyData(int enemy){
		return enemies.get(enemy);
	}
	
	public int getNumberOfEnemies(){
		return enemies.size();
	}
	
	public ArrayList<Integer> getTrees(){
		return trees;
	}
	
	public int getNumberOfTrees(){
		return trees.size();
	}
	
}

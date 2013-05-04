package com.example.pineapple;

import java.util.ArrayList;

import android.util.Log;

public class LevelLoader {
	private final String TAG = LevelLoader.class.getSimpleName();
	
	//Ground arrays for level
	private int[][] ground;
	private int[] startPos = new int[]{10, 0}; //x- and y-position of protagonist when the level begins
	
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
	
	//List for trees (just an x-position)
	private ArrayList<Integer> trees = new ArrayList<Integer>();
	//List for rocks {x position, type, size}
	private ArrayList<int[]> rocks = new ArrayList<int[]>();	
	
	
	//Checkpoint array for the tutorial, the mentor will stop at these locations and give some advice
	private int[] checkpoints;
	
	
	public LevelLoader(int level){

		switch(level){
		case 0:
			startPos = new int[]{10, 70};
			ground = new int[][]{
					{0,  258, 395, 649, 725, 800, 875, 983, 1254, 1264, 1300, 1310, 1528},
					{90, 90,  208, 208, 160, 146, 160, 208, 208,  240,  240,  208,  208}
			};
			
			checkpoints = new int[]{30, 200, 200, 250, 420, 530, 600, 790, 790, 950, 1240, 1240, 1400, 1400};
			
			//Platform 1
			p1 = new int[][]{
					{889, 1042},
					{133, 133},
					{889, 890, 949, 1041, 1042},
					{133, 140, 154, 140, 133}
			};
			platforms.add(p1);
			
			enemies.add(new int[]{1280, 220, 1000, 1});
			
			trees.add(800);
			
			rocks.add(new int[]{780, 1, 20});
			rocks.add(new int[]{820, 2, 23});
			break;
		case 1: 
			//
			ground = new int[][]{
					{-350, -50,  -40,   -10,   0,   100, 150, 300, 400, 420, 480},
					{40,   95,   130,   130,   95,  80,  95,  20,  150, 140, 135}
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
			enemies.add(new int[]{100, 20, 80, 1});
			//enemies.add(new int[]{100, 30, 80, 3});

			//Trees
			trees.add(0);
			trees.add(50);
			trees.add(100);
			//Rocks
			rocks.add(new int[]{20, 1, 20});
			rocks.add(new int[]{80, 2, 15});
			rocks.add(new int[]{160, 3, 15});
			rocks.add(new int[]{180, 3, 20});
			rocks.add(new int[]{200, 3, 25});
			rocks.add(new int[]{220, 3, 30});
			
			break;
		case 2:
			//Ground
			ground = new int[2][500];

			for (int i = 0; i<500; i++) {
				ground[0][i] = i*20;
				ground[1][i] = 50 + (int)(25*Math.cos((double)ground[0][i]/200*Math.PI));
			}
			//Enemies
			for (int j = 1; j <= 25; j++){
				int spawnPoint = j*50 + 100;
				enemies.add(new int[]{spawnPoint + 100, 10, spawnPoint + 40, 1});
				enemies.add(new int[]{spawnPoint + 100, 20, spawnPoint + 20, 2});
				enemies.add(new int[]{spawnPoint + 100, 0, spawnPoint, 3});

			}
			break;
		case 3:
			startPos = new int[]{10, -20};
			ground = new int[2][20];
			for (int i = 0; i<20; i++) {
				ground[0][i] = i*20;
				ground[1][i] = 100-(int)(600*Math.exp(-ground[0][i]/100.));
			}
			enemies.add(new int[]{20, -40, -40, 1});
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
	
	public ArrayList<int[]> getRocks(){
		return rocks;
	}
	
	public int getNumberOfRocks(){
		return trees.size();
	}
	
	public int getStartX(){
		return startPos[0];
	}
	
	public int getStartY(){
		return startPos[1];
	}
	
	public int[] getCheckpoints(){
		return checkpoints;
	}
	
}

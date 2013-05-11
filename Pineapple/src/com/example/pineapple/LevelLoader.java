package com.example.pineapple;

import java.util.ArrayList;

import android.util.Log;

public class LevelLoader {
	private final String TAG = LevelLoader.class.getSimpleName();

	//Ground arrays for level
	private int[][] ground;
	private int[] startPos = new int[]{10, 0}; //x- and y-position of protagonist when the level begins
	private int finishX;
	private int maxLengthPoint;

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
	//List for trees {x position, baseType, topType, back or foreground}
	private ArrayList<int[]> trees = new ArrayList<int[]>();
	//List for rocks {x position, type, size, back or foreground}
	private ArrayList<int[]> rocks = new ArrayList<int[]>();
	//List for flowers {x position, type}
	private ArrayList<int[]> flowers = new ArrayList<int[]>();


	//Checkpoint array for the tutorial, the mentor will stop at these locations and give some advice
	private int[] checkpoints;


	public LevelLoader(int level){

		switch(level){
		case 0:
			startPos = new int[]{10, 70};
			finishX = 1750;
			ground = new int[][]{
					{-100,  258, 395, 580, 590, 600, 610, 620, 649, 725, 800, 875, 983, 1254, 1264, 1300, 1310, 1528, 1600, 1610, 1900},
					{90,    90,  208, 208, 203, 200, 203, 208, 208, 160, 146, 160, 208, 208,  240,  240,  208,  208,  270,  275,  275 }
			};			
			checkpoints = new int[]{30, 200, 200, 250, 420, 530, 600, 790, 790, 950, 1240, 1240, 1400, 1400, 1700, 1700};

			//Platform 1
			p1 = new int[][]{
					{889, 1042},
					{133, 133},
					{889, 890, 949, 1041, 1042},
					{133, 140, 154, 140, 133}
			};
			platforms.add(p1);

			enemies.add(new int[]{1280, 220, 1000, 1});
			enemies.add(new int[]{1790, 220, 1600, 1});
			enemies.add(new int[]{1760, 220, 1600, 1});

			trees.add(new int[]{100, 1, 2, 0});
			trees.add(new int[]{130, 2, 1, 1});
			trees.add(new int[]{230, 0, 0, 0});
			trees.add(new int[]{450, 2, 0, 1});
			trees.add(new int[]{800, 0, 0, 0});
			trees.add(new int[]{950, 1, 1, 0});
			trees.add(new int[]{1360, 0, 0, 1});
			trees.add(new int[]{1800, 2, 2, 0});

			rocks.add(new int[]{780, 0, 20, 0});
			rocks.add(new int[]{820, 1, 23, 0});
			rocks.add(new int[]{320, 2, 30, 1});
			rocks.add(new int[]{1380, 1, 20, 0});
			rocks.add(new int[]{1770, 0, 26, 0});
			rocks.add(new int[]{1810, 2, 19, 0});
			break;
		case 1: 
			//
			finishX = 990;
			/*ground = new int[][]{
					{-350, -50,  -40,   -10,   0,   100, 150, 300, 400, 420, 600},
					{40,   95,   130,   130,   95,  80,  95,  20,  150, 140, 135}
			};*/

			ground = new int[][]{
					{-155, 322, 368, 417, 518, 609, 691, 782, 863, 900, 1000},
					{83,   83,  103, 156, 191, 191, 189, 167, 95,  95,  130}
			};

			//Platform 1
			p1 = new int[][]{
					{594, 656, 662, 715, 759, 829},
					{173, 157, 142, 131, 104, 85},
					{594, 612, 770, 816, 829},
					{173, 176, 146, 113, 85}
			};
			platforms.add(p1);
			
			p2 = new int[][]{
					{700, 759},
					{90, 90},
					{700, 730, 759},
					{90, 100, 90}
			};
			platforms.add(p2);

			//Enemies
			enemies.add(new int[]{300, 20, 150, 1});

			//Trees
			trees.add(new int[]{0, 1, 0, 0});
			trees.add(new int[]{50, 2, 0, 1});
			trees.add(new int[]{100, 0, 2, 0});
			trees.add(new int[]{140, 1, 1, 1});
			trees.add(new int[]{180, 1, 0, 0});
			trees.add(new int[]{240, 2, 0, 1});
			trees.add(new int[]{300, 0, 2, 0});
			trees.add(new int[]{320, 0, 1, 1});
			//Rocks
			rocks.add(new int[]{20, 0, 20, 0});
			rocks.add(new int[]{80, 1, 15, 0});
			rocks.add(new int[]{160, 2, 15, 0});
			rocks.add(new int[]{180, 2, 20, 0});
			rocks.add(new int[]{200, 2, 25, 0});
			rocks.add(new int[]{220, 2, 30, 0});

			break;
		case 2: //the hunt
			//-------------------------------------------------------------------------//
			finishX = 5900;
			startPos = new int[]{10, 40};
			//Ground
			maxLengthPoint = 300;
			ground = new int[2][maxLengthPoint+1];
			ground[0][0] = -100;
			ground[1][0] = 50;

			for (int i = 1; i<maxLengthPoint; i++) {
				ground[0][i] = i*20;
				ground[1][i] = 50 + (int)(25*Math.cos((double)ground[0][i]/200*Math.PI));
			}
			ground[0][maxLengthPoint] = maxLengthPoint*20;
			ground[1][maxLengthPoint] = 50 + (int)(25*Math.cos((double)ground[0][maxLengthPoint-1]/200*Math.PI));

			//Enemies
			enemies.add(new int[]{this.getStartX() + 200, 0, this.getStartX(), 3});
			int spawnPoint;
			int endPoint = ground[0][maxLengthPoint-1];
			for (int i = 0; i < 200; i++) {
				spawnPoint = i*50 + 200;
				if (spawnPoint < endPoint - 800){
					if (i % 8 == 0)
						enemies.add(new int[]{spawnPoint - 200, 0, spawnPoint, 1});
					if (i % 6 == 0)
						enemies.add(new int[]{spawnPoint - 150, 0, spawnPoint, 2});
					if (i % 3 == 0)
						enemies.add(new int[]{spawnPoint + 150, 0, spawnPoint, 3});
				} else
					break;
			}
			for (int i = 0; i < 20; i++){
				enemies.add(new int[]{finishX - 730 + i*10, 0, finishX - 500, 2});
			}

			//Trees
			for (int i = 1; i <= 100; i++){
				if( i % 2 == 0)
					trees.add(new int[]{50*i, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			}
			trees.add(new int[]{endPoint - 50, 0, 1, 0});
			trees.add(new int[]{-50, 2, 0, 0});
			trees.add(new int[]{-20, 1, 1, 0});
			//Rocks
			rocks.add(new int[]{50, 1, 20, 0});
			rocks.add(new int[]{2985, 1, 20, 1});
			rocks.add(new int[]{3000, 2, 20, 0});
			rocks.add(new int[]{3015, 1, 20, 1});
			for (int i = 1; i <= 4; i++)
				rocks.add(new int[]{finishX - 150*i, (int)(4*Math.random()), 20, 0});
			//Flowers
			flowers.add(new int[]{0, 0});
			flowers.add(new int[]{endPoint - 250, 0});
			flowers.add(new int[]{endPoint - 120, 0});
			flowers.add(new int[]{finishX + 20, 0});
			break;
			//-------------------------------------------------------------------------//
		case 3:
			finishX = 380;
			startPos = new int[]{10, -20};
			ground = new int[2][21];
			for (int i = 0; i<20; i++) {
				ground[0][i] = i*20;
				ground[1][i] = 100-(int)(600*Math.exp(-ground[0][i]/100.));
			}
			ground[0][20] = 20*25;
			ground[1][20] = 100-(int)(600*Math.exp(-ground[0][19]/100.));
			enemies.add(new int[]{20, -40, -40, 1});
			break;

		case 4: //for meny background
			finishX = 350;
			startPos = new int[]{10,150};
			//Ground
			ground = new int[][]{
					{-100, 500},
					{150, 150}
			};
			//Enemy
			enemies.add(new int[]{10 - 100, 100, 10, 1});
			enemies.add(new int[]{10 - 100, 100, 10, 1});
			enemies.add(new int[]{10 - 100, 100, 10, 2});
			enemies.add(new int[]{60 - 100, 100, 60, 1});
			enemies.add(new int[]{60 - 100, 100, 60, 1});
			enemies.add(new int[]{60 - 100, 100, 60, 2});
			enemies.add(new int[]{110 - 100, 100, 110, 1});
			enemies.add(new int[]{110 - 100, 100, 110, 2});
			enemies.add(new int[]{110 - 100, 100, 110, 3});
			enemies.add(new int[]{160 - 100, 100, 160, 1});
			enemies.add(new int[]{160 - 100, 100, 160, 2});
			enemies.add(new int[]{160 - 100, 100, 160, 3});
			//Tree
			trees.add(new int[]{130, 2, 0, 0});
			trees.add(new int[]{400, 1, 2, 0});
			//Rock
			rocks.add(new int[]{70, 3, 20, 0});
			//Flower
			flowers.add(new int[]{80, 0});
			flowers.add(new int[]{finishX + 10, 0});
			break;
		case 5://downhill
			//Ground
			ground = new int[2][14];
			ground[0][0] = -100;
			ground[1][0] = 50;
			ground[0][1] = 10;
			ground[1][1] = 60;
			ground[0][2] = 20;
			ground[1][2] = 100;
			ground[0][3] = 400;
			ground[1][3] = 10;
			ground[0][4] = 1400;
			ground[1][4] = 1500;
			ground[0][5] = 2000;
			ground[1][5] = 1400;
			ground[0][6] = 2600;
			ground[1][6] = 1300;
			ground[0][7] = 3000;
			ground[1][7] = 1200;
			ground[0][8] = 3100;
			ground[1][8] = 1150;
			ground[0][9] = 3200;
			ground[1][9] = 1100;
			ground[0][10] = 3300;
			ground[1][10] = 1100;
			ground[0][11] = 4000;
			ground[1][11] = 2000;
			ground[0][12] = 4500;
			ground[1][12] = 2000;
			ground[0][13] = 4700;
			ground[1][13] = 1700;
			//Start and finish
			startPos = new int[]{10,50};
			finishX = 4400;
			//Enemies
			enemies.add(new int[]{100 + 200, 0, 100, 1});
			enemies.add(new int[]{200 + 200, 0, 200, 1});
			//chase nr1 ---
			enemies.add(new int[]{401 - 20, 0, 401, 1});
			enemies.add(new int[]{401 - 40, 0, 401, 1});
			enemies.add(new int[]{401 - 60, 0, 401, 1});
			//          ---
			enemies.add(new int[]{400 + 200, 0, 500, 1});
			enemies.add(new int[]{700 - 200, 0, 700, 1});
			enemies.add(new int[]{900 - 200, 0, 900, 1});
			enemies.add(new int[]{1000 - 200, 1400, 1000, 1});
			enemies.add(new int[]{1100 - 200, 1400, 1100, 1});
			enemies.add(new int[]{1200 - 200, 1400, 1200, 1});
			enemies.add(new int[]{1900 - 200, 1400, 1900, 2});
			enemies.add(new int[]{2100 - 200, 1300, 2100, 2});
			enemies.add(new int[]{2300 - 200, 1300, 2300, 2});
			enemies.add(new int[]{2500 + 200, 1200, 2500, 2});
			enemies.add(new int[]{2700 + 200, 1200, 2700, 2});
			enemies.add(new int[]{2900 + 200, 1000, 2900, 2});
			enemies.add(new int[]{3100 + 200, 1000, 3100, 1});
			//chase nr2 ---
			enemies.add(new int[]{3300 - 20, 1100, 3300, 1});
			enemies.add(new int[]{3300 - 30, 1100, 3300, 1});
			enemies.add(new int[]{3300 - 40, 1100, 3301, 1});
			enemies.add(new int[]{3300 - 50, 1000, 3301, 1});
			enemies.add(new int[]{3300 - 60, 1000, 3301, 1});
			//          ---
			enemies.add(new int[]{3600 - 200, 1000, 3600, 2});
			enemies.add(new int[]{3600 - 180, 1000, 3600, 2});
			enemies.add(new int[]{3600 - 160, 1000, 3600, 2});
			enemies.add(new int[]{3600 - 140, 1000, 3600, 2});
			enemies.add(new int[]{3600 - 120, 1000, 3600, 2});
			//final stand
			enemies.add(new int[]{4200 - 160, 1990, 4200, 1});
			enemies.add(new int[]{4200 - 180, 1990, 4200, 1});
			enemies.add(new int[]{4200 - 200, 1990, 4200, 1});
			enemies.add(new int[]{4200 + 300, 1990, 4200, 2});
			enemies.add(new int[]{4200 + 320, 1990, 4200, 2});
			enemies.add(new int[]{4200 + 340, 1990, 4200, 2});
			enemies.add(new int[]{4200 + 360, 1990, 4200, 2});
			enemies.add(new int[]{4200 + 180, 1990, 4200, 3});
			enemies.add(new int[]{4200 + 200, 1990, 4200, 3});
			//trees
			for (int j = 1; j <= 160; j++){
				if( j % 4 == 0 && (j*20 < 400 || j*20 >= 1400))
					trees.add(new int[]{20*j, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			}
			trees.add(new int[]{4100, (int)(3*Math.random()), (int)(3*Math.random()), 1});
			trees.add(new int[]{4200, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{4300, (int)(3*Math.random()), (int)(3*Math.random()), 1});
			//rocks
			rocks.add(new int[]{4150, 1, 20, 0});
			rocks.add(new int[]{4250, 3, 20, 0});
			//flowers
			for (int j = 0; j < 4 ; j++){
				if(j != 2)
					flowers.add(new int[]{380 + 5*j, 0});
			}
			for (int j = 0; j < 2 ; j++){
				flowers.add(new int[]{3280 + 7*j, 0});
			}
			flowers.add(new int[]{4420, 0});
			flowers.add(new int[]{4435, 0});
			flowers.add(new int[]{4460, 0});
			flowers.add(new int[]{1, 0});
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

	public ArrayList<int[]> getTrees(){
		return trees;
	}

	public int getNumberOfTrees(){
		return trees.size();
	}

	public ArrayList<int[]> getRocks(){
		return rocks;
	}

	public int getNumberOfRocks(){
		return rocks.size();
	}

	public ArrayList<int[]> getFlowers(){
		return flowers;
	}

	public int getNumberOfFlowers(){
		return flowers.size();
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

	public int getFinishX(){
		return finishX;
	}
}

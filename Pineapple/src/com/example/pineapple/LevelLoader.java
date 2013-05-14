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
	private int[][] p7;
	private int[][] p8;
	private int[][] p9;
	private int[][] p10;

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
	//A row should contain {startX, startY, spawnX?, spawnY?, type}
	//spawnX is the x position the protagonist has to reach in order for the enemy to spawn
	//spawnY is the y position the protagonist has to reach in order for the enemy to spawn (remember y is "upside down")
	//spawnY is optional, if not set spawnY = 10000 (spawn right away)
	private ArrayList<int[]> enemies = new ArrayList<int[]>();
	//List for trees {x position, baseType, topType, back or foreground}
	private ArrayList<int[]> trees = new ArrayList<int[]>();
	//List for rocks {x position, type, size, back or foreground}
	private ArrayList<int[]> rocks = new ArrayList<int[]>();
	//List for flowers {x position, type}
	private ArrayList<int[]> flowers = new ArrayList<int[]>();
	//List for skeletons {x position, add more to y if visible above ground}
	private ArrayList<int[]> skeletons = new ArrayList<int[]>();
	//List for butterflies, butterflies.add(new Butterfly(x position, y position, (optional) multiplier fly)}
	private ArrayList<Butterfly> butterflies = new ArrayList<Butterfly>();
	//List for birds, birds.add(new Bird(x position, y position, (optional) killBird (if bird can be killed))}
	private ArrayList<Bird> birds = new ArrayList<Bird>();
	//Hints
	private ArrayList<Hint> hints = new ArrayList<Hint>();


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
			checkpoints = new int[]{30, 200, 200, 250, 420, 530, 600, 780, 780, Const.tutorialFruitX, 950, 1240, 1240, 1400, 1400, 1700, 1700};

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
			startPos = new int[]{10, 0};
			finishX = 2000;
			/*ground = new int[][]{
					{},
					{}
			};*/

			ground = new int[][]{
					{-155, 322, 368, 417, 518, 609, 691, 782, 863, 900, 1000, 1100, 1150, 1450, 1460, 1490,  1500, 1600, 1650, 1750, 1800, 1820, 1900, 1920, 2100},
					{83,   83,  103, 156, 191, 191, 189, 167, 95,  95,  130, 110,   100,   95,   130,  130,  95,   80,   95,  40,   20,   40,   150,  140,  135}
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
					{1870, 2000},
					{0, -10},
					{1870, 1900, 1950, 2000},
					{0, 10, 10, -10}
			};
			platforms.add(p2);
	
			//Enemies
			enemies.add(new int[]{300, 20, 150, 1});
			enemies.add(new int[]{600, 30, 400, 1});
			enemies.add(new int[]{1200, 20, 1300, 1});
			enemies.add(new int[]{1990, -20, 1750, 1});

			//Trees
			trees.add(new int[]{0, 1, 0, 0});
			trees.add(new int[]{140, 1, 1, 1});
			trees.add(new int[]{300, 0, 2, 0});
			trees.add(new int[]{500, 1, 2, 0});
			trees.add(new int[]{700, 0, 1, 0});
			trees.add(new int[]{800, 2, 1, 0});
			trees.add(new int[]{950, 1, 0, 0});
			trees.add(new int[]{1100, 1, 2, 0});
			trees.add(new int[]{1300, 2, 1, 0});
			trees.add(new int[]{1400, 0, 0, 0});
			trees.add(new int[]{1700, 2, 1, 0});
			trees.add(new int[]{1950, 0, 0, 0});
			//Rocks
			rocks.add(new int[]{20, 0, 20, 0});
			rocks.add(new int[]{80, 1, 15, 0});
			rocks.add(new int[]{160, 2, 15, 0});
			rocks.add(new int[]{500, 2, 25, 0});
			rocks.add(new int[]{510, 3, 18, 1});

			//Hints
			hints.add(new Hint(40, 85, "Beware! Continue at your own risk! Strange creatures have been spotted in this area!"));
			hints.add(new Hint(1250, 100, "Take care! I've dug a large hole up ahead!      -The Mentor"));
			break;
		case 2://Hide and seek, watch out
			//Start and finish
			startPos = new int[]{20, 0};
			finishX = 2500;
			//Ground
			ground = new int[2][4];
			ground[0][0] = -100;
			ground[1][0] = -20;
			ground[0][1] = 0;
			ground[1][1] = 0;
			ground[0][2] = 2100;
			ground[1][2] = 0;
			ground[0][3] = 2600;
			ground[1][3] = -40;
			//Enemies
			//---Drones
			enemies.add(new int[]{140, 0, 20, 1});
			enemies.add(new int[]{200, 0, 120, 1});
			enemies.add(new int[]{230, 0, 160, 1});
			enemies.add(new int[]{300, 0, 240, 1});
			enemies.add(new int[]{400, 0, 340, 1});
			enemies.add(new int[]{420, 0, 490, 1});
			enemies.add(new int[]{420, 0, 500, 1});
			enemies.add(new int[]{660, 0, 600, 1});
			enemies.add(new int[]{720, 0, 660, 1});
			enemies.add(new int[]{800, 0, 850, 1});
			enemies.add(new int[]{1020, 0, 960, 1});
			enemies.add(new int[]{1200, 0, 1250, 1});
			enemies.add(new int[]{1300, 0, 1355, 1});
			enemies.add(new int[]{1420, 0, 1355, 1});
			enemies.add(new int[]{1620, 0, 1560, 1});
			enemies.add(new int[]{1660, 0, 1600, 1});
			enemies.add(new int[]{1740, 0, 1790, 1});
			enemies.add(new int[]{1840, 0, 1790, 1});
			enemies.add(new int[]{1950, 0, 1890, 1});
			enemies.add(new int[]{1990, 0, 2020, 1});
			//---Ninjas
			enemies.add(new int[]{2230, -60, 2250, 2});
			enemies.add(new int[]{2230, -60, 2260, 2});
			enemies.add(new int[]{2230, -60, 2270, 2});
			enemies.add(new int[]{2230, -60, 2280, 2});
			//---Tanks
			enemies.add(new int[]{0, 0, 1250, 3});
			//Trees
			trees.add(new int[]{-40, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{2200, (int)(3*Math.random()), (int)(3*Math.random()), 1});
			//Rocks
			rocks.add(new int[]{50, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{120, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{140, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{150, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{200, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{230, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{250, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{280, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{300, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{330, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{340, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{350, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{400, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{410, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{420, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{450, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{470, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{500, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{540, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{560, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{590, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{600, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{620, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{650, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{660, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{680, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{700, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{720, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{740, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{770, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{800, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{830, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{840, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{850, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{900, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{920, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{950, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{960, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{980, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{990, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1020, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1040, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1080, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1120, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1140, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{1180, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{1200, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1230, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1250, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1280, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1300, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1330, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{1340, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1350, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1400, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1410, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{1420, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1450, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1470, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1500, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{1540, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1560, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1590, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1600, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{1620, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1650, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1660, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1680, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1700, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{1720, (int)(4*Math.random()), 25, 0});
			rocks.add(new int[]{1740, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1770, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1800, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1830, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1840, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1850, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{1900, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1920, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1950, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1960, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1980, (int)(4*Math.random()), 35, 0});
			rocks.add(new int[]{1990, (int)(4*Math.random()), 40, 1});
			//Flowers
			flowers.add(new int[]{-10, 0});
			flowers.add(new int[]{-20, 0});
			flowers.add(new int[]{-40, 0});
			flowers.add(new int[]{-45, 0});
			flowers.add(new int[]{2360, 0});
			flowers.add(new int[]{2380, 0});
			flowers.add(new int[]{2410, 0});
			//Skeletons
			skeletons.add(new int[]{1200, 0});
			//Butterfly
			butterflies.add(new Butterfly(-8, -8));
			//Birds
			birds.add(new Bird(-60, -40, false));
			break;
		case 3:
			finishX = 900;
			startPos = new int[]{10, -20};
			ground = new int[2][22];
			for (int i = 0; i<20; i++) {
				ground[0][i] = i*20;
				ground[1][i] = 100-(int)(600*Math.exp(-ground[0][i]/100.));
			}
			ground[0][20] = 20*25;
			ground[1][20] = 100-(int)(600*Math.exp(-ground[0][19]/100.));
			ground[0][21] = 1000;
			ground[1][21] = 100;
			p1 = new int[][]{
					{500, 650, 700},
					{50, 40, -20},
					{500, 600, 700},
					{50, 50, 40}
			};
			p2 = new int[][]{
					{600, 699},
					{10, -20},
					{600, 640, 699},
					{10, 10, -20}
			};
			platforms.add(p1);
			platforms.add(p2);
			enemies.add(new int[]{20, -40, -40, 1});
			break;

		case 4: //Short trip (and for meny background (set YPadding = 20?))
			//Start and finish
			finishX = 350;
			startPos = new int[]{10,150};
			//Ground
			ground = new int[][]{
					{-100, -2, -1, 500},
					{0, 100, 150, 150}
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
			flowers.add(new int[]{-10, 0});
			flowers.add(new int[]{80, 0});
			flowers.add(new int[]{finishX + 10, 0});
			//Skeletons
			skeletons.add(new int[]{220, 0});
			skeletons.add(new int[]{-50, 40});
			//Butterfly
			butterflies.add(new Butterfly(80 + 2, 150 - 8));
			//Birds
			birds.add(new Bird(finishX + 8, 150 - 43, false));
			break;
		case 5://Go downhill			
			//Start and finish
			startPos = new int[]{10,50};
			finishX = 4400;
			//Ground
			ground = new int[2][14];
			ground[0][0] = -100;
			ground[1][0] = 50;
			ground[0][1] = 10;
			ground[1][1] = 60;
			ground[0][2] = 20;
			ground[1][2] = 100;
			ground[0][3] = 400; //Down
			ground[1][3] = 10;
			ground[0][4] = 1400; //Here
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
			ground[0][10] = 3300; //Down
			ground[1][10] = 1100; 
			ground[0][11] = 4000; //Here
			ground[1][11] = 2000; 
			ground[0][12] = 4500;
			ground[1][12] = 2000;
			ground[0][13] = 4800;
			ground[1][13] = 1700;
			//Enemies
			enemies.add(new int[]{100 + 200, 0, 100, 1});
			enemies.add(new int[]{200 + 200, 0, 200, 1});
			//--- Chase nr1 ---
			enemies.add(new int[]{401 - 20, 0, 401, 1});
			enemies.add(new int[]{401 - 40, 0, 401, 1});
			enemies.add(new int[]{401 - 60, 0, 401, 1});
			//---           ---
			enemies.add(new int[]{400, 0, 500, 1});
			enemies.add(new int[]{410, 0, 500, 1});
			enemies.add(new int[]{420, 0, 500, 1});
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
			//--- Chase nr2 ---
			enemies.add(new int[]{3300 - 20, 1100, 3300, 1});
			enemies.add(new int[]{3300 - 30, 1100, 3300, 1});
			enemies.add(new int[]{3300 - 40, 1100, 3300, 1});
			enemies.add(new int[]{3300 - 50, 1000, 3300, 1});
			enemies.add(new int[]{3300 - 60, 1000, 3300, 1});
			//---           ---
			enemies.add(new int[]{3600 - 200, 1000, 3600, 2});
			enemies.add(new int[]{3600 - 180, 1000, 3600, 2});
			enemies.add(new int[]{3600 - 160, 1000, 3600, 2});
			enemies.add(new int[]{3600 - 140, 1000, 3600, 2});
			//--- Final stand ---
			enemies.add(new int[]{4200 - 300, 1800, 4200, 1});
			enemies.add(new int[]{4200 - 320, 1800, 4200, 1});
			enemies.add(new int[]{4200 + 200, 1990, 4200, 3});
			enemies.add(new int[]{4300 + 100, 2000, 4300, 2});
			enemies.add(new int[]{4300 + 120, 2000, 4300, 2});
			enemies.add(new int[]{4300 + 140, 2000, 4300, 2});
			//---             ---
			//Trees
			for (int j = 1; j <= 160; j++){
				if( j % 4 == 0 && (j*20 < 400 || j*20 >= 1400))
					trees.add(new int[]{20*j, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			}
			trees.add(new int[]{4100, (int)(3*Math.random()), (int)(3*Math.random()), 1});
			trees.add(new int[]{4200, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{4300, (int)(3*Math.random()), (int)(3*Math.random()), 1});
			//Rocks
			rocks.add(new int[]{4150, 1, 20, 0});
			rocks.add(new int[]{4250, 3, 20, 0});
			rocks.add(new int[]{-50, 2, 50, 0});
			//Flowers
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
			//Skeletons
			skeletons.add(new int[]{800, 50});
			skeletons.add(new int[]{3500, 50});
			//Butterfly
			butterflies.add(new Butterfly(4435 + 2, 2000 - 8));
			//Birds
			birds.add(new Bird(150, 20, true));
			birds.add(new Bird(3230, 1100 - 43, true));
			break;
		case 6: //Up and away
			//Start and finish
			startPos = new int[]{10, 20};
			finishX = 1100;
			//Ground
			ground = new int[2][14];
			ground[0][0] = -200;
			ground[1][0] = 20;
			ground[0][1] = 10;
			ground[1][1] = 20;
			ground[0][2] = 100;
			ground[1][2] = 30;
			ground[0][3] = 300;
			ground[1][3] = 100;
			ground[0][4] = 500;
			ground[1][4] = 140;
			ground[0][5] = 600;
			ground[1][5] = 160;
			ground[0][6] = 610; //Ledge 1
			ground[1][6] = 120; 
			ground[0][7] = 700; //
			ground[1][7] = 120; 
			ground[0][8] = 770; //Ledge 2
			ground[1][8] = -270; 
			ground[0][9] = 785; //
			ground[1][9] = -270;
			ground[0][10] = 840;//Ledge 3
			ground[1][10] = -640;
			ground[0][11] = 855;//
			ground[1][11] = -640;
			ground[0][12] = 900; //Final ledge
			ground[1][12] = -1000;
			ground[0][13] = 1200;
			ground[1][13] = -1000;
			//Platform uppx uppy lowx lowy
			p1 = new int[][]{
					{10, 100, 200, 300, 400, 500, 580},
					{-70, -50, -50, -20, 20, 60, 90},
					{10, 70, 100, 300, 400, 500, 580},
					{-70, -30, -30, 20, 50, 80, 90}
			};
			p2 = new int[][]{
					{50, 200, 300, 400, 500, 570, 750},
					{-100, -140, -150, -180, -200, -200, -270},
					{50, 100, 300, 400, 500, 570, 750},
					{-100, -100, -110, -140, -170, -180, -270}
			};
			p3 = new int[][]{
					{10, 100, 200, 300, 400, 500, 570, 730},
					{-500, -470, -450, -390, -350, -330, -320, -290},
					{10, 70, 100, 300, 400, 500, 570, 730},
					{-500, -450, -430, -350, -320, -300, -300, -290}
			};
			p4 = new int[][]{
					{50, 200, 300, 400, 500, 570, 800},
					{-520, -550, -580, -580, -600, -620, -630},
					{50, 100, 300, 400, 500, 570, 800},
					{-520, -520, -530, -550, -550, -590, -630}
			};
			p5 = new int[][]{
					{10, 100, 200, 300, 400, 500, 570, 780},
					{-780, -780, -760, -750, -730, -700, -670, -650},
					{10, 70, 100, 300, 400, 500, 570, 780},
					{-780, -770, -750, -700, -680, -650, -650, -650}
			};
			p6 = new int[][]{
					{50, 200, 300, 400, 500, 570, 750, 850},
					{-800, -840, -850, -880, -890, -900, -950, -980},
					{50, 100, 300, 400, 500, 570, 750, 850},
					{-800, -800, -810, -820, -860, -870, -930, -980}
			};
			platforms.add(p1);
			platforms.add(p2);
			platforms.add(p3);
			platforms.add(p4);
			platforms.add(p5);
			platforms.add(p6);
			//Enemies
			enemies.add(new int[]{610, 120, 10, 1});
			enemies.add(new int[]{630, 120, 10, 1});
			enemies.add(new int[]{650, 120, 10, 1});
			enemies.add(new int[]{500, 60, 300, 100, 1});
			enemies.add(new int[]{520, 60, 300, 100, 1});
			enemies.add(new int[]{100, 20, 300, 2});
			enemies.add(new int[]{120, 20, 300, 2});
			//---Enemies on plattforms nr---
			enemies.add(new int[]{300, -20, 500, 80, 1});//1
			enemies.add(new int[]{280, -160, 0, -100, 1});//2
			enemies.add(new int[]{320, -160, 0, -100, 3});
			enemies.add(new int[]{280, -400, 600, -270, 3});//3
			enemies.add(new int[]{300, -400, 600, -270, 1});
			enemies.add(new int[]{320, -400, 600, -270, 3});
			enemies.add(new int[]{300, -580, 0, -500, 3});//4
			enemies.add(new int[]{280, -760, 700, -650, 1});//5
			enemies.add(new int[]{320, -760, 700, -650, 3});
			enemies.add(new int[]{280, -860, 0, -800, 3});//6
			enemies.add(new int[]{300, -860, 0, -800, 1});
			enemies.add(new int[]{320, -860, 0, -800, 3});
			//Trees
			trees.add(new int[]{-10, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{650, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{940, (int)(3*Math.random()), (int)(3*Math.random()), 1});
			trees.add(new int[]{1020, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{1170, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			//Rocks
			rocks.add(new int[]{-50, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{500, (int)(4*Math.random()), 17, 0});
			rocks.add(new int[]{570, (int)(4*Math.random()), 25, 1});
			//Flowers
			flowers.add(new int[]{0, 0});
			flowers.add(new int[]{630, 0});
			flowers.add(new int[]{640, 0});
			flowers.add(new int[]{645, 0});
			flowers.add(new int[]{770, 0});
			flowers.add(new int[]{773, 0});
			flowers.add(new int[]{840, 0});
			flowers.add(new int[]{1150, 0});
			//Skeletons
			skeletons.add(new int[]{640, 10});
			//Butterfly
			butterflies.add(new Butterfly(640 + 2, 120 - 8));
			butterflies.add(new Butterfly(770 + 2, -270 - 8));
			butterflies.add(new Butterfly(840 + 2, -640 - 8));
			//Birds
			birds.add(new Bird(1000, -1040, true));
			break;
		case 7://Dont fall
			//Start and finish
			startPos = new int[]{10, 20};
			finishX = 2000;
			//Ground
			ground = new int[2][6];
			ground[0][0] = -100;
			ground[1][0] = 0;
			ground[0][1] = 100;
			ground[1][1] = 0;
			ground[0][2] = 110;
			ground[1][2] = 40;
			ground[0][3] = 1810;
			ground[1][3] = 40;
			ground[0][4] = 1850;
			ground[1][4] = -50;
			ground[0][5] = 2200;
			ground[1][5] = -60;
			//Platforms
			p1 = new int[][]{
					{140, 240, 340},
					{-20, -50, -30},
					{140, 150, 240, 330, 340},
					{-20, -10, -10, -20, -30}
			};
			p2 = new int[][]{
					{390, 500, 600},
					{-20, -45, -50},
					{390, 400, 500, 580, 600},
					{-20, -15, -10, -20, -50}
			};
			p3 = new int[][]{
					{680, 800, 1000},
					{-20, -30, -50},
					{680, 700, 800, 980, 1000},
					{-20, -10, 0, -30, -50}
			};
			p4 = new int[][]{
					{1070, 1400, 1530},
					{-30, -50, -70},
					{1070, 1080, 1200, 1500, 1530},
					{-30, -20, 0, -30, -70}
			};
			p5 = new int[][]{
					{1600, 1700, 1790},
					{-30, -40, -25},
					{1600, 1700, 1780, 1790},
					{-30, 0, -10, -25}
			};
			platforms.add(p1);
			platforms.add(p2);
			platforms.add(p3);
			platforms.add(p4);
			platforms.add(p5);
			//Enemies
			enemies.add(new int[]{300, 40, 100, 2});
			enemies.add(new int[]{310, 40, 100, 2});
			enemies.add(new int[]{320, 40, 100, 2});
			enemies.add(new int[]{330, 40, 100, 3});
			enemies.add(new int[]{500, 40, 300, 2});
			enemies.add(new int[]{510, 40, 300, 2});
			enemies.add(new int[]{520, 40, 300, 3});
			enemies.add(new int[]{700, 40, 500, 2});
			enemies.add(new int[]{710, 40, 500, 2});
			enemies.add(new int[]{720, 40, 500, 3});
			//---Enemy on platform nr---
			enemies.add(new int[]{290, -30, 150, 1});//1
			enemies.add(new int[]{580, -50, 390, 1});//2
			enemies.add(new int[]{590, -50, 390, 1});
			enemies.add(new int[]{950, -40, 690, 1});//3
			enemies.add(new int[]{970, -40, 690, 1});
			enemies.add(new int[]{980, -40, 690, 1});
			enemies.add(new int[]{1460, -70, 1080, 1});//4
			enemies.add(new int[]{1480, -70, 1080, 1});
			enemies.add(new int[]{1500, -70, 1080, 1});
			enemies.add(new int[]{1520, -70, 1080, 1});
			enemies.add(new int[]{1750, -50, 1640, 1});//5
			enemies.add(new int[]{1760, -50, 1640, 1});
			enemies.add(new int[]{1770, -50, 1640, 1});
			enemies.add(new int[]{1780, -50, 1640, 1});
			enemies.add(new int[]{1790, -50, 1640, 1});
			//Trees
			trees.add(new int[]{-10, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{360, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{640, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{1030, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{1570, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{1900, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			//Rocks
			rocks.add(new int[]{130, (int)(4*Math.random()), 20, 0});
			rocks.add(new int[]{290, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{950, (int)(4*Math.random()), 40, 0});
			rocks.add(new int[]{980, (int)(4*Math.random()), 20, 1});
			rocks.add(new int[]{1530, (int)(4*Math.random()), 30, 0});
			//Flowers
			flowers.add(new int[]{0, 0});
			flowers.add(new int[]{650, 0});
			flowers.add(new int[]{660, 0});
			flowers.add(new int[]{1910, 0});
			//Skeletons
			skeletons.add(new int[]{40, 0});
			//Butterfly
			butterflies.add(new Butterfly(130 + 2, 30));
			butterflies.add(new Butterfly(1570+5, -30));
			//Birds
			birds.add(new Bird(finishX - 9, -55 - 41, false));
			break;
		case 8: //Hunt
			//Start and finish
			startPos = new int[]{10, 40};
			finishX = 5900;
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
			//Enemy
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
				if( i % 3 == 0)
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
			//Skeletons
			skeletons.add(new int[]{5400, 10});
			break;
		case 9: //How a Maze (ing)
			//Start and finish
			//startPos = new int[]{1500, -360};
			startPos = new int[]{0, 0};
			finishX = 2600;
			//Ground
			ground = new int[2][5];
			ground[0][0] = -200;
			ground[1][0] = 0;
			ground[0][1] = 2030;
			ground[1][1] = 0;
			ground[0][2] = 2050;
			ground[1][2] = -20;
			ground[0][3] = 2100;
			ground[1][3] = -340;
			ground[0][4] = 2900;
			ground[1][4] = -340;
			//Platforms
			p1 = new int[][]{
					{100, 200, 2035},
					{-40, -60, -60},
					{100, 110, 2030, 2035},
					{-40, -30, -20, -60}
			};
			p2 = new int[][]{
					{500, 600, 2000},
					{-100, -120, -120},
					{500, 510, 1990, 2000},
					{-100, -90, -110, -120}
			};
			p3 = new int[][]{
					{650, 750, 2000},
					{-160, -180, -180},
					{650, 660, 1990, 2000},
					{-160, -150, -170, -180}
			};
			p4 = new int[][]{
					{900, 1000, 2000},
					{-220, -240, -240},
					{900, 910, 1990, 2000},
					{-220, -210, -230, -240}
			};
			p5 = new int[][]{
					{1200, 1300, 2015},
					{-280, -300, -300},
					{1200, 1210, 1990, 2015},
					{-280, -270, -290, -300}
			};
			p6 = new int[][]{
					{1400, 1500, 2015},
					{-340, -360, -360},
					{1400, 1410, 1990, 2015},
					{-340, -330, -350, -360}
			};
			p7 = new int[][]{
					{1600, 1700, 2010},
					{-400, -420, -420},
					{1600, 1610, 1990, 2010},
					{-400, -390, -410, -420}
			};
			p8 = new int[][]{
					{1800, 1900, 2030},
					{-460, -480, -480},
					{1800, 1810, 1990, 2030},
					{-460, -450, -470, -480}
			};
			p9 = new int[][]{
					{1950, 2000, 2089},
					{-520, -550, -570},
					{1950, 1980, 2080, 2089},
					{-520, -500, -550, -570}
			};
			p10 = new int[][]{
					{2090, 2800},
					{-400, -1000},
					{2090, 2200, 2800},
					{-400, -400, -1000}
			};
			platforms.add(p1);
			platforms.add(p2);
			platforms.add(p3);
			platforms.add(p4);
			platforms.add(p5);
			platforms.add(p6);
			platforms.add(p7);
			platforms.add(p8);
			platforms.add(p9);
			platforms.add(p10);
			//Enemies
			enemies.add(new int[]{200, 0, 50, 1});
			enemies.add(new int[]{200, 0, 60, 1});
			enemies.add(new int[]{200, 0, 70, 1});
			enemies.add(new int[]{400, 0, 300, 1});
			enemies.add(new int[]{400, 0, 310, 1});
			//--on platform nr---
			enemies.add(new int[]{400, -60, 200, -50, 1});//1
			enemies.add(new int[]{420, -60, 200, -50, 1});
			enemies.add(new int[]{440, -60, 200, -50, 1});
			enemies.add(new int[]{800, -120, 600, -110, 1});//2
			enemies.add(new int[]{820, -120, 600, -110, 1});
			enemies.add(new int[]{840, -120, 600, -110, 1});
			enemies.add(new int[]{950, -180, 750, -170, 1});//3
			enemies.add(new int[]{970, -180, 750, -170, 1});
			enemies.add(new int[]{990, -180, 750, -170, 1});
			enemies.add(new int[]{1200, -240, 1000, -230, 1});//4
			enemies.add(new int[]{1220, -240, 1000, -230, 1});
			enemies.add(new int[]{1240, -240, 1000, -230, 1});
			enemies.add(new int[]{1500, -300, 1300, -290, 1});//5
			enemies.add(new int[]{1520, -300, 1300, -290, 1});
			enemies.add(new int[]{1540, -300, 1300, -290, 1});
			enemies.add(new int[]{1620, -420, 1550, -350, 2});//6
			enemies.add(new int[]{1640, -420, 1550, -350, 2});
			enemies.add(new int[]{1660, -420, 1550, -350, 2});
			enemies.add(new int[]{1500, -360, 1700, -350, 1});//6
			enemies.add(new int[]{1520, -360, 1700, -350, 1});
			enemies.add(new int[]{1540, -360, 1700, -350, 1});
			enemies.add(new int[]{1800, -420, 1600, -350, 3});
			enemies.add(new int[]{1900, -420, 1700, -410, 1});//7
			enemies.add(new int[]{1920, -420, 1700, -410, 1});
			enemies.add(new int[]{1990, -480, 1900, -470, 1});//8
			enemies.add(new int[]{1890, -470, 1970, -480, 2});//b8
			enemies.add(new int[]{2080, -570, 2000, -540, 1});//9
			enemies.add(new int[]{1960, -530, 2060, -560, 2});//b9
			//---
			enemies.add(new int[]{1500, 0, 2100, -580, 2});//down on ground again
			enemies.add(new int[]{1500, 0, 2100, -580, 2});
			enemies.add(new int[]{1500, 0, 2100, -580, 2});
			enemies.add(new int[]{1500, 0, 2100, -580, 2});
			enemies.add(new int[]{2400, -600, 2100, -580, 2});
			enemies.add(new int[]{2400, -600, 2100, -580, 2});
			//Trees
			trees.add(new int[]{-10, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{2300, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			trees.add(new int[]{2400, (int)(3*Math.random()), (int)(3*Math.random()), 1});
			trees.add(new int[]{2500, (int)(3*Math.random()), (int)(3*Math.random()), 0});
			//Rocks
			rocks.add(new int[]{10, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{300, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{500, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{700, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{900, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1100, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1300, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1500, (int)(4*Math.random()), 30, 0});
			rocks.add(new int[]{1700, (int)(4*Math.random()), 30, 1});
			rocks.add(new int[]{1900, (int)(4*Math.random()), 30, 0});
			//Flowers
			flowers.add(new int[]{60, 0});
			flowers.add(new int[]{70, 0});
			flowers.add(new int[]{75, 0});
			flowers.add(new int[]{85, 0});
			flowers.add(new int[]{90, 0});
			flowers.add(new int[]{95, 0});
			flowers.add(new int[]{2105, 0});
			flowers.add(new int[]{2110, 0});
			flowers.add(new int[]{2120, 0});
			flowers.add(new int[]{2125, 0});
			flowers.add(new int[]{2130, 0});
			flowers.add(new int[]{2140, 0});
			flowers.add(new int[]{2310, 0});
			flowers.add(new int[]{2320, 0});
			flowers.add(new int[]{2490, 0});
			flowers.add(new int[]{2590, 0});
			flowers.add(new int[]{2600, 0});
			flowers.add(new int[]{2605, 0});
			flowers.add(new int[]{2610, 0});
			flowers.add(new int[]{2615, 0});
			flowers.add(new int[]{2620, 0});
			//Skeletons
			skeletons.add(new int[]{150, 0});
			//Butterflies
			butterflies.add(new Butterfly(70 + 2, - 8));
			butterflies.add(new Butterfly(85 + 2, - 8, 0.5));
			butterflies.add(new Butterfly(95 + 2, - 8, -1));
			//Birds
			birds.add(new Bird(2320, -340 - 43, true));
			break;
		case 10:
			startPos = new int[]{10, 0};
			finishX = 2250;
			ground = new int[][]{
					{-100, 147, 154, 187, 208, 241, 281, 381, 479, 591, 728, 980, 1172, 1404, 1491, 1596, 1790, 2037, 2154, 2300},
					{  57,  57, 151, 200, 268, 432, 531, 683, 678, 651, 736, 770, 762,  767,  793,  860,  900,  569,  526,  526} 
			};
			
			//Roof
			p1 = new int[][]{
					{210, 240, 2300},
					{57,  15,  15},
					{210, 260, 293, 340, 405, 490, 602, 780, 1062, 1189, 1218, 1407, 1578, 2017, 2161, 2285, 2300},
					{57,  183, 410, 538, 635, 638, 615, 548, 570,  661,  706,  710,  564,  442,  453,  450,  15}
			};
			
			p2 = new int[][]{
					{727, 964, 1052},
					{680,  647, 681},
					{727, 759, 1030, 1052},
					{680, 709, 711,  681}
			};
			p3 = new int[][]{
					{1574, 1831},
					{793, 774},
					{1574, 1630, 1768, 1831},
					{793, 844, 857, 774}
			};
			p4 = new int[][]{
					{1418, 1577, 1700},
					{711, 691, 750},
					{1418, 1473, 1553, 1623, 1700},
					{711, 738, 726, 741, 750}
			};
			p5 = new int[][]{
					{1620, 1977},
					{683, 600},
					{1620, 1745, 1887, 1977},
					{683, 715, 694, 600}
			};
			
			enemies.add(new int[]{580, 640, 490, 3}); //Welcome tank
			
			enemies.add(new int[]{627, 655, 868, 2}); //Sneaky ninjas
			enemies.add(new int[]{647, 655, 868, 2});
			enemies.add(new int[]{1173, 660, 868, 2});
			enemies.add(new int[]{1190, 660, 868, 2});
			
			platforms.add(p1);
			platforms.add(p2);
			platforms.add(p3);
			platforms.add(p4);
			platforms.add(p5);
			break;
		case 11: //Final boss fight
			startPos = new int[]{0, 0};
			finishX = 100;
			//Ground
			ground = new int[2][2];
			ground[0][0] = -200;
			ground[1][0] = 0;
			ground[0][1] = 200;
			ground[1][1] = 0;
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
	
	public ArrayList<int[]> getSkeletons(){
		return skeletons;
	}

	public int getNumberOfSkeletons(){
		return skeletons.size();
	}
	
	public ArrayList<Butterfly> getButterflies(){
		return butterflies;
	}
	
	public ArrayList<Bird> getBirds(){
		return birds;
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
	
	public ArrayList<Hint> getHints(){
		return hints;
	}
}


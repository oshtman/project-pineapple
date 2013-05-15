package com.example.valentine;

public class Const {
	//Scaling coefficients, from an imagined rectangle around the protagonist
		//Use this to fine tune the placement of the parts of the protagonist
		public static final double bodyXScale = 0.833;
		public static final double bodyYScale = 0.667;
		public static final double bodyXOffset = 0.133;
		public static final double bodyYOffset = 0.264; 

		public static final double eyeMouthXScale = 0.867;
		public static final double eyeMouthYScale = 0.567;
		public static final double eyeMouthXOffset = 0.05;
		public static final double eyeMouthYOffset = 0.044; 
		
		public static final double eyeBeardXScale = 0.9;
		public static final double eyeBeardYScale = 0.856;
		public static final double eyeBeardXOffset = 0.05;
		public static final double eyeBeardYOffset = 0.044; 

		public static final double footXAxis = 0.30;
		public static final double footYAxis = 0.5;
		public static final double footRadius = 0.35;
		public static final double footXScale = 0.45;
		public static final double footYScale = 0.156;
		public static final double backFootOffset = 0.1;
		
		public static final double weaponXScale = 0.7;
		public static final double weaponYScale = 0.378;
		public static final double weaponXAxis = 0.3;
		public static final double weaponYAxis = 0.411;
		public static final double weaponRadius = 0.4;

		public static final double pupilXOffset = 0.267;
		public static final double pupilYOffset = 0.178;
		public static final double pupilXScale = 0.417;
		public static final double pupilYScale = 0.056;
		public static final double pupilRadius = 0.08;

		//Miscellaneous constants for protagonist rendering
		public static final double breathOffset = 0.03;
		public static final float jumpFeetAngle = 45;
		
		//Enemy constants
		
		public static final double enemyBodyXScale = 0.9;
		public static final double enemyBodyYScale = 0.9;
		public static final double enemyBodyXOffset = 0.05;
		public static final double enemyBodyYOffset = 0.05; 

		public static final double enemyEyeMouthXScale = 0.327;
		public static final double enemyEyeMouthYScale = 0.5;
		public static final double enemyEyeMouthXOffset = 0.194;
		public static final double enemyEyeMouthYOffset = 0.3; 

		public static final double enemyFootXAxis = 0.20;
		public static final double enemyFootYAxis = 0.5;
		public static final double enemyFootRadius = 0.35;
		public static final double enemyFootXScale = 0.45;
		public static final double enemyFootYScale = 0.256;
		public static final int enemyMaxFootAngle = 45;

		public static final double enemyPupilXOffset = 0.25;
		public static final double enemyPupilYOffset = 0.4;
		public static final double enemyPupilXScale = 0.15;
		public static final double enemyPupilYScale = 0.056;
		public static final double enemyPupilRadius = 0.04;
		
		public static final double enemyArmXAxis = 0.7;
		public static final double enemyArmYAxis = 0.16;
		public static final double enemyArmXScale = 0.694;
		public static final double enemyArmYScale = 0.413;
		public static final double enemyArmRadius = 0.3;
		
		public static final double enemyArmorXScale = 1;
		public static final double enemyArmorYScale = 0.9;
		public static final double enemyArmorXOffset = 0;
		public static final double enemyArmorYOffset = 0; 
		
		public static final double enemyNinjaXScale = 1;
		public static final double enemyNinjaYScale = 0.7;
		public static final double enemyNinjaXOffset = 0;
		public static final double enemyNinjaYOffset = -0.1; 

		public static final double maxRockSize = 30;
		public static final double partOfRockVisible = 0.8;
		
		public static final double maxTreeWidth = 100;
		public static final double maxTreeHeight = 100;
		public static final double partOfTreeVisible = 0.8;
		
		public static final double maxCloudWidth = 100;
		public static final double maxCloudHeight = 30;
		
		public static final double finishFlagHeight = 50;
		public static final double finishFlagWidth = 20;
		public static final double partOfFinishFlagVisible = 0.9;
		
		public static final double menuButtonHeight = 10;
		public static final double menuButtonWidth = menuButtonHeight*2.863;
		public static final double menuButtonXPadding = 5;
		public static final double menuButtonYPadding = 5;
		public static final double menuButtonSpace = 5;
		
		public static final double butterflySize = 5;
		public static final double flowerSize = 8;
		public static final double sunSize = 15;
		public static final double skeletonSize = 40;
		
		public static final double timeAreaWidth = 25;
		public static final double timeAreaHeight = 10;
		
		public static final double sliderHandleWidth = 4;
		
		public static final double criticalHealth = 0.2;
		
		public static final double meterWidth = 20;
		public static final double meterHeight = 10;
		public static final double meterFrameSize = 1;
		public static final double blinkInterval = 0.05;
		public static final int HUDPadding = 4;
		
		public static final int levelButtonsPerRow = 6;
		
		public static final double dustWidth = 40;
		public static final double dustHeight = 20; 
		public static final int dustDecayTime = 10;
		
		public static final int mumbleDelay = 250;
		
		public static int tutorialFruitX = 800;
		public static int tutorialFruitY = 80;
		public static final int tutorialFruitSize = 6;
		
		public static final int updateSize = 8;
		
		public static final int[] leaderboardColumns = new int[]{5, 12, 65, 80, 100, 114, 128, 140};
		public static final int leaderboardStartY = 10;
		public static final double leaderboardRows = 20;
		
		public static final double hintRange = 20;
		public static final double hintSize = 15;
		
		public static final int levelCap = 5;
	}

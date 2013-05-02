package com.example.pineapple;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
	private final String TAG = GamePanel.class.getSimpleName();
	private final int INVALID_POINTER = -1;

	//Scaling coefficients, from an imagined rectangle around the protagonist
	//Use this to fine tune the placement of the parts of the protagonist
	private final double bodyXScale = 0.833;
	private final double bodyYScale = 0.667;
	private final double bodyXOffset = 0.133;
	private final double bodyYOffset = 0.264; 

	private final double eyeMouthXScale = 0.867;
	private final double eyeMouthYScale = 0.567;
	private final double eyeMouthXOffset = 0.05;
	private final double eyeMouthYOffset = 0.044; 

	private final double footXAxis = 0.30;
	private final double footYAxis = 0.5;
	private final double footRadius = 0.35;
	private final double footXScale = 0.45;
	private final double footYScale = 0.156;
	private final double backFootOffset = 0.1;
	//private final double footXOffset = 0.35;
	//private final double footYOffset = 0.844; 

	private final double weaponXScale = 0.7;
	private final double weaponYScale = 0.378;
	private final double weaponXAxis = 0.3;
	private final double weaponYAxis = 0.411;
	private final double weaponRadius = 0.4;

	private final double pupilXOffset = 0.267;
	private final double pupilYOffset = 0.178;
	private final double pupilXScale = 0.417;
	private final double pupilYScale = 0.056;
	private final double pupilRadius = 0.08;

	//Miscellaneous constants for rendering
	private final double breathOffset = 0.03;
	private final float jumpFeetAngle = 45;

	private int leftStickId = INVALID_POINTER;
	private int rightStickId = INVALID_POINTER;
	private final int width = 155;
	private final int height = 100;
	private double screenX;
	private double screenY;
	private final int screenXPadding = 50;
	private final int screenYPadding = 20;
	private MainThread thread;
	private Protagonist protagonist;
	private Ground ground;
	private double scaleY, scaleX;
	private Stick leftStick, rightStick;
	private LevelLoader levelLoader;
	private ArrayList<Platform> platforms;
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;
	private ArrayList<Integer> trees;
	private int level;
	private HeatMeter heatMeter;
	private boolean firing = false;
	private Paint green = new Paint();
	private Paint red = new Paint();
	private Paint frame = new Paint();
	private double time;

	//Ground rendering variables 
	private int numberOfPatches, foliageSize = 2;
	private double xGap, yGap, gap, groundAngle; 
	private Paint groundPaint = new Paint();
	
	//Bitmaps
	private Bitmap bodyBitmap;
	private Bitmap footBitmap;
	private Bitmap eyeMouthBitmap;
	private Bitmap weaponBitmap;
	private Bitmap pupilBitmap;
	private Bitmap stickBitmap;
	private Bitmap[] bulletBitmaps;
	
	private Bitmap bodyBitmapFlipped;
	private Bitmap footBitmapFlipped;
	private Bitmap eyeMouthBitmapFlipped;
	private Bitmap weaponBitmapFlipped;
	private Bitmap pupilBitmapFlipped;

	public GamePanel(Context context, int level){
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		this.level = level;

		//Create game components
		levelLoader = new LevelLoader(level);
		heatMeter = new HeatMeter(0.01);
		bullets = new ArrayList<Bullet>();
		ground = new Ground(levelLoader.getLevelX(level), levelLoader.getLevelY(level));
		protagonist = new Protagonist(width/2, ground.getYFromX(77), this);
		leftStick = new Stick(Stick.LEFT);
		rightStick = new Stick(Stick.RIGHT);
		thread = new MainThread(this.getHolder(), this);

		loadPlatforms();
		loadEnemies();
		loadTrees();

		green.setColor(Color.GREEN);
		red.setColor(Color.RED);
		groundPaint.setColor(Color.rgb(10, 250, 10));

	}

	//Load the platforms from LevelLoader and add them to the platforms list 
	public void loadPlatforms(){
		platforms = new ArrayList<Platform>();
		for(int i = 0; i < levelLoader.getNumberOfPlatforms(); i++){
			platforms.add(new Platform(levelLoader.getPlatformUpperX(i), levelLoader.getPlatformUpperY(i), levelLoader.getPlatformLowerX(i), levelLoader.getPlatformLowerY(i)));
		}
	}

	public void loadEnemies(){
		enemies = new ArrayList<Enemy>();
		for(int i = 0; i < levelLoader.getNumberOfEnemies(); i++){
			int[] enemyData = levelLoader.getEnemyData(i);
			enemies.add(new Enemy(enemyData[0], enemyData[1], enemyData[2], enemyData[3], this));
		}
	}
	
	public void loadTrees(){
		trees = levelLoader.getTrees();
		
	}

	//Method that gets called every frame to update the games state
	public void update(){
		protagonist.checkSlope(ground, platforms);
		handleSticks();
		moveProtagonist();
		moveEnemies();
		moveBullets();
		moveScreen();
		handleHeatMeter();
		handleBulletEnemyCollisions();
		handleProtagonistEnemyCollisions();
		this.time++;
	}

	public void handleSticks(){
		if(leftStick.isPointed()) {
			protagonist.handleLeftStick(leftStick.getAngle(), 0.4);
		} else if (Math.abs(protagonist.getXVel()) > 0){
			protagonist.slowDown();
			protagonist.setStepCount(0);
		}
		if(rightStick.isPointed()){
			//Aim
			double angle = rightStick.getAngle();
			protagonist.aim(angle);
			//Fire
			if(!heatMeter.isCoolingDown()){
				bullets.add(new Bullet(protagonist.getXPos()+protagonist.getWidth()/2*Math.cos(angle/180*Math.PI), protagonist.getYPos()-protagonist.getWidth()/2*Math.sin(angle/180*Math.PI), angle, 10));
				firing = true;
			}
		}
	}

	public void moveProtagonist(){
		protagonist.gravity();
		protagonist.move();
		protagonist.faceDirection(leftStick, rightStick);
		protagonist.breathe();
		protagonist.invincibility();
		protagonist.checkGround(ground);
		protagonist.checkPlatform(platforms);
		protagonist.dashing(ground, platforms);
	}

	//Move all the enemies and check for obstacles etc
	public void moveEnemies(){
		for(int i = 0; i < enemies.size(); i++){
			Enemy enemy = enemies.get(i);
			if(enemy.hasSpawned()){
				enemy.gravity();
				enemy.accelerate(protagonist);
				enemy.move();
				enemy.checkGround(ground);
				enemy.checkPlatform(platforms);
			} else {
				if(protagonist.getXPos() > enemy.getSpawnX()){
					enemy.spawn();
				}
			}
		}
	}

	public void moveBullets(){
		for(int i = 0; i < bullets.size(); i++){
			Bullet b = bullets.get(i);
			b.gravity(1);
			b.move();
			int leftBound = ground.getX(0);
			int rightBound = ground.getX(ground.getLength()-1);
			if(b.getXPos() < leftBound || b.getXPos() > rightBound){
				bullets.remove(i);
				i--;
			} else if(b.checkObstacles(ground, platforms)){
				bullets.remove(i);
				i--;
			}

		}
	}

	//Moves the screen if the protagonist is close to the edge of the screen
	public void moveScreen(){
		if(protagonist.getXPos() - screenX > width - screenXPadding){
			screenX = protagonist.getXPos() - width + screenXPadding;
		} else if(protagonist.getXPos() - screenX < screenXPadding){
			screenX = protagonist.getXPos() - screenXPadding;
		}
		if(protagonist.getYPos() - screenY > height - screenYPadding){
			screenY = protagonist.getYPos() - height + screenYPadding;
		} else if(protagonist.getYPos() - screenY < screenYPadding){
			screenY = protagonist.getYPos() - screenYPadding;
		}

	}

	//Handles the HeatMeter 
	public void handleHeatMeter(){
		if(heatMeter.isCoolingDown()){//If the weapon is overheated
			heatMeter.coolDown();     //Cool down                      (Not able to fire)
		} else if(firing){			  //Else if the protagonist has fired
			heatMeter.addHeat(0.01);  //Heat up the weapon
			firing = false;
		} else {                      //Otherwise cool down the weapon (Still able to fire)
			heatMeter.coolDown();
		}
	}

	//Check collision between enemies and bullets
	public void handleBulletEnemyCollisions(){
		for(int i = 0; i < bullets.size(); i++){//All bullets
			Bullet bullet = bullets.get(i);
			for(int j = 0; j < enemies.size(); j++){//All enemies
				Enemy enemy = enemies.get(j);
				if(bullet.collideEnemy(enemy) && enemy.hasSpawned()){//If collision detected
					bullets.remove(i);//Remove the bullet from the game
					i--;

					enemy.takeDamage(0.05); //Reduce the enemies' health SET A CONSTANT OR SOMETHING HERE INSTEAD OF 0.05

					if(enemy.getHealth() <= 0){//If the enemy is dead
						enemies.remove(j);
						Log.d(TAG, "Enemy down.");
					}
					break;
				}
			}
		}
	}

	//Check collision between enemies and protagonist
	public void handleProtagonistEnemyCollisions(){
		for(int i = 0; i < enemies.size(); i++){
			//Dashmove
			if(protagonist.collide(enemies.get(i)) && protagonist.isDashBonus()){
				Log.d(TAG,"" + enemies.get(i).getHealth());
				enemies.get(i).takeDashDamage(protagonist);
				Log.d(TAG,"" + enemies.get(i).getHealth());
			}
			if(protagonist.collide(enemies.get(i)) && !protagonist.isInvincible()){
				protagonist.setInvincible(true);
				protagonist.setXVel(-protagonist.getXVel());
				protagonist.setYVel(-5);
				protagonist.reduceHealth(0.05); //Change this constant
				protagonist.setTouchingGround(false);
			}
		}
	}

	//Method that gets called to render the graphics
	public void render(Canvas canvas){
		canvas.drawColor(Color.rgb(135, 206, 250));
		renderSun(canvas);
		renderTrees(canvas);
		renderPlatforms(canvas);
		renderEnemies(canvas);
		renderProtagonist(canvas);
		renderGround(canvas);
		renderBullets(canvas);
		renderSticks(canvas);
		renderHeatMeter(canvas);
		renderHealthMeter(canvas);

	}

	//Draw the enemies
	public void renderEnemies(Canvas canvas){
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).hasSpawned())
				canvas.drawRect((float)((enemies.get(i).getXPos()-enemies.get(i).getWidth()/2-screenX)*scaleX), (float)((enemies.get(i).getYPos()-enemies.get(i).getHeight()/2-screenY)*scaleY), (float)((enemies.get(i).getXPos()+enemies.get(i).getWidth()/2-screenX)*scaleX), (float)((enemies.get(i).getYPos()+enemies.get(i).getHeight()/2-screenY)*scaleY), red);
		}
	}

	//Draw the protagonist
	public void renderProtagonist(Canvas canvas){
		/*Paint p = new Paint();
		p.setColor(Color.BLUE);
		canvas.drawRect((float)((protagonist.getXPos()-protagonist.getWidth()/2-screenX)*scaleX), (float)((protagonist.getYPos()-protagonist.getHeight()/2)*scaleY), (float)((protagonist.getXPos()+protagonist.getWidth()/2-screenX)*scaleX), (float)((protagonist.getYPos()+protagonist.getHeight()/2)*scaleY), p);*/
		float aimAngle = (float)rightStick.getAngle();
		float feetAngle;
		if(protagonist.isTouchingGround()){
			feetAngle = (float)(180/Math.PI*Math.sin((double)protagonist.getStepCount()/protagonist.getNumberOfSteps()*Math.PI));
		} else {
			feetAngle = jumpFeetAngle;
		}
		//Draw all the protagonist parts
		Matrix m = new Matrix();
		if(protagonist.isFacingRight()){
			//Draw back foot
			m.setRotate(-feetAngle, footBitmap.getWidth()/2, footBitmap.getHeight()/2);
			m.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-footXAxis-backFootOffset) - protagonist.getWidth()*footRadius*Math.sin(-feetAngle*Math.PI/180) - screenX)*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(footYAxis-0.5) + protagonist.getHeight()*footRadius*Math.cos(-feetAngle*Math.PI/180) - screenY)*scaleY));
			canvas.drawBitmap(footBitmap, m, null);
			//Draw body
			m.setTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-bodyXOffset) - screenX)*scaleX), (float)((protagonist.getYPos() - protagonist.getHeight()*(0.5-bodyYOffset + breathOffset*Math.sin((double)protagonist.getBreathCount()/protagonist.getBreathMax()*2*Math.PI)) - screenY)*scaleY));
			canvas.drawBitmap(bodyBitmap, m, null);
			//Draw eyes and mouth
			m.setTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-eyeMouthXOffset) - screenX)*scaleX), (float)((protagonist.getYPos() - protagonist.getHeight()*(0.5-eyeMouthYOffset) - screenY)*scaleY));
			canvas.drawBitmap(eyeMouthBitmap, m, null);
			//Draw front foot
			m.setRotate(feetAngle, footBitmap.getWidth()/2, footBitmap.getHeight()/2);
			m.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-footXAxis) - protagonist.getWidth()*footRadius*Math.sin(feetAngle*Math.PI/180) - screenX)*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(footYAxis-0.5) + protagonist.getHeight()*footRadius*Math.cos(feetAngle*Math.PI/180) - screenY)*scaleY));
			canvas.drawBitmap(footBitmap, m, null);
			//Draw pupils
			m.setTranslate((float)((protagonist.getXPos() + protagonist.getWidth()*(pupilXOffset-0.5)+protagonist.getWidth()*pupilRadius*Math.cos(aimAngle*Math.PI/180)-screenX)*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(pupilYOffset-0.5)-protagonist.getHeight()*pupilRadius*Math.sin(aimAngle*Math.PI/180) - screenY)*scaleY));
			canvas.drawBitmap(pupilBitmap, m, null);
			//Draw weapon
			m.setRotate(-aimAngle, weaponBitmap.getWidth()/2, weaponBitmap.getHeight()/2);
			m.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-weaponXAxis) + protagonist.getWidth()*weaponRadius*Math.cos(aimAngle*Math.PI/180) - screenX)*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(weaponYAxis-0.5) - protagonist.getHeight()*weaponRadius*Math.sin(aimAngle*Math.PI/180) - screenY)*scaleY));
			canvas.drawBitmap(weaponBitmap, m, null);
		} else {
			//Draw back foot
			m.setRotate(feetAngle, footBitmapFlipped.getWidth()/2, footBitmapFlipped.getHeight()/2);
			m.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-footXAxis+backFootOffset) - protagonist.getWidth()*footRadius*Math.sin(Math.PI-feetAngle*Math.PI/180) - screenX)*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(footYAxis-0.5) + protagonist.getHeight()*footRadius*Math.cos(-feetAngle*Math.PI/180) - screenY)*scaleY));
			canvas.drawBitmap(footBitmapFlipped, m, null);
			//Draw body
			m.setTranslate((float)((protagonist.getXPos() + protagonist.getWidth()*(0.5-bodyXOffset) - screenX)*scaleX) - bodyBitmapFlipped.getWidth(), (float)((protagonist.getYPos() - protagonist.getHeight()*(0.5-bodyYOffset + breathOffset*Math.sin((double)protagonist.getBreathCount()/protagonist.getBreathMax()*2*Math.PI)) - screenY)*scaleY));
			canvas.drawBitmap(bodyBitmapFlipped, m, null);
			//Draw eyes and mouth
			m.setTranslate((float)((protagonist.getXPos() + protagonist.getWidth()*(0.5-eyeMouthXOffset) - screenX)*scaleX) - eyeMouthBitmapFlipped.getWidth(), (float)((protagonist.getYPos() - protagonist.getHeight()*(0.5-eyeMouthYOffset) - screenY)*scaleY));
			canvas.drawBitmap(eyeMouthBitmapFlipped, m, null);
			//Draw front foot
			m.setRotate(-feetAngle, footBitmapFlipped.getWidth()/2, footBitmapFlipped.getHeight()/2);
			m.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-footXAxis) - protagonist.getWidth()*footRadius*Math.sin(Math.PI+feetAngle*Math.PI/180) - screenX)*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(footYAxis-0.5) + protagonist.getHeight()*footRadius*Math.cos(feetAngle*Math.PI/180) - screenY)*scaleY));
			canvas.drawBitmap(footBitmapFlipped, m, null);
			//Draw pupils
			m.setTranslate((float)((protagonist.getXPos() + protagonist.getWidth()*(pupilXOffset-0.5)+protagonist.getWidth()*pupilRadius*Math.cos(aimAngle*Math.PI/180)-screenX)*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(pupilYOffset-0.5)-protagonist.getHeight()*pupilRadius*Math.sin(aimAngle*Math.PI/180) - screenY)*scaleY));
			canvas.drawBitmap(pupilBitmapFlipped, m, null);
			//Draw weapon
			m.setRotate(180-aimAngle, weaponBitmapFlipped.getWidth()/2, weaponBitmapFlipped.getHeight()/2);
			m.postTranslate((float)((protagonist.getXPos()  + protagonist.getWidth()*(0.5-weaponXAxis) + protagonist.getWidth()*weaponRadius*Math.cos(aimAngle*Math.PI/180) - screenX)*scaleX - weaponBitmapFlipped.getWidth()), (float)((protagonist.getYPos() + protagonist.getHeight()*(weaponYAxis-0.5) + protagonist.getHeight()*weaponRadius*Math.sin(Math.PI+aimAngle*Math.PI/180) - screenY)*scaleY));
			canvas.drawBitmap(weaponBitmapFlipped, m, null);
		}


	}

	//Draws the ground using a Path
	//Draw only the parts which are visible on the screen
	public void renderGround(Canvas canvas){
		int i = 0;
		while(ground.getX(i+1) < screenX){
			i++;
		}
		int startIndex = i;
		while(ground.getX(i) < screenX + width){
			i++;
			if(i == ground.getLength()-2)
				break;
		}
		int stopIndex = i;
		for(i = startIndex; i <= stopIndex; i++){
			Path p = new Path();
			p.moveTo((int)((ground.getX(i)-screenX)*scaleX), (int)((ground.getY(i)-screenY)*scaleY));
			p.lineTo((int)((ground.getX(i+1)-screenX)*scaleX), (int)((ground.getY(i+1)-screenY)*scaleY));
			p.lineTo((int)((ground.getX(i+1)-screenX)*scaleX), (int)((200-screenY)*scaleY)); //Fix line 63 and 64, (200)
			p.lineTo((int)((ground.getX(i)-screenX)*scaleX), (int)((200-screenY)*scaleY));
			p.lineTo((int)((ground.getX(i)-screenX)*scaleX), (int)((ground.getY(i)-screenY)*scaleY));
			canvas.drawPath(p, groundPaint);
			Paint groundLine = new Paint();
			groundLine.setStrokeWidth((float)(3*scaleY));
			//canvas.drawLine((int)((ground.getX(i)-screenX)*scaleX), (int)((ground.getY(i)-screenY)*scaleY), (int)((ground.getX(i+1)-screenX)*scaleX), (int)((ground.getY(i+1)-screenY)*scaleY), new Paint());
			
		}
		//Experiment (Different ground details)
		//Balls
		
		/*for(i = startIndex; i <= stopIndex; i++){
			xGap = (ground.getX(i+1)-ground.getX(i));
			yGap = (ground.getY(i+1)-ground.getY(i));
			gap = Math.sqrt(Math.pow(xGap, 2) + Math.pow(yGap, 2));
			numberOfPatches = (int)(gap/foliageSize/2+2);
			for(int j = 0; j < numberOfPatches; j++){
				canvas.drawOval(new RectF((float)((ground.getX(i)+xGap*j/numberOfPatches - foliageSize - screenX)*scaleX), (float)((ground.getY(i)+yGap*j/numberOfPatches-foliageSize - screenY)*scaleY), (float)((ground.getX(i)+xGap*j/numberOfPatches+foliageSize - screenX)*scaleX), (float)((ground.getY(i)+yGap*j/numberOfPatches+foliageSize - screenY)*scaleY)), groundPaint);
			}
		}*/
		
		//Spikes
		Path p = new Path();
		for(i = startIndex; i <= stopIndex; i++){
			xGap = (ground.getX(i+1)-ground.getX(i));
			yGap = (ground.getY(i+1)-ground.getY(i));
			gap = Math.sqrt(Math.pow(xGap, 2) + Math.pow(yGap, 2));
			numberOfPatches = (int)(gap/foliageSize/2+2);
			groundAngle = Math.atan(ground.getSlope((ground.getX(i)+ground.getX(i+1))/2));
			p.moveTo((float)((ground.getX(i)-screenX)*scaleX), (float)((ground.getY(i)-screenY)*scaleY));
			for(int j = 0; j < numberOfPatches; j++){
				p.lineTo((float)((ground.getX(i)+xGap*(j+0.5)/numberOfPatches+foliageSize*Math.sin(groundAngle)-screenX)*scaleX), (float)((ground.getY(i)+yGap/numberOfPatches*(j+0.5)-foliageSize*Math.cos(groundAngle)-screenY)*scaleY));
				p.lineTo((float)((ground.getX(i)+xGap*(j+1)/numberOfPatches - screenX)*scaleX), (float)((ground.getY(i)+yGap/numberOfPatches*(j+1)-screenY)*scaleY));
			}
			p.lineTo((float)((ground.getX(i)-screenX)*scaleX), (float)((ground.getY(i)-screenY)*scaleY));
			canvas.drawPath(p, groundPaint);
		}
		
	}

	//Draw the platforms
	public void renderPlatforms(Canvas canvas){
		Paint p = new Paint();
		p.setColor(Color.CYAN);
		for(int i = 0; i < platforms.size(); i++){
			Path path = platforms.get(i).getPath();
			Path newPath = new Path(path); 
			Matrix m = new Matrix();
			m.postTranslate(-(float)screenX, -(float)screenY);
			m.postScale((float)scaleX, (float)scaleY);
			newPath.transform(m);
			canvas.drawPath(newPath, p);
		}
	}

	//Draw the sticks
	public void renderSticks(Canvas canvas){
		canvas.drawBitmap(stickBitmap, (float)((leftStick.getX()-leftStick.getRadius())*scaleX), (float)((leftStick.getY()-leftStick.getRadius())*scaleY), null);
		canvas.drawBitmap(stickBitmap, (float)((rightStick.getX()-rightStick.getRadius())*scaleX), (float)((rightStick.getY()-rightStick.getRadius())*scaleY), null);
	}

	//Draw the bullets
	public void renderBullets(Canvas canvas){
		int radius = Bullet.getRadius();
		for(int i = 0; i < bullets.size(); i++){
			Bullet b = bullets.get(i);
			Matrix m = new Matrix();
			m.setRotate(b.getRotation(), bulletBitmaps[0].getWidth()/2, bulletBitmaps[0].getHeight()/2);
			m.postTranslate((float)((b.getXPos()-radius/2.-screenX)*scaleX), (float)((b.getYPos()-radius/2.-screenY)*scaleY));
			canvas.drawBitmap(bulletBitmaps[b.getType()], m, null);
		}
	}

	//Draw the HeatMeter
	public void renderHeatMeter(Canvas canvas){
		int xPadding = 10;
		int yPadding = 10;
		int width = 20;
		int height = 10;
		int frameSize = 1;
		double blinkInterval = 0.05; //Lower value means faster blinking (Should be at least coolingRate of HeatMeter)

		//This makes the frame blink red if overheated
		if(heatMeter.isCoolingDown() && heatMeter.getHeat() % (2*blinkInterval) > blinkInterval){
			frame.setColor(Color.RED);
		} else {
			frame.setColor(Color.BLACK);
		}

		//Draw in top right corner
		//Draw frame
		canvas.drawRect((float)((this.width - width - xPadding - frameSize)*scaleX), (float)((yPadding-frameSize)*scaleY), (float)((this.width - xPadding + frameSize)*scaleX), (float)((yPadding+height+frameSize)*scaleY), frame);
		//Draw green background
		canvas.drawRect((float)((this.width - width - xPadding)*scaleX), (float)(yPadding*scaleY), (float)((this.width - xPadding)*scaleX), (float)((yPadding+height)*scaleY), green);
		//Draw red indicator that moves with current heat level
		canvas.drawRect((float)((this.width - width - xPadding)*scaleX), (float)(yPadding*scaleY), (float)((this.width - xPadding - width*(1-heatMeter.getHeat()))*scaleX), (float)((yPadding+height)*scaleY), red);
	}

	public void renderHealthMeter(Canvas canvas){
		int xPadding = 10;
		int yPadding = 10;
		int width = 20;
		int height = 10;
		int frameSize = 1;

		//Draw in top left corner
		//Draw frame
		canvas.drawRect((float)((xPadding-frameSize)*scaleX), (float)((yPadding-frameSize)*scaleY), (float)((xPadding+width+frameSize)*scaleX), (float)((yPadding+height+frameSize)*scaleY), new Paint());
		//Draw green background
		canvas.drawRect((float)(xPadding*scaleX), (float)(yPadding*scaleY), (float)((xPadding+width)*scaleX), (float)((yPadding+height)*scaleY), red);
		//Draw red indicator that moves with current heat level
		canvas.drawRect((float)(xPadding*scaleX), (float)(yPadding*scaleY), (float)((xPadding+width*protagonist.getHealth())*scaleX), (float)((yPadding+height)*scaleY), green);
	}

	//Draw the sun, moving in time
	public void renderSun(Canvas canvas){
		float x = (float)(width*scaleX/3);//Make generalll
		float y = (float)((50 + 50*Math.sin(Math.PI + time/500))*scaleY);//Make generalll
		float radius = 60;
		Paint p = new Paint();
		p.setColor(Color.YELLOW);

		canvas.drawCircle(x, y, radius, p);
	}

	//Draw trees
	public void renderTrees(Canvas canvas){
		//x is centre of tree?
		Paint trunk = new Paint();
		trunk.setColor(Color.DKGRAY);
		Paint top = new Paint();
		top.setColor(Color.GREEN);
		Paint border = new Paint();
		border.setStyle(Style.STROKE);
		for(int i = 0; i < trees.size(); i++){
			float y = (float)(100); //Make generalll
			float trunkHeight = (float)(height/4);//Make generalll
			float trunkWidth = (float)(5);//Make generalll
			float radius = 20;
			canvas.drawCircle((float)((trees.get(i) - screenX/4)*scaleX), (float)((y - trunkHeight - radius/2)*scaleX), (float)(radius*scaleX), top);
			canvas.drawCircle((float)((trees.get(i) - screenX/4)*scaleX), (float)((y - trunkHeight - radius/2)*scaleX), (float)(radius*scaleX), border);
			canvas.drawRect((float)((trees.get(i) - trunkWidth/2 - screenX/4)*scaleX), (float)((y - trunkHeight)*scaleY), (float)((trees.get(i) + trunkWidth/2 - (float)(screenX/4))*scaleX), (float)(y*scaleY), trunk);
		}
		

		
	}

	@Override
	public boolean onTouchEvent(MotionEvent e){
		double x;
		double y;

		int index = e.getActionIndex();
		int id = e.getPointerId(index);

		switch(e.getActionMasked()){

		case MotionEvent.ACTION_DOWN:
			x = e.getX()/scaleX;
			y = e.getY()/scaleY;

			if(x > width/2){
				rightStick.handleTouch(x, y);
				rightStickId = id;
			} else {
				leftStick.handleTouch(x, y);
				leftStickId = id;
			}

			break;

		case MotionEvent.ACTION_POINTER_DOWN:

			x = e.getX(index)/scaleX;
			y = e.getY(index)/scaleY;


			if(x > width/2){
				rightStick.handleTouch(x, y);
				rightStickId = id;
			} else {
				leftStick.handleTouch(x, y);
				leftStickId = id;
			}

			break;

		case MotionEvent.ACTION_MOVE:

			for(index=0; index<e.getPointerCount(); index++) {
				id=e.getPointerId(index);
				x = (int) e.getX(index)/scaleX;
				y = (int) e.getY(index)/scaleY; 

				if(id == rightStickId) {
					if(x > width/2){
						rightStick.handleTouch(x, y);
						rightStickId = id;
					} 
				}
				else if(id == leftStickId){
					if(x < width/2){
						leftStick.handleTouch(x, y);
						leftStickId = id;
					}
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			rightStick.release();
			leftStick.release();
			leftStickId = INVALID_POINTER;
			rightStickId = INVALID_POINTER;

			break;


		case MotionEvent.ACTION_POINTER_UP:
			if(id == leftStickId){
				leftStick.release();
				leftStickId = INVALID_POINTER;
			}
			if(id == rightStickId){
				rightStick.release();
				rightStickId = INVALID_POINTER;
			}
			break;
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//Calculate the scale that we will use to render the game
		scaleY = (double)getHeight()/height;
		scaleX = (double)getWidth()/width;

		//Load Bitmaps
		bodyBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.valentine_in_game_90_body), (int)(protagonist.getWidth()*scaleX*bodyXScale), (int)(protagonist.getHeight()*scaleY*bodyYScale), true);
		eyeMouthBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.valentine_in_game_90_eye_mouth), (int)(protagonist.getWidth()*scaleX*eyeMouthXScale), (int)(protagonist.getHeight()*scaleY*eyeMouthYScale), true);
		footBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.valentine_in_game_90_foot), (int)(protagonist.getWidth()*scaleX*footXScale), (int)(protagonist.getHeight()*scaleY*footYScale), true);
		weaponBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.valentine_in_game_90_hand_gun), (int)(protagonist.getWidth()*scaleX*weaponXScale), (int)(protagonist.getHeight()*scaleY*weaponYScale), true);
		pupilBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.valentine_in_game_90_pupil), (int)(protagonist.getWidth()*scaleX*pupilXScale), (int)(protagonist.getHeight()*scaleY*pupilYScale), true);
		stickBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.stick), (int)(2*leftStick.getRadius()*scaleX), (int)(2*leftStick.getRadius()*scaleX), true);
		bulletBitmaps = new Bitmap[]{
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bullet1), (int)(2*Bullet.getRadius()*scaleX), (int)(2*Bullet.getRadius()*scaleX), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bullet2), (int)(2*Bullet.getRadius()*scaleX), (int)(2*Bullet.getRadius()*scaleX), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bullet3), (int)(2*Bullet.getRadius()*scaleX), (int)(2*Bullet.getRadius()*scaleX), true)
		};

		Matrix m = new Matrix();
		m.setScale(-1, 1);

		bodyBitmapFlipped = Bitmap.createBitmap(bodyBitmap, 0, 0, bodyBitmap.getWidth(), bodyBitmap.getHeight(), m, false);
		eyeMouthBitmapFlipped = Bitmap.createBitmap(eyeMouthBitmap, 0, 0, eyeMouthBitmap.getWidth(), eyeMouthBitmap.getHeight(), m, false);
		footBitmapFlipped = Bitmap.createBitmap(footBitmap, 0, 0, footBitmap.getWidth(), footBitmap.getHeight(), m, false);
		weaponBitmapFlipped = Bitmap.createBitmap(weaponBitmap, 0, 0, weaponBitmap.getWidth(), weaponBitmap.getHeight(), m, false);
		pupilBitmapFlipped = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.valentine_in_game_90_pupil), (int)(protagonist.getWidth()*scaleX*pupilXScale), (int)(protagonist.getHeight()*scaleY*pupilYScale), true);

		//Start the thread
		thread.setRunning(true);
		try{thread.start();} catch(IllegalThreadStateException e){}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//End the thread
		boolean retry = true;
		while(retry){
			try{
				thread.join();
				retry = false;
			} catch(InterruptedException e){

			}
		}
	}


	public Ground getGround() {
		return ground;
	}

	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}

	//Pause the game
	public void pause(){
		thread.setRunning(false);
	}
	
	//Resume the game
	public void resume(){
		thread.setRunning(true);
	}
}

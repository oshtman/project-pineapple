package com.example.pineapple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;

import com.scoreloop.client.android.core.controller.RequestController;
import com.scoreloop.client.android.core.controller.RequestControllerException;
import com.scoreloop.client.android.core.controller.RequestControllerObserver;
import com.scoreloop.client.android.core.controller.ScoresController;
import com.scoreloop.client.android.core.controller.TermsOfServiceController;
import com.scoreloop.client.android.core.controller.TermsOfServiceControllerObserver;
import com.scoreloop.client.android.core.controller.UserController;
import com.scoreloop.client.android.core.model.Score;
import com.scoreloop.client.android.core.model.Session;
import com.scoreloop.client.android.core.model.User;

public class MenuPanel extends SurfaceView implements SurfaceHolder.Callback{
	private static final String TAG = MenuPanel.class.getSimpleName();
	public final static String LEVEL = "com.example.pineapple.LEVEL";
	private final int MAIN_MENU = 0;
	private final int LEVEL_MENU = 1;
	private final int SETTINGS_MENU = 2;
	private final int HIGHSCORE_MENU = 3;
	private final int PLAY = 4;
	private final int width = 155, height = 100;
	private double scaleX, scaleY;
	private Button playButton, settingsButton, highscoreButton, soundButton, musicButton, scoreButton;
	private MainThread thread;
	private Bitmap backgroundBitmap;
	private Context context;
	private Protagonist protagonist;
	private Matrix renderMatrix;
	private Bitmap bodyBitmap, weaponBitmap, footBitmap, pupilBitmap, eyeMouthBitmap;
	private Bitmap bodyBitmapFlipped, weaponBitmapFlipped, footBitmapFlipped, pupilBitmapFlipped, eyeMouthBitmapFlipped;
	private Button[] levelButtons;
	private Slider musicSlider, soundSlider;
	private Bitmap[] levelBitmaps;
	private Bitmap[] butterflyBitmaps;
	private Bitmap sliderLineBitmap, sliderHandleBitmap;
	private Bitmap onBitmap, offBitmap, updateBitmap;
	private int nextLevel;
	private int menuState;
	private SoundManager sm;
	private MediaPlayer theme;
	private int currentLevel;
	private int touchX, touchY;
	private int[] desiredX = new int[]{30, 60, 100, 120, 170};
	private SharedPreferences settings;
	private Butterfly butterfly;
	private float aimAngle, feetAngle;
	private int time = 0;
	private Paint userPaint, leaderboardPaint;
	private EditText nameField;

	private final TermsOfServiceController controller;
	private static UserController userController;
	private static String userName = "loading...";
	private static boolean leaderboardsLoaded = false;
	private static ArrayList<List<Score>> highScoreList= new ArrayList<List<Score>>();
	private static int currentHighScoreMode = 0;
	private static int leaderboardLevel = 0, leaderboardDifficulty = 0;
	private static int loaderRotation = 0;
	private Paint textBackground = new Paint();

	public MenuPanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		leaderboardsLoaded = false;
		this.context = context;
		sm = new SoundManager(context, 1);
		protagonist = new Protagonist(-20, 80);
		butterfly = new Butterfly();
		renderMatrix = new Matrix();
		setKeepScreenOn(true);

		settings = context.getSharedPreferences("gameSettings", Context.MODE_PRIVATE);
		currentLevel = settings.getInt("currentLevel", 0);
		sm = new SoundManager(getContext());
		loadSounds();
		playTheme();

		userPaint = new Paint();
		userPaint.setColor(Color.WHITE);

		leaderboardPaint = new Paint();
		leaderboardPaint.setColor(Color.WHITE);

		textBackground.setARGB(120, 40, 40, 40);

		nameField = new EditText(context);



		//controller to handle the user's acceptance of the Terms Of Service
		controller = new TermsOfServiceController(new TermsOfServiceControllerObserver() {
			@Override
			public void termsOfServiceControllerDidFinish(final TermsOfServiceController controller, final Boolean accepted) {
				if(accepted != null) {
					// we have conclusive result
					if(accepted) {
						// user did accept
					}
					else {
						// user declined
					}
				}
			}
		});

		// first, a request observer...
		RequestControllerObserver observer = new RequestControllerObserver() {

			@Override
			public void requestControllerDidReceiveResponse(RequestController requestController) {
				UserController userController = (UserController)requestController;
				Log.d(TAG, "Load successful");
				// insert values into text fields
				User user = userController.getUser();
				setUserName(user.getLogin());
			}

			@Override
			public void requestControllerDidFail(RequestController aRequestController, Exception anException) {
				Log.d(TAG, "Load failed");
			}
		};

		// here's the UserController doing our work to update the profile data
		UserController userController = new UserController(observer);

		// and fire the request
		userController.loadUser();
		thread = new MainThread(this.getHolder(), this);
	}
	public void update(){
		if(Math.abs(protagonist.getXPos() - desiredX[menuState]) > 10){
			protagonist.accelerate(0.3*Math.signum(desiredX[menuState]-protagonist.getXPos()));
			protagonist.faceDirection((int)Math.signum(desiredX[menuState]-protagonist.getXPos()));
			protagonist.setAim(90 - (int)Math.signum(desiredX[menuState]-protagonist.getXPos())*90);
			protagonist.step(1);
		} else {
			protagonist.slowDown();
			protagonist.setStepCount(0);
			protagonist.faceDirection((int)Math.signum(butterfly.getX()-protagonist.getXPos()));
			protagonist.setAim((int)(180/Math.PI * Math.atan2(butterfly.getY() - protagonist.getYPos(), butterfly.getX() - protagonist.getXPos())));
		}
		if(protagonist.getXPos() > 160){
			goToGame(nextLevel);
		}
		protagonist.breathe();
		protagonist.move();
		butterfly.update();
		if(menuState == SETTINGS_MENU){
			setVolumes();
		}

		time++;

	}

	public void playTheme(){
		if(!theme.isPlaying()){ //If theme isn't already playing
			try {
				theme.reset();
				loadSounds();
				theme.prepare();
			} catch(IOException e){	}
			theme.setLooping(true);
			theme.setVolume(settings.getFloat("musicVolume", 1), settings.getFloat("musicVolume", 1));
			theme.start();
		}
	}

	public void stopTheme(){
		if(theme.isPlaying()){
			try {
				theme.stop();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} 

		}
	}

	public void loadSounds(){
		sm.addSound(0, R.raw.fire_sound);
		theme = MediaPlayer.create(getContext(), R.raw.short_instrumental);
		try {
			theme.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		theme.setVolume(settings.getFloat("musicVolume", 0), settings.getFloat("musicVolume", 0));
	}

	public void setVolumes(){
		if(musicSlider.isSliding()){
			Editor e = settings.edit();
			e.putFloat("musicVolume", (float)musicSlider.getValue());
			e.commit();
			theme.setVolume((float)musicSlider.getValue(), (float)musicSlider.getValue());
		}
		if(soundSlider.isSliding()){
			Editor e = settings.edit();
			e.putFloat("soundVolume", (float)soundSlider.getValue());
			e.commit();
			sm.playSound(0, settings.getFloat("soundVolume", 0));
		}
	}

	public void render(Canvas canvas){
		canvas.drawBitmap(backgroundBitmap, 0, 0, null);
		renderButterfly(canvas);
		renderButtons(canvas);
		renderProtagonist(canvas);
		renderSliders(canvas);
		renderUser(canvas);
		renderLeaderboards(canvas);
	}

	public void renderButtons(Canvas canvas){
		switch(menuState){
		case MAIN_MENU:
			canvas.drawBitmap(playButton.getBitmap(), (float)(playButton.getX()*scaleX), (float)(playButton.getY()*scaleY), null);
			canvas.drawBitmap(settingsButton.getBitmap(), (float)(settingsButton.getX()*scaleX), (float)(settingsButton.getY()*scaleY), null);
			canvas.drawBitmap(highscoreButton.getBitmap(), (float)(highscoreButton.getX()*scaleX), (float)(highscoreButton.getY()*scaleY), null);
			break;
		case LEVEL_MENU:
			for(Button b: levelButtons){
				canvas.drawBitmap(b.getBitmap(), (float)(b.getX()*scaleX), (float)(b.getY()*scaleY), null);
			}
			break;
		case SETTINGS_MENU:
			canvas.drawBitmap(soundButton.getBitmap(), (float)(soundButton.getX()*scaleX), (float)(soundButton.getY()*scaleY), null);
			canvas.drawBitmap(musicButton.getBitmap(), (float)(musicButton.getX()*scaleX), (float)(musicButton.getY()*scaleY), null);
			canvas.drawBitmap(scoreButton.getBitmap(), (float)(scoreButton.getX()*scaleX), (float)(scoreButton.getY()*scaleY), null);
			if(settings.getBoolean("scoring", false)){
				canvas.drawBitmap(onBitmap, (float)((scoreButton.getX()+Const.HUDPadding+scoreButton.getWidth())*scaleX), (float)(scoreButton.getY()*scaleY), null);
			}  else {
				canvas.drawBitmap(offBitmap, (float)((scoreButton.getX()+Const.HUDPadding+scoreButton.getWidth())*scaleX), (float)(scoreButton.getY()*scaleY), null);
			}
			break;
		}

	}

	public void renderSliders(Canvas canvas){
		if(menuState == SETTINGS_MENU){
			canvas.drawBitmap(sliderLineBitmap, (float)(musicSlider.getX()*scaleX), (float)(musicSlider.getY()*scaleY), null);
			canvas.drawBitmap(sliderHandleBitmap, (float)((musicSlider.getX() + musicSlider.getWidth()*musicSlider.getValue()-Const.sliderHandleWidth/2)*scaleX), (float)((musicSlider.getY()+Const.menuButtonHeight/4*Math.sin(time/45.*Math.PI))*scaleY), null);

			canvas.drawBitmap(sliderLineBitmap, (float)(soundSlider.getX()*scaleX), (float)(soundSlider.getY()*scaleY), null);
			canvas.drawBitmap(sliderHandleBitmap, (float)((soundSlider.getX() + soundSlider.getWidth()*soundSlider.getValue()-Const.sliderHandleWidth/2)*scaleX), (float)((soundSlider.getY()+Const.menuButtonHeight/4*Math.cos(time/45.*Math.PI))*scaleY), null);
		}
	}

	public void renderUser(Canvas canvas){
		canvas.drawText("User: " + userName, (float)((Const.HUDPadding/2*scaleX)), (float)((height-Const.HUDPadding/2)*scaleY), userPaint);
	}

	public void renderLeaderboards(Canvas canvas){
		if(menuState == HIGHSCORE_MENU){
			if(leaderboardsLoaded){
				String levelS = (leaderboardLevel > 0)?("level " + leaderboardLevel):"tutorial";
				String difficultyS = (leaderboardDifficulty == 0)?"normal":"hard";
				String leaderboardTitle = "Leaderboard for " + levelS + " @difficulty " + difficultyS;

				canvas.drawRect((float)(Const.HUDPadding/2*scaleX), 0, (float)((width-Const.HUDPadding/2)*scaleX), (float)(height*scaleY), textBackground);
				canvas.drawText(leaderboardTitle, (float)(Const.HUDPadding/2*scaleX), (float)(Const.HUDPadding*scaleY), leaderboardPaint);
				canvas.drawLine((float)(Const.HUDPadding/2*scaleX), (float)(Const.HUDPadding*scaleY), (float)((width - Const.HUDPadding/2)*scaleX), (float)(Const.HUDPadding*scaleY), leaderboardPaint);
				try{ //Try to render the leaderboard
					for(int i = 0; i < highScoreList.get(2*leaderboardLevel).size(); i++){
						Score s = highScoreList.get(2*leaderboardLevel).get(i);
						String entry = "#"+s.getRank()+". "+s.getUser().getLogin()+": " + s.getResult().intValue();
						canvas.drawText(entry, (float)(Const.HUDPadding/2*scaleX), (float)(3/2.*Const.HUDPadding*scaleY+i*(height-Const.HUDPadding)/20.*scaleY), leaderboardPaint);
					}
				} catch(IndexOutOfBoundsException e){ //The rendering might get interrupted by a leaderboard update
					Log.e(TAG, "The leaderboard rendering got interrupted");
				}
			} else {
				loaderRotation += 10;
				Matrix m = new Matrix();
				m.setRotate(loaderRotation, updateBitmap.getWidth()/2, updateBitmap.getHeight()/2);
				m.postTranslate((float)(width/2*scaleX - updateBitmap.getWidth()/2), (float)(height/2*scaleY - updateBitmap.getHeight()/2));
				canvas.drawBitmap(updateBitmap, m, null);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {


	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		scaleY = (double)getHeight()/height;
		scaleX = (double)getWidth()/width;


		Bitmap playBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.start), (int)(1.5*Const.menuButtonWidth*scaleX), (int)(1.5*Const.menuButtonHeight*scaleY), true);
		playButton = new Button(10, 10, playBitmap.getWidth()/scaleX, playBitmap.getHeight()/scaleY, playBitmap);

		Bitmap settingsBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.settings), (int)(1.5*Const.menuButtonWidth*scaleX), (int)(1.5*Const.menuButtonHeight*scaleY), true);
		settingsButton = new Button(10, (int)(10 + 1.5*Const.menuButtonHeight), settingsBitmap.getWidth()/scaleX, settingsBitmap.getHeight()/scaleY, settingsBitmap);

		Bitmap highscoreBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.highscore), (int)(1.5*Const.menuButtonWidth*scaleX), (int)(1.5*Const.menuButtonHeight*scaleY), true);
		highscoreButton = new Button(10, (int)(10 + 2*1.5*Const.menuButtonHeight), highscoreBitmap.getWidth()/scaleX, highscoreBitmap.getHeight()/scaleY, highscoreBitmap);

		Bitmap soundBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.effects), (int)(1.5*Const.menuButtonWidth*scaleX), (int)(1.5*Const.menuButtonHeight*scaleY), true);
		soundButton = new Button(10, 10, soundBitmap.getWidth()/scaleX, soundBitmap.getHeight()/scaleY, soundBitmap);

		Bitmap musicBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.music), (int)(1.5*Const.menuButtonWidth*scaleX), (int)(1.5*Const.menuButtonHeight*scaleY), true);
		musicButton = new Button(10, (int)(10 + 1.5*Const.menuButtonHeight), musicBitmap.getWidth()/scaleX, musicBitmap.getHeight()/scaleY, musicBitmap);

		Bitmap scoreBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.score), (int)(1.5*Const.menuButtonWidth*scaleX), (int)(1.5*Const.menuButtonHeight*scaleY), true);
		scoreButton = new Button(10, (int)(10 + 3*Const.menuButtonHeight), scoreBitmap.getWidth()/scaleX, scoreBitmap.getHeight()/scaleY, scoreBitmap);

		musicSlider = new Slider(musicButton.getX() + musicButton.getWidth() + Const.HUDPadding, musicButton.getY(), musicButton.getWidth(), musicButton.getHeight(), settings.getFloat("musicVolume", 1));
		soundSlider = new Slider(soundButton.getX() + soundButton.getWidth() + Const.HUDPadding, soundButton.getY(), soundButton.getWidth(), soundButton.getHeight(), settings.getFloat("soundVolume", 1));


		backgroundBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.menu_background), (int)(width*scaleX), (int)(height*scaleY), true);
		Log.d(TAG, "Loaded");
		bodyBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.protagonist_body), (int)(protagonist.getWidth()*scaleX*Const.bodyXScale), (int)(protagonist.getHeight()*scaleY*Const.bodyYScale), true);
		eyeMouthBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.protagonist_eye_mouth), (int)(protagonist.getWidth()*scaleX*Const.eyeMouthXScale), (int)(protagonist.getHeight()*scaleY*Const.eyeMouthYScale), true);
		footBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.protagonist_foot), (int)(protagonist.getWidth()*scaleX*Const.footXScale), (int)(protagonist.getHeight()*scaleY*Const.footYScale), true);
		weaponBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.protagonist_weapon), (int)(protagonist.getWidth()*scaleX*Const.weaponXScale), (int)(protagonist.getHeight()*scaleY*Const.weaponYScale), true);
		pupilBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.protagonist_pupil), (int)(protagonist.getWidth()*scaleX*Const.pupilXScale), (int)(protagonist.getHeight()*scaleY*Const.pupilYScale), true);

		//Flip images
		renderMatrix.setScale(-1, 1);
		bodyBitmapFlipped = Bitmap.createBitmap(bodyBitmap, 0, 0, bodyBitmap.getWidth(), bodyBitmap.getHeight(), renderMatrix,false);
		eyeMouthBitmapFlipped = Bitmap.createBitmap(eyeMouthBitmap, 0, 0, eyeMouthBitmap.getWidth(), eyeMouthBitmap.getHeight(), renderMatrix,false);
		footBitmapFlipped = Bitmap.createBitmap(footBitmap, 0, 0, footBitmap.getWidth(), footBitmap.getHeight(), renderMatrix,false);
		weaponBitmapFlipped = Bitmap.createBitmap(weaponBitmap, 0, 0, weaponBitmap.getWidth(), weaponBitmap.getHeight(), renderMatrix,false);
		pupilBitmapFlipped = Bitmap.createBitmap(pupilBitmap, 0, 0, pupilBitmap.getWidth(), pupilBitmap.getHeight(), renderMatrix,false);

		levelBitmaps = new Bitmap[]{
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tutorial), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_1), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_2), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_3), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_4), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_5), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_6), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_7), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_8), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.level_9), (int)(Const.menuButtonWidth*scaleX), (int)(Const.menuButtonHeight*scaleY), true),
		};

		butterflyBitmaps = new Bitmap[]{
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.butterfly_in), (int)(Const.butterflySize*scaleX), (int)(Const.butterflySize*scaleY), true),
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.butterfly_out), (int)(Const.butterflySize*scaleX), (int)(Const.butterflySize*scaleY), true)
		};

		sliderLineBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.slider_line), (int)(musicSlider.getWidth()*scaleX), (int)(musicSlider.getHeight()*scaleY), true);
		sliderHandleBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.slider_handle), (int)(Const.sliderHandleWidth*scaleX), (int)(musicSlider.getHeight()*scaleY), true);

		onBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.on), (int)(scoreButton.getWidth()*scaleX), (int)(scoreButton.getHeight()*scaleY), true);
		offBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.off), (int)(scoreButton.getWidth()*scaleX), (int)(scoreButton.getHeight()*scaleY), true);
		updateBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.update), (int)(Const.updateSize*scaleX), (int)(Const.updateSize*scaleY), true);
		if(currentLevel > 7)
			currentLevel = 7;
		levelButtons = new Button[currentLevel+1];
		for(int i = 0; i <= currentLevel && i < 8; i++){
			levelButtons[i] = new Button(Const.HUDPadding + (int)(Const.menuButtonWidth*(i/Const.levelButtonsPerRow)), Const.HUDPadding + (int)(Const.menuButtonHeight*(i%Const.levelButtonsPerRow)), Const.menuButtonWidth, Const.menuButtonHeight, levelBitmaps[i]);
		}

		userPaint.setTextSize((float)(10*scaleY));
		userPaint.setAntiAlias(true);
		userPaint.setDither(true);

		leaderboardPaint.setTextSize((float)((height/20.)*scaleY));
		leaderboardPaint.setAntiAlias(true);
		leaderboardPaint.setDither(true);

		//Start the thread
		if (thread.getState()==Thread.State.TERMINATED) { 
			thread = new MainThread(getHolder(),this);

		}
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

	public void resume(){
		playTheme();
		leaderboardsLoaded = false;
		currentHighScoreMode = 0;
	}

	//Pause the game
	public void pause(){
		stopTheme();
		thread.setRunning(false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e){

		touchX = (int)(e.getX()/scaleX);
		touchY = (int)(e.getY()/scaleY);
		if(e.getAction() == MotionEvent.ACTION_DOWN){

			switch(menuState){
			case MAIN_MENU:
				if(playButton.isClicked(touchX, touchY)){
					menuState = LEVEL_MENU;
				}
				if(settingsButton.isClicked(touchX, touchY)){
					menuState = SETTINGS_MENU;
				}
				if(highscoreButton.isClicked(touchX, touchY)){
					menuState = HIGHSCORE_MENU;
					loadHighscores();
				}
				break;
			case LEVEL_MENU:
				for(int i = 0; i < levelButtons.length; i++){
					if(levelButtons[i].isClicked(touchX, touchY)){
						nextLevel = i;
						menuState = PLAY;
					}
				}
				break;
			case SETTINGS_MENU:
				if(soundButton.isClicked(touchX, touchY)){
					Editor ed = settings.edit();
					ed.putFloat("soundVolume", settings.getFloat("soundVolume", 1)>0?0:1);
					ed.commit();
					soundSlider.setValue(settings.getFloat("soundVolume", 1));
					sm.playSound(0, settings.getFloat("soundVolume", 0));

				}
				if(musicButton.isClicked(touchX, touchY)){
					Editor ed = settings.edit();
					ed.putFloat("musicVolume", settings.getFloat("musicVolume", 1)>0?0:1);
					ed.commit();
					theme.setVolume(settings.getFloat("musicVolume", 1), settings.getFloat("musicVolume", 1));
					musicSlider.setValue(settings.getFloat("musicVolume", 1));
				}
				if(scoreButton.isClicked(touchX, touchY)){
					Editor ed = settings.edit();
					ed.putBoolean("scoring", settings.getBoolean("scoring", false)?false:true);
					ed.commit();
					if(settings.getBoolean("scoring", false)){
						controller.query(null);
					}
				}
				soundSlider.handleTouch(touchX, touchY);
				musicSlider.handleTouch(touchX, touchY);
				break;
			case HIGHSCORE_MENU:
				if(leaderboardsLoaded && touchX > width/2 && touchY > height/2){
					leaderboardsLoaded = false;
					currentHighScoreMode = 0;
					loadHighscores();
				} else {
					leaderboardLevel++;
					if(leaderboardLevel >= 10)
						leaderboardLevel = 0;
				}
				break;

			}
		}
		if(e.getAction() == MotionEvent.ACTION_MOVE){
			soundSlider.handleTouch(touchX, touchY);
			musicSlider.handleTouch(touchX, touchY);
		}
		if(e.getAction() == MotionEvent.ACTION_UP){
			musicSlider.release();
			soundSlider.release();
		}
		return true;
	}

	public void goToGame(int level){
		stopTheme();

		Intent intent = new Intent(context, GameActivity.class);
		intent.putExtra(LEVEL, level);

		context.startActivity(intent);
	}

	public void back(){
		switch(menuState){
		case LEVEL_MENU:
		case SETTINGS_MENU:
		case HIGHSCORE_MENU:
			menuState = MAIN_MENU;
			break;
		}
	}

	public void renderProtagonist(Canvas canvas){
		feetAngle = (float)(180/Math.PI*Math.sin((double)protagonist.getStepCount()/protagonist.getNumberOfSteps()*Math.PI));
		aimAngle = (float)(protagonist.getAim());
		//Draw all the protagonist parts

		if(protagonist.isFacingRight()){
			//Draw back foot
			renderMatrix.setRotate(-feetAngle, footBitmap.getWidth()/2, footBitmap.getHeight()/2);
			renderMatrix.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-Const.footXAxis-Const.backFootOffset) - protagonist.getWidth()*Const.footRadius*Math.sin(-feetAngle*Math.PI/180) )*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(Const.footYAxis-0.5) + protagonist.getHeight()*Const.footRadius*Math.cos(-feetAngle*Math.PI/180) )*scaleY));
			canvas.drawBitmap(footBitmap, renderMatrix, null);
			//Draw body
			renderMatrix.setTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-Const.bodyXOffset) )*scaleX), (float)((protagonist.getYPos() - protagonist.getHeight()*(0.5-Const.bodyYOffset + Const.breathOffset*Math.sin((double)protagonist.getBreathCount()/protagonist.getBreathMax()*2*Math.PI)) )*scaleY));
			canvas.drawBitmap(bodyBitmap, renderMatrix, null);
			//Draw eyes and mouth
			renderMatrix.setTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-Const.eyeMouthXOffset) )*scaleX), (float)((protagonist.getYPos() - protagonist.getHeight()*(0.5-Const.eyeMouthYOffset) )*scaleY));
			canvas.drawBitmap(eyeMouthBitmap, renderMatrix, null);
			//Draw front foot
			renderMatrix.setRotate(feetAngle, footBitmap.getWidth()/2, footBitmap.getHeight()/2);
			renderMatrix.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-Const.footXAxis) - protagonist.getWidth()*Const.footRadius*Math.sin(feetAngle*Math.PI/180) )*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(Const.footYAxis-0.5) + protagonist.getHeight()*Const.footRadius*Math.cos(feetAngle*Math.PI/180) )*scaleY));
			canvas.drawBitmap(footBitmap, renderMatrix, null);
			//Draw pupils
			renderMatrix.setTranslate((float)((protagonist.getXPos() + protagonist.getWidth()*(Const.pupilXOffset-0.5)+protagonist.getWidth()*Const.pupilRadius*Math.cos(aimAngle*Math.PI/180))*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(Const.pupilYOffset-0.5)+protagonist.getHeight()*Const.pupilRadius*Math.sin(aimAngle*Math.PI/180) )*scaleY));
			canvas.drawBitmap(pupilBitmap, renderMatrix, null);
			//Draw weapon
			renderMatrix.setTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-Const.weaponXAxis) + protagonist.getWidth()*Const.weaponRadius)*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(Const.weaponYAxis-0.5))*scaleY));
			canvas.drawBitmap(weaponBitmap, renderMatrix, null);
		} else {
			//Draw back foot
			renderMatrix.setRotate(feetAngle, footBitmapFlipped.getWidth()/2, footBitmapFlipped.getHeight()/2);
			renderMatrix.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-Const.footXAxis+Const.backFootOffset) - protagonist.getWidth()*Const.footRadius*Math.sin(Math.PI-feetAngle*Math.PI/180) )*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(Const.footYAxis-0.5) + protagonist.getHeight()*Const.footRadius*Math.cos(-feetAngle*Math.PI/180) )*scaleY));
			canvas.drawBitmap(footBitmapFlipped, renderMatrix, null);
			//Draw body
			renderMatrix.setTranslate((float)((protagonist.getXPos() + protagonist.getWidth()*(0.5-Const.bodyXOffset) )*scaleX) - bodyBitmapFlipped.getWidth(), (float)((protagonist.getYPos() - protagonist.getHeight()*(0.5-Const.bodyYOffset + Const.breathOffset*Math.sin((double)protagonist.getBreathCount()/protagonist.getBreathMax()*2*Math.PI)) )*scaleY));
			canvas.drawBitmap(bodyBitmapFlipped, renderMatrix, null);
			//Draw eyes and mouth
			renderMatrix.setTranslate((float)((protagonist.getXPos() + protagonist.getWidth()*(0.5-Const.eyeMouthXOffset) )*scaleX) - eyeMouthBitmapFlipped.getWidth(), (float)((protagonist.getYPos() - protagonist.getHeight()*(0.5-Const.eyeMouthYOffset) )*scaleY));
			canvas.drawBitmap(eyeMouthBitmapFlipped, renderMatrix, null);
			//Draw front foot
			renderMatrix.setRotate(-feetAngle, footBitmapFlipped.getWidth()/2, footBitmapFlipped.getHeight()/2);
			renderMatrix.postTranslate((float)((protagonist.getXPos() - protagonist.getWidth()*(0.5-Const.footXAxis) - protagonist.getWidth()*Const.footRadius*Math.sin(Math.PI+feetAngle*Math.PI/180) )*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(Const.footYAxis-0.5) + protagonist.getHeight()*Const.footRadius*Math.cos(feetAngle*Math.PI/180) )*scaleY));
			canvas.drawBitmap(footBitmapFlipped, renderMatrix, null);
			//Draw pupils
			renderMatrix.setTranslate((float)((protagonist.getXPos() + protagonist.getWidth()*(Const.pupilXOffset-0.5)+protagonist.getWidth()*Const.pupilRadius*Math.cos(aimAngle*Math.PI/180))*scaleX), (float)((protagonist.getYPos() + protagonist.getHeight()*(Const.pupilYOffset-0.5)+protagonist.getHeight()*Const.pupilRadius*Math.sin(aimAngle*Math.PI/180) )*scaleY));
			canvas.drawBitmap(pupilBitmapFlipped, renderMatrix, null);
			//Draw weapon
			renderMatrix.setTranslate((float)((protagonist.getXPos()  + protagonist.getWidth()*(0.5-Const.weaponXAxis) - protagonist.getWidth()*Const.weaponRadius)*scaleX - weaponBitmapFlipped.getWidth()), (float)((protagonist.getYPos() + protagonist.getHeight()*(Const.weaponYAxis-0.5))*scaleY));
			canvas.drawBitmap(weaponBitmapFlipped, renderMatrix, null);
		}
	}

	public void renderButterfly(Canvas canvas){
		renderMatrix.setRotate(butterfly.getRot(), butterflyBitmaps[0].getWidth()/2, butterflyBitmaps[0].getHeight()/2);
		renderMatrix.postTranslate((float)((butterfly.getX()-Const.butterflySize/2)*scaleX), (float)((butterfly.getY()-Const.butterflySize/2)*scaleY));
		canvas.drawBitmap(butterflyBitmaps[(int)(Math.abs(butterfly.getCounter() % 2))], renderMatrix, null);
	}

	public static void setUserName(String name){
		userName = name;
	}

	public void uploadUserName(String name){
		User user = Session.getCurrentSession().getUser();

		// update his values
		user.setLogin(name);

		// set up a request observer
		RequestControllerObserver observer = new RequestControllerObserver() {

			@Override
			public void requestControllerDidReceiveResponse(RequestController aRequestController) {
				// update displayed values
				User user = ((UserController)aRequestController).getUser();
				setUserName(user.getLogin());
				Log.d(TAG, "Name changed successfully!");
			}

			@Override
			public void requestControllerDidFail(RequestController controller, Exception exception) {

				Log.d(TAG, "Name change failed because:");
				RequestControllerException ctrlException = (RequestControllerException) exception;
				if(ctrlException.hasDetail(RequestControllerException.DETAIL_USER_UPDATE_REQUEST_USERNAME_TAKEN)) {
					Log.d(TAG, "Name taken");
				}
				else if(ctrlException.hasDetail(RequestControllerException.DETAIL_USER_UPDATE_REQUEST_USERNAME_TOO_SHORT)) {
					Log.d(TAG, "Name too short");
				}
				else if(ctrlException.hasDetail(RequestControllerException.DETAIL_USER_UPDATE_REQUEST_INVALID_USERNAME)) {
					Log.d(TAG, "Name invalid");
				}

			}
		};

		// with our observer, set up the request controller
		UserController userController = new  UserController(observer);

		// pass the user into the controller
		userController.setUser(user);

		// submit our changes
		userController.submitUser();
	}

	public static void loadHighscores(){
		if(currentHighScoreMode == 0){
			highScoreList.clear();
		}
		RequestControllerObserver observer = new RequestControllerObserver() {

			@Override
			public void requestControllerDidReceiveResponse(RequestController requestController) {
				List<Score> retrievedScores = ((ScoresController)requestController).getScores();
				Log.d(TAG, "Connection to leaderboards level " + currentHighScoreMode + " successful");
				highScoreList.add(retrievedScores);
				for(Score s: retrievedScores){
					Log.d(TAG, "User: " + s.getUser().getLogin() + " Score: " + s.getResult());
				}
				currentHighScoreMode++;
				loadHighscores();
			}

			@Override
			public void requestControllerDidFail(RequestController aRequestController, Exception anException) {
				Log.d(TAG, "Connection to leaderboards failed");
			}
		};
		ScoresController controller = new ScoresController(observer);
		int maxModes = 20, maxRanks = 20;
		if(currentHighScoreMode < maxModes){
			controller.setMode(currentHighScoreMode); 
			controller.setRangeLength(maxRanks); 
			controller.loadRangeAtRank(1);
		} else {
			leaderboardsLoaded = true;
		}
	}




}

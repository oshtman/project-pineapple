package com.pineapple.valentine;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class LevelBriefer {
	private static final int OPEN = 0, CLOSED = 1, OPENING = 2, CLOSING = 3, SWITCHING = 4;
	private final double HEADER_X = 5, HEADER_Y = 10, TEXT_X= 5, TEXT_Y = 15;
	private int level = -1, previousLevel;
	private double x, y, width, height;
	private String[] headers;
	private ArrayList<ArrayList<String>> briefingTexts;
	private Paint background;
	private Paint header;
	private Paint text;
	private Bitmap thumb;
	private int state = CLOSED;
	private int animationTimer = 0;
	private int animationStop;
	private final int animationTime = 10; 
	private double topY, botY;
	private double scaleX, scaleY;
	private SharedPreferences localScores, settings;

	public LevelBriefer(double x, double y, double width, double height, double scaleX, double scaleY, SharedPreferences settings, SharedPreferences localScores){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		headers = new String[]{
				"Tutorial",
				"Level 1",
				"Level 2",
				"Level 3",
				"Level 4",
				"Level 5",
				"Level 6",
				"Level 7",
				"Level 8",
				"Level 9",
				"Level 10",
				"Final",
				"Credits"
		};
		String[] rawTexts = new String[]{
				"Let your mentor teach you how to make it in this friendly world, the land of Papilio!",
				"Take your first trembling steps toward uncovering the secrets of the mysterious monsters.",
				"More of the strange monsters appear out of nowhere in different forms.",
				"Short trip, but leaving the land of Papilio, your home. Make the best of it! We wish you good luck.",
				"Hide and seek, watch out for sneaky monsters. This could be dangerous for someone like you. Hurry slowly.",
				"Go downhill you must as your journey continues. Are you fast enough to not get caught?",
				"Up, up and away! Are you afraid of heights little one? You better not be!",
				"Don't fall, and you will pass. So jump, jump for your life!",
				"Hunt in hills! Remember, he who fights and runs away may live to fight another day.",
				"How amazing! Which way should you go? Some say all good things are three, you might say twice.",
				"Enter the lair of the enemy! The end is good, everything is good. Or is it?",
				"Final battle. Defeat the evil, or it will defeat you! Evil shall by your hands be expelled!",
				"How much do I love that noble man More than I could tell with words I fear though he'll remain alone With a holy halo of his own"
				};
		//Split the hints up into rows and add them to the final hint list
		briefingTexts = new ArrayList<ArrayList<String>>();
		int lettersPerRow = 33;
		for(int i = 0; i < rawTexts.length; i++){
			briefingTexts.add(new ArrayList<String>());
			while(rawTexts[i].length() > lettersPerRow){
				String row = rawTexts[i].substring(0, lettersPerRow-1);
				int spaceIndex = row.lastIndexOf(" ");
				briefingTexts.get(i).add(rawTexts[i].substring(0, spaceIndex));
				rawTexts[i] = rawTexts[i].substring(spaceIndex+1, rawTexts[i].length());
			}
			briefingTexts.get(i).add(rawTexts[i]);
		}

		header = new Paint();
		header.setTextSize((float)(10*scaleY));
		header.setColor(Color.WHITE);
		header.setDither(true);
		header.setAntiAlias(true);

		text = new Paint();
		text.setTextSize((float)(5*scaleY));
		text.setColor(Color.WHITE);
		text.setDither(true);
		text.setAntiAlias(true);

		background = new Paint();
		background.setColor(Color.argb(120, 120, 120, 120));

		this.localScores = localScores;
		this.settings = settings;

	}

	public boolean handleClick(int level){
		if(level == this.level){
			return true;
		} else if(state == CLOSED){
			open();
			this.level = level;
			return false;
		} else {
			switchLevel(level);

			return false;
		}
	}

	public void open(){
		state = OPENING;
		animationStop = animationTimer + animationTime;
		topY = -100;
	}

	public void close(){
		if(state != SWITCHING && state != CLOSING && state != OPENING){
			state = CLOSING;
			animationStop = animationTimer + animationTime;
			topY = 0;
		} else {
			state = CLOSED;
		}
		level = -1;
	}

	public void switchLevel(int level){
		if(state != SWITCHING && state != CLOSING && state != OPENING){
			previousLevel = this.level;
			this.level = level;
			state = SWITCHING;
			animationStop = animationTimer + animationTime;
			topY = -100;
			botY = 0;
		}
	}

	public void update(){
		animationTimer++;
		switch(state){
		case OPEN:
			break;
		case CLOSED:
			//Nothing
			break;
		case OPENING:
			topY += 100./animationTime;
			if(animationTimer >= animationStop){
				state = OPEN;
			}
			break;
		case CLOSING:
			topY += 100./animationTime;
			if(animationTimer >= animationStop){
				state = CLOSED;
			}
			break;
		case SWITCHING:
			topY += 100./animationTime;
			botY += 100./animationTime;
			if(animationTimer >= animationStop){
				state = OPEN;
			}
			break;
		}

	}

	public void render(Canvas canvas){
		switch(state){
		case CLOSED:
			break;
		case OPEN:
			canvas.drawRect((float)(x*scaleX), (float)(y*scaleY), (float)((x+width)*scaleX), (float)((y+height)*scaleY), background);
			canvas.drawText(headers[level], (float)((HEADER_X+x)*scaleX), (float)((HEADER_Y+y)*scaleY), header);
			for(int i = 0; i < briefingTexts.get(level).size(); i++){
				canvas.drawText(briefingTexts.get(level).get(i), (float)((TEXT_X+x)*scaleX), (float)(i*text.getTextSize() + ((TEXT_Y+y)*scaleY)), text);
			}
			if(settings.getBoolean("scoring", false)){
				canvas.drawText("Your best score: " + localScores.getInt("score_"+settings.getInt("difficulty", 0)+"_"+level, 0), (float)((TEXT_X+x)*scaleX), (float)((height+y)*scaleY-2*text.getTextSize()), text);
			}
			canvas.drawText("Click the level again to start!", (float)((TEXT_X+x)*scaleX), (float)((height+y)*scaleY-text.getTextSize()), text);

			break;
		case SWITCHING: 
			//Draw bot view
			canvas.drawRect((float)(x*scaleX), (float)((botY+y)*scaleY), (float)((x+width)*scaleX), (float)((botY+y+height)*scaleY), background);
			canvas.drawText(headers[previousLevel], (float)((HEADER_X+x)*scaleX), (float)((botY+HEADER_Y+y)*scaleY), header);
			for(int i = 0; i < briefingTexts.get(previousLevel).size(); i++){
				canvas.drawText(briefingTexts.get(previousLevel).get(i), (float)((TEXT_X+x)*scaleX), (float)(i*text.getTextSize() + (botY+TEXT_Y+y)*scaleY), text);
			}
			if(settings.getBoolean("scoring", false)){
				canvas.drawText("Your best score: " + localScores.getInt("score_"+settings.getInt("difficulty", 0)+"_"+previousLevel, 0), (float)((TEXT_X+x)*scaleX), (float)((height+y+botY)*scaleY-2*text.getTextSize()), text);
			}
			canvas.drawText("Click the level again to start!", (float)((TEXT_X+x)*scaleX), (float)((height+y+botY)*scaleY-text.getTextSize()), text);


		case OPENING:
		case CLOSING:
			//Draw top view
			canvas.drawRect((float)(x*scaleX), (float)((topY+y)*scaleY), (float)((x+width)*scaleX), (float)((topY+y+height)*scaleY), background);
			canvas.drawText(headers[level], (float)((HEADER_X+x)*scaleX), (float)((topY+HEADER_Y+y)*scaleY), header);
			for(int i = 0; i < briefingTexts.get(level).size(); i++){
				canvas.drawText(briefingTexts.get(level).get(i), (float)((TEXT_X+x)*scaleX), (float)(i*text.getTextSize() + (topY+TEXT_Y+y)*scaleY), text);
			}
			if(settings.getBoolean("scoring", false)){
				canvas.drawText("Your best score: " + localScores.getInt("score_"+settings.getInt("difficulty", 0)+"_"+level, 0), (float)((TEXT_X+x)*scaleX), (float)((height+y+topY)*scaleY-2*text.getTextSize()), text);
			}
			canvas.drawText("Click the level again to start!", (float)((TEXT_X+x)*scaleX), (float)((height+y+topY)*scaleY-text.getTextSize()), text);
			break;
		}

	}


}

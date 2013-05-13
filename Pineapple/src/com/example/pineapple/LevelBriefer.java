package com.example.pineapple;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LevelBriefer {
	private static final int OPEN = 1, CLOSED = 0, OPENING = 2, CLOSING = 3;
	private final int HEADER_X = 5, HEADER_Y = 5, TEXT_X = 8, TEXT_Y = 10;
	private final int EXP_UP = 1, EXP_DOWN = 2, EXP_RIGHT = 3, EXP_LEFT = 4; 
	private int level;
	private double x, y, width, height, maxWidth, maxHeight;
	private String[] headers;
	private ArrayList<ArrayList<String>> briefingTexts;
	private Paint background;
	private Paint header;
	private Paint text;
	private Bitmap thumb;
	private int state = CLOSED;
	private final int animationTime = 20;
	private int animationTimer = 0;
	private ArrayList<Integer> animationActions;

	public LevelBriefer(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		maxWidth = width;
		maxHeight = height;
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
				"Level 9"
		};
		String[] rawTexts = new String[]{
				"Tutorial",
				"Level 1",
				"Level 2",
				"Level 3",
				"Level 4",
				"Level 5",
				"Level 6",
				"Level 7",
				"Level 8",
				"Level 9"
		};
		//Split the hints up into rows and add them to the final hint list
		briefingTexts = new ArrayList<ArrayList<String>>();
		int lettersPerRow = 50;
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
		animationActions = new ArrayList<Integer>();
		
		header = new Paint();
		text = new Paint();
		
		
		background = new Paint();
		background.setColor(Color.argb(120, 120, 120, 120));
	}

	public void handleClick(int level){
		if(level == this.level){
			close();
		} else if(state == CLOSED){
			open();
			this.level = level;
		} else {
			close();
			open();
			this.level = level;
		}
	}

	public void open(){
		for(int i = 0; i < animationTime; i++){
			if(i < animationTime/2){
				animationActions.add(EXP_RIGHT);
			} else {
				animationActions.add(EXP_DOWN);
			}
		}
	}

	public void close(){
		for(int i = 0; i < animationTime; i++){
			if(i < animationTime/2){
				animationActions.add(EXP_UP);
			} else {
				animationActions.add(EXP_LEFT);
			}
		}
	}


	public void update(){
		if(animationActions.size() > 0){
			if(animationActions.get(0) == EXP_UP || animationActions.get(0) == EXP_LEFT){
				state = CLOSING;
			} else {
				state = OPENING;
			}
			switch(animationActions.get(0)){
			case EXP_RIGHT:
				width += maxWidth/animationTime*2;
				break;
			case EXP_LEFT:
				width -= maxWidth/animationTime*2;
				if(animationActions.size() == 1)
					state = CLOSED;
				break;
			case EXP_UP:
				height -= maxHeight/animationTime*2;
				break;
			case EXP_DOWN:
				height += maxHeight/animationTime*2;
				if(animationActions.size() == 1)
					state = OPEN;
				break;
			}
			animationActions.remove(0);
		} else {
			
		}

	}

	public void render(Canvas canvas, double scaleX, double scaleY){
		switch(state){
		case CLOSED:
			break;
		case OPEN:
			canvas.drawRect((float)(x*scaleX), (float)(y*scaleY), (float)((x+width)*scaleX), (float)((y+height)*scaleY), background);
			canvas.drawText(headers[level], (float)((HEADER_X+x)*scaleX), (float)((HEADER_Y+y)*scaleX), header);
			for(int i = 0; i < briefingTexts.get(level).size(); i++){
				canvas.drawText(briefingTexts.get(level).get(i), (float)((TEXT_X+x)*scaleX), (float)(i*text.getTextSize() + (TEXT_Y*scaleY)), text);
			}
			break;
		case OPENING:
		case CLOSING:
			if(height > 0){
				canvas.drawRect((float)(x*scaleX), (float)(y*scaleY), (float)((x+width)*scaleX), (float)((y+height)*scaleY), background);
			} else {
				canvas.drawLine((float)(x*scaleX), (float)(y*scaleY), (float)((x+width)*scaleX), (float)(y*scaleY), background);
			}
			break;
		}

	}


}

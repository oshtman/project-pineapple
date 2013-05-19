package com.pineapple.valentine;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {
	private SoundPool mSoundPool;
	private HashMap<Integer, Integer> mSoundPoolMap;
	private HashMap<Integer, Integer> resIds;
	private HashMap<Integer, Integer> playIds;
	private AudioManager  mAudioManager;
	private Context mContext;
	private boolean musicLoaded = false;

	public SoundManager(Context theContext) {
		this(theContext, 32);
	}
	
	public SoundManager(Context theContext, int simultaneousTracks) {
		mContext = theContext;
		mSoundPool = new SoundPool(simultaneousTracks, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap = new HashMap<Integer, Integer>();
		resIds = new HashMap<Integer, Integer>();
		playIds = new HashMap<Integer, Integer>();
		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	public void addSound(int index, int soundID) {
		mSoundPoolMap.put(index, mSoundPool.load(mContext, soundID, 1));
		resIds.put(index, soundID);
	}

	public void playSound(int index, double volume) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = (float)volume * streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		
		playIds.put(index, mSoundPool.play((Integer) mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f));
		
	}

	public void playLoopedSound(int index, double volume) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = (float)volume * streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		
		playIds.put(index, mSoundPool.play((Integer) mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f));
	}
	
	public void stop(int index){
		if(playIds.containsKey(index)){
			mSoundPool.stop(playIds.get(index));
		}
	}
	
	public int getDuration(int index){
		MediaPlayer mp = MediaPlayer.create(mContext, resIds.get(index));
		int duration = mp.getDuration()/40;
		mp.release();
		return duration;
	}
	
	public boolean musicLoaded(){
		return musicLoaded;
	}
	
}

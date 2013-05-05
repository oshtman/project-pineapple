package com.example.pineapple;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {
	private SoundPool mSoundPool;
	private HashMap<Integer, Integer> mSoundPoolMap;
	private HashMap<Integer, Integer> resIds;
	private AudioManager  mAudioManager;
	private Context mContext;
	private boolean musicLoaded = false;

	public SoundManager(Context theContext) {
		mContext = theContext;
		mSoundPool = new SoundPool(32, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap = new HashMap<Integer, Integer>();
		resIds = new HashMap<Integer, Integer>();
		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	public void addSound(int index, int soundID) {
		mSoundPoolMap.put(index, mSoundPool.load(mContext, soundID, 1));
		resIds.put(index, soundID);
		
		if(index == 1){
			mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
				@Override
				public void onLoadComplete(SoundPool soundPool, int mySoundId, int status) {
					musicLoaded = true;
				}
			});
		}
	}

	public void playSound(int index) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play((Integer) mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
	}

	public void playLoopedSound(int index) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		mSoundPool.play((Integer) mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
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

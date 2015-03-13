package no.ntnu.progark.towerdefense.main;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


public class SoundManager {
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> soundPoolMap;
	private static AudioManager  audioManager;
	private static Context context;
	
	
	public static void initSounds(Context c) {
	    context = c;
	    soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
	    soundPoolMap = new HashMap<Integer, Integer>();
	    audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public static void addSound(int index, int SoundID) {
	    soundPoolMap.put(index, soundPool.load(context, SoundID, 1));
	}
	
	public static void playSound(int index) {
	float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
	}
	 
	public static void playLoopedSound(int index) {
	    float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
	}
	
	public static void pause(int id){
		soundPool.pause(id);
	}
}

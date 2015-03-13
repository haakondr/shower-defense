package no.ntnu.progark.towerdefense.main;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicHandler {
	private static int musicResource;
	private static MediaPlayer mp;

	public static void setMusicResource(int res) {
		musicResource = res;

	}

	/**
	 * Should be called when creating an activity. 
	 * Won't start playing if the resource parameter is the same as currently playing, to avoid changing of music between activities with same music
	 * @param res - the music resource to be played, usually in R.raw.*
	 * @param c - the context from an activity
	 */
	public static void playMusicForActivity(int res, Context c) {
		if(musicResource != res) {
			setMusicResource(res);
			playMusic(c);
			return;
		}else if(mp!=null) {
			Utils.log("Resuming music");
			mp.start();
			
		}
	}

	public static void playMusic(Context c) {
			if(mp != null) {
				mp.release();
			}
	
		if(musicResource != 0) {			
			mp = MediaPlayer.create(c, musicResource);
			mp.setLooping(true);
			mp.start();
		}
	}

	public static void resumeMusic() {
		mp.start();
	}

	public static void pauseMusic() {
		mp.pause();
	}


	public static void stopMusic() {
		if(mp!=null) {
			Utils.log("stopping music");
			mp.pause();
		}
	}
}

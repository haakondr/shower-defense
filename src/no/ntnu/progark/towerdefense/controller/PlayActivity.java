package no.ntnu.progark.towerdefense.controller;


import sheep.game.Game;
import no.ntnu.progark.towerdefense.R;
import no.ntnu.progark.towerdefense.main.GameHandler;
import no.ntnu.progark.towerdefense.main.Globals;
import no.ntnu.progark.towerdefense.main.MusicHandler;
import no.ntnu.progark.towerdefense.main.SoundManager;
import no.ntnu.progark.towerdefense.main.Utils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;

public class PlayActivity extends Activity {
	private TowerDefenseState tdState;
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		
		
		game = new Game(this, null);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		tdState = new TowerDefenseState(this, metrics);
		game.pushState(tdState);
		initSounds();
		game.setLongClickable(true);
		setContentView(game);
		
		
	}

	private void initSounds() {
		MusicHandler.setMusicResource(R.raw.human4);
		MusicHandler.playMusic(this);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.actionbar, menu);
//
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
////		case R.id.create_tower:
////			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
	
	@Override
	public void onPause() {
		tdState.setLongPressListenerEnabled(false);
		super.onPause();
		MusicHandler.pauseMusic();		
	}
	
	@Override
	public void onResume() {
		tdState.setLongPressListenerEnabled(true);
		super.onResume();
		MusicHandler.resumeMusic();
	}
}

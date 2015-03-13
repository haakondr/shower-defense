package no.ntnu.progark.towerdefense.controller;

import no.ntnu.progark.towerdefense.R;
import no.ntnu.progark.towerdefense.main.GameHandler;
import no.ntnu.progark.towerdefense.main.Globals;
import no.ntnu.progark.towerdefense.main.SoundManager;
import no.ntnu.progark.towerdefense.main.Utils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener{
	
	private final int HOWTO_DIALOG = 0;
	private final int CREDITS_DIALOG = 1;
	private final int GAMEOVER_DIALOG = 2;
	private final int HIGHSCORE_DIALOG = 3;
	private Button btn_play, btn_howto, btn_credits, btn_submitscore, btn_highscore;
	private TextView tv_gameover;
	private Dialog gameoverDialog;
//	private EditText editText_nameText;
	private SharedPreferences prefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		prefs = getSharedPreferences("TD", MODE_PRIVATE);
		
		btn_play  = (Button)findViewById(R.id.btn_play);
//		btn_settings = (Button)findViewById(R.id.btn_settings);
		btn_howto = (Button)findViewById(R.id.btn_howto);
		btn_credits = (Button)findViewById(R.id.btn_credits);
//		btn_highscore = (Button)findViewById(R.id.btn_highscore);
		
		btn_play.setOnClickListener(this);
//		btn_settings.setOnClickListener(this);
		btn_howto.setOnClickListener(this);
		btn_credits.setOnClickListener(this);
//		btn_highscore.setOnClickListener(this);
		gameoverDialog = new Dialog(this);
	}

	@Override
	public void onClick(View v) {
		if(v==btn_play) {
			removeDialog(GAMEOVER_DIALOG);
			Intent intent = new Intent(MenuActivity.this, PlayActivity.class); 
			startActivity(intent);
//		}else if(v==btn_settings) {
			//TODO: legg til funksjonalitet for settings her
		}else if(v==btn_howto) {
			showDialog(HOWTO_DIALOG);
		}else if(v==btn_credits) {
			showDialog(CREDITS_DIALOG);
//		}else if(v==btn_highscore) {
//			showDialog(HIGHSCORE_DIALOG);
//		}else if(v==btn_submitscore) {
//			Utils.log("Submitting score. Name = "+editText_nameText.getText().toString()+" score = "+GameHandler.getScore());
//			GameHandler.saveScore(prefs, editText_nameText.getText().toString());
//			gameoverDialog.dismiss();
		}
	}
	
	@Override
	public Dialog onCreateDialog(int id) {
		if(id == HOWTO_DIALOG) {
			Dialog howToDialog = new Dialog(this);
			howToDialog.setContentView(R.layout.text_dialog);
			howToDialog.setCanceledOnTouchOutside(true);
			TextView text = (TextView)howToDialog.findViewById(R.id.tv_text);
			text.setText(String.format(getResources().getString(R.string.howto), Globals.LIVES));
			howToDialog.setTitle("How to play");
			return howToDialog;
		}else if(id == CREDITS_DIALOG) {
			Dialog creditsDialog = new Dialog(this);
			creditsDialog.setContentView(R.layout.text_dialog);
			creditsDialog.setCanceledOnTouchOutside(true);
			TextView text = (TextView)creditsDialog.findViewById(R.id.tv_text);
			text.setText(R.string.credits);
			creditsDialog.setTitle("Credits");
			return creditsDialog;
		}else if (id==GAMEOVER_DIALOG) {
//			gameoverDialog = new Dialog(this);
			gameoverDialog.setContentView(R.layout.dialog_game_over);
			gameoverDialog.setCanceledOnTouchOutside(true);
			gameoverDialog.setTitle("Game Over");
			tv_gameover = (TextView)gameoverDialog.findViewById(R.id.tv_gameover);
			tv_gameover.setText("Your score was "+GameHandler.getScore());
//			editText_nameText = (EditText)gameoverDialog.findViewById(R.id.editText_name);
//			btn_submitscore = (Button) gameoverDialog.findViewById(R.id.btn_submitScore);
//			btn_submitscore.setOnClickListener(this);	
			return gameoverDialog;
		}else if(id==HIGHSCORE_DIALOG) {
			Dialog highscoreDialog = new Dialog(this);
			highscoreDialog.setContentView(R.layout.highscore);
			highscoreDialog.setCanceledOnTouchOutside(true);
			highscoreDialog.setTitle("Highscore");
			TextView highscoreList = (TextView)highscoreDialog.findViewById(R.id.tv_highscore);
			highscoreList.setText(GameHandler.getHighscoreText(prefs));
			return highscoreDialog;
		}
		else return null;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(GameHandler.isGameActive()) {
			showDialog(GAMEOVER_DIALOG);
			GameHandler.setGameActive(false);
			Utils.log("game over. score was "+GameHandler.getScore());
		}
	}
}

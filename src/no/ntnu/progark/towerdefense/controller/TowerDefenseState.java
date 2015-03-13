package no.ntnu.progark.towerdefense.controller;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import no.ntnu.progark.towerdefense.R;
import no.ntnu.progark.towerdefense.main.GameHandler;
import no.ntnu.progark.towerdefense.main.Globals;
import no.ntnu.progark.towerdefense.main.Utils;
import no.ntnu.progark.towerdefense.model.game.TowerDefenseMapLayer;
import no.ntnu.progark.towerdefense.model.towers.factories.DeathKnightTowerFactory;
import no.ntnu.progark.towerdefense.model.towers.factories.AxeThrowerTowerFactory;
import no.ntnu.progark.towerdefense.model.towers.factories.DemonTowerFactory;
import no.ntnu.progark.towerdefense.model.towers.factories.CatapultTowerFactory;
import no.ntnu.progark.towerdefense.view.SpriteFactory;
import no.ntnu.progark.towerdefense.view.TowerSprite;
import sheep.game.State;
import sheep.game.World;
import sheep.math.Vector2;

public class TowerDefenseState extends State implements OnClickListener {
	
	private GameController gameController;
	
	private static final int INVALID_POINTER_ID = -1;
	private final int DIALOG_TOWER = 0;
	private final int DIALOG_GAME_OVER = 1;

	private World world;
	private TowerDefenseMapLayer mapLayer;
	private Paint whiteFrame;
	
	private float newTowerLocationX, newTowerLocationY;
	private ScaleGestureDetector scaleDetector;
	private GestureController gestureController;
//	private Context context;
		private PlayActivity context;


	private float scaleFactorX;
	private float scaleFactorY;
	private float mLastTouchX, mLastTouchY;
	private float mPosX = 0.0f;
	private float mPosY = 0.0f;
	private int mActivePointerId = INVALID_POINTER_ID;
	private Dialog dialog;
	private LinearLayout img_demon_tower, img_deathknight_tower, img_catapult_tower,
	img_axethrower_tower;
	
	private float displayWidth, displayHeight;
	private float maxPosY, maxPosX;
	
	private float  screenAdjustFactor;
	
	private Paint whitePaint;
	private Paint blackPaint;
	private Rect topRect;
	private LinearLayout axethrowerLayout;
	
	public TowerDefenseState(PlayActivity context, DisplayMetrics metrics) {
		this.context = context;
		addTouchListener(this);
		world = new World();
		mapLayer = new TowerDefenseMapLayer(context);
		world.addLayer(mapLayer);
		
		this.displayWidth = metrics.widthPixels;
		this.displayHeight = metrics.heightPixels;

		gameController = new GameController(mapLayer);
		
		screenAdjustFactor = displayHeight / (float)mapLayer.getMapHeight();
		scaleFactorX = screenAdjustFactor;
		scaleFactorY = screenAdjustFactor;
		
		Utils.log("screen size: "+displayWidth+","+displayHeight);
		Utils.log("map size: " +mapLayer.getMapWidth()+","+mapLayer.getMapHeight());
		Utils.log("screen adjust factor:" +screenAdjustFactor+","+screenAdjustFactor);

		whiteFrame = new Paint();
		whiteFrame.setColor(Color.WHITE);
		whiteFrame.setStyle(Paint.Style.STROKE);
		whitePaint = new Paint(Color.WHITE);
		blackPaint = new Paint(Color.BLACK);
		topRect = new Rect(0,0,metrics.widthPixels,40);

		initialize();
		

		// Create our ScaleGestureDetector
		gestureController = new GestureController(this);

		scaleDetector = new ScaleGestureDetector(context, gestureController);
	}

	@Override
	public void onClick(View v) {
		if(v==img_axethrower_tower) {
			TowerSprite tower = SpriteFactory.createTowerSprite(new AxeThrowerTowerFactory().createTower());
			if(GameHandler.getMoney() >= tower.getModel().getPrice()){
			tower.setPosition(newTowerLocationX, newTowerLocationY);
			tower.setReferenceX(newTowerLocationX);
			tower.setInitialX(newTowerLocationX);
			gameController.addTower(tower);
			GameHandler.spendMoney(tower.getModel().getPrice());
			dialog.dismiss();
			}
		}else if(v==img_deathknight_tower) {
			TowerSprite tower = SpriteFactory.createTowerSprite(new DeathKnightTowerFactory().createTower());
			if(GameHandler.getMoney() >= tower.getModel().getPrice()){
			tower.setPosition(newTowerLocationX, newTowerLocationY);
			tower.setReferenceX(newTowerLocationX);
			tower.setInitialX(newTowerLocationX);
			gameController.addTower(tower);
			GameHandler.spendMoney(tower.getModel().getPrice());
			dialog.dismiss();
			}
		}else if(v==img_demon_tower) {
			TowerSprite tower = SpriteFactory.createTowerSprite(new DemonTowerFactory().createTower());
			if(GameHandler.getMoney() >= tower.getModel().getPrice()){
			tower.setPosition(newTowerLocationX, newTowerLocationY);
			tower.setReferenceX(newTowerLocationX);
			tower.setInitialX(newTowerLocationX);
			gameController.addTower(tower);
			GameHandler.spendMoney(tower.getModel().getPrice());
			dialog.dismiss();
			}
		}else if(v==img_catapult_tower) {
			TowerSprite tower = SpriteFactory.createTowerSprite(new CatapultTowerFactory().createTower());
			if(GameHandler.getMoney() >= tower.getModel().getPrice()){
			tower.setPosition(newTowerLocationX, newTowerLocationY);
			tower.setReferenceX(newTowerLocationX);
			tower.setInitialX(newTowerLocationX);
			gameController.addTower(tower);
			GameHandler.spendMoney(tower.getModel().getPrice());
			dialog.dismiss();
			}
		}else {
			return;
		}

	}

	public void setNewTowerLocationX(float newTowerLocationX) {
		
		this.newTowerLocationX = (newTowerLocationX - mPosX)/scaleFactorX;
		Utils.log("clicked X location to "+newTowerLocationX+". scalefactor is: "+scaleFactorX+". calculating tower location X: "+this.newTowerLocationX+" | mPosX = "+mPosX);
	}

	public void setNewTowerLocationY(float newTowerLocationY) {
		this.newTowerLocationY = (newTowerLocationY - mPosY)/scaleFactorY;		
		Utils.log("clicked Y location to "+newTowerLocationY+". scalefactor is: "+scaleFactorY+". calculating tower location Y: "+this.newTowerLocationY+" | mPosY = "+mPosY);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// Let the ScaleGestureDetector inspect all events.
		scaleDetector.onTouchEvent(event);
		gestureController.detectGestures(event);

		final int action = event.getAction();
		
		
		/*
		 * MotionEvent.ACTION_MASK = 0 x 00 00 00 FF
		 * 
		 * Utfør bitwise-AND for å nulle ut alt annet enn handlingens id (AA)
		 * fra getAction():
		 * 
		 * Resultat: 0 x 00 00 00 AA
		 */
		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN: {
				final float x = event.getX();
				final float y = event.getY();
	
				mLastTouchX = x;
				mLastTouchY = y;
				mActivePointerId = event.getPointerId(0);
				break;
			}

			case MotionEvent.ACTION_MOVE: {
				final int pointerIndex = event.findPointerIndex(mActivePointerId);
				final float x = event.getX(pointerIndex);
				final float y = event.getY(pointerIndex);
	
				// Only move if the ScaleGestureDetector isn't processing a gesture.
				if (!scaleDetector.isInProgress()) {
					final float dx = x - mLastTouchX;
					final float dy = y - mLastTouchY;
	
					mPosX += dx;
					mPosY += dy;
				}

	//			maxPosX = Math.max(0f, Math.min(x, mapLayer.getMapWidth() - (displayWidth/scaleFactorX)));
	//			maxPosY = Math.max(0f, Math.min(y, mapLayer.getMapHeight() - (displayHeight/scaleFactorX)));
	//			Utils.log("XY pos: "+x+","+y+"  setting XY to "+maxPosX+","+maxPosY);
				
				mLastTouchX = x;
				mLastTouchY = y;

				break;
			}

			case MotionEvent.ACTION_UP: {
				mActivePointerId = INVALID_POINTER_ID;
				break;
			}

			case MotionEvent.ACTION_CANCEL: {
				mActivePointerId = INVALID_POINTER_ID;
				break;
			}

			case MotionEvent.ACTION_POINTER_UP: {
				/*
				 * MotionEvent.ACTION_POINTER_INDEX_SHIFT = 8
				 * MotionEvent.ACTION_POINTER_INDEX_MASK = 0 x 00 00 FF 00
				 * 
				 * Bitwise-AND med event.getAction() for å nulle ut alt utenom
				 * indeksen (II).
				 * 
				 * Utfør bitshift mot høyre (8 bits) for å få indeksen (II) i
				 * 10-tallssystemet:
				 * 
				 * Resultat: 0 x 00 00 II 00 ---> 0 x 00 00 00 II
				 * 
				 * (II er aldri større enn XX, der X er antall fingre skjermen
				 * støtter samtidig, i praksis maks 03).
				 */
				final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				final int pointerId = event.getPointerId(pointerIndex);
				if (pointerId == mActivePointerId) {
					// This was our active pointer going up. Choose a new
					// active pointer and adjust accordingly.
					final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
					mLastTouchX = event.getX(newPointerIndex);
					mLastTouchY = event.getY(newPointerIndex);
					mActivePointerId = event.getPointerId(newPointerIndex);
				}
				break;
			}
		}
		return true;
	}

	private void initialize() {
		GameHandler.resetGameValues();

		dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_create_tower);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setTitle("Choose Tower");
	
		img_deathknight_tower = (LinearLayout) dialog.findViewById(R.id.layout_deathknight_tower);
		img_axethrower_tower = (LinearLayout) dialog.findViewById(R.id.layout_axeThrower);
		img_demon_tower = (LinearLayout) dialog.findViewById(R.id.layout_demon);
		img_catapult_tower = (LinearLayout) dialog.findViewById(R.id.layout_catapult_tower);
		
		TextView axeCost = (TextView)dialog.findViewById(R.id.text_axeThrower_cost);
		axeCost.setText("$"+Globals.TOWERCOST_AXETHROWER);
		
		TextView dkCost = (TextView)dialog.findViewById(R.id.text_deathKnight_cost);
		dkCost.setText("$"+Globals.TOWERCOST_DEATHKNIGHT);
		
		TextView demonCost = (TextView)dialog.findViewById(R.id.text_demon_cost);
		demonCost.setText("$"+Globals.TOWERCOST_DEMON);
		
		TextView cataCost = (TextView)dialog.findViewById(R.id.text_catapult_cost);
		cataCost.setText("$"+Globals.TOWERCOST_CATAPULT);

		img_deathknight_tower.setOnClickListener(this);
		img_axethrower_tower.setOnClickListener(this);
		img_demon_tower.setOnClickListener(this);
		img_catapult_tower.setOnClickListener(this);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		canvas.translate(mPosX, mPosY);		
		canvas.scale(scaleFactorX, scaleFactorY);
		super.draw(canvas);
		world.draw(canvas);
		gameController.draw(canvas);
		canvas.restore();
		drawGameState(canvas);
	}
	
	private void drawGameState(Canvas canvas) {
		canvas.drawRect(topRect, blackPaint);
		whitePaint.setColor(Color.WHITE);
		canvas.drawText("Warcraft TD", 20, 20, whitePaint);
		canvas.drawText("Wave: " + gameController.getWaveController().getCurrentWaveNumber(),200,10, whitePaint);
		canvas.drawText("Money: " + GameHandler.getMoney(),200,20, whitePaint);
		canvas.drawText("Lives: " + GameHandler.getLives(), 350, 10, whitePaint);
		canvas.drawText("Score: " + GameHandler.getScore(), 350, 20, whitePaint);
	}
	

	@Override
	public void update(float dt) {
		super.update(dt);
		world.update(dt);
		gameController.update(dt);
		if(GameHandler.isDead()) {
			context.finish();
		}
	}
	
	public void multiplyScaleFactor(float factor) {
		this.scaleFactorX *= factor;
		this.scaleFactorY *= factor;

		scaleFactorX = Math.max(screenAdjustFactor, Math.min(scaleFactorX, 3.0f));
		scaleFactorY = Math.max(screenAdjustFactor, Math.min(scaleFactorY, 3.0f));
	}
	
	public void showDialog(int id) {
		Utils.log("Displaying create tower dialog");
		if (id == DIALOG_TOWER) {
			ImageView axeImg = (ImageView)dialog.findViewById(R.id.img_axethrower_tower);
			ImageView dkImg = (ImageView)dialog.findViewById(R.id.img_deathknight_tower);
			ImageView demonImg = (ImageView)dialog.findViewById(R.id.img_demon_tower);
			ImageView cataImg = (ImageView)dialog.findViewById(R.id.img_catapult_tower);
			
			if(Globals.TOWERCOST_AXETHROWER > GameHandler.getMoney()) {
				axeImg.setImageResource(R.drawable.axethrower1_grey);
				
			}else {
				axeImg.setImageResource(R.drawable.axethrower1);
			}
			if(Globals.TOWERCOST_CATAPULT > GameHandler.getMoney()) {
				cataImg.setImageResource(R.drawable.catapult1_grey);
			}else {
				cataImg.setImageResource(R.drawable.catapult1);
			}
			if(Globals.TOWERCOST_DEATHKNIGHT > GameHandler.getMoney()) {
				dkImg.setImageResource(R.drawable.dk1_grey);
			}else {
				dkImg.setImageResource(R.drawable.dk1);
			}
			if(Globals.TOWERCOST_DEMON > GameHandler.getMoney()) {
				demonImg.setImageResource(R.drawable.demon1_grey);
			}else {
				demonImg.setImageResource(R.drawable.demon1);
			}
			
			dialog.show();
		}
	}
	
	public void setLongPressListenerEnabled(boolean enabled) {
		gestureController.setLongPressEnabled(enabled);
	}
}

package no.ntnu.progark.towerdefense.view;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.SpriteView;
import no.ntnu.progark.towerdefense.main.GameHandler;
import no.ntnu.progark.towerdefense.main.Globals;
import no.ntnu.progark.towerdefense.main.Utils;
import no.ntnu.progark.towerdefense.model.enemies.EnemyImageStructure;
import no.ntnu.progark.towerdefense.model.game.EnemyPath;
import no.ntnu.progark.towerdefense.model.game.TowerDefenseMapLayer;

public class EnemySprite extends Sprite {

	public final static String KNIGHT = "knight";
	public final static String FOOTMAN = "footman";
	public final static String OGRE = "ogre";
	public final static String PEASANT = "peasant";
	public final static String SKELETON = "skeleton";

	private TowerDefenseMapLayer mapLayer;
	private EnemyPath path;

	private EnemyImageStructure images;
	private boolean hasRemovedLifeFromPlayer;

	private int hitPoints;
	private int reward;

	private long animationCounter;
	private static long ANIMATION_DELAY = 200;

	private int currentFrame;
	private float imageWidth, imageHeight;
	private boolean isVisible, reachedGoal, dead;

	public EnemySprite(String type, EnemyImageStructure images){
		super(images.getAnimationImages()[0]);
		imageWidth = images.getAnimationImages()[0].getWidth();
		imageHeight = images.getAnimationImages()[0].getHeight(); 
		this.images = images;
		dead = false;

		//TODO: Set things
		if (type.equals(PEASANT)) {
			setReward(Globals.REWARD_PEASANT);
			setHitPoints(Globals.HITPOINTS_PEASANT);

		} else if(type.equals(SKELETON)) {
			setReward(Globals.REWARD_SKELETON);
			setHitPoints(Globals.HITPOINTS_SKELETON);

		} else if(type.equalsIgnoreCase(FOOTMAN)) {
			setReward(Globals.REWARD_FOOTMAN);
			setHitPoints(Globals.HITPOINTS_FOOTMAN);

		} else if(type.equalsIgnoreCase(KNIGHT)) {
			setReward(Globals.REWARD_KNIGHT);
			setHitPoints(Globals.HITPOINTS_KNIGHT);

		} else if(type.equalsIgnoreCase(OGRE)) {
			setReward(Globals.REWARD_OGRE);
			setHitPoints(Globals.HITPOINTS_OGRE);

		}
		animationCounter = System.currentTimeMillis();
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public void setMapLayer(TowerDefenseMapLayer mapLayer){
		this.mapLayer = mapLayer;
		this.path = mapLayer.getEnemyPath();
		setPosition(mapLayer.getSpawnArea().centerX(), mapLayer.getSpawnArea().centerY());
	}


	public void update(float dt) {
		if(isDead()) {
			setSpeed(0, 0);
			setView((SpriteView)images.getDeathImage());
		} else if(isInGoalArea()){
			reachedGoal = true;
			setSpeed(0, 0);
			if(!hasRemovedLifeFromPlayer) {
				GameHandler.removeLife();
				hasRemovedLifeFromPlayer=true;
			}
		} else if(!isInGoalArea() && !isDead()) {
			int[] logicalPosition = mapLayer.positionToMapCoordinates(getPosition());
			int turningType = path.isTurningPoint(logicalPosition);

			switch (turningType) {
			case EnemyPath.TURNING_EAST:
				setSpeed(20, 0);
				setOffset(imageHeight/2, -imageWidth/2);
				setOrientation(270.0f);
				break;
			case EnemyPath.TURNING_NORTH:
				setSpeed(0, -20);
				setOffset(-imageHeight/2, -imageWidth/2);
				setOrientation(180.0f);
				break;
			case EnemyPath.TURNING_SOUTH:
				setSpeed(0, 20);
				setOffset(imageHeight/2, imageWidth/2);
				setOrientation(0.0f);
				break;
			case EnemyPath.TURNING_WEST:
				setSpeed(-20, 0);
				setOffset(-imageHeight/2, imageWidth/2);
				setOrientation(90.0f);
				break;
			default:
				//Do nothing
				break;
			}
			if(shouldAnimate()){
				currentFrame = (currentFrame+1)%images.getAnimationImages().length;
				setView((SpriteView)images.getAnimationImages()[currentFrame]);
				animationCounter = System.currentTimeMillis();
			}
		}
		
		if (isVisible && !dead) {
			super.update(dt);
		}
		
	}

	public boolean shouldAnimate() {
		return (animationCounter + ANIMATION_DELAY) < System.currentTimeMillis();
	}

	public boolean isInGoalArea(){
		return mapLayer.getGoalArea().contains((int)getX(), (int)getY());
	}

	public boolean isDead() {
		if(getHitPoints() <= 0 && !dead){
			Utils.log("Enemy died");
			dead = true;
			GameHandler.increaseMoney(getReward());
			GameHandler.increaseScore(getReward());
		}
		return getHitPoints() <= 0;
	}

	public boolean hasReachedGoal() {
		return reachedGoal;
	}

	public float getXPosition() {
		return getX();
	}

	public float getYPosition() {
		return getY();
	}

	public void setVisible(boolean visible){
		this.isVisible = visible;
	}

	@Override
	public void draw(Canvas canvas){
		if(isVisible)
			super.draw(canvas);
	}

	public void reduceHitPoints(int damage) { 
		hitPoints -= damage;
	}

	public int getHitPoints() {
		return hitPoints;
	}


	public EnemyImageStructure getImages() {
		return images;
	}

	public int getReward() {
		return reward;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

}

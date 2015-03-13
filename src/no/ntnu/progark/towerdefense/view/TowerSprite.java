package no.ntnu.progark.towerdefense.view;


import no.ntnu.progark.towerdefense.main.Utils;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;
import sheep.game.Sprite;
import sheep.graphics.SpriteView;

public class TowerSprite extends Sprite {

	private int currentFrame;
	private long animationCounter;
	private static final long ANIMATION_DELAY = 100;
	private boolean firing = false;
	private EnemySprite target;
	private AbstractTowerModel model;
	private float referenceX;
	private float initalX;
	
	public TowerSprite(AbstractTowerModel model){
		super(model.getImages().getIdleImage());
		this.model = model;
		animationCounter = System.currentTimeMillis();
		Utils.log("x " +this.getX() + " width " + model.getImages().getIdleImage().getWidth());
	}
	
	public AbstractTowerModel getModel(){
		return model;
	}

	@Override
	public void update(float dt){
		if(firing){
			if(shouldAnimate()) {
				currentFrame = (currentFrame+1)%model.getImages().getAnimationImages().length;
				setView((SpriteView)model.getImages().getAnimationImages()[currentFrame]);
				animationCounter = System.currentTimeMillis();
			}
		}
		else{
			setView((SpriteView)model.getImages().getIdleImage());
		}
		super.update(dt);
	}

	private boolean shouldAnimate(){
		return (animationCounter + ANIMATION_DELAY) < System.currentTimeMillis();
	}

	public void setFiring(boolean firing) {
		this.firing = firing;
		if(firing && target != null){
			if(getTarget().getXPosition() < getX()){
				setScale(-1, 1);
				setPosition(referenceX, getY());
			}
			else{
				setScale(1, 1);
				setPosition(initalX, getY());
			}
		}
	}

	public boolean isFiring(){
		return firing;
	}

	public void setTarget(EnemySprite enemy){
		if(target != enemy){
			target = enemy;
		}
	}

	public void removeTarget() {
		target = null;
	}

	public EnemySprite getTarget(){
		return target;
	}
	
	public void setReferenceX(float x){
		 referenceX = x+(model.getImages().getIdleImage().getWidth()/2);
	}
	
	public void setInitialX(float x){
		initalX = x;
	}
}

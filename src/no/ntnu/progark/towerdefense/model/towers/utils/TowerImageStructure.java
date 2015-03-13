package no.ntnu.progark.towerdefense.model.towers.utils;

import sheep.graphics.Image;

public class TowerImageStructure {

	private Image[] animationImages;
	private Image idleImage;
	
	public TowerImageStructure(Image[] animationImages, Image idleImage){
		this.animationImages = animationImages;
		this.idleImage = idleImage;
	}

	public Image[] getAnimationImages() {
		return animationImages;
	}

	public Image getIdleImage() {
		return idleImage;
	}
	
	public static TowerImageStructure getTowerByType(String type){
		if(type.equals("axeThrower")){
			return TowerImageHandler.getAxeThrowerImages();
		}
		else if(type.equals("deathKnight")){
			return TowerImageHandler.getDeathKnightImages();
		}
		else if(type.equalsIgnoreCase("demon")){
			return TowerImageHandler.getDemonImages();
		}
		else if(type.equalsIgnoreCase("catapult")){
			return TowerImageHandler.getCatapultImages();
		}
		else{
			return null;
		}
	}
	
	
}

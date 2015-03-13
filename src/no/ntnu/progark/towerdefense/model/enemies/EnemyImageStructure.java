package no.ntnu.progark.towerdefense.model.enemies;
import sheep.graphics.Image;

public class EnemyImageStructure {

	private Image[] animationImages;
	private Image hitImage;
	private Image deathImage;
	
	public EnemyImageStructure(Image[] animationImages, Image hitImage, Image deathImage){
		this.animationImages = animationImages;
		this.hitImage = hitImage;
		this.deathImage = deathImage;
	}
	
	public Image[] getAnimationImages(){
		return animationImages;
	}
	
	public Image getHitImage(){
		return hitImage;
	}
	
	public Image getDeathImage(){
		return deathImage;
	}
	
	public static EnemyImageStructure getEnemyImageByType(String type){
		if(type.equals("peasant")){
			return EnemyImageHandler.getPeasentImages();
		}else if(type.equals("skeleton")){
			return EnemyImageHandler.getSkeletonImages();
		}else if(type.equalsIgnoreCase("footman")){
			return EnemyImageHandler.getFootmanImages();
		}else if(type.equalsIgnoreCase("knight")){
			return EnemyImageHandler.getKnightImages();
		}else if(type.equalsIgnoreCase("ogre")){
			return EnemyImageHandler.getOgreImages();
		}
		return null;
	}
}

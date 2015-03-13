package no.ntnu.progark.towerdefense.model.enemies;

import no.ntnu.progark.towerdefense.R;
import sheep.graphics.Image;

public class EnemyImageHandler {
	public static EnemyImageStructure getPeasentImages(){
		return new EnemyImageStructure(new Image[]{new Image(R.drawable.peasant),new Image(R.drawable.peasant2), new Image(R.drawable.peasant3)}, new Image(R.drawable.hitpeasant), new Image(R.drawable.deadpeasant));
	}
	public static EnemyImageStructure getFootmanImages(){
		return new EnemyImageStructure(new Image[]{new Image(R.drawable.footman1),new Image(R.drawable.footman2), new Image(R.drawable.footman3)}, new Image(R.drawable.hitfootman), new Image(R.drawable.deadfootman));
	}
	
	public static EnemyImageStructure getSkeletonImages(){
		return new EnemyImageStructure(new Image[]{new Image(R.drawable.skeleton1),new Image(R.drawable.skeleton2), new Image(R.drawable.skeleton3), new Image(R.drawable.skeleton4)}, new Image(R.drawable.skeletonhit), new Image(R.drawable.skeletondead));
	}
	
	public static EnemyImageStructure getOgreImages() {
		return new EnemyImageStructure(new Image[]{new Image(R.drawable.ogre1),new Image(R.drawable.ogre2), new Image(R.drawable.ogre3), new Image(R.drawable.ogre4)}, new Image(R.drawable.hitogre), new Image(R.drawable.deadogre));
	}
	
	public static EnemyImageStructure getKnightImages() {
		return new EnemyImageStructure(new Image[]{new Image(R.drawable.knight1),new Image(R.drawable.knight2), new Image(R.drawable.knight3), new Image(R.drawable.knight4)}, new Image(R.drawable.hitknight), new Image(R.drawable.deadknight));
	}
}

package no.ntnu.progark.towerdefense.model.towers.utils;

import no.ntnu.progark.towerdefense.R;
import sheep.graphics.Image;

public class TowerImageHandler {
	
	public static TowerImageStructure getAxeThrowerImages(){
		return new TowerImageStructure(new Image[]{new Image(R.drawable.axethrower2), new Image(R.drawable.axethrower3), new Image(R.drawable.axethrower4), new Image(R.drawable.axethrower5)}, new Image(R.drawable.axethrower1));
	}
	
	public static TowerImageStructure getDeathKnightImages(){
		return new TowerImageStructure(new Image[]{new Image(R.drawable.dk2), new Image(R.drawable.dk3), new Image(R.drawable.dk4), new Image(R.drawable.dk5)}, new Image(R.drawable.dk1));
	}
	
	public static TowerImageStructure getDemonImages(){
		return new TowerImageStructure(new Image[]{new Image(R.drawable.demon2),new Image(R.drawable.demon3),new Image(R.drawable.demon4),new Image(R.drawable.demon5),new Image(R.drawable.demon6),new Image(R.drawable.demon7)}, new Image(R.drawable.demon1));
	}
	
	public static TowerImageStructure getCatapultImages(){
		return new TowerImageStructure(new Image[]{new Image(R.drawable.catapult1),new Image(R.drawable.catapult2),new Image(R.drawable.catapult3)}, new Image(R.drawable.catapult1));
	}
}

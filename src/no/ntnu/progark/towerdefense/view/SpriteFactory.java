package no.ntnu.progark.towerdefense.view;

import java.util.ArrayList;

import no.ntnu.progark.towerdefense.model.enemies.EnemyImageStructure;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;

public class SpriteFactory {
	
	public static ArrayList<EnemySprite> createEnemySprite(String type, int count) {
		ArrayList<EnemySprite> sprites = new ArrayList<EnemySprite>();
		for (int i = 0; i < count; i++) {
			sprites.add(new EnemySprite(type, EnemyImageStructure.getEnemyImageByType(type)));
		}
		return sprites;
	}
	
	public static TowerSprite createTowerSprite(AbstractTowerModel model) {
		return new TowerSprite(model);
	}
}

package no.ntnu.progark.towerdefense.model.towers.abstracts;

import no.ntnu.progark.towerdefense.model.towers.utils.TowerImageStructure;

public interface AbstractTowerModel {
	public int getDamage();
	public float getRange();
	public TowerImageStructure getImages();
	public void upgrade(int damage, int range);
	public boolean canAttackAir();
	public boolean canAttackGround();
	public boolean isSplashDamage();
	public int getPrice();
}

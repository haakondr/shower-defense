package no.ntnu.progark.towerdefense.model.towers;

import no.ntnu.progark.towerdefense.main.Globals;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;
import no.ntnu.progark.towerdefense.model.towers.utils.TowerImageStructure;

public class CatapultModel implements AbstractTowerModel {

	public static final String TOWER_TYPE = "catapult";
	private int damage = Globals.DAMAGE_CATAPULT;
	private int range = Globals.RANGE_CATAPULT;
	private int price = Globals.TOWERCOST_CATAPULT;

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public float getRange() {
		return range;
	}

	@Override
	public TowerImageStructure getImages() {
		return TowerImageStructure.getTowerByType(TOWER_TYPE);
	}

	@Override
	public void upgrade(int damage, int range) {
		this.damage += damage;
		this.range += range;
		
	}

	@Override
	public boolean canAttackAir() {
		return false;
	}

	@Override
	public boolean canAttackGround() {
		return false;
	}

	@Override
	public boolean isSplashDamage() {
		return true;
	}

	@Override
	public int getPrice() {
		return price;
	}
}

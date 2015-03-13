package no.ntnu.progark.towerdefense.model.towers;

import no.ntnu.progark.towerdefense.main.Globals;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;
import no.ntnu.progark.towerdefense.model.towers.utils.TowerImageStructure;

public class AxeThrowerModel implements AbstractTowerModel {

	public static final String TOWER_TYPE = "axeThrower";
	
	private int damage = Globals.DAMAGE_AXETHROWER;
	private int range = Globals.RANGE_AXETHROWER;
	private int price = Globals.TOWERCOST_AXETHROWER;
	
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
	public boolean canAttackAir() {
		return true;
	}

	@Override
	public boolean canAttackGround() {
		return true;
	}

	@Override
	public void upgrade(int damage, int range) {
		this.damage += damage;
		this.range += range;
		price += (damage + range);
		
	}

	@Override
	public boolean isSplashDamage() {
		return false;
	}

	@Override
	public int getPrice() {
		return price;
	}
}

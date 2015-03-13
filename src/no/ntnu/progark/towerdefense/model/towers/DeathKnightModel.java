package no.ntnu.progark.towerdefense.model.towers;

import no.ntnu.progark.towerdefense.main.Globals;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;
import no.ntnu.progark.towerdefense.model.towers.utils.TowerImageStructure;

public class DeathKnightModel implements AbstractTowerModel {

	public final static String TOWER_TYPE = "deathKnight";
	
	
	private int damage = Globals.DAMAGE_DEATHKNIGHT;
	private int range = Globals.RANGE_DEATHKNIGHT;
	private int price = Globals.TOWERCOST_DEATHKNIGHT;
	
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
		return false;
	}

	@Override
	public void upgrade(int damage, int range) {
		this.damage += damage;
		this.range += range;
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

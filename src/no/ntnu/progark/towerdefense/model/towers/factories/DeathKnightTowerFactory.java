package no.ntnu.progark.towerdefense.model.towers.factories;

import no.ntnu.progark.towerdefense.model.towers.DeathKnightModel;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModelFactory;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;

public class DeathKnightTowerFactory implements AbstractTowerModelFactory {

	@Override
	public AbstractTowerModel createTower() {
		return new DeathKnightModel();
	}
}

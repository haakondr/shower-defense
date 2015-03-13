package no.ntnu.progark.towerdefense.model.towers.factories;

import no.ntnu.progark.towerdefense.model.towers.DemonModel;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModelFactory;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;

public class DemonTowerFactory implements AbstractTowerModelFactory {
	@Override
	public AbstractTowerModel createTower() {
		return new DemonModel();
	}
}

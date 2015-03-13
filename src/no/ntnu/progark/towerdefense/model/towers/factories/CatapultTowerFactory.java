package no.ntnu.progark.towerdefense.model.towers.factories;

import no.ntnu.progark.towerdefense.model.towers.CatapultModel;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModelFactory;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;

public class CatapultTowerFactory implements AbstractTowerModelFactory {

	@Override
	public AbstractTowerModel createTower() {
		return new CatapultModel();
	}

}

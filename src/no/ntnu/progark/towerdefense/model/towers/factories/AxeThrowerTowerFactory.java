package no.ntnu.progark.towerdefense.model.towers.factories;

import no.ntnu.progark.towerdefense.model.towers.AxeThrowerModel;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModelFactory;
import no.ntnu.progark.towerdefense.model.towers.abstracts.AbstractTowerModel;

public class AxeThrowerTowerFactory implements AbstractTowerModelFactory{ 

	@Override
	public AbstractTowerModel createTower() {
		return new AxeThrowerModel();
	}

}

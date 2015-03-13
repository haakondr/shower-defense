package no.ntnu.progark.towerdefense.controller;

import java.util.ArrayList;
import no.ntnu.progark.towerdefense.main.Utils;
import no.ntnu.progark.towerdefense.model.game.TowerDefenseMapLayer;
import no.ntnu.progark.towerdefense.view.EnemySprite;

public class WaveController {
	
	private int waveNumber;
	private int NUMBER_OF_WAVES = 5;
	private String[] waveSetup;
	
	private ArrayList<Wave> waves;
	private TowerDefenseMapLayer mapLayer;
	
	public WaveController(TowerDefenseMapLayer mapLayer){
		this.mapLayer = mapLayer;
		waves = new ArrayList<Wave>();
		waveNumber = 0;
		waveSetup = new String[NUMBER_OF_WAVES];
		setupWaves();
	}
	
	private void setupWaves() {
		
		waveSetup[0] = "10." + EnemySprite.PEASANT;
		waveSetup[1] = "20." + EnemySprite.SKELETON;
		waveSetup[2] = "30." + EnemySprite.FOOTMAN;
		waveSetup[3] = "40." + EnemySprite.OGRE;
		waveSetup[4] = "50." + EnemySprite.KNIGHT;
		
		for(int i = 0; i < NUMBER_OF_WAVES; i++){
			String[] s = waveSetup[i].split("\\.");
			waves.add(new Wave(s[1], Integer.parseInt(s[0])));
		}
	}
	
	public void newWave(){
			waveNumber++;
	}
	
	public int getCurrentWaveNumber(){
		return waveNumber;
	}

	public ArrayList<EnemySprite> getNextWaveEnemies(){
		Utils.log("All waves size:"  + waves.size());
		if(waves.size() > 0){
			Wave w = waves.remove(0);
			
			ArrayList<EnemySprite> enemies = w.getEnemies();
			for (EnemySprite e : enemies) {
				e.setMapLayer(mapLayer);
			}
			newWave();
			return enemies;
		}
		return null;
	}
}

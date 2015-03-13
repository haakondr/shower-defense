package no.ntnu.progark.towerdefense.controller;

import java.util.ArrayList;

import no.ntnu.progark.towerdefense.view.EnemySprite;
import no.ntnu.progark.towerdefense.view.SpriteFactory;

public class Wave {
	private int count;
	private ArrayList<EnemySprite> enemiesInWave;
	
	public Wave(String type, int count){
		this.count = count;
		enemiesInWave = SpriteFactory.createEnemySprite(type, count);
	}
	
	public int getCount(){
		return count;
	}
	
	public ArrayList<EnemySprite> getEnemies(){
		return enemiesInWave;
	}
	
	
}

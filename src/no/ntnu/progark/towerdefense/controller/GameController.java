package no.ntnu.progark.towerdefense.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import no.ntnu.progark.towerdefense.main.Utils;
import no.ntnu.progark.towerdefense.model.game.TowerDefenseMapLayer;
import no.ntnu.progark.towerdefense.view.EnemySprite;
import no.ntnu.progark.towerdefense.view.TowerSprite;

public class GameController {
	
	private WaveController waveController;
	private TowerDefenseMapLayer mapLayer;
	private long time = 0;
	private int mobNumber = 0;
	
	private boolean gameOver;
	
	private static final long DELAY_BETWEEN_MOBS = 2000;
	
	private List<EnemySprite> currentWave;
	private List<TowerSprite> towers;
	
	public GameController(TowerDefenseMapLayer mapLayer){
		this.mapLayer = mapLayer;
		waveController = new WaveController(mapLayer);
		currentWave = Collections.synchronizedList(waveController.getNextWaveEnemies());		
		towers = Collections.synchronizedList(new ArrayList<TowerSprite>()); 
		addNewMobToWave();
	}

	private void addNewMobToWave() {
		synchronized (currentWave) {
			if(mobNumber < currentWave.size()) {
				Utils.log("Mob number: " + mobNumber);
				EnemySprite enemy = currentWave.get(mobNumber);
				enemy.setPosition(mapLayer.getSpawnArea().centerX(), mapLayer.getSpawnArea().centerY());
				time = System.currentTimeMillis();
				enemy.setVisible(true);
				++mobNumber;	
			}
		}
	}
	
	public void update(float dt) {
		/*
		 * If the wave is complete spawn a new wave
		 */
		if (isWaveComplete()) {
			Utils.log("Wave is dead, spawning next wave");
			
			currentWave.clear();
			mobNumber = 0;
			ArrayList<EnemySprite> s = waveController.getNextWaveEnemies();
			if (s == null) {
				gameOver = true;
			} else {
				currentWave = Collections.synchronizedList(s);
			}
		} 
		if (!gameOver) {
			synchronized (towers) {
				for (TowerSprite tower : towers) {
					tower.update(dt);
				}
			}
			
			synchronized (currentWave) {
				for (EnemySprite enemy : currentWave) {
					enemy.update(dt);
					if(enemy.isInGoalArea() && enemy.isVisible()){
						enemy.setVisible(false);
					}
				}
			}
			
			if (shouldSpawnNewMob()){
				addNewMobToWave();
			}
			towersShootAtEnemies();
		} else {
			//TODO: Chill
			System.exit(1);
		}
	}
	
	
	public boolean isGameOver(){
		return gameOver;
	}

	private boolean isWaveComplete() {
		synchronized (currentWave) {
			for (EnemySprite enemy : currentWave) {
				if(!enemy.isDead() && !enemy.hasReachedGoal())
					return false;
			}
		}
//		Utils.log(deadCounter + " of " + currentWave.size() + " is dead");
		
		for (EnemySprite enemy : currentWave) {
			if(!enemy.isDead() && !enemy.hasReachedGoal())
				return false;
		}
		return true;
	}

	public WaveController getWaveController(){
		return waveController;
	}
	
	public void draw (Canvas canvas) {
		if(!gameOver){
			synchronized (towers) {
				for (TowerSprite t : towers) {
					t.draw(canvas);
				}
			}
			synchronized (currentWave) {
				for (EnemySprite e : currentWave) {
					e.draw(canvas);
				}
			}
		}
	}

	private boolean shouldSpawnNewMob() {
		return ((time + DELAY_BETWEEN_MOBS) < System.currentTimeMillis());
	}
	
	public void towersShootAtEnemies() {
		synchronized(towers) {
			for (TowerSprite tower : towers) {
				if(tower.isFiring()) {
					if(tower.getTarget().isDead()){
						tower.removeTarget();
						tower.setFiring(false);	
					}
					else if(!tower.getTarget().isVisible()){
						tower.removeTarget();
						tower.setFiring(false);	
					}
					else if(!(targetInRange(tower, tower.getTarget()))){
						tower.removeTarget();
						tower.setFiring(false);
					}
					else {
						//Enemy is alive, visible and within range. FIRE!
						fireAt(tower, tower.getTarget());
					}
				}
				//If there is no target, find a new one.
				else {
					synchronized (currentWave) {
						for (EnemySprite enemy : currentWave){
							if(targetInRange(tower, enemy) && !enemy.isDead() && enemy.isVisible()){
								fireAt(tower, enemy);
								break;
							}
						}
					}
				}
			}
		}

	}
	
	public boolean targetInRange(TowerSprite tower, EnemySprite enemy){
		return (Math.pow(tower.getX() - enemy.getXPosition(), 2) + Math.pow(tower.getY() - enemy.getYPosition(), 2)) <= Math.pow(tower.getModel().getRange(), 2);
	}
	
	public void fireAt(TowerSprite tower, EnemySprite enemy){
		tower.setTarget(enemy);
		enemy.reduceHitPoints(tower.getModel().getDamage());
		tower.setFiring(true);
	}

	public void addTower(TowerSprite tower) {
		towers.add(tower);
	}
}

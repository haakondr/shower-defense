package no.ntnu.progark.towerdefense.model.game;

import no.ntnu.progark.towerdefense.R;
import no.ntnu.progark.towerdefense.main.Utils;
import no.ntnu.progark.towerdefense.tiled.core.Map;
import no.ntnu.progark.towerdefense.tiled.core.XMLMapTransformer;
import no.ntnu.progark.towerdefense.tiled.view.OrthoMapView;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import sheep.game.Layer;
import sheep.math.BoundingBox;

public class TowerDefenseLayer extends Layer {
	
	private Map map;
	private OrthoMapView mapView;
	private XMLMapTransformer mapParser;
	private Context context;
	
	private static int ENEMY_SPAWN_DELAY = 3000;
	private long currentTime;
	private static int ENEMY_COUNT = 10;
	
	public TowerDefenseLayer(Context context) {
		
		currentTime =  System.currentTimeMillis();
		
		this.context = context;
		mapParser = new XMLMapTransformer();
		try {
			map = mapParser.readMap(context.getResources(), R.raw.map);
			mapView = new OrthoMapView(map);
			Log.v("Number of tilesets: ", "" + map.getTilesets().size());
			Log.v("Tiles in first: ", ""
					+ map.getTilesets().get(0).getTotalImages());
		} catch (Exception e) {
			Log.d("Exception!!1", e.getMessage());
		}
		initializeSprites();
	}

	private void initializeSprites(){
		Utils.log("map.getWidth:" + map.getWidth());
//		EnemySprite firstEnemy = (EnemySprite)enemySprites.remove(0);
//		activeSprites.add(firstEnemy);
//		TowerSprite firstTower = (TowerSprite)towerSprites.remove(0);
//		activeTowerSprites.add(firstTower);
	}
	
	
	private boolean shouldSpawnNewEnemy() {
		return ((currentTime + ENEMY_SPAWN_DELAY)< System.currentTimeMillis());
	}

	@Override
	public void draw(Canvas canvas, BoundingBox box) {
//		canvas.clipRect(box.getPoints()[0], box.getPoints()[1], box.getPoints()[3], box.getPoints()[4]);
		
		mapView.paintComponent(canvas);
//		for (EnemySprite enemy : activeSprites) {
//			enemy.draw(canvas);
//		}
//		for (TowerSprite tower : activeTowerSprites){
//			tower.draw(canvas);
//		}
	}

	@Override
	public void update(float dt) {
		
	}

	
}

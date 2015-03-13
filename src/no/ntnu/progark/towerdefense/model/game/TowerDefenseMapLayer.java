package no.ntnu.progark.towerdefense.model.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import no.ntnu.progark.towerdefense.R;
import no.ntnu.progark.towerdefense.main.Utils;
import no.ntnu.progark.towerdefense.tiled.core.Map;
import no.ntnu.progark.towerdefense.tiled.core.MapObject;
import no.ntnu.progark.towerdefense.tiled.core.ObjectGroup;
import no.ntnu.progark.towerdefense.tiled.core.XMLMapTransformer;
import no.ntnu.progark.towerdefense.tiled.view.OrthoMapView;
import sheep.game.Layer;
import sheep.math.BoundingBox;
import sheep.math.Vector2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class TowerDefenseMapLayer extends Layer {
	private Map map;
	private OrthoMapView mapView;
	private XMLMapTransformer mapParser;
	private Context context;
	private Rect goalArea, spawnArea, walkableArea;
	private Rect[] waypoints;
	
	/*
	 * Class variables for logical map.
	 */
	private boolean[][] walkableMap;
	private LinkedList<int[]> wayPointsList;
	private EnemyPath enemyPath;
	
	/*
	 * A* class variables
	 */
	private PriorityQueue<TiledNode> openset;
	private HashSet<TiledNode> closedset;
	
	public TowerDefenseMapLayer(Context context) {
		this.context = context;
		mapParser = new XMLMapTransformer();
		try {
			map = mapParser.readMap(context.getResources(), R.raw.map2);
			mapView = new OrthoMapView(map);
			
			//Logical map
			walkableMap = new boolean[map.getWidth()][map.getHeight()];
			wayPointsList = new LinkedList<int[]>();
			enemyPath = null;
			
			//The openset keeps the best f = h + g value at the top of the min-heap.
			openset = new PriorityQueue<TiledNode>(4, new Comparator<TiledNode>() {
				@Override
				public int compare(TiledNode node1, TiledNode node2) {
					if (node1.f > node2.f) {
						return 1;
					} else if (node1.f < node2.f) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			
			//The closedset keeps the processed nodes
			closedset = new HashSet<TiledNode>();
			/*
			 * Set attributes based on the various map objects.
			 */
			ObjectGroup og = (ObjectGroup) map.getLayers().get(context.getString(R.string.object_layer_name));
			MapObject spawnAreaObject = og.getObjectByName(context.getString(R.string.spawn_area));
			MapObject goalAreaObject = og.getObjectByName(context.getString(R.string.goal_area));
			
			spawnArea =  new Rect(spawnAreaObject.getX(), spawnAreaObject.getY(), 
					(spawnAreaObject.getX() + spawnAreaObject.getWidth()),
					(spawnAreaObject.getY() + spawnAreaObject.getHeight()));
			
			goalArea =  new Rect(goalAreaObject.getX(), goalAreaObject.getY(), 
					(goalAreaObject.getX() + goalAreaObject.getWidth()),
					(goalAreaObject.getY() + goalAreaObject.getHeight()));
			
			/*
			 * Extract waypoints and walkable areas from object layer.
			 */
			List<MapObject> waypointMapObjects = new LinkedList<MapObject>();
			List<MapObject> walkableMapObjects = new LinkedList<MapObject>();
			for (MapObject m : og.getMapObjects().values()) {
				if (m.getType().equals(context.getString(R.string.mapobject_type_waypoint))) {
					waypointMapObjects.add(m);
				} else if (m.getType().equals(context.getString(R.string.mapobject_type_area))) {
					if (m.getName().startsWith("walkable")) {
						walkableMapObjects.add(m);
					}
				}
			}
			
			/*
			 * Iterate over all walkable objects, and fill the walkable map from their data.
			 */
			for (int i = 0; i < walkableMapObjects.size(); i++) {
				String name = context.getString(R.string.walkable_area);
				
				MapObject walkableAreaObject = og.getObjectByName(name + (i+1));
				Utils.log("Processed: " + name + (i+1));
				walkableArea = new Rect(walkableAreaObject.getX(), walkableAreaObject.getY(),
						(walkableAreaObject.getX() + walkableAreaObject.getWidth()),
						(walkableAreaObject.getY() + walkableAreaObject.getHeight()));
					
				/* Fill the walkable map with 'true' on walkable tiles */
				for (int x = walkableArea.left / map.getTileWidth(); x < walkableArea.right / map.getTileWidth(); x++) {
					for (int y = walkableArea.top / map.getTileHeight(); y < walkableArea.bottom / map.getTileHeight(); y++) {
						walkableMap[x][y] = true;
					}
				}
			}
			
			/*
			 * Sort waypoints in ascending order then store them as Rect-objects, 
			 * maintaining the sorted order, in the waypoints array.
			 */
			Collections.sort(waypointMapObjects, new Comparator<MapObject>() {
				@Override
				public int compare(MapObject mo1, MapObject mo2) {
					int a = Integer.parseInt(mo1.getName());
					int b = Integer.parseInt(mo2.getName());
					if (a == b) {
						return 0;
					} else {
						return (a > b) ? 1 : -1;
					}
				}
			});
			
			waypoints = new Rect[waypointMapObjects.size()];
			for (int i = 0; i < waypoints.length; i++) {
				MapObject m = waypointMapObjects.get(i);
				waypoints[i] = new Rect(m.getX(), m.getY(), 
				(m.getX() + m.getWidth()),
				(m.getY() + m.getHeight()));
			}
			
			for (Rect r : waypoints) {
				int[] s = getAreaInCoordinates(r, false);
				wayPointsList.addLast(s);
			}
			
			//TODO: Increase robustness (what if there are no waypoints etc?)
			TiledNode start = new TiledNode(getAreaInCoordinates(spawnArea, true));
			TiledNode waypoint_1 = new TiledNode(wayPointsList.getFirst());
			
			/* Start -> First Waypoint */
			TiledNode temp = AStar(start, waypoint_1);
	
			/* For the waypoints after the first */
			for (int w = 1; w < wayPointsList.size(); w++) {
				temp = AStar(temp, new TiledNode(wayPointsList.get(w)));
				Utils.log("W = " + w + " toString(): " + temp.toString());
			}
			
			TiledNode exit = AStar(temp, new TiledNode(getAreaInCoordinates(goalArea, true)));
			
			
			/* Construct path */
			LinkedList<int[]> path = new LinkedList<int[]>();
			if (exit == null) {
				enemyPath = new EnemyPath(path);
			} else {
				while (exit.parent != null) {
					path.addFirst(new int[]{exit.x, exit.y});
					exit = exit.parent;
				}
				path.addFirst(getAreaInCoordinates(spawnArea, true));
				enemyPath = new EnemyPath(path);
			}
			
			Utils.log(enemyPath.toString());
			
		} catch (Exception e) {
			Utils.log("Exception:" + e.getMessage());
		}
	}
	

	/*
	 * A* implementation
	 */
	private TiledNode AStar(TiledNode start, TiledNode goal) {
		start.g = 0;
		start.h = start.getManhattanDistance(goal);
		start.f = start.h;
		
		openset.clear();
		closedset.clear();
		
		openset.add(start);
		
		while (!openset.isEmpty()) {
			TiledNode x = openset.remove();
			if (x.equals(goal)) {
				return x;
			}
			closedset.add(x);
			
			for (TiledNode n : x.getNeighbours()) {
				if (closedset.contains(n)) {
					continue;
				}

				int tentative_g_score = x.g + 1;
				boolean tentative_is_better;
				
				if (!openset.contains(n)) {
					tentative_is_better = true;
				} else if (tentative_g_score < n.g) {
					tentative_is_better = true;
				} else {
					tentative_is_better = false;
				}
				if (tentative_is_better) {
					n.parent = x;
					n.g = tentative_g_score;
					n.h = n.getManhattanDistance(goal);
					n.f = n.g + n.h;
					openset.add(n);
				}
			}
		}
		return null;
	}
	
	public EnemyPath getEnemyPath() {
		return enemyPath;
	}
	
	private boolean isWalkable(int x, int y) {
		if (x < 0 || x >= getNumberOfTilesWidth() || y < 0 || y >= getNumberOfTilesHeight()) {
			return false;
		}
		return walkableMap[x][y];
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Canvas canvas, BoundingBox box) {
		mapView.paintComponent(canvas);
	}
	
	
	public int[] positionToMapCoordinates(Vector2 position) {
		float tolerance = 0.2f;
		float x = position.getX() / map.getTileWidth() - 0.5f;
		float y = position.getY() / map.getTileHeight() - 0.5f;
		
//		Utils.log("[x:" + x + ",y:" + y + "]");
		
//		if (Math.abs(x - Math.round(x)) < tolerance && Math.abs(y - Math.round(y)) < tolerance) {
//			return new int[]{(int)Math.round(x), (int)Math.round(y)};
//		} else {
//			return new int[]{(int)x, (int)};
//		}
		return new int[]{(int)Math.round(x), (int)Math.round(y)};
	}
	
	/**
	 * Returns the height of the map contained in this layer
	 * @return the height
	 */
	public int getMapHeight() {
		return map.getHeight() * map.getTileHeight();
	}
	
	/**
	 * Returns the width of the map contained in this layer
	 * @return the width
	 */
	public int getMapWidth() {
		return map.getWidth() * map.getTileWidth();
	}
	
	public int getNumberOfTilesWidth() {
		return map.getWidth();
	}

	public int getNumberOfTilesHeight() {
		return map.getHeight();
	}
	
	public Rect getGoalArea() {
		return goalArea;
	}
	
	public Rect getSpawnArea() {
		return spawnArea;
	}
	
	public Rect[] getWaypoints() {
		return waypoints;
	}
	
	/**
	 * Takes an area, and returns a coordinate touple (x, y) indicating
	 * its position in the logical map. The result is zero-indexed. The 
	 * "origin", (0, 0), of the map is in the upper-right corner
	 * 
	 * @param area the are in question
	 * @param centered whether to calculate the coordinates based on the
	 * 		  center coordinates of the area (or top-left coordinates).
	 * 
	 * @return the touple (x, y) as a 1-dimensional array.
	 */
	private int[] getAreaInCoordinates(Rect area, boolean centered) {
		if (!centered) {
			return new int[]{area.left / map.getTileWidth(),
					 area.top / map.getTileHeight()};
		} else {
			return new int[]{area.centerX() / map.getTileWidth(),
					 area.centerY() / map.getTileHeight()};
		}
	}
	
	private class TiledNode {
		/*
		 * Ensure that coordinates are immutable since the hashing
		 * relies on them.
		 */
		final int x, y; 
		int g, h, f; //a* values
		TiledNode parent; //for path reconstruction
		
		public TiledNode(int[] unitArea) {
			x = unitArea[0];
			y = unitArea[1];
		}
		
		public TiledNode(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		//Admissible heurestic
		public int getManhattanDistance(TiledNode goal) {
			return Math.abs(this.x - goal.x) + Math.abs(this.y - goal.y);
		}
		
		/*
		 * Gets the 4-directional neighbors
		 */
		public LinkedList<TiledNode> getNeighbours() {
			LinkedList<TiledNode> list = new LinkedList<TiledNode>();

			//bottom
			if ((y + 1) <= (getNumberOfTilesHeight() - 1) && isWalkable(x, y + 1)) {
				list.add(new TiledNode(x, y + 1));
			}
			//left
			if ((x - 1) >= 0 && isWalkable(x - 1, y)) {
				list.add(new TiledNode(x - 1, y));
			}
			//right
			if ((x + 1) <= (getNumberOfTilesWidth() - 1) && isWalkable(x + 1, y)) {
				list.add(new TiledNode(x + 1, y));
			}
			//top
			if ((y - 1) >= 0 && isWalkable(x, y - 1)) {
				list.add(new TiledNode(x, y - 1));
			}

			return list;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) { //check for null-pointer
				return false; 
			} else if (o == this) { //pointer-wise comparison
				return true;
			} else if (!(o instanceof TiledNode)) { //class-wise comparison
				return false;
			} else {
				TiledNode n = (TiledNode) o;
				if (!getOuterType().equals(n.getOuterType())) {
					return false;
				}
				//Define equality on x and y coordinates
				if ((this.x == n.x) && (this.y == n.y)) {
					return true;
				} else {
					return false;
				}
			}
		}

		private TowerDefenseMapLayer getOuterType() {
			return TowerDefenseMapLayer.this;
		}

		@Override
		public String toString() {
			return "TiledNode [x=" + x + ", y=" + y + ", g=" + g + ", h=" + h
					+ ", f=" + f + "]";
		}
	}
}
package no.ntnu.progark.towerdefense.model.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class represents a path in which the enemies will use
 * to navigate through the map. 
 * 
 * @author havard
 */
public class EnemyPath {
	public static final int TURNING_NORTH = 0;
	public static final int TURNING_EAST = 1;
	public static final int TURNING_SOUTH = 2;
	public static final int TURNING_WEST = 3;
	
	private LinkedList<int[]> path;
	private HashMap<LinkedList<Integer>, Integer> turningPoints;
	
	public EnemyPath(LinkedList<int[]> path) {
		this.path = path;
		this.turningPoints = new HashMap<LinkedList<Integer>, Integer>();
		
		int deltaX = 0;
		int deltaY = 0;
		
		
		for (int p = 0; p < path.size() - 1; p++) {
			int newDeltaX = path.get(p + 1)[0] - path.get(p)[0];
			int newDeltaY = path.get(p + 1)[1] - path.get(p)[1];
			
			
			if (newDeltaX != deltaX) {
				if (newDeltaX > 0) { //EAST
					turningPoints.put(new LinkedList<Integer>(Arrays.asList(path.get(p)[0], path.get(p)[1])), TURNING_EAST);
				} else if (newDeltaX < 0) { //WEST
					turningPoints.put(new LinkedList<Integer>(Arrays.asList(path.get(p)[0], path.get(p)[1])), TURNING_WEST);
				}
				deltaX = newDeltaX;
			}
			
			if (newDeltaY != deltaY) {
				if (newDeltaY > 0) { //SOUTH
					turningPoints.put(new LinkedList<Integer>(Arrays.asList(path.get(p)[0], path.get(p)[1])), TURNING_SOUTH);
				} else if (newDeltaY < 0) { //NORTH
					turningPoints.put(new LinkedList<Integer>(Arrays.asList(path.get(p)[0], path.get(p)[1])), TURNING_NORTH);
				}
				deltaY = newDeltaY;
			}
		}
	}
	
	/**
	 * Determines what kind of turning is made at the specified point
	 * 
	 * @param point 
	 * 		The (x,y) point to be tested.
	 * @return
	 * 		If the point was not a turning point, -1 is returned. 
	 * 		Otherwise the type of turning is returned. The types
	 * 		are as following:
	 * 
	 *		EnemyPath.TURNING_NORTH = 0;
	 *		EnemyPath.TURNING_EAST = 1;
	 *		EnemyPath.TURNING_SOUTH = 2;
	 *		EnemyPath.TURNING_WEST = 3;
	 */
	public int isTurningPoint(int[] point) {
		LinkedList<Integer> t = new LinkedList<Integer>(Arrays.asList(point[0], point[1]));
		if (!turningPoints.containsKey(t)) {
			return -1;
		} else {
			return turningPoints.get(t);
		}
	}
	
	/*
	 * Hide default constructor
	 */
	private EnemyPath() { };
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (path == null) {
			sb.append("Path is a null pointer\n");
		} else {
			sb.append("-- PATH --\n");
			for (int[] p : path) {
				sb.append("[x:" + p[0] + ",y:" + p[1] + "]").append("\n");
			}
			sb.append("-- TURNING POINTS --\n");
			for (LinkedList<Integer> t : turningPoints.keySet()) {
				sb.append("[x:" + t.getFirst() + ",y:" + t.getLast() + "]").append("=")
				.append(turningPoints.get(t)).append("\n");
			}
		}
		return sb.toString();
	}
}

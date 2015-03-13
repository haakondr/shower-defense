/*
 *  Tiled Map Editor, (c) 2004-2008
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Adam Turk <aturk@biggeruniverse.com>
 *  Bjorn Lindeijer <bjorn@lindeijer.nl>
 */

package no.ntnu.progark.towerdefense.tiled.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A layer containing {@link MapObject map objects}.
 */
public class ObjectGroup extends MapLayer
{
    private HashMap<String, MapObject> objects;

    /**
     * Default constructor.
     */
    public ObjectGroup() {
    	objects = new HashMap<String, MapObject>();
    }

    /**
     * @param map    the map this object group is part of
     */
    public ObjectGroup(Map map) {
        super(map);
        objects = new HashMap<String, MapObject>();
    }

    /**
     * @see MapLayer#rotate(int)
     */
    @Override
	public void rotate(int angle) {
        // TODO: Implement rotating an object group
    }

    /**
     * @see MapLayer#mirror(int)
     */
    @Override
	public void mirror(int dir) {
        // TODO: Implement mirroring an object group
    }

    @Override
	public void mergeOnto(MapLayer other) {
        // TODO: Implement merging with another object group
    }

    @Override
	public void copyFrom(MapLayer other) {
        // TODO: Implement copying from another object group (same as merging)
    }

    @Override
	public void copyTo(MapLayer other) {
        // TODO: Implement copying to another object group (same as merging)
    }

    /**
     * @see MapLayer#resize(int,int,int,int)
     */
    @Override
	public void resize(int width, int height, int dx, int dy) {
        // TODO: Translate contained objects by the change of origin
    }

    @Override
	public boolean isEmpty() {
        return objects.isEmpty();
    }

    /**
     * @deprecated
     */
    @Deprecated
	@Override
	public MapLayer createDiff(MapLayer ml) {
        return null;
    }

    public void addObject(MapObject mo) {
        objects.put(mo.getName(), mo);
        mo.setObjectGroup(this);
    }

    public void removeObject(MapObject mo) {
        objects.remove(mo.getName());
        mo.setObjectGroup(null);
    }

    public Iterator<MapObject> getObjects() {
        return objects.values().iterator();
    }

    public MapObject getObjectByName(String name) {
    	return objects.get(name);
    }
    
    public HashMap<String, MapObject> getMapObjects() {
    	return objects;
    }
    
    public MapObject getObjectAt(int x, int y) {
        for (MapObject obj : objects.values()) {
            // Attempt to get an object bordering the point that has no width
            if (obj.getWidth() == 0 && obj.getX() + bounds.x == x) {
                return obj;
            }

            // Attempt to get an object bordering the point that has no height
            if (obj.getHeight() == 0 && obj.getY() + bounds.y == y) {
                return obj;
            }

            Rectangle rect = new Rectangle(obj.getX() + bounds.x * myMap.getTileWidth(),
                    obj.getY() + bounds.y * myMap.getTileHeight(),
                    obj.getWidth(), obj.getHeight());
            if (rect.contains(x, y)) {
                return obj;
            }
        }
        return null;
    }
}

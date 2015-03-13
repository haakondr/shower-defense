/*
 *  Tiled Map Editor, (c) 2004-2006
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

/**
 * MultilayerPlane makes up the core functionality of both Maps and Brushes.
 * This class handles the order of layers as a group.
 */
public class MultilayerPlane implements Iterable<MapLayer>
{
    private HashMap<String, MapLayer> layersImba;
    protected Rectangle bounds;          //in tiles

    /**
     * Default constructor.
     */
    public MultilayerPlane() {
        layersImba = new HashMap<String, MapLayer>();
        bounds = new Rectangle();
    }
    
    public HashMap<String, MapLayer> getLayers() {
    	return layersImba;
    }

    /**
     * Construct a MultilayerPlane to the specified dimensions.
     *
     * @param width
     * @param height
     */
    public MultilayerPlane(int width, int height) {
        this();
        bounds.width = width;
        bounds.height = height;
    }

    /**
     * Returns the total number of layers.
     *
     * @return the size of the layer vector
     */
    public int getTotalLayers() {
        return layersImba.size();
    }

    /**
     * Changes the bounds of this plane to include all layers completely.
     */
    public void fitBoundsToLayers() {
        int width = 0;
        int height = 0;

        Rectangle layerBounds = new Rectangle();

        for (MapLayer layer : layersImba.values()) {
            layer.getBounds(layerBounds);
            if (width < layerBounds.width) width = layerBounds.width;
            if (height < layerBounds.height) height = layerBounds.height;
        }

        bounds.width = width;
        bounds.height = height;
    }

    /**
     * Returns a <code>Rectangle</code> representing the maximum bounds in
     * tiles.
     * @return a new rectangle containing the maximum bounds of this plane
     */
    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }

    /**
     * Adds a layer to the map.
     *
     * @param layer The {@link MapLayer} to add
     * @return the layer passed to the function
     */
    public MapLayer addLayer(MapLayer layer) {
    	layersImba.put(layer.getName(), layer);
        return layer;
    }

    /**
     * Removes all layers from the plane.
     */
    public void removeAllLayers() {
        layersImba.clear();
    }

    /**
     * Determines wether the point (x,y) falls within the plane.
     *
     * @param x
     * @param y
     * @return <code>true</code> if the point is within the plane,
     *         <code>false</code> otherwise
     */
    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < bounds.width && y < bounds.height;
    }

    @Override
	public Iterator<MapLayer> iterator() {
        return layersImba.values().iterator();
    }
}

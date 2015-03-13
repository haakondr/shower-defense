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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Vector;

import android.util.Log;

/**
 * The Map class is the focal point of the <code>tiled.core</code> package.
 * This class also handles notifing listeners if there is a change to any layer
 * or object contained by the map.
 *
 * @version $Id$
 */
public class Map extends MultilayerPlane
{
    /** Orthogonal. */
    public static final int MDO_ORTHO   = 1;
    /** Isometric. */
    public static final int MDO_ISO     = 2;
    /** Hexagonal. */
    public static final int MDO_HEX     = 4;
    /** Shifted (used for iso and hex). */
    public static final int MDO_SHIFTED = 5;

    private Vector<MapLayer> specialLayers;
    private Vector<TileSet> tilesets;

    private int tileWidth, tileHeight;
    private int orientation = MDO_ORTHO;
    private Properties properties;
    private String filename;

    /**
     * @param width  the map width in tiles.
     * @param height the map height in tiles.
     */
    public Map(int width, int height) {
        super(width, height);

        properties = new Properties();
        tilesets = new Vector<TileSet>();
        specialLayers = new Vector<MapLayer>();
    }


    public void addLayerSpecial(MapLayer layer) {
        layer.setMap(this);
        specialLayers.add(layer);
    }

    @Override
	public MapLayer addLayer(MapLayer layer) {
        layer.setMap(this);
        super.addLayer(layer);
        return layer;
    }

    /**
     * Create a new empty TileLayer with the dimensions of the map. By default,
     * the new layer's name is set to "Layer [layer index]"
     *
     * @return The new TileLayer instance.
     */
    public MapLayer addLayer() {
        MapLayer layer = new TileLayer(this, bounds.width, bounds.height);
//        layer.setName(Resources.getString("general.layer.layer") +
//                      " " + super.getTotalLayers());
        super.addLayer(layer);
        return layer;
    }

    /**
     * Create a new empty ObjectGroup. By default, the new layer's name is set
     * to "ObjectGroup [layer index]"
     *
     * @return The new ObjectGroup instance.
     */
    public MapLayer addObjectGroup() {
        MapLayer layer = new ObjectGroup(this);
//        layer.setName(Resources.getString("general.objectgroup.objectgroup") +
//                      " " + super.getTotalLayers());
        super.addLayer(layer);
        return layer;
    }

    /**
     * Adds a Tileset to this Map. If the set is already attached to this map,
     * <code>addTileset</code> simply returns.
     *
     * @param tileset a tileset to add
     */
    public void addTileset(TileSet tileset) {
        if (tileset == null || tilesets.indexOf(tileset) > -1) {
            return;
        }

        Tile t = tileset.getTile(0);

        if (t != null) {
            int tw = t.getWidth();
            int th = t.getHeight();
            if (tw != tileWidth) {
                if (tileWidth == 0) {
                    tileWidth = tw;
                    tileHeight = th;
                }
            }
        }

        tilesets.add(tileset);
    }

    /**
     * @return the map properties
     */
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties prop) {
        properties = prop;
    }


    public void removeLayerSpecial(MapLayer layer) {
        if (specialLayers.remove(layer)) {
        }
    }

    public void removeAllSpecialLayers() {
        specialLayers.clear();
    }

    /**
     * Calls super method, and additionally fires a {@link MapChangedEvent}.
     *
     * @see MultilayerPlane#removeAllLayers
     */
    @Override
	public void removeAllLayers() {
        super.removeAllLayers();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Sets a new tile width.
     *
     * @param width the new tile width
     */
    public void setTileWidth(int width) {
        tileWidth = width;
    }

    /**
     * Sets a new tile height.
     *
     * @param height the new tile height
     */
    public void setTileHeight(int height) {
        tileHeight = height;
    }


    public void setOrientation(int orientation) {
        this.orientation = orientation;
        // TODO: fire mapChangedNotification about orientation change
    }

    public String getFilename() {
        return filename;
    }

    public Iterator<MapLayer> getLayersSpecial() {
        return specialLayers.iterator();
    }

    /**
     * Returns a vector with the currently loaded tilesets.
     *
     * @return Vector
     */
    public Vector<TileSet> getTilesets() {
        return tilesets;
    }
    
    @Override
    public HashMap<String, MapLayer> getLayers() {
    	return super.getLayers();
    }

    /**
     * Get the tile set that matches the given global tile id, only to be used
     * when loading a map.
     *
     * @param gid a global tile id
     * @return the tileset containing the tile with the given global tile id,
     *         or <code>null</code> when no such tileset exists
     */
    public TileSet findTileSetForTileGID(int gid) {
        TileSet has = null;
        for (TileSet tileset : tilesets) {
            if (tileset.getFirstGid() <= gid) {
                has = tileset;
            }
        }
        return has;
    }

    /**
     * Returns width of map in tiles.
     *
     * @return int
     */
    public int getWidth() {
        return bounds.width;
    }

    /**
     * Returns height of map in tiles.
     *
     * @return int
     */
    public int getHeight() {
        return bounds.height;
    }

    /**
     * Returns default tile width for this map.
     *
     * @return the default tile width
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * Returns default tile height for this map.
     *
     * @return the default tile height
     */
    public int getTileHeight() {
        return tileHeight;
    }

    /**
     * Returns wether the given tile coordinates fall within the map
     * boundaries.
     *
     * @param x The tile-space x-coordinate
     * @param y The tile-space y-coordinate
     * @return <code>true</code> if the point is within the map boundaries,
     *         <code>false</code> otherwise
     */
    public boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && x < bounds.width && y < bounds.height;
    }

    /**
     * Returns the maximum tile height. This is the height of the highest tile
     * in all tilesets or the tile height used by this map if it's smaller.
     *
     * @return int The maximum tile height
     */
    public int getTileHeightMax() {
        int maxHeight = tileHeight;

        for (TileSet tileset : tilesets) {
            int height = tileset.getTileHeight();
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        return maxHeight;
    }

    /**
     * Swaps the tile sets at the given indices.
     */
    public void swapTileSets(int index0, int index1) {
        if (index0 == index1) return;
        TileSet set = tilesets.get(index0);
        tilesets.set(index0, tilesets.get(index1));
        tilesets.set(index1, set);

        if (index0 > index1) {
            int temp = index1;
            index1 = index0;
            index0 = temp;
        }
    }

    /**
     * Returns the sum of the size of each tile set.
     *
     * @return
     */
    /*
    public int getTotalTiles() {
        int totalTiles = 0;
        Iterator itr = tilesets.iterator();

        while (itr.hasNext()) {
            TileSet cur = (TileSet)itr.next();
            totalTiles += cur.getTotalTiles();
        }

        return totalTiles;
    }
    */

    /**
     * Returns the orientation of this map. Orientation will be one of
     * {@link Map#MDO_ISO}, {@link Map#MDO_ORTHO}, {@link Map#MDO_HEX},
     * and {@link Map#MDO_SHIFTED}.
     *
     * @return The orientation from the enumerated set
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Returns string describing the map. The form is <code>Map[width x height
     * x layers][tileWidth x tileHeight]</code>, for example <code>
     * Map[64x64x2][24x24]</code>.
     *
     * @return string describing map
     */
    @Override
	public String toString() {
        return "Map[" + bounds.width + "x" + bounds.height + "x" +
            getTotalLayers() + "][" + tileWidth + "x" +
            tileHeight + "]";
    }
}

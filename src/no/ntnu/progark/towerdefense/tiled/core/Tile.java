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

import java.util.Properties;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * The core class for our tiles.
 *
 * @version $Id$
 */
public class Tile
{
    private Bitmap internalImage;
    private int id = -1;
    protected int tileImageId = -1;
    private int groundHeight;          // Height above/below "ground"
    private Properties properties;
    private TileSet tileset;

    public Tile() {
        properties = new Properties();
    }

    public Tile(TileSet set) {
        this();
        setTileSet(set);
    }

    /**
     * Copy constructor
     *
     * @param t
     */
    public Tile(Tile t) {
        properties = (Properties)t.properties.clone();
        tileImageId = t.tileImageId;
        tileset = t.tileset;
    }

    /**
     * Sets the id of the tile as long as it is at least 0.
     *
     * @param i The id of the tile
     */
    public void setId(int i) {
        if (i >= 0) {
            id = i;
        }
    }

    /**
     * Changes the image of the tile as long as it is not null.
     *
     * @param i the new image of the tile
     */
    public void setImage(Bitmap i) {
        if (tileset != null) {
            tileset.overlayImage(tileImageId, i);
        } else {
            internalImage = i;
        }
    }

    public void setImage(int id) {
        tileImageId = id;
    }



    /**
     * Sets the parent tileset for a tile. If the tile is already
     * a member of a set, and this method is called with a different
     * set as argument, the tile image is transferred to the new set.
     *
     * @param set
     */
    public void setTileSet(TileSet set) {
        if (tileset != null && tileset != set) {
            setImage(set.addImage(getImage()));
        } else {
            if (internalImage != null) {
                setImage(set.addImage(internalImage));
                internalImage = null;
            }
        }
        tileset = set;
    }

    public void setProperties(Properties p) {
        properties = p;
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Returns the tile id of this tile, relative to tileset.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the global tile id by adding the tile id to the map-assigned.
     *
     * @return id
     */
    public int getGid() {
        if (tileset != null) {
            return id + tileset.getFirstGid();
        }
        return id;
    }

    /**
     * Returns the {@link tiled.core.TileSet} that this tile is part of.
     *
     * @return TileSet
     */
    public TileSet getTileSet() {
        return tileset;
    }

    /**
     * This drawing function handles drawing the tile image at the
     * specified zoom level. It will attempt to use a cached copy,
     * but will rescale if the requested zoom does not equal the
     * current cache zoom.
     *
     * @param g Graphics instance to draw to
     * @param x x-coord to draw tile at
     * @param y y-coord to draw tile at
     * @param zoom Zoom level to draw the tile
     */
    public void drawRaw(Canvas c, int x, int y) {
        Bitmap img = getImage();
        if (img != null) {
        	c.drawBitmap(img, x, y - img.getHeight(), new Paint());
        } else {
            // TODO: Allow drawing IDs when no image data exists as a
            // config option
        }
    }

    /**
     * Draws the tile at the given pixel coordinates in the given
     * graphics context, and at the given zoom level
     *
     * @param g
     * @param x
     * @param y
     * @param zoom
     */
    public void draw(Canvas c, int x, int y) {
        // Invoke raw draw function
        int gnd_h = (int)(groundHeight);
        drawRaw(c, x, y - gnd_h);
    }

    public int getWidth() {
        if (tileset != null) {
            Dimension d = tileset.getImageDimensions(tileImageId);
            return d.width;
        } else if (internalImage != null){
            return internalImage.getWidth();
        }
        return 0;
    }

    public int getHeight() {
        if (tileset != null) {
            Dimension d = tileset.getImageDimensions(tileImageId);
            return d.height;
        } else if (internalImage != null) {
            return internalImage.getHeight();
        }
        return 0;
    }

    public int getImageId() {
        return tileImageId;
    }

    /**
     * Returns the tile image for this Tile.
     *
     * @return Image
     */
    public Bitmap getImage() {
        if (tileset != null) {
            return tileset.getImageById(tileImageId);
        } else {
            return internalImage;
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
        return "Tile " + id + " (" + getWidth() + "x" + getHeight() + ")";
    }
}

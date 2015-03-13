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

import no.ntnu.progark.towerdefense.tiled.core.Dimension;
import android.graphics.Bitmap;

/**
 * Cuts tiles from a tileset image according to a regular rectangular pattern.
 * Supports a variable spacing between tiles and a margin around them.
 */
public class BasicTileCutter implements TileCutter
{
    private int nextX, nextY;
    private Bitmap image;
    private final int tileWidth;
    private final int tileHeight;
    private final int tileSpacing;
    private final int tileMargin;

    public BasicTileCutter(int tileWidth, int tileHeight, int tileSpacing,
                           int tileMargin)
    {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tileSpacing = tileSpacing;
        this.tileMargin = tileMargin;

        reset();
    }

    @Override
	public String getName() {
        return "Basic";
    }

    @Override
	public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
	public Bitmap getNextTile() {
        if (nextY + tileHeight + tileMargin <= image.getHeight()) {
            Bitmap tile = Bitmap.createBitmap(image, nextX, nextY, tileWidth, tileHeight, null, false);
            nextX += tileWidth + tileSpacing;

            if (nextX + tileWidth + tileMargin > image.getWidth()) {
                nextX = tileMargin;
                nextY += tileHeight + tileSpacing;
            }

            return tile;
        }

        return null;
    }

    @Override
	public void reset() {
        nextX = tileMargin;
        nextY = tileMargin;
    }

    @Override
	public Dimension getTileDimensions() {
        return new Dimension(tileWidth, tileHeight);
    }

    /**
     * Returns the spacing between tile images.
     * @return the spacing between tile images.
     */
    public int getTileSpacing() {
        return tileSpacing;
    }

    /**
     * Returns the margin around the tile images.
     * @return the margin around the tile images.
     */
    public int getTileMargin() {
        return tileMargin;
    }

    /**
     * Returns the number of tiles per row in the tileset image.
     * @return the number of tiles per row in the tileset image.
     */
    public int getTilesPerRow() {
        return (image.getWidth() - 2 * tileMargin + tileSpacing) /
                (tileWidth + tileSpacing);
    }
}

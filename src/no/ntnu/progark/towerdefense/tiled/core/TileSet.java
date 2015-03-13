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

import no.ntnu.progark.towerdefense.tiled.util.NumberedSet;
import no.ntnu.progark.towerdefense.tiled.core.BasicTileCutter;
import no.ntnu.progark.towerdefense.tiled.core.TileCutter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * todo: Update documentation
 * <p>TileSet handles operations on tiles as a set, or group. It has several
 * advanced internal functions aimed at reducing unnecessary data replication.
 * A 'tile' is represented internally as two distinct pieces of data. The
 * first and most important is a {@link Tile} object, and these are held in
 * a {@link Vector}.</p>
 *
 * <p>The other is the tile image.</p>
 */
public class TileSet
{
	public static final String PACKAGE_NAME = "no.ntnu.progark.towerdefense";
    private String base;
    private NumberedSet<Tile> tiles;
    private NumberedSet<Bitmap> images;
    private int firstGid;
    private TileCutter tileCutter;
    private Rectangle tileDimensions;
    private int tileSpacing;
    private int tileMargin;
    private int tilesPerRow;
    private String externalSource;
    private String name;
    private int transparentColor;
    private Properties defaultTileProperties;
    private Bitmap tileSetImage;

    /**
     * Default constructor
     */
    public TileSet() {
        tiles = new NumberedSet<Tile>();
        images = new NumberedSet<Bitmap>();
        tileDimensions = new Rectangle();
        defaultTileProperties = new Properties();
        transparentColor = 1;
    }

    /**
     * Creates a tileset from a tileset image file.
     *
     * @param imgFilename
     * @param cutter
     * @throws IOException
     * @see TileSet#importTileBitmap(BufferedImage, TileCutter)
     */
    @SuppressWarnings("unused")
	public void importTileBitmap(Resources res, String resourceName, TileCutter cutter)
            throws IOException
    {
    	
    	
        Bitmap image = BitmapFactory.decodeResource(res, res.getIdentifier(resourceName, "drawable", PACKAGE_NAME));
        Log.v("TileSet.importTileBitmap, info about image: ", "height:" + image.getHeight()
        		+ ", width:" + image.getHeight());
        
        if (image == null) {
            throw new IOException("Failed to load " + resourceName);
        }

//        Toolkit tk = Toolkit.getDefaultToolkit();
//
        if (transparentColor < 0) {
        	throw new IOException("Not supporting transparent colors " + resourceName);
//            int rgb = transparentColor.getRGB();
//            image = tk.createImage(
//                    new FilteredImageSource(image.getSource(),
//                            new TransparentImageFilter(rgb)));
        }

        Bitmap buffered = image.copy(Bitmap.Config.ARGB_8888, true);
        Log.v("TileSet.importTileBitmap, info about buffered: ", "height:" + buffered.getHeight()
        		+ ", width:" + buffered.getHeight());
        importTileBitmap(buffered, cutter);
    }

    /**
     * Creates a tileset from a buffered image. Tiles are cut by the passed
     * cutter.
     *
     * @param tilebmp     the image to be used, must not be null
     * @param cutter      the tile cutter, must not be null
     */
    private void importTileBitmap(Bitmap tileBitmap, TileCutter cutter)
    {
        assert tileBitmap != null;
        assert cutter != null;

        tileCutter = cutter;
        tileSetImage = tileBitmap;

        cutter.setImage(tileBitmap);

        
        tileDimensions = new Rectangle(cutter.getTileDimensions());
        Log.v("TileSet.importTileBitmap (cutting): tileDimensions", tileDimensions.toString());
        if (cutter instanceof BasicTileCutter) {
            BasicTileCutter basicTileCutter = (BasicTileCutter) cutter;
            tileSpacing = basicTileCutter.getTileSpacing();
            tileMargin = basicTileCutter.getTileMargin();
            tilesPerRow = basicTileCutter.getTilesPerRow();
            
            Log.v("Tilecutter info: ", "spacing:" + tileSpacing + ", " + "margin:" + tileMargin
            		+ "," + "tilesPerRow:" + tilesPerRow);
        }

        Bitmap tile = cutter.getNextTile();
        while (tile != null) {
            Tile newTile = new Tile();
            newTile.setImage(addImage(tile));
            addNewTile(newTile);
            tile = cutter.getNextTile();
        }
    }

    /**
     * Sets the URI path of the external source of this tile set. By setting
     * this, the set is implied to be external in all other operations.
     *
     * @param source a URI of the tileset image file
     */
    public void setSource(String source) {
        externalSource = source;
    }

    /**
     * Sets the base directory for the tileset
     *
     * @param base a String containing the native format directory
     */
    public void setBaseDir(String base) {
        this.base = base;
    }
    /**
     * Sets the first global id used by this tileset.
     *
     * @param firstGid first global id
     */
    public void setFirstGid(int firstGid) {
        this.firstGid = firstGid;
    }

    /**
     * Sets the name of this tileset.
     *
     * @param name the new name for this tileset
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the transparent color in the tileset image.
     *
     * @param color
     */
    public void setTransparentColor(int color) {
        transparentColor = color;
    }

    /**
     * Adds the tile to the set, setting the id of the tile only if the current
     * value of id is -1.
     *
     * @param t the tile to add
     * @return int The <b>local</b> id of the tile
     */
    public int addTile(Tile t) {
        if (t.getId() < 0) {
            t.setId(tiles.getMaxId() + 1);
        }

        if (tileDimensions.width < t.getWidth()) {
            tileDimensions.width = t.getWidth();
        }

        if (tileDimensions.height < t.getHeight()) {
            tileDimensions.height = t.getHeight();
        }

        // Add any default properties
        // TODO: use parent properties instead?
        t.getProperties().putAll(defaultTileProperties);

        tiles.put(t.getId(), t);
        t.setTileSet(this);

        return t.getId();
    }

    /**
     * This method takes a new Tile object as argument, and in addition to
     * the functionality of <code>addTile()</code>, sets the id of the tile
     * to -1.
     *
     * @see TileSet#addTile(Tile)
     * @param t the new tile to add.
     */
    public void addNewTile(Tile t) {
        t.setId(-1);
        addTile(t);
    }

    /**
     * Removes a tile from this tileset. Does not invalidate other tile
     * indices. Removal is simply setting the reference at the specified
     * index to <b>null</b>.
     *
     * todo: Fix the behaviour of this function? It actually does seem to
     * todo: invalidate other tile indices due to implementation of
     * todo: NumberedSet.
     *
     * @param i the index to remove
     */
    public void removeTile(int i) {
        tiles.remove(i);
    }

    /**
     * Returns the amount of tiles in this tileset.
     *
     * @return the amount of tiles in this tileset
     */
    public int size() {
        return tiles.size();
    }

    /**
     * Returns the maximum tile id.
     *
     * @return the maximum tile id, or -1 when there are no tiles
     */
    public int getMaxTileId() {
        return tiles.getMaxId();
    }

    /**
     * Returns an iterator over the tiles in this tileset.
     *
     * @return an iterator over the tiles in this tileset.
     */
    public Iterator<Tile> iterator() {
        return tiles.iterator();
    }

    /**
     * Generates a vector that removes the gaps that can occur if a tile is
     * removed from the middle of a set of tiles. (Maps tiles contiguously)
     *
     * @return a {@link Vector} mapping ordered set location to the next
     *         non-null tile
     */
    public Vector<Tile> generateGaplessVector() {
        Vector<Tile> gapless = new Vector<Tile>();

        for (int i = 0; i <= getMaxTileId(); i++) {
            if (getTile(i) != null) gapless.add(getTile(i));
        }

        return gapless;
    }

    /**
     * Returns the width of tiles in this tileset. All tiles in a tileset
     * should be the same width, and the same as the tile width of the map the
     * tileset is used with.
     *
     * @return int - The maximum tile width
     */
    public int getTileWidth() {
        return tileDimensions.width;
    }

    /**
     * Returns the tile height of tiles in this tileset. Not all tiles in a
     * tileset are required to have the same height, but the height should be
     * at least the tile height of the map the tileset is used with.
     *
     * If there are tiles with varying heights in this tileset, the returned
     * height will be the maximum.
     *
     * @return the max height of the tiles in the set
     */
    public int getTileHeight() {
        return tileDimensions.height;
    }

    /**
     * Returns the spacing between the tiles on the tileset image.
     * @return the spacing in pixels between the tiles on the tileset image
     */
    public int getTileSpacing() {
        return tileSpacing;
    }

    /**
     * Returns the margin around the tiles on the tileset image.
     * @return the margin in pixels around the tiles on the tileset image
     */
    public int getTileMargin() {
        return tileMargin;
    }

    /**
     * Returns the number of tiles per row in the original tileset image.
     * @return the number of tiles per row in the original tileset image.
     */
    public int getTilesPerRow() {
        return tilesPerRow;
    }

    /**
     * Gets the tile with <b>local</b> id <code>i</code>.
     *
     * @param i local id of tile
     * @return A tile with local id <code>i</code> or <code>null</code> if no
     *         tile exists with that id
     */
    public Tile getTile(int i) {
        try {
            return (Tile) tiles.get(i);
        } catch (ArrayIndexOutOfBoundsException a) {}
        return null;
    }

    /**
     * Returns the first non-null tile in the set.
     *
     * @return The first tile in this tileset, or <code>null</code> if none
     *         exists.
     */
    public Tile getFirstTile() {
        Tile ret = null;
        int i = 0;
        while (ret == null && i <= getMaxTileId()) {
            ret = getTile(i);
            i++;
        }
        return ret;
    }

    /**
     * Returns the source of this tileset.
     *
     * @return a filename if tileset is external or <code>null</code> if
     *         tileset is internal.
     */
    public String getSource() {
        return externalSource;
    }

    /**
     * Returns the base directory for the tileset
     *
     * @return a directory in native format as given in the tileset file or tag
     */
    public String getBaseDir() {
        return base;
    }

    /**
     * Returns the first global id connected to this tileset.
     *
     * @return first global id
     */
    public int getFirstGid() {
        return firstGid;
    }

    /**
     * @return the name of this tileset.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the transparent color of the tileset image, or <code>null</code>
     * if none is set.
     *
     * @return Color - The transparent color of the set
     */
    public int getTransparentColor() {
        return transparentColor;
    }

    /**
     * @return the name of the tileset, and the total tiles
     */
    @Override
	public String toString() {
        return getName() + " [" + size() + "]";
    }


    /**
     * Returns the number of images in the set.
     *
     * @return the number of images in the set
     */
    public int getTotalImages() {
        return images.size();
    }

    /**
     * @return an Enumeration of the image ids
     */
    public Enumeration<String> getImageIds() {
        Vector<String> v = new Vector<String>();
        for (int id = 0; id <= images.getMaxId(); ++id) {
            if (images.containsId(id)) {
                v.add(Integer.toString(id));
            }
        }
        return v.elements();
    }

    // TILE IMAGE CODE

    /**
     * This function uses the CRC32 checksums to find the cached version of the
     * image supplied.
     *
     * @param i an Image object
     * @return returns the id of the given image, or -1 if the image is not in
     *         the set
     */
    public int getIdByImage(Bitmap i) {
        return images.indexOf(i);
    }

    /**
     * @param id
     * @return the image identified by the key, or <code>null</code> when
     *         there is no such image
     */
    public Bitmap getImageById(int id) {
        return (Bitmap) images.get(id);
    }

    /**
     * Overlays the image in the set referred to by the given key.
     *
     * @param id
     * @param image
     */
    public void overlayImage(int id, Bitmap image) {
        images.put(id, image);
    }

    /**
     * Returns the dimensions of an image as specified by the id.
     *
     * @deprecated Unless somebody can explain the purpose of this function in
     *             its documentation, I consider this function deprecated. It
     *             is only used by tiles, but they should in my opinion just
     *             use their "internalImage". - Bjorn
     * @param id the image id
     * @return dimensions of image with referenced by given key
     */
    @Deprecated
	public Dimension getImageDimensions(int id) {
        Bitmap img = (Bitmap) images.get(id);
        if (img != null) {
            return new Dimension(img.getWidth(), img.getHeight());
        } else {
            return new Dimension(0, 0);
        }
    }

    /**
     * Adds the specified image to the image cache. If the image already exists
     * in the cache, returns the id of the existing image. If it does not
     * exist, this function adds the image and returns the new id.
     *
     * @param image the java.awt.Image to add to the image cache
     * @return the id as an <code>int</code> of the image in the cache
     */
    public int addImage(Bitmap image) {
        return images.findOrAdd(image);
    }

    public int addImage(Bitmap image, int id) {
        return images.put(id, image);
    }

    public void removeImage(int id) {
        images.remove(id);
    }

    /**
     * Returns whether the tileset is derived from a tileset image.
     *
     * @return tileSetImage != null
     */
    public boolean isSetFromImage() {
        return tileSetImage != null;
    }

    public void setDefaultProperties(Properties defaultSetProperties) {
        defaultTileProperties = defaultSetProperties;
    }
}

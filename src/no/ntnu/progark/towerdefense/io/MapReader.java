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

package no.ntnu.progark.towerdefense.io;

import java.io.FileFilter;
import java.io.InputStream;

import android.content.res.Resources;

import no.ntnu.progark.towerdefense.tiled.core.Map;
import no.ntnu.progark.towerdefense.tiled.core.TileSet;


/**
 * Used by Tiled to denote a plugin for reading maps. The map file
 * can have any format, as long as the MapReader implementor returns
 * instances of {@link Map} and {@link TileSet}.
 */
public interface MapReader extends FileFilter
{
    /**
     * Loads a map from the resources. 
     *
     * @param resources the resource manager.
     * @param resourceName the name of the map file.
     * @return A {@link tiled.core.Map} instance with the relevant data
     * @throws Exception
     */
    public Map readMap(Resources resources, int resourceId) throws Exception;

    
    /**
     * Overload this to load a map from an already opened stream. Useful
     * for maps which are part of a larger binary dataset
     * 
     * @param in
     * @return A {@link tiled.core.Map} object with the relevant data
     * @throws Exception
     */
    public Map readMap(InputStream in) throws Exception;
    
    /**
     * Overload this to load a tileset from an open stream.
     * 
     * @param in
     * @return A (@link tiled.core.TileSet} instance
     * @throws Exception
     */
    public TileSet readTileset(InputStream in) throws Exception;
}

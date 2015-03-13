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

import java.util.Properties;

/**
 * An object occupying an {@link ObjectGroup}.
 */
public class MapObject implements Cloneable
{
    private Properties properties = new Properties();
    private ObjectGroup objectGroup;
    private Rectangle bounds = new Rectangle();
    private String name = "Object";
    private String type = "";

    public MapObject(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
    }

    @Override
	public Object clone() throws CloneNotSupportedException {
        MapObject clone = (MapObject) super.clone();
        clone.bounds = new Rectangle(bounds);
        clone.properties = (Properties) properties.clone();
        return clone;
    }

    /**
     * @return the object group this object is part of
     */
    public ObjectGroup getObjectGroup() {
        return objectGroup;
    }

    /**
     * Sets the object group this object is part of. Should only be called by
     * the object group.
     *
     * @param objectGroup the object group this object is part of
     */
    public void setObjectGroup(ObjectGroup objectGroup) {
        this.objectGroup = objectGroup;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public int getX() {
        return bounds.x;
    }

    public void setX(int x) {
        bounds.x = x;
    }

    public int getY() {
        return bounds.y;
    }

    public void setY(int y) {
        bounds.y = y;
    }

    public void translate(int dx, int dy) {
        bounds.translate(dx, dy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return bounds.width;
    }

    public void setWidth(int width) {
        bounds.width = width;
    }

    public void setHeight(int height) {
        bounds.height = height;
    }

    public int getHeight() {
        return bounds.height;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties p) {
        properties = p;
    }

    @Override
	public String toString() {
        return type + " (" + getX() + "," + getY() + ")";
    }
}

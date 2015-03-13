///*
// *  Tiled Map Editor, (c) 2004-2006
// *
// *  This program is free software; you can redistribute it and/or modify
// *  it under the terms of the GNU General Public License as published by
// *  the Free Software Foundation; either version 2 of the License, or
// *  (at your option) any later version.
// *
// *  Adam Turk <aturk@biggeruniverse.com>
// *  Bjorn Lindeijer <bjorn@lindeijer.nl>
// */
//
package no.ntnu.progark.towerdefense.tiled.view;
//
//import java.util.Iterator;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import no.ntnu.progark.towerdefense.tiled.core.*;
//
///**
// * The base class for map views. This is meant to be extended for different
// * tile map orientations, such as orthagonal and isometric.
// *
// * @version $Id$
// */
public abstract class MapView {
//
//    public static int ZOOM_NORMALSIZE = 5;
//
//    protected Map map;
//    protected Canvas canvas;
//    protected int modeFlags;
//    protected double zoom = 1.0;
//    protected int zoomLevel = ZOOM_NORMALSIZE;
//
//    // Grid properties
//    protected boolean showGrid;
//    protected boolean antialiasGrid;
//    protected Color gridColor;
//    protected int gridOpacity;
//
//
//
//    private static final int DEFAULT_BACKGROUND_COLOR = Color.rgb(64, 64, 64);
//    /** The default grid color (black). */
//    public static final int DEFAULT_GRID_COLOR = Color.BLACK;
//
//    /**
//     * Creates a new <code>MapView</code> that displays the specified map.
//     *
//     * @param map the map to be displayed by this map view
//     */
//    protected MapView(Map map, Canvas canvas) {
//        this.map = map;
//        this.canvas = canvas;
//    }
//
//    public void toggleMode(int modeModifier) {
//        modeFlags ^= modeModifier;
//    }
//
//    public void setMode(int modeModifier, boolean value) {
//        if (value) {
//            modeFlags |= modeModifier;
//        }
//        else {
//            modeFlags &= ~modeModifier;
//        }
//    }
//
//    public boolean getMode(int modeModifier) {
//        return (modeFlags & modeModifier) != 0;
//    }
//
//    public void setGridColor(Color gridColor) {
//        this.gridColor = gridColor;
//    }
//
//    public void setGridOpacity(int gridOpacity) {
//        this.gridOpacity = gridOpacity;
//    }
//
//    public void setAntialiasGrid(boolean antialiasGrid) {
//        this.antialiasGrid = antialiasGrid;
//    }
//
//    public boolean getShowGrid() {
//        return showGrid;
//    }
//
//    public void setShowGrid(boolean showGrid) {
//        this.showGrid = showGrid;
//    }
//
//
//    // Zooming
//
//
//
//
//    // Scrolling
//
//    public abstract Dimension getPreferredSize();
//
//    public Dimension getPreferredScrollableViewportSize() {
//        return getPreferredSize();
//    }
//
//    public boolean getScrollableTracksViewportHeight() {
//        return false;
//    }
//
//    public boolean getScrollableTracksViewportWidth() {
//        return false;
//    }
//
//    public abstract int getScrollableBlockIncrement(Rectangle visibleRect,
//            int orientation, int direction);
//
//    public abstract int getScrollableUnitIncrement(Rectangle visibleRect,
//            int orientation, int direction);
//
//    /**
//     * Creates a MapView instance that will render the map in the right
//     * orientation.
//     *
//     * @param p the Map to create a view for
//     * @return a suitable instance of a MapView for the given Map
//     * @see Map#getOrientation()
//     */
//    public static MapView createViewforMap(Map p) throws Exception {
//        MapView mapView = null;
//
//        int orientation = p.getOrientation();
//
//        if (orientation == Map.MDO_ISO) {
//        	throw new Exception("No support for ISO MAPS");
////            mapView = new IsoMapView(p);
//        }
//        else if (orientation == Map.MDO_ORTHO) {
//            mapView = new OrthoMapView(p, canvas);
//        }
//        else if (orientation == Map.MDO_HEX) {
//        	throw new Exception("No support for HEX MAPS");
////            mapView = new HexMapView(p);
//        	
//        }
//        else if (orientation == Map.MDO_SHIFTED) {
//        	throw new Exception("No support for SHIFTED MAPS");
////            mapView = new ShiftedMapView(p);
//        }
//
//        return mapView;
//    }
//
//    // Painting
//
//    /**
//     * Draws all the visible layers of the map. Takes several flags into
//     * account when drawing, and will also draw the grid, and any 'special'
//     * layers.
//     *
//     * @param g the Graphics2D object to paint to
//     * @see javax.swing.JComponent#paintComponent(Graphics)
//     * @see MapLayer
//     * @see SelectionLayer
//     */
//    public void paintComponent(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g.create();
//
//        MapLayer layer;
//        Rectangle clip = g2d.getClipBounds();
//
//        g2d.setStroke(new BasicStroke(2.0f));
//
//        // Do an initial fill with the background color
//        // todo: make background color configurable
//        //try {
//        //    String colorString = displayPrefs.get("backgroundColor", "");
//        //    g2d.setColor(Color.decode(colorString));
//        //} catch (NumberFormatException e) {
//        //}
//        g2d.setColor(DEFAULT_BACKGROUND_COLOR);
//        g2d.fillRect(clip.x, clip.y, clip.width, clip.height);
//
//        paintSubMap(map, g2d, 1.0f);
//
//        if (!getMode(PF_NOSPECIAL)) {
//            Iterator li = map.getLayersSpecial();
//
//            while (li.hasNext()) {
//                layer = (MapLayer) li.next();
//                if (layer.isVisible()) {
//                    if (layer instanceof SelectionLayer) {
//                        g2d.setComposite(AlphaComposite.getInstance(
//                                AlphaComposite.SRC_ATOP, 0.3f));
//                        g2d.setColor(
//                                ((SelectionLayer) layer).getHighlightColor());
//                    }
//                    paintLayer(g2d, (TileLayer) layer);
//                }
//            }
//
//            // Paint Brush
//            if (currentBrush != null) {
//                currentBrush.drawPreview(g2d, this);
//            }
//        }
//
//        // Grid color (also used for coordinates)
//        g2d.setColor(gridColor);
//
//        if (showGrid) {
//            // Grid opacity
//            if (gridOpacity < 255) {
//                g2d.setComposite(AlphaComposite.getInstance(
//                        AlphaComposite.SRC_ATOP,
//                        (float) gridOpacity / 255.0f));
//            }
//            else {
//                g2d.setComposite(AlphaComposite.SrcOver);
//            }
//
//            // Configure grid antialiasing
//            if (antialiasGrid) {
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                                     RenderingHints.VALUE_ANTIALIAS_ON);
//            }
//            else {
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                                     RenderingHints.VALUE_ANTIALIAS_OFF);
//            }
//
//            g2d.setStroke(new BasicStroke());
//            paintGrid(g2d);
//        }
//
//        if (getMode(PF_COORDINATES)) {
//            g2d.setComposite(AlphaComposite.SrcOver);
//            paintCoordinates(g2d);
//        }
//
//        //if (editor != null && editor.getCurrentLayer() instanceof TileLayer) {
//        //    g2d.setComposite(AlphaComposite.SrcOver);
//        //
//        //    TileLayer tl = (TileLayer) editor.getCurrentLayer();
//        //    if (tl != null && tl.isVisible()) {
//        //        paintPropertyFlags(g2d, tl);
//        //    }
//        //}
//    }
//
//    public void paintSubMap(MultilayerPlane m, Graphics2D g2d,
//                            float mapOpacity) {
//        Iterator li = m.getLayers();
//        MapLayer layer;
//
//        while (li.hasNext()) {
//            layer = (MapLayer) li.next();
//            if (layer != null) {
//                float opacity = layer.getOpacity() * mapOpacity;
//                if (layer.isVisible() && opacity > 0.0f) {
//                    if (opacity < 1.0f) {
//                        g2d.setComposite(AlphaComposite.getInstance(
//                                AlphaComposite.SRC_ATOP, opacity));
//                    }
//                    else {
//                        g2d.setComposite(AlphaComposite.SrcOver);
//                    }
//
//                    if (layer instanceof TileLayer) {
//                        paintLayer(g2d, (TileLayer) layer);
//                    }
//                    else if (layer instanceof ObjectGroup) {
//                        paintObjectGroup(g2d, (ObjectGroup) layer);
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Draws a TileLayer. Implemented in a subclass.
//     *
//     * @param g2d   the graphics context to draw the layer onto
//     * @param layer the TileLayer to be drawn
//     */
//    protected abstract void paintLayer(Graphics2D g2d, TileLayer layer);
//
//    /**
//     * Draws an ObjectGroup. Implemented in a subclass.
//     *
//     * @param g2d   the graphics context to draw the object group onto
//     * @param og    the ObjectGroup to be drawn
//     */
//    protected abstract void paintObjectGroup(Graphics2D g2d, ObjectGroup og);
//
//    protected void paintEdge(Graphics2D g2d, MapLayer layer, int x, int y) {
//        /*
//        Polygon grid = createGridPolygon(x, y, 0);
//        PathIterator itr = grid.getPathIterator(null);
//        double nextPoint[] = new double[6];
//        double prevPoint[], firstPoint[];
//
//        Point p = screenToTileCoords(x, y);
//        int tx = p.x;
//        int ty = p.y;
//
//        itr.currentSegment(nextPoint);
//        firstPoint = prevPoint = nextPoint;
//
//        // Top
//        itr.next();
//        nextPoint = new double[6];
//        itr.currentSegment(nextPoint);
//        if (layer.getTileAt(tx, ty - 1) == null) {
//            g.drawLine(
//                    (int)prevPoint[0], (int)prevPoint[1],
//                    (int)nextPoint[0], (int)nextPoint[1]);
//        }
//
//        // Right
//        itr.next();
//        prevPoint = nextPoint;
//        nextPoint = new double[6];
//        itr.currentSegment(nextPoint);
//        if (layer.getTileAt(tx + 1, ty) == null) {
//            g.drawLine(
//                    (int)prevPoint[0], (int)prevPoint[1],
//                    (int)nextPoint[0], (int)nextPoint[1]);
//        }
//
//        // Left
//        itr.next();
//        prevPoint = nextPoint;
//        nextPoint = new double[6];
//        itr.currentSegment(nextPoint);
//        if (layer.getTileAt(tx, ty + 1) == null) {
//            g.drawLine(
//                    (int)prevPoint[0], (int)prevPoint[1],
//                    (int)nextPoint[0], (int)nextPoint[1]);
//        }
//
//        // Bottom
//        if (layer.getTileAt(tx - 1, ty) == null) {
//            g.drawLine(
//                    (int)nextPoint[0], (int)nextPoint[1],
//                    (int)firstPoint[0], (int)firstPoint[1]);
//        }
//        */
//    }
//
//    /**
//     * Tells this view a certain region of the map needs to be repainted.
//     * <p>
//     * Same as calling repaint() unless implemented more efficiently in a
//     * subclass.
//     *
//     * @param region the region that has changed in tile coordinates
//     */
//    public void repaintRegion(Rectangle region) {
//        repaint();
//    }
//
//    /**
//     * Draws the map grid.
//     *
//     * @param g2d the graphics context to draw the grid onto
//     */
//    protected abstract void paintGrid(Graphics2D g2d);
//
//    /**
//     * Draws the coordinates on each tile.
//     *
//     * @param g2d the graphics context to draw the coordinates onto
//     */
//    protected abstract void paintCoordinates(Graphics2D g2d);
//
//    protected abstract void paintPropertyFlags(Graphics2D g2d, TileLayer layer);
//
//    /**
//     * Returns a Polygon that matches the grid around the specified <b>Map</b>.
//     *
//     * @param tx
//     * @param ty
//     * @param border
//     * @return the created polygon
//     */
//    protected abstract Polygon createGridPolygon(int tx, int ty, int border);
//
//    // Conversion functions
//
//    public abstract Point screenToTileCoords(int x, int y);
//
//    /**
//     * Returns the pixel coordinates on the map based on the given screen
//     * coordinates. The map pixel coordinates may be different in more ways
//     * than the zoom level, depending on the projection the view implements.
//     *
//     * @param x x in screen coordinates
//     * @param y y in screen coordinates
//     * @return the position in map pixel coordinates
//     */
//    public abstract Point screenToPixelCoords(int x, int y);
//
//    /**
//     * Returns the location on the screen of the top corner of a tile.
//     *
//     * @param x
//     * @param y
//     * @return Point
//     */
//    public abstract Point tileToScreenCoords(int x, int y);
}

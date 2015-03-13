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

package no.ntnu.progark.towerdefense.tiled.view;

import java.util.Iterator;

import sheep.math.BoundingBox;
import no.ntnu.progark.towerdefense.tiled.core.Dimension;
import no.ntnu.progark.towerdefense.tiled.core.Map;
import no.ntnu.progark.towerdefense.tiled.core.Rectangle;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import no.ntnu.progark.towerdefense.tiled.core.*;

/**
 * An orthographic map view.
 */
public class OrthoMapView {
	public final static int ORIENTATION_VERTICAL = 1;
	public final static int ORIENTATION_HORIZONTAL = 2;
	public final static int ZOOM_NORMALSIZE = 5;
	public final static int DEFAULT_GRID_COLOR = Color.BLACK;
	public final static int DEFAULT_BACKGROUND_COLOR = Color.rgb(64, 64, 64);

	private Map map;
	private boolean showGrid;
	private Paint gridPaint;

	/**
	 * Creates a new orthographic map view that displays the specified map onto
	 * the given canvas.
	 * 
	 * @param map
	 *            the map to be displayed by this map view
	 * @param canvas
	 *            the canvas to to draw the map onto
	 */
	public OrthoMapView(Map map) {
		this.map = map;
		gridPaint = new Paint();
		gridPaint.setColor(DEFAULT_GRID_COLOR);
		showGrid = false;
	}



	public Dimension getMapSize() {
		Dimension tsize = getTileSize();

		return new Dimension(map.getWidth() * tsize.width, map.getHeight()
				* tsize.height);
	}

	protected void paintLayer(Canvas c, TileLayer layer) {
		// Determine tile size and offset
		Dimension tsize = getTileSize();
		if (tsize.width <= 0 || tsize.height <= 0) {
			return;
		}

		// Determine area to draw from clipping rectangle
		Rectangle clipRect = new Rectangle(c.getClipBounds());
		int startX = clipRect.x / tsize.width;
		int startY = clipRect.y / tsize.height;
		int endX = (clipRect.x + clipRect.width) / tsize.width + 1;
		int endY = (clipRect.y + clipRect.height) / tsize.height + 3;
		// (endY +2 for high tiles, could be done more properly)

		// Draw this map layer
		for (int y = startY, gy = (startY + 1) * tsize.height; y < endY; y++, gy += tsize.height) {
			for (int x = startX, gx = startX * tsize.width; x < endX; x++, gx += tsize.width) {
				Tile tile = layer.getTileAt(x, y);

				if (tile != null) {
					// if (layer instanceof SelectionLayer) {
					// gridPoly.translate(gx, gy);
					// g2d.fillPolygon(gridPoly);
					// gridPoly.translate(-gx, -gy);
					// //paintEdge(g, layer, gx, gy);
					// }
					// else {
					tile.draw(c, gx, gy);

					// }
				}
			}
		}
	}

	// protected void paintObjectGroup(Graphics2D g2d, ObjectGroup og) {
	// final Dimension tsize = getTileSize();
	// final Rectangle bounds = og.getBounds();
	// Iterator<MapObject> itr = og.getObjects();
	// g2d.translate(
	// bounds.x * tsize.width,
	// bounds.y * tsize.height);
	//
	// while (itr.hasNext()) {
	// MapObject mo = itr.next();
	// double ox = mo.getX() * zoom;
	// double oy = mo.getY() * zoom;
	//
	// Image objectImage = mo.getImage(zoom);
	// if (objectImage != null) {
	// g2d.drawImage(objectImage, (int) ox, (int) oy, null);
	// }
	//
	// if (mo.getWidth() == 0 || mo.getHeight() == 0) {
	// g2d.setRenderingHint(
	// RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_ON);
	// g2d.setColor(Color.black);
	// g2d.fillOval((int) ox + 1, (int) oy + 1,
	// (int) (10 * zoom), (int) (10 * zoom));
	// g2d.setColor(Color.orange);
	// g2d.fillOval((int) ox, (int) oy,
	// (int) (10 * zoom), (int) (10 * zoom));
	// g2d.setRenderingHint(
	// RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_OFF);
	// } else {
	// g2d.setColor(Color.black);
	// g2d.drawRect((int) ox + 1, (int) oy + 1,
	// (int) (mo.getWidth() * zoom),
	// (int) (mo.getHeight() * zoom));
	// g2d.setColor(Color.orange);
	// g2d.drawRect((int) ox, (int) oy,
	// (int) (mo.getWidth() * zoom),
	// (int) (mo.getHeight() * zoom));
	// }
	// if (zoom > 0.0625) {
	// final String s = mo.getName() != null ? mo.getName() : "(null)";
	// g2d.setColor(Color.black);
	// g2d.drawString(s, (int) (ox - 5) + 1, (int) (oy - 5) + 1);
	// g2d.setColor(Color.white);
	// g2d.drawString(s, (int) (ox - 5), (int) (oy - 5));
	// }
	// }
	//
	// g2d.translate(
	// -bounds.x * tsize.width,
	// -bounds.y * tsize.height);
	// }

	protected void paintGrid(Canvas c) {
		// Determine tile size
		Dimension tsize = getTileSize();
		if (tsize.width <= 0 || tsize.height <= 0) {
			return;
		}

		// Determine lines to draw from clipping rectangle
		Rectangle clipRect = new Rectangle(c.getClipBounds());
		int startX = clipRect.x / tsize.width * tsize.width;
		int startY = clipRect.y / tsize.height * tsize.height;
		int endX = clipRect.x + clipRect.width;
		int endY = clipRect.y + clipRect.height;

		for (int x = startX; x < endX; x += tsize.width) {

			c.drawLine(x, clipRect.y, x,
					(clipRect.y + clipRect.height - 1), gridPaint);
		}
		for (int y = startY; y < endY; y += tsize.height) {
			c.drawLine(clipRect.x, y, (clipRect.x
					+ clipRect.width - 1), y, gridPaint);
		}
	}

	// protected void paintCoordinates(Graphics2D g2d) {
	// Dimension tsize = getTileSize();
	// if (tsize.width <= 0 || tsize.height <= 0) {
	// return;
	// }
	// g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
	// RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	//
	// // Determine tile size and offset
	// Font font = new Font("SansSerif", Font.PLAIN, tsize.height / 4);
	// g2d.setFont(font);
	// FontRenderContext fontRenderContext = g2d.getFontRenderContext();
	//
	// // Determine area to draw from clipping rectangle
	// Rectangle clipRect = g2d.getClipBounds();
	// int startX = clipRect.x / tsize.width;
	// int startY = clipRect.y / tsize.height;
	// int endX = (clipRect.x + clipRect.width) / tsize.width + 1;
	// int endY = (clipRect.y + clipRect.height) / tsize.height + 1;
	//
	// // Draw the coordinates
	// int gy = startY * tsize.height;
	// for (int y = startY; y < endY; y++) {
	// int gx = startX * tsize.width;
	// for (int x = startX; x < endX; x++) {
	// String coords = "(" + x + "," + y + ")";
	// Rectangle2D textSize =
	// font.getStringBounds(coords, fontRenderContext);
	//
	// int fx = gx + (int) ((tsize.width - textSize.getWidth()) / 2);
	// int fy = gy + (int) ((tsize.height + textSize.getHeight()) / 2);
	//
	// g2d.drawString(coords, fx, fy);
	// gx += tsize.width;
	// }
	// gy += tsize.height;
	// }
	// }

	// public void repaintRegion(Rectangle region) {
	// Dimension tsize = getTileSize();
	// if (tsize.width <= 0 || tsize.height <= 0) {
	// return;
	// }
	// int maxExtraHeight =
	// (int) (map.getTileHeightMax() * zoom - tsize.height);
	//
	// // Calculate the visible corners of the region
	// int startX = region.x * tsize.width;
	// int startY = region.y * tsize.height - maxExtraHeight;
	// int endX = (region.x + region.width) * tsize.width;
	// int endY = (region.y + region.height) * tsize.height;
	//
	// Rectangle dirty =
	// new Rectangle(startX, startY, endX - startX, endY - startY);
	//
	// repaint(dirty);
	// canvas.
	// }

	public Point screenToTileCoords(int x, int y) {
		Dimension tsize = getTileSize();
		return new Point(x / tsize.width, y / tsize.height);
	}

	protected Dimension getTileSize() {
		return new Dimension((int) (map.getTileWidth()),
				(int) (map.getTileHeight()));
	}

	// protected Polygon createGridPolygon(int tx, int ty, int border) {
	// Dimension tsize = getTileSize();
	//
	// Polygon poly = new Polygon();
	// poly.addPoint(tx - border, ty - border);
	// poly.addPoint(tx + tsize.width + border, ty - border);
	// poly.addPoint(tx + tsize.width + border, ty + tsize.height + border);
	// poly.addPoint(tx - border, ty + tsize.height + border);
	//
	// return poly;
	// }

	public Point tileToScreenCoords(int x, int y) {
		Dimension tsize = getTileSize();
		return new Point(x * tsize.width, y * tsize.height);
	}

	/**
	 * Draws all the visible layers of the map. Takes several flags into account
	 * when drawing, and will also draw the grid, and any 'special' layers.
	 * 
	 * @param g
	 *            the Graphics2D object to paint to
	 * @see javax.swing.JComponent#paintComponent(Graphics)
	 * @see MapLayer
	 * @see SelectionLayer
	 */
	public void paintComponent(Canvas c) {

		MapLayer layer;

		// Do an initial fill with the background color
		// todo: make background color configurable
		// try {
		// String colorString = displayPrefs.get("backgroundColor", "");
		// g2d.setColor(Color.decode(colorString));
		// } catch (NumberFormatException e) {
		// }
		c.drawColor(DEFAULT_BACKGROUND_COLOR);

		paintSubMap(map, c);

		// if (!getMode(PF_NOSPECIAL)) {
		// Iterator li = map.getLayersSpecial();
		//
		// while (li.hasNext()) {
		// layer = (MapLayer) li.next();
		// if (layer.isVisible()) {
		// if (layer instanceof SelectionLayer) {
		// g2d.setComposite(AlphaComposite.getInstance(
		// AlphaComposite.SRC_ATOP, 0.3f));
		// g2d.setColor(
		// ((SelectionLayer) layer).getHighlightColor());
		// }
		// paintLayer(g2d, (TileLayer) layer);
		// }
		// }
		//
		// // Paint Brush
		// if (currentBrush != null) {
		// currentBrush.drawPreview(g2d, this);
		// }
		// }

		if (showGrid) {
			// Grid opacity
			// if (gridOpacity < 255) {
			// g2d.setComposite(AlphaComposite.getInstance(
			// AlphaComposite.SRC_ATOP,
			// (float) gridOpacity / 255.0f));
			// }
			// else {
			// g2d.setComposite(AlphaComposite.SrcOver);
			// }
			//
			// // Configure grid antialiasing
			// if (antialiasGrid) {
			// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			// RenderingHints.VALUE_ANTIALIAS_ON);
			// }
			// else {
			// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			// RenderingHints.VALUE_ANTIALIAS_OFF);
			// }

			paintGrid(c);
		}

		// if (getMode(PF_COORDINATES)) {
		// g2d.setComposite(AlphaComposite.SrcOver);
		// paintCoordinates(g2d);
		// }

		// if (editor != null && editor.getCurrentLayer() instanceof TileLayer)
		// {
		// g2d.setComposite(AlphaComposite.SrcOver);
		//
		// TileLayer tl = (TileLayer) editor.getCurrentLayer();
		// if (tl != null && tl.isVisible()) {
		// paintPropertyFlags(g2d, tl);
		// }
		// }
	}

	public void paintSubMap(MultilayerPlane m, Canvas c) {
		Iterator<MapLayer> li = m.iterator();
		MapLayer layer;

		while (li.hasNext()) {
			layer = li.next();
			if (layer != null) {
				if (layer.isVisible()) {
					if (layer instanceof TileLayer) {
						paintLayer(c, (TileLayer) layer);
					}
//					 else if (layer instanceof ObjectGroup) {
//						paintObjectGroup(g2d, (ObjectGroup) layer);
//					}
				}
			}
		}
	}

}

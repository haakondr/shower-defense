package no.ntnu.progark.towerdefense.tiled.core;

import sheep.math.BoundingBox;
import android.graphics.Rect;

public class Rectangle {

	/**
	 * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
	 */
	public int x;

	/**
	 * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
	 */
	public int y;

	/**
	 * The width of the <code>Rectangle</code>.
	 */
	public int width;

	/**
	 * The height of the <code>Rectangle</code>.
	 */
	public int height;

	/**
	 * Constructs a new <code>Rectangle</code> whose upper-left corner is at
	 * (0,&nbsp;0) in the coordinate space, and whose width and height are both
	 * zero.
	 */
	public Rectangle() {
		this(0, 0, 0, 0);
	}

	/**
	 * Constructs a new <code>Rectangle</code>, initialized to match the values
	 * of the specified <code>Rectangle</code>.
	 * 
	 * @param r
	 *            the <code>Rectangle</code> from which to copy initial values
	 *            to a newly constructed <code>Rectangle</code>
	 */
	public Rectangle(Rectangle r) {
		this(r.x, r.y, r.width, r.height);
	}

	/**
	 * Constructs a new <code>Rectangle</code> whose upper-left corner is
	 * specified as {@code (x,y)} and whose width and height are specified by
	 * the arguments of the same name.
	 * 
	 * @param x
	 *            the specified X coordinate
	 * @param y
	 *            the specified Y coordinate
	 * @param width
	 *            the width of the <code>Rectangle</code>
	 * @param height
	 *            the height of the <code>Rectangle</code>
	 */
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Converts the android rectangle to our rectangle format.
	 * @param r
	 */
	public Rectangle(Rect r) {
		this(r.left, r.top, r.width(), r.height());
	}
	
	/**
	 * Converts a bounding box representation to our rectangle format.
	 * The bounding box points are ordered like this:
	 * xmin, ymin,	xmin, ymax,	xmax, ymax,	xmax, ymin
	 */
	public Rectangle(BoundingBox box) {
		this((int)box.getPoints()[0], //xmin
			 (int)box.getPoints()[3], //ymax
			 (int)(box.getPoints()[4] - box.getPoints()[2]), //(xmax - xmin)
			 (int)(box.getPoints()[3] - box.getPoints()[1])); //(ymax - ymin));
	}

	/**
	 * Constructs a new <code>Rectangle</code> whose upper-left corner is at
	 * (0,&nbsp;0) in the coordinate space, and whose width and height are
	 * specified by the arguments of the same name.
	 * 
	 * @param width
	 *            the width of the <code>Rectangle</code>
	 * @param height
	 *            the height of the <code>Rectangle</code>
	 */
	public Rectangle(int width, int height) {
		this(0, 0, width, height);
	}

	/**
	 * Returns the X coordinate of the bounding <code>Rectangle</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the X coordinate of the bounding <code>Rectangle</code>.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the Y coordinate of the bounding <code>Rectangle</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the Y coordinate of the bounding <code>Rectangle</code>.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the width of the bounding <code>Rectangle</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the width of the bounding <code>Rectangle</code>.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Returns the height of the bounding <code>Rectangle</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the height of the bounding <code>Rectangle</code>.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Gets the size of this <code>Rectangle</code>, represented by the returned
	 * <code>Dimension</code>.
	 * <p>
	 * This method is included for completeness, to parallel the
	 * <code>getSize</code> method of <code>Component</code>.
	 * 
	 * @return a <code>Dimension</code>, representing the size of this
	 *         <code>Rectangle</code>.
	 */
	public Dimension getSize() {
		return new Dimension(width, height);
	}

	/**
	 * Constructs a new <code>Rectangle</code> whose top left corner is
	 * (0,&nbsp;0) and whose width and height are specified by the
	 * <code>Dimension</code> argument.
	 * 
	 * @param d
	 *            a <code>Dimension</code>, specifying width and height
	 */
	public Rectangle(Dimension d) {
		this(0, 0, d.width, d.height);
	}

	/**
	 * Gets the bounding <code>Rectangle</code> of this <code>Rectangle</code>.
	 * <p>
	 * This method is included for completeness, to parallel the
	 * <code>getBounds</code> method of {@link Component}.
	 * 
	 * @return a new <code>Rectangle</code>, equal to the bounding
	 *         <code>Rectangle</code> for this <code>Rectangle</code>.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Sets the bounding <code>Rectangle</code> of this <code>Rectangle</code>
	 * to match the specified <code>Rectangle</code>.
	 * <p>
	 * This method is included for completeness, to parallel the
	 * <code>setBounds</code> method of <code>Component</code>.
	 * 
	 * @param r
	 *            the specified <code>Rectangle</code>
	 */
	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	/**
	 * Sets the bounding <code>Rectangle</code> of this <code>Rectangle</code>
	 * to the specified <code>x</code>, <code>y</code>, <code>width</code>, and
	 * <code>height</code>.
	 * <p>
	 * This method is included for completeness, to parallel the
	 * <code>setBounds</code> method of <code>Component</code>.
	 * 
	 * @param x
	 *            the new X coordinate for the upper-left corner of this
	 *            <code>Rectangle</code>
	 * @param y
	 *            the new Y coordinate for the upper-left corner of this
	 *            <code>Rectangle</code>
	 * @param width
	 *            the new width for this <code>Rectangle</code>
	 * @param height
	 *            the new height for this <code>Rectangle</code>
	 */
	public void setBounds(int x, int y, int width, int height) {
		reshape(x, y, width, height);
	}

	/**
	 * Sets the bounding <code>Rectangle</code> of this <code>Rectangle</code>
	 * to the specified <code>x</code>, <code>y</code>, <code>width</code>, and
	 * <code>height</code>.
	 * <p>
	 * 
	 * @param x
	 *            the new X coordinate for the upper-left corner of this
	 *            <code>Rectangle</code>
	 * @param y
	 *            the new Y coordinate for the upper-left corner of this
	 *            <code>Rectangle</code>
	 * @param width
	 *            the new width for this <code>Rectangle</code>
	 * @param height
	 *            the new height for this <code>Rectangle</code>
	 * @deprecated As of JDK version 1.1, replaced by
	 *             <code>setBounds(int, int, int, int)</code>.
	 */
	@Deprecated
	public void reshape(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Checks whether or not this <code>Rectangle</code> contains the point at
	 * the specified location {@code (X,Y)}.
	 * 
	 * @param X
	 *            the specified X coordinate
	 * @param Y
	 *            the specified Y coordinate
	 * @return <code>true</code> if the point {@code (X,Y)} is inside this
	 *         <code>Rectangle</code>; <code>false</code> otherwise.
	 * @deprecated As of JDK version 1.1, replaced by
	 *             <code>contains(int, int)</code>.
	 */
	@Deprecated
	public boolean inside(int X, int Y) {
		int w = this.width;
		int h = this.height;
		if ((w | h) < 0) {
			// At least one of the dimensions is negative...
			return false;
		}
		// Note: if either dimension is zero, tests below must return false...
		int x = this.x;
		int y = this.y;
		if (X < x || Y < y) {
			return false;
		}
		w += x;
		h += y;
		// overflow || intersect
		return ((w < x || w > X) && (h < y || h > Y));
	}

	/**
	 * Checks whether or not this <code>Rectangle</code> contains the point at
	 * the specified location {@code (x,y)}.
	 * 
	 * @param x
	 *            the specified X coordinate
	 * @param y
	 *            the specified Y coordinate
	 * @return <code>true</code> if the point {@code (x,y)} is inside this
	 *         <code>Rectangle</code>; <code>false</code> otherwise.
	 * @since 1.1
	 */
	public boolean contains(int x, int y) {
		return inside(x, y);
	}

	/**
	 * Adds a point, specified by the integer arguments {@code newx,newy} to the
	 * bounds of this {@code Rectangle}.
	 * <p>
	 * If this {@code Rectangle} has any dimension less than zero, the rules for
	 * <a href=#NonExistant>non-existant</a> rectangles apply. In that case, the
	 * new bounds of this {@code Rectangle} will have a location equal to the
	 * specified coordinates and width and height equal to zero.
	 * <p>
	 * After adding a point, a call to <code>contains</code> with the added
	 * point as an argument does not necessarily return <code>true</code>. The
	 * <code>contains</code> method does not return <code>true</code> for points
	 * on the right or bottom edges of a <code>Rectangle</code>. Therefore, if
	 * the added point falls on the right or bottom edge of the enlarged
	 * <code>Rectangle</code>, <code>contains</code> returns <code>false</code>
	 * for that point. If the specified point must be contained within the new
	 * {@code Rectangle}, a 1x1 rectangle should be added instead:
	 * 
	 * <pre>
	 * r.add(newx, newy, 1, 1);
	 * </pre>
	 * 
	 * @param newx
	 *            the X coordinate of the new point
	 * @param newy
	 *            the Y coordinate of the new point
	 */
	public void add(int newx, int newy) {
		if ((width | height) < 0) {
			this.x = newx;
			this.y = newy;
			this.width = this.height = 0;
			return;
		}
		int x1 = this.x;
		int y1 = this.y;
		long x2 = this.width;
		long y2 = this.height;
		x2 += x1;
		y2 += y1;
		if (x1 > newx)
			x1 = newx;
		if (y1 > newy)
			y1 = newy;
		if (x2 < newx)
			x2 = newx;
		if (y2 < newy)
			y2 = newy;
		x2 -= x1;
		y2 -= y1;
		if (x2 > Integer.MAX_VALUE)
			x2 = Integer.MAX_VALUE;
		if (y2 > Integer.MAX_VALUE)
			y2 = Integer.MAX_VALUE;
		reshape(x1, y1, (int) x2, (int) y2);
	}
	
    /**
     * Translates this <code>Rectangle</code> the indicated distance,
     * to the right along the X coordinate axis, and 
     * downward along the Y coordinate axis.
     * @param dx the distance to move this <code>Rectangle</code> 
     *                 along the X axis
     * @param dy the distance to move this <code>Rectangle</code> 
     *                 along the Y axis
     * @see       java.awt.Rectangle#setLocation(int, int)
     * @see       java.awt.Rectangle#setLocation(java.awt.Point)
     */
    public void translate(int dx, int dy) {
        int oldv = this.x;
        int newv = oldv + dx;
        if (dx < 0) {
            // moving leftward
            if (newv > oldv) {
                // negative overflow
                // Only adjust width if it was valid (>= 0).
                if (width >= 0) {
                    // The right edge is now conceptually at
                    // newv+width, but we may move newv to prevent
                    // overflow.  But we want the right edge to
                    // remain at its new location in spite of the
                    // clipping.  Think of the following adjustment
                    // conceptually the same as:
                    // width += newv; newv = MIN_VALUE; width -= newv;
                    width += newv - Integer.MIN_VALUE;
                    // width may go negative if the right edge went past
                    // MIN_VALUE, but it cannot overflow since it cannot
                    // have moved more than MIN_VALUE and any non-negative
                    // number + MIN_VALUE does not overflow.
                }
                newv = Integer.MIN_VALUE;
            }
        } else {
            // moving rightward (or staying still)
            if (newv < oldv) {
                // positive overflow
                if (width >= 0) {
                    // Conceptually the same as:
                    // width += newv; newv = MAX_VALUE; width -= newv;
                    width += newv - Integer.MAX_VALUE;
                    // With large widths and large displacements
                    // we may overflow so we need to check it.
                    if (width < 0) width = Integer.MAX_VALUE;
                }
                newv = Integer.MAX_VALUE;
            }
        }
        this.x = newv;

        oldv = this.y;
        newv = oldv + dy;
        if (dy < 0) {
            // moving upward
            if (newv > oldv) {
                // negative overflow
                if (height >= 0) {
                    height += newv - Integer.MIN_VALUE;
                    // See above comment about no overflow in this case
                }
                newv = Integer.MIN_VALUE;
            }
        } else {
            // moving downward (or staying still)
            if (newv < oldv) {
                // positive overflow
                if (height >= 0) {
                    height += newv - Integer.MAX_VALUE;
                    if (height < 0) height = Integer.MAX_VALUE;
                }
                newv = Integer.MAX_VALUE;
            }
        }
        this.y = newv;
    }
    
    /**
     * Returns a <code>String</code> representing this 
     * <code>Rectangle</code> and its values.
     * @return a <code>String</code> representing this 
     *               <code>Rectangle</code> object's coordinate and size values.
     */
    @Override
	public String toString() {
	return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
    }

}

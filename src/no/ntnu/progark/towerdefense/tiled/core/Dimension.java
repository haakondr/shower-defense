package no.ntnu.progark.towerdefense.tiled.core;

public class Dimension {

	/**
	 * The width dimension. Negative values can be used
	 */
	public int width;

	/**
	 * The height dimension. Negative values can be used
	 */
	public int height;

	/**
	 * Creates an instance of <code>Dimension</code> whose width and height are
	 * the same as for the specified dimension.
	 */
	public Dimension(Dimension d) {
		this(d.width, d.height);
	}

	/**
	 * Constructs a <code>Dimension</code> and initializes it to the specified
	 * width and specified height.
	 * 
	 * @param width
	 *            the specified width
	 * @param height
	 *            the specified height
	 */
	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	* Sets the size of this <code>Dimension</code> object 
	* to the specified width and height.
	* This method is included for completeness, to parallel the
	* <code>setSize</code> method defined by <code>Component</code>.
	*
	* @param width the new width for this <code>Dimension</code> object
	* @param height the new height for this <code>Dimension</code> object
	*/
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	} 
	
	/**
	* Returns the width of this dimension in double precision.
	* @return the width of this dimension in double precision
	*/
	public double getWidth() {
		return width;
	}
	
	/**
	* Returns the height of this dimension in double precision.
	* @return the height of this dimension in double precision
	*/
	public double getHeight() {
		return height;
	}

	/**
	* Returns a string representation of the values of this 
	* <code>Dimension</code> object's <code>height</code> and 
	* <code>width</code> fields. This method is intended to be used only 
	* for debugging purposes, and the content and format of the returned 
	* string may vary between implementations. The returned string may be 
	* empty but may not be <code>null</code>.
	* 
	* @return a string representation of this <code>Dimension</code> 
	* object
	*/
	@Override
	public String toString() {
		return getClass().getName() + "[width=" + width + ",height=" + height + "]";
	}

}

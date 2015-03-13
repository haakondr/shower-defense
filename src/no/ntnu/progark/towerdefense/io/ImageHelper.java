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

package no.ntnu.progark.towerdefense.io;

import java.io.IOException;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * This class provides functions to help out with saving/loading images.
 *
 * @version $Id$
 */
public class ImageHelper {
    /**
     * Converts a byte array into an image. The byte array must include the
     * image header, so that a decision about format can be made.
     *
     * @param imageData The byte array of the data to convert.
     * @return Image The image instance created from the byte array
     * @throws IOException
     * @see java.awt.Toolkit#createImage(byte[] imagedata)
     */
    static public Bitmap bytesToImage(byte[] imageData) throws IOException {
    	return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }

    /**
     * This function loads the image denoted by <code>file</code>. This
     * supports PNG, GIF, JPG, and BMP (in 1.5).
     *
     * @param file
     * @return the (partially) loaded image
     * @throws IOException
     */
    static public Bitmap loadImageFile(Resources resources, String resourceName) throws IOException {
    	 return BitmapFactory.decodeResource(resources, resources.getIdentifier(resourceName, null, null));
    }
}

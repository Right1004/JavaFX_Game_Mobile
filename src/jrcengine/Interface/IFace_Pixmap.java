package jrcengine.Interface;

import jrcengine.Interface.IFace_Graphics.PixmapFormat;

public interface IFace_Pixmap {
	public int getWidth();

	public int getHeight();

	public PixmapFormat getFormat();

	public void dispose();
}

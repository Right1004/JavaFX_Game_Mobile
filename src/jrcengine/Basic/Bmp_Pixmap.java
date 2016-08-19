package jrcengine.Basic;

import jrcengine.Interface.IFace_Pixmap;
import jrcengine.Interface.IFace_Graphics.PixmapFormat;
import android.graphics.Bitmap;

/*
 * 비트맵의 정보를 관리해 주는 모듈이다.
 */

public class Bmp_Pixmap implements IFace_Pixmap {
	Bitmap bitmap;
	PixmapFormat format;

	public Bmp_Pixmap(Bitmap bitmap, PixmapFormat format) {
		this.bitmap = bitmap;
		this.format = format;
	}

	public int getWidth() {
		return bitmap.getWidth();
	}

	public int getHeight() {
		return bitmap.getHeight();
	}

	public PixmapFormat getFormat() {
		return format;
	}

	public void dispose() {
		bitmap.recycle();
	}
}

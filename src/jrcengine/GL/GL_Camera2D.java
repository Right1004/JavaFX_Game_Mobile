package jrcengine.GL;

import javax.microedition.khronos.opengles.GL10;

import jrcengine.Basic.GL_Graphics;

import jrcengine.Math.Math_Overlap_Rectangle;
import jrcengine.Math.Math_Vector;

public class GL_Camera2D {
	public final Math_Vector position;
	public float zoom;
	public final float frustum_Width;
	public final float frustum_Height;
	public final GL_Graphics glGraphics;

	private final Math_Overlap_Rectangle bounds;

	public GL_Camera2D(GL_Graphics glGraphics, float frustum_Width,
			float frustum_Height) {
		this.glGraphics = glGraphics;
		this.frustum_Width = frustum_Width;
		this.frustum_Height = frustum_Height;
		this.position = new Math_Vector(frustum_Width / 2, frustum_Height / 2);
		this.zoom = 1.0f;
		this.bounds = null;
	}

	public GL_Camera2D(GL_Graphics glGraphics, int center_x_set,
			int center_y_set, int width_set, int hight_set, float frustumWidth,
			float frustumHeight) {
		this.glGraphics = glGraphics;
		this.frustum_Width = frustumWidth;
		this.frustum_Height = frustumHeight;
		this.position = new Math_Vector(frustumWidth / 2, frustumHeight / 2);
		this.zoom = 1.0f;
		this.bounds = new Math_Overlap_Rectangle(center_x_set - width_set / 2,
				center_y_set - hight_set / 2, width_set, hight_set);
	}

	public void setViewportAndMatrices() {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(position.x - frustum_Width * zoom / 2, position.x
				+ frustum_Width * zoom / 2, position.y - frustum_Height * zoom
				/ 2, position.y + frustum_Height * zoom / 2, 1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	

	public void touchToWorld(Math_Vector touch) {
		touch.x = (touch.x / (float) glGraphics.getWidth()) * frustum_Width
				* zoom;
		touch.y = (1 - touch.y / (float) glGraphics.getHeight())
				* frustum_Height * zoom;
		touch.add(position).sub(frustum_Width * zoom / 2,
				frustum_Height * zoom / 2);
	}

	public void touchToWorld_partition(Math_Vector touch, GL_Camera2D old_cam) {

		touch.x = ((touch.x - bounds.lowerLeft.x))
				/ (float)bounds.width * frustum_Width * zoom;
		touch.y = ((touch.y - bounds.lowerLeft.y) / (float) bounds.height)
				* frustum_Height * zoom;
		touch.add(position).sub(frustum_Width * zoom / 2,
				frustum_Height * zoom / 2);

	}

	public Math_Overlap_Rectangle get_Math_Overlap_Rectangle() {
		return bounds;
	}

}

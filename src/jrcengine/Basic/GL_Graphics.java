package jrcengine.Basic;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class GL_Graphics {
	GLSurfaceView glView;
	GL10 gl;

	GL_Graphics(GLSurfaceView glView) {
		this.glView = glView;
	}

	public GL10 getGL() {
		return gl;
	}

	void setGL(GL10 gl) {
		this.gl = gl;
	}

	public int getWidth() {
		return glView.getWidth();
	}

	public int getHeight() {
		return glView.getHeight();
	}
}

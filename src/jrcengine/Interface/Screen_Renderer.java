package jrcengine.Interface;

import jrcengine.Basic.GL_Graphics;
import jrcengine.GL.GL_SpriteBatcher;

public abstract class Screen_Renderer {
	protected GL_Graphics glGraphics;

	protected GL_SpriteBatcher batcher;

	public Screen_Renderer(GL_Graphics glGraphics, GL_SpriteBatcher batcher) {
		this.glGraphics = glGraphics;
		this.batcher = batcher;

	}

	abstract public void render();

	abstract protected void renderBackground();

	abstract protected void renderObjects();

}
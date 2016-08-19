package jrcengine.Basic;

import jrcengine.Interface.Abstract_Screen;
import jrcengine.Interface.IFace_Game;

public abstract class GL_Screen extends Abstract_Screen {
	protected final GL_Graphics glGraphics;
	protected final GL_Game glGame;

	public GL_Screen(IFace_Game game) {
		super(game);
		glGame = (GL_Game) game;
		glGraphics = ((GL_Game) game).getGLGraphics();
	}

}

package jrcengine.Interface;

public abstract class Abstract_Screen {
	protected final IFace_Game game;

	public Abstract_Screen(IFace_Game game) {
		this.game = game;
	}

	public abstract void update(float deltaTime);

	public abstract void present(float deltaTime);

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();
	
}

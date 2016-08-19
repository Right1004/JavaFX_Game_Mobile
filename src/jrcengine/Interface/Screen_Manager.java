package jrcengine.Interface;

abstract public class Screen_Manager {


	public Screen_Manager() {

	}

	abstract protected void generate();

	abstract public void update(float deltaTime);

	abstract protected void update_checkCollisions(float deltaTime);

}

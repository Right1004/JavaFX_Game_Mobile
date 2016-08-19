package jrcengine.Interface;

public interface IFace_Game {
	public IFace_Input getInput();

	public IFace_FileIO getFileIO();

	public IFace_Graphics getGraphics();

	public IFace_Audio getAudio();

	public void setScreen(Abstract_Screen screen);
	
	public void exit();

	public Abstract_Screen getCurrentScreen();

	public Abstract_Screen getStartScreen();
}
package pangpang.MainGame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jrcengine.Basic.GL_Game;
import jrcengine.Interface.Abstract_Screen;
import jrcengine.Manage.*;
import jrcengine.NetworkModule.NetworkModule;

public class test_StartTest extends GL_Game
{
	boolean firstTimeCreate = true;
	static Map_Controler map_controler;
	
	public Abstract_Screen getStartScreen()
	{
		map_controler = new Map_Controler(this);
		return new Screen_MainMenu(this);
	}
	
	

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate)
		{

            NetworkModule.startClient();
			Manage_Settings.load(getFileIO());
			Manage_Assets.load(this);
			firstTimeCreate = false;
		}
		else
		{
			Manage_Assets.reload();
			
		}
	}

	@Override
	public void onPause()
	{
		super.onPause();
		 if(Manage_Settings.soundEnabled)
	           Manage_Assets.music.pause();
	}
	
}

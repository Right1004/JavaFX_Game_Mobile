package jrcengine.Basic;

import jrcengine.Interface.Abstract_Screen;
import jrcengine.Interface.IFace_Audio;
import jrcengine.Interface.IFace_FileIO;
import jrcengine.Interface.IFace_Game;
import jrcengine.Interface.IFace_Graphics;
import jrcengine.Interface.IFace_Input;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

/*
 * 여기서 구현하는 것은 전체적인 게임의 엑티비티의 구현이다. 
 * 인터페이스는 GAME을 사용하였다.
 * 여기서 발생하는 ERROR CODE 는 12이다.
 */

public abstract class g2D_Game extends Activity implements IFace_Game {
	g2D_RenderView renderView;
	IFace_Graphics graphics;
	IFace_Audio audio;
	IFace_Input input;
	IFace_FileIO fileIO;
	Abstract_Screen screen;
	WakeLock wakeLock;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		int frameBufferWidth = isLandscape ? 480 : 320; // 600 : 480;
		int frameBufferHeight = isLandscape ? 320 : 480;// 480 : 600;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
				frameBufferHeight, Config.RGB_565);

		float scaleX = (float) frameBufferWidth
				/ getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = (float) frameBufferHeight
				/ getWindowManager().getDefaultDisplay().getHeight();

		renderView = new g2D_RenderView(this, frameBuffer);
		graphics = new g2D_Graphics(getAssets(), frameBuffer);
		fileIO = new File_FileIO(getAssets());
		audio = new Sound_Audio(this);
		input = new Control_Input(this, renderView, scaleX, scaleY);
		screen = getStartScreen();
		setContentView(renderView);

		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
				"GLGame");
	}

	@Override
	public void onResume() {
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();

		if (isFinishing())
			screen.dispose();
	}

	public IFace_Input getInput() {
		return input;
	}

	public IFace_FileIO getFileIO() {
		return fileIO;
	}

	public IFace_Graphics getGraphics() {
		return graphics;
	}

	public IFace_Audio getAudio() {
		return audio;
	}

	public void setScreen(Abstract_Screen screen) {
		if (screen == null) {
			Log_Exception.logEvent("Error Code 12", "Game_Screen_non");
			throw new IllegalArgumentException(
					"Screen must not be null <Error Code12> ");
		}
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	public Abstract_Screen getCurrentScreen() {
		return screen;
	}
}
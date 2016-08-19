package jrcengine.Basic;

import android.util.Log;

public class Log_TimeCounter {
	long startTime = System.nanoTime();
	int frames = 0;

	public void logFrame() {
		frames++;
		if (System.nanoTime() - startTime >= 1000000000) {
			Log.d("FPSCounter", "fps: " + frames);
			frames = 0;
			startTime = System.nanoTime();
		}
	}
}

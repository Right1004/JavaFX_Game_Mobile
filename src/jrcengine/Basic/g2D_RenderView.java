package jrcengine.Basic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
 * 게임의 더블 버퍼를 위해서 구현한 것으로
 * 게임의 깜밖임을 최소화 시켜주는 역활을 한다.
 * 예외 처리로는 에러 코드번호 14가있다.
 */

public class g2D_RenderView extends SurfaceView implements Runnable
{
	g2D_Game game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;

	public g2D_RenderView(g2D_Game game, Bitmap framebuffer)
	{
		super(game);
		this.game = game;
		this.framebuffer = framebuffer;
		this.holder = getHolder();
	}

	public void resume()
	{
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}

	public void run()
	{
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while (running)
		{
			if (!holder.getSurface().isValid())
				continue;

			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();

			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);

			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(framebuffer, null, dstRect, null);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause()
	{
		running = false;
		while (true)
		{
			try
			{
				renderThread.join();
				break;
			}
			catch (InterruptedException e)
			{
				Log_Exception.logEvent("Error Code 14", "RenderView_interrupt");
				// retry
			}
		}
	}
}
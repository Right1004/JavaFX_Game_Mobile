package pangpang.MainGame;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import jrcengine.Basic.GL_Screen;
import jrcengine.GL.GL_Camera2D;
import jrcengine.GL.GL_SpriteBatcher;
import jrcengine.GL.GL_Texture;
import jrcengine.GL.GL_TextureRegion;
import jrcengine.Interface.IFace_Game;
import jrcengine.Interface.IFace_Input.TouchEvent;
import jrcengine.Manage.Manage_Assets;
import jrcengine.Manage.Manage_Settings;
import jrcengine.Math.Math_Overlap;
import jrcengine.Math.Math_Overlap_Rectangle;
import jrcengine.Math.Math_Vector;
import jrcengine.NetworkModule.NetworkModule;

public class ScoreScreen_temp extends GL_Screen {
	GL_Camera2D guiCam;
	GL_SpriteBatcher batcher;
	Math_Overlap_Rectangle nextBounds;
	Math_Vector touchPoint;
	GL_Texture helpImage;
	GL_TextureRegion helpRegion;

	String[] highScores;
	private int buttons_Tag;
	float xOffset = 0;

	public ScoreScreen_temp(IFace_Game game) {
		super(game);

		guiCam = new GL_Camera2D(glGraphics, Screen_MainMenu.GAME_WIDTH, Screen_MainMenu.GAME_HEIGHT);
		nextBounds = new Math_Overlap_Rectangle(390, 60, 60, 60);
		touchPoint = new Math_Vector();
		batcher = new GL_SpriteBatcher(glGraphics, 100);
		buttons_Tag = 29;

		highScores = new String[5];
		for (int i = 0; i < 5; i++) {
			highScores[i] = (i + 1) + ". " + Manage_Settings.highscores[i];
			xOffset = Math.max(highScores[i].length() * Manage_Assets.font.glyphWidth, xOffset);
		}
		xOffset = 100 - xOffset / 2 + Manage_Assets.font.glyphWidth / 2;

		NetworkModule.sendPacket(Manage_Assets.NetworkProtocol._REQUEST_THE_ROOM_LIST_INFORMATION + "", "PhoneRoom",
				2 + "", 4 + "");

		NetworkModule.sendPacket(Manage_Assets.NetworkProtocol._REQEUST_MOBILE_PANGPANG_SCORE + "");

		// NetworkModule.sendPacket(Manage_Assets.NetworkProtocol._REQEUST_MOBILE_PANGPANG_SCORE
		// + "");
		NetworkModule.setglMainGame(this);

	}

	@Override
	public void resume() {
		helpImage = new GL_Texture(glGame, "space0.png");
		helpRegion = new GL_TextureRegion(helpImage, 0, 0, Screen_MainMenu.GAME_HEIGHT, Screen_MainMenu.GAME_WIDTH);
	}

	@Override
	public void pause() {
		helpImage.dispose();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (event.type == TouchEvent.TOUCH_UP && 38 == buttons_Tag) {
				if (Math_Overlap.pointInRectangle(nextBounds, touchPoint)) {
					buttons_Tag = 29;
					// Assets.playSound(Assets.clickSound);
					game.setScreen(new Options(game));
					return;
				}
				this.buttons_Tag = 29;
			}

			if (event.type == TouchEvent.TOUCH_DOWN) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (Math_Overlap.pointInRectangle(nextBounds, touchPoint)) {

					this.buttons_Tag = 38;
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(helpImage);
		batcher.drawSprite(Screen_MainMenu.GAME_WIDTH / 2, Screen_MainMenu.GAME_HEIGHT / 2, Screen_MainMenu.GAME_WIDTH,
				Screen_MainMenu.GAME_HEIGHT, helpRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Manage_Assets.items);
		batcher.drawSprite(180, 270, 300, 33, Manage_Assets.highScoresRegion);

		float y = 130;
		for (int i = 4; i >= 0; i--) {
			Manage_Assets.font.drawText(batcher, highScores[i], xOffset, y);
			y += Manage_Assets.font.glyphHeight;
		}
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture.get(buttons_Tag));
		batcher.drawSprite(420, 90, 60, 60, Manage_Assets.textureRegion.get(buttons_Tag));
		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void dispose() {
	}
}

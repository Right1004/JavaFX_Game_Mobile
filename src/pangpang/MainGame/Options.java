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
import jrcengine.Math.Math_Overlap;
import jrcengine.Math.Math_Overlap_Rectangle;
import jrcengine.Math.Math_Vector;
import jrcengine.Manage.Manage_Settings;

public class Options extends GL_Screen {
	GL_Camera2D guiCam;
	GL_SpriteBatcher batcher;
	Math_Overlap_Rectangle exitBounds;
	Math_Overlap_Rectangle nextBounds;
	Math_Overlap_Rectangle autoBounds;
	Math_Overlap_Rectangle gravityBounds;
	Math_Overlap_Rectangle soundBounds;
	Math_Overlap_Rectangle vibrationBounds;
	Math_Overlap_Rectangle differentBounds;
	Math_Overlap_Rectangle immortalBounds;
	Math_Vector touchPoint;
	GL_Texture helpImage;
	GL_TextureRegion helpRegion;

	String gravity[] = { "GV ON", "GV OFF" };
	String autoAttack[] = { "AUTO ATTACK ON", "AUTO ATTACK OFF" };
	String music[] = { "MUSIC ON", "MUSIC OFF" };
	String immotal[] = { "IMMOTAL ON", "IMMOTAL OFF" };
	String level[] = { "LEVEL 1", "LEVEL 2", "LEVEL 3", "LEVEL 4" };
	String vibra[] = { "VIB ON", "VIB OFF" };
	String temp = new String();

	// button-label color
	int buttons_Exit_Tag;
	int buttons_Next_Tag;
	int buttons_AutoAttack_Tag;
	int buttons_Gravity_Tag;
	int buttons_Sound_Tag;
	int buttons_Vibration_Tag;
	int buttons_Different_Tag;
	int buttons_immortal_Tag;

	public Options(IFace_Game game) {
		super(game);

		guiCam = new GL_Camera2D(glGraphics, Screen_MainMenu.GAME_WIDTH,
				Screen_MainMenu.GAME_HEIGHT);
		autoBounds = new Math_Overlap_Rectangle(30, 150, 60, 60);
		gravityBounds = new Math_Overlap_Rectangle(30, 240, 60, 60);
		soundBounds = new Math_Overlap_Rectangle(30, 60, 60, 60);
		vibrationBounds = new Math_Overlap_Rectangle(120, 60, 60, 60);
		differentBounds = new Math_Overlap_Rectangle(120, 150, 60, 60);
		immortalBounds = new Math_Overlap_Rectangle(120, 240, 60, 60);
		exitBounds = new Math_Overlap_Rectangle(390, 60, 60, 60);
		nextBounds = new Math_Overlap_Rectangle(300, 60, 60, 60);
		touchPoint = new Math_Vector();
		batcher = new GL_SpriteBatcher(glGraphics, 100);

		buttons_Exit_Tag = 29;
		buttons_Next_Tag = 24;
		buttons_AutoAttack_Tag = Manage_Settings.autoAttackEnabled != true ? 0
				: 1;
		buttons_Gravity_Tag = Manage_Settings.gravityEnabled != true ? 17 : 16;
		buttons_Sound_Tag = Manage_Settings.soundEnabled != true ? 26 : 25;
		buttons_Vibration_Tag = Manage_Settings.vibrationEnabled != true ? 30
				: 31;
		buttons_Different_Tag = Manage_Settings.difficult + 55;
		buttons_immortal_Tag = Manage_Settings.immortal != true ? 59 : 60;
	}

	@Override
	public void resume() {
		helpImage = new GL_Texture(glGame, "space0.png");
		helpRegion = new GL_TextureRegion(helpImage, 0, 0,
				Screen_MainMenu.GAME_HEIGHT, Screen_MainMenu.GAME_WIDTH);
	}

	@Override
	public void pause() {
		helpImage.dispose();
	}

	private void fileUp() {

		Manage_Settings.autoAttackEnabled = (buttons_AutoAttack_Tag == 1) ? true
				: false;
		Manage_Settings.gravityEnabled = (buttons_Gravity_Tag == 16) ? true
				: false;
		Manage_Settings.soundEnabled = (buttons_Sound_Tag == 25) ? true : false;
		Manage_Settings.vibrationEnabled = (buttons_Vibration_Tag == 31) ? true
				: false;
		Manage_Settings.difficult = buttons_Different_Tag - 55;

		Manage_Settings.immortal = (buttons_immortal_Tag == 60) ? true : false;

		Manage_Settings.save(game.getFileIO());

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (event.type == TouchEvent.TOUCH_UP) {
				if (Math_Overlap.pointInRectangle(exitBounds, touchPoint)
						&& 38 == buttons_Exit_Tag) {
					// Assets.playSound(Assets.clickSound);
					buttons_Exit_Tag = 29;

					fileUp();
					game.setScreen(new Screen_MainMenu(game));
					return;
				}
				if (Math_Overlap.pointInRectangle(nextBounds, touchPoint)
						&& 37 == buttons_Next_Tag) {
					buttons_Next_Tag = 24;

					fileUp();
					game.setScreen(new ScoreScreen(game));
					return;
				}
				if (Math_Overlap.pointInRectangle(gravityBounds, touchPoint)) {

					this.buttons_Gravity_Tag = (buttons_Gravity_Tag != 16) ? 16
							: 17;
					return;
				}

				if (Math_Overlap.pointInRectangle(autoBounds, touchPoint)) {

					this.buttons_AutoAttack_Tag = (buttons_AutoAttack_Tag != 0) ? 0
							: 1;
					return;
				}

				if (Math_Overlap.pointInRectangle(soundBounds, touchPoint)) {

					this.buttons_Sound_Tag = (buttons_Sound_Tag != 25) ? 25
							: 26;
					
					if(this.buttons_Sound_Tag == 25) 
                       Manage_Assets.music.play();
                    else
                    	Manage_Assets.music.pause();
					
					return;
				}

				if (Math_Overlap.pointInRectangle(vibrationBounds, touchPoint)) {
					this.buttons_Vibration_Tag = (buttons_Vibration_Tag != 31) ? 31
							: 30;
					return;
				}

				if (Math_Overlap.pointInRectangle(immortalBounds, touchPoint)) {
					this.buttons_immortal_Tag = (buttons_immortal_Tag != 60) ? 60
							: 59;
					return;
				}

				if (Math_Overlap.pointInRectangle(differentBounds, touchPoint)) {
					if (buttons_Different_Tag < 58) {
						this.buttons_Different_Tag += 1;
					} else {
						this.buttons_Different_Tag = 55;
					}
				}

				this.buttons_Exit_Tag = 29;
				buttons_Next_Tag = 24;
			}

			if (event.type == TouchEvent.TOUCH_DOWN) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (Math_Overlap.pointInRectangle(exitBounds, touchPoint)) {

					this.buttons_Exit_Tag = 38;
					return;
				}

				if (Math_Overlap.pointInRectangle(nextBounds, touchPoint)) {

					this.buttons_Next_Tag = 37;
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
		batcher.drawSprite(Screen_MainMenu.GAME_WIDTH / 2,
				Screen_MainMenu.GAME_HEIGHT / 2, Screen_MainMenu.GAME_WIDTH,
				Screen_MainMenu.GAME_HEIGHT, helpRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Manage_Assets.texture.get(buttons_Gravity_Tag)); // gravity
		batcher.drawSprite(60, 270, 60, 60,
				Manage_Assets.textureRegion.get(buttons_Gravity_Tag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture.get(buttons_AutoAttack_Tag)); // auto
																				// attack
		batcher.drawSprite(60, 180, 60, 60,
				Manage_Assets.textureRegion.get(buttons_AutoAttack_Tag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture.get(buttons_Sound_Tag)); // music
																			// on
		batcher.drawSprite(60, 90, 60, 60,
				Manage_Assets.textureRegion.get(buttons_Sound_Tag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture.get(buttons_Vibration_Tag)); // vibration
		// on
		batcher.drawSprite(150, 90, 60, 60,
				Manage_Assets.textureRegion.get(buttons_Vibration_Tag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture.get(44)); // immotall
		// on
		batcher.drawSprite(150, 270, 60, 60,
				Manage_Assets.textureRegion.get(buttons_immortal_Tag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture.get(43)); // diffents

		batcher.drawSprite(150, 180, 60, 60,
				Manage_Assets.textureRegion.get(buttons_Different_Tag));
		batcher.endBatch();

		// exit buttons
		batcher.beginBatch(Manage_Assets.texture.get(buttons_Exit_Tag));
		batcher.drawSprite(420, 90, 60, 60,
				Manage_Assets.textureRegion.get(buttons_Exit_Tag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture.get(buttons_Next_Tag));
		batcher.drawSprite(330, 90, 60, 60,
				Manage_Assets.textureRegion.get(buttons_Next_Tag));
		batcher.endBatch();
		batcher.beginBatch(Manage_Assets.items);

		temp = (buttons_Gravity_Tag == 16) ? gravity[0] : gravity[1];
		Manage_Assets.font.drawText(batcher, temp, 240, 320 - 30);
		temp = (buttons_AutoAttack_Tag == 1) ? autoAttack[0] : autoAttack[1];
		Manage_Assets.font.drawText(batcher, temp, 240, 320 - 60);
		temp = (buttons_Sound_Tag == 25) ? music[0] : music[1];
		Manage_Assets.font.drawText(batcher, temp, 240, 320 - 90);
		temp = (buttons_immortal_Tag == 60) ? immotal[0] : immotal[1];
		Manage_Assets.font.drawText(batcher, temp, 240, 320 - 120);
		temp = level[buttons_Different_Tag - 55];
		Manage_Assets.font.drawText(batcher, temp, 240, 320 - 150);
		temp = (buttons_Vibration_Tag == 31) ? vibra[0] : vibra[1];
		Manage_Assets.font.drawText(batcher, temp, 240, 320 - 180);
		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void dispose() {
	}
}

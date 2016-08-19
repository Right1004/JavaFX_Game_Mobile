// 선택 화면
package pangpang.MainGame;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import jrcengine.Basic.GL_Screen;
import jrcengine.GL.GL_Animation;
import jrcengine.GL.GL_Camera2D;
import jrcengine.GL.GL_SpriteBatcher;
import jrcengine.GL.GL_TextureRegion;

import jrcengine.Interface.IFace_Game;
import jrcengine.Interface.IFace_Input.TouchEvent;
import jrcengine.Math.Math_Overlap;
import jrcengine.Math.Math_Vector;
import jrcengine.Math.Math_Overlap_Rectangle;
import jrcengine.Manage.Manage_Assets;
import jrcengine.Manage.Manage_Settings;

public class Screen_MainMenu extends GL_Screen {

	static final int GAME_WIDTH = 480;
	static final int GAME_HEIGHT = 320;

	/* choice bar condition */
	int newGameButtonTag;
	int optionButtonTag;
	int aboutButtonTag;
	int quitButtonTag;

	/* click vector */
	Math_Vector click;

	GL_Camera2D guiCam;
	GL_SpriteBatcher batcher;
	Math_Overlap_Rectangle newGameButtons;
	Math_Overlap_Rectangle optionsButtons;
	Math_Overlap_Rectangle aboutButtons;
	Math_Overlap_Rectangle quitButtons;
	Math_Vector touchPoint;

	Girl girl;

	static boolean is_Debug = false;

	public Screen_MainMenu(IFace_Game game) {
		super(game);
		this.guiCam = new GL_Camera2D(glGraphics, GAME_WIDTH, GAME_HEIGHT);
		this.batcher = new GL_SpriteBatcher(glGraphics, 100);
		
		this.newGameButtons = new Math_Overlap_Rectangle(230, 39 + 64 + 64 + 64,
				200, 50);
		this.optionsButtons = new Math_Overlap_Rectangle(230, 39 + 64 + 64, 200,
				50);
		this.aboutButtons = new Math_Overlap_Rectangle(230, 39 + 64, 200, 50);
		this.quitButtons = new Math_Overlap_Rectangle(230, 39, 200, 50);
		this.touchPoint = new Math_Vector();

		this.newGameButtonTag = 4;
		this.optionButtonTag = 8;
		this.aboutButtonTag = 10;
		this.quitButtonTag = 14;
		this.girl = new Girl(53, 80, Girl.GIRL_HEIGHT / 2, 0, 0);
		this.click = new Math_Vector(400, 0);

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			if (event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (Math_Overlap.pointInRectangle(newGameButtons, touchPoint)
						&& newGameButtonTag == 5) {

					newGameButtonTag = 4;
					game.setScreen(new Screen_MainGame(game));
					return;
				}
				if (Math_Overlap.pointInRectangle(optionsButtons, touchPoint)
						&& optionButtonTag == 9) {
					optionButtonTag = 8;
					game.setScreen(new Options(game));
					return;
				}
				if (Math_Overlap.pointInRectangle(aboutButtons, touchPoint)
						&& aboutButtonTag == 11) {
					aboutButtonTag = 10;
					game.setScreen(new HelpScreen(game));
					return;
				}
				if (Math_Overlap.pointInRectangle(quitButtons, touchPoint)
						&& quitButtonTag == 15) {
					quitButtonTag = 14;
					this.game.exit();
					return;
				}

				this.newGameButtonTag = 4;
				this.optionButtonTag = 8;
				this.aboutButtonTag = 10;
				this.quitButtonTag = 14;

				// 캐릭터가 쫏아 가게 하기 위해서 만듬
				this.click.set(new Math_Vector(touchPoint.x, touchPoint.y));
			}

			if (event.type == TouchEvent.TOUCH_DOWN) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (Math_Overlap.pointInRectangle(newGameButtons, touchPoint)) {

					this.newGameButtonTag = 5;
					return;
				}

				if (Math_Overlap.pointInRectangle(optionsButtons, touchPoint)) {

					this.optionButtonTag = 9;
					return;
				}

				if (Math_Overlap.pointInRectangle(aboutButtons, touchPoint)) {

					this.aboutButtonTag = 11;
					return;
				}

				if (Math_Overlap.pointInRectangle(quitButtons, touchPoint)) {

					this.quitButtonTag = 15;
					return;
				}
			}
		}

		update_girl(deltaTime);

	}

	private void update_girl(float deltaTime) {

		if (Manage_Settings.gravityEnabled) {
			if (game.getInput().getAccelY() > 1.5) {
				girl.setStateFlag(Girl.OBJ_D_RIGHT);
			}
			if (game.getInput().getAccelY() < -1.5) {
				girl.setStateFlag(Girl.OBJ_D_LEFT);
			}
			if (game.getInput().getAccelY() < 1.5
					&& game.getInput().getAccelY() > -1.5)
				girl.setStateFlag(Girl.OBJ_D_STOP);
		} else {
			if (girl.position.x < click.x) {
				girl.setStateFlag(Girl.OBJ_D_RIGHT);
			}

			if (girl.position.x > click.x) {
				girl.setStateFlag(Girl.OBJ_D_LEFT);
			}

			if (girl.position.x < click.x + 5 && girl.position.x > click.x - 5) {
				girl.setStateFlag(Girl.OBJ_D_STOP);
			}
		}
		girl.update(deltaTime);
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Manage_Assets.texture.get(2));
		batcher.drawSprite(GAME_WIDTH / 2, GAME_HEIGHT / 2, GAME_WIDTH,
				GAME_HEIGHT, Manage_Assets.textureRegion.get(2));
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Manage_Assets.texture.get(45));
		batcher.drawSprite(120, 240, 140, 140, Manage_Assets.textureRegion.get(61));
		batcher.endBatch();
		
		batcher.beginBatch(Manage_Assets.texture // quit button 30c
				.get(this.quitButtonTag));
		batcher.drawSprite(330, 64, 200, 50,
				Manage_Assets.textureRegion.get(this.quitButtonTag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture // about button 26c
				.get(this.aboutButtonTag));
		batcher.drawSprite(330, 128, 200, 50,
				Manage_Assets.textureRegion.get(this.aboutButtonTag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture // options button 24c
				.get(this.optionButtonTag));
		batcher.drawSprite(330, 192, 200, 50,
				Manage_Assets.textureRegion.get(this.optionButtonTag));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture // newgame button 20c
				.get(this.newGameButtonTag));
		batcher.drawSprite(330, 256, 200, 50,
				Manage_Assets.textureRegion.get(this.newGameButtonTag));
		batcher.endBatch();

		if (Manage_Settings.autoAttackEnabled) {
			batcher.beginBatch(Manage_Assets.texture // auto_on
					.get(1));
			batcher.drawSprite(60, 80, 40, 40,
					Manage_Assets.textureRegion.get(1));
			batcher.endBatch();
		}
		if (Manage_Settings.gravityEnabled) {
			batcher.beginBatch(Manage_Assets.texture // gravity on
					.get(16));
			batcher.drawSprite(120, 80, 40, 40,
					Manage_Assets.textureRegion.get(16));
			batcher.endBatch();
		}
		if (Manage_Settings.soundEnabled) {
			batcher.beginBatch(Manage_Assets.texture // sound on
					.get(25));
			batcher.drawSprite(180, 80, 40, 40,
					Manage_Assets.textureRegion.get(25));
			batcher.endBatch();
		}
		if (Manage_Settings.vibrationEnabled) {
			batcher.beginBatch(Manage_Assets.texture // vibration on
					.get(31));
			batcher.drawSprite(60, 140, 40, 40,
					Manage_Assets.textureRegion.get(31));
			batcher.endBatch();
		}
		if (Manage_Settings.immortal) {
			batcher.beginBatch(Manage_Assets.texture // vibration on
					.get(44));
			batcher.drawSprite(120, 140, 40, 40,
					Manage_Assets.textureRegion.get(60));
			batcher.endBatch();
		}

		batcher.beginBatch(Manage_Assets.texture // vibration on
				.get(43));
		batcher.drawSprite(180, 140, 40, 40,
				Manage_Assets.textureRegion.get(Manage_Settings.difficult + 55));
		batcher.endBatch();

		batcher.beginBatch(Manage_Assets.texture.get(32));

		GL_TextureRegion keyFrame;
		float girl_Witdh;
		switch (girl.getStageFlag()) {
		case Girl.OBJ_D_LEFT:
			keyFrame = Manage_Assets.animation.get(01).getKeyFrame(
					girl.getStateTime(), GL_Animation.ANIMATION_LOOPING);
			girl_Witdh = Girl.GIRL_WIDTH / 2;
			break;
		case Girl.OBJ_D_RIGHT:
			keyFrame = Manage_Assets.animation.get(00).getKeyFrame(
					girl.getStateTime(), GL_Animation.ANIMATION_LOOPING);
			girl_Witdh = Girl.GIRL_WIDTH / 2;
			break;
		default:
			keyFrame = Manage_Assets.textureRegion.get(girl.getImage_flag());
			girl_Witdh = Girl.GIRL_WIDTH;
		}

		batcher.drawSprite(girl.position.x, girl.position.y, girl_Witdh,
				Girl.GIRL_HEIGHT, keyFrame);

		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		Manage_Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}

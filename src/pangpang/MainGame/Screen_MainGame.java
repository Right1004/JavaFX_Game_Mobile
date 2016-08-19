// 실제 게임 화면(플레이어가 실제 즐길 수 있는 화면)
package pangpang.MainGame;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import jrcengine.Basic.GL_Screen;
import jrcengine.Basic.Log_TimeCounter;
import jrcengine.GL.GL_Animation;
import jrcengine.GL.GL_Camera2D;
import jrcengine.GL.GL_SpriteBatcher;
import jrcengine.GL.GL_TextureRegion;

import jrcengine.Interface.IFace_Game;
import jrcengine.Interface.IFace_Input.TouchEvent;
import jrcengine.Math.Math_Overlap;
import jrcengine.Math.Math_Overlap_Rectangle;
import jrcengine.Math.Math_Vector;
import jrcengine.Manage.Manage_Assets;
import jrcengine.Manage.Manage_Settings;

public class Screen_MainGame extends GL_Screen {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	static int GIRL_TOTAL_LIFE =2;
	static int stage_num;

	private boolean reFreshPosition = true;		//사용자의 처음 x좌표를 기억한다.
	private float init_Accel_X;					//사용자 처음 x 좌표
//	private float init_Accel_Y;					//사용자 처음 y 좌표
//	private float init_Accel_Z;					//사용자 처음 z 좌표
	private final float GRAVITY_MISSILE_GAP = 1.3f;		//미사일 발사를 위한 겝

	int state;

	Log_TimeCounter fpsCounter;
	Math_Overlap_Rectangle pauseBounds;
	Math_Overlap_Rectangle resumeBounds;
	Math_Overlap_Rectangle quitBounds;

	private Map_Controler map_Controler;
	private GL_Camera2D guiCam;
	private GL_SpriteBatcher batcher;
	private MainGame_Manager manage;
	private MainGame_Renderer renderer;
	private int last_Score;
	private String scoreString;
	private String stageString;
	private String stage_Base_String;
	private Math_Vector touchPoint;
	private Math_Vector move_Point;

	
	private final float TOUCH_GAP = 0.3f;
	private float touch_down_new_time;
	private float touch_down_old_time;


	public Screen_MainGame(IFace_Game game) {
		super(game);
		this.state = GAME_READY;

		pauseBounds = new Math_Overlap_Rectangle(480 - 64, 320 - 64, 64, 64);
		resumeBounds = new Math_Overlap_Rectangle(240 - 88, 160, 192, 48);
		quitBounds = new Math_Overlap_Rectangle(240 - 88, 160 - 48, 192, 48);

		stage_num = 1;

		this.guiCam = new GL_Camera2D(glGraphics, Screen_MainMenu.GAME_WIDTH,
				Screen_MainMenu.GAME_HEIGHT);
		this.batcher = new GL_SpriteBatcher(glGraphics, 1000);

		this.fpsCounter = new Log_TimeCounter();
		this.move_Point = new Math_Vector(240, 0);
		this.touchPoint = new Math_Vector();
		this.map_Controler = test_StartTest.map_controler;
		MakeStage(stage_num);

		this.manage = new MainGame_Manager(Screen_MainMenu.GAME_WIDTH,
				Screen_MainMenu.GAME_HEIGHT);
		this.renderer = new MainGame_Renderer(glGraphics, batcher, manage);

		this.touch_down_new_time = 0;
		this.touch_down_old_time = 0;
		this.last_Score = 0;
		scoreString = "score:0";
		stage_Base_String = "stage:";
		stageString = stage_Base_String+stage_num;

	}

	private void MakeStage(int num) {
		map_Controler.readMap(num);

	}

	@Override
	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		update_Contoroller(state, deltaTime);

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateGravitySensor();
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateGravitySensor() {
		if (true == Manage_Settings.gravityEnabled) {
			if (this.init_Accel_X - game.getInput().getAccelX() > GRAVITY_MISSILE_GAP
					&& reFreshPosition) {
				manage.fire_Star_Missile();
				reFreshPosition = false;
			} else if (game.getInput().getAccelX() > this.init_Accel_X) {
				reFreshPosition = true;
			}
		}
	}

	private void update_Contoroller(int state, float deltaTime) {

		if (false == Manage_Settings.gravityEnabled)
			touch_down_new_time += deltaTime;

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();

		for (int i = 0; i < len; i++) {

			TouchEvent event = touchEvents.get(i);

			if (event.type == TouchEvent.TOUCH_DOWN) {

				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (GAME_READY == state)
					if (len > 0) {
						this.state = GAME_RUNNING;
						init_Accel_X = game.getInput().getAccelX();
//						init_Accel_Y = game.getInput().getAccelY();
//						init_Accel_Z = game.getInput().getAccelZ();
						return;
					}

				if (false == Manage_Settings.gravityEnabled) {
					move_Point = touchPoint;

					if (touch_down_new_time - touch_down_old_time < TOUCH_GAP) {
						manage.fire_Star_Missile();
						touch_down_new_time = 0;
					}

					touch_down_old_time = touch_down_new_time;
				}
			}

			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

			}

			if (event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (GAME_OVER == state)
					if (len > 0) {
						game.setScreen(new Screen_MainMenu(game));
						return;
					}

				if (GAME_LEVEL_END == state)
					if (len > 0) {
						
						stage_num += 1;
						MakeStage(stage_num);
						stageString = stage_Base_String+ stage_num;
						manage = new MainGame_Manager(
								Screen_MainMenu.GAME_WIDTH,
								Screen_MainMenu.GAME_HEIGHT);
						renderer = new MainGame_Renderer(glGraphics, batcher,
								manage);

						MainGame_Manager.Score = last_Score;
						this.state = GAME_READY;
					}

				if (GAME_RUNNING == state) {
					if (Math_Overlap.pointInRectangle(pauseBounds, touchPoint)) {
						this.state = GAME_PAUSED;
						return;
					}
				}

				if (GAME_PAUSED == state) {
					if (Math_Overlap.pointInRectangle(resumeBounds, touchPoint)) {
						this.state = GAME_RUNNING;
						return;
					}

					if (Math_Overlap.pointInRectangle(quitBounds, touchPoint)) {
						if (MainGame_Manager.mBoss != null)
							MainGame_Manager.mBoss = null;
						MainGame_Manager.is_Double_Attack = false;
						MainGame_Manager.is_Power_Attack = false;
						MainGame_Manager.GIRL_LIFE_NUMBER = Screen_MainGame.GIRL_TOTAL_LIFE;
						MainGame_Manager.GIRL_SHIELD_NUMBER =3;
						
						game.setScreen(new Screen_MainMenu(game));
						return;

					}
				}

				if (Screen_MainMenu.is_Debug)
					manage.check_Enemy_Status(touchPoint.x, touchPoint.y);

			}
		}
	}

	private void updateReady() {

	}

	private void updateRunning(float deltaTime) {

		manage.update(renderer, deltaTime, game.getInput().getAccelX(), game
				.getInput().getAccelY(), move_Point.x, move_Point.y);
		if (MainGame_Manager.Score != this.last_Score) {
			this.last_Score = MainGame_Manager.Score;
			scoreString = "score:" + this.last_Score;
		}
		if (manage.getState() == MainGame_Manager.MANAGE_STATE_NEXT_LEVEL) {
			state = GAME_LEVEL_END;
		}
		if (manage.getState() == MainGame_Manager.MANAGE_STATE_GAME_OVER) {
			state = GAME_OVER;
			if (this.last_Score >= Manage_Settings.highscores[4])
				scoreString = "new highscore:" + last_Score;
			else
				scoreString = "score:" + last_Score;
			Manage_Settings.addScore(last_Score);
			Manage_Settings.save(game.getFileIO());
		}

	}

	private void updatePaused() {

	}

	private void updateGameOver() {
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		renderer.render(deltaTime, state);

		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Manage_Assets.items);

		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}

		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_BLEND);
		if (Screen_MainMenu.is_Debug)
			fpsCounter.logFrame();
	}

	private void presentReady() {
		batcher.drawSprite(240, 160, 192, 32, Manage_Assets.ready);
	}

	private void presentRunning() {
		batcher.drawSprite(480 - 32, 320 - 32, 64, 64, Manage_Assets.pause);
		Manage_Assets.font.drawText(batcher, scoreString, 16, 320 - 40);

		for (int i = 0; i < MainGame_Manager.mGirl.get_Int_Shield(); i++) {
			GL_TextureRegion keyFrame = Manage_Assets.coinAnim.getKeyFrame(
					MainGame_Manager.mGirl.getStateTime(),
					GL_Animation.ANIMATION_LOOPING);
			batcher.drawSprite(260 + 15 * i, 320 - 20, 20, 20, keyFrame);
		}
		batcher.endBatch();
		if (MainGame_Manager.mGirl.get_Int_Life() > 0) {
			batcher.beginBatch(Manage_Assets.texture.get(23));
			for (int i = MainGame_Manager.mGirl.get_Int_Life(); i > 0; i--)
				batcher.drawSprite(160 + 20 * i, 320 - 20, 20, 20,
						Manage_Assets.textureRegion.get(23));
			batcher.endBatch();
		}

		batcher.beginBatch(Manage_Assets.items);
		Manage_Assets.font.drawText(batcher, stageString, 315, 320 - 40);
	}

	private void presentPaused() {
		batcher.drawSprite(240, 160, 192, 96, Manage_Assets.pauseMenu);
		Manage_Assets.font.drawText(batcher, scoreString, 16, 320 - 20);
	}

	private void presentLevelEnd() {

		String str = "stage clear";
		float strWidth = Manage_Assets.font.glyphWidth * str.length();
		Manage_Assets.font.drawText(batcher, str, 240 - strWidth / 2, 160);
	}

	private void presentGameOver() {
		batcher.drawSprite(240, 160, 160, 96, Manage_Assets.gameOver);
		float scoreWidth = Manage_Assets.font.glyphWidth * scoreString.length();
		Manage_Assets.font.drawText(batcher, scoreString, 240 - scoreWidth / 2,
				320 - 110);
	}

	@Override
	public void pause() {
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
package pangpang.MainGame;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;
import jrcengine.Basic.GL_Graphics;
import jrcengine.GL.GL_Animation;
import jrcengine.GL.GL_Camera2D;
import jrcengine.GL.GL_SpriteBatcher;
import jrcengine.GL.GL_TextureRegion;
import jrcengine.Manage.*;

public class MainGame_Renderer {

	private GL_Graphics glGraphics;
	private MainGame_Manager manage;
	private GL_Camera2D game_cam;
	private GL_SpriteBatcher batcher;

	private Random rnd;

	private int[] backGroundNum;
	private int choicBackGroundNum;

	static float UnDeadCurTime;
	static boolean unDeadOnUser;

	public MainGame_Renderer(GL_Graphics glGraphics, GL_SpriteBatcher batcher,
			MainGame_Manager manage) {

		rnd = new Random();
		backGroundNum = new int[] { 51, 52, 3 };
		choicBackGroundNum = rnd.nextInt(3);
		UnDeadCurTime = 0;
		unDeadOnUser = true;
		this.glGraphics = glGraphics;
		this.manage = manage;
		this.game_cam = new GL_Camera2D(glGraphics, manage.get_World_Width(),
				manage.get_World_Height());
		this.batcher = batcher;

	}

	public void render(float deltaTime, int state) {
		game_cam.setViewportAndMatrices();
		renderBackground();
		if (state != Screen_MainGame.GAME_READY)
			renderObjects(deltaTime);
	}

	private void renderBackground() {
		batcher.beginBatch(Manage_Assets.texture.get(3));
		batcher.drawSprite(game_cam.position.x, game_cam.position.y, manage
				.get_World_Width(), manage.get_World_Height(),
				Manage_Assets.textureRegion
						.get(backGroundNum[choicBackGroundNum]));
		batcher.endBatch();
	}

	private void renderObjects(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		if (MainGame_Manager.mExplosion.size() > 0
				|| test_StartTest.map_controler.get_Enemy_live_Count() > 0) {
			batcher.beginBatch(Manage_Assets.texture.get(39));
			render_Explosion_Effect();
			render_Enemys();
			batcher.endBatch();
		}
		
		if (MainGame_Manager.mStarMis.size() > 0
				|| MainGame_Manager.mBossStarMis.size() > 0) {
			batcher.beginBatch(Manage_Assets.texture.get(41));
			render_Boss_Star_Mis();
			render_Star_Mis();
			batcher.endBatch();
		}
		
		if (MainGame_Manager.mItem.size() > 0) {
			batcher.beginBatch(Manage_Assets.texture.get(46));
			render_Item();
			batcher.endBatch();
		}

		if (MainGame_Manager.mBubbleMis.size() > 0) {
			batcher.beginBatch(Manage_Assets.texture.get(40));
			render_Bubble_Mis();
			batcher.endBatch();
		}

		render_User(deltaTime);
		render_Boss();

		gl.glDisable(GL10.GL_BLEND);

	}
	
	private void render_Item() {
		
		int len = MainGame_Manager.mItem.size();
		for (int i = 0; i < len; i++) {
			Item item = MainGame_Manager.mItem.get(i);

			batcher.drawSprite(item.position.x, item.position.y,
					Item.ITEM_WIDTH,
					Item.ITEM_HEIHGT,
					(float) (item.get_Rotate_Number() * -22.5),
					Manage_Assets.textureRegion.get(item.get_Image_Number()));
		}	
	}

	private void render_Boss() {
		if (null == MainGame_Manager.mBoss)
			return;

		batcher.beginBatch(Manage_Assets.texture.get(42));
		batcher.drawSprite(MainGame_Manager.mBoss.position.x,
				MainGame_Manager.mBoss.position.y, Boss.BOSS_WIDTH,
				Boss.BOSS_HEIHGT,
				(float) 22.5 * MainGame_Manager.mBoss.get_Rotate_Number(),
				Manage_Assets.textureRegion.get(MainGame_Manager.mBoss
						.getImage_flag()));
		batcher.endBatch();
	}

	private void render_Explosion_Effect() {
		int len = MainGame_Manager.mExplosion.size();
		for (int i = 0; i < len; i++) {
			Explosion_Effect effect = MainGame_Manager.mExplosion.get(i);

			batcher.drawSprite(effect.position.x, effect.position.y,
					effect.get_Width(), effect.get_Width(),
					(float) (effect.get_Direction() * -22.5),
					Manage_Assets.textureRegion.get(effect.get_Image_Number()));
		}
	}

	private void render_Boss_Star_Mis() {

		int len = MainGame_Manager.mBossStarMis.size();
		for (int i = 0; i < len; i++) {
			Boss_Star_Missile mMissle = MainGame_Manager.mBossStarMis.get(i);

			batcher.drawSprite(mMissle.position.x, mMissle.position.y,
					Boss_Star_Missile.Boss_Star_Missile_WIDTH,
					Boss_Star_Missile.Boss_Star_Missile_HEIHGT,
					(float) (mMissle.get_Rotate_Number() * -22.5),
					Manage_Assets.textureRegion.get(mMissle.get_Image_Number()));
		}
	}

	private void render_Star_Mis() {

		int len = MainGame_Manager.mStarMis.size();
		for (int i = 0; i < len; i++) {
			Star_Missile mMissle = MainGame_Manager.mStarMis.get(i);

			batcher.drawSprite(mMissle.position.x, mMissle.position.y,
					Star_Missile.Star_Missile_WIDTH,
					Star_Missile.Star_Missile_HEIHGT,
					(float) (mMissle.get_Rotate_Number() * -22.5),
					Manage_Assets.textureRegion.get(mMissle.get_Image_Number()));
		}
	}

	private void render_User(float deltaTime) {

		GL_TextureRegion keyFrame;
		float girl_Witdh;
		switch (MainGame_Manager.mGirl.getStageFlag()) {
		case Girl.OBJ_D_LEFT:
			keyFrame = Manage_Assets.animation.get(01).getKeyFrame(
					MainGame_Manager.mGirl.getStateTime(),
					GL_Animation.ANIMATION_LOOPING);
			girl_Witdh = Girl.GIRL_WIDTH / 2;
			break;
		case Girl.OBJ_D_RIGHT:
			keyFrame = Manage_Assets.animation.get(00).getKeyFrame(
					MainGame_Manager.mGirl.getStateTime(),
					GL_Animation.ANIMATION_LOOPING);
			girl_Witdh = Girl.GIRL_WIDTH / 2;
			break;
		case Girl.OBJ_D_ATTAK:
			keyFrame = Manage_Assets.textureRegion.get(MainGame_Manager.mGirl
					.getImage_flag());
			girl_Witdh = Girl.GIRL_WIDTH;
			break;
		default:
			keyFrame = Manage_Assets.textureRegion.get(53);
			girl_Witdh = Girl.GIRL_WIDTH;
		}
		UnDeadCurTime += deltaTime;

		if (MainGame_Manager.mGirl.get_Is_Un_Dead() && UnDeadCurTime > 0.2) {
			UnDeadCurTime = 0;
			unDeadOnUser = !unDeadOnUser;
		}

		if (unDeadOnUser) {
			if (!MainGame_Manager.mGirl.get_Is_Dead()) {
				batcher.beginBatch(Manage_Assets.texture.get(32));
				batcher.drawSprite(MainGame_Manager.mGirl.position.x,
						MainGame_Manager.mGirl.position.y, girl_Witdh,
						Girl.GIRL_HEIGHT, keyFrame);
				batcher.endBatch();
			}
		}

	}

	private void render_Enemys() {

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (!MainGame_Manager.mEnemy[i][j].get_Is_Dead()) {
					batcher.drawSprite(
							MainGame_Manager.mEnemy[i][j].position.x,
							MainGame_Manager.mEnemy[i][j].position.y,
							Enemy.ENEMY_WIDTH,
							Enemy.ENEMY_HEIGHT,
							(float) (MainGame_Manager.mEnemy[i][j].get_Dir() * -22.5),
							Manage_Assets.textureRegion
									.get(MainGame_Manager.mEnemy[i][j]
											.get_Chracter_Number() + 39));
				}
			}
		}
	}

	private void render_Bubble_Mis() {

		int len = MainGame_Manager.mBubbleMis.size();
		for (int i = 0; i < len; i++) {
			Bubble_Missile mMissle = MainGame_Manager.mBubbleMis.get(i);

			batcher.drawSprite(mMissle.position.x, mMissle.position.y,
					Bubble_Missile.BUBLLE_MISSILE_WIDTH,
					Bubble_Missile.BUBLLE_MISSILE_HEIHGT,
					(float) (mMissle.get_Directions() * -22.5),
					Manage_Assets.textureRegion.get(mMissle.get_Image_Number()));
		}

	}

	public GL_Camera2D get_Game_Cam() {
		return this.game_cam;
	}

}
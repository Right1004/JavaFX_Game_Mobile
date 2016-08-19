// 각 객체의 여러 인스턴스를 추적하고
// 매 프레임마다 객체를 업데이트 하며 객체와 Bob 사이의 충돌을 검사하고 반응을 수행
package pangpang.MainGame;

import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import jrcengine.Interface.Screen_Manager;
import jrcengine.Manage.Manage_Settings;
import jrcengine.Math.Math_Overlap;
import jrcengine.Math.Math_Vector;

public class MainGame_Manager extends Screen_Manager {

	public static final int MANAGE_STATE_RUNNING = 0;
	public static final int MANAGE_STATE_WAIT_TIME = 1;
	public static final int MANAGE_STATE_NEXT_LEVEL = 2;
	public static final int MANAGE_STATE_GAME_OVER = 3;

	private int bossDelay = 3; // 보스가 출현하는 탄 간격

	private String st_name[] = { "ENTER", "BEGINPOS", "POSITION", "SYNC",
			"ATTACK", "BEGINBACK", "BACKPOS" };

	private final int world_Width;
	private final int world_Height;

	static int GIRL_LIFE_NUMBER = Screen_MainGame.GIRL_TOTAL_LIFE;
	static int GIRL_SHIELD_NUMBER =3;
	static int star_Missile_Delay = 60; // 총 쏠수있는 딜레이
	static float stand_Attack_Statue_Girl = 0.5f;
	
	private int start_Missile_loop;
	private Vibrator vibe;
	private boolean isBossStage;

	private Random rnd;
	private int state;
	private boolean gunShut; // 총을 쐈는지 안쐈는지 해석.
	private float gunShut_delay; // 총쏘고 모션대기.
	private int endDir; // 끝나고 움직이는 방향

	AttackEnemy mAttack; // 적군 공격 Class

	static int Score;
	static boolean is_Power_Attack = false;
	static boolean is_Double_Attack = false;
	static boolean is_Attac_Delay = false;

	static Girl mGirl;
	static Boss mBoss;
	static ArrayList<Star_Missile> mStarMis;
	static ArrayList<Bubble_Missile> mBubbleMis;
	static ArrayList<Boss_Star_Missile> mBossStarMis;
	static ArrayList<Explosion_Effect> mExplosion;
	static ArrayList<Item> mItem;
	static Enemy[][] mEnemy;

	public MainGame_Manager(int world_Width, int world_Height) {
		
		this.gunShut_delay = 0;
		this.gunShut = false;
		this.world_Width = world_Width;
		this.world_Height = world_Height;
		this.vibe = (Vibrator) test_StartTest.map_controler.get_Contents()
				.getSystemService(Context.VIBRATOR_SERVICE);
		this.state = MANAGE_STATE_RUNNING;
		this.isBossStage = false;
		// this.state = Screen_MainGame.GAME_RUNNING;
		this.rnd = new Random();
		this.endDir = (rnd.nextInt(2) == 1) ? 1 : -1;
		this.start_Missile_loop = 0;
		Score = 0;
		this.mAttack = new AttackEnemy(); // 적군 공격 Class
		mEnemy = new Enemy[6][8];
		mExplosion = new ArrayList<Explosion_Effect>();
		mBubbleMis = new ArrayList<Bubble_Missile>();
		mStarMis = new ArrayList<Star_Missile>();
		mBossStarMis = new ArrayList<Boss_Star_Missile>();
		mItem = new ArrayList<Item>();
		mGirl = new Girl(32, 240, Girl.GIRL_HEIGHT / 2, GIRL_LIFE_NUMBER, GIRL_SHIELD_NUMBER);

		generate();

	}

	// 게임 세계의 생성
	@Override
	protected void generate() {

		mAttack.ResetAttack();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				mEnemy[i][j] = new Enemy();
				mEnemy[i][j].MakeEnemy(i, j);
			}// for i
		}// for j

	}

	public void update(MainGame_Renderer rander, float deltaTime,
			float accel_X, float accel_Y, float click_X, float click_Y) {

		if (MANAGE_STATE_RUNNING == state) {
			if (Manage_Settings.autoAttackEnabled == true)
				fire_Star_Missile();
			update_checkCollisions(deltaTime);
		}
		update_Objections(deltaTime, accel_X, accel_Y, click_X, click_Y);
		Update_Check_Next_Stage(deltaTime);
		Update_Check_Game_Over();
	}

	private void update_Objections(float deltaTime, float accel_X,
			float accel_Y, float click_X, float click_Y) {

		start_Missile_loop++;

		update_Boss(deltaTime);
		if (MANAGE_STATE_RUNNING == state) {
			update_Girl(deltaTime, accel_X, accel_Y, click_X);
		}
		update_Explosion(deltaTime);
		update_Enemy(deltaTime);
		update_Bubble_Missile(deltaTime);
		update_Boss_Star_Missile(deltaTime);
		update_Star_Missile(deltaTime);
		update_Item(deltaTime);
		update_Attack_Sprite(deltaTime);
	}

	private void update_Item(float deltaTime) {

		int len = mItem.size();

		if (len < 0)
			return;

		for (int i = 0; i < len; i++) {
			Item item = mItem.get(i);
			item.update(deltaTime);

			if (item.get_Out_Bound()) {
				mItem.remove(item);
				len = mItem.size();
			}
		}
	}

	private void update_Explosion(float deltaTime) {
		int len = mExplosion.size();

		if (len < 0)
			return;

		for (int i = 0; i < len; i++) {
			Explosion_Effect expl = mExplosion.get(i);
			expl.update(deltaTime);

			if (expl.get_Over_Time()) {
				mExplosion.remove(expl);
				len = mExplosion.size();
			}
		}
	}

	private void update_Boss_Star_Missile(float deltaTime) {

		int len = mBossStarMis.size();

		if (len <= 0)
			return;

		for (int i = 0; i < len; i++) {
			Boss_Star_Missile mstar = mBossStarMis.get(i);
			mstar.update(deltaTime);

			if (mstar.get_Out_Bound() || mstar.get_Is_Dead()) {
				mBossStarMis.remove(mstar);
				len = mBossStarMis.size();
			}

		}
	}

	private void update_Star_Missile(float deltaTime) {

		int len = mStarMis.size();

		if (len <= 0)
			return;

		for (int i = 0; i < len; i++) {
			Star_Missile mstar = mStarMis.get(i);
			mstar.update(deltaTime);

			if (mstar.get_Out_Bound() || mstar.get_Is_Dead()) {
				mStarMis.remove(mstar);
				len = mStarMis.size();
			}

		}
	}

	public void check_Enemy_Status(float click_X, float click_Y) {

		float x, y, sh, st;

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (Math_Overlap.pointInRectangle(mEnemy[i][j].bounds,
						new Math_Vector(click_X, click_Y))) {
					if (mEnemy[i][j].get_Is_Dead())
						continue;
					x = mEnemy[i][j].position.x;
					y = mEnemy[i][j].position.y;
					sh = mEnemy[i][j].get_Shield();
					st = mEnemy[i][j].get_Statue();

					Log.e("Enemy Id.[" + i + "]" + "[" + j + "]" + ", X좌표 : "
							+ x + " " + ", Y좌표 : " + y,
							"statue . shield : "
									+ sh
									+ ", Statue : "
									+ st_name[(int) st - 1]
									+ ", Total_Enemy : "
									+ test_StartTest.map_controler
											.get_Enemy_live_Count());
					break;
				}// if
			}// j
		}// i

	}

	public void fire_Star_Missile() {
		if (start_Missile_loop < star_Missile_Delay
				|| (mGirl.get_Is_Dead() == true))
			return;

		if (is_Double_Attack) {
			mStarMis.add(new Star_Missile(mGirl.position.x - 10,
					mGirl.position.y + Girl.GIRL_HEIGHT / 2
							+ Star_Missile.Star_Missile_HEIHGT / 2));
			mStarMis.add(new Star_Missile(mGirl.position.x + 10,
					mGirl.position.y + Girl.GIRL_HEIGHT / 2
							+ Star_Missile.Star_Missile_HEIHGT / 2));
		} else {
			mStarMis.add(new Star_Missile(mGirl.position.x, mGirl.position.y
					+ Girl.GIRL_HEIGHT / 2 + Star_Missile.Star_Missile_HEIHGT
					/ 2));
		}

		if (!is_Attac_Delay)
			gunShut = true;

		start_Missile_loop = 0;
	}

	private void update_Boss(float deltaTime) {
		if (mBoss != null)
			mBoss.update(deltaTime);
	}

	private void update_Girl(float deltaTime, float accel_X, float accel_Y,
			float click_X) {

		if (mGirl.get_Is_Dead() != true) {
			if (Manage_Settings.gravityEnabled) {
				if (accel_Y > 1.5) {
					mGirl.setStateFlag(Girl.OBJ_D_RIGHT);
				}
				if (accel_Y < -1.5) {
					mGirl.setStateFlag(Girl.OBJ_D_LEFT);
				}
				if (accel_Y < 1.5 && accel_Y > -1.5)
					mGirl.setStateFlag(Girl.OBJ_D_STOP);
			} else {
				if (mGirl.position.x < click_X) {
					mGirl.setStateFlag(Girl.OBJ_D_RIGHT);
				}

				if (mGirl.position.x > click_X) {
					mGirl.setStateFlag(Girl.OBJ_D_LEFT);
				}

				if (mGirl.position.x < click_X + 5
						&& mGirl.position.x > click_X - 5) {
					mGirl.setStateFlag(Girl.OBJ_D_STOP);
				}
			}

			if (gunShut)
				mGirl.setStateFlag(Girl.OBJ_D_ATTAK);

			gunShut_delay += deltaTime;
			mGirl.update(deltaTime);

			if (gunShut_delay > stand_Attack_Statue_Girl) {
				gunShut = false;
				gunShut_delay = 0;
			}
		}
	}

	private void update_Bubble_Missile(float deltaTime) {
		int len = mBubbleMis.size();

		if (len <= 0)
			return;

		for (int i = 0; i < len; i++) {
			Bubble_Missile mbubble = mBubbleMis.get(i);
			mbubble.update(deltaTime);

			if (mbubble.get_Out_Bound() || mbubble.get_Is_Dead()) {
				mBubbleMis.remove(mbubble);
				len = mBubbleMis.size();
			}

		}

	}

	private void update_Attack_Sprite(float deltaTime) {
		mAttack.Attack();
	}

	private void update_Enemy(float deltaTime) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				mEnemy[i][j].update(deltaTime);
			}// for i
		}// for j
	}

	// 충돌 검사 메서드를 모두 호출
	@Override
	protected void update_checkCollisions(float deltaTime) {

		check_Star_Mi_To_Enemy(deltaTime);
		check_Bubble_Mi_To_Girl(deltaTime);
		check_Girl_To_Enemy(deltaTime);
		check_Star_Mi_To_Explo(deltaTime);
		check_Boss_Star_Mi_To_Girl(deltaTime);
		check_Star_Mi_To_Boss(deltaTime);
		check_Item_To_Girl(deltaTime);

		// if(false)보스전을 위해 남겨둔것
	}

	private void check_Item_To_Girl(float deltaTime) {

		int len = mItem.size();

		if (len < 0 || mGirl.get_Is_Dead())
			return;

		for (int i = 0; i < len; i++) {
			Item item = mItem.get(i);
			if (Math_Overlap.overlapRectangles(item.bounds, mGirl.bounds)) {
				item.crash_Girl();
				mItem.remove(item);
				len = mItem.size();

			}
		}

	}

	private void check_Star_Mi_To_Boss(float deltaTime) {
		int len = mStarMis.size();

		if (len < 0 || mBoss == null)
			return;
		for (int i = 0; i < len; i++) {

			Star_Missile missile = mStarMis.get(i);
			if (Math_Overlap.overlapRectangles(missile.bounds, mBoss.bounds)) {
				mStarMis.remove(missile);
				len = mStarMis.size();

				mBoss.crash_Star_Missile();

				if (mBoss.get_Is_Dead()) {
					mBoss = null;
					return;
				}

			}
		}

	}

	private void check_Boss_Star_Mi_To_Girl(float deltaTime) {
		int len = mBossStarMis.size();

		if (len < 0 || mGirl.get_Is_Dead())
			return;
		for (int i = 0; i < len; i++) {
			Boss_Star_Missile missile = mBossStarMis.get(i);
			if (Math_Overlap.overlapRectangles(missile.bounds, mGirl.bounds)) {
				mBossStarMis.remove(missile);
				len = mBossStarMis.size();

				if (!mGirl.get_Is_Un_Dead() && !Manage_Settings.immortal) {
					mGirl.cresh_Boss_Star_Missile();
					if (Manage_Settings.vibrationEnabled)
						vibe.vibrate(400);
				}
			}
		}
	}

	private void check_Star_Mi_To_Explo(float deltaTime) {
		int len00 = mStarMis.size(); // 주인공 미사일
		int len01 = mExplosion.size(); // 폭발 잔해
		if (len00 < 0 || len01 < 0)
			return;

		for (int i = 0; i < len00; i++) {
			Star_Missile strMis = mStarMis.get(i);
			for (int k = 0; k < len01; k++) {
				Explosion_Effect effect = mExplosion.get(k);

				if (Math_Overlap
						.overlapRectangles(effect.bounds, strMis.bounds)) {
					effect.crash_Star_Missile();

					mExplosion.remove(effect);
					len01 = mExplosion.size();

				}
			}
		}

	}

	private void check_Bubble_Mi_To_Girl(float deltaTime) {
		int len = mBubbleMis.size();

		if (len < 0 || mGirl.get_Is_Dead())
			return;
		for (int i = 0; i < len; i++) {
			Bubble_Missile missile = mBubbleMis.get(i);
			if (Math_Overlap.overlapRectangles(missile.bounds, mGirl.bounds)) {
				mBubbleMis.remove(missile);
				len = mBubbleMis.size();

				if (!mGirl.get_Is_Un_Dead() && !Manage_Settings.immortal) {
					mGirl.cresh_Bubble_Missile();
					if (Manage_Settings.vibrationEnabled)
						vibe.vibrate(200);
				}
			}
		}

	}

	private void check_Girl_To_Enemy(float deltaTime) {
		int len = test_StartTest.map_controler.get_Enemy_live_Count();
		if (len < 1 || mGirl.get_Is_Dead())
			return;

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (true == mEnemy[i][j].get_Is_Dead()
						|| mGirl.get_Is_Un_Dead())
					continue;
				if (Math_Overlap.overlapRectangles(mEnemy[i][j].bounds,
						mGirl.bounds)) {
					mEnemy[i][j].crash_Girl();

					if (!Manage_Settings.immortal) {
						mGirl.cresh_Enemy();
						if (Manage_Settings.vibrationEnabled)
							vibe.vibrate(400);
					}
				}

			}
		}

	}

	private void check_Star_Mi_To_Enemy(float deltaTime) {
		int len = mStarMis.size();

		if (len < 0)
			return;
		for (int i = 0; i < len; i++) {
			Star_Missile strMis = mStarMis.get(i);
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 8; k++) {
					if (true == mEnemy[j][k].get_Is_Dead())
						continue;
					if (Math_Overlap.overlapRectangles(mEnemy[j][k].bounds,
							strMis.bounds)) {
						mEnemy[j][k].crash_Star_Missile();

						mStarMis.remove(strMis);
						len = mStarMis.size();
					}
				}
			}
		}
	}
	
	// 모든 적들을 없에면 다음 스테이지로 넘어간다.
	private void Update_Check_Next_Stage(float deltaTime) {
		// 보스 검사도 여기서 처리한다.
		if ((test_StartTest.map_controler.get_Enemy_live_Count() <= 0)
				&& (Screen_MainGame.stage_num % bossDelay > 0)
				&& (mBoss == null)) {
			state = MANAGE_STATE_WAIT_TIME;
			mGirl.clear_Game_Update(deltaTime, endDir);

			if (mGirl.position.x > Screen_MainMenu.GAME_WIDTH + 20
					|| mGirl.position.x < -20)
			{
				state = MANAGE_STATE_NEXT_LEVEL;
				
				GIRL_LIFE_NUMBER = mGirl.get_Int_Life();
				GIRL_SHIELD_NUMBER = mGirl.get_Int_Shield();
				
			}
		}

		else if ((test_StartTest.map_controler.get_Enemy_live_Count() <= 0)
				&& (Screen_MainGame.stage_num % bossDelay == 0)
				&& (null == mBoss) && (false == isBossStage)) {

			int enemyNumber = 0;

			this.isBossStage = true;
			mBoss = new Boss(Screen_MainMenu.GAME_WIDTH / 2,
					Screen_MainMenu.GAME_HEIGHT + 70);

			for (int i = 2; i <= 4; i++) {
				for (int j = 0; j < 8; j++) {
					if (test_StartTest.map_controler.get_Selection(i, j) != -1) {

						mEnemy[i][j].ResetEnemy();
						enemyNumber++;
					}
				}
			}

			test_StartTest.map_controler.set_Enemy_Cnt(enemyNumber);

		}

		else if ((test_StartTest.map_controler.get_Enemy_live_Count() <= 0)
				&& (Screen_MainGame.stage_num % bossDelay == 0)
				&& (null == mBoss) && (true == isBossStage)) {
			state = MANAGE_STATE_WAIT_TIME;
			mGirl.clear_Game_Update(deltaTime, endDir);

			if (mGirl.position.x > Screen_MainMenu.GAME_WIDTH + 20
					|| mGirl.position.x < -20)
			{
				state = MANAGE_STATE_GAME_OVER;
				mGirl.item_Reset();
				GIRL_LIFE_NUMBER = Screen_MainGame.GIRL_TOTAL_LIFE;
				GIRL_SHIELD_NUMBER = 3;
			}
		}
	}

	// 플레이어의 목숨이 0이되면 게임은 종료 되고 그 최고 값은 배열에 저장되어 파일로 이동된다.
	private void Update_Check_Game_Over() {

		if (mGirl.get_Int_Life() < 0) {
			state = MANAGE_STATE_GAME_OVER;
			mGirl.item_Reset();
			GIRL_LIFE_NUMBER = Screen_MainGame.GIRL_TOTAL_LIFE;
			GIRL_SHIELD_NUMBER = 3;
		}
	}

	public int getState() {
		return this.state;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	public int get_World_Width() {
		return this.world_Width;
	}

	public int get_World_Height() {
		return this.world_Height;
	}

}
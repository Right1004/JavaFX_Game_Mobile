package pangpang.MainGame;

import java.util.Random;
import jrcengine.Interface.Object_Dynamic;
import jrcengine.Manage.Manage_Settings;
import jrcengine.Math.Math_Vector;

public class Boss extends Object_Dynamic {

	public static final float BOSS_MOVE_VELOCITY = 60;
	public static final float BOSS_WIDTH = 60;
	public static final float BOSS_HEIHGT = 60;

	private final int SCOPEWIDTH = 100; // 보스가 왔다 갔다 하는 거리. 클수록 좁아짐.
	private float bossShutSpeed[] = { 1.3f, 1.0f, 0.7f, 0.2f };
	private int bossShield[] = { 40, 80, 160, 320 };
	private boolean isDead;
	private int boss_Move_Direction;
	private int attack_Direction; // 5~11
	private int shield;
	private Random rnd;
	private int rotateNumber;
	private float rotateTime;

	public Boss(float x, float y) {
		super(x, y, BOSS_WIDTH, BOSS_HEIHGT);

		this.rotateTime = 0;
		this.rnd = new Random();
		this.shield = bossShield[Manage_Settings.difficult];
		this.isDead = false;
		this.velocity.set(new Math_Vector(0, -BOSS_MOVE_VELOCITY));
		this.boss_Move_Direction = 0;
		this.rotateNumber = 0;

	}

	public void update(float deltaTime) {

		move();
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		stateTime += deltaTime;
		rotateTime += deltaTime;
	}

	private void move() {
		if (position.y < Screen_MainMenu.GAME_HEIGHT - 40) {
			if (boss_Move_Direction == 0)
				boss_Move_Direction = 1;

			velocity.set(new Math_Vector(BOSS_MOVE_VELOCITY
					* boss_Move_Direction, 0));
		}

		if ((position.x < SCOPEWIDTH && boss_Move_Direction < 0)
				|| (position.x > Screen_MainMenu.GAME_WIDTH - SCOPEWIDTH && boss_Move_Direction > 0)) {

			boss_Move_Direction = -boss_Move_Direction;

			velocity.set(new Math_Vector(BOSS_MOVE_VELOCITY
					* boss_Move_Direction, 0));
		}

		if (rotateTime > 0.1) {
			rotateNumber++;
			rotateTime = 0;
		}
		if (rotateNumber > 15)
			rotateNumber = 0;

		if (stateTime > bossShutSpeed[Manage_Settings.difficult]) {
			attack_Direction = rnd.nextInt(2) + 5;
			MainGame_Manager.mBossStarMis.add(new Boss_Star_Missile(position.x,
					position.y, attack_Direction));

			attack_Direction = rnd.nextInt(2) + 7;
			MainGame_Manager.mBossStarMis.add(new Boss_Star_Missile(position.x,
					position.y, attack_Direction));

			attack_Direction = rnd.nextInt(3) + 9;
			MainGame_Manager.mBossStarMis.add(new Boss_Star_Missile(position.x,
					position.y, attack_Direction));
			stateTime = 0;
		}

	}

	public boolean get_Is_Dead() {
		return this.isDead;
	}

	public int get_sheild() {
		return this.shield;
	}

	int get_Rotate_Number() {
		return this.rotateNumber;
	}

	public void crash_Star_Missile() {

		if (MainGame_Manager.is_Power_Attack)
			shield -= 4;
		else
			shield -= 1;

		if (shield > 0) {
			MainGame_Manager.mExplosion.add(new Explosion_Effect(position.x,
					position.y, rnd.nextInt(16)));
			MainGame_Manager.Score += rnd.nextInt(50) + 100;
		} else {
			int k = rnd.nextInt(5) + 10;
			isDead = true;

			for (int i = 0; i < k; i++)
				MainGame_Manager.mExplosion.add(new Explosion_Effect(
						position.x, position.y, rnd.nextInt(16)));
			MainGame_Manager.Score += (rnd.nextInt(500) + 3000);
			clearAllEnemies();
		}

	}

	private void clearAllEnemies() {
		int len;
		len = MainGame_Manager.mBossStarMis.size();

		for (int i = 0; i < len; i++) {
			Boss_Star_Missile missile = MainGame_Manager.mBossStarMis.get(i);

			int k = rnd.nextInt(3) + 3;
			for (int j = 0; j < k; j++)
				MainGame_Manager.mExplosion
						.add(new Explosion_Effect(missile.position.x,
								missile.position.y, rnd.nextInt(16)));

		}
		MainGame_Manager.mBossStarMis.clear();

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				Enemy enemy = MainGame_Manager.mEnemy[i][j];

				if (enemy.get_Is_Dead() != true) {
					enemy.boss_Die();
				}
			}
		}

	}
}
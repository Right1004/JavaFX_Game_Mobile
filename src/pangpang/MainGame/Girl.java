package pangpang.MainGame;

import jrcengine.Interface.Object_Dynamic;
import jrcengine.Math.Math_Vector;

public class Girl extends Object_Dynamic {

	public static final float GIRL_MOVE_VELOCITY = 40;
	public static final float GIRL_WIDTH = 30f;
	public static final float GIRL_HEIGHT = 30f;

	private int life;
	private int shield;
	private boolean is_Dead;
	private boolean is_Un_Dead;
	private int Un_Dead_Cult;

	public Girl(int flag, float x, float y, int life, int shield) {
		super(flag, x, y, GIRL_WIDTH, GIRL_HEIGHT);

		this.shield = shield;
		this.life = life;
		reset(x, y);
	}

	public void reset(float x, float y) {
		position.set(new Math_Vector(x, y));
		state_Flag = OBJ_D_STOP;
		MainGame_Manager.stand_Attack_Statue_Girl = 0.5f;
		MainGame_Manager.star_Missile_Delay = 60;
		is_Dead = false;
		Un_Dead_Cult = 500;
		is_Un_Dead = true;

	}
	
	public void item_Reset()
	{
		MainGame_Manager.is_Double_Attack = false;
		MainGame_Manager.is_Power_Attack = false;
	}

	public void update(float deltaTime) {

		if (is_Un_Dead) {
			Un_Dead_Cult--;

			if (Un_Dead_Cult < 0) {
				is_Un_Dead = false;
				MainGame_Renderer.UnDeadCurTime = 0;
				MainGame_Renderer.unDeadOnUser = true;
			}
		}

		if (state_Flag == OBJ_D_RIGHT
				&& position.x <= Screen_MainMenu.GAME_WIDTH - GIRL_HEIGHT / 2)
			velocity.set(GIRL_MOVE_VELOCITY, 0);
		else if (state_Flag == OBJ_D_LEFT && position.x >= GIRL_WIDTH / 2)
			velocity.set(-GIRL_MOVE_VELOCITY, 0);
		else if (state_Flag == OBJ_D_STOP || state_Flag == OBJ_D_ATTAK
				|| position.x > Screen_MainMenu.GAME_WIDTH - GIRL_HEIGHT / 2
				|| position.x < GIRL_WIDTH / 2)
			velocity.set(0, 0);

		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		stateTime += deltaTime;
	}

	public boolean get_Is_Dead() {
		return this.is_Dead;
	}

	public int get_Int_Shield() {
		return this.shield;
	}

	public int get_Int_Life() {
		return this.life;
	}

	public boolean get_Is_Un_Dead() {
		return this.is_Un_Dead;
	}

	public void set_Is_Un_Dead(boolean input) {
		this.is_Un_Dead = input;
	}

	public void set_Un_dead_CulTime(int cul_Time) {
		this.Un_Dead_Cult = cul_Time;
	}

	public void set_Int_Shield(int a) {
		this.shield = a;
	}

	public void set_Int_Life(int a) {
		this.life = a;
	}

	public void set_Int_Life_Increase() {
		this.life += 1;
	}

	public void cresh_Bubble_Missile() {
		shield -= 1;
		if (shield >= 0) { // 쉴드가 있으면
			is_Un_Dead = true;
			Un_Dead_Cult = 200;
		} else {
			is_Dead = true;
			life--;

			if (life >= 0)
			{
				shield = 3;
				reset(position.x, position.y);
				item_Reset();
			}
		}

	}

	public void cresh_Boss_Star_Missile() {

		is_Dead = true;
		life--;

		if (life >= 0)
		{
			shield = 3;
			reset(position.x, position.y);
			item_Reset();
		}
	}

	public void cresh_Enemy() {
		is_Dead = true;
		life--;
		if (life >= 0)
		{
			shield = 3;
			reset(position.x, position.y);
			item_Reset();
		}
	}

	public void clear_Game_Update(float deltaTime, int dir) {
		velocity.set(GIRL_MOVE_VELOCITY * dir, 0);

		state_Flag = (dir == -1) ? OBJ_D_LEFT : OBJ_D_RIGHT;

		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		stateTime += deltaTime;
	}
}

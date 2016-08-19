package pangpang.MainGame;

import jrcengine.Interface.Object_Dynamic;
import jrcengine.Math.Math_Vector;

public class Boss_Star_Missile extends Object_Dynamic {

	public static final int Boss_Star_Missile_WIDTH = 10;
	public static final int Boss_Star_Missile_HEIHGT = 10;
	public static final int BASIC_SPEED = 60;

	private int image_Number;
	private boolean isDead;
	private boolean outBound;
	private int rotateNumber;

	public Boss_Star_Missile(float x, float y, int dir) {
		super(x, y, Boss_Star_Missile_WIDTH, Boss_Star_Missile_HEIHGT);

		this.rotateNumber = 0;
		this.image_Number = 50;
		this.isDead = false;
		this.velocity = new Math_Vector(BASIC_SPEED
				* test_StartTest.map_controler.get_Direc_Dis_X(dir),
				BASIC_SPEED * test_StartTest.map_controler.get_Direc_Dis_Y(dir));

	}

	public void update(float deltaTime) {

		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		outBound = (position.x < -Boss_Star_Missile_WIDTH / 2
				|| position.x > Screen_MainMenu.GAME_WIDTH
						+ Boss_Star_Missile_WIDTH / 2 || position.y < -Boss_Star_Missile_HEIHGT / 2) ? true
				: false;

		stateTime += deltaTime;

		if(stateTime >0.1)
		{
			rotateNumber++;
			stateTime = 0;
		}

		if (rotateNumber > 15)
			rotateNumber = 0;
	}

	boolean get_Out_Bound() {
		return this.outBound;
	}

	boolean get_Is_Dead() {
		return this.isDead;
	}

	int get_Image_Number() {
		return this.image_Number;
	}

	int get_Rotate_Number()
	{
		return this.rotateNumber;
	}

}
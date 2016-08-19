package pangpang.MainGame;

import android.util.Log;
import jrcengine.Interface.Object_Dynamic;
import jrcengine.Math.Math_Vector;

public class Item extends Object_Dynamic {

	public static final int ITEM_WIDTH = 15;
	public static final int ITEM_HEIHGT = 15;
	public static final int BASIC_SPEED = 60;

	private int image_Number;
	private boolean outBound;
	private int rotateNumber;

	public Item(float x, float y, int image_Number) {
		super(x, y, ITEM_WIDTH, ITEM_HEIHGT);

		this.rotateNumber = 0;
		this.image_Number = image_Number;
		this.velocity = new Math_Vector(0, -BASIC_SPEED);

	}

	public void update(float deltaTime) {

		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		outBound = (position.x < -ITEM_WIDTH / 2
				|| position.x > Screen_MainMenu.GAME_WIDTH + ITEM_WIDTH / 2 || position.y < -ITEM_HEIHGT / 2) ? true
				: false;

		stateTime += deltaTime;

		if (stateTime > 0.1) {
			rotateNumber++;
			stateTime = 0;
		}

		if (rotateNumber > 15)
			rotateNumber = 0;
	}

	public boolean get_Out_Bound() {
		return this.outBound;
	}

	public int get_Image_Number() {
		return this.image_Number;
	}

	public int get_Rotate_Number() {
		return this.rotateNumber;
	}

	public void crash_Girl() {
		switch (image_Number) {
		case 62: // 무기 2개 발사
			MainGame_Manager.is_Double_Attack = true;
			break;

		case 63: // 무기 강화
			MainGame_Manager.is_Power_Attack = true;
			break;

		case 64: // 무기 발사 쿨타임
			MainGame_Manager.stand_Attack_Statue_Girl = 0.1f;
			MainGame_Manager.star_Missile_Delay = 50;
			break;

		case 65: // 방어막
			MainGame_Manager.mGirl.set_Int_Shield(3);
			break;

		case 66: // 무적
			MainGame_Manager.mGirl.set_Un_dead_CulTime(200);
			MainGame_Manager.mGirl.set_Is_Un_Dead(true);
			break;
		case 67:
			if (MainGame_Manager.mGirl.get_Int_Life() < 3)
				MainGame_Manager.mGirl.set_Int_Life_Increase();
			break;
		default:
			Log.e("exception", "outIndexItem");
			break;
		}

	}

}
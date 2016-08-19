package pangpang.MainGame;

import java.util.Random;

import jrcengine.Interface.Object_Dynamic;
import jrcengine.Math.Math_Vector;

public class Bubble_Missile extends Object_Dynamic {

	public static final int BUBLLE_MISSILE_WIDTH = 5;
	public static final int BUBLLE_MISSILE_HEIHGT = 5;

	private Random rnd = new Random();
	private int image_Number;
	private boolean isDead;
	private int directions;
	private boolean outBound;

	public Bubble_Missile(float x, float y, int dir) {
		super(x, y, BUBLLE_MISSILE_WIDTH, BUBLLE_MISSILE_HEIHGT);

		this.isDead = false;
		this.directions = dir;
		this.image_Number = rnd.nextInt(3)+45;
		this.velocity = new Math_Vector(test_StartTest.map_controler.get_Direc_Dis_X(dir)*60,
				test_StartTest.map_controler.get_Direc_Dis_Y(dir)*60);

	}
	
	public void update(float deltaTime) {

		position.add(velocity.x*deltaTime , velocity.y*deltaTime );
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		
		outBound = (position.x <-BUBLLE_MISSILE_WIDTH/2 || position.x >Screen_MainMenu.GAME_WIDTH+BUBLLE_MISSILE_WIDTH/2
				|| position.y<-BUBLLE_MISSILE_HEIHGT/2) ? true :false ;
		
		stateTime += deltaTime;
	}
	
	boolean get_Out_Bound()
	{
		return this.outBound;
	}

	boolean get_Is_Dead() {
		return this.isDead;
	}

	int get_Directions() {
		return this.directions;
	}
	
	int get_Image_Number() {
		return this.image_Number;
	}
	

}

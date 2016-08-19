package pangpang.MainGame;



import jrcengine.Interface.Object_Dynamic;
import jrcengine.Math.Math_Vector;

public class Star_Missile extends Object_Dynamic {

	public static final int Star_Missile_WIDTH = 5;
	public static final int Star_Missile_HEIHGT = 5;

	private int image_Number;
	private boolean isDead;
	private boolean outBound;
	private int rotateNumber;

	public Star_Missile (float x, float y) {
		super(x, y, Star_Missile_WIDTH, Star_Missile_HEIHGT);

		this.image_Number = (MainGame_Manager.is_Power_Attack ==false)? 48 :49;  
		this.isDead = false;
		this.velocity = new Math_Vector(0,
				60);
		this.rotateNumber = 0;

	}
	
	public void update(float deltaTime) {

		position.add(velocity.x*deltaTime , velocity.y*deltaTime );
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		
		outBound = (position.x <-Star_Missile_WIDTH/2 || position.x >Screen_MainMenu.GAME_WIDTH+Star_Missile_WIDTH/2
				|| position.y> +Screen_MainMenu.GAME_HEIGHT+Star_Missile_HEIHGT/2) ? true :false ;
		
		stateTime += deltaTime;
		rotateNumber++;
		
		if(rotateNumber>15)
			rotateNumber = 0;
	}
	
	boolean get_Out_Bound()
	{
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

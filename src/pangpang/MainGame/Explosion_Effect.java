package pangpang.MainGame;

import java.util.Random;

import jrcengine.Interface.Object_Dynamic;

public class Explosion_Effect extends Object_Dynamic{
	
	public static float EXPROTION_BASE_EFFECT_WIDTH = 10.0f;	// nomal frame size
	public static float EXPROTION_BASE_EFFECT_HEIGHT = 10.0f; // nomal frame size
	
	private boolean overtime;
	private int direction;
	private Random rnd;
	private int culTime;
	private int imageNumber;
	private float imageWidth[] = {EXPROTION_BASE_EFFECT_WIDTH,
			EXPROTION_BASE_EFFECT_WIDTH+1,
			EXPROTION_BASE_EFFECT_WIDTH+2,
			EXPROTION_BASE_EFFECT_WIDTH+3,
			EXPROTION_BASE_EFFECT_WIDTH+2,
			EXPROTION_BASE_EFFECT_WIDTH+1};
	private float outWidth;
	
	
	
	public Explosion_Effect(float x, float y, int dir) {
		super(x, y, EXPROTION_BASE_EFFECT_WIDTH, EXPROTION_BASE_EFFECT_HEIGHT, 0);
		// TODO Auto-generated constructor stub
		overtime = false;
		rnd = new Random();
		culTime = rnd.nextInt(3)+3; // 3~ 5ÃÊ ; 
		imageNumber = rnd.nextInt(6)+39; //39~44;
		direction = dir;
		velocity.set(test_StartTest.map_controler.get_Direc_Dis_X(dir)*10,
				test_StartTest.map_controler.get_Direc_Dis_Y(dir)*10);
	}
	
	public void update(float deltatime)
	{
		position.add(velocity.x*deltatime,velocity.y*deltatime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		
		stateTime+= deltatime;
		outWidth = imageWidth[(int)(stateTime*2)%6];
		
		if(stateTime >culTime)
			overtime = true;
	}
	
	public float get_Width()
	{
		return  outWidth;
	}
	
	public boolean get_Over_Time()
	{
		return this.overtime;
	}
	
	public int get_Image_Number()
	{
		return this.imageNumber;
	}
	
	public int get_Direction()
	{
		return this.direction;
	}
	
	public void crash_Star_Missile()
	{
		MainGame_Manager.Score += rnd.nextInt(30)+30;
	}

}
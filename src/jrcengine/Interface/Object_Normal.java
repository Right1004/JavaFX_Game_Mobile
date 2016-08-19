package jrcengine.Interface;

import jrcengine.Math.Math_Overlap_Circle;
import jrcengine.Math.Math_Overlap_Rectangle;
import jrcengine.Math.Math_Vector;

public class Object_Normal {

	
	public final Math_Overlap_Rectangle bounds;
	public final Math_Overlap_Circle radious_bounds;
	public Math_Vector position;
	
	private int image_flag;
	private int[] image_flags;

	private boolean isTouchDown;
	private boolean isTouchUp;
	private boolean isDragged;
	private boolean isClicked;
	protected float stateTime;

	public Object_Normal(int flag, float center_x, float center_y, float width,
			float height) {
		this.bounds = new Math_Overlap_Rectangle(center_x - width / 2, center_y
				- height / 2, width, height);
		this.radious_bounds = null;
		this.image_flag = flag;
		intialize(center_x, center_y);
	}

	public Object_Normal(float center_x, float center_y, float width,
			float height, int... flags) {
		this.bounds = new Math_Overlap_Rectangle(center_x - width / 2, center_y
				- height / 2, width, height);
		this.radious_bounds = null;
		this.image_flags = flags;
		intialize(center_x, center_y);
	}

	public Object_Normal(int flag, float center_x, float center_y, float radious) {
		this.bounds = null;
		this.radious_bounds = new Math_Overlap_Circle(center_x, center_y, radious);
		this.image_flag = flag;
		intialize(center_x, center_y);
	}

	public Object_Normal(float center_x, float center_y, float radious, int... flags) {
		this.bounds = null;
		this.radious_bounds = new Math_Overlap_Circle(center_x, center_y, radious);
		this.image_flags = flags;
		intialize(center_x, center_y);
	}

	public Object_Normal(float center_x, float center_y, float width,
			float height) {
		this.bounds = new Math_Overlap_Rectangle(center_x - width / 2, center_y
				- height / 2, width, height);
		this.radious_bounds = null;
		this.image_flags = null;
		intialize(center_x, center_y);
	}

	private void intialize(float center_x, float center_y) {
		this.position = new Math_Vector(center_x,center_y);
		this.stateTime = 0;
		this.isDragged = false;
		this.isClicked = false;
	}

	public float getStateTime() {
		return stateTime;
	}

	public int getImage_flags(int number) {
		return this.image_flags[number];
	}
	
	public void setIsClicked(boolean set) {
		this.isClicked = set;
	}

	public void setIsDragged(boolean set) {
		this.isDragged = set;
	}

	public void setIsTouchDown(boolean set) {
		this.isTouchDown = set;
	}

	public void setIsTouchUp(boolean set) {
		this.isTouchUp = set;
	}

	public int getImage_flag() {
		return this.image_flag;
	}
	
	public boolean getIsClicked() {
		return this.isClicked;
	}

	public boolean getIsDragged() {
		return this.isDragged;
	}

	public boolean getIsTouchDown() {
		return this.isTouchDown;
	}

	public boolean getIsTouchUp() {
		return this.isTouchUp;
	}

}

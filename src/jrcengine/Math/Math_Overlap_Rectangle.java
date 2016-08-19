package jrcengine.Math;

public class Math_Overlap_Rectangle {
	public final Math_Vector lowerLeft;
	public float width, height;

	public Math_Overlap_Rectangle(float lowerLeft_x, float lowerLeft_y,
			float width, float height) {
		this.lowerLeft = new Math_Vector(lowerLeft_x, lowerLeft_y);
		this.width = width;
		this.height = height;
	}

	public void setRectangle(Math_Vector position) {
		this.lowerLeft.set(position.x - width / 2, position.y - height / 2);
	}
}

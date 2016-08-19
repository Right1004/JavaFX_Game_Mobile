package jrcengine.Math;

public class Math_Overlap_Circle {
	public final Math_Vector center = new Math_Vector();
	public float radius;

	public Math_Overlap_Circle(float x, float y, float radius) {
		this.center.set(x, y);
		this.radius = radius;
	}
}

package jrcengine.Math;

import android.util.FloatMath;

public class Math_Vector {
	public static float TO_RADIANS = (1 / 180.0f) * (float) Math.PI;
	public static float TO_DEGREES = (1 / (float) Math.PI) * 180;
	public float x, y;

	public Math_Vector() {
	}

	public Math_Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Math_Vector(Math_Vector other) {
		this.x = other.x;
		this.y = other.y;
	}

	public Math_Vector cpy() {
		return new Math_Vector(x, y);
	}

	public Math_Vector set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Math_Vector set(Math_Vector other) {
		this.x = other.x;
		this.y = other.y;
		return this;
	}

	public Math_Vector add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Math_Vector add(Math_Vector other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}

	public Math_Vector sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Math_Vector sub(Math_Vector other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}

	public Math_Vector mul(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	
	public Math_Vector mul(Math_Vector other){
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}

	public float len() {
		return FloatMath.sqrt(x * x + y * y);
	}

	public Math_Vector nor() {
		float len = len();
		if (len != 0) {
			this.x /= len;
			this.y /= len;
		}
		return this;
	}
	
	public boolean equals(Math_Vector other){
		
		if(this.x == other.x && this.y == other.y)
			return true;
		
		return false;
	}
	
	public boolean equals(float other_x, float other_y){
		
		if(this.x == other_x && this.y == other_y)
		{
			return true;
		}
		return false;
	}

	public float angle() {
		float angle = (float) Math.atan2(y, x) * TO_DEGREES;
		if (angle < 0)
			angle += 360;
		return angle;
	}

	public Math_Vector rotate(float angle) {
		float rad = angle * TO_RADIANS;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);

		float newX = this.x * cos - this.y * sin;
		float newY = this.x * sin + this.y * cos;

		this.x = newX;
		this.y = newY;

		return this;
	}

	public float dist(Math_Vector other) {
		float distX = this.x - other.x;
		float distY = this.y - other.y;
		return FloatMath.sqrt(distX * distX + distY * distY);
	}

	public float dist(float x, float y) {
		float distX = this.x - x;
		float distY = this.y - y;
		return FloatMath.sqrt(distX * distX + distY * distY);
	}

	public float distSquared(Math_Vector other) {
		float distX = this.x - other.x;
		float distY = this.y - other.y;
		return distX * distX + distY * distY;
	}

	public float distSquared(float x, float y) {
		float distX = this.x - x;
		float distY = this.y - y;
		return distX * distX + distY * distY;
	}
}
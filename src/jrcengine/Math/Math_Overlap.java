package jrcengine.Math;

public class Math_Overlap {
	public static final int Left_Crash = 0;
	public static final int Right_Crash = 1;
	public static final int Up_Crash = 2;
	public static final int Down_Crash = 3;
	public static final int Error_Crash = 7;

	private static final float CRASH_WIDTH = 0.2f;

	public static boolean overlapCircles(Math_Overlap_Circle c1,
			Math_Overlap_Circle c2) {
		float distance = c1.center.distSquared(c2.center);
		float radiusSum = c1.radius + c2.radius;
		return distance <= radiusSum * radiusSum;
	}

	public static boolean overlapRectangles(Math_Overlap_Rectangle r1,
			Math_Overlap_Rectangle r2) {
		if (r1.lowerLeft.x < r2.lowerLeft.x + r2.width
				&& r1.lowerLeft.x + r1.width > r2.lowerLeft.x
				&& r1.lowerLeft.y < r2.lowerLeft.y + r2.height
				&& r1.lowerLeft.y + r1.height > r2.lowerLeft.y) {


			return true;
		} else
			return false;
	}

	public static int overlapRectangles(Math_Overlap_Rectangle r1,
			Math_Overlap_Rectangle r2, boolean tag) {

		// LEFT RIGHT REGION SEARCH
		if ((r1.lowerLeft.x < r2.lowerLeft.x + r2.width)
				&& (r1.lowerLeft.x > r2.lowerLeft.x + r2.width - CRASH_WIDTH)
				&& (r2.lowerLeft.y + r2.height - CRASH_WIDTH > r1.lowerLeft.y)
				&& (r1.lowerLeft.y + r1.height - CRASH_WIDTH > r2.lowerLeft.y)) {
			return Right_Crash;
		} else if ((r1.lowerLeft.x + r1.width > r2.lowerLeft.x)
				&& (r1.lowerLeft.x + r1.width - CRASH_WIDTH < r2.lowerLeft.x)
				&& (r2.lowerLeft.y + r2.height - CRASH_WIDTH > r1.lowerLeft.y)
				&& (r1.lowerLeft.y + r1.height - CRASH_WIDTH > r2.lowerLeft.y)) {
			return Left_Crash;
		} else if ((r1.lowerLeft.y < r2.lowerLeft.y + r2.height)
				&& (r1.lowerLeft.y > r2.lowerLeft.y + r2.height - CRASH_WIDTH)
				&& (r1.lowerLeft.x + r1.width > r2.lowerLeft.x)
				&& (r2.lowerLeft.x + r2.width > r1.lowerLeft.x)) {
			return Up_Crash;
		} else if ((r1.lowerLeft.y + r1.height > r2.lowerLeft.y)
				&& (r1.lowerLeft.y + r1.height - CRASH_WIDTH < r2.lowerLeft.y)
				&& (r1.lowerLeft.x + r1.width > r2.lowerLeft.x)
				&& (r2.lowerLeft.x + r2.width > r1.lowerLeft.x)) {
			return Down_Crash;
		} else
			return Error_Crash;
	}

	public static boolean overlapCircleRectangle(Math_Overlap_Circle c,
			Math_Overlap_Rectangle r) {
		float closestX = c.center.x;
		float closestY = c.center.y;

		if (c.center.x < r.lowerLeft.x) {
			closestX = r.lowerLeft.x;
		} else if (c.center.x > r.lowerLeft.x + r.width) {
			closestX = r.lowerLeft.x + r.width;
		}

		if (c.center.y < r.lowerLeft.y) {
			closestY = r.lowerLeft.y;
		} else if (c.center.y > r.lowerLeft.y + r.height) {
			closestY = r.lowerLeft.y + r.height;
		}

		return c.center.distSquared(closestX, closestY) < c.radius * c.radius;
	}

	public static boolean pointInCircle(Math_Overlap_Circle c, Math_Vector p) {
		return c.center.distSquared(p) < c.radius * c.radius;
	}

	public static boolean pointInCircle(Math_Overlap_Circle c, float x, float y) {
		return c.center.distSquared(x, y) < c.radius * c.radius;
	}

	public static boolean pointInRectangle(Math_Overlap_Rectangle r,
			Math_Vector p) {
		return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x
				&& r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;
	}

	public static boolean pointInRectangle(Math_Overlap_Rectangle r, float x,
			float y) {
		return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x
				&& r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
	}
}
package jrcengine.Basic;

import java.util.List;


import jrcengine.Interface.IFace_Handler_Touch;
import jrcengine.Interface.IFace_Input;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

/*
 * 이 게임에서 발생하는 모든 MOVE이벤트와 ACCEL을 받아들이는 CLASS이다
 * 이 CLASS는 INPUT의 인터페이스이다.
 */

public class Control_Input implements IFace_Input {
	Handler_Accelerometer accelHandler;
	IFace_Handler_Touch touchHandler;

	public Control_Input(Context context, View view, float scaleX, float scaleY) {
		accelHandler = new Handler_Accelerometer(context);
		if (Integer.parseInt(VERSION.SDK) < 5)
			touchHandler = new Handler_SingleTouch(view, scaleX, scaleY);
		else
			touchHandler = new Handler_MultiTouch(view, scaleX, scaleY);
	}

	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	public int getTouchX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}

	public int getTouchY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}

	public float getAccelX() {
		return accelHandler.getAccelX();
	}

	public float getAccelY() {
		return accelHandler.getAccelY();
	}

	public float getAccelZ() {
		return accelHandler.getAccelZ();
	}

	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}

}

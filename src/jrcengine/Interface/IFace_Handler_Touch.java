package jrcengine.Interface;

import java.util.List;


import jrcengine.Interface.IFace_Input.TouchEvent;
import android.view.View.OnTouchListener;

public interface IFace_Handler_Touch extends OnTouchListener {
	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	public List<TouchEvent> getTouchEvents();
}

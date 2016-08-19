package jrcengine.Basic;

import jrcengine.Interface.ExceptionLog;
import android.util.Log;

/*
 * Slime Runner에서 발생하는 Exception Log는 모두 이 모듈에서 처리한다.
 * 그래서 static fyild를 사용한 것이다.
 */

public class Log_Exception extends Exception implements ExceptionLog {

	private String errorcode;
	private String explain;

	public Log_Exception(String errorcode, String explain) {
		this.errorcode = errorcode;
		this.explain = explain;
	}

	public static void logEvent(String errorcode, String explain) {
		Log.e("" + errorcode, "" + explain);
	}

	@Override
	public String getMessage() {
		logEvent(errorcode, explain);
		return errorcode + " " + explain;
	}

}

package jrcengine.Basic;

import jrcengine.Interface.ExceptionLog;
import android.util.Log;

/*
 * Slime Runner���� �߻��ϴ� Exception Log�� ��� �� ��⿡�� ó���Ѵ�.
 * �׷��� static fyild�� ����� ���̴�.
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

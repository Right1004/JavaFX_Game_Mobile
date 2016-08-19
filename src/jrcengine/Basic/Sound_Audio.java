package jrcengine.Basic;

import java.io.IOException;


import jrcengine.Interface.IFace_Audio;
import jrcengine.Interface.IFace_Music;
import jrcengine.Interface.IFace_Sound;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

/*
 * ��ü ���� Sound�� Music�� ���� ���ִ� class�ν� 
 * Sound�� Music�� ������ �ִ� ��Ȱ�� �ϴ� ��Ŀ�̴�. <function class>
 * Music�� ��쿡 streaming�� ����Ͽ� ram�� ������ ������Ű�� �ʴ� ��ٶ� ������ �ε��� ���̴� class �̴�.
 * ���ν� background Music stc �� ���⿡ ���ϰ� �ȴ�.
 * Sound�� ��쿡�� �Ϲ������� �Ѽ��� 20������ loading �����ϰ� ����� EFFECT sound �ν� �κ� ���� ȿ��
 * ���� ���̴� ���� ������ �Ҹ��̴�.
 * 
 * ���⼭ �߻��ϴ� Error CODE�� 10�̴�.
 */

public class Sound_Audio implements IFace_Audio {

	private final int SoundGreatNum = 10; // ���� ��� ������ Ǯ ������
	AssetManager assets;
	SoundPool soundPool;

	public Sound_Audio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(SoundGreatNum,
				AudioManager.STREAM_MUSIC, 0);
	}

	public IFace_Music newMusic(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			return new Sound_Music(assetDescriptor);
		} catch (IOException e) {
			Log_Exception.logEvent("Error Code 10", "Audio_FileName");
			throw new RuntimeException("Couldn't load music <error Code 10> '"
					+ filename + "'");
		}
	}

	public IFace_Sound newSound(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new Sound_Sound(soundPool, soundId);
		} catch (IOException e) {
			Log_Exception.logEvent("Error Code 10", "Audio_FileName");
			throw new RuntimeException("Couldn't load sound <error Code 10> '"
					+ filename + "'");
		}
	}
}

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
 * 전체 적인 Sound와 Music을 관리 해주는 class로써 
 * Sound와 Music을 생성해 주는 역활을 하는 펀커이다. <function class>
 * Music의 경우에 streaming을 사용하여 ram에 완전히 적제시키지 않는 기다란 음악의 로딩에 쓰이는 class 이다.
 * 예로써 background Music stc 가 여기에 속하게 된다.
 * Sound의 경우에는 일반적으로 한순간 20개까지 loading 가능하게 만들고 EFFECT sound 로써 부분 적인 효과
 * 음에 쓰이는 작은 단위의 소리이다.
 * 
 * 여기서 발생하는 Error CODE는 10이다.
 */

public class Sound_Audio implements IFace_Audio {

	private final int SoundGreatNum = 10; // 동시 재상 가능한 풀 사이즈
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

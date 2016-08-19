package jrcengine.Basic;

import jrcengine.Interface.IFace_Sound;
import android.media.SoundPool;

/*
 * device의 기기에 적제되어 사용할 음악 파일을 관리해 주는 모듈이다.
 */

public class Sound_Sound implements IFace_Sound {
	int soundId;
	SoundPool soundPool;

	public Sound_Sound(SoundPool soundPool, int soundId) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	public void play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	public void dispose() {
		soundPool.unload(soundId);
	}

}

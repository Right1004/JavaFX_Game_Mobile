package jrcengine.Basic;

import jrcengine.Interface.IFace_Sound;
import android.media.SoundPool;

/*
 * device�� ��⿡ �����Ǿ� ����� ���� ������ ������ �ִ� ����̴�.
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

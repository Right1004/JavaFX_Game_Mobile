package jrcengine.Basic;

import java.io.IOException;


import jrcengine.Interface.IFace_Music;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/*
 * 안드로이도에서 스트리밍 하여줄 음악 파일을 관리해 주는 모듈이다.
 * 예외 처리로는 에러 코드 17이있다.
 */

public class Sound_Music implements IFace_Music, OnCompletionListener {
	MediaPlayer mediaPlayer;
	boolean isPrepared = false; // Music'statue Flag

	public Sound_Music(AssetFileDescriptor assetDescriptor) {
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
					assetDescriptor.getStartOffset(),
					assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			Log_Exception.logEvent("Error Code 11", "Music_FileName");
			throw new RuntimeException("Couldn't load music <error Code 11>");
		}
	}

	public void dispose() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer.release();
	}

	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	public boolean isStopped() {
		return !isPrepared;
	}

	public void pause() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	public void play() {
		if (mediaPlayer.isPlaying())
			return;

		try {
			synchronized (this) {
				if (!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			Log_Exception.logEvent("Error Code 17", "Not appropriate State");
			e.printStackTrace();
		} catch (IOException e) {
			Log_Exception.logEvent("Error Code 17", "Not appropriate State");
			e.printStackTrace();
		}
	}

	public void setLooping(boolean isLooping) {
		mediaPlayer.setLooping(isLooping);
	}

	public void setVolume(float volume) {
		mediaPlayer.setVolume(volume, volume);
	}

	public void stop() {
		mediaPlayer.stop();
		synchronized (this) {
			isPrepared = false;
		}
	}

	public void onCompletion(MediaPlayer player) {
		synchronized (this) {
			isPrepared = false;
		}
	}
}

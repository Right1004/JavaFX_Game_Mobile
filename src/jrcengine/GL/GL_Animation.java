package jrcengine.GL;

/*
 * 스프라이트 이미지 출력에 사용된다.
 * 모드로는 고정 픽스 모드와 유동 픽스 모드가있다
 */

public class GL_Animation {
	public static final int ANIMATION_LOOPING = 0;
	public static final int ANIMATION_NONLOOPING = 1;

	final GL_TextureRegion[] keyFrames;
	final float frameDuration;

	public GL_Animation(float frameDuration, GL_TextureRegion... keyFrames) {
		this.frameDuration = frameDuration;
		this.keyFrames = keyFrames;
	}

	// 고정 모드이냐 아니냐가 달려있다.
	public GL_TextureRegion getKeyFrame(float stateTime, int mode) {
		int frameNumber = (int) (stateTime / frameDuration);

		if (mode == ANIMATION_NONLOOPING) {
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
		} else {
			frameNumber = frameNumber % keyFrames.length;
		}
		return keyFrames[frameNumber];
	}
}

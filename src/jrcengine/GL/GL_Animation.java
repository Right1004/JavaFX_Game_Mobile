package jrcengine.GL;

/*
 * ��������Ʈ �̹��� ��¿� ���ȴ�.
 * ���δ� ���� �Ƚ� ���� ���� �Ƚ� ��尡�ִ�
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

	// ���� ����̳� �ƴϳİ� �޷��ִ�.
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

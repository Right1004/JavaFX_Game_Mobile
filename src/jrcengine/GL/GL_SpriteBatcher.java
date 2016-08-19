package jrcengine.GL;

import javax.microedition.khronos.opengles.GL10;

import jrcengine.Basic.GL_Graphics;
import jrcengine.Math.Math_Vector;
import android.util.FloatMath;

/*
 * 이미지를 저장하여 opengl에서 신속히 처리해 주는 모듈이다.
 */

public class GL_SpriteBatcher {
	final float[] verticesBuffer;
	int bufferIndex;
	final GL_Vertices vertices;
	int numSprites;

	public GL_SpriteBatcher(GL_Graphics glGraphics, int maxSprites) {
		this.verticesBuffer = new float[maxSprites * 4 * 4];
		this.vertices = new GL_Vertices(glGraphics, maxSprites * 4,
				maxSprites * 6, false, true);
		this.bufferIndex = 0;
		this.numSprites = 0;

		short[] indices = new short[maxSprites * 6];
		int len = indices.length;
		short j = 0;
		for (int i = 0; i < len; i += 6, j += 4) {
			indices[i + 0] = (short) (j + 0);
			indices[i + 1] = (short) (j + 1);
			indices[i + 2] = (short) (j + 2);
			indices[i + 3] = (short) (j + 2);
			indices[i + 4] = (short) (j + 3);
			indices[i + 5] = (short) (j + 0);
		}
		vertices.setIndices(indices, 0, indices.length);
	}

	public void beginBatch(GL_Texture texture) {
		texture.bind();
		numSprites = 0;
		bufferIndex = 0;
	}

	public void endBatch() {
		vertices.setVertices(verticesBuffer, 0, bufferIndex);
		vertices.bind();
		vertices.draw(GL10.GL_TRIANGLES, 0, numSprites * 6);
		vertices.unbind();
	}

	public void drawSprite(float draw_center_x, float draw_center_y,
			float draw_width, float draw_height, GL_TextureRegion region) {
		float draw_halfWidth = draw_width / 2;
		float draw_halfHeight = draw_height / 2;
		float draw_Left_up_x = draw_center_x - draw_halfWidth; // Left up_x_spot
		float draw_left_up_y = draw_center_y - draw_halfHeight; // left
																// up_y_spot
		float draw_right_down_x = draw_center_x + draw_halfWidth; // right_down_x_spot
		float draw_right_down_y = draw_center_y + draw_halfHeight; // right_down_y_spot

		verticesBuffer[bufferIndex++] = draw_Left_up_x;
		verticesBuffer[bufferIndex++] = draw_left_up_y;
		verticesBuffer[bufferIndex++] = region.text_l_u_p_x;
		verticesBuffer[bufferIndex++] = region.text_r_d_p_y;

		verticesBuffer[bufferIndex++] = draw_right_down_x;
		verticesBuffer[bufferIndex++] = draw_left_up_y;
		verticesBuffer[bufferIndex++] = region.text_r_d_p_x;
		verticesBuffer[bufferIndex++] = region.text_r_d_p_y;

		verticesBuffer[bufferIndex++] = draw_right_down_x;
		verticesBuffer[bufferIndex++] = draw_right_down_y;
		verticesBuffer[bufferIndex++] = region.text_r_d_p_x;
		verticesBuffer[bufferIndex++] = region.text_l_u_p_y;

		verticesBuffer[bufferIndex++] = draw_Left_up_x;
		verticesBuffer[bufferIndex++] = draw_right_down_y;
		verticesBuffer[bufferIndex++] = region.text_l_u_p_x;
		verticesBuffer[bufferIndex++] = region.text_l_u_p_y;

		numSprites++;
	}

	public void drawSprite(float draw_center_x, float draw_center_y,
			float draw_width, float draw_height, float angle,
			GL_TextureRegion region) {
		float draw_halfWidth = draw_width / 2;
		float draw_halfHeight = draw_height / 2;

		float rad = angle * Math_Vector.TO_RADIANS; // 중심으로 기죽으로 위로 오른족 위
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);

		float draw_left_down_x = -draw_halfWidth * cos - (-draw_halfHeight)
				* sin;
		float draw_left_down_y = -draw_halfWidth * sin + (-draw_halfHeight)
				* cos;
		float draw_right_down_x = draw_halfWidth * cos - (-draw_halfHeight)
				* sin;
		float draw_right_down_y = draw_halfWidth * sin + (-draw_halfHeight)
				* cos;
		float draw_right_up_x = draw_halfWidth * cos - draw_halfHeight * sin;
		float draw_right_up_y = draw_halfWidth * sin + draw_halfHeight * cos;
		float draw_left_up_x = -draw_halfWidth * cos - draw_halfHeight * sin;
		float draw_left_up_y = -draw_halfWidth * sin + draw_halfHeight * cos;

		draw_left_down_x += draw_center_x;
		draw_left_down_y += draw_center_y;
		draw_right_down_x += draw_center_x;
		draw_right_down_y += draw_center_y;
		draw_right_up_x += draw_center_x;
		draw_right_up_y += draw_center_y;
		draw_left_up_x += draw_center_x;
		draw_left_up_y += draw_center_y;

		verticesBuffer[bufferIndex++] = draw_left_down_x;// x1;
		verticesBuffer[bufferIndex++] = draw_left_down_y;// y1;
		verticesBuffer[bufferIndex++] = region.text_l_u_p_x;
		verticesBuffer[bufferIndex++] = region.text_r_d_p_y;

		verticesBuffer[bufferIndex++] = draw_right_down_x;// x2;
		verticesBuffer[bufferIndex++] = draw_right_down_y;// y2;
		verticesBuffer[bufferIndex++] = region.text_r_d_p_x;
		verticesBuffer[bufferIndex++] = region.text_r_d_p_y;

		verticesBuffer[bufferIndex++] = draw_right_up_x;// x3;
		verticesBuffer[bufferIndex++] = draw_right_up_y;// y3;
		verticesBuffer[bufferIndex++] = region.text_r_d_p_x;
		verticesBuffer[bufferIndex++] = region.text_l_u_p_y;

		verticesBuffer[bufferIndex++] = draw_left_up_x;// x4;
		verticesBuffer[bufferIndex++] = draw_left_up_y;// y4;
		verticesBuffer[bufferIndex++] = region.text_l_u_p_x;
		verticesBuffer[bufferIndex++] = region.text_l_u_p_y;

		numSprites++;
	}
}
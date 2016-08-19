package pangpang.MainGame;

import android.util.Log;

public class Enemy_life {
	private int enemy_Life[][] = new int[6][8];

	public Enemy_life(String data) {
		String tmp[] = data.split("\n");
		String s;

		for (int i = 1; i < tmp.length; i++) {
			s = tmp[i];
			for (int j = 0; j < 8; j++) {
				switch (s.charAt(j)) {
				case '-':
					enemy_Life[i - 1][j] = -1;
					break;
				default:
					enemy_Life[i - 1][j] = s.charAt(j) - 48; // 숫자로 변환
				}
			} // for j
		}// for i
		if (Screen_MainMenu.is_Debug)
			Log.v("Enemy_Life", "Make Enemy_Life success");

	}

	// -----------------------------------
	// get_enemy_life
	// -----------------------------------

	public int get_Enemy_Life(int kind, int num) {
		return enemy_Life[kind][num];
	}

}

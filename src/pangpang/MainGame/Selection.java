package pangpang.MainGame;

import android.util.Log;

public class Selection {
	private int pathNum[][] = new int[6][8];
	private int enemy_ToTal_Cnt; // 적의 총 수를 의미한다.

	public Selection(String data) {
		String tmp[] = data.split("\n");
		String s;
		enemy_ToTal_Cnt = 0;
		char ch;

		for (int i = 1; i < tmp.length; i++) {
			s = tmp[i];
			for (int j = 0; j < 8; j++) {
				ch = s.charAt(j);
				switch (ch) {
				case '-':
					pathNum[i - 1][j] = -1;
					break;

				default:
					enemy_ToTal_Cnt++; // total enemy number
					if (ch <= '9')
						pathNum[i - 1][j] = ch - 48; // 0 ~9;
					else
						pathNum[i - 1][j] = ch - 87; // 'a'~'z'
				}
			} // for j
		}// for i
		if (Screen_MainMenu.is_Debug)
			Log.e("Selction", "Make Selection success");

	}// creator

	// -----------------------------------------------
	// path- number
	// -----------------------------------------------
	public int getSelection(int kind, int num) {

		return this.pathNum[kind][num];
	}

	// -----------------------------------------------
	// total_enemy_number
	// -----------------------------------------------
	public int GetEnemyCount() {
		return this.enemy_ToTal_Cnt;
	}

}

package pangpang.MainGame;

import java.util.ArrayList;
import android.util.Log;

//-------------------------------
//J.R.C company
//map file
//Path Decoder
//-------------------------------

public class Path {

	private ArrayList<SinglePath> mPath = new ArrayList<SinglePath>();

	public Path(String map_data) {
		String tmp[] = map_data.split("\n");
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].indexOf("//") >= 0 || tmp[i].trim().equals(""))
				continue;
			mPath.add(new SinglePath(tmp[i]));
		}
		if (Screen_MainMenu.is_Debug)
			Log.v("Path", "Make Path Success");

	}

	public SinglePath getPath(int index) {
		return mPath.get(index);
	}

}

class SinglePath {

	private int start_X; // 시작 위치
	private int start_Y;
	private int dir[]; // 방향
	private int len[]; // 진행 길이

	public SinglePath(String line_map_data) {
		// line_map_data = " 0: 200,-30: 8-43, 9-3, 10-3, 11-3, 12-3, 13-3,
		// 14-13;

		String tmp[] = line_map_data.split(":");

		int n = tmp[1].indexOf(',');
		start_X = Integer.parseInt(tmp[1].substring(0, n).trim());
		start_Y = Integer.parseInt(tmp[1].substring(n + 1).trim());

		String stmp[] = tmp[2].split(",");
		n = stmp.length;

		dir = new int[n];
		len = new int[n];

		int p;
		for (int i = 0; i < n; i++) {
			p = stmp[i].indexOf('-');
			dir[i] = Integer.parseInt(stmp[i].substring(0, p).trim());
			len[i] = Integer.parseInt(stmp[i].substring(p + 1).trim());
		}

	}

	public int get_Start_X() {
		return this.start_X;
	}

	public int get_Start_Y() {
		return this.start_Y;
	}

	public int[] get_Directions() {
		return this.dir;
	}

	public int[] get_Lens() {
		return this.len;
	}
}

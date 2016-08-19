package pangpang.MainGame;

import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.util.Log;

public class Map_Controler {

	private Activity contents;

	// 16 방향에 대한 삼각함수 x의 각각의 방향을 선언해준것 여기서 0번이 북쪽을 시작으로 시계방향으 회전한다.
	private float direction_dis_X[] = { 0f, 0.39f, 0.75f, 0.93f, 1f, 0.93f,
			0.75f, 0.39f, 0f, -0.39f, -0.75f, -0.93f, -1f, -0.93f, -0.75f,
			-0.39f };

	private float dircetion_dis_Y[] = { 1f, 0.93f, 0.75f, 0.39f, 0f, -0.39f,
			-0.75f, -0.93f, -1f, -0.93f, -0.75f, -0.39f, 0f, 0.39f, 0.75f,
			0.93f };

	public int syncCnt = 0; // 싱크 계산용
	public int dirLen = 75; // 전투 대형 유지 후 처음으로 이동할 거리
	public int dir = 4; // 위의 방향
	public int dirCnt = 0; // 현재 부대가 이동한 거리

	private Path mPath; // Path
	private Selection mSelect; // Select
	private DelayTime mDelay; // Delay
	private Position mPos; // Position
	private Enemy_life mEny_life; // Enemy_life

	private int enemy_Cnt; // 현 스태이지 살아있는 적수
	private int attackTime; // 공격 시작 시간

	Map_Controler(Activity A) {
		// this class . funcer
		contents = A; // R을 사용하기 위해서 엑티비티를 받아서 가져온다.
	}

	// -------------------------------------
	// Read map file
	// -------------------------------------
	public void readMap(int map_Number) {
		map_Number--;
		// stage map loading

		if (Screen_MainMenu.is_Debug)
			Log.e("map", "" + map_Number);

		InputStream map_file = contents.getResources().openRawResource(
				R.raw.stage01 + map_Number);

		try {
			byte[] data = new byte[map_file.available()];
			map_file.read(data);
			map_file.close();
			String load_data = new String(data, "EUC-KR");
			makeMap(load_data);
		} catch (IOException e) {
			Log.e("error", "not load MapData");
		}
	}

	public void makeMap(String data) {
		int n1 = data.indexOf("selection");
		mPath = new Path(data.substring(0, n1)); // Path

		int n2 = data.indexOf("delay");
		mSelect = new Selection(data.substring(n1, n2)); // Selection
		enemy_Cnt = mSelect.GetEnemyCount(); // 적군의 수

		n1 = data.indexOf("position");
		mDelay = new DelayTime(data.substring(n2, n1)); // Delay
		attackTime = mDelay.getDelay(0, 5); // 마지막 캐릭터

		n2 = data.indexOf("shield");
		mPos = new Position(data.substring(n1, n2)); // Position

		mEny_life = new Enemy_life(data.substring(n2)); // enemy_life

	}

	// -------------------------------------------------------------
	// get_function
	// -------------------------------------------------------------

	// life_Enemy_count
	public int get_Enemy_live_Count() {
		return this.enemy_Cnt;
	}

	// init_start_attack_time
	public int get_Attack_Time() {
		return this.attackTime;
	}

	// single_path
	public SinglePath get_Path(int num) {
		return this.mPath.getPath(num);
	}

	// get_selection(입장 패치)
	public int get_Selection(int kind, int num) {
		return this.mSelect.getSelection(kind, num);
	}

	// get_pos_x (최초 좌표_X)
	public int get_Pos_X(int kind, int num) {
		return this.mPos.get_Pos_X(kind, num);
	}

	// get_pos_y (최초 좌표_Y)
	public int get_Pos_Y(int kind, int num) {
		return this.mPos.get_Pos_Y(kind, num);
	}

	// get_enemy_num
	public int get_Enemy_Num(int kind, int num) {
		return this.mPos.getEnemyNum(kind, num);
	}

	// get_enemy_life
	public int get_Enemy_Life(int kind, int num) {
		return this.mEny_life.get_Enemy_Life(kind, num);
	}

	// get_delay
	public int get_Delay(int kind, int num) {
		return this.mDelay.getDelay(kind, num);
	}

	public float get_Direc_Dis_X(int tag) {
		return this.direction_dis_X[tag];
	}

	public float get_Direc_Dis_Y(int tag) {
		return this.dircetion_dis_Y[tag];
	}

	public void set_Enemy_Cnt_Minus() {
		this.enemy_Cnt -= 1;
	}
	
	public void set_Enemy_Cnt(int number) {
		this.enemy_Cnt = number;
	}

	public Activity get_Contents() {
		return this.contents;
	}
}

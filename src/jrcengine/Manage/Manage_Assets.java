package jrcengine.Manage;

import java.util.ArrayList;

import jrcengine.Basic.GL_Game;
import jrcengine.GL.GL_Animation;
import jrcengine.GL.GL_Font;
import jrcengine.GL.GL_Texture;
import jrcengine.GL.GL_TextureRegion;
import jrcengine.Interface.IFace_Music;
import jrcengine.Interface.IFace_Sound;

public class Manage_Assets {

	public static ArrayList<GL_Texture> texture = new ArrayList<GL_Texture>();
	public static ArrayList<GL_TextureRegion> textureRegion = new ArrayList<GL_TextureRegion>();
	public static ArrayList<GL_Animation> animation = new ArrayList<GL_Animation>();

	public static IFace_Music music;
	public static ArrayList<IFace_Sound> sound = new ArrayList<IFace_Sound>();

	// 외부 자원 //
	public static GL_Texture items;
	public static GL_TextureRegion mainMenu;
	public static GL_TextureRegion pauseMenu;
	public static GL_TextureRegion ready;
	public static GL_TextureRegion gameOver;
	public static GL_TextureRegion highScoresRegion;
	public static GL_TextureRegion logo;
	public static GL_TextureRegion soundOn;
	public static GL_TextureRegion soundOff;
	public static GL_TextureRegion arrow;
	public static GL_TextureRegion pause;
	public static GL_TextureRegion castle;
	public static GL_Animation coinAnim;
	public static GL_Font font;

	public class NetworkProtocol {
		public static final int _ACCESS_THE_SERVER = 0x00000001;

		public static final int _ANSWER_LOGIN = 0x0000000e;

		public static final int _REQUEST_MOBILE_LOGIN = 0x00000037;
		
		/**
		 * Request the Room List Information 2
		 */
		public static final int _REQUEST_THE_ROOM_LIST_INFORMATION = 0x00000002;

		/**
		 * Request Mobile login to server 56
		 */
		public static final int _REQUEST_MOBILE_MAKE_GAME_UNIVERSE = 0x00000038;

		/**
		 * Request Mobile login to server 57
		 */
		public static final int _ANSWER_MOBILE_MAKE_GAME_UNIVERSE = 0x00000039;

		/**
		 * Request METEORGAME STONE REINIT GAME PLAY 74
		 */

		public static final int _REQEUST_MOBILE_PANGPANG_SCORE = 0x0000004a;

		/**
		 * Answer METEORGAME STONE REINIT GAME PLAY 75
		 */
		public static final int _ANSWER_MOBILE_PANGPANG_SCORE = 0x0000004b;

		/**
		 * 32bits encrypt key for AES
		 */
		public static final String sEncryptKey = "fe8025947de7cd71";

		/**
		 * encrypt mode
		 */
		public static final String ALGO = "AES";

		/**
		 * 64bits encrypt key for AES
		 */
		public static final String keyStr = "Z8LSq0wWwB5v+6YJzurcP463H3F12iZh74fDj4S74oUH4EONkiKb2FmiWUbtFh97GG/c/lbDE47mvw6j94yXxKHOpoqu6zpLKMKPcOoSppcVWb2q34qENBJkudXUh4MWcreondLmLL2UyydtFKuU9Sa5VgY/CzGaVGJABK2ZR94=";

		/**
		 * Error Packet
		 */
		public static final int _ERROR_PACKET = 0xffffffff;

		/**
		 * packet sending split token
		 */
		public static final String sSenderSplitProtocolToken = "/&&";

		/**
		 * packet receive split token
		 */
		public static final String sReceiverSplitProtocolToken = "/&";

		/**
		 * packet split sending multiple token
		 */
		public static final String sSenderSplitMultipleToken = "@";

		/**
		 * packet split receive multiple token
		 */
		public static final String sReceiverSplitMultipleToken = "@";

		/**
		 * pangapng position information token
		 */
		public static final String sPangPangPositionInformationWordToken = "!!";

		/**
		 * pangpang position coordinator token
		 */
		public static final String sPangPangPositionCoordinationToken = ">!";

	}

	public static void load(GL_Game game) {
		// ------------------ key_pad.png 파일에서 정의 ------------------
		// number_demo
		texture.add(new GL_Texture(game, "auto00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(0), 0, 0, 94, 94)); // 0
																				// -
																				// auto00
		texture.add(new GL_Texture(game, "auto01.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(1), 0, 0, 94, 94)); // 1
																				// -
																				// auto01

		texture.add(new GL_Texture(game, "back01.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(2), 0, 0, 640, 960)); // 2
																					// -
																					// back01
		texture.add(new GL_Texture(game, "back02.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(3), 0, 0, 1091, 672)); // 3
																					// -
																					// back02

		texture.add(new GL_Texture(game, "btn00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(4), 0, 0, 409, 101)); // 4
																					// -
																					// btn00
		texture.add(new GL_Texture(game, "btn01.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(5), 0, 0, 409, 101)); // 5
																					// -
																					// btn01
		texture.add(new GL_Texture(game, "btn10.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(6), 0, 0, 409, 101)); // 6
																					// -
																					// btn10
		texture.add(new GL_Texture(game, "btn11.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(7), 0, 0, 409, 101)); // 7
																					// -
																					// btn11
		texture.add(new GL_Texture(game, "btn20.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(8), 0, 0, 409, 101)); // 8
																					// -
																					// btn20
		texture.add(new GL_Texture(game, "btn21.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(9), 0, 0, 409, 101)); // 9
																					// -
																					// btn21
		texture.add(new GL_Texture(game, "btn30.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(10), 0, 0, 409, 101)); // 10
																					// -
																					// btn30
		texture.add(new GL_Texture(game, "btn31.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(11), 0, 0, 409, 101)); // 11
																					// -
																					// btn31
		texture.add(new GL_Texture(game, "btn40.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(12), 0, 0, 409, 101)); // 12
																					// -
																					// btn40
		texture.add(new GL_Texture(game, "btn41.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(13), 0, 0, 409, 101)); // 13
																					// -
																					// btn41
		texture.add(new GL_Texture(game, "btn50.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(14), 0, 0, 409, 101)); // 14
																					// -
																					// btn50
		texture.add(new GL_Texture(game, "btn51.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(15), 0, 0, 409, 101)); // 15
																					// -
																					// btn51

		texture.add(new GL_Texture(game, "gravity00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(16), 0, 0, 94, 94)); // 16
																				// -
																				// gravity00
		texture.add(new GL_Texture(game, "gravity01.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(17), 0, 0, 94, 94)); // 17
																				// -
																				// gravity01

		texture.add(new GL_Texture(game, "item00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(18), 0, 0, 30, 30)); // 18
																				// -
																				// item00
		texture.add(new GL_Texture(game, "item01.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(19), 0, 0, 30, 30)); // 19
																				// -
																				// item01
		texture.add(new GL_Texture(game, "item02.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(20), 0, 0, 30, 30)); // 20
																				// -
																				// item02
		texture.add(new GL_Texture(game, "item03.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(21), 0, 0, 30, 30)); // 21
																				// -
																				// item03
		texture.add(new GL_Texture(game, "item04.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(22), 0, 0, 30, 30)); // 22
																				// -
																				// item04
		texture.add(new GL_Texture(game, "life00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(23), 0, 0, 30, 30)); // 23
																				// -
																				// life00

		texture.add(new GL_Texture(game, "play00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(24), 0, 0, 94, 94)); // 24
																				// -
																				// play00
		texture.add(new GL_Texture(game, "sound00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(25), 0, 0, 94, 94)); // 25
																				// -
																				// sound00
		texture.add(new GL_Texture(game, "sound01.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(26), 0, 0, 94, 94)); // 26
																				// -
																				// sound01
		texture.add(new GL_Texture(game, "space0.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(27), 0, 0, 362, 548)); // 27
																					// -
																					// space0
		texture.add(new GL_Texture(game, "spear00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(28), 0, 0, 25, 25)); // 28
																				// -
																				// spear00
		texture.add(new GL_Texture(game, "stop00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(29), 0, 0, 94, 94)); // 29
																				// -
																				// stop00

		texture.add(new GL_Texture(game, "vibrator00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(30), 0, 0, 94, 94)); // 30
																				// -
																				// vibrator00
		texture.add(new GL_Texture(game, "vibrator01.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(31), 0, 0, 94, 94)); // 31
																				// -
																				// vibrator01

		texture.add(new GL_Texture(game, "move.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(32), 0, 0, 52, 55)); // 32
																				// -
																				// back
																				// unit

		texture.add(new GL_Texture(game, "help00.png"));
		textureRegion.add(new GL_TextureRegion(texture.get(33), 0, 0, 480, 320)); // 33
																					// help

		texture.add(new GL_Texture(game, "help01.png")); // 34 - help 01
		textureRegion.add(new GL_TextureRegion(texture.get(34), 0, 0, 480, 320)); // 34
																					// help01

		texture.add(new GL_Texture(game, "help02.png")); // 35 - help 02
		textureRegion.add(new GL_TextureRegion(texture.get(35), 0, 0, 480, 320)); // 35
																					// help01

		texture.add(new GL_Texture(game, "help03.png")); // 36 - help 03
		textureRegion.add(new GL_TextureRegion(texture.get(36), 0, 0, 480, 320)); // 36
																					// help01
		// texture
		texture.add(new GL_Texture(game, "play01.png")); // 37 - play 01
		textureRegion.add(new GL_TextureRegion(texture.get(37), 0, 0, 94, 94));

		texture.add(new GL_Texture(game, "stop01.png")); // 38 - stop 01
		textureRegion.add(new GL_TextureRegion(texture.get(38), 0, 0, 94, 94));

		texture.add(new GL_Texture(game, "ball.png")); // 39 ball
		textureRegion.add(new GL_TextureRegion(texture.get(39), 0, 0, 60, 59)); // 39
																				// region;핑크
		textureRegion.add(new GL_TextureRegion(texture.get(39), 61, 0, 60, 59)); // 40
																					// region;빨
		textureRegion.add(new GL_TextureRegion(texture.get(39), 121, 0, 60, 59)); // 41region;주
		textureRegion.add(new GL_TextureRegion(texture.get(39), 181, 0, 60, 59)); // 42region;노
		textureRegion.add(new GL_TextureRegion(texture.get(39), 241, 0, 60, 59)); // 43region;초
		textureRegion.add(new GL_TextureRegion(texture.get(39), 301, 0, 60, 59)); // 44region;파

		texture.add(new GL_Texture(game, "missile.png")); // 40 missle
		textureRegion.add(new GL_TextureRegion(texture.get(40), 0, 0, 30, 30)); // 45region
																				// m-보
		textureRegion.add(new GL_TextureRegion(texture.get(40), 31, 0, 30, 30)); // 46region
																					// m-분
		textureRegion.add(new GL_TextureRegion(texture.get(40), 61, 0, 30, 30)); // 47region
																					// m-노

		texture.add(new GL_Texture(game, "spear.png")); // 41 star_missile
		textureRegion.add(new GL_TextureRegion(texture.get(41), 0, 0, 25, 25)); // 48region
																				// s_m_노
		textureRegion.add(new GL_TextureRegion(texture.get(41), 26, 0, 25, 25)); // 49region
																					// s_m_주
		textureRegion.add(new GL_TextureRegion(texture.get(41), 51, 0, 25, 25)); // 50region
																					// s_m_보

		textureRegion.add(new GL_TextureRegion(texture.get(3), 1092, 0, 1091, 672)); // 51
																						// back
																						// 03
		textureRegion.add(new GL_TextureRegion(texture.get(3), 2183, 0, 1091, 672)); // 52
																						// back
																						// 04

		textureRegion.add(new GL_TextureRegion(texture.get(32), 400, 0, 54, 57)); // 53
																					// girl
																					// front

		texture.add(new GL_Texture(game, "boss.png")); // 42 boss base
		textureRegion.add(new GL_TextureRegion(texture.get(42), 0, 0, 100, 100)); // 54
																					// boss

		texture.add(new GL_Texture(game, "level.png")); // 43 Level
		textureRegion.add(new GL_TextureRegion(texture.get(43), 0, 0, 94, 94)); // 55
																				// level
																				// 1
		textureRegion.add(new GL_TextureRegion(texture.get(43), 95, 0, 94, 94)); // 56
																					// level
																					// 2
		textureRegion.add(new GL_TextureRegion(texture.get(43), 189, 0, 94, 94)); // 57
																					// level
																					// 3
		textureRegion.add(new GL_TextureRegion(texture.get(43), 283, 0, 94, 94)); // 58
																					// level
																					// 4

		texture.add(new GL_Texture(game, "immortal.png")); // 44 immortal
		textureRegion.add(new GL_TextureRegion(texture.get(44), 0, 0, 94, 94)); // 59
																				// immo
																				// of
		textureRegion.add(new GL_TextureRegion(texture.get(44), 95, 0, 94, 94)); // 60
																					// immo
																					// on

		texture.add(new GL_Texture(game, "img_pangpang.png")); // 45 Logo
		textureRegion.add(new GL_TextureRegion(texture.get(45), 0, 0, 217, 217)); // 61
																					// Logo

		texture.add(new GL_Texture(game, "item.png")); // 46
		textureRegion.add(new GL_TextureRegion(texture.get(46), 0, 0, 20, 20)); // 62
																				// item1
		textureRegion.add(new GL_TextureRegion(texture.get(46), 21, 0, 20, 20)); // 63
																					// item2
		textureRegion.add(new GL_TextureRegion(texture.get(46), 41, 0, 20, 20)); // 64
																					// item3
		textureRegion.add(new GL_TextureRegion(texture.get(46), 61, 0, 20, 20)); // 65
																					// item4
		textureRegion.add(new GL_TextureRegion(texture.get(46), 81, 0, 20, 20)); // 66
																					// item5
		textureRegion.add(new GL_TextureRegion(texture.get(46), 101, 0, 20, 20)); // 67
																					// item6

		animation.add(new GL_Animation(0.2f, new GL_TextureRegion(texture.get(32), 100, 0, 29, 54),
				new GL_TextureRegion(texture.get(32), 200, 0, 29, 54),
				new GL_TextureRegion(texture.get(32), 300, 0, 29, 54))); // ani
																			// 00
																			// -
																			// right
																			// move

		animation.add(new GL_Animation(0.2f, new GL_TextureRegion(texture.get(32), 150, 0, 29, 54),
				new GL_TextureRegion(texture.get(32), 250, 0, 29, 54),
				new GL_TextureRegion(texture.get(32), 350, 0, 29, 54))); // ani
																			// -01
																			// -left
																			// move

		// ---- externer resource ----//

		items = new GL_Texture(game, "items.png");
		mainMenu = new GL_TextureRegion(items, 0, 224, 300, 110);
		pauseMenu = new GL_TextureRegion(items, 224, 128, 192, 96);
		ready = new GL_TextureRegion(items, 320, 224, 192, 32);
		gameOver = new GL_TextureRegion(items, 352, 256, 160, 96);
		highScoresRegion = new GL_TextureRegion(items, 0, 257, 300, 110 / 3);
		logo = new GL_TextureRegion(items, 0, 352, 274, 142);
		soundOff = new GL_TextureRegion(items, 0, 0, 64, 64);
		soundOn = new GL_TextureRegion(items, 64, 0, 64, 64);
		arrow = new GL_TextureRegion(items, 0, 64, 64, 64);
		pause = new GL_TextureRegion(items, 64, 64, 64, 64);
		castle = new GL_TextureRegion(items, 128, 64, 64, 64);
		font = new GL_Font(items, 224, 0, 16, 16, 20);

		coinAnim = new GL_Animation(0.2f, new GL_TextureRegion(items, 128, 32, 32, 32),
				new GL_TextureRegion(items, 160, 32, 32, 32), new GL_TextureRegion(items, 192, 32, 32, 32),
				new GL_TextureRegion(items, 160, 32, 32, 32));

		music = game.getAudio().newMusic("background.mp3");
		music.setLooping(true);
		music.setVolume(0.5f);
		if (Manage_Settings.soundEnabled)
			music.play();

	}

	public static void reload() {
		int len = texture.size();

		for (int i = 0; i < len; i++)
			texture.get(i).reload();

		items.reload();

		if (Manage_Settings.soundEnabled)
			music.play();
	}

	public static void playSound(IFace_Sound sound) {

	}
}

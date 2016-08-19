
package jrcengine.NetworkModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import jrcengine.Basic.GL_Screen;
import jrcengine.Manage.Manage_Assets;

public class NetworkModule {

	private static Thread thread;

	private static Socket socket;

	private static Timer msTimer;

	private static TimerTask msSecond;

	private static OutputStream outputStream;

	private static String id = "test";

	private static String password = "1";

	private static GL_Screen glMainGame;

	private static String IP_ADDRESS = "192.168.0.5";

	public static void startClient() {

		if (socket != null)
			return;

		thread = new Thread() {
			@Override
			public void run() {

				Log.d("mobile Network Module", "start success");
				try {
					socket = new Socket();
					// set the connection first argument is the server IP
					// address and next is port number
					socket.connect(new InetSocketAddress(IP_ADDRESS, 5001));

					msTimer = new Timer();
					msSecond = new TimerTask() {
						public void run() {

							sendPacket(Manage_Assets.NetworkProtocol._ACCESS_THE_SERVER + "");

						}
					};

					// this thread run per 7seconds
					msTimer.schedule(msSecond, 0, 5000);

				} catch (Exception e) {

					System.err.println("you cannot connect the server");
					if (!socket.isClosed()) {
						stopClient();
					}

				}

				sendPacket(Manage_Assets.NetworkProtocol._REQUEST_MOBILE_LOGIN + "", id, password);

				receive();

			}
		};
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();

	}

	public static void setglMainGame(GL_Screen gl_game) {
		glMainGame = gl_game;
	}

	public static void receive() {

		while (true) {

			try {

				byte[] byteArr = new byte[10000];
				InputStream inputStream = socket.getInputStream();
				// 서버가 비정상적으로 종료했을 경우 IOException 발생

				int readByteCount = inputStream.read(byteArr); // 서버가 정상적으로
				// Socket의 close()를 호출했을 경우
				if (readByteCount == -1) {
					throw new IOException();
				}

				String data = new String(byteArr, 0, readByteCount, "UTF-8");
				// Log.d("mobile Network Module", data);

				/*
				 * processing all packet using next method for splitting the
				 * packet
				 */
				String[] multiplePackets = SplitPacketManager
						.splitMultiplePacket(EncryptionManager.decrypt64bits(data));

				for (int i = 0; i < multiplePackets.length; i++) {

					/*
					 * processing all packet particle using next method for
					 * splitting the packet
					 */
					String[] splitPacket = SplitPacketManager.splitProtocol(multiplePackets[i]);

					int protocol = Integer.parseInt(splitPacket[0]);

					switch (protocol) {
					case Manage_Assets.NetworkProtocol._ANSWER_LOGIN:
						Log.d("mobile Network Module", "Login success id:" + id + " password:" + password);
						break;

					case Manage_Assets.NetworkProtocol._ANSWER_MOBILE_PANGPANG_SCORE:
						Log.d("testing", splitPacket[1]);
						break;

					default:
						System.err.println("[" + protocol + "] this protocol do not exist in the protocol list");
						break;

					}
				}

			} catch (Exception e) {
				System.err.println("[" + e + "] you cannot connect the server - receiving Error");
				stopClient();
				break;

			}

		}

	}

	/**
	 * send message to the game server
	 * 
	 * @param partitionPacketNumber
	 * @param datas
	 */
	public static void sendPacket(String... datas) {
		String packet = new String();

		packet = packet.concat(Manage_Assets.NetworkProtocol.sSenderSplitProtocolToken);

		for (int i = 0; i < datas.length; i++) {
			packet = packet.concat(datas[i] + Manage_Assets.NetworkProtocol.sSenderSplitProtocolToken);
		}
		packet = packet.concat(Manage_Assets.NetworkProtocol.sSenderSplitMultipleToken);

		send(packet);
	}

	/**
	 * send packet
	 * 
	 * @param data
	 */
	public static void send(final String data) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					String _data = data;

					byte[] byteArr = EncryptionManager.encrypt64bits(_data).getBytes("UTF-8");
					outputStream = socket.getOutputStream();
					outputStream.write(byteArr);
					outputStream.flush();
				} catch (Exception e) {
					System.err.println("[서버 통신 안됨] : " + e);
					stopClient();
				}
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	public static void stopClient() {
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}

			if (thread != null && thread.isAlive())
				thread.interrupt();
			if (msTimer != null) {
				msTimer.cancel();
				msTimer = null;
			}
			if (msSecond != null) {
				msSecond.cancel();
				msSecond = null;
			}

		} catch (IOException e) {
		}
	}

}

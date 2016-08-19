package jrcengine.NetworkModule;

import jrcengine.Manage.Manage_Assets;

/**
 * @author KJW finish at 2016/ 08/ 11
 * @version 2.0.0v
 * @description this class split the packet using Token defined in
 *              Settings.class
 * @copyRight of KJW all Rights Reserved and follow the MIT license
 */
public class SplitPacketManager {

	/**
	 * split the multiple packet to packets
	 * 
	 * @param packet
	 * @return
	 */
	public static String[] splitMultiplePacket(String packet) {

		String _partitioningPacket[] = packet.split(Manage_Assets.NetworkProtocol.sReceiverSplitMultipleToken);
		return _partitioningPacket;

	}

	/**
	 * split the packet base on the programmer's rule
	 * 
	 * @param packet
	 * @return
	 */
	public static String[] splitProtocol(String packet) {

		String _partitioningPacket[] = packet.split(Manage_Assets.NetworkProtocol.sReceiverSplitProtocolToken);

		for (int i = 0; i < _partitioningPacket.length - 1; i++) {
			_partitioningPacket[i] = _partitioningPacket[i + 1];
		}

		_partitioningPacket[_partitioningPacket.length - 1] = null;

		return _partitioningPacket;

	}
}

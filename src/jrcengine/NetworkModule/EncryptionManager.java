
package jrcengine.NetworkModule;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import jrcengine.Manage.Manage_Assets;

/**
 * @author KJW finish at 2016/ 08/ 11
 * @version 2.0.0v
 * @description this class encryption and decyription class
 * @copyRight of KJW all Rights Reserved and follow the MIT license
 */
public class EncryptionManager {
	/**
	 * this method change the message String to encrypt String using 32bits AES
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String encrypt16bits(String message) throws Exception {

		// use key coss2
		SecretKeySpec skeySpec = new SecretKeySpec(Manage_Assets.NetworkProtocol.sEncryptKey.getBytes(), "AES");

		// Instantiate the cipher
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypted = cipher.doFinal(message.getBytes());

		return Hex.encodeHexString(encrypted);

	}

	/**
	 * this method change the message encrypt String to normal String using
	 * 32bits AES
	 * 
	 * @param encrypted
	 * @return
	 * @throws Exception
	 */
	public static String decrypt16bits(String encrypted) throws Exception {

		// use key coss2
		SecretKeySpec skeySpec = new SecretKeySpec(Manage_Assets.NetworkProtocol.sEncryptKey.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		byte[] original = cipher.doFinal(Hex.decodeHex(encrypted.toCharArray()));

		String originalString = new String(original);

		return originalString;
	}

	/**
	 * this method generate key for 64bits AES Encryption
	 * 
	 * @return
	 */
	private static Key generateKey64bits() {
		byte[] keyValue = null;
		MessageDigest sha = null;
		try {
			keyValue = Manage_Assets.NetworkProtocol.keyStr.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			keyValue = sha.digest(keyValue);
			keyValue = Arrays.copyOf(keyValue, 16); // use only first 128 bit
			Key key = new SecretKeySpec(keyValue, Manage_Assets.NetworkProtocol.ALGO);
			return key;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

		return null;
	}

	/**
	 * this method change the message String to encrypt String using 64bits AES
	 * 
	 * @param Data
	 * @return
	 */
	public static String encrypt64bits(String Data) {
		Key key;
		Cipher c;
		byte[] encVal = null;
		try {
			key = generateKey64bits();
			c = Cipher.getInstance(Manage_Assets.NetworkProtocol.ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			encVal = c.doFinal(Data.getBytes());
			String encryptedValue = android.util.Base64.encodeToString(encVal, 16);
			return encryptedValue;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		return null;
	}

	/**
	 * this method change the message encrypt String to normal String using
	 * 64bits AES
	 * 
	 * if decpytion fail then returnERROR_PACKET(-1)
	 * 
	 * @param encryptedData
	 * @return
	 */
	public static String decrypt64bits(String encryptedData) {
		Key key;
		Cipher c;
		boolean ischeck = true;

		byte[] decValue = null;
		while (ischeck) {
			key = generateKey64bits();
			try {
				c = Cipher.getInstance(Manage_Assets.NetworkProtocol.ALGO);
				c.init(Cipher.DECRYPT_MODE, key);
				byte[] decordedValue;
				
				decordedValue = android.util.Base64.encode(encryptedData.getBytes(), 16);
				decValue = c.doFinal(decordedValue);

				String decryptedValue = new String(decValue);
				return decryptedValue;
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				ischeck = false;
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "/&" + Manage_Assets.NetworkProtocol._ERROR_PACKET + "/&@";

	}
}

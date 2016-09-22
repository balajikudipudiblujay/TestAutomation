package global.reusables;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class EncryptionUtil {
	private static String encryptionKey ="Blowfish";
	private static Base64 base64 = new Base64(true);
	//Pavan--Encryption using Blowfish Algorithm
	public static String encrypt(String Data)throws Exception{
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF8"), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return base64.encodeToString(cipher.doFinal(Data.getBytes("UTF8")));
	}
	//Pavan--Decryption using Blowfish Algorithm
	public static String decrypt(String encrypted)throws Exception{
		byte[] encryptedData = base64.decodeBase64(encrypted);
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF8"), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decrypted = cipher.doFinal(encryptedData);
		return new String(decrypted); 
	}
}
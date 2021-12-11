
package in.jaxer.core.encoders;

import in.jaxer.core.constants.Constants;
import in.jaxer.core.constants.ContentType;
import in.jaxer.core.utilities.JValidator;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Shakir Ansari
 */
public class AesEncoder
{

	private static Cipher cipher = null;

	private static byte[] getKeyBytes(String key)
	{
		JValidator.requireNotNull(key, "Key cannot be null");

		while (key.length() < 16)
		{
			key = key + "0";
		}

		if (key.length() != 16)
		{
			throw new IllegalArgumentException("Secret key should must be 16 char long only");
		}

//		if (key.length() > 16)
//		{
//			key = key.substring(0, 15);
//		}
//		System.out.println("AesEncryptor.checkKey() - key: [" + key + "], len: [" + key.length() + "]");
		return Base64.getDecoder().decode(key);
	}

	private static SecretKeySpec getSecretKeySpec(String key)
	{
		return new SecretKeySpec(Arrays.copyOf(getKeyBytes(key), 16), Constants.AES);
	}

	public static String encode(final String key, final String message)
	{
		try
		{
			if (cipher == null)
			{
				cipher = Cipher.getInstance(Constants.AES);
			}

			SecretKey secretKey = getSecretKeySpec(key);

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] cipherText = cipher.doFinal(message.getBytes(ContentType.UTF_8));

			return Base64.getEncoder().encodeToString(cipherText);

		} catch (Exception exception)
		{
			throw new RuntimeException("Error occured while encrypting message", exception);
		}

	}

	public static String decode(final String key, final String message)
	{
		try
		{
			if (cipher == null)
			{
				cipher = Cipher.getInstance(Constants.AES);
			}

			SecretKey secretKey = getSecretKeySpec(key);

			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(message));

			return new String(cipherText);
		} catch (Exception exception)
		{
			throw new RuntimeException("Error occured while decrypting message", exception);
		}
	}
}

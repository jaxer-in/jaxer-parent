
package in.jaxer.core.encoders;

import in.jaxer.core.constants.Constants;
import in.jaxer.core.constants.ContentType;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Shakir Ansari
 */
public class DesEncoder
{

	private static KeyGenerator keygenerator = null;

	private static SecretKey secretKey = null;

	private static Cipher cipher;

	private static void init() throws Exception
	{
		if (keygenerator == null)
		{
			keygenerator = KeyGenerator.getInstance(Constants.DES);
		}

		if (secretKey == null)
		{
			secretKey = keygenerator.generateKey();
		}

		if (cipher == null)
		{
			cipher = Cipher.getInstance(Constants.DES);
		}
	}

	public static String encode(final String message)
	{
		try
		{
			init();

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] cipherText = cipher.doFinal(message.getBytes(ContentType.UTF_8));

			return Base64.getEncoder().encodeToString(cipherText);

		} catch (Exception exception)
		{
			throw new RuntimeException("Error occured while encrypting message", exception);
		}
	}

	public static String decode(final String message)
	{
		try
		{
			init();

			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] cipherBytes = cipher.doFinal(Base64.getDecoder().decode(message));

			return new String(cipherBytes);

		} catch (Exception exception)
		{
			throw new RuntimeException("Error occured while decrypting message", exception);
		}
	}
}
package in.jaxer.core.encoders;

import in.jaxer.core.constants.Constants;
import in.jaxer.core.interfaces.Encoder;
import in.jaxer.core.utilities.JValidator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Shakir
 * @since 0.0.1
 */
public class DesEncoder implements Encoder
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

	@Override
	public String encode(final String message)
	{
		JValidator.throwWhenBlank(message);
		try
		{
			init();

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] cipherText = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

			return Base64.getEncoder().encodeToString(cipherText);

		} catch (Exception exception)
		{
			JValidator.rethrow(exception);
			return null;
		}
	}

	@Override
	public String decode(final String message)
	{
		JValidator.throwWhenBlank(message);
		try
		{
			init();

			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] cipherBytes = cipher.doFinal(Base64.getDecoder().decode(message));

			return new String(cipherBytes);

		} catch (Exception exception)
		{
			JValidator.rethrow(exception);
			return null;
		}
	}
}

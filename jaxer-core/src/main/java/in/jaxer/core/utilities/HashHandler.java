package in.jaxer.core.utilities;

import in.jaxer.core.constants.Constants;
import in.jaxer.core.exceptions.JaxerCoreException;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Formatter;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class HashHandler
{
	private static String toHexString(MessageDigest digest)
	{
		return new BigInteger(1, digest.digest()).toString(16);
	}

	private static String toHexString(byte[] bytes)
	{
		Formatter formatter = new Formatter();
		for (byte b : bytes)
		{
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	private static String getHash(String msg, String encodeType)
	{
		log.debug("msg: {}, encodeType: {}", msg, encodeType);
		try
		{
			MessageDigest digest = MessageDigest.getInstance(encodeType);

			//Update msg string in message digest
			digest.update(msg.getBytes(), 0, msg.length());

			return toHexString(digest);
		} catch (Exception ex)
		{
			log.error("Exception", ex);
			throw new JaxerCoreException("Exception occured while generating hash", ex);
		}
	}

	private static String getHash(String msg, String key, String hashType)
	{
		log.debug("msg: {}, key: {}, hashType", msg, key, hashType);
		try
		{
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), hashType);
			Mac mac = Mac.getInstance(hashType);
			mac.init(signingKey);
			return toHexString(mac.doFinal(msg.getBytes()));
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerCoreException("Exception occured while generating hash", exception);
		}
	}

	private static String getFileChecksum(String encodeType, InputStream inputStream)
	{
		log.debug("encodeType: {}, inputStream: {}", encodeType, inputStream);
		try
		{
			MessageDigest digest = MessageDigest.getInstance(encodeType);

			byte[] byteArray = new byte[Constants.BUFFER_SIZE];
			int bytesCount = 0;
			while ((bytesCount = inputStream.read(byteArray)) != -1)
			{
				digest.update(byteArray, 0, bytesCount);
			}

			return toHexString(digest);

		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerCoreException("Exception occured while generating file hash", exception);
		}
	}

	private static String getFileChecksum(String encodeType, File file)
	{
		log.debug("encodeType: {}, file: {}", encodeType, file);

		try (FileInputStream fileInputStream = new FileInputStream(file))
		{
			return getFileChecksum(encodeType, fileInputStream);
		} catch (Exception ex)
		{
			log.error("Exception", ex);
			throw new JaxerCoreException("Exception occured while generating file hash", ex);
		}
	}

	//-- public
	public static String getMD5Hash(String msg)
	{
		return getHash(msg, Constants.MD5);
	}

	public static String getSHA1Hash(String msg)
	{
		return getHash(msg, Constants.SHA_1);
	}

	public static String getSHA256Hash(String msg)
	{
		return getHash(msg, Constants.SHA_256);
	}

	public static String getSHA512Hash(String msg)
	{
		return getHash(msg, Constants.SHA_512);
	}

	public static String getSHA1Hash(String msg, String key)
	{
		return getHash(msg, key, Constants.HMAC_SHA1);
	}

	public static String getSHA256Hash(String msg, String key)
	{
		return getHash(msg, key, Constants.HMAC_SHA256);
	}

	public static String getSHA512Hash(String msg, String key)
	{
		return getHash(msg, key, Constants.HMAC_SHA512);
	}

	public static String getFileChecksumMD5(File file)
	{
		return getFileChecksum(Constants.MD5, file);
	}

	public static String getFileChecksumSHA1(File file)
	{
		return getFileChecksum(Constants.SHA_1, file);
	}

	public static String getFileChecksumSHA256(File file)
	{
		return getFileChecksum(Constants.SHA_256, file);
	}

	public static String getFileChecksumSHA512(File file)
	{
		return getFileChecksum(Constants.SHA_512, file);
	}

	public static String getFileChecksumMD5(String file)
	{
		return getFileChecksum(Constants.MD5, new File(file));
	}

	public static String getFileChecksumSHA1(String file)
	{
		return getFileChecksum(Constants.SHA_1, new File(file));
	}

	public static String getFileChecksumSHA256(String file)
	{
		return getFileChecksum(Constants.SHA_256, new File(file));
	}

	public static String getFileChecksumSHA512(String file)
	{
		return getFileChecksum(Constants.SHA_512, new File(file));
	}

	@Deprecated
	public static String getFileChecksumMD5(InputStream inputStream)
	{
		return getFileChecksum(Constants.MD5, inputStream);
	}

	@Deprecated
	public static String getFileChecksumSHA1(InputStream inputStream)
	{
		return getFileChecksum(Constants.SHA_1, inputStream);
	}

	@Deprecated
	public static String getFileChecksumSHA256(InputStream inputStream)
	{
		return getFileChecksum(Constants.SHA_256, inputStream);
	}

	@Deprecated
	public static String getFileChecksumSHA512(InputStream inputStream)
	{
		return getFileChecksum(Constants.SHA_512, inputStream);
	}
}

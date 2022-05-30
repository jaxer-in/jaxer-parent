package in.jaxer.core.encoders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Shakir
 */
public class Base64Encoder
{
	public static byte[] encode(byte[] bytes)
	{
		return Base64.getEncoder().encode(bytes);
	}

	public static byte[] decode(byte[] bytes)
	{
		return Base64.getDecoder().decode(bytes);
	}

	public static String encode(String text)
	{
		return new String(encode(text.getBytes()));
	}

	public static String decode(String text)
	{
		return new String(decode(text.getBytes()));
	}

	public static void encodeImage(String sourceFile, String targetFile) throws IOException
	{
		encodeImage(new File(sourceFile), new File(targetFile));
	}

	public static void decodeImage(String sourceFile, String targetFile) throws IOException
	{
		decodeImage(new File(sourceFile), new File(targetFile));
	}

	public static void encodeImage(File sourceFile, File targetFile) throws IOException
	{
		try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
			 FileOutputStream fileOutputStream = new FileOutputStream(targetFile))
		{
			final byte[] byteArray = new byte[(int) sourceFile.length()];
			fileInputStream.read(byteArray);
			String file64 = Base64.getEncoder().encodeToString(byteArray);
			fileOutputStream.write(file64.getBytes());
		}
	}

	public static void decodeImage(File sourceFile, File targetFile) throws IOException
	{
		try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
			 FileOutputStream fileOutputStream = new FileOutputStream(targetFile))
		{
			final byte[] byteArray = new byte[(int) sourceFile.length()];
			fileInputStream.read(byteArray);
			fileOutputStream.write(Base64.getDecoder().decode(new String(byteArray)));
		}
	}
}

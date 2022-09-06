package in.jaxer.core.utilities;

import in.jaxer.core.HttpUtils;
import in.jaxer.core.exceptions.JaxerCoreException;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class JUtilities
{
	/**
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.MathUtils#min(int...)}
	 */
	@Deprecated
	public static int min(int... values)
	{
		int min = values[0];
		for (int val : values)
		{
			min = Math.min(min, val);
		}
		return min;
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.MathUtils#max(int...)}
	 */
	@Deprecated
	public static int max(int... values)
	{
		int max = values[0];
		for (int value : values)
		{
			max = Math.max(max, value);
		}
		return max;
	}

	public static void reverse(byte[] bytes, int len)
	{
		for (int i = 0; i < len / 2; i++)
		{
			byte t = bytes[i];
			bytes[i] = bytes[len - i - 1];
			bytes[len - i - 1] = t;
		}
	}

	public static boolean isValidUrl(String urlString)
	{
		try
		{
			Object object = new URL(urlString).getContent();
			return true;
		} catch (Exception exception)
		{
			return false;
		}
	}

	public static boolean isHtmlString(String str)
	{
		log.debug("str: {}", str);
		Pattern htmlPattern = Pattern.compile(".*\\<[^>]+>.*", Pattern.DOTALL);
		return htmlPattern.matcher(str).matches();
	}

	public static String getExtension(String str)
	{
		log.debug("str: {}", str);

		return str.contains(".") ? str.substring(str.lastIndexOf(".") + 1) : null;
	}

	public static String getExtensionWithDot(String sourceImage)
	{
		log.debug("sourceImage: {}", sourceImage);

		String ext = getExtension(sourceImage);
		return ext == null ? null : "." + ext;
	}

	public static Dimension getImageDimension(String sourceImageFile)
	{
		log.debug("sourceImageFile: {}", sourceImageFile);
		return getImageDimension(new File(sourceImageFile));
	}

	public static Dimension getImageDimension(File imgFile)
	{
		log.debug("imgFile: {}", imgFile);
		try (InputStream inputStream = new FileInputStream(imgFile))
		{
			return getImageDimension(inputStream);
		} catch (Exception e)
		{
			throw new JaxerCoreException(e);
		}
	}

	public static Dimension getImageDimension(InputStream inputStream)
	{
		log.debug("inputStream: {}", inputStream);
		try
		{
			return getImageDimension(ImageIO.read(inputStream));
		} catch (Exception e)
		{
			throw new JaxerCoreException(e);
		}
	}

	public static Dimension getImageDimension(BufferedImage bufferedImage)
	{
		log.debug("bufferedImage: {}", bufferedImage);
		return bufferedImage == null ? null : new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
	}

	public static Dimension getScreenDimension()
	{
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public static void beep()
	{
		Toolkit.getDefaultToolkit().beep();
	}

	public static boolean isUrl(String urlString)
	{
		if (JValidator.isNullOrEmpty(urlString))
		{
			return false;
		}

		String tempUrlString = urlString.toLowerCase().trim();

		return (tempUrlString.startsWith("https://") || tempUrlString.startsWith("http://"));
	}

	public static String getHTML(String urlToRead) throws Exception
	{
		StringBuilder result = new StringBuilder();

		URL url = new URL(urlToRead);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestMethod(HttpUtils.Method.GET);

		try (InputStream inputStream = httpURLConnection.getInputStream();
			 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			 BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
		{
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				result.append(line).append(System.lineSeparator());
			}
		}

		return result.toString();
	}

	public static void sleep(long mili)
	{
		if (mili <= 0)
		{
			return;
		}

		try
		{
			Thread.sleep(mili);
		} catch (InterruptedException e)
		{
			throw new JaxerCoreException(e);
		}
	}

	public static void sleep()
	{
		sleep(2000);
	}

	public static URL getRealUrlBehindRedirect(String url)
	{
		log.debug("url: {}", url);
		try
		{
			URLConnection uRLConnection = new URL(url).openConnection();
			log.debug("Original url: {}", uRLConnection.getURL());

			uRLConnection.connect();
			log.debug("Connected url: {}", uRLConnection.getURL());

			try (InputStream inputStream = uRLConnection.getInputStream())
			{
				String redirectedUrl = uRLConnection.getURL().toString();
				log.debug("redirectedUrl: {}", redirectedUrl);

				String path = uRLConnection.getURL().getPath();
				log.debug("path: {}", path);

				return uRLConnection.getURL();
			}
		} catch (Exception exception)
		{
			throw new JaxerCoreException("Exception occured while reading real URL behind a redirect", exception);
		}
	}

	/**
	 * @deprecated, please use {@link in.jaxer.core.MathUtils#getPercentage(int, int)}
	 */
	@Deprecated
	public static int getPercentage(int value, int percentage)
	{
		return value * percentage / 100;
	}

	/**
	 * @deprecated, please use {@link in.jaxer.core.MathUtils#getPercentage(double, float)}
	 */
	@Deprecated
	public static double getPercentage(double value, float percentage)
	{
		return value * percentage / 100;
	}

	/**
	 * @deprecated please use {@link JsonHandler#toJsonString(Object)}
	 */
	@Deprecated
	public static String toJsonString(Object object)
	{
		return object == null ? null : JsonHandler.getGson().toJson(object);
	}

	/**
	 * @deprecated please use {@link JsonHandler#toObject(String, Class)}
	 */
	@Deprecated
	public static <T> T toObject(String jsonString, Class<T> clazz)
	{
		return JValidator.isNullOrEmpty(jsonString) ? null : JsonHandler.getGson().fromJson(jsonString, clazz);
	}

	/**
	 * @deprecated please use {@link JsonHandler#toObjectList(String, Class)}
	 */
	@Deprecated
	public static <T> List<T> toObjectList(String jsonString, Class<T> clazz)
	{
		return JValidator.isNullOrEmpty(jsonString) ? null : JsonHandler.toObjectList(jsonString, clazz);
	}

	public static void close(AutoCloseable autoCloseable)
	{
		try
		{
			autoCloseable.close();
			log.debug("autoCloseable.close() executed successfully: {}", autoCloseable.toString());
		} catch (Exception exception)
		{
			log.warn("autoCloseable.close() executed unsuccessfully: {}", autoCloseable.toString());
		}
	}

	public static void consoleLoadingAnimation() throws Exception
	{
		String anim = "|/-\\";
		for (int x = 0; x <= 100; x++)
		{
			String data = "\r" + anim.charAt(x % anim.length()) + " " + x + "%";
			System.out.write(data.getBytes());
			Thread.sleep(25);
		}
		System.out.println();
	}

	public static String getLocalHostAddress()
	{
		try
		{
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception exception)
		{
			throw new JaxerCoreException(exception);
		}
	}

	public static String getLocalHostAddressV4()
	{
		try
		{
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (Exception exception)
		{
			throw new JaxerCoreException(exception);
		}
	}

	public static String getLocalHostAddressV6()
	{
		try
		{
			return Inet6Address.getLocalHost().getHostAddress();
		} catch (Exception exception)
		{
			throw new JaxerCoreException(exception);
		}
	}

	public static BufferedImage getScreenShot()
	{
		try
		{
			return new Robot().createScreenCapture(new Rectangle(JUtilities.getScreenDimension()));
		} catch (Exception exception)
		{
			throw new JaxerCoreException((exception));
		}
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.NumberUtils#parseNumber(String)}
	 */
	@Deprecated
	public static Number parseNumber(String str)
	{
		try
		{
			return NumberFormat.getInstance(Locale.US).parse(str);
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Range for byte(1 byte) is -128 to 127
	 *
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.NumberUtils#toByte(String, byte)}
	 */
	@Deprecated
	public static byte parseByte(String str, byte defaultValue)
	{
		try
		{
			return parseNumber(str).byteValue();
		} catch (Exception ex)
		{
			return defaultValue;
		}
	}

	/**
	 * Range for short(2 bytes) is -32,768 to 32,767
	 *
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.NumberUtils#toShort(String, short)}
	 */
	@Deprecated
	public static short parseShort(String str, short defaultValue)
	{
		try
		{
			return Short.parseShort(str);
		} catch (Exception ex)
		{
			return defaultValue;
		}
	}

	/**
	 * Range for int(4 bytes) is -2,147,483,648 to 2,147,483,647
	 *
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.NumberUtils#toInt(String, int)}
	 */
	@Deprecated
	public static int parseInt(String str, int defaultValue)
	{
		try
		{
			return Integer.parseInt(str);
		} catch (Exception ex)
		{
			return defaultValue;
		}
	}

	/**
	 * Range for long(8 bytes) is -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
	 *
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.NumberUtils#toLong(String, long)}
	 */
	@Deprecated
	public static long parseLong(String str, long defaultValue)
	{
		try
		{
			return Long.parseLong(str);
		} catch (Exception ex)
		{
			return defaultValue;
		}
	}

	/**
	 * Range for float(4 bytes) is sufficient for storing 6 to 7 decimal digits
	 *
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.NumberUtils#toFloat(String, float)}
	 */
	@Deprecated
	public static float parseFloat(String str, float defaultValue)
	{
		try
		{
			return Float.parseFloat(str);
		} catch (Exception ex)
		{
			return defaultValue;
		}
	}

	/**
	 * Range for double(8 bytes) is sufficient for storing 15 decimal digits
	 *
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.NumberUtils#toDouble(String, double)}
	 */
	@Deprecated
	public static double parseDouble(String str, double defaultValue)
	{
		try
		{
			return parseNumber(str).doubleValue();
		} catch (Exception ex)
		{
			return defaultValue;
		}
	}

	/**
	 * Range for boolean(8 bit) is sufficient for storing true or false
	 */
	public static boolean parseBoolean(String str, boolean defaultValue)
	{
		try
		{
			return Boolean.parseBoolean(str);
		} catch (Exception ex)
		{
			return defaultValue;
		}
	}
}

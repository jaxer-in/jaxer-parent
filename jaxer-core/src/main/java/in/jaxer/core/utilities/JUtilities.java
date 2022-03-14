
package in.jaxer.core.utilities;

import in.jaxer.core.constants.HttpConstants;
import in.jaxer.core.constants.Singletons;
import in.jaxer.core.exceptions.JaxerCoreException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
public class JUtilities
{

	public static int min(int... values)
	{
		int min = 0;
		for (int val : values)
		{
			min = Math.min(min, val);
		}
		return min;
	}

	public static int max(int... values)
	{
		int max = 0;
		for (int value : values)
		{
			max = Math.max(max, value);
		}
		return max;
	}

	public static void reverse(byte bytes[], int len)
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
			exception.printStackTrace();
			return false;
		}
	}

	public static boolean isHtmlString(String str)
	{
		log.debug("str: {}", str);
		Pattern htmlPattern = Pattern.compile(".*\\<[^>]+>.*", Pattern.DOTALL);
		return htmlPattern.matcher(str).matches();
	}

	public static String getExtension(String sourceImage)
	{
		log.debug("sourceImage: {}", sourceImage);
		if (sourceImage.contains("."))
		{
			return sourceImage.substring(sourceImage.lastIndexOf(".") + 1);
		}
		return null;
	}

	public static String getExtensionWithDot(String sourceImage)
	{
		log.debug("sourceImage: {}", sourceImage);

		String ext = getExtension(sourceImage);
		return ext == null ? null : "." + ext;
	}

	public static Dimension getImageDimension(String sourceImageFile) throws IOException
	{
		log.debug("sourceImageFile: {}", sourceImageFile);
		return getImageDimension(new File(sourceImageFile));
	}

	public static Dimension getImageDimension(File imgFile) throws IOException
	{
		log.debug("imgFile: {}", imgFile);
		try (InputStream inputStream = new FileInputStream(imgFile))
		{
			return getImageDimension(inputStream);
		}
//		return getImageDimension(ImageIO.read(imgFile));
	}

	public static Dimension getImageDimension(InputStream inputStream) throws IOException
	{
		log.debug("inputStream: {}", inputStream);
		return getImageDimension(ImageIO.read(inputStream));
	}

	public static Dimension getImageDimension(BufferedImage bufferedImage) throws IOException
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
		if (JValidator.isEmpty(urlString))
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
		httpURLConnection.setRequestMethod(HttpConstants.GET);

		try (InputStream inputStream = httpURLConnection.getInputStream();
			 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);)
		{
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				result.append(line).append(System.lineSeparator());
			}
		}

		return result.toString();
	}

	public static String getNotNull(Object data, String defaultValue)
	{
		if (data == null)
		{
			return defaultValue;
		} else
		{
			return data.toString();
		}
	}

	@Deprecated
	public static String getNotNull(Object data)
	{
		return getNotNull(data, "");
	}

	@Deprecated
	public static String toString(Object object)
	{
		return object == null ? null : object.toString();
	}

	@Deprecated
	public static String toString(int i)
	{
		return String.valueOf(i);
	}

	@Deprecated
	public static String toString(String string)
	{
		return string;
	}

	@Deprecated
	public static String toString(Date date)
	{
		return date == null ? null : String.valueOf(date.getTime());
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
			throw new RuntimeException(e);
		}
	}

	public static void sleep()
	{
		sleep(2000);
	}

	public static URL getRealUrlBehindRedirect(String url)
	{
		try
		{
			URLConnection uRLConnection = new URL(url).openConnection();
			System.out.println("Utilities.getRealUrlBehindRedirect() - Orignal url: " + uRLConnection.getURL());
			uRLConnection.connect();
			System.out.println("Utilities.getRealUrlBehindRedirect() - Connected url: " + uRLConnection.getURL());

			try (InputStream inputStream = uRLConnection.getInputStream();)
			{
				String redirectedUrl = uRLConnection.getURL().toString();
				String path = uRLConnection.getURL().getPath();
				System.out.println("Utilities.getRealUrlBehindRedirect() - Redirected url: " + redirectedUrl);
				System.out.println("Utilities.getRealUrlBehindRedirect() - path: " + path);
				return uRLConnection.getURL();
			}
		} catch (Exception exception)
		{
			throw new RuntimeException("Exception occured while reading real URL behind a redirect", exception);
		}
	}

	/**
	 *
	 * @param value
	 * @param percentage
	 * @return
	 * @deprecated
	 * @see getPercentage(double, float)
	 */
	@Deprecated
	public static int getPercentage(int value, int percentage)
	{
		return value * percentage / 100;
	}

	public static double getPercentage(double value, float percentage)
	{
		return value * percentage / 100;
	}

	public static String toJsonString(Object object)
	{
		return object == null ? null : Singletons.getGson().toJson(object);
	}

	public static <T> T toObject(String jsonString, Class<T> clazz)
	{
		return JValidator.isEmpty(jsonString) ? null : Singletons.getGson().fromJson(jsonString, clazz);
	}

	public static <T> List<T> toObjectList(String jsonString, Class<T> clazz)
	{
		return JValidator.isEmpty(jsonString) ? null : Collections.toList(jsonString, clazz);
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
		System.out.println("");
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
}

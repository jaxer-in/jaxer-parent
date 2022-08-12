package in.jaxer.core;

import lombok.extern.log4j.Log4j2;

import java.util.Properties;

/**
 * @author Shakir
 * @date 2022-08-13
 * @since v1.1.0-beta
 */
@Log4j2
public class SystemUtils
{
	/**
	 * @since v1.1.0-beta
	 */
	public static void setProperty(String key, String value)
	{
		log.info("key: {}, value: {}", key, value);
		System.setProperty(key, value);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static String getProperty(String key)
	{
		log.info("key: {}", key);
		return System.getProperty(key);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static String clearProperty(String key)
	{
		log.info("key: {}", key);
		return System.clearProperty(key);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static String getOsName()
	{
		return SystemUtils.getProperty("os.name");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static boolean isMac()
	{
		return getOsName().toLowerCase().contains("mac");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static boolean isWindows()
	{
		return getOsName().toLowerCase().contains("win");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static boolean isSolaris()
	{
		return getOsName().toLowerCase().contains("sunos");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static boolean isUnix()
	{
		String osName = getOsName();
		log.info("osName: {}", osName);

		return osName.toLowerCase().contains("nix")
				|| osName.toLowerCase().contains("nux")
				|| osName.toLowerCase().contains("aix");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static String getClasspathFromProperty()
	{
		return System.getProperty("java.class.path");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static String getUserHomeDirectory()
	{
		return SystemUtils.getProperty("user.home");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static String getPresentWorkingDirectory()
	{
		return SystemUtils.getProperty("user.dir");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static String getTempDirectory()
	{
		return SystemUtils.getProperty("java.io.tmpdir");
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static void printAllProperties()
	{
		Properties properties = System.getProperties();
		for (String key : properties.stringPropertyNames())
		{
			String value = properties.getProperty(key);
			log.info("[{} = {}]", key, value);
		}
	}
}

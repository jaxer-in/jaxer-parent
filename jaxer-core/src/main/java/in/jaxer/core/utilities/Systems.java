package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;

import java.util.Properties;

/**
 * @author Shakir Ansari
 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.SystemUtils}
 */
@Log4j2
@Deprecated
public class Systems
{
	public static void setProperty(String key, String value)
	{
		log.info("key: {}, value: {}", key, value);
		System.setProperty(key, value);
	}

	public static String getProperty(String key)
	{
		log.info("key: {}", key);
		return System.getProperty(key);
	}

	public static String clearProperty(String key)
	{
		log.info("key: {}", key);
		return System.clearProperty(key);
	}

	public static String getOsName()
	{
		return Systems.getProperty("os.name");
	}

	public static boolean isMac()
	{
		return getOsName().toLowerCase().contains("mac");
	}

	public static boolean isWindows()
	{
		return getOsName().toLowerCase().contains("win");
	}

	public static boolean isSolaris()
	{
		return getOsName().toLowerCase().contains("sunos");
	}

	public static boolean isUnix()
	{
		String osName = getOsName();
		log.info("osName: {}", osName);

		return osName.toLowerCase().contains("nix")
				|| osName.toLowerCase().contains("nux")
				|| osName.toLowerCase().contains("aix");
	}

	public static String getClasspathFromProperty()
	{
		return System.getProperty("java.class.path");
	}

	public static String getUserHomeDirectory()
	{
		return Systems.getProperty("user.home");
	}

	public static String getPresentWorkingDirectory()
	{
		return Systems.getProperty("user.dir");
	}

	public static String getTempDirectory()
	{
		return Systems.getProperty("java.io.tmpdir");
	}

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

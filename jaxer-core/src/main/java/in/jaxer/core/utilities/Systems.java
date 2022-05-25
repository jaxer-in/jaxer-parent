package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;

import java.util.Properties;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class Systems
{
	public static void setProperty(String key, String value)
	{
		System.setProperty(key, value);
	}

	public static String getProperty(String key)
	{
		return System.getProperty(key);
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
		return getOsName().toLowerCase().contains("nix") || getOsName().toLowerCase().contains("nux") || getOsName().toLowerCase().contains("aix");
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

			log.debug("[{} = {}]", key, value);
			System.out.println("[" + key + " = " + value + "]");
		}
	}
}


package in.jaxer.core.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.AWTException;
import java.awt.Robot;
import java.text.SimpleDateFormat;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author Shakir Ansari
 */
public class Singletons
{

	private static SimpleDateFormat simpleDateFormat;

	private static Gson gsonPretty;

	private static Gson gson;

	private static MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

	private static Robot robot = null;

	public static SimpleDateFormat getSimpleDateFormat()
	{
		if (simpleDateFormat == null)
		{
			simpleDateFormat = new SimpleDateFormat();
		}
		return simpleDateFormat;
	}

	public static Gson getGson(boolean pretty)
	{
		if (pretty)
		{
			if (gsonPretty == null)
			{
				gsonPretty = new GsonBuilder().setPrettyPrinting().create();
			}
			return gsonPretty;
		} else
		{
			if (gson == null)
			{
				gson = new Gson();
			}
			return gson;
		}
	}

	public static MimetypesFileTypeMap getMimetypesFileTypeMap()
	{
		if (mimetypesFileTypeMap == null)
		{
			mimetypesFileTypeMap = new MimetypesFileTypeMap();
		}
		return mimetypesFileTypeMap;
	}

	public static Robot getRobot() throws AWTException
	{
		if (robot == null)
		{
			robot = new Robot();
		}
		return robot;
	}
}

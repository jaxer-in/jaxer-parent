package in.jaxer.core.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.activation.MimetypesFileTypeMap;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * @author Shakir
 */
public class Singletons
{
	@Deprecated
	private static SimpleDateFormat simpleDateFormat;

	@Deprecated
	private static Gson gsonPretty;

	@Deprecated
	private static Gson gson;

	private static MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

	@Deprecated
	private static Robot robot = null;

	@Deprecated
	public static SimpleDateFormat getSimpleDateFormat()
	{
		if (simpleDateFormat == null)
		{
			simpleDateFormat = new SimpleDateFormat();
		}
		return simpleDateFormat;
	}

	@Deprecated
	public static Gson getGson()
	{
		if (gson == null)
		{
			gson = new Gson();
		}

		return gson;
	}

	@Deprecated
	public static Gson getGsonPrettyPrinting()
	{
		if (gsonPretty == null)
		{
			gsonPretty = new Gson();
		}

		return gsonPretty;
	}

	@Deprecated
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

	@Deprecated
	public static Robot getRobot() throws AWTException
	{
		if (robot == null)
		{
			robot = new Robot();
		}
		return robot;
	}
}

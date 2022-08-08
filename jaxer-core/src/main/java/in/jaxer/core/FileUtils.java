package in.jaxer.core;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shakir
 * @date 08-08-2022
 * @since v1.1.0-beta
 */
@Log4j2
public class FileUtils
{
	/**
	 * @since v1.1.0-beta
	 */
	public static List<String> readLines(File file)
	{
		log.debug("file: {}", file);
		List<String> lines = new ArrayList<>();

		try (FileInputStream fileInputStream = new FileInputStream(file);
			 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream)))
		{
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
			{
				lines.add(line);
			}
		} catch (Exception e)
		{
			log.error("Exception", e);
			ExceptionUtils.rethrow(e);
		}

		return lines;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static List<String> readLines(String file)
	{
		return readLines(new File(file));
	}

}

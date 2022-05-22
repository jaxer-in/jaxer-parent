package in.jaxer.core.net;

import in.jaxer.core.constants.Constants;
import in.jaxer.core.utilities.Files;
import in.jaxer.core.utilities.Systems;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class FileDownloader
{
	public static void download(final String url)
	{
		String filePath = Systems.getUserHomeDirectory() + File.separator + getFileName(url);

		try (InputStream inputStream = new BufferedInputStream(new URL(url).openStream());
			 FileOutputStream fileOutputStream = new FileOutputStream(filePath))
		{
			String length = Files.getFileSize(getLength(url));

			long total = 0l;
			int n = 0;
			final byte[] bytes = new byte[Constants.BUFFER_SIZE];
			while (-1 != (n = inputStream.read(bytes)))
			{
				fileOutputStream.write(bytes, 0, n);
				total += n;
				log.debug("Completed: {} out of {}", Files.getFileSize(total), length);

				fileOutputStream.flush();
			}
		} catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private static String getFileName(String urlString)
	{
		return urlString.substring(urlString.lastIndexOf('/') + 1);
	}

	private static long getLength(String url)
	{
		URLConnection conn = null;
		try
		{
			conn = new URL(url).openConnection();
			if (conn instanceof HttpURLConnection)
			{
//				((HttpURLConnection) conn).setRequestMethod("HEAD");
			}
			conn.getInputStream();
			return conn.getContentLength();
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		} finally
		{
			if (conn instanceof HttpURLConnection)
			{
				((HttpURLConnection) conn).disconnect();
			}
		}
	}
}

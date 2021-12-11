
package in.jaxer.core.utilities;

import com.google.gson.Gson;
import in.jaxer.core.constants.Constants;
import in.jaxer.core.constants.ContentType;
import in.jaxer.core.constants.Singletons;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
public class Files
{

	public static boolean equals(File file0, File file1)
	{
		try
		{
			String hash0 = HashHandler.getFileChecksumSHA1(file0);
			String hash1 = HashHandler.getFileChecksumSHA1(file1);

			log.debug("file0: {}, hash0: {}", file0, hash0);
			log.debug("file1: {}, hash1: {}", file1, hash1);

			return hash0.equals(hash1);
		} catch (Exception ex)
		{
			log.error("Exception", ex);
			throw new RuntimeException("Exception occured while executing equals(file0, file1)", ex);
		}
	}

	public static boolean equals(String file0, String file1)
	{
		return equals(new File(file0), new File(file1));
	}

	public static String getExtensionWithDot(String text)
	{
		String ext = getExtensionWithoutDot(text);
		return ext == null ? null : "." + ext;
	}

	public static String getExtensionWithoutDot(String text)
	{
		if (text.contains("."))
		{
			return text.substring(text.lastIndexOf(".") + 1, text.length());
		}

		return null;
	}

	public static String getFileSize(File file)
	{
		return getFileSize(file.length());
	}

	public static String getFileSize(long fileLength)
	{
		String sizeString = "0 KB";

		float size = inKB(fileLength);
		sizeString = String.format("%.2f", size) + " KB";
		if (size > (float) Constants.ONE_BYTE)
		{
			size = inMB(fileLength);
			sizeString = String.format("%.2f", size) + " MB";
			if (size > (float) Constants.ONE_BYTE)
			{
				size = inGB(fileLength);
				sizeString = String.format("%.2f", size) + " GB";
				if (size > (float) Constants.ONE_BYTE)
				{
					size = inTB(fileLength);
					sizeString = String.format("%.2f", size) + " TB";
				}
			}
		}
		return sizeString;
	}

	public static float inKB(long length)
	{
		return (length / 1024f);
	}

	public static float inMB(long length)
	{
		return (inKB(length) / 1024f);
	}

	public static float inGB(long length)
	{
		return (inMB(length) / 1024f);
	}

	public static float inTB(long length)
	{
		return (inGB(length) / 1024f);
	}

	//<editor-fold defaultstate="collapsed" desc=" --- readers-writers --- ">
	public static String readFile(String file) throws FileNotFoundException, IOException
	{
		return readFile(new File(file));
	}

	public static String readFile(File file) throws FileNotFoundException, IOException
	{
		StringBuilder builder = new StringBuilder();

		try (FileInputStream fileInputStream = new FileInputStream(file);
			 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));)
		{
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
			{
				builder.append(line).append(System.lineSeparator());
			}
		}

		return builder.toString();
	}

	public static void writeFile(String filePath, String msg, boolean append) throws FileNotFoundException, IOException
	{
		writeFile(new File(filePath), msg, append);
	}

	public static void writeFile(File file, String msg, boolean append) throws FileNotFoundException, IOException
	{
		try (FileOutputStream fileOutputStream = new FileOutputStream(file, append);)
		{
			fileOutputStream.write(msg.getBytes());
		}
	}

	public static final Object readSerializedObject(String filePath) throws Exception
	{
		return readSerializedObject(new File(filePath));
	}

	public static final Object readSerializedObject(File file) throws Exception
	{
		try (FileInputStream fileInputStream = new FileInputStream(file);
			 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);)
		{
			return objectInputStream.readObject();
		}
	}

	public static final void writeSerializedObject(String filePath, Object object) throws Exception
	{
		writeSerializedObject(new File(filePath), object);
	}

	public static final void writeSerializedObject(File file, Object object) throws Exception
	{
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
			 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);)
		{
			objectOutputStream.writeObject(object);
		}
	}

	public static final Object readXmlObject(String filePath) throws Exception
	{
		return readXmlObject(new File(filePath));
	}

	public static final Object readXmlObject(File file) throws Exception
	{
		try (FileInputStream fileInputStream = new FileInputStream(file);
			 XMLDecoder xMLDecoder = new XMLDecoder(fileInputStream);)
		{
			return xMLDecoder.readObject();
		}
	}

	public static final void writeXmlObject(String filePath, Object object) throws Exception
	{
		writeXmlObject(new File(filePath), object);
	}

	public static final void writeXmlObject(File file, Object object) throws Exception
	{
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
			 XMLEncoder xMLEncoder = new XMLEncoder(fileOutputStream);)
		{
			xMLEncoder.writeObject(object);
		}
	}

	public static <T> T readJsonObject(Class<T> outputClass, File file) throws FileNotFoundException, IOException
	{
		return readJsonObject(outputClass, Singletons.getGson(false), file);
	}

	public static <T> T readJsonObject(Class<T> outputClass, Gson gson, File file) throws FileNotFoundException, IOException
	{
		String fileData = readFile(file);
		return gson.fromJson(fileData, outputClass);
	}

	public static void writeJsonObject(File file, Object object) throws FileNotFoundException, IOException
	{
		writeJsonObject(Singletons.getGson(false), file, object);
	}

	public static void writeJsonObject(File file, Object object, boolean append) throws FileNotFoundException, IOException
	{
		writeJsonObject(Singletons.getGson(false), file, object, append);
	}

	public static void writeJsonObject(Gson gson, File file, Object object) throws FileNotFoundException, IOException
	{
		writeJsonObject(gson, file, object, false);
	}

	public static void writeJsonObject(Gson gson, File file, Object object, boolean append) throws FileNotFoundException, IOException
	{
		String jsonString = gson.toJson(object);
		writeFile(file, jsonString, append);
	}

	//</editor-fold>
	public static File createTempFile() throws FileAlreadyExistsException
	{
		return createTempFile(Strings.getUUID());
	}

	public static File createTempFile(String tempFileName) throws FileAlreadyExistsException
	{
		File file = new File(Systems.getTempDirectory(), tempFileName);
		if (file.exists())
		{
			throw new FileAlreadyExistsException(file.getAbsolutePath());
		}
		return file;
	}

	public static void copy(String source, String target) throws FileNotFoundException, IOException
	{
		copy(new File(source), new File(target));
	}

	public static void copy(File source, File target) throws FileNotFoundException, IOException
	{
		try (FileInputStream fileInputStream = new FileInputStream(source);
			 FileOutputStream fileOutputStream = new FileOutputStream(target);)
		{
			copyBytes(fileInputStream, fileOutputStream);
		}
	}

	public static void copyBytes(InputStream inputStream, OutputStream outputStream) throws FileNotFoundException, IOException
	{
		copyBytes(Constants.BUFFER_SIZE, inputStream, outputStream);
	}

	public static void copyBytes(int bufferSize, InputStream inputStream, OutputStream outputStream) throws FileNotFoundException, IOException
	{
		int i;
		byte[] buffer = new byte[bufferSize];

		long totalReadBytes = 0l;

		while ((i = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, i);
			totalReadBytes += i;

		}
		log.debug("totalReadBytes: {}", totalReadBytes);
		outputStream.flush();
	}

	public static void delete(String file)
	{
		delete(new File(file));
	}

	public static void delete(File file)
	{
		if (!file.exists())
		{
			return;
		}

		if (file.isDirectory())
		{
			for (File childFile : file.listFiles())
			{
				delete(childFile);
			}
		}

		log.debug("[{}] \t {}" + file.delete(), file.getAbsolutePath());
	}

	public static String getMimeType(String fileName)
	{
		return Singletons.getMimetypesFileTypeMap().getContentType(fileName);
	}

	public static String getMimeType(File file)
	{
		return getMimeType(file.getName());
	}

	public static String getDefaultMimeType(String filename)
	{
		String mime = getMimeType(filename);
		return JValidator.isNotEmpty(mime) ? mime : ContentType.APPLICATION_OCTET_STREAM;
	}

	public static String getDefaultMimeType(File file)
	{
		return getDefaultMimeType(file.getName());
	}

	public static void downloadFile(URL url) throws Exception
	{
		URLConnection uRLConnection = url.openConnection();

		String contentType = uRLConnection.getContentType();
		int contentLength = uRLConnection.getContentLength();

		log.debug("contentType: {}, contentLength: {}", contentType, contentLength);

		String filename = url.getFile();
		filename = filename.substring(filename.lastIndexOf('/') + 1);

		try (InputStream inputStream = uRLConnection.getInputStream();
			 FileOutputStream fileOutputStream = new FileOutputStream("D:/" + filename);)
		{
			Files.copyBytes(inputStream, fileOutputStream);
		} catch (Exception exception)
		{
			log.error("Exception", exception);
		}
	}
}

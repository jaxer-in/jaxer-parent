package in.jaxer.core.utilities;

import com.google.gson.Gson;
import in.jaxer.core.FileUtils;
import in.jaxer.core.constants.Constants;
import in.jaxer.core.constants.ContentType;
import in.jaxer.core.constants.Singletons;
import in.jaxer.core.exceptions.JaxerCoreException;
import lombok.extern.log4j.Log4j2;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author Shakir Ansari
 * @deprecated on v1.1.0-beta, please check {@link in.jaxer.core.FileUtils}
 */
@Log4j2
@Deprecated
public class Files
{
	public static boolean equals(String file0, String file1)
	{
		return equals(new File(file0), new File(file1));
	}

	public static boolean equals(File file0, File file1)
	{
		String hash0 = HashHandler.getFileChecksumSHA1(file0);
		log.debug("file0: {}, hash0: {}", file0, hash0);

		String hash1 = HashHandler.getFileChecksumSHA1(file1);
		log.debug("file1: {}, hash1: {}", file1, hash1);

		return hash0.equals(hash1);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#getExtension(String, boolean)}
	 */
	@Deprecated
	public static String getExtensionWithDot(String text)
	{
		String ext = getExtensionWithoutDot(text);
		return ext == null ? null : "." + ext;
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#getExtension(String)}
	 */
	@Deprecated
	public static String getExtensionWithoutDot(String text)
	{
		if (text.contains("."))
		{
			return text.substring(text.lastIndexOf(".") + 1);
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
		if (size > (float) FileUtils.ONE_BYTE)
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

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#readFile(String)}
	 */
	@Deprecated
	public static String readFile(String file) throws IOException
	{
		return readFile(new File(file));
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#readLines(File)}
	 */
	@Deprecated
	public static String readFile(File file) throws IOException
	{
		log.debug("file: {}", file);
		StringBuilder builder = new StringBuilder();

		try (FileInputStream fileInputStream = new FileInputStream(file);
			 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream)))
		{
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
			{
				builder.append(line).append(System.lineSeparator());
			}
		}

		return builder.toString();
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeFile(String, String, boolean)}
	 */
	@Deprecated
	public static void writeFile(String filePath, String msg, boolean append) throws IOException
	{
		writeFile(new File(filePath), msg, append);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeFile(File, String, boolean)}
	 */
	@Deprecated
	public static void writeFile(File file, String msg, boolean append) throws IOException
	{
		log.debug("file: {}, append: {}, msg: {}", file, append, msg);
		try (FileOutputStream fileOutputStream = new FileOutputStream(file, append))
		{
			fileOutputStream.write(msg.getBytes());
		}
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#readSerializedObject(String)}
	 */
	@Deprecated
	public static final Object readSerializedObject(String filePath) throws Exception
	{
		log.debug("filePath: {}", filePath);
		return readSerializedObject(new File(filePath));
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#readSerializedObject(File)}
	 */
	@Deprecated
	public static final Object readSerializedObject(File file) throws Exception
	{
		log.debug("file: {}", file);
		try (FileInputStream fileInputStream = new FileInputStream(file);
			 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream))
		{
			return objectInputStream.readObject();
		}
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeSerializedObject(String, Object)}
	 */
	@Deprecated
	public static final void writeSerializedObject(String filePath, Object object) throws Exception
	{
		log.debug("filePath: {}, object: {}", filePath, object);
		writeSerializedObject(new File(filePath), object);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeSerializedObject(File, Object)}
	 */
	@Deprecated
	public static final void writeSerializedObject(File file, Object object) throws Exception
	{
		log.debug("file: {}, object: {}", file, object);
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
			 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream))
		{
			objectOutputStream.writeObject(object);
		}
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#readXmlObject(String)}
	 */
	@Deprecated
	public static final Object readXmlObject(String filePath) throws Exception
	{
		log.debug("file: {}", filePath);
		return readXmlObject(new File(filePath));
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#readXmlObject(File)}
	 */
	@Deprecated
	public static final Object readXmlObject(File file) throws Exception
	{
		log.debug("file: {}", file);
		try (FileInputStream fileInputStream = new FileInputStream(file);
			 XMLDecoder xMLDecoder = new XMLDecoder(fileInputStream))
		{
			return xMLDecoder.readObject();
		}
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeXmlObject(String, Object)}
	 */
	@Deprecated
	public static final void writeXmlObject(String filePath, Object object) throws Exception
	{
		log.debug("filePath: {}, object", filePath, object);
		writeXmlObject(new File(filePath), object);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeXmlObject(File, Object)}
	 */
	@Deprecated
	public static final void writeXmlObject(File file, Object object) throws Exception
	{
		log.debug("file: {}, object", file, object);
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
			 XMLEncoder xMLEncoder = new XMLEncoder(fileOutputStream))
		{
			xMLEncoder.writeObject(object);
		}
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#readJsonObject(Class, File)}
	 */
	@Deprecated
	public static <T> T readJsonObject(Class<T> outputClass, File file) throws IOException
	{
		log.debug("outputClass: {}, file: {}, object", outputClass, file);
		return readJsonObject(outputClass, JsonHandler.getGson(), file);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#readJsonObject(Class, File)}
	 */
	@Deprecated
	public static <T> T readJsonObject(Class<T> outputClass, Gson gson, File file) throws IOException
	{
		log.debug("outputClass: {}, gson: {}, file: {}", outputClass, gson, file);
		String fileData = readFile(file);
		return gson.fromJson(fileData, outputClass);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeJsonObject(File, Object)}
	 */
	@Deprecated
	public static void writeJsonObject(File file, Object object) throws IOException
	{
		log.debug("file: {}, object: {}", file, object);
		writeJsonObject(JsonHandler.getGson(), file, object);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeJsonObject(File, Object)}
	 */
	@Deprecated
	public static void writeJsonObject(File file, Object object, boolean append) throws IOException
	{
		log.debug("file: {}, object: {}, append: {}", file, object, append);
		writeJsonObject(JsonHandler.getGson(), file, object, append);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeJsonObject(File, Object)}
	 */
	@Deprecated
	public static void writeJsonObject(Gson gson, File file, Object object) throws IOException
	{
		log.debug("gson: {}, file: {}, objetc: {}", gson, file, object);
		writeJsonObject(gson, file, object, false);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#writeJsonObject(File, Object)}
	 */
	@Deprecated
	public static void writeJsonObject(Gson gson, File file, Object object, boolean append) throws IOException
	{
		log.debug("gson: {}, file: {}, objetc: {}, append: {}", gson, file, object, append);
		String jsonString = gson.toJson(object);
		writeFile(file, jsonString, append);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link FileUtils#createTempFile()}
	 */
	@Deprecated
	public static File createTempFile() throws FileAlreadyExistsException
	{
		return createTempFile(Strings.getUUID());
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link FileUtils#createTempFile(String)}
	 */
	@Deprecated
	public static File createTempFile(String tempFileName) throws FileAlreadyExistsException
	{
		log.debug("tempFileName: {}", tempFileName);
		File file = new File(Systems.getTempDirectory(), tempFileName);
		if (file.exists())
		{
			throw new FileAlreadyExistsException(file.getAbsolutePath());
		}
		return file;
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link FileUtils#copy(String, String)}
	 */
	@Deprecated
	public static void copy(String source, String target) throws IOException
	{
		log.debug("source: {}, target: {}", source, target);
		copy(new File(source), new File(target));
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link FileUtils#copy(File, File)}
	 */
	@Deprecated
	public static void copy(File source, File target) throws IOException
	{
		log.debug("source: {}, target: {}", source, target);
		try (FileInputStream fileInputStream = new FileInputStream(source);
			 FileOutputStream fileOutputStream = new FileOutputStream(target))
		{
			copyBytes(fileInputStream, fileOutputStream);
		}
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link FileUtils#copyBytes(InputStream, OutputStream)}
	 */
	@Deprecated
	public static void copyBytes(InputStream inputStream, OutputStream outputStream) throws IOException
	{
		log.debug("inputStream: {}, outputStream: {}", inputStream, outputStream);
		copyBytes(FileUtils.BUFFER_SIZE, inputStream, outputStream);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link FileUtils#copyBytes(int, InputStream, OutputStream)}
	 */
	@Deprecated
	public static void copyBytes(int bufferSize, InputStream inputStream, OutputStream outputStream) throws IOException
	{
		log.debug("bufferSize: {}, inputStream: {}, outputStream: {}", bufferSize, inputStream, outputStream);

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

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#delete(String, boolean)}
	 */
	@Deprecated
	public static void delete(String file, boolean recursive)
	{
		log.debug("file: {}, recursive: {}", file, recursive);
		delete(new File(file), recursive);
	}

	/**
	 * @deprecated on v1.1.0-beta, please refer {@link in.jaxer.core.FileUtils#delete(File, boolean)}
	 */
	@Deprecated
	public static void delete(File file, boolean recursive)
	{
		log.debug("file: {}, recursive: {}", file, recursive);

		if (!file.exists())
		{
			return;
		}

		if (recursive && file.isDirectory())
		{
			for (File childFile : file.listFiles())
			{
				delete(childFile, recursive);
			}
		}

		log.debug("[{}] \t {}" + file.delete(), file.getAbsolutePath());
	}

	/**
	 * @deprecated on v1.1.0-beta
	 */
	@Deprecated
	public static String getMimeType(String fileName)
	{
		log.debug("fileName: {}", fileName);
		return Singletons.getMimetypesFileTypeMap().getContentType(fileName);
	}

	/**
	 * @deprecated on v1.1.0-beta
	 */
	@Deprecated
	public static String getMimeType(File file)
	{
		log.debug("file: {}", file);
		return getMimeType(file.getName());
	}

	/**
	 * @deprecated on v1.1.0-beta
	 */
	@Deprecated
	public static String getDefaultMimeType(String filename)
	{
		log.debug("filename: {}", filename);
		String mime = getMimeType(filename);
		return JValidator.isNotBlank(mime) ? mime : ContentType.APPLICATION_OCTET_STREAM;
	}

	/**
	 * @deprecated on v1.1.0-beta
	 */
	@Deprecated
	public static String getDefaultMimeType(File file)
	{
		log.debug("file: {}", file);
		return getDefaultMimeType(file.getName());
	}

	/**
	 * @deprecated on v1.1.0-beta
	 */
	@Deprecated
	public static void downloadFile(URL url)
	{
		try
		{
			log.debug("url: {}", url);
			URLConnection uRLConnection = url.openConnection();

			String contentType = uRLConnection.getContentType();
			int contentLength = uRLConnection.getContentLength();

			log.debug("contentType: {}, contentLength: {}", contentType, contentLength);

			String filename = url.getFile();
			filename = filename.substring(filename.lastIndexOf('/') + 1);

			try (InputStream inputStream = uRLConnection.getInputStream();
				 FileOutputStream fileOutputStream = new FileOutputStream("D:/" + filename))
			{
				Files.copyBytes(inputStream, fileOutputStream);
			}
		} catch (Exception exception)
		{
			log.error("Exception", exception);
			throw new JaxerCoreException(exception);
		}
	}
}

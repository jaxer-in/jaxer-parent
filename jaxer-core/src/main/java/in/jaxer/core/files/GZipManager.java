package in.jaxer.core.files;

import in.jaxer.core.utilities.Files;
import in.jaxer.core.utilities.Strings;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class GZipManager
{
	public static boolean gzipIt(FileInputStream fileInputStream, GZIPOutputStream gZIPOutputStream) throws IOException
	{
		Files.copyBytes(fileInputStream, gZIPOutputStream);
		return true;
	}

	public static boolean gunzipIt(GZIPInputStream gZIPInputStream, FileOutputStream fileOutputStream) throws IOException
	{
		Files.copyBytes(gZIPInputStream, fileOutputStream);
		return true;
	}

	public static boolean gzipIt(File sourceFile, File targetFile) throws IOException
	{
		log.debug("sourceFile: {}", sourceFile);
		log.debug("targetFile: {}", targetFile);

		try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
			 GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(new FileOutputStream(targetFile)))
		{
			return gzipIt(fileInputStream, gZIPOutputStream);
		}
	}

	public static boolean gzipIt(File sourceFile) throws IOException
	{
		log.debug("sourceFile: {}", sourceFile);
		return gzipIt(sourceFile.getAbsolutePath());
	}

	public static boolean gzipIt(String sourceFile, String targetFile) throws IOException
	{
		log.debug("sourceFile: {}", sourceFile);
		log.debug("targetFile: {}", targetFile);

		return gzipIt(new File(sourceFile), new File(targetFile));
	}

	public static boolean gzipIt(String sourceFile) throws IOException
	{
		log.debug("sourceFile: {}", sourceFile);
		return gzipIt(sourceFile, getTargetFileName(sourceFile));
	}

	public static boolean gunzipIt(File sourceFile, File targetFile) throws IOException
	{
		log.debug("sourceFile: {}", sourceFile);
		log.debug("targetFile: {}", targetFile);

		try (GZIPInputStream gZIPInputStream = new GZIPInputStream(new FileInputStream(sourceFile));
			 FileOutputStream fileOutputStream = new FileOutputStream(targetFile))
		{
			return gunzipIt(gZIPInputStream, fileOutputStream);
		}
	}

	public static boolean gunzipIt(File sourceFile) throws IOException
	{
		log.debug("sourceFile: {}", sourceFile);

		return gunzipIt(sourceFile.getAbsolutePath());
	}

	public static boolean gunzipIt(String sourceFile, String targetFile) throws IOException
	{
		log.debug("sourceFile: {}", sourceFile);
		log.debug("targetFile: {}", targetFile);

		return gunzipIt(new File(sourceFile), new File(targetFile));
	}

	public static boolean gunzipIt(String sourceFile) throws IOException
	{
		log.debug("sourceFile: {}", sourceFile);

		return gunzipIt(sourceFile, getSourceFileName(sourceFile));
	}

	private static String getTargetFileName(String sourceFileName)
	{
		log.debug("sourceFileName: {}", sourceFileName);

		String targetFileName = sourceFileName + ".gz";
		log.debug("targetFileName: {}", targetFileName);

		return targetFileName;
	}

	private static String getSourceFileName(String targetFileName)
	{
		log.debug("targetFileName: {}", targetFileName);
		if (!targetFileName.endsWith(".gz"))
		{
			throw new IllegalArgumentException("Given target file is not .gz file");
		}

		String sourceFileName = Strings.removeEndsWith(".gz", "");
		log.debug("sourceFileName: {}", sourceFileName);

		return sourceFileName;
	}
}


package in.jaxer.core.files;

import in.jaxer.core.utilities.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Shakir Ansari
 */
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
		try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
			 GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(new FileOutputStream(targetFile));)
		{
			return gzipIt(fileInputStream, gZIPOutputStream);
		}
	}

	public static boolean gzipIt(File sourceFile) throws IOException
	{
		return gzipIt(sourceFile.getAbsolutePath());
	}

	public static boolean gzipIt(String sourceFile, String targetFile) throws IOException
	{
		return gzipIt(new File(sourceFile), new File(targetFile));
	}

	public static boolean gzipIt(String sourceFile) throws IOException
	{
		return gzipIt(sourceFile, getTargetFileName(sourceFile));
	}

	public static boolean gunzipIt(File sourceFile, File targetFile) throws FileNotFoundException, IOException
	{
		try (GZIPInputStream gZIPInputStream = new GZIPInputStream(new FileInputStream(sourceFile));
			 FileOutputStream fileOutputStream = new FileOutputStream(targetFile);)
		{
			return gunzipIt(gZIPInputStream, fileOutputStream);
		}
	}

	public static boolean gunzipIt(File sourceFile) throws FileNotFoundException, IOException
	{
		return gunzipIt(sourceFile.getAbsolutePath());
	}

	public static boolean gunzipIt(String sourceFile, String targetFile) throws FileNotFoundException, IOException
	{
		return gunzipIt(new File(sourceFile), new File(targetFile));
	}

	public static boolean gunzipIt(String sourceFile) throws FileNotFoundException, IOException
	{
		return gunzipIt(sourceFile, getSourceFileName(sourceFile));
	}

	private static String getTargetFileName(String sourceFileName)
	{
		return sourceFileName + ".gz";
	}

	private static String getSourceFileName(String targetFileName)
	{
		if (!targetFileName.contains(".gz"))
		{
			throw new IllegalArgumentException("Given target file is not .gz file");
		}
		String sourceFileName = targetFileName.replace(".gz", "");

		System.out.println("GZipManager.getSourceFileName() - sourceFileName: [" + sourceFileName + "]");

		return sourceFileName;
	}
}

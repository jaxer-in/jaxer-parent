
package in.jaxer.core.utilities;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author Shakir Ansari
 */
public class ImageHandler
{

	public static void doOptimize(String source, String target, float quality) throws FileNotFoundException, IOException
	{
		doOptimize(new File(source), new File(target), quality);
	}

	public static void doOptimize(File source, File target, float quality) throws FileNotFoundException, IOException
	{
		System.out.println("ImageHandler.doOptimize() - Processing: " + source.getName());
		BufferedImage bufferedImage = ImageIO.read(source);

		try (OutputStream outputStream = new FileOutputStream(target);
			 ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream))
		{
			ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(Files.getExtensionWithoutDot(source.getName())).next();
			imageWriter.setOutput(imageOutputStream);

			ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
			if (imageWriteParam.canWriteCompressed())
			{
				imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				imageWriteParam.setCompressionQuality(quality);
			}

			imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
			imageWriter.dispose();
		}
	}

	public static String resize(String source, int width, int height) throws FileNotFoundException, IOException
	{
		return resize(new File(source), width, height);
	}

	public static String resize(File source, int width, int height) throws FileNotFoundException, IOException
	{
		try (FileInputStream fileInputStream = new FileInputStream(source);)
		{
			BufferedImage inputImage = ImageIO.read(fileInputStream);
			BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

			// scales the input image to the output image
			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, width, height, null);
			g2d.dispose();

			String ext = Files.getExtensionWithoutDot(source.getName());
			String outputImagePath = getResizedFileName(source, width, height);

			// writes to output file
			ImageIO.write(outputImage, ext, new File(outputImagePath));

			System.out.println("ImageHandler.resize() - outputImagePath: " + outputImagePath);

			return outputImagePath;
		}
	}

	public static String resize(String source, int percentage) throws FileNotFoundException, IOException
	{
		return resize(new File(source), percentage);
	}

	public static String resize(File source, int percentage) throws FileNotFoundException, IOException
	{
		Dimension dimension = JUtilities.getImageDimension(source);
		int width = dimension.width * percentage / 100;
		int height = dimension.height * percentage / 100;

		return resize(source, width, height);
	}

	private static String getResizedFileName(File file, int width, int height)
	{
		return getNewName(file, "_" + width + "x" + height);
	}

	private static String getNewName(File file, String postfix)
	{
		String ext = Files.getExtensionWithDot(file.getName());

		return file.getAbsolutePath().replace(ext, postfix + ext);
	}
}

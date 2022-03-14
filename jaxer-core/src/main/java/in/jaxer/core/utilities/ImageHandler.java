
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
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
public class ImageHandler
{

	public static void doOptimize(String source, String target, float quality) throws FileNotFoundException, IOException
	{
		log.info("quality: {}, source: {}, target: {}", quality, source, target);
		doOptimize(new File(source), new File(target), quality);
	}

	public static void doOptimize(File source, File target, float quality) throws FileNotFoundException, IOException
	{
		log.info("quality: {}, source: {}, target: {}", quality, source, target);
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

	public static void resize(String source, String target, int width, int height) throws FileNotFoundException, IOException
	{
		resize(new File(source), new File(target), width, height);
	}

	public static void resize(File source, File target, int width, int height) throws FileNotFoundException, IOException
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

			// writes to output file
			ImageIO.write(outputImage, ext, target);

			log.debug("Resized: target: {}", target.getAbsolutePath());
		}
	}

	public static void resizeByWidth(File source, File target, int width) throws FileNotFoundException, IOException
	{
		if (width <= 0)
		{
			throw new IllegalArgumentException("width cannot be zero or less");
		}

		try (FileInputStream fileInputStream = new FileInputStream(source))
		{
			BufferedImage inputImage = ImageIO.read(fileInputStream);

			int w = inputImage.getWidth();
			int h = inputImage.getHeight();

			h = h / (w / width);

			BufferedImage outputImage = new BufferedImage(width, h, inputImage.getType());

			// scales the input image to the output image
			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, width, h, null);
			g2d.dispose();

			String ext = Files.getExtensionWithoutDot(source.getName());

			// writes to output file
			ImageIO.write(outputImage, ext, target);

			log.debug("Resized: target: {}", target.getAbsolutePath());
		}
	}

	public static void resizeByHeight(File source, File target, int height) throws FileNotFoundException, IOException
	{
		if (height <= 0)
		{
			throw new IllegalArgumentException("height cannot be zero or less");
		}

		try (FileInputStream fileInputStream = new FileInputStream(source))
		{
			BufferedImage inputImage = ImageIO.read(fileInputStream);

			int w = inputImage.getWidth();
			int h = inputImage.getHeight();

			w = w / (h / height);

			BufferedImage outputImage = new BufferedImage(height, h, inputImage.getType());

			// scales the input image to the output image
			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, height, h, null);
			g2d.dispose();

			String ext = Files.getExtensionWithoutDot(source.getName());

			// writes to output file
			ImageIO.write(outputImage, ext, target);

			log.debug("Resized: target: {}", target.getAbsolutePath());
		}
	}
	public static void resize(String source, String target, int percentage) throws FileNotFoundException, IOException
	{
		resize(new File(source), new File(target), percentage);
	}

	public static void resize(File source, File target, int percentage) throws FileNotFoundException, IOException
	{
		Dimension dimension = JUtilities.getImageDimension(source);
		int width = dimension.width * percentage / 100;
		int height = dimension.height * percentage / 100;

		resize(source, target, width, height);
	}

	private static String getNewName(File file, String postfix)
	{
		String ext = Files.getExtensionWithDot(file.getName());

		return file.getAbsolutePath().replace(ext, postfix + ext);
	}
}

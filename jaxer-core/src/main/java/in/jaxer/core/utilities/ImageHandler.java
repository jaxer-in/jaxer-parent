package in.jaxer.core.utilities;

import in.jaxer.core.exceptions.JaxerCoreException;
import lombok.extern.log4j.Log4j2;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
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
		log.info("source: {}, target: {}, width: {}, height: {}", source, target, width, height);
		resize(new File(source), new File(target), width, height);
	}

	public static void resize(File source, File target, int width, int height) throws FileNotFoundException, IOException
	{
		log.info("source: {}, target: {}, width: {}, height: {}", source, target, width, height);
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
		log.info("source: {}, target: {}, width: {}", source, target, width);
		if (width <= 0)
		{
			throw new JaxerCoreException("width cannot be zero or less");
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
		log.info("source: {}, target: {}, height: {}", source, target, height);
		if (height <= 0)
		{
			throw new JaxerCoreException("height cannot be zero or less");
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
		log.info("source: {}, target: {}, percentages: {}", source, target, percentage);
		resize(new File(source), new File(target), percentage);
	}

	public static void resize(File source, File target, int percentage) throws FileNotFoundException, IOException
	{
		log.info("source: {}, target: {}, percentages: {}", source, target, percentage);
		Dimension dimension = JUtilities.getImageDimension(source);
		int width = dimension.width * percentage / 100;
		int height = dimension.height * percentage / 100;

		resize(source, target, width, height);
	}

	private static String getNewName(File file, String postfix)
	{
		log.info("file: {}, postfix: {}", file, postfix);
		String ext = Files.getExtensionWithDot(file.getName());

		return file.getAbsolutePath().replace(ext, postfix + ext);
	}
}

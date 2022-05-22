package in.jaxer.core.files;

import in.jaxer.core.Jaxer;
import in.jaxer.core.constants.Constants;
import in.jaxer.core.utilities.Systems;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Shakir Ansari
 */
@Log4j2
@Getter
@Setter
@ToString
public class ImagePlaceholder
{
	private int width;
	private int height;
	private Font font;
	private Color backgroundColor;
	private Color textColor;
	private String text;
	private String fileType;

	public ImagePlaceholder()
	{
		this.width = 500;
		this.height = 500;
		this.font = new Font("consolas", Font.BOLD, 100);
		this.backgroundColor = new Color(220, 220, 220);
		this.textColor = new Color(180, 180, 180); //new Color(200, 200, 200);
		this.text = Jaxer.ProjectProperties.getKey(Jaxer.ProjectProperties.PROJECT_NAME);
		this.fileType = Constants.JPG;
	}

	public BufferedImage createPlaceholder(String msg) throws Exception
	{
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setPaint(backgroundColor);
		graphics2D.fillRect(0, 0, width, height);

		graphics2D.setFont(font);
		graphics2D.setPaint(textColor);

		TextLayout textLayout = new TextLayout(msg, graphics2D.getFont(), graphics2D.getFontRenderContext());

		double textHeight = textLayout.getBounds().getHeight();
		double textWidth = textLayout.getBounds().getWidth();

		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int centerX = width / 2 - (int) textWidth / 2;
		int centerY = height / 2 + (int) textHeight / 2;

		graphics2D.drawString(msg, centerX, centerY);

		graphics2D.setPaint(textColor);
		graphics2D.drawString(msg, centerX + 1, centerY + 1);

		return bufferedImage;
	}

	/**
	 * @param text your text on image
	 *
	 * @return will return file location
	 */
	public String saveImage(String text)
	{
		log.debug("text: {}", text);

		String filePath = Systems.getUserHomeDirectory() + File.separator + "image_placeholder_" + System.currentTimeMillis() + "." + this.getFileType();
		log.debug("filePath: {}", filePath);

		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath))
		{
			ImageIO.write(createPlaceholder(text), this.getFileType(), fileOutputStream);
			fileOutputStream.flush();
		} catch (Exception ex)
		{
			log.error("Exception", ex);
			throw new RuntimeException(ex);
		}

		return filePath;
	}
}

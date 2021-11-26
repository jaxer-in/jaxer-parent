
package in.jaxer.core.files;

import in.jaxer.core.Jaxer;
import in.jaxer.core.constants.Constants;
import in.jaxer.core.utilities.Systems;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Shakir Ansari
 */
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

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public Color getTextColor()
	{
		return textColor;
	}

	public void setTextColor(Color textColor)
	{
		this.textColor = textColor;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
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

	public void saveImage(String text) throws Exception
	{
		String filePath = Systems.getUserHomeDirectory() + File.separator + "image_placeholder_" + System.currentTimeMillis() + "." + this.getFileType();

		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);)
		{
			ImageIO.write(createPlaceholder(text), this.getFileType(), fileOutputStream);
			fileOutputStream.flush();
		}
		System.out.println("ImagePlaceholder.saveImage() - file is saved " + filePath);
	}

}

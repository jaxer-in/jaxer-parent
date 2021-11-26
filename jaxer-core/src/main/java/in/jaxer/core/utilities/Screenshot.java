
package in.jaxer.core.utilities;

import in.jaxer.core.constants.Singletons;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Shakir Ansari
 */
public class Screenshot
{

	public static BufferedImage getScreenShot() throws Exception
	{
		Rectangle rectangle = new Rectangle(Utilities.getScreenDimension());
		BufferedImage bufferedImage = Singletons.getRobot().createScreenCapture(rectangle);
		return bufferedImage;
	}
}

package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class ClipboardHandler
{
	private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	public void copy(String string)
	{
		log.info("string: {}", string);
		ClipboardHandler.clipboard.setContents(new StringSelection(string), null);
	}

	public String paste()
	{
		DataFlavor flavor = DataFlavor.stringFlavor;
		if (ClipboardHandler.clipboard.isDataFlavorAvailable(flavor))
		{
			try
			{
				return (String) ClipboardHandler.clipboard.getData(flavor);
			} catch (Exception exception)
			{
				log.error("Exception: {}", exception);
				throw new RuntimeException(exception);
			}
		}
		return null;
	}
}

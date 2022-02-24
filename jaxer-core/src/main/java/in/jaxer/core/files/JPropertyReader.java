
package in.jaxer.core.files;

import in.jaxer.core.utilities.JUtilities;
import in.jaxer.core.utilities.JValidator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Shakir Ansari
 */
public class JPropertyReader implements AutoCloseable
{

	private final InputStream inputStream;

	private final Properties properties;

	public JPropertyReader(String fileName) throws IOException
	{
		fileName = fileName.endsWith(".properties") ? fileName : fileName + ".properties";

		this.inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

		this.properties = new Properties();
		this.properties.load(inputStream);
	}

	public String getKey(String key)
	{
		JValidator.requireNotEmpty(key, "Key could not be empty.");
		return this.properties.getProperty(key);
	}

	public void setKey(String key, String value)
	{
		JValidator.requireNotEmpty(key, "Key could not be empty.");
		this.properties.setProperty(key, value);
	}

	public int getInt(String key, int defaultValue)
	{
		JValidator.requireNotEmpty(key, "Key could not be empty.");

		if (JValidator.isNotEmpty(this.properties.getProperty(key)))
		{
			return Integer.parseInt(this.properties.getProperty(key));
		}
		return defaultValue;
	}

	public boolean containKey(String key)
	{
		return this.properties.containsKey(key);
	}

	public boolean containsValue(String key)
	{
		return this.properties.containsValue(key);
	}

	@Override
	public void close()
	{
		JUtilities.close(this.inputStream);

		if (this.properties != null)
		{
			this.properties.clear();
		}
	}
}

package in.jaxer.core.files;

import in.jaxer.core.utilities.JUtilities;
import in.jaxer.core.utilities.JValidator;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class JPropertyReader implements AutoCloseable
{
	private final InputStream inputStream;
	private final Properties properties;

	public JPropertyReader(String fileName) throws IOException
	{
		log.info("fileName: {}", fileName);
		fileName = fileName.endsWith(".properties") ? fileName : fileName + ".properties";
		log.debug("fileName: {}", fileName);

		this.inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

		this.properties = new Properties();
		this.properties.load(inputStream);
	}

	public String getKey(String key)
	{
		log.debug("key: {}", key);
		JValidator.throwWhenNullOrEmpty(key, "Key could not be null/empty.");

		return this.properties.getProperty(key);
	}

	public void setKey(String key, String value)
	{
		log.debug("key: {}, value: {}", key, value);
		JValidator.throwWhenNullOrEmpty(key, "Key could not be null/empty.");

		this.properties.setProperty(key, value);
	}

	public int getInt(String key, int defaultValue)
	{
		log.debug("key: {}, defaultValue: {}", key, defaultValue);
		JValidator.throwWhenNullOrEmpty(key, "Key could not be null/empty.");

		if (JValidator.isNotNullAndNotEmpty(this.properties.getProperty(key)))
		{
			return Integer.parseInt(this.properties.getProperty(key));
		}
		return defaultValue;
	}

	public boolean containKey(String key)
	{
		log.debug("key: {}", key);
		JValidator.throwWhenNullOrEmpty(key, "Key could not be null/empty.");

		return this.properties.containsKey(key);
	}

	public boolean containsValue(String value)
	{
		log.debug("value: {}", value);
		JValidator.throwWhenNull(value, "Value could not be null");

		return this.properties.containsValue(value);
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

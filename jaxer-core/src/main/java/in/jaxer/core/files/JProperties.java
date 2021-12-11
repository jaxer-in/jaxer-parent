
package in.jaxer.core.files;

import in.jaxer.core.annotations.PropertyKey;
import in.jaxer.core.constants.Singletons;
import in.jaxer.core.utilities.PackageScanner;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
public class JProperties
{

	private String propertyFile;

	private String basePackage;

	public JProperties()
	{
	}

	public JProperties(String propertyFile, String basePackage)
	{
		this.propertyFile = propertyFile;
		this.basePackage = basePackage;
	}

	public String getPropertyFile()
	{
		return propertyFile;
	}

	public void setPropertyFile(String propertyFile)
	{
		this.propertyFile = propertyFile;
	}

	public String getBasePackage()
	{
		return basePackage;
	}

	public void setBasePackage(String basePackage)
	{
		this.basePackage = basePackage;
	}

	public void readProperties() throws Exception
	{
		String tempPropertyFile = this.propertyFile;
		String tempBasePackage = this.basePackage;

		log.debug("propertyFile: {}, tempPropertyFile: {}", propertyFile, tempPropertyFile);
		log.debug("basePackage: {}, tempBasePackage: {}", basePackage, tempBasePackage);

		try (JPropertyReader propertyReader = new JPropertyReader(tempPropertyFile);)
		{
			tempBasePackage = tempBasePackage.endsWith(".*") ? tempBasePackage : tempBasePackage + ".*";
			List<Class> classList = PackageScanner.findClasses(tempBasePackage);
			log.debug("classList: {}", classList);

			for (Class clazz : classList)
			{
				Field[] fields = clazz.getDeclaredFields();
				if (fields == null || fields.length < 1)
				{
					continue;
				}

				for (Field field : fields)
				{
					if (field.isAnnotationPresent(PropertyKey.class)
							&& Modifier.isStatic(field.getModifiers())
							&& !Modifier.isFinal(field.getModifiers()))
					{
						if (Modifier.isPrivate(field.getModifiers())
								|| Modifier.isProtected(field.getModifiers()))
						{
							field.setAccessible(true);
						}

						PropertyKey appPropertyKey = field.getAnnotation(PropertyKey.class);
						String value = propertyReader.getKey(appPropertyKey.name());

						log.debug("key: {} = value: {}", appPropertyKey.name(), value);

						if (value != null)
						{
							try
							{
								field.set(null, Singletons.getGson().fromJson(value, appPropertyKey.type()));
							} catch (Exception exception)
							{
								log.error("Exception", exception);
								field.set(null, value);
							}
						}

						if (Modifier.isPrivate(field.getModifiers())
								|| Modifier.isProtected(field.getModifiers()))
						{
							field.setAccessible(false);
						}
					}
				}
			}
		}
	}

	private void setFinalStatic(Field field, Object newValue) throws Exception
	{
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		modifiersField.setAccessible(false);
		field.set(null, newValue);
		field.setAccessible(false);
	}
}

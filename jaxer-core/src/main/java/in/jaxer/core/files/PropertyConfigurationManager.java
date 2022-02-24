
package in.jaxer.core.files;

import in.jaxer.core.annotations.PropertyConfiguration;
import in.jaxer.core.annotations.PropertyKey;
import in.jaxer.core.constants.Singletons;
import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.PackageScanner;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
@lombok.AllArgsConstructor
public class PropertyConfigurationManager
{

	public static final String PROPERTIES_EXT = ".properties";

	@lombok.Getter
	@lombok.Setter
	private String basePackage;

	public void readProperties() throws Exception
	{
		log.debug("basePackage: {}", this.getBasePackage());

		Set<Class> propertyConfigurationSet = PackageScanner.getClasses(basePackage, PropertyConfiguration.class);
		log.debug("propertyConfigurationSet: {}", propertyConfigurationSet);

		if (JValidator.isEmpty(propertyConfigurationSet))
		{
			log.error("No class found with @{} in base package: {}", PropertyConfiguration.class.getName(), this.getBasePackage());
			return;
		}

		String propertyFileName = null;
		for (Class<?> propertyConfigurationClass : propertyConfigurationSet)
		{
			PropertyConfiguration propertyConfigurationAnnotation = propertyConfigurationClass.getAnnotation(PropertyConfiguration.class);
			propertyFileName = propertyConfigurationAnnotation.value();
			log.info("propertyFileName: {}", propertyFileName);

			if (!propertyFileName.toLowerCase().endsWith(PROPERTIES_EXT))
			{
				throw new IllegalArgumentException("Property file '" + propertyFileName + "' must be a valid '" + PROPERTIES_EXT + "' file");
			}

			try (JPropertyReader jPropertyReader = new JPropertyReader(propertyFileName))
			{
				Field[] fields = propertyConfigurationClass.getDeclaredFields();
				if (fields == null || fields.length < 1)
				{
					log.warn("Field array found empty for class: {}", propertyConfigurationClass);
					continue;
				}

				Object newInstance = propertyConfigurationClass.newInstance();

				for (Field field : fields)
				{
					if (field.isAnnotationPresent(PropertyKey.class)
							//							&& Modifier.isStatic(field.getModifiers())
							&& !Modifier.isFinal(field.getModifiers()))
					{
						if (Modifier.isPrivate(field.getModifiers())
								|| Modifier.isProtected(field.getModifiers()))
						{
							field.setAccessible(true);
						}

						PropertyKey appPropertyKey = field.getAnnotation(PropertyKey.class);
						String value = jPropertyReader.getKey(appPropertyKey.name());

						log.debug("[key={}]=[value={}]", appPropertyKey.name(), value);

						if (value != null)
						{
							if (appPropertyKey.type().getName().equals(String.class.getName())
									|| appPropertyKey.type().getName().equals(Character.class.getName()))
							{
								field.set(newInstance, value);
							} else if (appPropertyKey.type().getName().equals(Integer.class.getName()))
							{
								field.set(newInstance, Integer.valueOf(value));
							} else if (appPropertyKey.type().getName().equals(Long.class.getName()))
							{
								field.set(newInstance, Long.valueOf(value));
							} else if (appPropertyKey.type().getName().equals(Float.class.getName()))
							{
								field.set(newInstance, Float.valueOf(value));
							} else if (appPropertyKey.type().getName().equals(Double.class.getName()))
							{
								field.set(newInstance, Double.valueOf(value));
							} else if (appPropertyKey.type().getName().equals(Short.class.getName()))
							{
								field.set(newInstance, Short.valueOf(value));
							} else if (appPropertyKey.type().getName().equals(Byte.class.getName()))
							{
								field.set(newInstance, Byte.valueOf(value));
							} else if (appPropertyKey.type().getName().equals(Boolean.class.getName()))
							{
								field.set(newInstance, Boolean.valueOf(value));
							} else
							{
								log.debug("appPropertyKey.type().getName(): {}", appPropertyKey.type().getName());
								try
								{
									field.set(newInstance, Singletons.getGson().fromJson(value, appPropertyKey.type()));
								} catch (Exception exception)
								{
									log.error("Exception", exception);

									try
									{
										field.set(newInstance, value);
									} catch (Exception innerException)
									{
										log.error("innerException", innerException);
									}
								}
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
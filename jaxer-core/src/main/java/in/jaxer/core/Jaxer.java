package in.jaxer.core;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Shakir
 * Sep 15, 2021 - 2:15:23 PM
 */
@Log4j2
public class Jaxer
{
	public static void main(String[] args) throws IOException
	{
		log.info("{}-v{}",
				 ProjectProperties.getKey(ProjectProperties.PROJECT_NAME),
				 ProjectProperties.getKey(ProjectProperties.PROJECT_VERSION));
	}

	public static class ProjectProperties
	{
		private final static Properties projectProperties = new Properties();

		public static final String PROJECT_VERSION = "project.version";

		public static final String PROJECT_ARTIFACTID = "project.artifactId";

		public static final String PROJECT_NAME = "project.name";

		public static final String PROJECT_DESCRIPTION = "project.description";

		public static final String PROJECT_URL = "project.url";

		public static final String PROJECT_ORGANIZATION_NAME = "project.organization.name";

		static
		{
			try
			{
				projectProperties.load(Jaxer.class.getClassLoader().getResourceAsStream("project.properties"));
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

		public static String getKey(String key)
		{
			return projectProperties.getProperty(key);
		}
	}

}

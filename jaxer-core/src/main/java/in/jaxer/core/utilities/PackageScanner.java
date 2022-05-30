package in.jaxer.core.utilities;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class PackageScanner
{
	private static final String DOT_STAR = ".*";
	private static final String DOT_CLASS = ".class";

	private static boolean isValidEntryName(String entryName)
	{
		log.debug("entryName: {}", entryName);

		return entryName != null
				&& entryName.endsWith(".class")
				&& !entryName.contains("$");
	}

	private static String removeDotClass(String className)
	{
		log.debug("className: {}", className);

		return Strings.removeEndsWith(className, ".class");
	}

	private static void getClassListFromJar(Set<Class> classes, URL jarUrl) throws IOException, ClassNotFoundException
	{
		log.debug("classes: {}, jarUrl: {}", classes, jarUrl);

		if (!jarUrl.getProtocol().equals("jar"))
		{
			throw new IllegalArgumentException("Unknown jar file: " + jarUrl);
		}

		String jarFileName;
		JarFile jarFile;
		Enumeration<JarEntry> jarEntries;
		String entryName;

		// build jar file name, then loop through zipped entries
		jarFileName = URLDecoder.decode(jarUrl.getFile(), "UTF-8");
		jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));

		log.info("jarFileName: {}", jarFileName);

		jarFile = new JarFile(jarFileName);
		jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements())
		{
			entryName = jarEntries.nextElement().getName();
			if (isValidEntryName(entryName))
			{
				String className = entryName.replace('/', '.');
				className = removeDotClass(className);
				classes.add(Class.forName(className));
			}
		}
	}

	private static void getClassListFromBuildFolder(Set<Class> classes, File buildFolder, String packageName, final boolean recursive) throws ClassNotFoundException
	{
		log.debug("classes: {}, buildFolder: {}, packageName: {}, recursive: {}", classes, buildFolder, packageName, recursive);

		File[] fileList = buildFolder.listFiles();
		for (File file : fileList)
		{
			if (file.isFile())
			{
				String entryName = file.getName();
				if (isValidEntryName(entryName))
				{
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

					String path = packageName.replace('.', '/');
					File packageFolder = new File(classLoader.getResource(path).getFile());

					String subPackage = "";
					subPackage = file.getAbsolutePath().replace(packageFolder.getAbsolutePath(), "");
					subPackage = subPackage.replace(file.getName(), "");

					if (packageFolder.exists() && packageFolder.canRead())
					{
						subPackage = removeDotClass(subPackage);
						subPackage = subPackage.contains("/") ? subPackage.replace("/", ".") : subPackage;
						subPackage = subPackage.contains("\\") ? subPackage.replace("\\", ".") : subPackage;
						packageName = packageName.contains("/") ? packageName.replace("/", ".") : packageName;
						packageName = packageName.contains("\\") ? packageName.replace("\\", ".") : packageName;

						String className = packageName + subPackage + "." + file.getName().substring(0, file.getName().length() - ".class".length());
						className = className.contains("..") ? className.replace("..", ".") : className;
						classes.add(Class.forName(className));
					}
				}
			} else if (recursive)
			{
				getClassListFromBuildFolder(classes, file, packageName, recursive);
			}
		}
	}

	private static Set<Class> getClassNamesFromPackage(String packageName) throws Exception
	{
		log.debug("packageName: {}", packageName);

		final Set<Class> classes = new HashSet<>();
		final boolean recursive = packageName.endsWith(".*");

		if (recursive)
		{
			packageName = packageName.substring(0, packageName.length() - ".*".length());
		}

		URL packageURL;
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		packageName = packageName.replace('.', '/');
		packageURL = classLoader.getResource(packageName);

		if (packageURL.getProtocol().equals("jar"))
		{
			getClassListFromJar(classes, packageURL);
		} else
		{
			URI uri = new URI(packageURL.toString());
			File folder = new File(uri.getPath());
			getClassListFromBuildFolder(classes, folder, packageName, recursive);
		}

		return classes;
	}

	public static Set<Class> findClasses(String packageName) throws Exception
	{
		log.debug("packageName: {}", packageName);
		return getClassNamesFromPackage(packageName);
	}

	public static Set<Class> findClasses(String packageName, Class<? extends Annotation> annotationClass) throws Exception
	{
		log.debug("packageName: {}, annotationClass: {}", packageName, annotationClass);

		Set<Class> classList = findClasses(packageName);

		Set<Class> annotationClassList = new HashSet<>();

		if (JValidator.isNotNullAndNotEmpty(classList))
		{
			for (Class clazz : classList)
			{
				if (clazz.isAnnotationPresent(annotationClass))
				{
					annotationClassList.add(clazz);
				}
			}
		}

		return annotationClassList;
	}

	public static Set<Class<?>> getClasses(String packageName)
	{
		log.debug("packageName: {}", packageName);
		Set<Class<?>> classSet = new HashSet<>();

		try
		{
			ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());

			ImmutableSet<ClassPath.ClassInfo> immutableSet = classPath.getTopLevelClassesRecursive(packageName);
			if (immutableSet != null)
			{
				for (ClassPath.ClassInfo classInfo : immutableSet)
				{
					String className = classInfo.getName();
					if (className.startsWith(packageName))
					{
						className = className.replace('/', '.');
						classSet.add(Class.forName(className));
					}
				}
			}
		} catch (Exception ex)
		{
			log.error("Exception", ex);
			throw new RuntimeException(ex);
		}
		return classSet;
	}

	public static Set<Class<? extends Annotation>> getClasses(String packageName, Class<? extends Annotation> annotationClass)
	{
		log.debug("packageName: {}, annotationClass: {}", packageName, annotationClass);

		Set<Class<? extends Annotation>> classSet = new HashSet<>();
		Set<Class<?>> classSetFull = getClasses(packageName);
		for (Class c : classSetFull)
		{
			if (c.isAnnotationPresent(annotationClass))
			{
				classSet.add(c);
			}
		}

		return classSet;
	}
}

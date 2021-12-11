
package in.jaxer.core.utilities;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author Shakir Ansari
 */
public class PackageScanner
{

	private static final String DOT_STAR = ".*";

	private static final String DOT_CLASS = ".class";

//	@Deprecated
//	private static Set<Class<?>> getTypesAnnotatedWith(Class c)
//	{
//		Reflections reflections = new Reflections("");
//
//		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(c);
//
//		for (Class<?> class1 : annotated)
//		{
////			System.out.println("PackageScanner.getTypesAnnotatedWith() - type: [" + class1.getName() + "]");
//		}
//		return annotated;
//	}
	private static boolean isValidEntryName(String entryName)
	{
		return entryName != null
				&& entryName.endsWith(".class")
				&& !entryName.contains("$");
	}

	private static String removeDotClass(String className)
	{
		return className.contains(".class")
				? className.substring(0, className.length() - ".class".length())
				: className;
	}

	private static void getClassListFromJar(List<Class> classes, URL jarUrl) throws UnsupportedEncodingException, IOException, ClassNotFoundException
	{
		System.out.println("PackageScanner.getClassListFromJar() - ");

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

		System.out.println("PackageScanner.getClassNamesFromPackage() - jarFileName: [" + jarFileName + "]");

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

	private static void getClassListFromBuildFolder(List<Class> classes, File buildFolder, String packageName, final boolean recursive) throws ClassNotFoundException
	{
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

	private static List<Class> getClassNamesFromPackage(String packageName) throws Exception
	{
		final List<Class> classes = new ArrayList<>();
		final boolean recursive = packageName.endsWith(".*");

		if (recursive)
		{
			packageName = packageName.substring(0, packageName.length() - ".*".length());
		}

		URL packageURL;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

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

	public static List<Class> findClasses(String packageName) throws Exception
	{
		List<Class> classList = getClassNamesFromPackage(packageName);

//		System.out.println("PackageScanner.findClasses() - classList: [" + classList + "]");
		return classList;
	}

	public static List<Class> findClasses(String packageName, Class<? extends Annotation> annotationClass) throws Exception
	{
		List<Class> classList = findClasses(packageName);

		List<Class> annotationClassList = new ArrayList<>();

		if (JValidator.isNotEmpty(classList))
		{
			for (Class clazz : classList)
			{
				if (clazz.isAnnotationPresent(annotationClass))
				{
					annotationClassList.add(clazz);
				}
			}
		}

		System.out.println("PackageScanner.findClasses() - classList: [" + classList + "]");

		return annotationClassList;
	}
}

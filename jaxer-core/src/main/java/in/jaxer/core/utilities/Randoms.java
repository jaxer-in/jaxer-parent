package in.jaxer.core.utilities;

import in.jaxer.core.RandomUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Random;

/**
 * @author Shakir Ansari
 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.RandomUtils}
 */
@Log4j2
@Deprecated
public class Randoms
{
	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getInt()}
	 */
	@Deprecated
	public static int getRandomInt()
	{
		return new Random().nextInt();
	}

	@Deprecated
	public static int getRandomIntInRange(int min, int max)
	{
		return new Random().nextInt((max - min) + 1) + min;
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getInt(int, int)}
	 */
	@Deprecated
	public static int getRandomInt(int min, int max)
	{
		return new Random().nextInt((max - min) + 1) + min;
	}

	@Deprecated
	public static int getRandomIntLowerThan(int lowerValue)
	{
		return new Random().nextInt(lowerValue - 1);
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getIntLessThan(int)}
	 */
	@Deprecated
	public static int getRandomIntLessThan(int lowerValue)
	{
		return new Random().nextInt(lowerValue - 1);
	}

	@Deprecated
	public static int getRandomIntHigherThan(int highValue)
	{
		return getRandomIntInRange(highValue + 1, Integer.MAX_VALUE);
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getIntGreaterThan(int)}
	 */
	@Deprecated
	public static int getRandomIntGreaterThan(int highValue)
	{
		return getRandomIntInRange(highValue + 1, Integer.MAX_VALUE);
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getChar()}
	 */
	@Deprecated
	public static char getRandomChar()
	{
		return (char) Randoms.getRandomIntInRange('a', 'z');
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getChar(char[])}
	 */
	@Deprecated
	public static char getRandomChar(char[] array)
	{
		return array[getRandomIntInRange(0, array.length - 1)];
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getChar(char, char)}
	 */
	@Deprecated
	public static char getRandomChar(char minChar, char maxChar)
	{
		return (char) Randoms.getRandomIntInRange(minChar, maxChar);
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getChar()}
	 */
	@Deprecated
	public static char getRandomCharLowercase()
	{
		return (char) Randoms.getRandomInt('a', 'z');
	}

	/**
	 * @deprecated on v1.1.0-beta, please use {@link RandomUtils#getCharUppercase()}
	 */
	@Deprecated
	public static char getRandomCharUppercase()
	{
		return (char) Randoms.getRandomIntInRange('A', 'Z');
	}
}

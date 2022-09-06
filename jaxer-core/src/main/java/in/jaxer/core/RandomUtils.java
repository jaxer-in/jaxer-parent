package in.jaxer.core;

import lombok.extern.log4j.Log4j2;

import java.util.Random;

/**
 * @author Shakir
 * @since v1.1.0-beta [2022-08-18]
 */
@Log4j2
@SuppressWarnings("unused")
public class RandomUtils
{
	/**
	 * @since v1.1.0-beta
	 */
	public static byte getByte()
	{
		return (byte) getInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static byte getByte(byte min, byte max)
	{
		return (byte) getInt(min, max);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static byte getByteLessThan(byte lowerValue)
	{
		return (byte) getIntLessThan(lowerValue);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static byte getByteGreaterThan(byte highValue)
	{
		return (byte) getIntGreaterThan(highValue);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static short getShort()
	{
		return (short) getInt(Short.MIN_VALUE, Short.MAX_VALUE);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static short getShort(short min, short max)
	{
		return (short) getInt(min, max);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static short getShortLessThan(short lowerValue)
	{
		return (short) getIntLessThan(lowerValue);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static short getShortGreaterThan(short highValue)
	{
		return (short) getIntGreaterThan(highValue);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static int getInt()
	{
		return new Random().nextInt();
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static int getInt(int min, int max)
	{
		return new Random().nextInt((max - min) + 1) + min;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static int getIntLessThan(int lowerValue)
	{
		return new Random().nextInt(lowerValue - 1);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static int getIntGreaterThan(int highValue)
	{
		return getInt(highValue + 1, Integer.MAX_VALUE);
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static long getLong()
	{
		return new Random().nextLong();
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static float getFloat()
	{
		return new Random().nextFloat();
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static double getDouble()
	{
		return new Random().nextDouble();
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static char getChar()
	{
		return (char) getInt('a', 'z');
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static char getCharUppercase()
	{
		return getChar('A', 'Z');
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static char getChar(char minChar, char maxChar)
	{
		return (char) getInt(minChar, maxChar);
	}

	/**
	 * @since v1.1.0-beta
	 */
	//TODO
	public static char getChar(char[] array)
	{
		return array[getInt(0, array.length - 1)];
	}

}

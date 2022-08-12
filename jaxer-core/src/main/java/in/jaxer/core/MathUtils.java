package in.jaxer.core;

/**
 * @author Shakir
 * @date 13-08-2022
 * @since v1.1.0-beta
 */
public class MathUtils
{
	/**
	 * @since v1.1.0-beta
	 */
	public static byte min(byte... values)
	{
		byte min = values[0];
		for (byte val : values)
		{
			min = (byte) Math.min(min, val);
		}
		return min;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static byte max(byte... values)
	{
		byte max = values[0];
		for (byte value : values)
		{
			max = (byte) Math.max(max, value);
		}
		return max;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static short min(short... values)
	{
		short min = values[0];
		for (short val : values)
		{
			min = (short) Math.min(min, val);
		}
		return min;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static short max(short... values)
	{
		short max = values[0];
		for (short value : values)
		{
			max = (short) Math.max(max, value);
		}
		return max;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static int min(int... values)
	{
		int min = values[0];
		for (int val : values)
		{
			min = Math.min(min, val);
		}
		return min;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static int max(int... values)
	{
		int max = values[0];
		for (int value : values)
		{
			max = Math.max(max, value);
		}
		return max;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static long min(long... values)
	{
		long min = values[0];
		for (long val : values)
		{
			min = Math.min(min, val);
		}
		return min;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static long max(long... values)
	{
		long max = values[0];
		for (long value : values)
		{
			max = Math.max(max, value);
		}
		return max;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static float min(float... values)
	{
		float min = values[0];
		for (float val : values)
		{
			min = Math.min(min, val);
		}
		return min;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static float max(float... values)
	{
		float max = values[0];
		for (float value : values)
		{
			max = Math.max(max, value);
		}
		return max;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static double min(double... values)
	{
		double min = values[0];
		for (double val : values)
		{
			min = Math.min(min, val);
		}
		return min;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static double max(double... values)
	{
		double max = values[0];
		for (double value : values)
		{
			max = Math.max(max, value);
		}
		return max;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static int getPercentage(int value, int percentage)
	{
		return value * percentage / 100;
	}

	/**
	 * @since v1.1.0-beta
	 */
	public static double getPercentage(double value, float percentage)
	{
		return value * percentage / 100;
	}

}

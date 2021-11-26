
package in.jaxer.core.utilities;

import java.text.DecimalFormat;

/**
 *
 * @author Shakir Ansari
 */
public class NumberToWords
{

	private NumberToWords()
	{
	}

	private static final String[] tensNames =
	{
		"",
		" ten",
		" twenty",
		" thirty",
		" forty",
		" fifty",
		" sixty",
		" seventy",
		" eighty",
		" ninety"
	};

	private static final String[] numNames =
	{
		"",
		" one",
		" two",
		" three",
		" four",
		" five",
		" six",
		" seven",
		" eight",
		" nine",
		" ten",
		" eleven",
		" twelve",
		" thirteen",
		" fourteen",
		" fifteen",
		" sixteen",
		" seventeen",
		" eighteen",
		" nineteen"
	};

	private static String convertLessThanOneThousand(int number)
	{
		String soFar;

		if (number % 100 < 20)
		{
			soFar = numNames[number % 100];
			number /= 100;
		} else
		{
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
		{
			return soFar;
		}
		return numNames[number] + " hundred" + soFar;
	}

	public static String convert(long number)
	{
		// 0 to 999 999 999 999 999 999 999- quintillion
		if (number == 0)
		{
			return "zero";
		}

		String result = "";
		String snumber = Long.toString(number);

		// pad with "0"
		String mask = "000000000000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		int quintillion = Integer.parseInt(snumber.substring(0, 3));
		int quadrillion = Integer.parseInt(snumber.substring(3, 6));
		int trillions = Integer.parseInt(snumber.substring(6, 9));
		int billions = Integer.parseInt(snumber.substring(9, 12));
		int millions = Integer.parseInt(snumber.substring(12, 15));
		int hundredThousands = Integer.parseInt(snumber.substring(15, 18));
		int thousands = Integer.parseInt(snumber.substring(18, 21));

		if (quintillion != 0)
		{
			result = convertLessThanOneThousand(quintillion) + " quintillion ";
		}

		if (quadrillion != 0)
		{
			result = result + convertLessThanOneThousand(quadrillion) + " quadrillion ";
		}

		if (trillions != 0)
		{
			result = result + convertLessThanOneThousand(trillions) + " trillion ";
		}

		if (billions != 0)
		{
			result = result + convertLessThanOneThousand(billions) + " billion ";
		}

		if (millions != 0)
		{
			result = result + convertLessThanOneThousand(millions) + " million ";
		}

		switch (hundredThousands)
		{
			case 0:
				break;
			case 1:
				result = result + " one thousand ";
				break;
			default:
				result = result + convertLessThanOneThousand(hundredThousands) + " thousand ";
				break;
		}

		result = result + convertLessThanOneThousand(thousands);

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}
}

package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class Sortings
{
	public static void bubbleSort(int[] array)
	{
		log.debug("array: {}", Arrays.toString(array));

		boolean doMore = true;
		while (doMore)
		{
			doMore = false;
			for (int i = 0; i < array.length - 1; i++)
			{
				if (array[i] > array[i + 1])
				{
					int temp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = temp;
					doMore = true;
				}
			}
		}
	}

	public static void selectionSort(int[] array)
	{
		log.debug("array: {}", Arrays.toString(array));

		for (int i = 0; i < array.length - 1; i++)
		{
			// Index of smallest remaining value.
			int minIndex = i;
			for (int j = i + 1; j < array.length; j++)
			{
				if (array[minIndex] > array[j])
				{
					minIndex = j;
				}
			}

			if (minIndex != i)
			{
				// Exchange current element with smallest remaining.
				int temp = array[i];
				array[i] = array[minIndex];
				array[minIndex] = temp;
			}
		}
	}

	public static void reverseArray(int[] array)
	{
		log.debug("array: {}", Arrays.toString(array));

		int left = 0;
		int right = array.length - 1;

		while (left < right)
		{
			int temp = array[left];
			array[left] = array[right];
			array[right] = temp;

			left++;
			right--;
		}
	}
}

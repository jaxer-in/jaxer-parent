package in.jaxer.core.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Shakir
 * date 2022-05-23 16:46
 */
class SortingsTest
{
	@Nested
	public class BubbleSortTest
	{
		@Test
		void bubbleSortTest1()
		{
			System.out.println("bubbleSortTest1");

			int[] sourceArray = {10, 2, 25, 5, 7, 8};
			int[] expectedArray = {2, 5, 7, 8, 10, 25};

			Sortings.bubbleSort(sourceArray);

			Assertions.assertArrayEquals(expectedArray, sourceArray);
		}

		@Test
		void bubbleSortTest2()
		{
			System.out.println("bubbleSortTest2");

			int length = 100;
			int[] array = new int[length];
			for (int i = 0; i < length; i++)
			{
				array[i] = Randoms.getRandomInt();
			}

			Sortings.bubbleSort(array);
			int min = array[0];
			int max = array[length - 1];

			Assertions.assertEquals(min, JUtilities.min(array));
			Assertions.assertEquals(max, JUtilities.max(array));
		}
	}

	@Nested
	public class SelectionSortTest
	{
		@Test
		void selectionSortTest1()
		{
			System.out.println("selectionSortTest1");
			int[] sourceArray = {10, 2, 333, 25, 5, 7, 8, 99};
			int[] expectedArray = {2, 5, 7, 8, 10, 25, 99, 333};

			Sortings.selectionSort(sourceArray);

			Assertions.assertArrayEquals(expectedArray, sourceArray);
		}

		@Test
		void selectionSortTest2()
		{
			System.out.println("selectionSortTest2");

			int length = 100;
			int[] array = new int[length];
			for (int i = 0; i < length; i++)
			{
				array[i] = Randoms.getRandomInt();
			}

			Sortings.selectionSort(array);
			int min = array[0];
			int max = array[length - 1];

			Assertions.assertEquals(min, JUtilities.min(array));
			Assertions.assertEquals(max, JUtilities.max(array));
		}
	}

	@Nested
	public class ReverseArrayTest
	{
		@Test
		void reverseArrayTest1()
		{
			System.out.println("reverseArrayTest1");
			int[] sourceArray = {10, 2, 333, 25, 5, 7, 8, 99};
			int[] expectedArray = {99, 8, 7, 5, 25, 333, 2, 10};

			Sortings.reverseArray(sourceArray);

			Assertions.assertArrayEquals(expectedArray, sourceArray);
		}

		@Test
		void reverseArrayTest2()
		{
			System.out.println("reverseArrayTest2");

			int length = 100;
			int[] array = new int[length];
			for (int i = 0; i < length; i++)
			{
				array[i] = Randoms.getRandomInt();
			}

			Sortings.selectionSort(array);

			int min = array[0];
			int max = array[length - 1];

			Sortings.reverseArray(array);

			Assertions.assertEquals(max, array[0]);
			Assertions.assertEquals(min, array[length - 1]);
		}
	}
}
package in.jaxer.core;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author Shakir
 * @date 17-08-2022
 * @since v1.1.0-beta
 */
@Log4j2
public class SortingUtilsTest
{
	@Nested
	class BubbleSortTest
	{
		@Nested
		class StringTest
		{
			@Test
			void withRandomStrings()
			{
				String[] unsortedArray = {"shakir", "ansari", "john", "doe", "ajaxer"};
				log.info("before {}", Arrays.toString(unsortedArray));

				ArrayUtils.bubbleSort(unsortedArray);
				log.info("after {}", Arrays.toString(unsortedArray));

				String[] sortedArray = {"ajaxer", "ansari", "doe", "john", "shakir"};

				Assertions.assertArrayEquals(unsortedArray, sortedArray);
			}
		}
	}

	@Nested
	class ReverseTest
	{
		@Nested
		class StringTest
		{
			@Test
			void withRandomStrings()
			{
				String[] beforeArray = {"shakir", "ansari", "john", "doe", "ajaxer"};
				log.info("before {}", Arrays.toString(beforeArray));

				ArrayUtils.reverse(beforeArray);
				log.info("after {}", Arrays.toString(beforeArray));

				String[] expectedArray = {"ajaxer", "doe", "john", "ansari", "shakir"};

				Assertions.assertArrayEquals(beforeArray, expectedArray);
			}
		}
	}
}

package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Shakir Ansari
 * @since 0.0.1
 */
@Log4j2
public class Collections
{
	/**
	 * @since 0.0.1
	 * @deprecated As of 1.0.6-beta, replaced by {@link #isNullOrEmpty(Collection)}
	 */
	@Deprecated
	public static boolean isEmpty(Collection collection)
	{
		return JValidator.isEmpty(collection);
	}

	/**
	 * @since 0.0.1
	 * @deprecated As of 1.0.6-beta, replaced by {@link #isNotNullAndNotEmpty(Collection)}
	 */
	@Deprecated
	public static boolean isNotEmpty(Collection collection)
	{
		return JValidator.isNotEmpty(collection);
	}

	/**
	 * @see in.jaxer.core.utilities.JValidator#isBlank(Collection)
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta
	 */
	@Deprecated
	public static boolean isNullOrEmpty(Collection collection)
	{
		return collection == null || collection.isEmpty();
	}

	/**
	 * @see in.jaxer.core.utilities.JValidator#isNotBlank(Collection)
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta
	 */
	@Deprecated
	public static boolean isNotNullAndNotEmpty(Collection collection)
	{
		return collection != null && !collection.isEmpty();
	}

	/**
	 * @since 0.0.1
	 * @deprecated As of 1.0.6-beta, replaced by {@link #isNullOrEmpty(Map)}
	 */
	@Deprecated
	public static boolean isEmpty(Map map)
	{
		return JValidator.isEmpty(map);
	}

	/**
	 * @since 0.0.1
	 * @deprecated As of 1.0.6-beta, replaced by {@link #isNotNullAndNotEmpty(Map)}
	 */
	@Deprecated
	public static boolean isNotEmpty(Map map)
	{
		return !isEmpty(map);
	}

	/**
	 * @see in.jaxer.core.utilities.JValidator#isBlank(Map)
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta
	 */
	@Deprecated
	public static boolean isNullOrEmpty(Map map)
	{
		return map == null || map.isEmpty();
	}

	/**
	 * @see in.jaxer.core.utilities.JValidator#isNotBlank(Map)
	 * @since 1.0.6-beta
	 * @deprecated As of 1.0.9-beta
	 */
	@Deprecated
	public static boolean isNotNullAndNotEmpty(Map map)
	{
		return map != null && !map.isEmpty();
	}

	/**
	 * @since 0.0.1
	 */
	public static <T> List<T> append(List<T> list1, List<T> list2)
	{
		if (list1 == null && list2 == null)
		{
			log.warn("list1: {}, list2: {}", list1, list2);
			return null;
		}

		if (list1.isEmpty())
		{
			return list2;
		}

		if (list2.isEmpty())
		{
			return list1;
		}

		for (T t : list2)
		{
			list1.add(t);
		}

		return list1;
	}

	/**
	 * @since 0.0.1
	 */
	public static <T> List<T> subList(List<T> list, int startIndex)
	{
		if (JValidator.isBlank(list))
		{
			return list;
		}

		return list.subList(startIndex, list.size());
	}

	/**
	 * @see java.util.List#subList(int, int)
	 * @since 0.0.1
	 * @deprecated As of 1.0.9-beta
	 */
	@Deprecated
	public static <T> List<T> subList(List<T> list, int startIndex, int endIndex)
	{
		if (JValidator.isBlank(list))
		{
			return list;
		}

		if (startIndex < 0)
		{
			startIndex = 0;
		}

		if (endIndex > list.size())
		{
			endIndex = list.size();
		}

		if (startIndex == 0 && endIndex == list.size())
		{
			return list;
		}

		List<T> responseList = new ArrayList<>();
		for (int i = startIndex; i < endIndex - 1; i++)
		{
			responseList.add(list.get(i));
		}

		return responseList;
	}

	/**
	 * @see 0.0.1
	 */
	public static <T> List<T> reverse(List<T> list)
	{
		if (JValidator.isBlank(list))
		{
			return list;
		}

		int left = 0;
		int right = list.size() - 1;
		T temp = null;

		while (left < right)
		{
			temp = list.get(left);
			list.set(left, list.get(right));
			list.set(right, temp);

			left++;
			right--;
		}

		return list;
	}

	/**
	 * @see 0.0.1
	 */
	public static <T> List<T> getNextIdList(int chunk, int currentIndex, List<T> idList)
	{
		List<T> temp = new ArrayList<>();

		for (int i = currentIndex; i < chunk + currentIndex; i++)
		{
			if (i < idList.size())
			{
				temp.add(idList.get(i));
			}
		}

		return temp;
	}

	/**
	 * @see 1.0.9-beta
	 */
	public static <T> List<T> asArrayList(T... tArray)
	{
		if (tArray == null || tArray.length == 0)
		{
			return null;
		}

		List<T> tList = new ArrayList<>();
		for (T t : tArray)
		{
			tList.add(t);
		}

		return tList;
	}

	/**
	 * @see 1.0.9-beta
	 */
	public static <T> List<T> getTopIdList(List<T> idList, int chunkSize)
	{
		if (JValidator.isBlank(idList))
		{
			log.warn("idList is null or empty");
			return null;
		}

		if (chunkSize > idList.size())
		{
			log.warn("chunkSize: {} is greater then idList.size(): {}", chunkSize, idList.size());
			return idList;
		}

		List<T> topIdList = null;
		for (int i = 0; i < chunkSize; i++)
		{
			if (i < idList.size())
			{
				if (topIdList == null)
				{
					topIdList = new ArrayList<>();
				}

				topIdList.add(idList.get(i));
			}
		}
		return topIdList;
	}
}

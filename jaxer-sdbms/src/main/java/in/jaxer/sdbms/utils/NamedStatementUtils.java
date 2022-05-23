package in.jaxer.sdbms.utils;

import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.Strings;
import in.jaxer.sdbms.NamedStatement;
import in.jaxer.sdbms.Parameter;
import in.jaxer.sdbms.annotations.Column;
import lombok.extern.log4j.Log4j2;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Shakir
 * Date 4 Nov, 2021 - 11:19:40 AM
 */
@Log4j2
public class NamedStatementUtils
{

	private static String getUniqueParameterName(String parameterName)
	{
		return parameterName + "_" + Strings.getUUID(parameterName).replace("-", "");
//		return parameterName + "_" + HashHandler.getMD5Hash(parameterName);
//		return parameterName + "_" + Number52Encoder.encode(parameterName);
	}

	private static String getUniqueParameterName(String parameterName, Object object)
	{
		return getUniqueParameterName(parameterName + object.toString().replace(" ", ""));
	}

	public static String setParameterListName(String sqlQuery, String parameterName, Collection collection)
	{
		String tempParam = "";

		for (int i = 0; i < collection.size(); i++)
		{
//			tempParam += ":" + getUniqueParameterName(parameterName + i) + ",";
			tempParam += ":" + parameterName + i + ",";
		}

		if (tempParam.startsWith(":"))
		{
			tempParam = tempParam.substring(1);
		}

		if (tempParam.endsWith(","))
		{
			tempParam = tempParam.substring(0, tempParam.length() - 1);
		}

		return sqlQuery.replace(parameterName, tempParam);
	}

	public static String setParameterListName(String sqlQuery, List<Parameter> parameters)
	{
		for (Parameter param : parameters)
		{
			if (param.isCollection())
			{
				sqlQuery = setParameterListName(sqlQuery, param.getName(), (Collection) param.getValue());
			}
		}
		return sqlQuery;
	}

	public static void setParameterValue(Map<String, Parameter> valueMap, List<Parameter> parameters)
	{
		if (JValidator.isNullOrEmpty(parameters))
		{
			return;
		}

		for (Parameter param : parameters)
		{
			if (param.isCollection())
			{
				Collection collection = (Collection) param.getValue();

				int index = 0;
				for (Object object : collection)
				{
					String newParamName = param.getName() + index++;
					valueMap.put(newParamName, new Parameter(newParamName, object));
				}
			} else
			{
				valueMap.put(param.getName(), param);
			}
		}
	}

	public static String queryParser(String query, Map paramMap)
	{
		JValidator.throwWhenNullOrEmpty(query, "Query cannot be empty");

		int length = query.length();
		StringBuilder parsedQuery = new StringBuilder(length);

		boolean inSingleQuote = false;
		boolean isDoubleQuote = false;

		int index = 1;

		for (int i = 0; i < length; i++)
		{
			char c = query.charAt(i);
			if (inSingleQuote)
			{
				if (c == '\'')
				{
					inSingleQuote = false;
				}
			} else if (isDoubleQuote)
			{
				if (c == '"')
				{
					isDoubleQuote = false;
				}
			} else
			{
				if (c == '\'')
				{
					inSingleQuote = true;
				} else if (c == '"')
				{
					isDoubleQuote = true;
				} else if (c == ':' && i + 1 < length && Character.isJavaIdentifierStart(query.charAt(i + 1)))
				{
					int j = i + 2;
					while (j < length && Character.isJavaIdentifierPart(query.charAt(j)))
					{
						j++;
					}
					String name = query.substring(i + 1, j);
					c = '?'; // replace the parameter with a question mark
					i += name.length(); // skip past the end if the parameter

					List indexList = (List) paramMap.get(name);
					if (indexList == null)
					{
						indexList = new ArrayList();
						paramMap.put(name, indexList);
					}

					//Add index in list
					indexList.add(index++);
				}
			}
			parsedQuery.append(c);
		}

		return parsedQuery.toString();
	}

	public static void main(String[] args) throws SQLException
	{
		List<String> items = Arrays.asList("it000", "it001", "it002");
		List<String> prods = Arrays.asList("pr000", "pr001", "pr002");
		String p1 = "v1";
		int p2 = 23;

		String sql = "select * from table where item in (:items) and prod in (:prods) and p1 = :p1 and p2 = :p2";

		Map<String, Collection> nameMap1 = new HashMap<>();
		nameMap1.put("items", items);
		nameMap1.put("prods", prods);

		Map<String, Object> nameMap2 = new HashMap<>();
		nameMap2.put("p1", p1);
		nameMap2.put("p2", p2);
		nameMap2.put("p3", null);

		Map<String, Object> valueMap = new HashMap<>();

//		setParameterValue(valueMap, nameMap1);
//		setParameterValue(valueMap, nameMap2);
//		sql = setParameterListName(sql, nameMap1);
//		System.out.println("NamedPreparedStatementUtils.main() - " + sql);
//		System.out.println("NamedPreparedStatementUtils.main() - valueMap [" + valueMap + "]");
		try (NamedStatement namedStatement = new NamedStatement(null, sql);)
		{
			namedStatement.setParameter("p1", p1);
			namedStatement.setParameter("p2", p2);
			namedStatement.setParameterList("items", items);
			namedStatement.setParameterList("prods", prods);

//			System.out.println("== getQuery: " + namedStatement.getQuery());
//			System.out.println("== getParamMap: " + namedStatement.getParamMap());
//			System.out.println("== getParamlistMap: " + namedStatement.getParamlistMap());
//			System.out.println("== getValueMap: " + namedStatement.getValueMap());
//			System.out.println("== getIndexMap: " + namedStatement.getIndexMap());
//			Set a = namedStatement.getIndexMap().entrySet();
//			for (Object object : a)
//			{
//				System.out.println("NamedStatementUtils.main() - " + object);
//			}
		}
	}

	public static void setParameteres(NamedStatement namedStatement, List<Parameter> parameterList)
	{
		if (JValidator.isNullOrEmpty(parameterList))
		{
			log.error("parameterList is empty");
			return;
		}

		for (Parameter parameter : parameterList)
		{
			if (parameter.getValue() instanceof Collection)
			{
				namedStatement.setParameterList(parameter.getName(), (Collection) parameter.getValue());
			} else
			{
				namedStatement.setParameter(parameter.getName(), parameter.getValue());
			}
		}
	}

	public static void setParameteres(NamedStatement namedStatement, HashMap<String, Object> hashMap, Class outputClass, Object bean) throws Exception
	{
		Set<Map.Entry<String, Object>> entryset = hashMap.entrySet();
		for (Map.Entry<String, Object> entry : entryset)
		{
			Field[] fields = outputClass.getDeclaredFields();
			for (Field field : fields)
			{
				if (!field.isAnnotationPresent(Column.class))
				{
					continue;
				}
				if (entry.getValue().equals(field.getName()))
				{
					Object object = new PropertyDescriptor(field.getName(), outputClass).getReadMethod().invoke(bean);
					namedStatement.setParameter((String) entry.getValue(), object);
				}
			}
		}
	}
}

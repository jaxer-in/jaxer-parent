package in.jaxer.sdbms;

import in.jaxer.core.utilities.JValidator;
import in.jaxer.sdbms.exceptions.ColumnNotFoundException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shakir
 */
@Getter
public class Row
{
	private final List<Column> columnList;

	public Row(List<Column> columnList)
	{
		this.columnList = new ArrayList<>(columnList);
	}

	public Column getColumn(String columnName)
	{
		if (JValidator.isNotNullAndNotEmpty(columnList))
		{
			for (Column column : columnList)
			{
				if (column.getName().equalsIgnoreCase(columnName))
				{
					return column;
				}
			}
		}

		throw new ColumnNotFoundException("Column not found by name: " + columnName);
	}

	public Column getColumn(int columnIndex)
	{
		if (JValidator.isNotNullAndNotEmpty(columnList))
		{
			for (Column column : columnList)
			{
				if (column.getIndex() == columnIndex)
				{
					return column;
				}
			}
		}

		throw new ColumnNotFoundException("Column not found at index: " + columnIndex);
	}

	@Override
	public String toString()
	{
		String row = this.getClass().getName() + " [";

		if (JValidator.isNotNullAndNotEmpty(this.columnList))
		{
			String appender = ", ";
			for (Column column : columnList)
			{
				row += "`" + column.getName().toLowerCase() + "`" + "=" + column.getValue() + appender;
			}

			if (row.endsWith(appender))
			{
				row = row.substring(0, row.length() - appender.length());
			}
		}

		return row + "]";
	}
}

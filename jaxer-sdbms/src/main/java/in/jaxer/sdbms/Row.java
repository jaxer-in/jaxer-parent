
package in.jaxer.sdbms;

import in.jaxer.core.utilities.JValidator;
import in.jaxer.sdbms.exceptions.ColumnNotFoundException;
import in.jaxer.sdbms.exceptions.ReadOnlyException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author Shakir Ansari
 */
@Getter
public class Row
{

	private final List<Column> columnList;

	private boolean readOnly;

	public Row(boolean readOnly)
	{
		this.columnList = new ArrayList<>();
		this.readOnly = readOnly;
	}

	public void makeReadOnly()
	{
		this.readOnly = true;
	}

	public Column getColumn(String columnName)
	{
		if (JValidator.isNotEmpty(columnList))
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
		if (JValidator.isNotEmpty(columnList))
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

	public void addColumn(Column column)
	{
		if (this.readOnly)
		{
			throw new ReadOnlyException("Row is read only, unable to add columns");
		}
		this.columnList.add(column);
	}

	@Override
	public String toString()
	{
		String row = this.getClass().getName() + " [";

		if (JValidator.isNotEmpty(this.columnList))
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

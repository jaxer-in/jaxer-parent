
package in.jaxer.sdbms;

import in.jaxer.core.utilities.Validator;
import in.jaxer.sdbms.exceptions.ColumnNotFoundException;
import in.jaxer.sdbms.exceptions.ReadOnlyException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shakir Ansari
 */
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

	public boolean isReadOnly()
	{
		return readOnly;
	}

	public List<Column> getColumnList()
	{
		return this.columnList;
	}

	public Column getColumn(String columnName)
	{
		if (Validator.isNotEmpty(columnList))
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
		if (Validator.isNotEmpty(columnList))
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

	public Object getColumnValue(String columnName)
	{
		return getColumn(columnName).getValue();
	}

	public Object getColumnValue(int columnIndex)
	{
		return getColumn(columnIndex).getValue();
	}

	public void addColumn(Column column)
	{
		if (this.readOnly)
		{
			throw new ReadOnlyException("Row in read only, unable to add columns");
		}
		this.columnList.add(column);
	}

	@Override
	public String toString()
	{
		String row = this.getClass().getName() + " [";

		if (Validator.isNotEmpty(this.columnList))
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

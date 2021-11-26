
package in.jaxer.sdbms;

/**
 *
 * @author Shakir Ansari
 */
public class Column
{

	private int index;

	private String name;

	private Object value;

	private boolean autoIncrement;

	private boolean nullable;

	public Column(int index, String name, Object value, boolean autoIncrement, boolean nullable)
	{
		this.index = index;
		this.name = name;
		this.value = value;
		this.autoIncrement = autoIncrement;
		this.nullable = nullable;
	}

	public int getIndex()
	{
		return index;
	}

	public String getName()
	{
		return name;
	}

	public Object getValue()
	{
		return value;
	}

	public boolean isAutoIncrement()
	{
		return autoIncrement;
	}

	public boolean isNullable()
	{
		return nullable;
	}

	@Override
	public String toString()
	{
		return Column.class.getName() + " {" + "index=" + index + ", name=" + name + ", value=" + value + ", autoIncrement=" + autoIncrement + ", nullable=" + nullable + '}';
	}
}

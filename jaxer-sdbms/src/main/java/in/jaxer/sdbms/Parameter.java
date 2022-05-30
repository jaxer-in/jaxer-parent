package in.jaxer.sdbms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

/**
 * @author Shakir Ansari
 */
@Getter
@Setter
@ToString
public class Parameter
{

	private String name;

	private Object value;

	private boolean equals;

	private boolean collection;

	/**
	 * @param name  Name of the parameter
	 * @param value Value of the parameter
	 */
	public Parameter(String name, Object value)
	{
		this.name = name;
		this.value = value;
		this.equals = true;
		this.collection = this.value instanceof Collection;
	}

	/**
	 * @param name   Name of the parameter
	 * @param value  Value of the parameter
	 * @param equals value is equal or not
	 *               user_id = 2 OR user_id != 2
	 */
	public Parameter(String name, Object value, boolean equals)
	{
		this.name = name;
		this.value = value;
		this.equals = equals;
	}
}

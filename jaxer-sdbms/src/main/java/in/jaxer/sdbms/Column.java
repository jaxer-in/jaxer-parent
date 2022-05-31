package in.jaxer.sdbms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Shakir
 */
@Getter
@ToString
@AllArgsConstructor
public class Column
{
	private int index;
	private String name;
	private Object value;
	private boolean autoIncrement;
	private boolean nullable;
}

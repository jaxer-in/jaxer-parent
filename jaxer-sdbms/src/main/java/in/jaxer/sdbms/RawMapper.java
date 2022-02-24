
package in.jaxer.sdbms;

import in.jaxer.sdbms.exceptions.JaxerSDBMSException;
import java.sql.ResultSet;

/**
 *
 * @author Shakir Ansari
 * @param <T> for entity/model class
 */
public interface RawMapper<T>
{

	/**
	 *
	 * @param resultSet
	 * @param index     will start from zero 0
	 * @return
	 * @throws JaxerSDBMSException
	 */
	T map(ResultSet resultSet, int index) throws JaxerSDBMSException;
}

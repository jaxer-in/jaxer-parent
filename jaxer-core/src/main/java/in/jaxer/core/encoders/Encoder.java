
package in.jaxer.core.encoders;

/**
 *
 * @author Shakir
 * @date 24 Apr, 2022 - 6:28:45 PM
 */
public abstract class Encoder
{

	abstract public String convert(int x);

	abstract public int convert(String string);

//	abstract public String validationPattern();

	abstract public String encode(String message);

	abstract public String decode(String message);

}

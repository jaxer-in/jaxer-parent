
package in.jaxer.core.encoders;

/**
 *
 * @author Shakir
 * @date 24 Apr, 2022 - 6:28:45 PM
 */
public interface Encoder
{

	public String encode(String message);

	public String decode(String message);

}

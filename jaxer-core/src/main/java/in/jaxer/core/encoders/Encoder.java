
package in.jaxer.core.encoders;

/**
 *
 * @author Shakir
 * @date 24 Apr, 2022 - 6:28:45 PM
 */
public interface Encoder
{

	String INVALID_ENCRYPTION_FORMAT = "Invalid encryption format";

	public String encode(String message);

	public String decode(String message);

}

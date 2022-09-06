package in.jaxer.core.interfaces;

/**
 * @author Shakir
 * @date 2022-04-24
 */
public interface Encoder
{
	String INVALID_ENCRYPTION_FORMAT = "Invalid encryption format";

	String encode(String message);

	String decode(String message);
}

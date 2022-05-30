package in.jaxer.core.encoders;

/**
 * @author Shakir
 * date 2022-04-24 - 18:28
 */
public interface Encoder
{
	String INVALID_ENCRYPTION_FORMAT = "Invalid encryption format";

	String encode(String message);

	String decode(String message);
}

package in.jaxer.core.constants;

/**
 * @author Shakir
 */
public class RegexConstants
{
	public static final String AADHAR_CARD_NUMBER = "[0-9]{12}";

	public static final String HASHTAG = "((#|@){1}+[a-zA-Z0-9_]{1,})";

	@Deprecated
	public static final String ALPHA = "^[a-zA-Z]*$";
	public static final String CHARACTERS_ONLY = "^[a-zA-Z]*$";

	@Deprecated
	public static final String NUMERIC = "^[0-9]*$";
	public static final String NUMBER_ONLY = "^[0-9]*$";

	public static final String ALPHA_NUMERIC = "^[a-zA-Z0-9]*$";

	public static final String PANCARD_NUMBER = "[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}";

}

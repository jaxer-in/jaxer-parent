package in.jaxer.core.utilities;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * @author Shakir
 * date 2022-05-22 23:48
 */
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClipboardHandlerTest
{
	private ClipboardHandler clipboardHandler;

	private String backMsg = null;

	public ClipboardHandlerTest()
	{
	}

	@BeforeAll
	public void setUpClass()
	{
		clipboardHandler = new ClipboardHandler();
	}

	@BeforeEach
	public void setUp()
	{
		backMsg = clipboardHandler.paste();
	}

	@AfterEach
	public void tearDown()
	{
		clipboardHandler.copy(backMsg);
	}

	@Test
	void onCopyPasteEmptyString()
	{
		String source = "";
		clipboardHandler.copy(source);
		String paste = clipboardHandler.paste();
		MatcherAssert.assertThat(source, CoreMatchers.equalTo(paste));
	}

	@Test
	void onCopyPasteNullValue()
	{
		String source = null;
		clipboardHandler.copy(source);
		String paste = clipboardHandler.paste();
		MatcherAssert.assertThat(source, CoreMatchers.equalTo(paste));
	}

	@Test
	void onCopyPasteSomeLoremIpsum()
	{
		String source = Strings.getUUID();
		clipboardHandler.copy(source);
		String paste = clipboardHandler.paste();
		MatcherAssert.assertThat(source, CoreMatchers.equalTo(paste));
	}
}
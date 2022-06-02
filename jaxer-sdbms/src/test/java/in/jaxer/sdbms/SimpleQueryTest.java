package in.jaxer.sdbms;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class SimpleQueryTest
{
	Connection connection = Mockito.mock(Connection.class);

	@Test
	@Disabled
	void simpleQueryTest01()
	{
		SimpleQuery simpleQuery = new SimpleQuery.SimpleQueryBuilder()
				.withConnection(connection)
				.withSqlQuery("select * from users limit 1")
				.build();

		Mockito.when(simpleQuery.getResultList(User.class)).thenReturn(
				Collections.singletonList(new User("John Doe", "john.doe@rmail.com", "54321"))
		);

		List<User> userList = simpleQuery.getResultList(User.class);
		for(User u: userList)
		{
			System.out.println(u);
		}
	}

}
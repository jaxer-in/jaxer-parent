package in.jaxer.api.listners;

import in.jaxer.api.dtos.RequestResponseDto;

import java.sql.Connection;

/**
 * @author Shakir
 */
public interface Authentication
{
	void doAuthentication(Connection connection, RequestResponseDto requestResponseDto) throws Exception;
}

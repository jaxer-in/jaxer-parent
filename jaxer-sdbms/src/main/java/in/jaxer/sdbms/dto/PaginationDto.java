package in.jaxer.sdbms.dto;

import lombok.ToString;

/**
 * @author Shakir
 * date 2021-10-29 15:14
 */
@ToString
public class PaginationDto
{
	/**
	 * Current page index
	 * Start from 0
	 */
	public int pageIndex = 0;

	/**
	 * Records per page
	 * default is 10
	 */
	public int pageSize = 10;

	/**
	 * Server will fetch total records
	 * then calculate with page size
	 */
	public long totalPages;

	/**
	 * Server will fetch total records
	 */
	public long totalRecords;

	/**
	 * sorting column in db
	 */
	public String sortBy;

	/**
	 * sorting order either ASC / DESC
	 * default is ASC
	 */
	public String orderBy = "ASC";

}

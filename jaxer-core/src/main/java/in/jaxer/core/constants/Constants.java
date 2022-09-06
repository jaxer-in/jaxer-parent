package in.jaxer.core.constants;

/**
 * @author Shakir
 */
public class Constants
{
	/**
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.FileUtils#BUFFER_SIZE}
	 */
	@Deprecated
	public static final int BUFFER_SIZE = 1024;

	/**
	 * @deprecated on v1.1.0-beta, please use {@link in.jaxer.core.FileUtils#ONE_BYTE}
	 */
	@Deprecated
	public static final int ONE_BYTE = 1024;

	public static final long ONE_SECOND = 1000L;

	/**
	 * Standard Hashing names
	 */
	public static final String MD5 = "MD5";
	public static final String SHA_1 = "SHA-1";
	public static final String SHA_256 = "SHA-256";
	public static final String SHA_512 = "SHA-512";
	public static final String HMAC_SHA1 = "HmacSHA1";
	public static final String HMAC_SHA256 = "HmacSHA256";
	public static final String HMAC_SHA512 = "HmacSHA512";

	/**
	 * Standard encryptions
	 */
	public static final String AES = "AES";
	public static final String DES = "DES";

	/**
	 * Time in mili seconds
	 */
	public static final long SECOND = ONE_SECOND;
	public static final long MINUTE = SECOND * 60L;
	public static final long HOUR = MINUTE * 60L;
	public static final long DAY = HOUR * 24L;
	public static final long WEEK = DAY * 7L;
	public static final long MONTH = DAY * 30L;
	public static final long YEAR = DAY * 365L;

	/**
	 * Standard month names (eng)
	 */
	public static final String january = "january";
	public static final String february = "february";
	public static final String march = "march";
	public static final String april = "april";
	public static final String may = "may";
	public static final String june = "june";
	public static final String july = "july";
	public static final String august = "august";
	public static final String september = "september";
	public static final String october = "october";
	public static final String november = "november";
	public static final String december = "december";

	/**
	 * Standard day names (eng)
	 */
	public static final String monday = "monday";
	public static final String tuesday = "tuesday";
	public static final String wednesday = "wednesday";
	public static final String thursday = "thursday";
	public static final String friday = "friday";
	public static final String saturday = "saturday";
	public static final String sunday = "sunday";

	/**
	 * Date/Time formats
	 */
	public static final String SimpleDate = "yyyy-MM-dd";
	public static final String SimpleTime = "hh:mm:ss";
	public static final String PrettyDate = "EEE, dd-MMM-yyyy";
	public static final String MySQLDateTime = "yyyy-MM-dd HH:mm:ss";
	public static final String MySQLDate = "yyyy-MM-dd";

	/**
	 * Standards file extensions
	 */
	public static final String MP4 = "mp4";
	public static final String JPG = "jpg";
	public static final String PNG = "png";
	public static final String JPEG = "jpeg";
	public static final String MP3 = "mp3";
}

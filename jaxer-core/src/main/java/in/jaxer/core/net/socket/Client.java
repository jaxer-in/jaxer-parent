package in.jaxer.core.net.socket;

import in.jaxer.core.utilities.JUtilities;
import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class Client implements AutoCloseable
{
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private Socket socket;
	private String port;
	private String host;

	public DataInputStream getDataInputStream()
	{
		return dataInputStream;
	}

	public void setDataInputStream(DataInputStream dataInputStream)
	{
		this.dataInputStream = dataInputStream;
	}

	public DataOutputStream getDataOutputStream()
	{
		return dataOutputStream;
	}

	public void setDataOutputStream(DataOutputStream dataOutputStream)
	{
		this.dataOutputStream = dataOutputStream;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	private void close(Closeable closeable)
	{
		JUtilities.close(closeable);
	}

	public void start() throws IOException
	{
		log.info("CLIENT starting at port: {}", this::getPort);

		this.socket = new Socket(this.host, Integer.parseInt(port));
		this.dataInputStream = new DataInputStream(this.socket.getInputStream());
		this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());

		log.info("CLIENT started at port: {}", this::getPort);
	}

	public void stop()
	{
		log.info("CLIENT stopping at port: {}", this::getPort);

		this.close(this.dataInputStream);
		this.close(this.dataOutputStream);
		this.close(this.socket);

		log.info("CLIENT stopped at port: {}", this::getPort);
	}

	public void sendMessage(String msg) throws IOException
	{
		log.debug("Sending msg: {}", msg);

		this.dataOutputStream.writeUTF(msg);
		this.dataOutputStream.flush();
	}

	public String readMessage() throws IOException
	{
		return this.dataInputStream.readUTF();
	}

	@Override
	public void close() throws Exception
	{
		stop();
	}
}

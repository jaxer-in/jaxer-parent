package in.jaxer.core.net.socket;

import in.jaxer.core.utilities.JUtilities;
import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class Server implements AutoCloseable
{
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private ServerSocket serverSocket;
	private Socket socket;
	private String port;

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

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

	private void close(Closeable closeable)
	{
		JUtilities.close(closeable);
	}

	public void start() throws IOException
	{
		log.info("SERVER starting at port: {}", this::getPort);
		this.serverSocket = new ServerSocket(Integer.parseInt(port));

		log.info("SERVER waiting for clients at port: {}", this::getPort);
		this.socket = this.serverSocket.accept();
		log.info("client got connected at port: {}", this::getPort);

		this.dataInputStream = new DataInputStream(this.socket.getInputStream());
		this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());

		log.info("SERVER started at port: {}", this::getPort);
	}

	public void stop()
	{
		log.info("SERVER stopping at port: {}", this::getPort);

		this.close(this.dataInputStream);
		this.close(this.dataOutputStream);
		this.close(this.serverSocket);
		this.close(this.socket);

		log.info("SERVER stopped at port: {}", this::getPort);
	}

	public void sendMessage(String msg) throws IOException
	{
		log.info("Sending msg: {}", msg);
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

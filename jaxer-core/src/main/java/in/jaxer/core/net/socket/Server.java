
package in.jaxer.core.net.socket;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Shakir Ansari
 */
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
		try
		{
			if (closeable != null)
			{
				closeable.close();
			}
		} catch (Exception e)
		{
		}
	}

	public void start() throws IOException
	{
		System.out.println("SERVER starting at port: " + this.port);
		this.serverSocket = new ServerSocket(Integer.parseInt(port));

		System.out.println("SERVER waiting for clients at port: " + this.port);
		this.socket = this.serverSocket.accept();
		System.out.println("SERVER client connected at port: " + this.port);

		this.dataInputStream = new DataInputStream(this.socket.getInputStream());
		this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());

		System.out.println("SERVER started at port: " + this.port);
	}

	public void stop()
	{
		System.out.println("SERVER stopping at port: " + this.port);
		this.close(this.dataInputStream);
		this.close(this.dataOutputStream);
		this.close(this.serverSocket);
		this.close(this.socket);
		System.out.println("SERVER stopped at port: " + this.port);
	}

	public void sendMessage(String msg) throws IOException
	{
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

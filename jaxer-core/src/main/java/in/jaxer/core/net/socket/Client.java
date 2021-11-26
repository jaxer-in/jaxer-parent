
package in.jaxer.core.net.socket;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Shakir Ansari
 */
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
		System.out.println("CLIENT starting at port: " + this.port);

		this.socket = new Socket(this.host, Integer.parseInt(port));
		this.dataInputStream = new DataInputStream(this.socket.getInputStream());
		this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());

		System.out.println("CLIENT started at port: " + this.port);
	}

	public void stop()
	{
		System.out.println("CLIENT stopping at port: " + this.port);
		this.close(this.dataInputStream);
		this.close(this.dataOutputStream);
		this.close(this.socket);
		System.out.println("CLIENT stopped at port: " + this.port);
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

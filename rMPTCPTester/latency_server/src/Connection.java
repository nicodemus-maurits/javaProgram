import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread  {
	int port = 11112;
	ServerSocket serverSocket;
	Socket socket;
	Measurement measurement;
	int reconnect_counter;
	int reconnect_intervall = 3;
	
	// Open TCP Connection
	Connection(int port)
	{
		this.port = port;
	 	try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void incomming()
	{
		try {
			socket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void run()
	{
		measurement = new Measurement();
		String msg = new String();
		
		System.out.println("Waiting for incomming Connection");
		
		// Wait for incomming Connection
		incomming();
		System.out.println("Connection established");
		
		//get Messages
		while(true)
		{
			try {
				msg = readMessage();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// add to measurements
			measurement.addMeasurement(msg);
			
			
		}
	}
	
	private String readMessage() throws IOException
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 	char[] buffer = new char[2000];
	 	int sizeMsg = bufferedReader.read(buffer, 0, 2000); 
	 	String msg = new String(buffer, 0, sizeMsg);
	 	return msg;
	}
}

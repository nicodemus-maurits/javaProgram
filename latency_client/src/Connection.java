import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Connection extends Thread {
	Socket socket;
	String ip = "127.0.0.1";
	int port = 11112;
	long deltaT = 10;
	int reconnect_counter = 0;
	SocketAddress sockaddr;
	int reconnect_intervall = 3;
	
	public Connection() {
		
	}
	
	private void connect()
	{
		while (true)
		{
			try {
				socket = new Socket();
				socket.connect(sockaddr);
				socket.setKeepAlive(true);
				break;
			} catch (Exception e) {
				try {
					System.out.println("Connection lost. Try to reconnect in 5s...");
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	private PrintWriter getWriter()
	{
		PrintWriter printWriter = null;
		
		try {
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return printWriter;
	}
	
	private void getInput()
	{
		String input = "";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// Get Server Data from user
		System.out.println("Enter Server IP  : ");
		try {
			input = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ip = input;
		
		System.out.println("Enter Server Port: ");
		try {
			input = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(deltaT);
		port = Integer.parseInt(input);
		
		sockaddr = new InetSocketAddress(ip, port);
	}

	public void run()
	{
		long oldTime;
		long currentTime;
		Measurement measurement = new Measurement();
		
		getInput();
		
		connect();
		
		oldTime = System.currentTimeMillis();
		
		PrintWriter printWriter = null;
		
		printWriter = getWriter();
		
		
		while(true)
		{
			currentTime = System.currentTimeMillis();
			
			//send data with a period of deltaT
			if (currentTime - oldTime > deltaT)
			{
				String msg = measurement.createMeasurement();
				
				System.out.println("Sending Measurement: " + msg);
				printWriter.print(msg);
				printWriter.flush();
				oldTime = currentTime;
				
				
				if(socket.isClosed())
				{
					connect();
					printWriter = getWriter();
				}
				else
				{
					
				}
				
				/*				
				if (reconnect_counter >= reconnect_intervall )
				{
					reconnect_counter = 0;
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					connect();
				}
				else
				{
					reconnect_counter++;
				}
				*/
			}
			else
			{
				
			}
			
		}

	}


}

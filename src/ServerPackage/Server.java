package ServerPackage;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Server {
	ServerSocket Ssocket;
	LinkedList<InetAddress> addresses;
	int port = 10101;
	
	InputStream sin;
	OutputStream sout;
	DataInputStream in;
	DataOutputStream out;
	
	void waitForConnections(Socket socket) throws IOException
	{
		socket = Ssocket.accept();
		
		sin = socket.getInputStream();
		sout = socket.getOutputStream();
		
		in = new DataInputStream(sin);
		out = new DataOutputStream(sout);
	}
	Server()
	{
		try{
			Ssocket = new ServerSocket(port);
			Socket socket = Ssocket.accept();
			
			sin = socket.getInputStream();
			sout = socket.getOutputStream();
			
			in = new DataInputStream(sin);
			out = new DataOutputStream(sout);
			
			
			addresses = new LinkedList<InetAddress>();
			Integer n;
			while ((n = in.readInt()) != null)
			{
				switch (n)
				{
				case 0:
					InetAddress newAddress = InetAddress.getByName(in.readUTF());
					addresses.add(newAddress);
					System.out.println("Server got" + newAddress.toString());
					break;
				case 1:
					if (addresses.isEmpty()) 
					{
						out.writeUTF("Error");
						System.out.println("Error");
					}
					else 
					{
						out.writeUTF(addresses.getFirst().getHostAddress());
						System.out.println("Server send " + addresses.getFirst().getHostAddress());
						addresses.removeFirst();
					}
					break;
				case 2:
					waitForConnections(socket);
				
				}
			}
		} catch(Exception e) {System.out.println("Exception " + e);}
	}
}

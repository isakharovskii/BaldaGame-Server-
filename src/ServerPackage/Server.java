package ServerPackage;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Server {
	ServerSocket Ssocket;
	LinkedList<InetAddress> forFiveAddresses;
	LinkedList<InetAddress> forSixAddresses;
	LinkedList<InetAddress> forSevenAddresses;
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
			
			
			forFiveAddresses = new LinkedList<InetAddress>();
			forSixAddresses = new LinkedList<InetAddress>();
			forSevenAddresses = new LinkedList<InetAddress>();
			Integer n;
			while ((n = in.readInt()) != null)
			{
				switch (n)
				{
				case 0:
					Integer i = in.readInt();
					InetAddress newAddress = InetAddress.getByName(in.readUTF());
					switch (i)
					{
					case 5:
						forFiveAddresses.add(newAddress);
						break;
					case 6:
						forSixAddresses.add(newAddress);
						break;
					case 7:
						forSevenAddresses.add(newAddress);
						break;
					}
					
					System.out.println("Server got" + newAddress.toString());
					break;
				case 1:
					Integer i2 = in.readInt();
					switch (i2)
					{
						case 5:
							if (forFiveAddresses.isEmpty()) 
							{
								out.writeUTF("Error");
								System.out.println("Error");
							}
							else 
							{
								out.writeUTF(forFiveAddresses.getFirst().getHostAddress());
								System.out.println("Server send " + forFiveAddresses.getFirst().getHostAddress());
								forFiveAddresses.removeFirst();
							}
							break;
						case 6:
							if (forSixAddresses.isEmpty()) 
							{
								out.writeUTF("Error");
								System.out.println("Error");
							}
							else 
							{
								out.writeUTF(forSixAddresses.getFirst().getHostAddress());
								System.out.println("Server send " + forSixAddresses.getFirst().getHostAddress());
								forSixAddresses.removeFirst();
							}
							break;
						case 7:
							if (forSevenAddresses.isEmpty()) 
							{
								out.writeUTF("Error");
								System.out.println("Error");
							}
							else 
							{
								out.writeUTF(forSevenAddresses.getFirst().getHostAddress());
								System.out.println("Server send " + forSevenAddresses.getFirst().getHostAddress());
								forSevenAddresses.removeFirst();
							}
							break;
					}
					
					break;
				case 2:
					waitForConnections(socket);
				
				}
			}
		} catch(Exception e) {System.out.println("Exception " + e);}
	}
}

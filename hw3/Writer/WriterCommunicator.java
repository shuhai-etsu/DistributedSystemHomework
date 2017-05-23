/*---------------------------------------------
 *This handles the server socket establishment
 *File name: WriterCommunicator.java
 *Author: Shuhai Li
 *Date: November 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: 
------------------------------------------------*/

import java.net.ServerSocket;
import java.net.InetAddress;
class WriterCommunicator
{
	ServerSocket serverSocket=null;

	public WriterCommunicator(int port, int portMax)
	{
		do 
		{
			try 
			{
				serverSocket = new ServerSocket(port); //instantiate the ServerSocket object
				if (serverSocket.isBound() == true)
				{
					System.out.println("Writer service started on Server "
						                +InetAddress.getLocalHost().getHostName()
						                +" at port "+ port);
				}
			}
			catch (Exception e)
			{
				System.out.println("IO Error in binding the port");
			}

			port++;
			if (port >= portMax)
			{
				System.out.println("no port available currently");
				System.exit(1);
			}

		} while (serverSocket.isBound() != true);
	}

	public ServerSocket getServerSocket()
	{
		return serverSocket;
	}

} //end of class WriterCommuncator
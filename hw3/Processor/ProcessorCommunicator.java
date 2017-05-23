/*---------------------------------------------
 *This class handles the server socket establishment

 *File name: ProcessorCommunicator.java
 *Author: Shuhai Li
 *Date: Novermber 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: 
------------------------------------------------*/
import java.net.ServerSocket;
import java.net.InetAddress;
class ProcessorCommunicator
{
	ServerSocket serverSocket = null;   //declare a ServerSocket object

	/*----------------------Constructor--------------*/
	public ProcessorCommunicator(int port, int portMax)
	{
		do 
		{
			try 
			{
				serverSocket = new ServerSocket(port); //instantiate the ServerSocket object
				if (serverSocket.isBound() == true)
				{
					System.out.println("Processor service started on Server "
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
				System.exit(1);
				System.out.println("no port available currently");
			}

		} while (serverSocket.isBound() != true);

	}

	public ServerSocket getServerSocket()
	{
		return serverSocket;
	}

}
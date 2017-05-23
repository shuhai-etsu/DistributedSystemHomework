/*---------------------------------------------
 *This class handles the socket communication for Reader
 *File name: ReaderCommunication.java
 *Author: Shuhai Li
 *Date: November 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note:
------------------------------------------------*/
import java.net.Socket;
class ReaderCommunicator
{
	Socket socket;
	public ReaderCommunicator(String host, int port)
	{
		socket = null;
		try 
		{
			socket = new Socket(host,port);    //instantiate a socket object with given host and port number
			System.out.println("connected to " + host +" at port " + port);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Socket getSocket()
	{
		return socket;
	}

}
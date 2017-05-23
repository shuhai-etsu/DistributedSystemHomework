/*---------------------------------------------
 *This class handles command line arguments 

 *File name: ProcessorArgument.java
 *Author: Shuhai Li
 *Date: Novermber 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: 
------------------------------------------------*/
class ProcessorArgument
{
	public static String remoteHostName = "127.0.1.1"; //Writer server host name
	public static int remotePort = 7777;           //Writer server port number
	public static int port = 6666;                 //local port number to start processor 
	public final int portMax = 65535;             //maximum port number to search

	public ProcessorArgument(String[] args)
	{
		if (args.length == 0)
		{
			remoteHostName = "127.0.1.1";
			remotePort = 7777;
			port = 6666;
		}

		if (args.length > 0)
		{
			remoteHostName = args[0];    //first command line argument - output file name
		}

		if (args.length > 1)
		{
			remotePort = Integer.parseInt(args[1]); //second command line arugment - port number 
		}

		if (args.length > 2)
		{
			port = Integer.parseInt(args[2]);       //second command line arugment - port number 
		}
	}
}
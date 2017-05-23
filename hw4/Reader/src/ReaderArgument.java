/*---------------------------------------------
 *This class handles the command line arguments
 *File name: ReaderArgument.java
 *Author: Shuhai Li
 *Date: November 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: 
------------------------------------------------*/
public class ReaderArgument
{
	public static String inputFileName;
	public static String host;
	public static int port;

	public ReaderArgument(String[] args)
	{
		if (args.length ==0)
		{
			inputFileName = "student.txt";
			host = "127.0.0.1";
			port = 6000;
		}

		if (args.length > 0)
		{
			inputFileName = args[0];   //first command line argument-input file name
		}
		
		if (args.length > 1)
		{
			host = args[1];            //second command line argument-remote host name
		}

		if (args.length > 2)
		{
			port = Integer.parseInt(args[2]); //third command line argument-remote port number
		}
	}

}
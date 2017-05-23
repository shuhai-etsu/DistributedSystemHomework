/*---------------------------------------------
 *This handles the command line arguments
 *File name: WriterArgument.java
 *Author: Shuhai Li
 *Date: November 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: 
------------------------------------------------*/
class WriterArgument
{
	public String outputFileName;
	public int port;
	public final int portMax=65535;

	public WriterArgument(String[] args)
	{

		if (args.length ==0)
		{
			outputFileName = "output.txt";
			port = 7777;
		}
		if (args.length > 0)
		{
			outputFileName = args[0];      //first command line argument - output file name
		}
		if (args.length > 1)
		{
			port = Integer.parseInt(args[1]); //second command line arugment - port number 
		}
	}
}
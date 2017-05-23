/*---------------------------------------------
 *This handles the command line arguments
 *File name: WriterArgument.java
 *Author: Shuhai Li
 *Date: December 01,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note:
 * 2016-12-01   Removed parameter inputFileName
------------------------------------------------*/
class WriterArgument
{
	//public String outputFileName;
	public int port;
	public final int portMax=65535;

	public WriterArgument(String[] args)
	{
		if (args.length ==0)
		{
			port = 7000;
		}
		if (args.length > 0)
		{
			port = Integer.parseInt(args[1]); //second command line argument - port number
		}
	}
}
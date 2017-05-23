/*---------------------------------------------
 *Writer class is responsible getting records from
 Processor and writing records to a text file
 *File name: Writer.java
 *Author: Shuhai Li
 *Date: November 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: 
 2016-11-10     Command line argument and communication part were singled out
                to separate classes
------------------------------------------------*/
import java.net.Socket;
import java.util.concurrent.*;

public class Writer
{	
	public static void main(String[] args)
	{
		WriterArgument wa = new WriterArgument(args);
		WriterCommunicator wc = new WriterCommunicator(wa.port,wa.portMax);

	/*------Create and start threads for processing and writing records------*/
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		Socket socket = null;
		while (true)
		{
			try 
			{
				socket = wc.getServerSocket().accept();          //create server side socket
				System.out.println("server socket created successful");
				Runnable a = new WriteRecord(socket,wa.outputFileName); //instantiate a WriteRecord object
				executorService.execute(a);                          //put the runnalbe in thread pool
			}
			catch (Exception e)
			{
				System.out.println("something wrong with Server socket");
			}

		}
		
	} //end main()
	
} //end class Writer
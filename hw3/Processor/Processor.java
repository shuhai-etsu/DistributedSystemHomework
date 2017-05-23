/*---------------------------------------------
 *This class is responsible getting records from Reader class,
  calculate age for the records and send the records to Writer class

 *File name: Processor.java
 *Author: Shuhai Li
 *Date: Novermber 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: 
 2016-11-10 Command line arugments and communication
                 were singled out into separate classes;
 2016-11-10  Thread pool is created to handle tasks rather
                 than threads being created on fly
------------------------------------------------*/
import java.net.Socket;
import java.util.concurrent.*;

public class Processor
{	
	public static void main(String[] args)
	{
		
		LinkedBlockingQueue<StudentRecord> queue = new LinkedBlockingQueue<StudentRecord>();

		Socket socket1 = null;
		Socket socket2 = null;

		ProcessorArgument pa = new ProcessorArgument(args);
		ProcessorCommunicator pc = new ProcessorCommunicator(pa.port,pa.portMax);

	/*----------------------Create thread pool-------------------------------*/	
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		while (true)
		{
			/*------Create and execute a thread for sending records to Writer-------*/
			try 
			{
				socket1 = new Socket(pa.remoteHostName,pa.remotePort);    //instantiate a socket object with given host and port number
				System.out.println("connected to " + pa.remoteHostName 
									+ " at port " + pa.remotePort);
				Runnable a = new SendRecord(socket1,queue);               //instantiate a WriteRecord 
				executorService.execute(a);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}	

			/*-------Create and execute a thread for processing records ----*/
			try 
			{
				socket2 = pc.getServerSocket().accept();          //if accept() successful, server side socket is created
				Runnable b = new ProcessRecord(socket2,queue);       //instantiate a ProcessRecord object
				executorService.execute(b);
			}
			catch (Exception e)
			{
				System.out.println("something wrong with socket creation in processor");
			}

		}//end while(true)
		
	} //end main()
	
} //end class Processor
/*---------------------------------------------
 *This is the Reader part of the app, it is responsible
 for reading records from a text file
 *File name: Reader.java
 *Author: Shuhai Li
 *Date: November 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: single out communication and command line argument into
                 separate classes
------------------------------------------------*/

public class Reader
{
	public static void main(String[] args)
	{
        ReaderArgument ra = new ReaderArgument(args);
        ReaderCommunicator rc = new ReaderCommunicator(ra.host,ra.port);
		Runnable a = new ReadRecord(rc.getSocket(), ra.inputFileName);  //instantiate a runnable with the socket and input file

		Thread thread = new Thread(a); //thread that reads student records
		thread.start();               //start the threads

		try 
		{
			thread.join();              //join thread once finished
			System.out.println("ReaderRecord thread joined");
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Exception occurred in ReaderRecord thread!");
			e.printStackTrace();
		}
		
		try
		{
			rc.getSocket().close();             //close the socket
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
					
	} //end main()
	
} //end class Reader
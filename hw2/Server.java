/*---------------------------------------------
 *This is the server part of client-server program 
 *File name: Server.java
 *Author: Shuhai Li
 *Date: October 05,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note:
------------------------------------------------*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{	
	public static void main(String[] args)
	{
		
	/*---------------variable declaration-----------------------*/ 
		LinkedBlockingQueue<StudentRecord> queue1 = new LinkedBlockingQueue<StudentRecord>();
		String outputFileName = "output.txt";
		int port = 7000;
		int portMax = 65535;

	/*-------------------command line arguments-------------------*/
		if (args.length == 1)
		{
			outputFileName = args[0];    //first command line argument - output file name
		}
		if (args.length == 2)
		{
			outputFileName = args[0];
			port = Integer.parseInt(args[1]); //second command line arugment - port number 
		}
		
	/*------------------Server socket creation-------------------*/
		ServerSocket serverSocket = null;   //declare a ServerSocket object
		do 
		{
			try 
			{
				serverSocket = new ServerSocket(port); //instantiate the ServerSocket object
				if (serverSocket.isBound() == true)
				{
					System.out.println("Successful bound to port "+ port);
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

	/*------Create and start threads for processing and writing records------*/
		Thread thread1 = null;
		Thread thread2 = null;
		while (true)
		{
			try 
			{
				Socket socket = serverSocket.accept();          //if accept() successful, server side socket is created
				System.out.println("server socket created successful");
				Runnable a = new ProcessRecord(socket,queue1);       //instantiate a ProcessRecord object
				Runnable b = new WriteRecord(queue1,outputFileName); //instantiate a WriteRecord object

				thread1 = new Thread(a);            //thread 1 that processes records
				thread2 = new Thread(b);           //thread 2 that writes records to a file

				thread1.start();               //start the threads
				thread2.start();
			}
			catch (Exception e)
			{
				System.out.println("something wrong with Server socket");
			}

			try 
			{
				thread1.join();              //join thread 1
				System.out.println("process record thread joined");
			} 
			catch (InterruptedException e) 
			{
				System.out.println("Exception occurs in thread 1!");
				e.printStackTrace();
			}
			
			try 
			{
				thread2.join();            //join thread 2
				System.out.println("write record thread joined");
			} 
			catch (InterruptedException e) 
			{
				System.out.println("Exception occurs in thread 2!");
				e.printStackTrace();
			}

		}//end while(true)
		
	} //end main()
	
} //end class Client
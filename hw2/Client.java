/*---------------------------------------------
 *This is the client part of client-server program
 *File name: Client.java
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
import java.net.Socket;

public class Client
{
	public static void main(String[] args)
	{
		String inputFileName = "student.txt";
		String host = "127.0.0.1";
		int port = 7000;
		Socket socket = null;

		if (args.length == 1)
		{
			inputFileName = args[0];   //first command line argument-input file name
		}
		if (args.length == 2)
		{
			inputFileName = args[0];  
			host = args[1];            //second command line argument-host name
		}

		if (args.length == 3)
		{
			inputFileName = args[0]; 
			host = args[1];
			port = Integer.parseInt(args[2]); //third command line argument-port number
		}

		try 
		{
			socket = new Socket(host,port);    //instantiate a socket object with given host and port number
			System.out.println("client socket created");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		Runnable a = new ReadRecord(socket, inputFileName);  //instantiate a runnable with the socket and input file

		Thread thread1 = new Thread(a); //thread 1 that reads student records
		thread1.start();               //start the threads

		try 
		{
			thread1.join();              //join thread 1 if it is finished
			System.out.println("Read record thread 1 joined");
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Exception occurs in thread 1!");
			e.printStackTrace();
		}
		
		try
		{
			socket.close();             //close the socket
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
					
	} //end main()
	
} //end class hw1_v5

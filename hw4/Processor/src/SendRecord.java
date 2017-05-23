/*---------------------------------------------
 *This class sends records to Writer class via socket communication
 *File name: SendRecord.java
 *Author: Shuhai Li
 *Date: November 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note:
------------------------------------------------*/

import java.util.concurrent.LinkedBlockingQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class SendRecord implements Runnable   
{
	LinkedBlockingQueue<StudentRecord> queue;
	Socket socket;
	
	/*------------------Constructor-------------------*/
	public SendRecord(Socket socket, LinkedBlockingQueue<StudentRecord> queue)
	{
		this.socket = socket;
		this.queue = queue;
	}
	/*----------------Implementation of run() from Runnable---------*/
	@Override
	public void run()
	{
		StudentRecord sr=null;
		ObjectOutputStream outToServer = null;         //declare an ObjectOutputStream object
		try
		{
			outToServer = new ObjectOutputStream(socket.getOutputStream());  //instantiate the ObjectOutputStream object
			//outToServer.flush();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		while(true)
		{
			try
			{
				sr = queue.take();                         //retrieve the head record if available
				outToServer.writeObject(sr);
				System.out.println("SendingRecord of Processor\n");
				System.out.println(sr);
				if (sr.getId().compareTo("0") == 0) break;  //if run into the record with id=-1, break the while loop					
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
	  	}
		System.out.println("Sending records to Writer finished");

	} //end run()
} //end Class ReadRecord
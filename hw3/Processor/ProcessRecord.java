/*---------------------------------------------
 *This class calculates age for student records

 *File name: ProcessRecord.java
 *Author: Shuhai Li
 *Date: Novermber 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: 
 2016-11-10--- gson format was used to transmit records on socket communication
------------------------------------------------*/

import java.util.concurrent.LinkedBlockingQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import com.google.gson.Gson;

class ProcessRecord implements Runnable
{
	LinkedBlockingQueue<StudentRecord> queue;
	Socket socket;

/*------------------Class constructor--------------------*/	
	public ProcessRecord(Socket socket,LinkedBlockingQueue<StudentRecord> queue)
	{
		this.socket = socket;
		this.queue = queue;
	}

/*-----------------Implementation of run() from Runnable class-------*/	
	@Override
	public void run()
	{
		StudentRecord sr = null;
		LocalDate dateBirth = null;
		LocalDate currentDate = LocalDate.now();
		String dob = null;                        //date of birth
		int age = 0;
		
		BufferedReader toServer = null;
		Gson gson = new Gson();
		String json;

		try 
		{
			toServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		while (true)
		{
			try 
			{
				try
				{
					json = toServer.readLine();
					if (json==null || json.length()==0)
						continue;
					System.out.println(json);
					sr = gson.fromJson(json, StudentRecord.class);
					System.out.println(sr);
				}
				catch (Exception e)  
				{
					System.out.println("Error in reading gson from socket");
				}

				dob = sr.getDateOfBirth(); 
				dateBirth = LocalDate.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE); //parse  the date of birth in the record to LocalDate object
				age = currentDate.getYear()-dateBirth.getYear();                    //use getYear() to calculate age
				sr.setAge(age);                                                     //set age for the record                               
				queue.offer(sr);	
				                                                //put the record on queue 2
				if (sr.getId().compareTo("0") == 0) 
					break;                             //if run into the record with id=-1, break the while loop and terminate the thread
			}
			catch (Exception e)
			{
				System.out.println("waiting on queue is interupted");
			}
		} 		
		System.out.println("Process record thread finished");
	} //end run()
}	//end class ProcessRecord

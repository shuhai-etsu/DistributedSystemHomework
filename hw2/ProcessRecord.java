/*---------------------------------------------
 *This class calculates age for student record 
 *File name: ProcessRecord.java
 *Author: Shuhai Li
 *Date: October 05,2016
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
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

class ProcessRecord implements Runnable
{
	LinkedBlockingQueue<StudentRecord> queue1;
	Socket socket;

/*------------------Class constructor--------------------*/	
	public ProcessRecord(Socket socket,LinkedBlockingQueue<StudentRecord> queue1)
	{
		this.socket = socket;
		this.queue1 = queue1;
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
		ObjectInputStream toServer = null;
		Object ob = null;
		try 
		{
			toServer = new ObjectInputStream(socket.getInputStream());    //initiate the ObjectInputStream object
			System.out.println("Object input stream created successfully");
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
					ob = toServer.readObject();              //read an object from ObjectInputStream
					sr = (StudentRecord)ob;                  //cast the object to StudentRecord
					System.out.println("Process Record Thread printing");	
					System.out.println(sr);
				}
				catch (Exception e)                                              //retrieve the head record from queue 1
				{
					e.printStackTrace();
					System.out.println("Error in readOjbect");
				}

				dob = sr.getDateOfBirth(); 
				dateBirth = LocalDate.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE); //parse  the date of birth in the record to LocalDate object
				age = currentDate.getYear()-dateBirth.getYear();                    //use getYear() to calculate age
				sr.setAge(age);                                                   //set age for the record
				queue1.offer(sr);	                                                //put the record on queue 2
				if (sr.getId().compareTo("0") == 0) break;         //if run into the record with id=-1, break the while loop and terminate the thread
			}
			catch (Exception e)
			{
				System.out.println("waiting on queue 1 is interupted");
			}
		} 		
		System.out.println("Process record thread finished");
	} //end public run()
}	//end class ProcessRecord

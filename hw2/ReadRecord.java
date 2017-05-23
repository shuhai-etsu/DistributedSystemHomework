/*---------------------------------------------
 *This code read student records from a text file and send records to a socket
 *File name: ReadRecord.java
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
import java.net.Socket;
import java.io.ObjectOutputStream;

class ReadRecord implements Runnable   
{
	Socket socket;
	String inputFileName;
	
	/*------------------Constructor-------------------*/
	public ReadRecord(Socket socket, String inputFileName)
	{
		this.socket = socket;
		this.inputFileName = inputFileName;
	}
	/*----------------Implementation of run() from Runnable---------*/
	@Override
	public void run()
	{
		Scanner input = null;
		String lastName = null;
		String firstName = null;
		String id = null;
		String dob = null;
		StudentRecord sr = null;

		ObjectOutputStream outToServer = null;         //declare an ObjectOutputStream object
		try
		{
			outToServer = new ObjectOutputStream(socket.getOutputStream());  //instantiate the ObjectOutputStream object
			outToServer.flush();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			input = new Scanner(new File(inputFileName)); //initiate Scanner object with a File object (input file specified here)
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Cannot open the file");
			System.exit(1);
		}

		while (input.hasNext())
		{
			lastName = input.next();    //read last name 
			firstName = input.next();   //read first name
			id = input.next();          //read id
			dob = input.next();         //read date of birth (format yyyy-mm-dd, e.g. 1991-09-30)
			
			sr = new StudentRecord.StudentRecordBuilder(lastName, firstName).id(id).dateOfBirth(dob).build(); //create student record object
			System.out.println("Read record thread 1 printing"); 
			System.out.println(sr);
			
			try
			{
				outToServer.writeObject(sr);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		} 
		input.close();                  //close input file
		System.out.println("Read record thread 1 finished");

	}
} //end Class ReadRecord

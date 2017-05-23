/*---------------------------------------------
 *This code read student records from a text file 
    and send records to a socket
 *File name: ReadRecord.java
 *Author: Shuhai Li
 *Date: Novermber 10,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note: instead of using objectstream, Gson objects
                 are used to transmit records over sockets
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
import java.io.*;
import com.google.gson.Gson;

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

		DataOutputStream outToServer = null; //declare an DataOutputStream object
		Gson gson = new Gson();
		String json;

		try
		{
			outToServer = new DataOutputStream(socket.getOutputStream());
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
			System.out.println("Reading records from text file"); 

			json = gson.toJson(sr);
			System.out.println(json);
			try
			{
				outToServer.writeBytes(json+"\n");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		} 
		input.close();                  //close input file
		System.out.println("Reading record from text file finished");

	} //end run()
} //end Class ReadRecord

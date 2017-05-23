/*---------------------------------------------
 *This class writes student records to an external file 
 *File name: WriteRecord.java
 *Author: Shuhai Li
 *Date: Novermber 10,2016
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
import java.io.ObjectInputStream;
import java.io.*;
import java.net.Socket;

class WriteRecord implements Runnable
{
	Socket socket;                    
	String outputFileName;            //output file name

/*--------------Constructor----------------*/
	public WriteRecord(Socket socket, String outputFileName)
	{
		this.socket = socket;
		this.outputFileName = outputFileName;
	}

/*--------------Implementation of run() inherented from Runnable----------*/	
	@Override
	public void run()
	{
		StudentRecord sr=null;
		ObjectInputStream toServer = null;
		Object ob = null;
		FileWriter fw = null;
		BufferedWriter output = null;
		try 
		{
			toServer = new ObjectInputStream(socket.getInputStream());    //initiate the ObjectInputStream object
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			fw = new FileWriter(outputFileName);   //initiate FileWriter object with specified output file
			output = new BufferedWriter(fw);      //initiate BufferWriter object
			while(true)
			{
				try
				{
					ob = toServer.readObject();              //read an object from ObjectInputStream
					sr = (StudentRecord)ob;                  //cast the object to StudentRecord
				}
				catch (Exception e)                     
				{
					e.printStackTrace();
					System.out.println("Error in readOjbect");
				}
				
				System.out.println("Writing records to file");		
				output.write(sr.toString());	
				System.out.println(sr);	
				if (sr.getId().compareTo("0") == 0) 
					break;                                 //if run into the record with id=-1, break the while loop
		  	}
		  	output.close();                                //close the file
		  	System.out.println("Finished writing records to a file");
		}
		catch(Exception e)
		{
			System.out.println("Cannot write to the file");
		}
		
	} //end run()
}	//end class WriteRecord		
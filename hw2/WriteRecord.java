/*---------------------------------------------
 *This class writes student records to an external file 
 *File name: WriteRecord.java
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
import java.io.ObjectInputStream;
import java.io.*;

class WriteRecord implements Runnable
{
	LinkedBlockingQueue<StudentRecord> queue1;   //queue storing student records
	String outputFileName;                       //output file name

/*--------------Constructor----------------*/
	public WriteRecord(LinkedBlockingQueue<StudentRecord> queue1, String outputFileName)
	{
		this.queue1=queue1;
		this.outputFileName=outputFileName;
	}

/*--------------Implementation of run() inherented from Runnable----------*/	
	@Override
	public void run()
	{
		StudentRecord sr=null;
		try
		{
			FileWriter fw = new FileWriter(outputFileName);   //initiate FileWriter object with specified output file
			BufferedWriter output = new BufferedWriter(fw);   //initiate BufferWriter object
			while(true)
			{
				try
				{
					sr = queue1.take();                         //retrieve the head record if available
					output.write(sr.toString());	
					System.out.println("Write record thread printing");	
					System.out.println(sr);	
					if (sr.getId().compareTo("0") == 0) break;  //if run into the record with id=-1, break the while loop					
				}
				catch (InterruptedException e)
				{
					System.out.println("waiting for queue1 interrupted");
				}
		  	}
		  	output.close();                                //close the file
		}
		catch(Exception e)
		{
			System.out.println("Cannot write to the file");
		}
		
		System.out.println("Write record thread finished");
	}
}	//end class WriteRecord		
/*---------------------------------------------
 *This program demonstrates multithreading feature in JAVA 
 *File name: hw1_v5.java
 *Author: Shuhai Li
 *Date: September 14,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
------------------------------------------------*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class hw1_v5
{
	//initiate a queue containing student records sitting between thread1 and thread2
	static LinkedBlockingQueue<StudentRecord> queue1 = new LinkedBlockingQueue<StudentRecord>(); 
	static LinkedBlockingQueue<StudentRecord> queue2 = new LinkedBlockingQueue<StudentRecord>();//queue  between thread2 and thread3
	static String inputFileName="student.txt";
	static String outputFileName="output.txt";
	public static void main(String[] args)
	{
		if (args.length == 1)
		{
			inputFileName = args[0];
		}
		if (args.length == 2)
		{
			inputFileName = args[0];
			outputFileName = args[1];
		}
		Runnable a = new ReadRecord();  //initiate a runnable object with concrete class-ReadRecord
		Runnable b = new ProcessRecord(); 
		Runnable c = new WriteRecord();
		
		Thread thread1 = new Thread(a); //thread 1 that reads student records
		Thread thread2 = new Thread(b); //thread 2 that processes students records
		Thread thread3 = new Thread(c); //thread 3 that writes student records

		thread1.start();               //start the threads
		thread2.start();
		thread3.start();
		
		try 
		{
			thread1.join();  //join thread 1 if it is finished
			System.out.println("Thread 1 joined");
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Exception occurs in thread 1!");
			e.printStackTrace();
		}
		
		try 
		{
			thread2.join(); //join thread 2 if it is finished
			System.out.println("Thread 2 joined");
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Exception occurs in thread 2!");
			e.printStackTrace();
		}
	
		try 
		{
			thread3.join(); //join thread 3 if it is finished
			System.out.println("Thread 3 joined");
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Exception occurs in thread 3!");
			e.printStackTrace();
		}			
	} //end main()
	

/*------------Nested class for reading records------------------------*/
	static class ReadRecord implements Runnable   
	{

		
		@Override
		public void run()
		{
			Scanner input = null;
			String lastName = null;
			String firstName = null;
			String id = null;
			String dob = null;
			StudentRecord sr = null;
	
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
				System.out.println("Thread 1 printing"); 
				System.out.println(sr);
				queue1.offer(sr);            //put the student record on queue 1
			} 
			input.close();                  //close input file
			System.out.println("Thread 1 finished");
		}
	} //end Class ReadRecord


	/*--------Nested class for processing records---*/
	static class ProcessRecord implements Runnable
	{
		@Override
		public void run()
		{
			StudentRecord sr=null;
			LocalDate dateBirth=null;
			LocalDate currentDate = LocalDate.now();
			String dob=null;                                //date of birth
			int age=0;
			while (true)
			{
				if (queue1.isEmpty()) 
					continue;

				sr=queue1.poll();                                                  //retrieve the head record from queue 1
				dob=sr.getDateOfBirth(); 
				dateBirth=LocalDate.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE); //parse  the date of birth in the record to LocalDate object
				age=currentDate.getYear()-dateBirth.getYear();                    //use getYear() to calculate age
				sr.setAge(age);                                                   //set age for the record
				queue2.offer(sr);	                                                //put the record on queue 2
				System.out.println("Thread 2 printing");	
				System.out.println(sr);
				if (sr.getId().compareTo("-1") == 0) break;           //if run into the record with id=-1, break the while loop and terminate the thread

			} 
			
			System.out.println("Thread 2 finished");
		}
	}	//end class ProcessRecord
	

	/*--------Nested class for writing records---*/
	static class WriteRecord implements Runnable
	{
		@Override
		public void run()
		{
			StudentRecord sr=null;
			try
			{
				FileWriter fw = new FileWriter(outputFileName);   //initiate FileWriter object with specified output file
				BufferedWriter output = new BufferedWriter(fw); //initiate BufferWriter object
				while(true)
				{
					if (queue2.isEmpty()) continue;
	
					sr = queue2.poll();                         //if queue 2 is not empty, retrieve the head record
					output.write(sr.toString());	
					System.out.println("Thread 3 printing");	
					System.out.println(sr);	
					if (sr.getId().compareTo("-1") == 0) break; //if run into the record with id=-1, break the while loop and terminate the thread
			  	}
			  	output.close();                                //close the file
			}
			catch(Exception e)
			{
				System.out.println("Cannot write to the file");
			}
			
			System.out.println("Thread 3 finished");
		}
	}	//end class WriteRecord		
} //end class hw1_v5

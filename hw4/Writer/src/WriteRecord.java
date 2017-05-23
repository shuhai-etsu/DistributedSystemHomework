/*---------------------------------------------
 *This class writes student records to an external file
 *File name: WriteRecord.java
 *Author: Shuhai Li
 *Date: December 1,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note:
 * 12/01/2016-instead of wring records to a file, write records to a H2 database
------------------------------------------------*/
import java.io.ObjectInputStream;
import java.net.Socket;

class WriteRecord implements Runnable
{
    Socket socket;
    /*--------------Constructor----------------*/
    public WriteRecord(Socket socket)
    {
        this.socket = socket;
    }

    /*--------------Implementation of run() inherent from Runnable----------*/
    @Override
    public void run()
    {
        StudentRecord sr = null;
        ObjectInputStream toServer = null;
        Object ob = null;
        Database db = new Database();
        db.createTable();
        try
        {
            toServer = new ObjectInputStream(socket.getInputStream());    //initiate the ObjectInputStream object
            try
            {
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
                    }

                    if (sr.getLastName().compareTo("Blank") == 0)
                    {
                        System.out.println("---------------------");
                        System.out.println("Query (student with id="+sr.getId()+") results:");
                        db.queryRecord(sr.getId());
                    }else
                    {
                        System.out.println("----------------------");
                        System.out.println("Inserting this record:");
                        System.out.println(sr);
                        db.insertRecord(sr);

                    }
                    if (sr.getId().compareTo("0") == 0)  //if run into the record with id=0, break the while loop
                        break;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    } //end run()
}	//end class WriteRecord

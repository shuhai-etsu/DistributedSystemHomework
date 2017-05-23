/*---------------------------------------------
 *Driver class for Writer process
 *File name: Writer.java
 *Author: Shuhai Li
 *Date: Decemember 01,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note:
 2016-11-10     Command line argument and communication part were singled out
                to separate classes
2016-12-01
------------------------------------------------*/
import java.net.Socket;
import java.util.concurrent.*;

public class Writer
{
    public static void main(String[] args)
    {
        WriterArgument wa = new WriterArgument(args);
        WriterCommunicator wc = new WriterCommunicator(wa.port,wa.portMax);

	/*------Create and start threads for processing and writing records------*/
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        while (true)
        {
            try
            {
                Socket socket = wc.getServerSocket().accept();          //create server side socket
                if (socket != null && socket.isClosed() == false && socket.isConnected() == true)
                {
                    System.out.println("server socket created successful");
                    Runnable a = new WriteRecord(socket); //instantiate a WriteRecord object
                    executorService.submit(a);                          //put the runnable in thread pool
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("something wrong with Server socket");
            }

        }

    } //end main()

} //end class Writer
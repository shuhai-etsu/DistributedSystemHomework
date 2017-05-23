/*---------------------------------------------
 *This class creates connection to database and handles data manipulation
 *File name: Database.java
 *Author: Shuhai Li
 *Date: December 1,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note:
 * ------------------------------------------------*/
import java.sql.*;
public class Database {
    Connection conn=null;
    /*--------------Constructor--------------------*/
    public Database()
    {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*--------------Create a table named tableTest--------------------*/
    public void createTable()
    {
        try {
            Statement st =  conn.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS tableTest(lname VARCHAR(255),fname VARCHAR(255)," +
                    "id VARCHAR(10), dob VARCHAR(15),age INT)");  //create table
            //st.execute("DELETE FROM tableTest");  //empty the table
            System.out.println("table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*--------------Insert a student record into tableTest--------------------*/
    public void insertRecord(StudentRecord sr)
    {
        try {
            Statement st = conn.createStatement();
            st.execute("INSERT INTO tableTest VALUES ('"+sr.getLastName()+"','"+sr.getFirstName()+
                    "','"+sr.getId()+"','"+sr.getDateOfBirth()+"',"+sr.getAge()+")" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*--------------Fetch and display student records based on id from tableTest-----------*/
    public ResultSet queryRecord(String id)
    {
        ResultSet rs=null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM tableTest WHERE id='"+id+"'" );
            try
            {
                while(rs.next())
                {
                    System.out.println(rs.getString("lname")+"\n"+
                                        rs.getString("fname")+"\n"+
                                        rs.getString("id")+"\n"+
                                        rs.getString("dob")+"\n"+
                                        rs.getInt("age"));
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                System.out.println("record not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
/*---------------------------------------------
 *This class calculates age for student record 
 *File name: StudentRecord.java
 *Author: Dr.Barrett
 *Create Date: October 05,2016
 *Course: Distributed System
 *Organization: East Tennessee State University
 *Revision note:  2016/10/05   make StudentRecord serializable so it can be 
                               sent and received through socket communication
------------------------------------------------*/

import java.io.Serializable;

public class StudentRecord implements Serializable {
    private String lastName;    // Required
    private String firstName;   // Required
    private String id;          // Optional
    private String dateOfBirth; // Optional
    private int age;            // Optional

    public static class StudentRecordBuilder {
        private String firstName;
        private String lastName;
        private String id = "";
        private String dateOfBirth = "";
        private int age = 0;

        public StudentRecordBuilder(String lastName, String firstName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public StudentRecordBuilder id(String s) {
            this.id = s;
            return this;
        }

        public StudentRecordBuilder dateOfBirth(String s) {
            this.dateOfBirth = s;
            return this;
        }

        public StudentRecord build() {
            return new StudentRecord(this);
        }

    }

    private StudentRecord(StudentRecordBuilder b) {
        this.id = b.id;
        this.lastName = b.lastName;
        this.firstName = b.firstName;
        this.dateOfBirth = b.dateOfBirth;
        this.age = b.age;
    }

    public String getId(){
        return this.id;
    }

    public String getDateOfBirth(){
        return this.dateOfBirth;
    }

    public void setAge(int year){
        this.age = year;
    }

    public int getAge(){return this.age;}

    public String getLastName() {return this.lastName;}

    public String getFirstName(){return this.firstName;}

    @Override
    public String toString() {
        return lastName + "\n"
                + firstName + "\n"
                + id + "\n"
                + dateOfBirth + "\n"
                + age + "\n";
    }
}

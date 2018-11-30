package Classes;

import java.util.Date;

public class Student {

    private int id;
    private String stuID;
    private int completeID;
    private String fName;
    private String mName;
    private String surname;
    private String contact;
    private String hTown;

    public Student() {
    }

    public Student(String stuID, int completeID, String fName, String mName, String surname, String contact, String hTown) {
        this.stuID = stuID;
        this.completeID = completeID;
        this.fName = fName;
        this.mName = mName;
        this.surname = surname;
        this.contact = contact;
        this.hTown = hTown;
    }

    public Student(int id, String stuID, int completeID, String fName, String mName, String surname, String contact, String hTown) {
        this.id = id;
        this.stuID = stuID;
        this.completeID = completeID;
        this.fName = fName;
        this.mName = mName;
        this.surname = surname;
        this.contact = contact;
        this.hTown = hTown;
    }

    public Student(int completeID, String fName, String mName, String surname, String contact, String hTown) {
        this.completeID = completeID;
        this.fName = fName;
        this.mName = mName;
        this.surname = surname;
        this.contact = contact;
        this.hTown = hTown;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuID() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public int getCompleteID() {
        return completeID;
    }

    public void setCompleteID(int completeID) {
        this.completeID = completeID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String gethTown() {
        return hTown;
    }

    public void sethTown(String hTown) {
        this.hTown = hTown;
    }

}

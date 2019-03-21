package Classes;

import static com.oracle.jrockit.jfr.ContentType.Bytes;
import java.awt.Image;
import java.util.Date;

public class School {

    private int schoolid;
    private String schoolName;
    private String schoolAddress;
    private String schoolLocation;
    private String schoolVac;
    private String schoolResume;
    private String schoolContact;
    private int pos;

    public School() {
    }

    public School(String schoolName, String schoolAddress, String schoolContact, String schoolLocation, String schoolVac, String schoolResume, int pos) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.schoolLocation = schoolLocation;
        this.schoolVac = schoolVac;
        this.schoolResume = schoolResume;
        this.schoolContact = schoolContact;
        this.pos = pos;
    }


    public School(int schoolid, String schoolName, String schoolAddress, String schoolContact, String schoolLocation, String schoolVac, String schoolResume, int pos) {
        this.schoolid = schoolid;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.schoolLocation = schoolLocation;
        this.schoolVac = schoolVac;
        this.schoolResume = schoolResume;
        this.schoolContact = schoolContact;
        this.pos = pos;
    }

    public int getSchoolid() {
        return schoolid;
    }

    public void setSchoolId(int schoolid) {
        this.schoolid = schoolid;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolLocation() {
        return schoolLocation;
    }

    public void setSchoolLocation(String schoolLocation) {
        this.schoolLocation = schoolLocation;
    }

    public String getSchoolVac() {
        return schoolVac;
    }

    public void setSchoolVac(String schoolVac) {
        this.schoolVac = schoolVac;
    }

    public String getSchoolResume() {
        return schoolResume;
    }

    public void setSchoolResume(String schoolResume) {
        this.schoolResume = schoolResume;
    }

    public String getSchoolContact() {
        return schoolContact;
    }

    public void setSchoolContact(String schoolContact) {
        this.schoolContact = schoolContact;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

}

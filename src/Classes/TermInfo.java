package Classes;

public class TermInfo {

    private int id, clasID, acaID, yerID, stuID, terID, attiID, conID, intID;
    private String teacherRemarks, headRemarks;
    private double balance, totalfees, totalMarks;
    private int attendance, outof, promoted;

    public TermInfo() {
    }

    public TermInfo(int id, int clasID, int acaID, int yerID, int stuID, int attiID, int conID, int intID, int terID, String teacherRemarks, String headRemarks, double balance, double totalfees, int attendance, int outof, int promoted, double totalMarks) {
        this.id = id;
        this.clasID = clasID;
        this.acaID = acaID;
        this.yerID = yerID;
        this.stuID = stuID;
        this.attiID = attiID;
        this.conID = conID;
        this.intID = intID;
        this.terID = terID;
        this.teacherRemarks = teacherRemarks;
        this.headRemarks = headRemarks;
        this.balance = balance;
        this.totalfees = totalfees;
        this.attendance = attendance;
        this.outof = outof;
        this.promoted = promoted;
        this.totalMarks = totalMarks;
    }

    public TermInfo(int clasID, int acaID, int yerID, int stuID, int attiID, int conID, int intID, int terID, String teacherRemarks, String headRemarks, double balance, double totalfees, int attendance, int outof, int promoted, double totalMarks) {
        this.clasID = clasID;
        this.acaID = acaID;
        this.yerID = yerID;
        this.stuID = stuID;
        this.attiID = attiID;
        this.conID = conID;
        this.intID = intID;
        this.terID = terID;
        this.teacherRemarks = teacherRemarks;
        this.headRemarks = headRemarks;
        this.balance = balance;
        this.totalfees = totalfees;
        this.attendance = attendance;
        this.outof = outof;
        this.promoted = promoted;
        this.totalMarks = totalMarks;
    }

    public TermInfo(int attiID, int conID, int intID, String teacherRemarks, String headRemarks, double balance, double totalfees, int attendance, int outof, int promoted, double totalMarks) {
        this.attiID = attiID;
        this.conID = conID;
        this.intID = intID;
        this.teacherRemarks = teacherRemarks;
        this.headRemarks = headRemarks;
        this.balance = balance;
        this.totalfees = totalfees;
        this.attendance = attendance;
        this.outof = outof;
        this.promoted = promoted;
        this.totalMarks = totalMarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClasID() {
        return clasID;
    }

    public void setClasID(int clasID) {
        this.clasID = clasID;
    }

    public int getAcaID() {
        return acaID;
    }

    public void setAcaID(int acaID) {
        this.acaID = acaID;
    }

    public int getYerID() {
        return yerID;
    }

    public void setYerID(int yerID) {
        this.yerID = yerID;
    }

    public int getStuID() {
        return stuID;
    }

    public void setStuID(int stuID) {
        this.stuID = stuID;
    }

    public int getTerID() {
        return terID;
    }

    public void setTerID(int terID) {
        this.terID = terID;
    }

    public int getAttiID() {
        return attiID;
    }

    public void setAttiID(int attiID) {
        this.attiID = attiID;
    }

    public int getConID() {
        return conID;
    }

    public void setConID(int conID) {
        this.conID = conID;
    }

    public int getIntID() {
        return intID;
    }

    public void setIntID(int intID) {
        this.intID = intID;
    }

    public String getTeacherRemarks() {
        return teacherRemarks;
    }

    public void setTeacherRemarks(String teacherRemarks) {
        this.teacherRemarks = teacherRemarks;
    }

    public String getHeadRemarks() {
        return headRemarks;
    }

    public void setHeadRemarks(String headRemarks) {
        this.headRemarks = headRemarks;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalfees() {
        return totalfees;
    }

    public void setTotalfees(double totalfees) {
        this.totalfees = totalfees;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getOutof() {
        return outof;
    }

    public void setOutof(int outof) {
        this.outof = outof;
    }

    public int getPromoted() {
        return promoted;
    }

    public void setPromoted(int promoted) {
        this.promoted = promoted;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

}

package Classes;

public class Academic {

    private int acaid;
    private String academicYear;

    public Academic() {
    }

    public Academic(String academicYear) {
        this.academicYear = academicYear;
    }

    public Academic(int acaid, String academicYear) {
        this.acaid = acaid;
        this.academicYear = academicYear;
    }

    public int getAcaid() {
        return acaid;
    }

    public void setAcaid(int acaid) {
        this.acaid = acaid;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    
}

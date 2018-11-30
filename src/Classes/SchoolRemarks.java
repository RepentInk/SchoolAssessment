
package Classes;

public class SchoolRemarks {
   
    public int id;
    public double lowMarks;
    public double higMarks;
    public String grd;
    public String rem;

    public SchoolRemarks() {
    }

    public SchoolRemarks(double lowMarks, double higMarks, String grd, String rem) {
        this.lowMarks = lowMarks;
        this.higMarks = higMarks;
        this.grd = grd;
        this.rem = rem;
    }

    public SchoolRemarks(int id, double lowMarks, double higMarks, String grd, String rem) {
        this.id = id;
        this.lowMarks = lowMarks;
        this.higMarks = higMarks;
        this.grd = grd;
        this.rem = rem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLowMarks() {
        return lowMarks;
    }

    public void setLowMarks(double lowMarks) {
        this.lowMarks = lowMarks;
    }

    public double getHigMarks() {
        return higMarks;
    }

    public void setHigMarks(double higMarks) {
        this.higMarks = higMarks;
    }

    public String getGrd() {
        return grd;
    }

    public void setGrd(String grd) {
        this.grd = grd;
    }

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }  
}

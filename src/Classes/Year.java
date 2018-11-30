package Classes;

public class Year {
    
    private int yid;
    private String yearName;

    public Year() {
    }

    public Year(int yid) {
        this.yid = yid;
    }

    public Year(String yearName) {
        this.yearName = yearName;
    }
    
    public Year(int yid, String yearName) {
        this.yid = yid;
        this.yearName = yearName;
    }

    public int getYid() {
        return yid;
    }

    public void setYid(int yid) {
        this.yid = yid;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }
     
}

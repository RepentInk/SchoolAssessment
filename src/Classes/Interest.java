package Classes;

public class Interest {
    
    private int id;
    private String interestName;

    public Interest() {
    }

    public Interest(String interestName) {
        this.interestName = interestName;
    }

    public Interest(int id, String interestName) {
        this.id = id;
        this.interestName = interestName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }
    
}

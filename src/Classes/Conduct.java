package Classes;


public class Conduct {
    
    private int id;
    private String conductName;

    public Conduct() {
    }

    public Conduct(String conductName) {
        this.conductName = conductName;
    }

    public Conduct(int id, String conductName) {
        this.id = id;
        this.conductName = conductName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConductName() {
        return conductName;
    }

    public void setConductName(String conductName) {
        this.conductName = conductName;
    }
    
}

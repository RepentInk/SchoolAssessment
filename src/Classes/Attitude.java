package Classes;

public class Attitude {

    private int id;
    private String attitudeName;

    public Attitude() {
    }

    public Attitude(String attitudeName) {
        this.attitudeName = attitudeName;
    }

    public Attitude(int id, String attitudeName) {
        this.id = id;
        this.attitudeName = attitudeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttitudeName() {
        return attitudeName;
    }

    public void setAttitudeName(String attitudeName) {
        this.attitudeName = attitudeName;
    }

}


package Classes;


public class Classess {
    
    private int cid;
    private String className;

    public Classess() {
    }

    public Classess(String className) {
        this.className = className;
    }

    public Classess(int cid, String className) {
        this.cid = cid;
        this.className = className;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    
}

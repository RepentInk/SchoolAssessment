package Classes;

/**
 *
 * @author Ink
 */
public class Subject {

    private int sid;
    private String scode;
    private String subjectName;

    public Subject() {
    }

    public Subject(String scode, String subjectName) {
        this.scode = scode;
        this.subjectName = subjectName;
    }

    public Subject(int sid, String scode, String subjectName) {
        this.sid = sid;
        this.scode = scode;
        this.subjectName = subjectName;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
}

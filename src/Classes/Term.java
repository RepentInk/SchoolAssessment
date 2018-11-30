package Classes;

public class Term {

    private int tid;
    private String termName;

    public Term() {
    }

    public Term(String termName) {
        this.termName = termName;
    }

    public Term(int tid, String termName) {
        this.tid = tid;
        this.termName = termName;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

}

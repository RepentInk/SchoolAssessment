package Classes;

public class Assessment {

    private int aid;
    private int stuId;
    private int term_id;
    private int academic_id;
    private int subject_id;
    private int year_id;
    private int class_id;
    private double classTest;
    private double others;
    private double total;
    private double fiftyPercentAssessment;

    public Assessment() {
    }

    public Assessment(int aid, int stuId, int class_id, double classTest, double others, double total, double fiftyPercentAssessment) {
        this.aid = aid;
        this.stuId = stuId;
        this.class_id = class_id;
        this.classTest = classTest;
        this.others = others;
        this.total = total;
        this.fiftyPercentAssessment = fiftyPercentAssessment;
    }

    public Assessment(int stuId, int term_id, int academic_id, int subject_id, int year_id, int class_id, double classTest, double others, double total, double fiftyPercentAssessment) {
        this.stuId = stuId;
        this.term_id = term_id;
        this.academic_id = academic_id;
        this.subject_id = subject_id;
        this.year_id = year_id;
        this.class_id = class_id;
        this.classTest = classTest;
        this.others = others;
        this.total = total;
        this.fiftyPercentAssessment = fiftyPercentAssessment;
    }

    public Assessment(int aid, int stuId, int term_id, int academic_id, int subject_id, int year_id, int class_id, double classTest, double others, double total, double fiftyPercentAssessment) {
        this.aid = aid;
        this.stuId = stuId;
        this.term_id = term_id;
        this.academic_id = academic_id;
        this.subject_id = subject_id;
        this.year_id = year_id;
        this.class_id = class_id;
        this.classTest = classTest;
        this.others = others;
        this.total = total;
        this.fiftyPercentAssessment = fiftyPercentAssessment;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public int getAcademic_id() {
        return academic_id;
    }

    public void setAcademic_id(int academic_id) {
        this.academic_id = academic_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getYear_id() {
        return year_id;
    }

    public void setYear_id(int year_id) {
        this.year_id = year_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public double getClassTest() {
        return classTest;
    }

    public void setClassTest(double classTest) {
        this.classTest = classTest;
    }

    public double getOthers() {
        return others;
    }

    public void setOthers(double others) {
        this.others = others;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getFiftyPercentAssessment() {
        return fiftyPercentAssessment;
    }

    public void setFiftyPercentAssessment(double fiftyPercentAssessment) {
        this.fiftyPercentAssessment = fiftyPercentAssessment;
    }

}

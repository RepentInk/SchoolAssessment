package Classes;

public class Exams {

    private int eid;
    private int stuId;
    private int term_id;
    private int academic_id;
    private int subject_id;
    private int year_id;
    private int class_id;
    private double objectives;
    private double theory;
    private double totalExams;
    private double fiftyPercentExams;

    public Exams() {
    }

    public Exams(int eid, int stuId, double objectives, double theory, double totalExams, double fiftyPercentExams) {
        this.eid = eid;
        this.stuId = stuId;
        this.objectives = objectives;
        this.theory = theory;
        this.totalExams = totalExams;
        this.fiftyPercentExams = fiftyPercentExams;
    }

    public Exams(int stuId, int term_id, int academic_id, int subject_id, int year_id, int class_id, double objectives, double theory, double totalExams, double fiftyPercentExams) {
        this.stuId = stuId;
        this.term_id = term_id;
        this.academic_id = academic_id;
        this.subject_id = subject_id;
        this.year_id = year_id;
        this.class_id = class_id;
        this.objectives = objectives;
        this.theory = theory;
        this.totalExams = totalExams;
        this.fiftyPercentExams = fiftyPercentExams;
    }

    public Exams(int eid, int stuId, int term_id, int academic_id, int subject_id, int year_id, int class_id, double objectives, double theory, double totalExams, double fiftyPercentExams) {
        this.eid = eid;
        this.stuId = stuId;
        this.term_id = term_id;
        this.academic_id = academic_id;
        this.subject_id = subject_id;
        this.year_id = year_id;
        this.class_id = class_id;
        this.objectives = objectives;
        this.theory = theory;
        this.totalExams = totalExams;
        this.fiftyPercentExams = fiftyPercentExams;
    }

    public Exams(double objectives, double theory, double totalExams, double fiftyPercentExams) {
        this.objectives = objectives;
        this.theory = theory;
        this.totalExams = totalExams;
        this.fiftyPercentExams = fiftyPercentExams;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
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

    public double getObjectives() {
        return objectives;
    }

    public void setObjectives(double objectives) {
        this.objectives = objectives;
    }

    public double getTheory() {
        return theory;
    }

    public void setTheory(double theory) {
        this.theory = theory;
    }

    public double getTotalExams() {
        return totalExams;
    }

    public void setTotalExams(double totalExams) {
        this.totalExams = totalExams;
    }

    public double getFiftyPercentExams() {
        return fiftyPercentExams;
    }

    public void setFiftyPercentExams(double fiftyPercentExams) {
        this.fiftyPercentExams = fiftyPercentExams;
    }

}

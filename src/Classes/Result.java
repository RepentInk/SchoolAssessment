package Classes;

public class Result {

    private int rid;
    private int stuId;
    private int term_id;
    private int academic_id;
    private int subject_id;
    private int year_id;
    private int class_id;
    private double exams;
    private double assessment;
    private double totalResult;
    private String grade;
    private int remarks;

    public Result() {
    }

    public Result(int rid, int stuId, int class_id, double exams, double assessment, double totalResult, String grade, int remarks) {
        this.rid = rid;
        this.stuId = stuId;
        this.class_id = class_id;
        this.exams = exams;
        this.assessment = assessment;
        this.totalResult = totalResult;
        this.grade = grade;
        this.remarks = remarks;
    }

    public Result(int stuId, int term_id, int academic_id, int subject_id, int year_id, int class_id, double exams, double assessment, double totalResult, String grade, int remarks) {
        this.stuId = stuId;
        this.term_id = term_id;
        this.academic_id = academic_id;
        this.subject_id = subject_id;
        this.year_id = year_id;
        this.class_id = class_id;
        this.exams = exams;
        this.assessment = assessment;
        this.totalResult = totalResult;
        this.grade = grade;
        this.remarks = remarks;
    }

    public Result(int rid, int stuId, int term_id, int academic_id, int subject_id, int year_id, int class_id, double exams, double assessment, double totalResult, String grade, int remarks) {
        this.rid = rid;
        this.stuId = stuId;
        this.term_id = term_id;
        this.academic_id = academic_id;
        this.subject_id = subject_id;
        this.year_id = year_id;
        this.class_id = class_id;
        this.exams = exams;
        this.assessment = assessment;
        this.totalResult = totalResult;
        this.grade = grade;
        this.remarks = remarks;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
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

    public double getExams() {
        return exams;
    }

    public void setExams(double exams) {
        this.exams = exams;
    }

    public double getAssessment() {
        return assessment;
    }

    public void setAssessment(double assessment) {
        this.assessment = assessment;
    }

    public double getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(double totalResult) {
        this.totalResult = totalResult;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getRemarks() {
        return remarks;
    }

    public void setRemarks(int remarks) {
        this.remarks = remarks;
    }

}

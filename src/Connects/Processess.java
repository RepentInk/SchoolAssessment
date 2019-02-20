package Connects;

import Classes.Academic;
import Classes.Assessment;
import Classes.Classess;
import Classes.Exams;
import Classes.Result;
import Classes.School;
import Classes.SchoolRemarks;
import Classes.Student;
import Classes.Subject;
import Classes.Term;
import Classes.Year;
import Designs.AssessmentForm;
import java.util.List;

public interface Processess {

    //========Student Method Starts here=========================
    public void saveStudent(Student student);

    public List<Student> findAllStudent(int id);

    public void updateStudent(Student stu, int id, String stuid);

    public void deleteStudent(int id, int completeYear);

    //=========Students Methods ends here==========================
    //========Subjects methods starts here =======================
    public void saveSubject(Subject subject);

    public List<Subject> findAllSubject();

    public void updateSubject(Subject subject, int sid);

    public void deleteSubject(int id);

    //=======Subjects methods ends here ===========================
    //========Year methods starts here ============================
    public void saveYear(Year year);

    public List<Year> findAllYear();

    public void updateYear(Year year);

    public void deleteYear(int id);

    //=======Year methods ends here ===========================
    //========Year methods starts here ============================
    public void saveTerm(Term term);

    public List<Term> findAllTerm();

    public void updateTerm(Term term);

    public void deleteTerm(int id);

    //=======Year methods ends here ===========================
    //========Classess methods starts here ============================
    public void saveClass(Classess classess);

    public List<Classess> findAllClassess();

    public void updateClassess(Classess classess);

    public void deleteClass(int id);

    //=======Classess methods ends here ===========================
    //========Academic Year method starts here====================
    public void saveAcademic(Academic academic);

    public List<Academic> findAllAcademic();

    public void updateAcademic(Academic academic);

    public void deleteAcademic(int id);

    //=======Academic year ends here =============================
    //========Exams method starts here========================
    public void saveExams(Exams exams);

    public List<Exams> findAllExams(int termid, int acaid, int subid, int yearid, int clasid);

    public void updateExams(Exams exams, int stuid, int classid);

    public void deleteExams(int id);

    //========Exams methods ends here =============================
    //========Assessment method starts here=======================
    public void saveAssessment(Assessment assessment);

    public List<Assessment> findAllAssessment(int termid, int acaid, int subid, int yearid, int clasid);

    public void updateAssessment(Assessment assessment, int stuid, int classid);

    public void deleteAssessment(int id);

    //========Assessment methods ends here =============================
    //========Results method starts here================================
    public void saveResult(Result result);

    public List<Result> findAllResult();

    public void updateResult(Result result, int stuid, int classid);

    public void deleteResult(int id);

    //========Results methods ends here =============================
    //================School Remarks ================================
    public void saveSchoolRemarks(SchoolRemarks schRemark );

    public List<SchoolRemarks> findAllSchoolRemaks();

    public void updateSchoolRemarks(SchoolRemarks rmarks, int id);

    public void deleteRemarks(int id);
    
    //================School ================================
    public void saveSchoolDetails(School school );

    public List<School> findAllSchoolDetails();

    public void updateSchoolDetails(School school, int id);

    public void deleteSchoolDetails(int id);
}

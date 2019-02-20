/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connects;

import Classes.Academic;
import Classes.Exams;
import Classes.Result;
import Classes.Student;
import Classes.Subject;
import Classes.Term;
import Classes.Year;
import Classes.Assessment;
import Classes.Classess;
import Classes.School;
import Classes.SchoolRemarks;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Ink
 */
public class ProgramDAO implements Processess {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public ProgramDAO() {
        conn = myCon.ConnecrDb();
    }

    ///======Students methods starts here============================
    public void saveStudent(Student student) {
        try {
            String sql = "INSERT INTO Student (stu_ID,completedYear_id,fName,mName,surname,contact,hometown) VALUES (?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, student.getStuID());
            pst.setInt(2, student.getCompleteID());
            pst.setString(3, student.getfName());
            pst.setString(4, student.getmName());
            pst.setString(5, student.getSurname());
            pst.setString(6, student.getContact());
            pst.setString(7, student.gethTown());
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null, "Student records is saved");
        } catch (Exception e) {
            // JOptionPane.showMessageDialog(null, "Here");
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    public List<Student> findAllStudent(int id) {
        List<Student> studentList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Student where completedYear_id = '"+ id +"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStuID(rs.getString("stu_ID"));
                student.setCompleteID(rs.getInt("deleted_at"));
                student.setfName(rs.getString("fName"));
                student.setmName(rs.getString("mName"));
                student.setSurname(rs.getString("surname"));
                student.setContact(rs.getString("contact"));
                student.sethTown(rs.getString("hometown"));
                studentList.add(student);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return studentList;
    }

    public void updateStudent(Student student, int id, String stuid) {
        try {
            String sql = "UPDATE Student SET completedYear_id='" + student.getCompleteID() + "',fName='" + student.getfName() + "',"
                    + "mName='" + student.getmName() + "', surname='" + student.getSurname() + "',contact='" + student.getContact() + "', hometown='" + student.gethTown() + "' "
                    + "WHERE id='" + id + "' AND stu_ID='" + stuid + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Student Details is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteStudent(int id, int completeYear) {
        try {
            String sql = "DELETE FROM Student WHERE id='" + id + "' AND completedYear_id='" + completeYear + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Student Details is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //=======Students methods ends here===============================
    //========Subjects methods starts here=============================
    public void saveSubject(Subject subject) {
        try {
            String subjectSQL = "INSERT INTO Subject (subCode,subName) VALUES (?,?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setString(1, subject.getScode());
            pst.setString(2, subject.getSubjectName());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Subject Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Subject> findAllSubject() {
        List<Subject> listSubject = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSid(rs.getInt("id"));
                subject.setScode(rs.getString("subCode"));
                subject.setSubjectName(rs.getString("subName"));
                listSubject.add(subject);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return listSubject;
    }

    public void updateSubject(Subject subject, int sid) {
        try {
            String sql = "UPDATE Subject SET subCode='" + subject.getScode() + "', subName='" + subject.getSubjectName() + "' WHERE id='" + sid + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Subject is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteSubject(int id) {
        try {
            String sql = "DELETE FROM Subject WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Subject is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //=======Subjects methods ends here ===============================
    //=========Year method starts here ================================
    public void saveYear(Year year) {
        try {
            String subjectSQL = "INSERT into Year (yearName) values (?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setString(1, year.getYearName());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Year Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Year> findAllYear() {
        List<Year> listYear = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Year ORDER BY yearName DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Year year = new Year();
                year.setYid(rs.getInt("id"));
                year.setYearName(rs.getString("yearName"));
                listYear.add(year);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return listYear;
    }

    public void updateYear(Year year) {
        try {
            String sql = "UPDATE Year SET id='" + year.getYid() + "',yearName='" + year.getYearName() + "' WHERE id='" + year.getYid() + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Year is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteYear(int id) {
        try {
            String sql = "DELETE FROM Year WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Year is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //=========Year method ends here ==================================
    //========Term methods starts here=================================
    public void saveTerm(Term term) {
        try {
            String subjectSQL = "insert into Term (termName) values (?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setString(1, term.getTermName());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Term is Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Term> findAllTerm() {
        List<Term> listTerm = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Term";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Term term = new Term();
                term.setTid(rs.getInt("id"));
                term.setTermName(rs.getString("termName"));
                listTerm.add(term);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return listTerm;
    }

    public void updateTerm(Term term) {
        try {
            String sql = "UPDATE Term SET id='" + term.getTid() + "',termName='" + term.getTermName() + "' WHERE id='" + term.getTid() + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Term is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteTerm(int id) {
        try {
            String sql = "DELETE FROM Term WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Term is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //=======Term ends here ===========================================
    //===== Classess methods starts here =============================
    public void saveClass(Classess classess) {
        try {
            String subjectSQL = "insert into Classes (className) values (?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setString(1, classess.getClassName());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Class is Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Classess> findAllClassess() {
        List<Classess> listClass = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Classes";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Classess classess = new Classess();
                classess.setCid(rs.getInt("id"));
                classess.setClassName(rs.getString("className"));
                listClass.add(classess);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return listClass;
    }

    public void updateClassess(Classess classess) {
        try {
            String sql = "UPDATE Classes SET id='" + classess.getCid() + "',className='" + classess.getClassName() + "' WHERE id='" + classess.getCid() + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Class is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteClass(int id) {
        try {
            String sql = "DELETE FROM Classes WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Class is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //======Classess method starts here===============================
    //=======Academic year methods starts here =======================
    public void saveAcademic(Academic academic) {
        try {
            String subjectSQL = "INSERT into Academic (academicYear) values (?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setString(1, academic.getAcademicYear());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Academic year is Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "wrong");
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Academic> findAllAcademic() {
        List<Academic> listAcademic = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Academic order by academicYear DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Academic academic = new Academic();
                academic.setAcaid(rs.getInt("id"));
                academic.setAcademicYear(rs.getString("academicYear"));
                listAcademic.add(academic);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return listAcademic;
    }

    public void updateAcademic(Academic academic) {
        try {
            String sql = "UPDATE Academic SET id='" + academic.getAcaid() + "',academicYear='" + academic.getAcademicYear() + "' WHERE id='" + academic.getAcaid() + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Academic Year is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteAcademic(int id) {
        try {
            String sql = "DELETE FROM Academic WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Academic year is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //======Academic year methods ends here ==========================
    //===========Exams methods starts here ===========================
    public void saveExams(Exams exams) {
        try {
            String subjectSQL = "INSERT INTO Exams (stuID,term_id,academic_id,subject_id,year_id,class_id,objectives,theory,totalExams,fiftyPercent) VALUES (?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setInt(1, exams.getStuId());
            pst.setInt(2, exams.getTerm_id());
            pst.setInt(3, exams.getAcademic_id());
            pst.setInt(4, exams.getSubject_id());
            pst.setInt(5, exams.getYear_id());
            pst.setInt(6, exams.getClass_id());
            pst.setDouble(7, exams.getObjectives());
            pst.setDouble(8, exams.getTheory());
            pst.setDouble(9, exams.getTotalExams());
            pst.setDouble(10, exams.getFiftyPercentExams());
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null, "Exams is Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Exams> findAllExams(int termid, int acaid, int subid, int yearid, int clasid) {
        List<Exams> listExams = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Exams where term_id='" + termid + "' and academic_id='" + acaid + "' and subject_id='" + subid + "' and year_id='" + yearid + "' and class_id='" + clasid + "' order by totalExams DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Exams exams = new Exams();
                exams.setStuId(rs.getInt("stuID"));
                exams.setTerm_id(rs.getInt("term_id"));
                exams.setAcademic_id(rs.getInt("academic_id"));
                exams.setSubject_id(rs.getInt("subject_id"));
                exams.setYear_id(rs.getInt("year_id"));
                exams.setClass_id(rs.getInt("class_id"));
                exams.setObjectives(rs.getDouble("objectives"));
                exams.setTheory(rs.getDouble("theory"));
                exams.setTotalExams(rs.getDouble("totalExams"));
                exams.setFiftyPercentExams(rs.getDouble("fiftyPercent"));
                listExams.add(exams);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return listExams;
    }

    public void updateExams(Exams exams, int id, int classid) {
        try {
            String sql = "UPDATE Exams SET objectives='" + exams.getObjectives() + "', theory='" + exams.getTheory() + "', totalExams='" + exams.getTotalExams() + "',"
                    + "fiftyPercent='" + exams.getFiftyPercentExams() + "' WHERE id='" + id + "' and class_id='" + classid + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null, "Exams is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteExams(int id) {
        try {
            String sql = "DELETE FROM Exams WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null, "Exams is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //=============Exams methods ends here ===========================
    //============Assessment methods starts here =====================
    public void saveAssessment(Assessment assessment) {
        try {
            String subjectSQL = "INSERT INTO Assessment (stuID,term_id,academic_id,subject_id,year_id,class_id,classTest,others,totalAssess,fiftyPercent) VALUES (?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setInt(1, assessment.getStuId());
            pst.setInt(2, assessment.getTerm_id());
            pst.setInt(3, assessment.getAcademic_id());
            pst.setInt(4, assessment.getSubject_id());
            pst.setInt(5, assessment.getYear_id());
            pst.setInt(6, assessment.getClass_id());
            pst.setDouble(7, assessment.getClassTest());
            pst.setDouble(8, assessment.getOthers());
            pst.setDouble(9, assessment.getTotal());
            pst.setDouble(10, assessment.getFiftyPercentAssessment());
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null, "Assessment is Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Assessment> findAllAssessment(int termid, int acaid, int subid, int yearid, int clasid) {
        List<Assessment> listAssessment = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Assessment where term_id='" + termid + "' and academic_id='" + acaid + "' and subject_id='" + subid + "' and year_id='" + yearid + "' and class_id='" + clasid + "' order by totalAssess DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Assessment assess = new Assessment();
                assess.setStuId(rs.getInt("stuID"));
                assess.setTerm_id(rs.getInt("term_id"));
                assess.setAcademic_id(rs.getInt("academic_id"));
                assess.setSubject_id(rs.getInt("subject_id"));
                assess.setYear_id(rs.getInt("year_id"));
                assess.setClass_id(rs.getInt("class_id"));
                assess.setClassTest(rs.getDouble("classTest"));
                assess.setOthers(rs.getDouble("others"));
                assess.setTotal(rs.getDouble("totalAssess"));
                assess.setFiftyPercentAssessment(rs.getDouble("fiftyPercent"));
                listAssessment.add(assess);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return listAssessment;
    }

    public void updateAssessment(Assessment assessment, int id, int classid) {
        try {
            String sql = "UPDATE Assessment SET classTest='" + assessment.getClassTest() + "', others='" + assessment.getOthers() + "', totalAssess='" + assessment.getTotal() + "',"
                    + "fiftyPercent='" + assessment.getFiftyPercentAssessment() + "' WHERE id='" + id + "' and class_id='" + classid + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            // JOptionPane.showMessageDialog(null, "Exams is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteAssessment(int id) {
        try {
            String sql = "DELETE FROM Assessment WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null, "Exams is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //============Assessment methods ends here =================================
    //===========Final Results method goes here =================================
    public void saveResult(Result result) {
        try {
            String subjectSQL = "INSERT INTO Result (stuID,term_id,academic_id,subject_id,year_id,class_id,exams,assessment,total,grade,remarks) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setInt(1, result.getStuId());
            pst.setInt(2, result.getTerm_id());
            pst.setInt(3, result.getAcademic_id());
            pst.setInt(4, result.getSubject_id());
            pst.setInt(5, result.getYear_id());
            pst.setInt(6, result.getClass_id());
            pst.setDouble(7, result.getExams());
            pst.setDouble(8, result.getAssessment());
            pst.setDouble(9, result.getTotalResult());
            pst.setString(10, result.getGrade());
            pst.setInt(11, result.getRemarks());
            pst.executeUpdate();

            // JOptionPane.showMessageDialog(null, "Result is Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Result> findAllResult() {
        List<Result> listResult = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Result";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                result.setStuId(rs.getInt("stu_ID"));
                result.setExams(rs.getDouble("exams"));
                result.setAssessment(rs.getDouble("assessment"));
                result.setTotalResult(rs.getDouble("total"));
                result.setGrade(rs.getString("grade"));
                result.setRemarks(rs.getInt("remarks"));
                listResult.add(result);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return listResult;
    }

    public List<Result> findAllResultSpecific(int termid, int acaid, int subid, int yearid, int clasid) {
        List<Result> listResult = new ArrayList<>();
        try {
            String sql = "select * from Result where term_id='" + termid + "' and academic_id='" + acaid + "' and subject_id='" + subid + "' and year_id='" + yearid + "' and class_id='" + clasid + "' order by total DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                result.setStuId(rs.getInt("stuID"));
                result.setTerm_id(rs.getInt("term_id"));
                result.setAcademic_id(rs.getInt("academic_id"));
                result.setSubject_id(rs.getInt("subject_id"));
                result.setYear_id(rs.getInt("year_id"));
                result.setClass_id(rs.getInt("class_id"));
                result.setExams(rs.getDouble("exams"));
                result.setAssessment(rs.getDouble("assessment"));
                result.setTotalResult(rs.getDouble("total"));
                result.setGrade(rs.getString("grade"));
                result.setRemarks(rs.getInt("remarks"));
                listResult.add(result);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return listResult;
    }

    public void updateResult(Result result, int id, int classid) {
        try {
            String sql = "UPDATE Result SET exams='" + result.getExams() + "', assessment='" + result.getAssessment() + "', total='" + result.getTotalResult() + "',"
                    + "grade='" + result.getGrade() + "', remarks='" + result.getRemarks() + "' WHERE id='" + id + "' and class_id='" + classid + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null, "Result is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteResult(int id) {
        try {
            String sql = "DELETE FROM Result WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            // JOptionPane.showMessageDialog(null, "Result is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    ////=================Final Results methods ends here =========================
    //===== Classess methods starts here =============================
    public void saveSchoolRemarks(SchoolRemarks schRemark) {
        try {
            String subjectSQL = "insert into SchoolDetails (lmarks, hmarks, grade, remarks) values (?,?,?,?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setDouble(1, schRemark.getLowMarks());
            pst.setDouble(2, schRemark.getHigMarks() + 0.9);
            pst.setString(3, schRemark.getGrd());
            pst.setString(4, schRemark.getRem());
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null, "Range is Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<SchoolRemarks> findAllSchoolRemaks() {
        List<SchoolRemarks> listRemarks = new ArrayList<>();
        try {
            String sql = "SELECT * FROM SchoolDetails";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                SchoolRemarks remarks = new SchoolRemarks();

                remarks.setId(rs.getInt("id"));
                remarks.setLowMarks(rs.getDouble("lmarks"));
                remarks.setHigMarks(rs.getDouble("hmarks"));
                remarks.setGrd(rs.getString("grade"));
                remarks.setRem(rs.getString("remarks"));
                listRemarks.add(remarks);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return listRemarks;
    }

    public void updateSchoolRemarks(SchoolRemarks rmarks, int idd) {
        try {
            String sql = "UPDATE SchoolDetails SET lmarks='" + rmarks.getLowMarks() + "',hmarks='" + rmarks.getHigMarks() + "',grade='" + rmarks.getGrd() + "',remarks='" + rmarks.getRem() + "' WHERE id='" + idd + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Grading is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public void deleteRemarks(int id) {
        try {
            String sql = "DELETE FROM SchoolDetails WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Range Score is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    //======Classess method starts here===============================
    // All clear of records
    public void allClear(String db) {
        try {
            String sql = "DELETE FROM '" + db + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "All Records is cleared");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public void saveSchoolDetails(School school) {
        try {
            String subjectSQL = "INSERT INTO SchoolInfo (schoolName,schoolAddress,schoolLocation,schoolVacation,schoolResume,schoolLogo,contact) VALUES (?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(subjectSQL);
            pst.setString(1, school.getSchoolName());
            pst.setString(2, school.getSchoolAddress());
            pst.setString(3, school.getSchoolLocation());
            pst.setString(4, school.getSchoolVac());
            pst.setString(5, school.getSchoolResume());
            pst.setBytes(6, school.getSchoolLogo());
            pst.setString(7, school.getSchoolContact());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "School Info is Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }

    public List<School> findAllSchoolDetails() {
        List<School> listSchool = new ArrayList<>();
        try {
            String sql = "SELECT * FROM SchoolInfo";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                School sch = new School();

                sch.setSchoolId(rs.getInt("id"));
                sch.setSchoolName(rs.getString("schoolName"));
                sch.setSchoolAddress(rs.getString("schoolAddress"));
                sch.setSchoolLocation(rs.getString("schoolLocation"));
                sch.setSchoolVac(rs.getString("schoolVacation"));
                sch.setSchoolResume(rs.getString("schoolResume"));
                sch.setSchoolLogo(rs.getBytes("schoolLogo"));
                sch.setSchoolContact(rs.getString("contact"));
                listSchool.add(sch);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return listSchool;
    }

    public void updateSchoolDetails(School school, int id) {
        try {
            String sql = "UPDATE SchoolInfo SET schoolName='" + school.getSchoolName() + "', schoolAddress='" + school.getSchoolAddress() + "', schoolLocation='" + school.getSchoolLocation() + "',"
                    + "schoolVacation='" + school.getSchoolVac() + "', schoolResume='" + school.getSchoolResume() + "',schoolLogo='" + school.getSchoolLogo() + "', contact='" + school.getSchoolContact() + "' WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "School details is Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void deleteSchoolDetails(int id) {
        try {
            String sql = "DELETE FROM SchoolInfo WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "School details is deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }
}

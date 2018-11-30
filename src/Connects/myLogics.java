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
import static com.oracle.jrockit.jfr.ContentType.Bytes;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class myLogics {

    ProgramDAO pro = new ProgramDAO();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    int stuID, terID, acaID, subjID, yearID, clasID;
    String t1, t2, t3, t4;
    double ctext, others, total, fpercent;

    public myLogics() {
        conn = myCon.ConnecrDb();
    }

    //======== Method populate data into year comboBox
    public void setYearToCombo(JComboBox cmb, String title) {
        List<Year> year = pro.findAllYear();
        DefaultComboBoxModel years = (DefaultComboBoxModel) cmb.getModel();
        years.removeAllElements();
        years.addElement(title);

        for (Year y : year) {
            years.addElement(y.getYearName());
        }
    }

    //======== Method populate data into year comboBox
    public void setRemarksToCombo(JComboBox cmb, String title) {
        List<SchoolRemarks> rmarks = pro.findAllSchoolRemaks();
        DefaultComboBoxModel years = (DefaultComboBoxModel) cmb.getModel();
        years.removeAllElements();
        years.addElement(title);

        for (SchoolRemarks rm : rmarks) {
            years.addElement(rm.getLowMarks() + "   " + rm.getHigMarks() + "   " + rm.getGrd() + "  " + "  " + rm.getRem());
        }
    }

    //======Year method to decide if it exist ==========
    public boolean returnYear(String txt) {
        boolean myYear = false;
        try {
            String year = "select yearName from Year where yearName='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myYear = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myYear;
    }

    //====Method call class year and save data==========
    public void saveYear(JTextField tYear) {
        if (tYear.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {
            String txt = tYear.getText().trim();
            boolean yearExist = returnYear(tYear.getText().trim());

            if (yearExist == true) {
                tYear.setBackground(Color.LIGHT_GRAY);
                tYear.setText("Year Exist");
            } else {
                Year year = new Year(txt);
                pro.saveYear(year);
                pro.findAllYear();
            }
        }
    }

    //======Year method to decide if it exist ==========
    public boolean returnAcademic(String txt) {
        boolean myAcademic = false;
        try {
            String year = "select academicYear from Academic where academicYear='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myAcademic = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myAcademic;
    }

    //============Method save into academic into db
    public void saveAcademic(JTextField acad) {
        if (acad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {
            boolean exist = returnAcademic(acad.getText().trim());
            String txt = acad.getText().trim();

            if (exist == true) {
                acad.setBackground(Color.LIGHT_GRAY);
                acad.setText("Academic Year Exist");
            } else {
                Academic academic = new Academic(txt);
                pro.saveAcademic(academic);
                pro.findAllAcademic();
            }
        }
    }

    //======== Method populate data into academic year comboBox
    public void setAcademicToCombo(JComboBox cmb, String title) {
        List<Academic> academic = pro.findAllAcademic();
        DefaultComboBoxModel acade = (DefaultComboBoxModel) cmb.getModel();
        acade.removeAllElements();
        acade.addElement(title);

        for (Academic aca : academic) {
            acade.addElement(aca.getAcademicYear());
        }
    }

    //======Term method to decide if it exist ==========
    public boolean returnTerm(String txt) {
        boolean myTerm = false;
        try {
            String year = "select termName from Term where termName='" + txt.toLowerCase() + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myTerm = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myTerm;
    }
    
    public String returnTermName(int txt) {
        String myTerm = null;
        try {
            String year = "select termName from Term where id='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myTerm = rs.getString("termName");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myTerm;
    }

    //============Method save term into db
    public void saveTerm(JTextField ter) {
        if (ter.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {
            boolean exist = returnTerm(ter.getText().trim());
            String txt = ter.getText().trim();

            if (exist == true) {
                ter.setBackground(Color.LIGHT_GRAY);
                ter.setText("Term already exist");
            } else {
                Term term = new Term(txt.toLowerCase());
                pro.saveTerm(term);
                pro.findAllTerm();
            }
        }
    }

    //======== Method populate data into term comboBox
    public void setTermToCombo(JComboBox cmb, String title) {
        List<Term> term = pro.findAllTerm();
        DefaultComboBoxModel termCombo = (DefaultComboBoxModel) cmb.getModel();
        termCombo.removeAllElements();
        termCombo.addElement(title);

        for (Term ter : term) {
            termCombo.addElement(ter.getTermName());
        }
    }

    //======Term method to decide if it exist ==========
    public boolean returnClass(String txt) {
        boolean myClass = false;
        try {
            String year = "select className from Classes where className='" + txt.toLowerCase().trim() + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myClass = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myClass;
    }
    
    public String returnClassName(int txt) {
        String myClass = null;
        try {
            String year = "select className from Classes where id='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myClass = rs.getString("className");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myClass;
    }


    //============Method save term into db
    public void saveClass(JTextField cla) {
        if (cla.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {
            boolean exist = returnClass(cla.getText().trim());
            String txt = cla.getText().trim();

            if (exist == true) {
                cla.setBackground(Color.LIGHT_GRAY);
                cla.setText("Classes already exist");
            } else {
                Classess clax = new Classess(txt.toLowerCase());
                pro.saveClass(clax);
                pro.findAllClassess();
            }
        }
    }

    //======== Method populate data into term comboBox
    public void setClassToCombo(JComboBox cmb, String title) {
        List<Classess> clax = pro.findAllClassess();
        DefaultComboBoxModel claxCombo = (DefaultComboBoxModel) cmb.getModel();
        claxCombo.removeAllElements();
        claxCombo.addElement(title);

        for (Classess cla : clax) {
            claxCombo.addElement(cla.getClassName());
        }
    }

    //Counting number of students in a specific class
    public void totalStudent(JTextField txt_totStudent, int comID) {
        try {
            String sql = "Select count(completedYear_id) from Student where completedYear_id='" + comID + "' and deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String sum = rs.getString("count(completedYear_id)");
                txt_totStudent.setText(sum);
            }
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

    public int yearID(String yName) {
        int yid = 0;
        try {
            String sql = "Select id from Year where yearName='" + yName + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                yid = rs.getInt("id");
            }
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
        return yid;
    }

    public String yearName(int id) {
        String yName = null;
        try {
            String sql = "Select yearName from Year where id='" + id + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                yName = rs.getString("yearName");
            }
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
        return yName;
    }

    //method that save into program
    public void saveStudent(JTextField id, JComboBox bat, JTextField fName, JTextField mName, JTextField sName, JTextField Contact, JTextField hTown) {
        int stuID = Integer.parseInt(id.getText().trim());
        int completeID = yearID(bat.getSelectedItem().toString());
        String fname = fName.getText().trim();
        String mname = mName.getText().trim();
        String sname = sName.getText().trim();
        String contact = Contact.getText().trim();
        String htown = hTown.getText().trim();
        String batch = bat.getSelectedItem().toString();

        Student student = new Student(batch + (stuID + 1), completeID, fname, mname, sname, contact, htown);
        pro.saveStudent(student);
    }

    //======Term method to decide if it exist ==========
    public boolean returnSubject(String txt) {
        boolean mySubject = false;
        try {
            String year = "select subCode from Subject where subCode='" + txt.toLowerCase().trim() + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                mySubject = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return mySubject;
    }

    public String returnSubjectCode(int id) {
        String yName = null;
        try {
            String sql = "Select subCode from Subject where id='" + id + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                yName = rs.getString("subCode");
            }
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
        return yName;
    }
    public String returnSubjectName(int id) {
        String yName = null;
        try {
            String sql = "Select subName from Subject where id='" + id + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                yName = rs.getString("subName");
            }
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
        return yName;
    }
    

    //method that save into Subject
    public void saveSubject(JTextField code, JTextField subName) {

        boolean exist = returnSubject(code.getText().trim());
        String subcode = code.getText().toLowerCase().trim();
        String subname = subName.getText().trim();

        if (exist == true) {
            code.setBackground(Color.LIGHT_GRAY);
            code.setText("Subject code exist");
        } else {
            Subject subject = new Subject(subcode, subname);
            pro.saveSubject(subject);
        }
    }

    //method that save school information
    public void saveSchoolInfo(JTextField sname, JTextField saddress, JTextField slocation, JDateChooser svacation, JDateChooser sresume, byte[] logo) {

        String schoolname = sname.getText().toString();
        String schooladdress = saddress.getText().toString();
        String schoollocation = slocation.getText().toString();
        String schoolvacation = ((JTextField) svacation.getDateEditor().getUiComponent()).getText();
        String schoolresume = ((JTextField) sresume.getDateEditor().getUiComponent()).getText();

        if (sname.getText().isEmpty() || saddress.getText().isEmpty() || slocation.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill all required fields");
        } else {
            School school = new School(schoolname, schooladdress, schoollocation, schoolvacation, schoolresume, logo);
            pro.saveSchoolDetails(school);
        }
    }

    //======== Method populate data into term comboBox
    public void setSubjectToCombo(JComboBox cmb, String title) {
        List<Subject> sub = pro.findAllSubject();
        DefaultComboBoxModel subCombo = (DefaultComboBoxModel) cmb.getModel();
        subCombo.removeAllElements();
        subCombo.addElement(title);

        for (Subject subject : sub) {
            subCombo.addElement(subject.getScode());
        }
    }

    //Getting names of student based on batch and populating into list
    public List<Student> findAllStudentName(int name) {
        List<Student> studentName = new ArrayList<>();
        try {
            String sql = "SELECT stu_ID,fName,mName,surname FROM Student where completedYear_id='" + name + "' and deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setStuID(rs.getString("stu_ID"));
                student.setfName(rs.getString("fName"));
                student.setmName(rs.getString("mName"));
                student.setSurname(rs.getString("surname"));
                studentName.add(student);
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

        return studentName;
    }

    //Returning list based on select from list
    public List<Student> findOneStudentInfo(String info, int comID) {
        List<Student> studentinfo = new ArrayList<>();
        try {
            String sql = "select id,stu_ID,completedYear_id,fname,mname,surname,contact,hometown,(fname || ' '|| mname || ' ' || surname) as fullname from Student where fullname='" + info + "' and completedYear_id='" + comID + "' and deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStuID(rs.getString("stu_ID"));
                student.setCompleteID(rs.getInt("completedYear_id"));
                student.setfName(rs.getString("fName"));
                student.setmName(rs.getString("mName"));
                student.setSurname(rs.getString("surname"));
                student.setContact(rs.getString("contact"));
                student.sethTown(rs.getString("hometown"));
                studentinfo.add(student);
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }

        return studentinfo;
    }

    //Returning list based on select from list
    public List<Student> findStudentInfoNext(int comID) {
        List<Student> studentinfo = new ArrayList<>();
        try {
            String sql = "select * from Student where completedYear_id='" + comID + "' AND deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStuID(rs.getString("stu_ID"));
                student.setCompleteID(rs.getInt("completedYear_id"));
                student.setfName(rs.getString("fName"));
                student.setmName(rs.getString("mName"));
                student.setSurname(rs.getString("surname"));
                student.setContact(rs.getString("contact"));
                student.sethTown(rs.getString("hometown"));
                studentinfo.add(student);
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }

        return studentinfo;
    }

//  Method to update student
    public void updateStudentRecords(int id, String sid, int comID, String fn, String mn, String sn, String con, String hom) {
        Student stu = new Student(comID, fn, mn, sn, con, hom);
        pro.updateStudent(stu, id, sid);
    }

    //Getting subject to fields
    public List<Subject> findSubject(String name) {
        List<Subject> subjectName = new ArrayList<>();
        try {
            String sql = "SELECT id,subCode,subName FROM Subject where subCode='" + name + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSid(rs.getInt("id"));
                subject.setScode(rs.getString("subCode"));
                subject.setSubjectName(rs.getString("subName"));
                subjectName.add(subject);
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

        return subjectName;
    }

    //Searching by course code
    public void searchBySubjectCode(JLabel lbl, JTextField code, JTextField name, String com) {
        List<Subject> subject = findSubject(com);
        for (Subject s : subject) {
            lbl.setText("" + s.getSid());
            code.setText(s.getScode());
            name.setText(s.getSubjectName());
        }
    }

    public void updateSubjectCode(JLabel id, JTextField scode, JTextField sname) {
        int cid = Integer.parseInt(id.getText().toString());
        String code = scode.getText().trim();
        String name = sname.getText().trim();
        Subject s = new Subject(code, name);
        pro.updateSubject(s, cid);
    }

    //Setting delete feild to zero
    public void setStudentDelete(int id, String stuid, int comid) {
        int i = 0;
        try {
            String sql = "UPDATE Student SET deleted_at='" + i + "' WHERE id='" + id + "' AND stu_ID='" + stuid + "' AND completedYear_id='" + comid + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Student Details is Delete");
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

    //======== Method populate data into term comboBox
    public void setStudentNameToCombo(JComboBox cmb, String title, int com, JComboBox stuid) {
        List<Student> student = findAllStudentName(com);
        DefaultComboBoxModel stuCombo = (DefaultComboBoxModel) cmb.getModel();
        stuCombo.removeAllElements();
        stuCombo.addElement(title);

        DefaultComboBoxModel stuComboID = (DefaultComboBoxModel) stuid.getModel();
        stuComboID.removeAllElements();
        stuComboID.addElement(title);

        for (Student stu : student) {
            stuComboID.addElement(stu.getStuID());
            stuCombo.addElement(stu.getSurname().trim() + " " + stu.getmName().trim() + " " + stu.getfName().trim());
        }
    }

    //======Count total number of student==========
    public int totalStudentCount(int con) {
        int count = 0;
        try {
            String sql = "Select count(stu_ID) from Student where completedYear_id='" + con + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count(stu_ID)");
            }

        } catch (Exception e) {

        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return count;
    }

    //Returning list based on select from list
    public List<Student> findStudentInfoNextConcat(int comID) {
        List<Student> studentinfo = new ArrayList<>();
        try {
            String sql = "select stu_ID,(fname || ' '|| mname || ' ' || surname) as fullname from Student where completedYear_id='" + comID + "' AND deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setStuID(rs.getString("stu_ID"));
                student.setfName(rs.getString("fullname"));
                studentinfo.add(student);
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }
        return studentinfo;
    }

    //Returning names by search of iD
    public List<Student> findStudentByID(int comID, String id) {
        List<Student> studentinfo = new ArrayList<>();
        try {
            String sql = "select (fname || ' '|| mname || ' ' || surname) as fullname from Student where stu_ID='" + id + "' and completedYear_id='" + comID + "' AND deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setfName(rs.getString("fullname"));
                studentinfo.add(student);
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }
        return studentinfo;
    }

    //========== Returning ID ==================
    public int classID(String cName) {
        int cid = 0;
        try {
            String sql = "Select id from Classes where className='" + cName + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                cid = rs.getInt("id");
            }
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
        return cid;
    }

    public int termID(String tName) {
        int tid = 0;
        try {
            String sql = "Select id from Term where termName='" + tName + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                tid = rs.getInt("id");
            }
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
        return tid;
    }

    public int academicID(String aName) {
        int tid = 0;
        try {
            String sql = "Select id from Academic where academicYear='" + aName + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                tid = rs.getInt("id");
            }
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
        return tid;
    }
    
     public String academicName(int txt) {
        String academic = null;
        try {
            String sql = "Select academicYear from Academic where id='" + txt + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                academic = rs.getString("academicYear");
            }
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
        return academic;
    }

    public int subjectID(String sName) {
        int sid = 0;
        try {
            String sql = "Select id from Subject where subCode='" + sName + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                sid = rs.getInt("id");
            }
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
        return sid;
    }

    public String subjectName(String sName) {
        String subName = null;
        try {
            String sql = "Select subName from Subject where subCode='" + sName + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                subName = rs.getString("subName");
            }
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
        return subName;
    }

    ///===============Student Methods ==================
    public int studentID(String stuName) {
        int stuid = 0;
        try {
            String sql = "Select id from Student where stu_ID='" + stuName + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                stuid = rs.getInt("id");
            }
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
        return stuid;
    }

    public String findStudentByIDName(int id, int comy) {
        String studentinfo = null;
        try {
            String sql = "select (fname || ' '|| mname || ' ' || surname) as fullname from Student where id='" + id + "' and completedYear_id='" + comy + "' AND deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                studentinfo = rs.getString("fullname");
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }
        return studentinfo;
    }

    public String studentIDAssigned(int id, int comy) {
        String stuid = null;
        try {
            String sql = "Select stu_ID from Student where id='" + id + "' and completedYear_id='" + comy + "' AND deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                stuid = rs.getString("stu_ID");
            }
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
        return stuid;
    }

    public boolean returnRemarks(String txt) {
        boolean myYear = false;
        try {
            String year = "select grade from SchoolDetails where grade='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myYear = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myYear;
    }

    public int returnRemarksID(String txt) {
        int myID = 0;
        try {
            String year = "select id from SchoolDetails where remarks='" + txt.trim() + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myID = rs.getInt("id");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myID;
    }

    //Returning grade
    public String returnGrade(double txt) {
        String myGrade = null;
        try {
            String year = "select grade from SchoolDetails where lmarks <='" + txt + "' and hmarks >= '" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myGrade = rs.getString("grade");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myGrade;
    }

    //Returning grade
    public String returnRemarks(double txt) {
        String myRemarks = null;
        try {
            String year = "select remarks from SchoolDetails where lmarks <='" + txt + "' and hmarks >= '" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myRemarks = rs.getString("remarks");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myRemarks;
    }

    //Returning grade
    public String returnRemarksOnID(int txt) {
        String myRemarks = null;
        try {
            String year = "select remarks from SchoolDetails where id='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                myRemarks = rs.getString("remarks");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return myRemarks;
    }

    //Showing result specified
    public void resultOfStudent(JTable assTable, int ter, int aca, int sub, int yer, int clas) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);
        int count = 1, pos = 0;
        double total, tot = 0;

        Object[] object;

        List<Result> result = pro.findAllResultSpecific(ter, aca, sub, yer, clas);
        for (Result res : result) {
            total = tot;

            String studentid = studentIDAssigned(res.getStuId(), res.getYear_id());
            String fullname = findStudentByIDName(res.getStuId(), res.getYear_id());
            double assessment = res.getAssessment();
            double exams = res.getExams();
            tot = res.getTotalResult();
            String grade = res.getGrade();
            String remarks = returnRemarksOnID(res.getRemarks());
            //JOptionPane.showMessageDialog(null, tot + "=" + total);

            if (tot > total) {
                pos = count;
            } else if (tot == total) {
                pos = pos;
                count = pos + 1;
            } else {
                pos = count;
            }

            object = new Object[]{studentid, fullname.trim(), assessment, exams, tot, grade, pos, remarks};
            tmodel.addRow(object);

            count++;

        }
    }

    //Showing assessment specified
    public void examsOfStudent(JTable assTable, int ter, int aca, int sub, int yer, int clas) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;

        List<Exams> examses = pro.findAllExams(ter, aca, sub, yer, clas);
        for (Exams exe : examses) {

            String studentid = studentIDAssigned(exe.getStuId(), exe.getYear_id());
            String fullname = findStudentByIDName(exe.getStuId(), exe.getYear_id());
            double obj = exe.getObjectives();
            double theory = exe.getTheory();
            double tot = exe.getTotalExams();
            double percent = exe.getFiftyPercentExams();

            object = new Object[]{studentid, fullname.trim(), obj, theory, tot, percent};
            tmodel.addRow(object);

        }
    }

    //Showing assessment specified
    public void assessmentOfStudent(JTable assTable, int ter, int aca, int sub, int yer, int clas) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;

        List<Assessment> assessment = pro.findAllAssessment(ter, aca, sub, yer, clas);
        for (Assessment ass : assessment) {

            String studentid = studentIDAssigned(ass.getStuId(), ass.getYear_id());
            String fullname = findStudentByIDName(ass.getStuId(), ass.getYear_id());
            double classText = ass.getClassTest();
            double others = ass.getOthers();
            double tot = ass.getTotal();
            double percent = ass.getFiftyPercentAssessment();

            object = new Object[]{studentid, fullname.trim(), classText, others, tot, percent};
            tmodel.addRow(object);

        }
    }

    //Returning grade
    public boolean compareResult(int sid, int tid, int aid, int suid, int yid, int cid) {
        boolean result = false;
        try {
            String year = "select stuID,term_id,academic_id,subject_id,year_id,class_id from result where stuID='" + sid + "' and term_id='" + tid + "' and academic_id='" + aid + "' and subject_id='" + suid + "' and year_id='" + yid + "' and class_id='" + cid + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                result = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return result;
    }

    public List<Result> resultsListToPopulate(int sid, int tid, int aid, int suid, int yid, int cid) {
        List<Result> resultList = new ArrayList<>();
        try {
            String year = "select * from result where stuID='" + sid + "' and term_id='" + tid + "' and academic_id='" + aid + "' and subject_id='" + suid + "' and year_id='" + yid + "' and class_id='" + cid + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                result.setRid(rs.getInt("id"));
                result.setStuId(rs.getInt("stuID"));
                result.setAssessment(rs.getDouble("assessment"));
                result.setExams(rs.getDouble("exams"));
                result.setTotalResult(rs.getDouble("total"));
                result.setGrade(rs.getString("grade"));
                result.setRemarks(rs.getInt("remarks"));
                resultList.add(result);
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
        return resultList;
    }

    //Selecting tom exams table with condition
    public List<Exams> examsListToPopulate(int sid, int tid, int aid, int suid, int yid, int cid) {
        List<Exams> examsList = new ArrayList<>();
        try {
            String year = "select * from Exams where stuID='" + sid + "' and term_id='" + tid + "' and academic_id='" + aid + "' and subject_id='" + suid + "' and year_id='" + yid + "' and class_id='" + cid + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            while (rs.next()) {
                Exams exams = new Exams();
                exams.setEid(rs.getInt("id"));
                exams.setObjectives(rs.getDouble("objectives"));
                exams.setTheory(rs.getDouble("theory"));
                exams.setTotalExams(rs.getDouble("totalExams"));
                examsList.add(exams);
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
        return examsList;
    }

    //Selecting tom exams table with condition
    public List<Assessment> assessListToPopulate(int sid, int tid, int aid, int suid, int yid, int cid) {
        List<Assessment> assessList = new ArrayList<>();
        try {
            String year = "select * from Assessment where stuID='" + sid + "' and term_id='" + tid + "' and academic_id='" + aid + "' and subject_id='" + suid + "' and year_id='" + yid + "' and class_id='" + cid + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            while (rs.next()) {
                Assessment assess = new Assessment();
                assess.setAid(rs.getInt("id"));
                assess.setClassTest(rs.getDouble("classTest"));
                assess.setOthers(rs.getDouble("others"));
                assess.setTotal(rs.getDouble("totalAssess"));
                assessList.add(assess);
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
        return assessList;
    }

    public List<Result> reportStudent(int tid, int aid, int yid, int cid) {
        List<Result> resultList = new ArrayList<>();
      
        try {
            String year = "select * from result where term_id='" + tid + "' and academic_id='" + aid + "' and year_id='" + yid + "' and class_id='" + cid + "' GROUP BY stuID";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                result.setStuId(rs.getInt("stuID"));
                result.setYear_id(rs.getInt("year_id"));
                result.setAssessment(rs.getDouble("assessment"));
                result.setExams(rs.getDouble("exams"));
                result.setTotalResult(rs.getDouble("total"));
                result.setGrade(rs.getString("grade"));
                result.setRemarks(rs.getInt("remarks"));
                resultList.add(result);
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
        return resultList;
    }

    public List<Result> subjectIDReport(int tid, int aid, int yid, int cid) {
        List<Result> resultList = new ArrayList<>();
        Result result = new Result();
        try {
            String year = "select subject_id from result where term_id='" + tid + "' and academic_id='" + aid + "' and year_id='" + yid + "' and class_id='" + cid + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            while (rs.next()) {
                result.setSubject_id(rs.getInt("subject_id"));
                resultList.add(result);
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
        return resultList;
    }
    
    public List<Result> reportSubject(int tid, int aid, int yid, int cid) {
        List<Result> resultList = new ArrayList<>();
       
        try {
            String year = "select * from result where term_id='" + tid + "' and academic_id='" + aid + "' and year_id='" + yid + "' and class_id='" + cid + "' GROUP BY subject_id";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                result.setSubject_id(rs.getInt("subject_id"));
                result.setTotalResult(rs.getDouble("total"));
                resultList.add(result);
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
        return resultList;
    }
    
    
    public List<Result> reportMarks(int tid, int aid, int yid, int cid, int mark) {
        List<Result> resultList = new ArrayList<>();
       
        try {
            String year = "select total from result where term_id='" + tid + "' and academic_id='" + aid + "' and year_id='" + yid + "' and class_id='" + cid + "' and subject_id='"+ mark +"'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                result.setTotalResult(rs.getDouble("total"));
                resultList.add(result);
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
        return resultList;
    }
    
  
    //Showing assessment specified
    public void studentReportTable(JTable assTable, int tid, int aid, int yid, int cid) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);
        tmodel.setColumnCount(0);
    
        Object[] object = null;
     
        List<Result> report = reportSubject(tid, aid, yid, cid);
        List<Result> student = reportStudent(tid, aid, yid, cid);
      
        tmodel.addColumn("ID");
        tmodel.addColumn("Name");

        for(Result res : report) {
            
            String subject = returnSubjectCode(res.getSubject_id());
          
            
            List<Result> stu = reportMarks(tid, aid, yid, cid, res.getSubject_id());   
            
            for (Result s : stu) {
                double total = s.getTotalResult() ; 
                object = new Object[]{total};
                tmodel.addRow(object);
            }
          
            tmodel.addColumn(subject.toUpperCase()); 
           
        }
       
        tmodel.addColumn("Total");
        
        for(Result res : student) {
           
            String name = findStudentByIDName(res.getStuId(), res.getYear_id());
            String studentid = studentIDAssigned(res.getStuId(), res.getYear_id());
            double tot = res.getTotalResult();
            
            object = new Object[]{studentid, name, tot};
            tmodel.addRow(object);
        }
        
    }
    
   
//    Printing the reports
    //Showing result specified
    public void resultOfStudentReport(JTable assTable, int ter, int aca, int sub, int yer, int clas) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();

        int count = 1, pos = 0;
        double total, tot = 0;

        Object[] object;
        tmodel.setRowCount(0);

        tmodel.addColumn("ID");
        tmodel.addColumn("Name");
        tmodel.addColumn("Class Score");
        tmodel.addColumn("Exams Score");
        tmodel.addColumn("Total");
        tmodel.addColumn("Grade");
        tmodel.addColumn("Position");
        tmodel.addColumn("Remarks");

        List<Result> result = pro.findAllResultSpecific(ter, aca, sub, yer, clas);
        for (Result res : result) {
            total = tot;

            String studentid = studentIDAssigned(res.getStuId(), res.getYear_id());
            String fullname = findStudentByIDName(res.getStuId(), res.getYear_id());
            double assessment = res.getAssessment();
            double exams = res.getExams();
            tot = res.getTotalResult();
            String grade = res.getGrade();
            String remarks = returnRemarksOnID(res.getRemarks());
            //JOptionPane.showMessageDialog(null, tot + "=" + total);

            if (tot > total) {
                pos = count;
            } else if (tot == total) {
                pos = pos;
                count = pos + 1;
            } else {
                pos = count;
            }

            object = new Object[]{studentid.toString(), fullname.trim().toString(), assessment, exams, tot, grade, pos, remarks};
            tmodel.addRow(object);

            count++;

        }
    }

    //Showing assessment specified
    public void assessmentOfStudentReport(JTable assTable, int ter, int aca, int sub, int yer, int clas) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;

        tmodel.addColumn("ID");
        tmodel.addColumn("Fullname");
        tmodel.addColumn("Class Text");
        tmodel.addColumn("Others");
        tmodel.addColumn("Total");
        tmodel.addColumn("Percent");

        List<Assessment> assessment = pro.findAllAssessment(ter, aca, sub, yer, clas);
        for (Assessment ass : assessment) {

            String studentid = studentIDAssigned(ass.getStuId(), ass.getYear_id());
            String fullname = findStudentByIDName(ass.getStuId(), ass.getYear_id());
            double classText = ass.getClassTest();
            double others = ass.getOthers();
            double tot = ass.getTotal();
            double percent = ass.getFiftyPercentAssessment();

            object = new Object[]{studentid, fullname.trim(), classText, others, tot, percent};
            tmodel.addRow(object);

        }
    }

    //Showing assessment specified
    public void examsOfStudentReport(JTable assTable, int ter, int aca, int sub, int yer, int clas) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;

        tmodel.addColumn("ID");
        tmodel.addColumn("Fullname");
        tmodel.addColumn("Objectives");
        tmodel.addColumn("Theory");
        tmodel.addColumn("Total");
        tmodel.addColumn("Percent");

        List<Exams> examses = pro.findAllExams(ter, aca, sub, yer, clas);
        for (Exams exe : examses) {

            String studentid = studentIDAssigned(exe.getStuId(), exe.getYear_id());
            String fullname = findStudentByIDName(exe.getStuId(), exe.getYear_id());
            double obj = exe.getObjectives();
            double theory = exe.getTheory();
            double tot = exe.getTotalExams();
            double percent = exe.getFiftyPercentExams();

            object = new Object[]{studentid, fullname.trim(), obj, theory, tot, percent};
            tmodel.addRow(object);

        }
    }

    //Showing assessment specified
    public void remarksPopulate(JTable assTable) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;

        List<SchoolRemarks> remarks = pro.findAllSchoolRemaks();
        for (SchoolRemarks rem : remarks) {

            int id = rem.getId();
            double low = rem.getLowMarks();
            double high = rem.getHigMarks();
            String grade = rem.getGrd();
            String remark = rem.getRem();

            object = new Object[]{id, low, high, grade, remark};
            tmodel.addRow(object);

        }
    }

    //Showing assessment specified
    public void schoolDetails(JTable assTable, JLabel log) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;
        ImageIcon format = null;

        List<School> school = pro.findAllSchoolDetails();
        for (School sch : school) {
            int id = sch.getSchoolid();
            String schoolName = sch.getSchoolName();
            String schoolAddress = sch.getSchoolAddress();
            String schoolLoc = sch.getSchoolLocation();
            String schoolVac = sch.getSchoolVac();
            String schoolResume = sch.getSchoolResume();
            byte[] imagedata = sch.getSchoolLogo();
            format = new ImageIcon(imagedata);
            log.setIcon(format);

            object = new Object[]{id, schoolName, schoolAddress, schoolLoc, schoolVac, schoolResume};
            tmodel.addRow(object);
        }

    }

    public void updateSchoolDetails(int id, JTextField sname, JTextField saddress, JTextField slocation, JDateChooser svacation, JDateChooser sresume) {

        String schoolname = sname.getText().toString();
        String schooladdress = saddress.getText().toString();
        String schoollocation = slocation.getText().toString();
        String schoolvacation = ((JTextField) svacation.getDateEditor().getUiComponent()).getText();
        String schoolresume = ((JTextField) sresume.getDateEditor().getUiComponent()).getText();

        School school = new School(schoolname, schooladdress, schoollocation, schoolvacation, schoolresume);

        pro.updateSchoolDetails(school, id);
    }

    public List<School> findAllSchoolDetailsWithID(int id) {
        List<School> listSchool = new ArrayList<>();
        try {
            String sql = "SELECT * FROM SchoolInfo WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                School sch = new School();

                sch.setSchoolId(rs.getInt("id"));
                sch.setSchoolName(rs.getString("schoolName"));
                sch.setSchoolAddress(rs.getString("schoolAddress"));
                sch.setSchoolLocation(rs.getString("schoolLocation"));
                sch.setSchoolVac(rs.getString("schoolVacation"));
                sch.setSchoolResume(rs.getString("schoolResume"));
                sch.setSchoolLogo(rs.getBytes("schoolLogo"));

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

    public List<SchoolRemarks> findRemarksWithID(int id) {
        List<SchoolRemarks> listRemarks = new ArrayList<>();
        try {
            String sql = "SELECT * FROM SchoolDetails WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                SchoolRemarks rmk = new SchoolRemarks();

                rmk.setId(rs.getInt("id"));
                rmk.setLowMarks(rs.getDouble("lmarks"));
                rmk.setHigMarks(rs.getDouble("hmarks"));
                rmk.setGrd(rs.getString("grade"));
                rmk.setRem(rs.getString("remarks"));

                listRemarks.add(rmk);
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

    //Returning student id when Name and completed year is entered
    public int findStudentReport(String info, int comID) {
        int stuId = 0;
        try {
            String sql = "select id,(surname || ' '|| mname || ' ' || fname) as fullname from Student where fullname='" + info.toUpperCase() + "' and completedYear_id='" + comID + "' and deleted_at = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                stuId = rs.getInt("id");
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return stuId;
    }

    public List<Result> studentPerformance(int id) {
        List<Result> stuPerformance = new ArrayList<>();
        try {

            String per = "Select * from Result where stuID='" + id + "'";
            pst = conn.prepareStatement(per);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                
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

               stuPerformance.add(result);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        return stuPerformance;
    }

}

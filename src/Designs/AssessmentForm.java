package Designs;

import Classes.Academic;
import Classes.Assessment;
import Classes.Exams;
import Classes.Result;
import Classes.Student;
import Classes.Subject;
import Classes.Term;
import Classes.Year;
import Connects.ProgramDAO;
import Connects.logicHandler;
import Connects.myCon;
import Connects.myLogics;
import com.sun.glass.events.KeyEvent;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class AssessmentForm extends javax.swing.JFrame {

    myCon mets = new myCon();
    Vector originalTableModel;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    ProgramDAO pro = new ProgramDAO();
    myLogics mylogics = new myLogics();
    DecimalFormat df2 = new DecimalFormat("####.#");
    logicHandler lhand = new logicHandler();

    int pos = 0;
    int rep = 0;
    int exa = 0;
    int ass = 0;
    List<Exams> listExams = new ArrayList<>();
    List<Result> listResult = new ArrayList<>();
    List<Assessment> listAssessment = new ArrayList<>();

    int stuID, terID, acaID, subjID, yearID, clasID, remarksid;
    String t1, t2, t3, t4, grade;
    double ctext, others, total, fpercent;

    public AssessmentForm() {
        initComponents();
        conn = myCon.ConnecrDb();

        //setExtendedState(MAXIMIZED_BOTH);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        setIconImage(mets.myImage("/icons/globe.png"));

        originalTableModel = (Vector) ((DefaultTableModel) Assessment_Table.getModel()).getDataVector().clone();
        AssessmentColors();
        allClassess();
        mets.StartTim(lbl_Date, lbl_Time);

        lbl_result.setVisible(false);
        lbl_assess.setVisible(false);
        lbl_exams.setVisible(false);

        progress_save.setVisible(false);
    }

    public void AssessmentColors() {
        Assessment_Table.getColumnModel().getColumn(0).setCellRenderer(new myCon.CustomRenderer());
        Assessment_Table.getColumnModel().getColumn(1).setCellRenderer(new myCon.CustomRenderer1());
        Assessment_Table.getColumnModel().getColumn(2).setCellRenderer(new myCon.CustomRenderer2());
        Assessment_Table.getColumnModel().getColumn(3).setCellRenderer(new myCon.CustomRenderer3());
        Assessment_Table.getColumnModel().getColumn(4).setCellRenderer(new myCon.CustomRenderer4());
        Assessment_Table.getColumnModel().getColumn(5).setCellRenderer(new myCon.CustomRenderer5());
        Assessment_Table.getColumnModel().getColumn(6).setCellRenderer(new myCon.CustomRenderer6());
        Assessment_Table.getColumnModel().getColumn(7).setCellRenderer(new myCon.CustomRenderer7());
        Assessment_Table.getTableHeader().setDefaultRenderer(new myCon.HeaderColor());

        class_Table.getColumnModel().getColumn(0).setCellRenderer(new myCon.CustomRenderer());
        class_Table.getColumnModel().getColumn(1).setCellRenderer(new myCon.CustomRenderer1());
        class_Table.getColumnModel().getColumn(2).setCellRenderer(new myCon.CustomRenderer2());
        class_Table.getColumnModel().getColumn(3).setCellRenderer(new myCon.CustomRenderer3());
        class_Table.getColumnModel().getColumn(4).setCellRenderer(new myCon.CustomRenderer4());
        class_Table.getColumnModel().getColumn(5).setCellRenderer(new myCon.CustomRenderer5());
        class_Table.getTableHeader().setDefaultRenderer(new myCon.HeaderColor());

        exams_Table.getColumnModel().getColumn(0).setCellRenderer(new myCon.CustomRenderer());
        exams_Table.getColumnModel().getColumn(1).setCellRenderer(new myCon.CustomRenderer1());
        exams_Table.getColumnModel().getColumn(2).setCellRenderer(new myCon.CustomRenderer2());
        exams_Table.getColumnModel().getColumn(3).setCellRenderer(new myCon.CustomRenderer3());
        exams_Table.getColumnModel().getColumn(4).setCellRenderer(new myCon.CustomRenderer4());
        exams_Table.getColumnModel().getColumn(5).setCellRenderer(new myCon.CustomRenderer5());
        exams_Table.getTableHeader().setDefaultRenderer(new myCon.HeaderColor());
    }

    public void studentName(JComboBox myComboNam) {
        if (myComboNam.getSelectedIndex() <= 0) {

        } else {
            mylogics.setStudentNameToCombo(cmd_fullname, "--Select--", mylogics.yearID(myComboNam.getSelectedItem().toString()), cmd_StuID);
            mylogics.setStudentNameOnlyCombo(cmd_searchStudent, "--Select Name to Search--", mylogics.yearID(myComboNam.getSelectedItem().toString()));
        }
    }

    public void allClassess() {
        mylogics.setClassToCombo(cmd_Class, "--Select Class---");
        mylogics.setAcademicToCombo(cmb_Academic, "Select Academic Year");
        mylogics.setTermToCombo(cmd_Term, "Select Term");
        mylogics.setSubjectToCombo(cmb_Subject, "Select Subjects");
        mylogics.setYearToCombo(cmd_Batch, "Select Year Batch");
    }

    public void showNext(int index) {
        List<Student> student = mylogics.findStudentInfoNextConcat(mylogics.yearID(cmd_Batch.getSelectedItem().toString()));
        cmd_StuID.setSelectedItem(student.get(index).getStuID());
        String fullname = student.get(index).getfName();
        cmd_fullname.setSelectedItem(fullname.toString());
    }

    public void resultConditionNext(int index) {
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Result> result = mylogics.resultsListToPopulate(mylogics.studentID(cmd_StuID.getSelectedItem().toString()), terID, acaID, subjID, yearID, clasID);

        if (index <= 0) {
            //newRecords();
        } else {
            lbl_result.setText("" + result.get(index).getRid());
            txt_fpercent.setText("" + result.get(index).getAssessment());
            txt_spercent.setText("" + result.get(index).getExams());
            txt_GrandTotal.setText(" " + result.get(index).getTotalResult());
            lbl_Grade.setText(result.get(index).getGrade());
            lbl_Grade1.setText(mylogics.returnRemarksOnID(result.get(index).getRemarks()));
        }
    }

    public void examsConditionNext(int index) {
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Exams> exam = mylogics.examsListToPopulate(mylogics.studentID(cmd_StuID.getSelectedItem().toString()), terID, acaID, subjID, yearID, clasID);

        if (index <= 0) {
            // JOptionPane.showMessageDialog(null, "End");
        } else {
            txt_EOBJ.setText("" + exam.get(index).getObjectives());
            txt_Theory.setText("" + exam.get(index).getTheory());
            txt_totalE.setText("" + exam.get(index).getTotalExams());
        }

    }

    public void assessConditionNext(int index) {
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Assessment> assess = mylogics.assessListToPopulate(mylogics.studentID(cmd_StuID.getSelectedItem().toString()), terID, acaID, subjID, yearID, clasID);

        if (index <= 0) {
            //JOptionPane.showMessageDialog(null, "End");
        } else {
            txt_CTest.setText("" + assess.get(index).getClassTest());
            txt_Others.setText("" + assess.get(index).getOthers());
            txt_totalQ.setText("" + assess.get(index).getTotal());
        }
    }

    // Method to go next
    public void btnNext() {
        pos++;
        List<Student> student = mylogics.findStudentInfoNextConcat(mylogics.yearID(cmd_Batch.getSelectedItem().toString()));
        if (pos < student.size()) {
            showNext(pos);
        } else {
            pos = student.size() - 1;
            showNext(pos);
            JOptionPane.showMessageDialog(null, "End of List");
        }
    }

    public void btnNextResult() {
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        rep++;
        List<Result> result = mylogics.resultsListToPopulate(mylogics.studentID(cmd_StuID.getSelectedItem().toString()), terID, acaID, subjID, yearID, clasID);
        if (rep < result.size()) {
            resultConditionNext(rep);
        } else {
            rep = result.size() - 1;
            resultConditionNext(rep);
        }
    }

    public void btnNextExam() {
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        exa++;
        List<Exams> exam = mylogics.examsListToPopulate(mylogics.studentID(cmd_StuID.getSelectedItem().toString()), terID, acaID, subjID, yearID, clasID);
        if (exa < exam.size()) {
            examsConditionNext(exa);
        } else {
            exa = exam.size() - 1;
            examsConditionNext(exa);
        }
    }

    public void btnNextAssess() {
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        ass++;
        List<Exams> exam = mylogics.examsListToPopulate(mylogics.studentID(cmd_StuID.getSelectedItem().toString()), terID, acaID, subjID, yearID, clasID);
        if (ass < exam.size()) {
            assessConditionNext(ass);
        } else {
            ass = exam.size() - 1;
            assessConditionNext(ass);
        }
    }

    public void btnPreviousResult() {
        rep--;
        if (rep > 0) {
            resultConditionNext(rep);
        } else {
            rep = 0;
            resultConditionNext(rep);
        }
    }

    public void btnPreviousExams() {
        exa--;
        if (exa > 0) {
            examsConditionNext(exa);
        } else {
            exa = 0;
            examsConditionNext(exa);
        }
    }

    public void btnPreviousAssess() {
        ass--;
        if (ass > 0) {
            assessConditionNext(ass);
        } else {
            ass = 0;
            assessConditionNext(ass);
        }
    }

    //Method to go previous
    public void btnPrevious() {
        pos--;
        if (pos > 0) {
            showNext(pos);
        } else {
            pos = 0;
            showNext(pos);
            JOptionPane.showMessageDialog(null, "Begins");
        }
    }

    public void setStudentNameToCombo(JComboBox cmb) {
        List<Student> student = mylogics.findStudentByID(mylogics.yearID(cmd_Batch.getSelectedItem().toString()), cmd_StuID.getSelectedItem().toString());
        DefaultComboBoxModel stuCombo = (DefaultComboBoxModel) cmb.getModel();

        for (Student stu : student) {
            stuCombo.setSelectedItem(stu.getfName());
        }
    }

    public void setStudentIDToCombo(JComboBox cmb) {
        List<Student> student = mylogics.findStudentIDByName(mylogics.yearID(cmd_Batch.getSelectedItem().toString()), cmd_fullname.getSelectedItem().toString());
        DefaultComboBoxModel stuCombo = (DefaultComboBoxModel) cmb.getModel();

        for (Student stu : student) {
            stuCombo.setSelectedItem(stu.getStuID());
        }
    }

    public void allResultSpecific() {
        int term = mylogics.termID(cmd_Term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int subj = mylogics.subjectID(cmb_Subject.getSelectedItem().toString().trim());
        int year = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_Class.getSelectedItem().toString().trim());

        mylogics.resultOfStudent(Assessment_Table, term, aca, subj, year, clas);
        mylogics.assessmentOfStudent(class_Table, term, aca, subj, year, clas);
        mylogics.examsOfStudent(exams_Table, term, aca, subj, year, clas);
    }

    //============Saving into Assessment DB
    public void addAssessment() {

        stuID = mylogics.studentID(cmd_StuID.getSelectedItem().toString());
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        t1 = txt_CTest.getText().trim();
        t2 = txt_Others.getText().trim();
        t3 = txt_totalQ.getText().trim();
        t4 = txt_fpercent.getText().trim();

        if (t1.isEmpty()) {
            t1 = "0";
        }
        if (t2.isEmpty()) {
            t2 = "0";
        }
        if (t3.isEmpty()) {
            t3 = "0";
        }
        if (t4.isEmpty()) {
            t4 = "0";
        }

        ctext = Double.parseDouble(t1);
        others = Double.parseDouble(t2);
        total = Double.parseDouble(t3);
        fpercent = Double.parseDouble(t4);

        Assessment assess = new Assessment(stuID, terID, acaID, subjID, yearID, clasID, ctext, others, total, fpercent);
        listAssessment.add(assess);

    }

    public void addExams() {

        stuID = mylogics.studentID(cmd_StuID.getSelectedItem().toString());
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        t1 = txt_EOBJ.getText().trim();
        t2 = txt_Theory.getText().trim();
        t3 = txt_totalE.getText().trim();
        t4 = txt_spercent.getText().trim();

        if (t1.isEmpty()) {
            t1 = "0";
        }
        if (t2.isEmpty()) {
            t2 = "0";
        }
        if (t3.isEmpty()) {
            t3 = "0";
        }
        if (t4.isEmpty()) {
            t4 = "0";
        }

        ctext = Double.parseDouble(t1);
        others = Double.parseDouble(t2);
        total = Double.parseDouble(t3);
        fpercent = Double.parseDouble(t4);

        Exams exams = new Exams(stuID, terID, acaID, subjID, yearID, clasID, ctext, others, total, fpercent);
        listExams.add(exams);

    }

    public void addResult() {

        stuID = mylogics.studentID(cmd_StuID.getSelectedItem().toString());
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());
        remarksid = mylogics.returnRemarksID(lbl_Grade1.getText());

        t1 = txt_fpercent.getText().trim();
        t2 = txt_spercent.getText().trim();
        t3 = txt_GrandTotal.getText().trim();
        grade = lbl_Grade.getText().trim();

        if (t1.isEmpty()) {
            t1 = "0";
        }
        if (t2.isEmpty()) {
            t2 = "0";
        }
        if (t3.isEmpty()) {
            t3 = "0";
        }

        ctext = Double.parseDouble(t1);
        others = Double.parseDouble(t2);
        total = Double.parseDouble(t3);

        Result result = new Result(stuID, terID, acaID, subjID, yearID, clasID, others, ctext, total, grade, remarksid);
        listResult.add(result);

    }

    public void saveQuiz() {
        stuID = mylogics.studentID(cmd_StuID.getSelectedItem().toString());
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        t1 = txt_CTest.getText().trim();
        t2 = txt_Others.getText().trim();
        t3 = txt_totalQ.getText().trim();
        t4 = txt_fpercent.getText().trim();

        if (t1.isEmpty()) {
            t1 = "0";
        }
        if (t2.isEmpty()) {
            t2 = "0";
        }
        if (t3.isEmpty()) {
            t3 = "0";
        }
        if (t4.isEmpty()) {
            t4 = "0";
        }

        ctext = Double.parseDouble(t1);
        others = Double.parseDouble(t2);
        total = Double.parseDouble(t3);
        fpercent = Double.parseDouble(t4);

        if (txt_CTest.getText().isEmpty() && txt_Others.getText().isEmpty() && txt_totalQ.getText().isEmpty() && txt_fpercent.getText().isEmpty()) {

            for (int i = 0; i < listAssessment.size(); i++) {
                Assessment ass = new Assessment();
                ass = listAssessment.get(i);
                pro.saveAssessment(ass);
            }

        } else {

            for (int i = 0; i < listAssessment.size(); i++) {
                Assessment ass = new Assessment();
                ass = listAssessment.get(i);
                pro.saveAssessment(ass);
            }
            Assessment assess = new Assessment(stuID, terID, acaID, subjID, yearID, clasID, ctext, others, total, fpercent);
            pro.saveAssessment(assess);
        }
        listAssessment.clear();
    }

    public void saveExams() {

        stuID = mylogics.studentID(cmd_StuID.getSelectedItem().toString());
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        t1 = txt_EOBJ.getText().trim();
        t2 = txt_Theory.getText().trim();
        t3 = txt_totalE.getText().trim();
        t4 = txt_spercent.getText().trim();

        if (t1.isEmpty()) {
            t1 = "0";
        }
        if (t2.isEmpty()) {
            t2 = "0";
        }
        if (t3.isEmpty()) {
            t3 = "0";
        }
        if (t4.isEmpty()) {
            t4 = "0";
        }

        ctext = Double.parseDouble(t1);
        others = Double.parseDouble(t2);
        total = Double.parseDouble(t3);
        fpercent = Double.parseDouble(t4);

        if (txt_EOBJ.getText().isEmpty() && txt_Theory.getText().isEmpty() && txt_totalE.getText().isEmpty() && txt_spercent.getText().isEmpty()) {

            for (int ex = 0; ex < listExams.size(); ex++) {
                Exams exe = new Exams();
                exe = listExams.get(ex);
                pro.saveExams(exe);
            }

        } else {

            for (int ex = 0; ex < listExams.size(); ex++) {
                Exams exe = new Exams();
                exe = listExams.get(ex);
                pro.saveExams(exe);
            }

            Exams exams = new Exams(stuID, terID, acaID, subjID, yearID, clasID, ctext, others, total, fpercent);
            pro.saveExams(exams);

        }

        listExams.clear();

    }

    public void saveResult() {

        stuID = mylogics.studentID(cmd_StuID.getSelectedItem().toString());
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());
        remarksid = mylogics.returnRemarksID(lbl_Grade1.getText());

        t1 = txt_fpercent.getText().trim();
        t2 = txt_spercent.getText().trim();
        t3 = txt_GrandTotal.getText().trim();
        grade = lbl_Grade.getText().trim();

        if (t1.isEmpty()) {
            t1 = "0";
        }
        if (t2.isEmpty()) {
            t2 = "0";
        }
        if (t3.isEmpty()) {
            t3 = "0";
        }

        ctext = Double.parseDouble(t1);
        others = Double.parseDouble(t2);
        total = Double.parseDouble(t3);

        if (txt_fpercent.getText().isEmpty() && txt_spercent.getText().isEmpty() && txt_GrandTotal.getText().isEmpty()) {

            for (int ex = 0; ex < listResult.size(); ex++) {
                try {
                    Result res = new Result();
                    res = listResult.get(ex);
                    pro.saveResult(res);
                    progress_save.setValue(ex);
                    progress_save.setMaximum(listResult.size() + 1);
                    Thread.sleep(50);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(AssessmentForm.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }

        } else {

            for (int ex = 0; ex < listResult.size(); ex++) {
                try {
                    Result res = new Result();
                    res = listResult.get(ex);
                    pro.saveResult(res);

                    progress_save.setValue(ex);
                    progress_save.setMaximum(listResult.size() + 1);
                    Thread.sleep(50);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(AssessmentForm.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }

            Result result = new Result(stuID, terID, acaID, subjID, yearID, clasID, others, ctext, total, grade, remarksid);
            pro.saveResult(result);
        }

        listResult.clear();

    }

    public void saveAllAssessment() {

        stuID = mylogics.studentID(cmd_StuID.getSelectedItem().toString());
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        int remarksid = mylogics.returnRemarksID(lbl_Grade1.getText());

        double clastext = 0, otherscors = 0, totalQuize = 0, fiftyQuize = 0;
        double exeObj = 0, oexeTheory = 0, exeTotal = 0, exeFiftyPercent = 0;
        double fifPercentQ = 0, fifPercentE = 0, grandTotal = 0, percentf = 0, percentff = 0;

        String fifPer = txt_fpercent.getText().trim();
        String sisPer = txt_spercent.getText().trim();
        String grandTot = txt_GrandTotal.getText().trim();
        String fGrade = lbl_Grade.getText().trim();

        String eobj = txt_EOBJ.getText().trim();
        String etheory = txt_Theory.getText().trim();
        String etot = txt_totalE.getText().trim();
        String ePercent = txt_spercent.getText().trim();

        String cTest = txt_CTest.getText().trim();
        String cOther = txt_Others.getText().trim();
        String ctotQuize = txt_totalQ.getText().trim();
        String cpercent = txt_fpercent.getText().trim();

        //Conditions on results
        if (fifPer.isEmpty()) {
            fifPer = "0";
        }
        if (sisPer.isEmpty()) {
            sisPer = "0";
        }
        if (grandTot.isEmpty()) {
            grandTot = "0";
        }
        if (fGrade.isEmpty()) {
            fGrade = "Not Set";
        }

        //Conditions on exams
        if (eobj.isEmpty()) {
            eobj = "0";
        }
        if (etheory.isEmpty()) {
            etheory = "0";
        }
        if (etot.isEmpty()) {
            etot = "0";
        }
        if (ePercent.isEmpty()) {
            ePercent = "0";
        }

        //Conditions on assessment or class performance
        if (cTest.isEmpty()) {
            cTest = "0";
        }
        if (cOther.isEmpty()) {
            cOther = "0";
        }
        if (ctotQuize.isEmpty()) {
            ctotQuize = "0";
        }
        if (cpercent.isEmpty()) {
            cpercent = "0";
        }

        //getting results
        fifPercentQ = Double.parseDouble(fifPer);
        fifPercentE = Double.parseDouble(sisPer);
        grandTotal = Double.parseDouble(grandTot);

        //Getting assessment
        clastext = Double.parseDouble(cTest);
        otherscors = Double.parseDouble(cOther);
        totalQuize = Double.parseDouble(ctotQuize);
        fiftyQuize = Double.parseDouble(cpercent);

        //Getting Exams scores
        exeObj = Double.parseDouble(eobj);
        oexeTheory = Double.parseDouble(etheory);
        exeTotal = Double.parseDouble(etot);
        exeFiftyPercent = Double.parseDouble(ePercent);

        if (mylogics.compareResult(stuID, terID, acaID, subjID, yearID, clasID) == true) {
            JOptionPane.showMessageDialog(null, "Student Assessment Exist");
        } else {

            if (listResult.size() <= 0) {

                Exams exams = new Exams(stuID, terID, acaID, subjID, yearID, clasID, exeObj, oexeTheory, exeTotal, exeFiftyPercent);
                Assessment assess = new Assessment(stuID, terID, acaID, subjID, yearID, clasID, clastext, otherscors, totalQuize, fiftyQuize);
                Result result = new Result(stuID, terID, acaID, subjID, yearID, clasID, fifPercentE, fifPercentQ, grandTotal, fGrade, remarksid);

                pro.saveResult(result);
                pro.saveExams(exams);
                pro.saveAssessment(assess);

                showInTable();
                JOptionPane.showMessageDialog(null, "Saved");
            } else {
                progress_save.setVisible(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            for (int ex = 0; ex < listResult.size(); ex++) {
                                saveQuiz();
                                saveExams();
                                saveResult();

                                if (ex == listResult.size()) {
                                    showInTable();
                                    JOptionPane.showMessageDialog(null, "Saved");
                                    progress_save.setVisible(false);
                                }
                            }

                        } catch (Exception e) {
                        }
                    }
                }).start();
            }

        }
    }

    //Method that add two values
    public double addTwoValues(JTextField val1, JTextField val2) {
        double total;
        double v1, v2;
        String val = val1.getText().trim();
        String vall = val2.getText().trim();

        if (val.isEmpty()) {
            val = "0";
        }
        if (vall.isEmpty()) {
            vall = "0";
        }

        v1 = Double.parseDouble(val);
        v2 = Double.parseDouble(vall);
        total = Double.valueOf(df2.format(v1 + v2));

        return total;
    }

    //Method that add more values
    public double addMoreAValues(JTextField val1, JTextField val2) {
        double total;
        double v1, v2;
        String va1 = val1.getText().trim();
        String va2 = val2.getText().trim();

        if (va1.isEmpty()) {
            va1 = "0";
        }
        if (va2.isEmpty()) {
            va2 = "0";
        }

        v1 = Double.parseDouble(va1);
        v2 = Double.parseDouble(va2);

        total = Double.valueOf(df2.format(v1 + v2));
        return total;
    }

    //Method that return fifty percent
    public double returnFiftyPercent(JTextField val1) {
        String v1 = val1.getText().trim();
        double total = 0;

        if (v1.isEmpty()) {
            v1 = "0";
        }
        double fval = Double.parseDouble(v1);
        if (thirtypercent.isSelected()) {
            total = Double.valueOf(df2.format(fval * 0.3));
        } else if (fiftypercent.isSelected()) {
            total = Double.valueOf(df2.format(fval * 0.5));
        }

        return total;
    }

    //Method that return forty percent
    public double returnSixtyAPercent(JTextField val1) {

        String v1 = val1.getText().trim();
        double total = 0;

        if (v1.isEmpty()) {
            v1 = "0";
        }
        double fval = Double.parseDouble(v1);

        if (thirtypercent.isSelected()) {
            total = Double.valueOf(df2.format(fval * 0.7));
        } else if (fiftypercent.isSelected()) {
            total = Double.valueOf(df2.format(fval * 0.5));
        }

        return total;
    }

    //Method that result adding
    public double addExamsAndAssessmentPerceent(JTextField val1, JTextField val2) {
        double total;
        double v1, v2;
        String val = val1.getText().trim();
        String vall = val2.getText().trim();

        if (val.isEmpty()) {
            val = "0";
        }
        if (vall.isEmpty()) {
            vall = "0";
        }

        v1 = Double.parseDouble(val);
        v2 = Double.parseDouble(vall);
        total = Double.valueOf(df2.format(v1 + v2));

        return total;
    }

    //returning the grade
    public String returnResult(JTextField val1) {
        String result = null;
        if (val1.getText().isEmpty()) {
            result = "Not Set";
        } else {
            result = mylogics.returnGrade(Double.parseDouble(val1.getText().trim()));
        }
        return result;
    }

    public String returnRmarks(JTextField val1) {
        String result = null;
        if (val1.getText().isEmpty()) {
            result = "Not Set";
        } else {
            result = mylogics.returnRemarks(Double.parseDouble(val1.getText().trim()));
        }
        return result;
    }

    //getting all result
    //getting result with condition
    public void resultCondition() {
        try {
            int row = Assessment_Table.getSelectedRow();
            String id = Assessment_Table.getModel().getValueAt(row, 0).toString();

            terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
            acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
            subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
            yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
            clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

            List<Result> result = mylogics.resultsListToPopulate(mylogics.studentID(id), terID, acaID, subjID, yearID, clasID);

            for (Result res : result) {
                lbl_result.setText("" + res.getRid());
                cmd_StuID.setSelectedItem(mylogics.studentIDAssigned(res.getStuId(), yearID));
                cmd_fullname.setSelectedItem(mylogics.findStudentByIDName(res.getStuId(), yearID));
                txt_fpercent.setText("" + res.getAssessment());
                txt_spercent.setText("" + res.getExams());
                txt_GrandTotal.setText(" " + res.getTotalResult());
                lbl_Grade.setText(res.getGrade());
                lbl_Grade1.setText(mylogics.returnRemarksOnID(res.getRemarks()));
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

    }

    //getting result with condition
    public void examsCondition() {

        int row = Assessment_Table.getSelectedRow();
        String id = Assessment_Table.getModel().getValueAt(row, 0).toString();

        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Exams> exams = mylogics.examsListToPopulate(mylogics.studentID(id), terID, acaID, subjID, yearID, clasID);
        for (Exams exe : exams) {
            lbl_exams.setText("" + exe.getEid());
            txt_EOBJ.setText("" + exe.getObjectives());
            txt_Theory.setText("" + exe.getTheory());
            txt_totalE.setText("" + exe.getTotalExams());
        }
    }

    //getting quiz with condition
    public void quizCondition() {
        int row = Assessment_Table.getSelectedRow();
        String id = Assessment_Table.getModel().getValueAt(row, 0).toString();

        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Assessment> assess = mylogics.assessListToPopulate(mylogics.studentID(id), terID, acaID, subjID, yearID, clasID);

        for (Assessment a : assess) {
            lbl_assess.setText("" + a.getAid());
            txt_CTest.setText("" + a.getClassTest());
            txt_Others.setText("" + a.getOthers());
            txt_totalQ.setText("" + a.getTotal());
        }

    }

    ///Getting single data
    public void resultConditionResult() {
        try {
            String id = cmd_StuID.getSelectedItem().toString();

            terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
            acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
            subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
            yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
            clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

            List<Result> result = mylogics.resultsListToPopulate(mylogics.studentID(id), terID, acaID, subjID, yearID, clasID);

//            if (result.size() <= 0) {
//                JOptionPane.showMessageDialog(null, "Assessment not recorded", "NO RESULT", JOptionPane.INFORMATION_MESSAGE);
//            }
            for (Result res : result) {
                lbl_result.setText("" + res.getRid());
                cmd_StuID.setSelectedItem(mylogics.studentIDAssigned(res.getStuId(), yearID));
                cmd_fullname.setSelectedItem(mylogics.findStudentByIDName(res.getStuId(), yearID));
                txt_fpercent.setText("" + res.getAssessment());
                txt_spercent.setText("" + res.getExams());
                txt_GrandTotal.setText(" " + res.getTotalResult());
                lbl_Grade.setText(res.getGrade());
                lbl_Grade1.setText(mylogics.returnRemarksOnID(res.getRemarks()));
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

    }

    public void examsConditionExams() {

        String id = cmd_StuID.getSelectedItem().toString();

        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Exams> exams = mylogics.examsListToPopulate(mylogics.studentID(id), terID, acaID, subjID, yearID, clasID);
        for (Exams exe : exams) {
            lbl_exams.setText("" + exe.getEid());
            txt_EOBJ.setText("" + exe.getObjectives());
            txt_Theory.setText("" + exe.getTheory());
            txt_totalE.setText("" + exe.getTotalExams());
        }
    }

    public void quizConditionQuiz() {
        String id = cmd_StuID.getSelectedItem().toString();

        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Assessment> assess = mylogics.assessListToPopulate(mylogics.studentID(id), terID, acaID, subjID, yearID, clasID);

        for (Assessment a : assess) {
            lbl_assess.setText("" + a.getAid());
            txt_CTest.setText("" + a.getClassTest());
            txt_Others.setText("" + a.getOthers());
            txt_totalQ.setText("" + a.getTotal());
        }

    }

    //Setting the textfield to Empty
    public void newRecords() {
        if (txt_CTest.getText().isEmpty()) {
            //JOptionPane.showMessageDialog(null, "The textfeilds are already Empty");
        } else {
            txt_CTest.setText(" ");
            txt_Others.setText(" ");
            txt_EOBJ.setText(" ");
            txt_Theory.setText(" ");
            txt_GrandTotal.setText(" ");
            lbl_Grade.setText(" ");
            txt_totalQ.setText("");
            txt_fpercent.setText("");
            txt_totalE.setText("");
            txt_spercent.setText("");
        }
    }

    //Deleting assessment record
    public void deleteResult() {
        int result = Integer.parseInt(lbl_result.getText().toString());
        int exams = Integer.parseInt(lbl_exams.getText().toString());
        int quiz = Integer.parseInt(lbl_assess.getText().toString());

        try {
            pro.deleteResult(result);
            pro.deleteExams(exams);
            pro.deleteAssessment(quiz);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Confirming delete
    public void confirmDelete() {
        try {

            if (lbl_exams.getText().isEmpty() || lbl_assess.getText().isEmpty() || lbl_result.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Record is selected");
            } else {
                int ask = JOptionPane.showConfirmDialog(null, "DELETING RECORDS, Are you sure you want to delete this record ?", "DELETING", JOptionPane.YES_NO_OPTION);
                if (ask == 0) {
                    deleteResult();
                    showInTable();
                    JOptionPane.showMessageDialog(null, "Record is Deleted");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Empty record cannot be deleted");
        }
    }

    //getting result with condition
    public void resultCondition2() {
        stuID = mylogics.studentID(cmd_StuID.getSelectedItem().toString());
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Result> result = mylogics.resultsListToPopulate(stuID, terID, acaID, subjID, yearID, clasID);

        for (Result res : result) {
            txt_fpercent.setText("" + res.getAssessment());
            txt_spercent.setText("" + res.getExams());
            txt_GrandTotal.setText(" " + res.getTotalResult());
            lbl_Grade.setText(mylogics.returnRemarksOnID(mylogics.returnRemarksID(res.getGrade())));
        }

    }

    //preview print out
    public void previewPrint() {
        Receipt rec = new Receipt(this, true);
        rec.setLocationRelativeTo(this);
        rec.setAlwaysOnTop(true);
        rec.setVisible(true);
    }

    //Clear All
    public void clearAll() {
        pro.allClear("Result");
        pro.allClear("Exams");
        pro.allClear("Assessment");

        Result result = new Result(0, 0, 0, 0, 0, 0, 23, 34, 23, 70, "0", 0);
        pro.saveResult(result);

        Exams exam = new Exams(0, 0, 0, 0, 0, 0, 23, 34, 23, 70);
        pro.saveExams(exam);

        Assessment assess = new Assessment(0, 0, 0, 0, 0, 0, 23, 34, 23, 70);
        pro.saveAssessment(assess);
    }

    //Exporting to excel
    public void exportToExcel() {
        ExcelExportReport eep = new ExcelExportReport(null, true);
        eep.setVisible(true);
    }

    public void getPopulate() {
        txt_totalE.setText("" + addTwoValues(txt_EOBJ, txt_Theory));
        txt_spercent.setText("" + returnSixtyAPercent(txt_totalE));
        txt_GrandTotal.setText("" + addExamsAndAssessmentPerceent(txt_fpercent, txt_spercent));
        lbl_Grade.setText("" + returnResult(txt_GrandTotal));
        lbl_Grade1.setText(returnRmarks(txt_GrandTotal));
    }

    public void getPopulateTwo() {
        txt_totalQ.setText("" + addTwoValues(txt_CTest, txt_Others));
        txt_fpercent.setText("" + returnFiftyPercent(txt_totalQ));
        txt_GrandTotal.setText("" + addExamsAndAssessmentPerceent(txt_fpercent, txt_spercent));
        lbl_Grade.setText("" + returnResult(txt_GrandTotal));
        lbl_Grade1.setText(returnRmarks(txt_GrandTotal));
    }

    public void showInTable() {
        if (cmd_Term.getSelectedIndex() == 0 || cmb_Academic.getSelectedIndex() == 0 || cmb_Subject.getSelectedIndex() == 0 || cmd_Batch.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please Select Semester, Academic, Course and Batch");
        } else {
            allResultSpecific();
        }
    }

    public void clearRefresh() {
        int ask = JOptionPane.showConfirmDialog(null, "Do you want to Delete All Student Assessment Records?", "Delete", JOptionPane.YES_NO_OPTION);
        if (ask == 0) {
            clearAll();
            allResultSpecific();
        }
    }

    public void showReport() {
        StudentForm stuform = new StudentForm();
        stuform.setLocationRelativeTo(this);
        stuform.setVisible(true);
        dispose();
    }

    //Searching for item in table method starts here
    public void searchSpecificStudent(String SearchString) {
        try {
            DefaultTableModel currtableModel = (DefaultTableModel) Assessment_Table.getModel();
            currtableModel.setRowCount(0);
            for (Object rows2 : originalTableModel) {
                Vector rowVector = (Vector) rows2;

                for (Object column : rowVector) {
                    if (column.toString().contains(SearchString)) {
                        currtableModel.addRow(rowVector);
                        break;
                    }
                }
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

    public void returnStudentResult(JComboBox cmd) {
        stuID = mylogics.findStudentReport(cmd.getSelectedItem().toString(), mylogics.yearID(cmd_Batch.getSelectedItem().toString()));
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        if (cmd.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Select Student Name");
        } else {
            lhand.resultOfStudentSearch(Assessment_Table, stuID, terID, acaID, subjID, yearID, clasID);
            lhand.examsOfStudentSearch(exams_Table, stuID, terID, acaID, subjID, yearID, clasID);
            lhand.assessmentOfStudentSearch(class_Table, stuID, terID, acaID, subjID, yearID, clasID);
        }
    }

    public void updateResult() {
        int clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());
        int rem = mylogics.returnRemarksID(lbl_Grade1.getText());

        if (lbl_result.getText().isEmpty() || lbl_assess.getText().isEmpty() || lbl_exams.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select records to update");
        } else {
            mylogics.updateResult(lbl_result, txt_fpercent, txt_spercent, txt_GrandTotal, lbl_Grade, rem, clasID);
            mylogics.updateExams(lbl_exams, txt_EOBJ, txt_Theory, txt_totalE, txt_spercent, clasID);
            mylogics.updateAssessment(lbl_assess, txt_CTest, txt_Others, txt_totalQ, txt_fpercent, clasID);
            JOptionPane.showMessageDialog(null, "Results Updated");
            showInTable();
        }

    }

    public void backDesktop() {
        Desktop dp = new Desktop();
        dp.setVisible(true);
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        cmd_Batch = new javax.swing.JComboBox<>();
        cmb_Subject = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmb_Academic = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmd_Term = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btn_Show = new javax.swing.JButton();
        cmd_Class = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt_CTest = new javax.swing.JTextField();
        txt_Others = new javax.swing.JTextField();
        txt_totalQ = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        txt_EOBJ = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_Theory = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txt_GrandTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_Grade = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_fpercent = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_totalE = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_spercent = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        lbl_Grade1 = new javax.swing.JLabel();
        fiftypercent = new javax.swing.JRadioButton();
        thirtypercent = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmd_StuID = new javax.swing.JComboBox<>();
        cmd_fullname = new javax.swing.JComboBox<>();
        btn_prev = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        lbl_result = new javax.swing.JLabel();
        lbl_result1 = new javax.swing.JLabel();
        lbl_assess = new javax.swing.JLabel();
        lbl_exams = new javax.swing.JLabel();
        btn_plus = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btn_Save = new javax.swing.JButton();
        btn_New = new javax.swing.JButton();
        btn_Update = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        lbl_Back = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btn_Refresh = new javax.swing.JButton();
        lbl_Date = new javax.swing.JLabel();
        lbl_Time = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Assessment_Table = new javax.swing.JTable();
        progress_save = new javax.swing.JProgressBar();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        exams_Table = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        class_Table = new javax.swing.JTable();
        cmd_searchStudent = new javax.swing.JComboBox<>();
        search_student_result = new javax.swing.JButton();
        manuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menu_New = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menu_Save = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menu_Update = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        menu_Delete = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        menu_Preview = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        menu_Show = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        menu_Back = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenu2 = new javax.swing.JMenu();
        menuPerformance = new javax.swing.JMenuItem();
        export = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        menuPrint = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        clear = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        iconExport = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("STUDENTS ASSESSMENT FORM");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Provide the following Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(19, 159, 91))); // NOI18N

        cmd_Batch.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Batch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_BatchActionPerformed(evt);
            }
        });

        cmb_Subject.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_Subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_SubjectActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setText("Term");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setText("Year Batch");

        cmb_Academic.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_Academic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_AcademicActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setText("Subject");

        cmd_Term.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Term.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_TermActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setText("Academic Year");

        btn_Show.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_Show.setForeground(new java.awt.Color(102, 0, 51));
        btn_Show.setText("Show Assessment");
        btn_Show.setToolTipText("Click to Show list");
        btn_Show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ShowActionPerformed(evt);
            }
        });

        cmd_Class.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Class.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Class", "Nursery", "KG One", "KG Two", "KG Three", "BS One", "BS Two", "BS Three", "BS Four", "BS Five", "BS Six", "Form One", "Form Two", "Form Three" }));
        cmd_Class.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_ClassActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel11.setText("Class");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmb_Subject, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_Academic, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_Term, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_Batch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_Class, 0, 148, Short.MAX_VALUE)
                    .addComponent(btn_Show, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmd_Class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmd_Term, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmb_Academic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmb_Subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmd_Batch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Show, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Student Performance", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(19, 159, 91))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Class Test");

        txt_CTest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_CTestFocusLost(evt);
            }
        });
        txt_CTest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_CTestKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_CTestKeyTyped(evt);
            }
        });

        txt_Others.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_OthersFocusLost(evt);
            }
        });
        txt_Others.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_OthersKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_OthersKeyTyped(evt);
            }
        });

        txt_totalQ.setEditable(false);
        txt_totalQ.setForeground(new java.awt.Color(0, 0, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Others Scores");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Exams Obj");

        txt_EOBJ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_EOBJFocusLost(evt);
            }
        });
        txt_EOBJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_EOBJKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_EOBJKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Total Theory");

        txt_Theory.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_TheoryFocusLost(evt);
            }
        });
        txt_Theory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_TheoryKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_TheoryKeyTyped(evt);
            }
        });

        txt_GrandTotal.setEditable(false);
        txt_GrandTotal.setBackground(new java.awt.Color(204, 204, 204));
        txt_GrandTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_GrandTotal.setForeground(new java.awt.Color(255, 0, 0));
        txt_GrandTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_GrandTotal.setBorder(null);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 0, 51));
        jLabel13.setText("Exams Score + Assessment");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 0, 0));
        jLabel14.setText("Grade      :");

        lbl_Grade.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbl_Grade.setForeground(new java.awt.Color(255, 0, 0));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel15.setText("Total");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel16.setText("50%");

        txt_fpercent.setEditable(false);
        txt_fpercent.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_fpercent.setForeground(new java.awt.Color(255, 0, 0));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel17.setText("Total");

        txt_totalE.setEditable(false);
        txt_totalE.setForeground(new java.awt.Color(0, 0, 255));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel18.setText("50%");

        txt_spercent.setEditable(false);
        txt_spercent.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_spercent.setForeground(new java.awt.Color(255, 0, 0));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 0, 0));
        jLabel19.setText("Remarks :");

        lbl_Grade1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_Grade1.setForeground(new java.awt.Color(255, 0, 0));

        buttonGroup1.add(fiftypercent);
        fiftypercent.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fiftypercent.setForeground(new java.awt.Color(51, 0, 0));
        fiftypercent.setSelected(true);
        fiftypercent.setText("50/50");
        fiftypercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiftypercentActionPerformed(evt);
            }
        });

        buttonGroup1.add(thirtypercent);
        thirtypercent.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        thirtypercent.setForeground(new java.awt.Color(0, 0, 51));
        thirtypercent.setText("30/70");
        thirtypercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirtypercentActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Check format");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator3)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_Theory, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(txt_EOBJ))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_totalQ, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_fpercent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_totalE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_spercent, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_GrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(10, 10, 10)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_Grade1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(lbl_Grade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_CTest, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(txt_Others))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(thirtypercent)
                                .addGap(18, 18, 18)
                                .addComponent(fiftypercent)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fiftypercent)
                    .addComponent(thirtypercent)
                    .addComponent(jLabel12))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_CTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_totalQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txt_Others, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_fpercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_EOBJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel17)
                    .addComponent(txt_totalE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Theory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_spercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_GrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Grade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(lbl_Grade1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Student ID", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(19, 159, 91))); // NOI18N
        jPanel3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPanel3FocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel6.setText("Select ID");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setText("Full Name");

        cmd_StuID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmd_StuID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_StuIDActionPerformed(evt);
            }
        });

        cmd_fullname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmd_fullname.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmd_fullnameItemStateChanged(evt);
            }
        });
        cmd_fullname.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                cmd_fullnameMouseMoved(evt);
            }
        });
        cmd_fullname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmd_fullnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmd_fullnameFocusLost(evt);
            }
        });
        cmd_fullname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmd_fullnameMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmd_fullnameMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmd_fullnameMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmd_fullnameMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cmd_fullnameMouseReleased(evt);
            }
        });
        cmd_fullname.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                cmd_fullnameCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cmd_fullnameInputMethodTextChanged(evt);
            }
        });
        cmd_fullname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_fullnameActionPerformed(evt);
            }
        });
        cmd_fullname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmd_fullnameKeyReleased(evt);
            }
        });

        btn_prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/prev.png"))); // NOI18N
        btn_prev.setToolTipText("Click to go back");
        btn_prev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prevActionPerformed(evt);
            }
        });

        btn_next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/next.png"))); // NOI18N
        btn_next.setToolTipText("Click to go next");
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        btn_plus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_plus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/plus2.png"))); // NOI18N
        btn_plus.setToolTipText("Click to add more");
        btn_plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_plusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbl_exams, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_assess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(lbl_result, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_prev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_plus))
                    .addComponent(cmd_fullname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmd_StuID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(lbl_result1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(182, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmd_StuID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmd_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lbl_assess, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_prev, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_result, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_exams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btn_plus, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(lbl_result1, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jDesktopPane1.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        btn_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save.png"))); // NOI18N
        btn_Save.setToolTipText("Click to Save");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        btn_New.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/new.png"))); // NOI18N
        btn_New.setToolTipText("Click to Add New");
        btn_New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NewActionPerformed(evt);
            }
        });

        btn_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/update.png"))); // NOI18N
        btn_Update.setToolTipText("Click to Update");
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });

        btn_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete.png"))); // NOI18N
        btn_Delete.setToolTipText("Click to Delete");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });

        lbl_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/back1.png"))); // NOI18N
        lbl_Back.setToolTipText("Click to go back");
        lbl_Back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_BackMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_New, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(lbl_Back)
                        .addGap(16, 16, 16)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_New, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/repeat.png"))); // NOI18N
        btn_Refresh.setToolTipText("Click to Refresh");
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });

        lbl_Date.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_Date.setForeground(new java.awt.Color(204, 0, 0));
        lbl_Date.setText("Date");

        lbl_Time.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_Time.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Time.setText("Time");

        jTabbedPane1.setForeground(new java.awt.Color(51, 0, 0));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        Assessment_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Class (%)", "Exams (%)", "Total (%)", "Grd", "Pos", "Remarks"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        Assessment_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Assessment_TableMouseClicked(evt);
            }
        });
        Assessment_Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Assessment_TableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Assessment_TableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Assessment_Table);
        if (Assessment_Table.getColumnModel().getColumnCount() > 0) {
            Assessment_Table.getColumnModel().getColumn(1).setPreferredWidth(200);
            Assessment_Table.getColumnModel().getColumn(5).setPreferredWidth(40);
            Assessment_Table.getColumnModel().getColumn(6).setPreferredWidth(40);
            Assessment_Table.getColumnModel().getColumn(7).setPreferredWidth(90);
        }

        progress_save.setForeground(new java.awt.Color(102, 255, 204));
        progress_save.setToolTipText("");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
            .addComponent(progress_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(progress_save, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Result of Exams & Class Performance", jPanel6);

        exams_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Objectives", "Theory", "Total", "Percent(%)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(exams_Table);
        if (exams_Table.getColumnModel().getColumnCount() > 0) {
            exams_Table.getColumnModel().getColumn(0).setPreferredWidth(50);
            exams_Table.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Exams Score", jPanel7);

        class_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Class Test", "Others", "Total", "Percent(%)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(class_Table);
        if (class_Table.getColumnModel().getColumnCount() > 0) {
            class_Table.getColumnModel().getColumn(0).setPreferredWidth(50);
            class_Table.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Class Performance", jPanel8);

        cmd_searchStudent.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmd_searchStudent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmd_searchStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_searchStudentActionPerformed(evt);
            }
        });

        search_student_result.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search2.png"))); // NOI18N
        search_student_result.setToolTipText("Click to search for result");
        search_student_result.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_student_resultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmd_searchStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_student_result)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_Time, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(cmd_searchStudent, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(search_student_result, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_Date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_Time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1))
                    .addComponent(jDesktopPane1)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jScrollPane2.setViewportView(jPanel1);

        jMenu1.setText("File");

        menu_New.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menu_New.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newnew.png"))); // NOI18N
        menu_New.setText("New");
        menu_New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_NewActionPerformed(evt);
            }
        });
        jMenu1.add(menu_New);
        jMenu1.add(jSeparator5);

        menu_Save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menu_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newsave.png"))); // NOI18N
        menu_Save.setText("Save");
        menu_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_SaveActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Save);
        jMenu1.add(jSeparator6);

        menu_Update.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        menu_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newupdate.png"))); // NOI18N
        menu_Update.setText("Update");
        menu_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_UpdateActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Update);
        jMenu1.add(jSeparator7);

        menu_Delete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        menu_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newdelete.png"))); // NOI18N
        menu_Delete.setText("Delete");
        menu_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_DeleteActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Delete);
        jMenu1.add(jSeparator8);
        jMenu1.add(jSeparator9);

        menu_Preview.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menu_Preview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newpreview.png"))); // NOI18N
        menu_Preview.setText("Preview");
        menu_Preview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_PreviewActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Preview);
        jMenu1.add(jSeparator10);

        menu_Show.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_Show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newshow.png"))); // NOI18N
        menu_Show.setText("Show");
        menu_Show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_ShowActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Show);
        jMenu1.add(jSeparator11);

        menu_Back.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        menu_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newback.png"))); // NOI18N
        menu_Back.setText("Back");
        menu_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_BackActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Back);
        jMenu1.add(jSeparator12);

        manuBar.add(jMenu1);

        jMenu2.setText("View");

        menuPerformance.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuPerformance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/per.png"))); // NOI18N
        menuPerformance.setText("Monitor a student Performance");
        menuPerformance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPerformanceActionPerformed(evt);
            }
        });
        jMenu2.add(menuPerformance);

        export.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        export.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/excelSmall.png"))); // NOI18N
        export.setText("View & Export to Excel");
        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });
        jMenu2.add(export);
        jMenu2.add(jSeparator13);

        menuPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newpreview.png"))); // NOI18N
        menuPrint.setText("Initiate Print");
        menuPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrintActionPerformed(evt);
            }
        });
        jMenu2.add(menuPrint);
        jMenu2.add(jSeparator14);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newpreview.png"))); // NOI18N
        jMenuItem1.setText("View Report Card");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);
        jMenu2.add(jSeparator16);

        clear.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newdelete.png"))); // NOI18N
        clear.setText("Clear Result");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        jMenu2.add(clear);
        jMenu2.add(jSeparator4);
        jMenu2.add(jSeparator15);

        manuBar.add(jMenu2);

        iconExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/smallExcel.png"))); // NOI18N
        iconExport.setToolTipText("Click to Export to Excel");
        iconExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconExportMouseClicked(evt);
            }
        });
        iconExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconExportActionPerformed(evt);
            }
        });
        manuBar.add(iconExport);

        setJMenuBar(manuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_BackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_BackMouseClicked
        backDesktop();
    }//GEN-LAST:event_lbl_BackMouseClicked

    private void cmd_StuIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_StuIDActionPerformed
        String id = cmd_StuID.getSelectedItem().toString();
        terID = mylogics.termID(cmd_Term.getSelectedItem().toString());
        acaID = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        subjID = mylogics.subjectID(cmb_Subject.getSelectedItem().toString());
        yearID = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        clasID = mylogics.classID(cmd_Class.getSelectedItem().toString());

        List<Result> result = mylogics.resultsListToPopulate(mylogics.studentID(id), terID, acaID, subjID, yearID, clasID);

        if (cmd_StuID.getSelectedIndex() <= 0) {
            newRecords();
        } else if (result.isEmpty()) {
            newRecords();
            setStudentNameToCombo(cmd_fullname);
        } else {
            newRecords();
            resultConditionResult();
            examsConditionExams();
            quizConditionQuiz();
            setStudentNameToCombo(cmd_fullname);

        }
    }//GEN-LAST:event_cmd_StuIDActionPerformed

    private void cmd_TermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_TermActionPerformed
        //txt_Q1.setText("" + termID());
    }//GEN-LAST:event_cmd_TermActionPerformed

    private void btn_ShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ShowActionPerformed
        showInTable();
    }//GEN-LAST:event_btn_ShowActionPerformed

    private void cmb_AcademicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_AcademicActionPerformed
        // txt_Q2.setText("" + academicID());
    }//GEN-LAST:event_cmb_AcademicActionPerformed

    private void cmb_SubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_SubjectActionPerformed
        // txt_EOBJ.setText("" + courseID());
    }//GEN-LAST:event_cmb_SubjectActionPerformed

    private void cmd_BatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_BatchActionPerformed
        // txt_EQ1.setText("" + batchID());
        studentName(cmd_Batch);
    }//GEN-LAST:event_cmd_BatchActionPerformed

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if (thirtypercent.isSelected() || fiftypercent.isSelected()) {
            saveAllAssessment();
        } else {
            JOptionPane.showMessageDialog(null, "Please select assessment format");
            thirtypercent.setBackground(Color.white);
        }
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void txt_CTestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CTestKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getPopulateTwo();
            txt_Others.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            getPopulateTwo();
            txt_Others.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            cmd_StuID.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_CTestKeyPressed

    private void txt_CTestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_CTestFocusLost
        getPopulateTwo();
    }//GEN-LAST:event_txt_CTestFocusLost

    private void txt_OthersFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_OthersFocusLost
        getPopulateTwo();
    }//GEN-LAST:event_txt_OthersFocusLost

    private void txt_OthersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_OthersKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getPopulateTwo();
            txt_EOBJ.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            getPopulateTwo();
            txt_EOBJ.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_CTest.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_OthersKeyPressed

    private void txt_EOBJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_EOBJKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getPopulate();
            txt_Theory.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            getPopulate();
            txt_Theory.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_Others.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_EOBJKeyPressed

    private void txt_TheoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TheoryKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            btn_Save.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            getPopulate();
            btn_Save.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_EOBJ.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_TheoryKeyPressed

    private void txt_EOBJFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_EOBJFocusLost
        getPopulate();
    }//GEN-LAST:event_txt_EOBJFocusLost

    private void txt_TheoryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_TheoryFocusLost
        getPopulate();
    }//GEN-LAST:event_txt_TheoryFocusLost

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        updateResult();
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void btn_NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NewActionPerformed
        newRecords();
    }//GEN-LAST:event_btn_NewActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        confirmDelete();
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void menu_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_SaveActionPerformed
        getPopulateTwo();

    }//GEN-LAST:event_menu_SaveActionPerformed

    private void menu_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_BackActionPerformed
        backDesktop();
    }//GEN-LAST:event_menu_BackActionPerformed

    private void menu_NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_NewActionPerformed
        newRecords();
    }//GEN-LAST:event_menu_NewActionPerformed

    private void menu_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_UpdateActionPerformed
        getPopulateTwo();

    }//GEN-LAST:event_menu_UpdateActionPerformed

    private void menu_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_DeleteActionPerformed
//        confirmDelete();
    }//GEN-LAST:event_menu_DeleteActionPerformed

    private void menu_PreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_PreviewActionPerformed
        previewPrint();
    }//GEN-LAST:event_menu_PreviewActionPerformed

    private void menu_ShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_ShowActionPerformed
        //result();
    }//GEN-LAST:event_menu_ShowActionPerformed

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        showInTable();
    }//GEN-LAST:event_btn_RefreshActionPerformed

    private void Assessment_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Assessment_TableMouseClicked
        resultCondition();
        examsCondition();
        quizCondition();
    }//GEN-LAST:event_Assessment_TableMouseClicked

    private void Assessment_TableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Assessment_TableKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            resultCondition();
            examsCondition();
            quizCondition();
        }
    }//GEN-LAST:event_Assessment_TableKeyReleased

    private void Assessment_TableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Assessment_TableKeyPressed

    }//GEN-LAST:event_Assessment_TableKeyPressed

    private void menuPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrintActionPerformed
        previewPrint();
    }//GEN-LAST:event_menuPrintActionPerformed

    private void menuPerformanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPerformanceActionPerformed
        Performance per = new Performance();
        per.setLocationRelativeTo(this);
        per.setAlwaysOnTop(true);
        per.setVisible(true);
    }//GEN-LAST:event_menuPerformanceActionPerformed

    private void cmd_fullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_fullnameActionPerformed
        if (cmd_fullname.getSelectedIndex() <= 0) {
        } else {
            setStudentIDToCombo(cmd_StuID);
        }
    }//GEN-LAST:event_cmd_fullnameActionPerformed

    private void cmd_fullnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmd_fullnameKeyReleased

    }//GEN-LAST:event_cmd_fullnameKeyReleased

    private void cmd_fullnameMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmd_fullnameMouseReleased

    }//GEN-LAST:event_cmd_fullnameMouseReleased

    private void cmd_fullnameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmd_fullnameMousePressed

    }//GEN-LAST:event_cmd_fullnameMousePressed

    private void cmd_fullnameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmd_fullnameItemStateChanged


    }//GEN-LAST:event_cmd_fullnameItemStateChanged

    private void cmd_fullnameMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmd_fullnameMouseExited
        // cmd_StuID.setSelectedIndex(cmd_fullname.getSelectedIndex());
    }//GEN-LAST:event_cmd_fullnameMouseExited

    private void cmd_fullnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmd_fullnameMouseClicked

    }//GEN-LAST:event_cmd_fullnameMouseClicked

    private void cmd_fullnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmd_fullnameFocusGained

    }//GEN-LAST:event_cmd_fullnameFocusGained

    private void cmd_fullnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmd_fullnameFocusLost
        //cmd_StuID.setSelectedIndex(cmd_fullname.getSelectedIndex());
    }//GEN-LAST:event_cmd_fullnameFocusLost

    private void cmd_fullnameMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmd_fullnameMouseMoved

    }//GEN-LAST:event_cmd_fullnameMouseMoved

    private void cmd_fullnameInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmd_fullnameInputMethodTextChanged

    }//GEN-LAST:event_cmd_fullnameInputMethodTextChanged

    private void cmd_fullnameCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmd_fullnameCaretPositionChanged
        // cmd_StuID.setSelectedIndex(cmd_fullname.getSelectedIndex());
    }//GEN-LAST:event_cmd_fullnameCaretPositionChanged

    private void cmd_fullnameMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmd_fullnameMouseEntered
        //cmd_StuID.setSelectedIndex(cmd_fullname.getSelectedIndex());
    }//GEN-LAST:event_cmd_fullnameMouseEntered

    private void jPanel3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel3FocusLost

    }//GEN-LAST:event_jPanel3FocusLost

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
        exportToExcel();
    }//GEN-LAST:event_exportActionPerformed

    private void iconExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iconExportActionPerformed

    }//GEN-LAST:event_iconExportActionPerformed

    private void iconExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconExportMouseClicked
        exportToExcel();
    }//GEN-LAST:event_iconExportMouseClicked

    private void cmd_ClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_ClassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmd_ClassActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        if (cmd_Batch.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Select Year Batch");
        } else if (mylogics.totalStudentCount(mylogics.yearID(cmd_Batch.getSelectedItem().toString())) <= 0) {
            JOptionPane.showMessageDialog(null, "No students added yet");
        } else {
            btnNext();
            btnNextResult();
            btnNextExam();
            btnNextAssess();
        }
    }//GEN-LAST:event_btn_nextActionPerformed

    private void btn_prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prevActionPerformed
        if (cmd_Batch.getSelectedIndex() <= 0) {

        } else if (mylogics.totalStudentCount(mylogics.yearID(cmd_Batch.getSelectedItem().toString())) <= 0) {
            JOptionPane.showMessageDialog(null, "No students added yet");
        } else {
            btnPrevious();
            btnPreviousResult();
            btnPreviousExams();
            btnPreviousAssess();
        }
    }//GEN-LAST:event_btn_prevActionPerformed

    private void txt_CTestKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CTestKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == java.awt.event.KeyEvent.VK_BACK_SPACE) || (c == java.awt.event.KeyEvent.VK_DELETE) || (c == java.awt.event.KeyEvent.VK_PERIOD) || (c == java.awt.event.KeyEvent.VK_ENTER))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_CTestKeyTyped

    private void txt_OthersKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_OthersKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == java.awt.event.KeyEvent.VK_BACK_SPACE) || (c == java.awt.event.KeyEvent.VK_DELETE) || (c == java.awt.event.KeyEvent.VK_PERIOD) || (c == java.awt.event.KeyEvent.VK_ENTER))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_OthersKeyTyped

    private void txt_EOBJKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_EOBJKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == java.awt.event.KeyEvent.VK_BACK_SPACE) || (c == java.awt.event.KeyEvent.VK_DELETE) || (c == java.awt.event.KeyEvent.VK_PERIOD) || (c == java.awt.event.KeyEvent.VK_ENTER))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_EOBJKeyTyped

    private void txt_TheoryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TheoryKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == java.awt.event.KeyEvent.VK_BACK_SPACE) || (c == java.awt.event.KeyEvent.VK_DELETE) || (c == java.awt.event.KeyEvent.VK_PERIOD) || (c == java.awt.event.KeyEvent.VK_ENTER))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_TheoryKeyTyped

    private void thirtypercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirtypercentActionPerformed
        getPopulateTwo();
        getPopulate();
        jLabel16.setText("30%");
        jLabel18.setText("70%");
    }//GEN-LAST:event_thirtypercentActionPerformed

    private void fiftypercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiftypercentActionPerformed
        getPopulateTwo();
        getPopulate();
        jLabel16.setText("50%");
        jLabel18.setText("50%");
    }//GEN-LAST:event_fiftypercentActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        clearRefresh();
    }//GEN-LAST:event_clearActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        showReport();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void cmd_searchStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_searchStudentActionPerformed

    }//GEN-LAST:event_cmd_searchStudentActionPerformed

    private void search_student_resultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_student_resultActionPerformed
        returnStudentResult(cmd_searchStudent);
    }//GEN-LAST:event_search_student_resultActionPerformed

    private void btn_plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_plusActionPerformed
        if (cmd_Batch.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Select Year Batch");
        } else if (mylogics.totalStudentCount(mylogics.yearID(cmd_Batch.getSelectedItem().toString())) <= 0) {
            JOptionPane.showMessageDialog(null, "No students added yet");
        } else {
            addAssessment();
            addExams();
            addResult();
            btnNext();
        }
    }//GEN-LAST:event_btn_plusActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Assessment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Assessment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Assessment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Assessment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AssessmentForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable Assessment_Table;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_New;
    private javax.swing.JButton btn_Refresh;
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_Show;
    private javax.swing.JButton btn_Update;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_plus;
    private javax.swing.JButton btn_prev;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTable class_Table;
    private javax.swing.JMenuItem clear;
    public static javax.swing.JComboBox<String> cmb_Academic;
    public static javax.swing.JComboBox<String> cmb_Subject;
    public static javax.swing.JComboBox<String> cmd_Batch;
    public static javax.swing.JComboBox<String> cmd_Class;
    public static javax.swing.JComboBox<String> cmd_StuID;
    public static javax.swing.JComboBox<String> cmd_Term;
    public static javax.swing.JComboBox<String> cmd_fullname;
    public static javax.swing.JComboBox<String> cmd_searchStudent;
    private javax.swing.JTable exams_Table;
    private javax.swing.JMenuItem export;
    public static javax.swing.JRadioButton fiftypercent;
    private javax.swing.JMenu iconExport;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    public static javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    public static javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_Back;
    public static javax.swing.JLabel lbl_Date;
    private javax.swing.JLabel lbl_Grade;
    private javax.swing.JLabel lbl_Grade1;
    private javax.swing.JLabel lbl_Time;
    private javax.swing.JLabel lbl_assess;
    private javax.swing.JLabel lbl_exams;
    private javax.swing.JLabel lbl_result;
    private javax.swing.JLabel lbl_result1;
    private javax.swing.JMenuBar manuBar;
    private javax.swing.JMenuItem menuPerformance;
    private javax.swing.JMenuItem menuPrint;
    private javax.swing.JMenuItem menu_Back;
    private javax.swing.JMenuItem menu_Delete;
    private javax.swing.JMenuItem menu_New;
    private javax.swing.JMenuItem menu_Preview;
    private javax.swing.JMenuItem menu_Save;
    private javax.swing.JMenuItem menu_Show;
    private javax.swing.JMenuItem menu_Update;
    private javax.swing.JProgressBar progress_save;
    private javax.swing.JButton search_student_result;
    public static javax.swing.JRadioButton thirtypercent;
    private javax.swing.JTextField txt_CTest;
    private javax.swing.JTextField txt_EOBJ;
    private javax.swing.JTextField txt_GrandTotal;
    private javax.swing.JTextField txt_Others;
    private javax.swing.JTextField txt_Theory;
    private javax.swing.JTextField txt_fpercent;
    private javax.swing.JTextField txt_spercent;
    private javax.swing.JTextField txt_totalE;
    private javax.swing.JTextField txt_totalQ;
    // End of variables declaration//GEN-END:variables
}

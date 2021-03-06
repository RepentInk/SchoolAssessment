package Designs;

import Classes.School;
import Classes.SchoolRemarks;
import Classes.Student;
import Classes.Subject;
import Connects.ProgramDAO;
import Connects.logicHandler;
import Connects.myCon;
import Connects.myLogics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AddNew extends javax.swing.JFrame {

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    ProgramDAO pro = new ProgramDAO();
    AssessmentForm assess = new AssessmentForm();

    myLogics mylogics = new myLogics();
    logicHandler lhand = new logicHandler();
    List<SchoolRemarks> listRemarks = new ArrayList<>();

    int pos = 0;

    Double lmarks, hmarks;
    String grade;
    String remarks;

    public AddNew() {
        initComponents();
        conn = myCon.ConnecrDb();

        setIconImage(mets.myImage("/Icons/globe.png"));

        setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        lbl_id.setVisible(false);
        lbl_StuID.setVisible(false);
        lbl_sid.setVisible(false);
        lbl_remarksID.setVisible(false);

        //Year methods
        allYearCombo();
        //Academic year
        allAcademicYear();
        allTerm();
        allClassess();
        allSubject();
        allRemarks();
        populateRemarks();

        populateSchoolTable();

        // bill method calling
        allConduct();
        allInterest();
        allAttitude();
        allBills();

        lbl_bill.setVisible(false);
        lbl_attitude.setVisible(false);
        lbl_interest.setVisible(false);
        lbl_conduct.setVisible(false);
    }

    //=========Method for all year combo================
    public void allYearCombo() {
        mylogics.setYearToCombo(cmb_Search, "Select Year Batch");
        mylogics.setYearToCombo(cmb_YearBatch, "Select Year Batch");
        mylogics.setYearToCombo(yearCombo, "---See All Years---");
    }

    //======method for calling academic year to combo====
    public void allAcademicYear() {
        mylogics.setAcademicToCombo(academic, "See All Academic Year");
    }

    public void populateRemarks() {
        //remarks populate
        mylogics.remarksPopulate(remarks_table);
    }

    public void populateSchoolTable() {
        mylogics.schoolDetails(table_schoolInfo);
    }

    public void allTerm() {
        mylogics.setTermToCombo(Cmd_term, "---See All Terms---");
    }

    public void allClassess() {
        mylogics.setClassToCombo(classess, "--See All Classess---");
    }

    public void allSubject() {
        mylogics.setSubjectToCombo(cmd_Subjects, "--See All Subjects---");
    }

    public void allRemarks() {
        mylogics.setRemarksToCombo(cmd_gradingSystem, "--See All Grading---");
    }

    public void showTotalStu(JComboBox combo) {
        if (combo.getSelectedIndex() <= 0) {

        } else {
            mylogics.totalStudent(txt_ID, mylogics.yearID(combo.getSelectedItem().toString()));
        }
    }

    //List Student tableclick method
    public void listTableClick() {
        String tmp = (String) txt_Search.getSelectedValue();
        List<Student> studentinfo = mylogics.findOneStudentInfo(tmp, mylogics.yearID(cmb_Search.getSelectedItem().toString()));


        for (Student student : studentinfo) {
            lbl_StuID.setText("" + student.getId());
            stuIdtxt.setText(student.getStuID());
            cmb_YearBatch.setSelectedItem(mylogics.yearName(student.getCompleteID()));
            txt_FName.setText(student.getfName());
            txt_MName.setText(student.getmName());
            txt_SName.setText(student.getSurname());
            txt_Contact.setText("0" + student.getContact());
            txt_HTown.setText(student.gethTown());
     
        }

    }

    public void showNext(int index) {
        List<Student> student = mylogics.findStudentInfoNext(mylogics.yearID(cmb_Search.getSelectedItem().toString()));
        lbl_StuID.setText(Integer.toString(student.get(index).getId()));
        stuIdtxt.setText(student.get(index).getStuID());
        txt_FName.setText(student.get(index).getfName());
        txt_MName.setText(student.get(index).getmName());
        txt_SName.setText(student.get(index).getSurname());
        cmb_YearBatch.setSelectedItem(mylogics.yearName(student.get(index).getCompleteID()));
        txt_Contact.setText("0" + student.get(index).getContact());
        txt_HTown.setText(student.get(index).gethTown());
    }

    // Method to go next
    public void btnNext() {
        pos = Integer.parseInt(getID.getText());
        pos++;
        List<Student> student = mylogics.findStudentInfoNext(mylogics.yearID(cmb_Search.getSelectedItem().toString()));
        if (pos < student.size()) {
            showNext(pos);
        } else {
            pos = student.size() - 1;
            showNext(pos);
            JOptionPane.showMessageDialog(null, "End of List");
        }
    }

    //Method to go previous
    public void btnPrevious() {
        pos = Integer.parseInt(lbl_StuID.getText());
        pos--;
        if (pos > 0) {
            showNext(pos);
        } else {
            pos = 0;
            showNext(pos);
            JOptionPane.showMessageDialog(null, "Begins");
        }
    }

    //method that save into Subject
    public void saveSubject() {
        if (txt_SubCode.getText().isEmpty()) {
            txt_SubCode.setBackground(Color.red);
            txt_SubCode.setText("Enter Subject Code");
        } else if (txt_SubName.getText().isEmpty()) {
            txt_SubName.setBackground(Color.red);
            txt_SubName.setText("Enter Subject Name");
        } else {
            mylogics.saveSubject(txt_SubCode, txt_SubName);
        }
    }

    //method that save into program
    public void saveStudent() {
        if (cmb_YearBatch.getSelectedIndex() == 0 || txt_ID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required filled");
        } else {
            mylogics.saveStudent(txt_ID, cmb_YearBatch, txt_FName, txt_MName, txt_SName, txt_Contact, txt_HTown);
        }
    }

    //Showing student by year
    public void showStudent() {
        if (cmb_Search.getSelectedIndex() <= 0) {

        } else {
            setStudentName();
            showTotalStu(cmb_Search);
        }
    }

    //NEw student method
    public void newStudent() {
        stuIdtxt.setText(" ");
        txt_FName.setText(" ");
        txt_MName.setText(" ");
        txt_SName.setText(" ");
        cmb_YearBatch.setSelectedIndex(0);
        txt_Contact.setValue(null);
        txt_HTown.setText(" ");
    }

    //New schoolinfo 
    public void newSchoolDetails() {
        txt_schoolName.setText(" ");
        txt_schoolAddress.setText(" ");
        txt_schoolLocation.setText("");
        txt_schoolVac.setCalendar(null);
        txt_schoolResume.setCalendar(null);
        txt_schoolContact.setText(" ");
    }

    //new Subject
    public void newSubject() {
        txt_SubCode.setText(" ");
        txt_SubName.setText("");
        cmd_Subjects.setSelectedIndex(0);
    }

    //Enter new method starts here
    public void newAll() {
        if (TabbedPane.getSelectedIndex() == 0) {
            newStudent();
        } else if (TabbedPane.getSelectedIndex() == 1) {
            newSubject();
        }
    }

    public void setStudentName() {
        List<Student> sub = mylogics.findAllStudentName(mylogics.yearID(cmb_Search.getSelectedItem().toString()));
        DefaultListModel name = new DefaultListModel();
        name.removeAllElements();

        for (Student student : sub) {
            name.addElement(student.getfName().trim() + " " + student.getmName().trim() + " " + student.getSurname().trim());
        }

        txt_Search.setModel(name);
    }

    public void updateStu() {
        int sid = Integer.parseInt(lbl_StuID.getText().trim());
        String stuID = stuIdtxt.getText().trim();
        String fn = txt_FName.getText().trim();
        String mn = txt_MName.getText().trim();
        String sn = txt_SName.getText().trim();
        int comid = mylogics.yearID(cmb_YearBatch.getSelectedItem().toString());
        String con = txt_Contact.getText().trim();
        String hom = txt_HTown.getText().trim();

        mylogics.updateStudentRecords(sid, stuID, comid, fn, mn, sn, con, hom);

    }

    public void setStuDel() {
        int sid = Integer.parseInt(lbl_StuID.getText().trim());
        String stuID = stuIdtxt.getText().trim();
        int comid = mylogics.yearID(cmb_YearBatch.getSelectedItem().toString());

        mylogics.setStudentDelete(sid, stuID, comid);

    }

    //Updating method call
    public void updateAll() {
        if (TabbedPane.getSelectedIndex() == 0) {

            if (lbl_StuID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select to Update");
            } else {
                updateStu();
                showStudent();
            }

        } else if (TabbedPane.getSelectedIndex() == 1) {

            if (lbl_id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select to Update");
            } else {
                mylogics.updateSubjectCode(lbl_id, txt_SubCode, txt_SubName);
                subPopulate();
            }
        } else if (TabbedPane.getSelectedIndex() == 2) {
            if (lbl_remarksID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select to Update");
            } else {
                updateSchoolRemark();
                populateRemarks();
            }
        } else if (TabbedPane.getSelectedIndex() == 3) {
            int id = Integer.parseInt(lbl_sid.getText());
            if (id <= 0) {
                JOptionPane.showMessageDialog(null, "Select to update");
            } else {
                mylogics.updateSchoolDetails(id, txt_schoolName, txt_schoolAddress, txt_schoolContact, txt_schoolLocation, txt_schoolVac, txt_schoolResume, status);
                populateSchoolTable();
            }
        }
    }

    //deleting method
    public void deleteAll() {
        if (TabbedPane.getSelectedIndex() == 0) {
            if (lbl_StuID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select Detail to delete");
            } else {
                setStuDel();
                showStudent();
            }
        } else if (TabbedPane.getSelectedIndex() == 1) {
            if (lbl_id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select Detail to delete");
            } else {
                int cid = Integer.parseInt(lbl_id.getText().trim());
                pro.deleteSubject(cid);
                newSubject();
            }
        } else if (TabbedPane.getSelectedIndex() == 2) {
            if (lbl_remarksID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select Detail to delete");
            } else {
                int cid = Integer.parseInt(lbl_remarksID.getText().trim());
                pro.deleteRemarks(cid);
                newRemarks();
                populateRemarks();
            }
        } else if (TabbedPane.getSelectedIndex() == 3) {
            if (lbl_sid.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select Detail to delete");
            } else {
                int sid = Integer.parseInt(lbl_sid.getText().trim());
                pro.deleteSchoolDetails(sid);
                newSchoolDetails();
                populateSchoolTable();
            }
        }
    }

    //Enter new method starts here
    public void clearAll() {
        if (TabbedPane.getSelectedIndex() == 0) {
            pro.allClear("Student");
            Student student = new Student("19901", 10, "Isaac", "Kwadwo", "Nyarko", "0544474706", "Asankrangwa");
            pro.saveStudent(student);
        } else if (TabbedPane.getSelectedIndex() == 1) {
            pro.allClear("Subject");
            Subject subject = new Subject("eng", "English Language");
            pro.saveSubject(subject);
            allSubject();
        } else if (TabbedPane.getSelectedIndex() == 2) {
            pro.allClear("SchoolDetails");
            newRemarks();
            populateRemarks();
            allRemarks();
        } else if (TabbedPane.getSelectedIndex() == 3) {
            pro.allClear("SchoolInfo");
            populateSchoolTable();
        }
    }

    public void subPopulate() {
        if (cmd_Subjects.getSelectedIndex() <= 0) {

        } else {
            mylogics.searchBySubjectCode(lbl_id, txt_SubCode, txt_SubName, cmd_Subjects.getSelectedItem().toString());
        }
    }

    public void saveYear() {
        if (txt_Year.getText().toString().isEmpty()) {
            txt_Year.setBackground(Color.red);
            txt_Year.setText("Enter Year");
        } else {
            mylogics.saveYear(txt_Year);
            allYearCombo();
        }
    }

    public void saveAcademic() {
        if (txt_Academic.getText().toString().isEmpty()) {
            txt_Academic.setBackground(Color.red);
            txt_Academic.setText("Enter Academic Year");
        } else {
            mylogics.saveAcademic(txt_Academic);
            allAcademicYear();
        }
    }

    public void saveTerm() {
        if (txt_Term.getText().toString().isEmpty()) {
            txt_Term.setBackground(Color.red);
            txt_Term.setText("Enter Term");
        } else {
            mylogics.saveTerm(txt_Term);
            allTerm();
        }
    }

    public void saveClasses() {
        if (txt_Classess.getText().toString().isEmpty()) {
            txt_Classess.setBackground(Color.red);
            txt_Classess.setText("Enter Classes");
        } else {
            mylogics.saveClass(txt_Classess);
            allClassess();
        }
    }

    public void saveAll() {
        if (TabbedPane.getSelectedIndex() == 0) {
            saveStudent();
            showTotalStu(cmb_YearBatch);
            showStudent();
        } else if (TabbedPane.getSelectedIndex() == 1) {
            saveSubject();
            allSubject();
        } else if (TabbedPane.getSelectedIndex() == 2) {
            saveRemarks();
            allRemarks();
            populateRemarks();
        } else if (TabbedPane.getSelectedIndex() == 3) {
            mylogics.saveSchoolInfo(txt_schoolName, txt_schoolAddress, txt_schoolContact, txt_schoolLocation, txt_schoolVac, txt_schoolResume, status);
            populateSchoolTable();
        }

    }

    public void newRemarks() {
        txt_lmarks.setText(" ");
        txt_hmarks.setText(" ");
        cmb_gradeTwo.setSelectedIndex(0);
        cmb_remarks.setSelectedIndex(0);
    }

    public void addRemarks() {

        lmarks = Double.parseDouble(txt_lmarks.getText().trim());
        hmarks = Double.parseDouble(txt_hmarks.getText().trim());
        grade = cmb_gradeTwo.getSelectedItem().toString();
        remarks = cmb_remarks.getSelectedItem().toString().trim();

        if (mylogics.returnRemarks(grade) == true) {
            JOptionPane.showMessageDialog(null, "Grade already exist");
        } else {
            SchoolRemarks sRemarks = new SchoolRemarks(lmarks, hmarks, grade, remarks);
            listRemarks.add(sRemarks);
        }

    }

    public void saveRemarks() {

        lmarks = Double.parseDouble(txt_lmarks.getText().trim());
        hmarks = Double.parseDouble(txt_hmarks.getText().trim());
        grade = cmb_gradeTwo.getSelectedItem().toString().trim();
        remarks = cmb_remarks.getSelectedItem().toString().trim();

        if (txt_lmarks.getText().isEmpty() && txt_hmarks.getText().isEmpty() && cmb_gradeTwo.getSelectedIndex() == 0 && cmb_remarks.getSelectedIndex() == 0) {
            for (int i = 0; i < listRemarks.size(); i++) {
                SchoolRemarks rmarks = new SchoolRemarks();
                rmarks = listRemarks.get(i);
                pro.saveSchoolRemarks(rmarks);
            }
        } else if (listRemarks.size() == 0) {

            if (mylogics.returnRemarks(grade) == true) {
                JOptionPane.showMessageDialog(null, "Grade already exist");
            } else {
                SchoolRemarks sRemarks = new SchoolRemarks(lmarks, hmarks, grade, remarks);
                pro.saveSchoolRemarks(sRemarks);
            }
        } else {

            for (int i = 0; i < listRemarks.size(); i++) {
                SchoolRemarks rmarks = new SchoolRemarks();
                rmarks = listRemarks.get(i);
                pro.saveSchoolRemarks(rmarks);
            }

            SchoolRemarks sRemarks = new SchoolRemarks(lmarks, hmarks, grade, remarks);
            pro.saveSchoolRemarks(sRemarks);
        }

        JOptionPane.showMessageDialog(null, "Grading ranges set");

        listRemarks.clear();
    }

    public void updateSchoolRemark() {
        lmarks = Double.parseDouble(txt_lmarks.getText().trim());
        hmarks = Double.parseDouble(txt_hmarks.getText().trim());
        grade = cmb_gradeTwo.getSelectedItem().toString().trim();
        remarks = cmb_remarks.getSelectedItem().toString().trim();
        int id = Integer.parseInt(lbl_remarksID.getText());
        SchoolRemarks sRemarks = new SchoolRemarks(lmarks, hmarks, grade, remarks);
        pro.updateSchoolRemarks(sRemarks, id);
    }

//    public void loadImage() {
//        //Writing to excel
//        JFileChooser chooser = new JFileChooser();
//        chooser.showOpenDialog(null);
//        File f = chooser.getSelectedFile();
//
////        if (f.exists()) {
////            JOptionPane.showMessageDialog(null, "Image was not selected");
////        } else {
//        JOptionPane.showMessageDialog(null, "Image");
//
//        try {
//            filename = f.getAbsolutePath();
//            txt_path.setText(filename);
//            File image = new File(filename);
//            FileInputStream fis = new FileInputStream(image);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] buf = new byte[1024];
//
//            for (int readNum; (readNum = fis.read(buf)) != -1;) {
//                bos.write(buf, 0, readNum);
//            }
//
//            school_logo = bos.toByteArray();
//            ImageIcon icon = new ImageIcon(filename);
//            lbl_Logo.setIcon(icon);
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//
////        }
//    }
    public void schoolTableClick() {
        try {
            int row = table_schoolInfo.getSelectedRow();
            int id = Integer.parseInt(table_schoolInfo.getModel().getValueAt(row, 0).toString());

            List<School> school = mylogics.findAllSchoolDetailsWithID(id);

            for (School sch : school) {

                lbl_sid.setText("" + sch.getSchoolid());
                txt_schoolName.setText(sch.getSchoolName());
                txt_schoolAddress.setText(sch.getSchoolAddress());
                txt_schoolContact.setText(sch.getSchoolContact());
                txt_schoolLocation.setText(sch.getSchoolLocation());
                String dateVacation = sch.getSchoolVac();
                java.util.Date vac = new SimpleDateFormat("MMM EE d, yyyy").parse(dateVacation);
                txt_schoolVac.setDate(vac);

                String dateResume = sch.getSchoolResume();
                java.util.Date resume = new SimpleDateFormat("MMM EE d, yyyy").parse(dateResume);
                txt_schoolResume.setDate(resume);

                if (sch.getPos() == 0) {
                    rd_posandgrade.setSelected(true);
                } else if (sch.getPos() == 1) {
                    rd_pos.setSelected(true);
                } else {
                    rd_grade.setSelected(true);
                }

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

    public void remarksTableClick() {
        try {
            int row = remarks_table.getSelectedRow();
            double lmaks = Double.parseDouble(remarks_table.getModel().getValueAt(row, 1).toString());
            double hmaks = Double.parseDouble(remarks_table.getModel().getValueAt(row, 2).toString());
            String remarks = remarks_table.getModel().getValueAt(row, 3).toString();

            List<SchoolRemarks> remark = mylogics.findRemarksWithID(lmaks, hmaks, remarks);

            for (SchoolRemarks rmk : remark) {
                lbl_remarksID.setText("" + rmk.getId());
                txt_lmarks.setText("" + rmk.getLowMarks());
                txt_hmarks.setText("" + rmk.getHigMarks());
                cmb_gradeTwo.setSelectedItem("" + rmk.getGrd());
                cmb_remarks.setSelectedItem(rmk.getRem());
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

    //===================== Bill methods starts here ==========================
    public void saveConduct() {
        if (txt_conduct.getText().toString().isEmpty()) {
            txt_conduct.setBackground(Color.red);
            txt_conduct.setText("Enter Conduct");
        } else {
            lhand.saveConduct(txt_conduct);
            allConduct();
        }
    }

    public void allConduct() {
        lhand.setConductToCombo(cmb_allConduct, "See All Conduct");
    }

    public void saveInterest() {
        if (txt_interest.getText().toString().isEmpty()) {
            txt_interest.setBackground(Color.red);
            txt_interest.setText("Enter Interest");
        } else {
            lhand.saveInterest(txt_interest);
            allInterest();
        }
    }

    public void allInterest() {
        lhand.setInterestToCombo(cmb_allInterest, "See All Interest");
    }

    public void saveAttitude() {
        if (txt_attitude.getText().toString().isEmpty()) {
            txt_attitude.setBackground(Color.red);
            txt_attitude.setText("Enter Attitude");
        } else {
            lhand.saveAttitude(txt_attitude);
            allAttitude();
        }
    }

    public void allAttitude() {
        lhand.setAttitudeToCombo(cmb_allAttitude, "See All Attitude");
    }

    public void saveBill() {
        if (txt_billItem.getText().toString().isEmpty()) {
            txt_billItem.setBackground(Color.red);
            txt_billItem.setText("Enter Bill Item");
        } else {
            lhand.saveBill(txt_amount, txt_billItem);
            allBills();
        }
    }

    public void allBills() {
        lhand.setBillToCombo(cmb_allBills, "See All Bills");
    }

    public void showConduct() {
        if (cmb_allConduct.getSelectedIndex() <= 0) {
        } else {
            lhand.getSingleConduct(txt_conduct, lbl_conduct, cmb_allConduct.getSelectedItem().toString());
        }
    }

    public void deleteConduct() {
        lhand.deleteConduct(lbl_conduct);
    }

    public void showInterst() {
        if (cmb_allInterest.getSelectedIndex() <= 0) {
        } else {
            lhand.getSingleInterest(txt_interest, lbl_interest, cmb_allInterest.getSelectedItem().toString());
        }
    }

    public void deleteInterest() {
        lhand.deleteInterest(lbl_interest);
    }

    public void showAttitude() {
        if (cmb_allAttitude.getSelectedIndex() <= 0) {
        } else {
            lhand.getSingleAttitude(txt_attitude, lbl_attitude, cmb_allAttitude.getSelectedItem().toString());
        }
    }

    public void deleteAttitude() {
        lhand.deleteAttitude(lbl_attitude);
    }

    public void showBill() {
        if (cmb_allBills.getSelectedIndex() <= 0) {
        } else {
            lhand.getSingleBill(txt_billItem, txt_amount, lbl_bill, cmb_allBills.getSelectedItem().toString());
        }
    }

    public void deleteBill() {
        lhand.deleteBill(lbl_bill);
    }

    //======================= End =============================================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton9 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        TabbedPane = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txt_HTown = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_Contact = new javax.swing.JFormattedTextField();
        lbl_StuID = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txt_MName = new javax.swing.JTextField();
        txt_SName = new javax.swing.JTextField();
        txt_FName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmb_YearBatch = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txt_ID = new javax.swing.JTextField();
        btn_Import = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        stuIdtxt = new javax.swing.JTextField();
        btn_prev = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        getID = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        cmb_Search = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_Search = new javax.swing.JList<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        txt_SubCode = new javax.swing.JTextField();
        txt_SubName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_id = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cmd_Subjects = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cmd_gradingSystem = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        txt_lmarks = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cmb_remarks = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txt_hmarks = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        cmb_gradeTwo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        remarks_table = new javax.swing.JTable();
        lbl_remarksID = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        txt_schoolLocation = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txt_schoolName = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txt_schoolAddress = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lbl_sid = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txt_schoolContact = new javax.swing.JTextField();
        rd_posandgrade = new javax.swing.JRadioButton();
        rd_pos = new javax.swing.JRadioButton();
        rd_grade = new javax.swing.JRadioButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txt_schoolVac = new com.toedter.calendar.JDateChooser();
        txt_schoolResume = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_schoolInfo = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        txt_Academic = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btn_SaveAcademic = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        academic = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        txt_Year = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btn_SaveYear = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        yearCombo = new javax.swing.JComboBox<>();
        jPanel23 = new javax.swing.JPanel();
        txt_Term = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btn_SaveSemester = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Cmd_term = new javax.swing.JComboBox<>();
        jPanel22 = new javax.swing.JPanel();
        txt_Classess = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btn_SaveAcademic1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        classess = new javax.swing.JComboBox<>();
        jPanel18 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        btn_saveAttitude = new javax.swing.JButton();
        cmb_allAttitude = new javax.swing.JComboBox<>();
        btn_DeleteAttitude = new javax.swing.JButton();
        txt_attitude = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lbl_attitude = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        btn_SaveConduct = new javax.swing.JButton();
        cmb_allConduct = new javax.swing.JComboBox<>();
        btn_DeleteConduct = new javax.swing.JButton();
        txt_conduct = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        lbl_conduct = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        btn_SaveInterest = new javax.swing.JButton();
        cmb_allInterest = new javax.swing.JComboBox<>();
        btn_DeleteInterest = new javax.swing.JButton();
        txt_interest = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        lbl_interest = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        btn_SaveBill = new javax.swing.JButton();
        cmb_allBills = new javax.swing.JComboBox<>();
        btn_DeleteBill = new javax.swing.JButton();
        txt_billItem = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
        lbl_bill = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_Save = new javax.swing.JButton();
        btn_New = new javax.swing.JButton();
        btn_Update = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        lbl_Back = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btn_Clear = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNew = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuSave = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuUpdate = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuDelete = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuStudent = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        menuProgram = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        menuCourse = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();

        jButton9.setText("jButton7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ADD NEW STUDENT FORM");
        setPreferredSize(new java.awt.Dimension(1200, 768));

        jScrollPane1.setHorizontalScrollBar(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        TabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        TabbedPane.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Optional Field", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(52, 168, 83))); // NOI18N

        txt_HTown.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_HTown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_HTownActionPerformed(evt);
            }
        });
        txt_HTown.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_HTownKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel8.setText("Hometown");

        jLabel7.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel7.setText("Contact");

        try {
            txt_Contact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("### #### ###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_Contact.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_Contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ContactActionPerformed(evt);
            }
        });
        txt_Contact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ContactKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_StuID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(158, 158, 158))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Contact)
                            .addComponent(txt_HTown))
                        .addContainerGap())))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_Contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_HTown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_StuID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Required Field", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(0, 153, 153))); // NOI18N

        txt_MName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_MName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MNameActionPerformed(evt);
            }
        });
        txt_MName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_MNameKeyPressed(evt);
            }
        });

        txt_SName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_SName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SNameActionPerformed(evt);
            }
        });
        txt_SName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_SNameKeyPressed(evt);
            }
        });

        txt_FName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_FName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_FNameActionPerformed(evt);
            }
        });
        txt_FName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_FNameKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel3.setText("Middle Name");

        jLabel4.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel4.setText("Surname");

        jLabel2.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel2.setText("First Name");

        jLabel6.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel6.setText("Year Batch");

        cmb_YearBatch.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_YearBatch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year Batch" }));
        cmb_YearBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_YearBatchActionPerformed(evt);
            }
        });
        cmb_YearBatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_YearBatchKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel9.setText("Student ID");

        txt_ID.setEditable(false);
        txt_ID.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDActionPerformed(evt);
            }
        });
        txt_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_IDKeyPressed(evt);
            }
        });

        btn_Import.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        btn_Import.setForeground(new java.awt.Color(204, 0, 0));
        btn_Import.setText("Click to Import from Excel");
        btn_Import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ImportActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel14.setText("Total Students in selected Batch :");

        stuIdtxt.setEditable(false);
        stuIdtxt.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        btn_prev.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/prev.png"))); // NOI18N
        btn_prev.setToolTipText("Click to go previous");
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

        getID.setText("56");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_MName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(stuIdtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_prev, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txt_FName)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(171, 171, 171)
                                .addComponent(getID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btn_Import, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_SName)
                            .addComponent(cmb_YearBatch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jSeparator13)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(getID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Import, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(stuIdtxt))
                    .addComponent(btn_prev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_FName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_MName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_SName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cmb_YearBatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Batch to View Names", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(19, 159, 91))); // NOI18N

        cmb_Search.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_SearchActionPerformed(evt);
            }
        });

        txt_Search.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        txt_Search.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txt_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_SearchMouseClicked(evt);
            }
        });
        txt_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txt_Search);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmb_Search, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(cmb_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(743, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPane.addTab("Add Student Details", jPanel3);

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Subject", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(52, 168, 83))); // NOI18N

        txt_SubCode.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_SubCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_SubCodeFocusGained(evt);
            }
        });
        txt_SubCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_SubCodeKeyPressed(evt);
            }
        });

        txt_SubName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_SubName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_SubNameFocusGained(evt);
            }
        });
        txt_SubName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_SubNameKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel12.setText("Subject Code");

        jLabel13.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel13.setText("Subject Name");

        jLabel19.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel19.setText("All Subjects");

        cmd_Subjects.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Subjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_SubjectsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_id, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txt_SubName, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmd_Subjects, 0, 194, Short.MAX_VALUE)
                            .addComponent(txt_SubCode))))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cmd_Subjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_SubCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_SubName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_id, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(813, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(450, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Add Subjects", jPanel5);

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 0, 0));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("ENTER YOUR SCHOOL GRADING SYSTEM");

        cmd_gradingSystem.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("Grading Format");

        txt_lmarks.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_lmarks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_lmarksKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_lmarksKeyTyped(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 51));
        jLabel24.setText("Grade");

        cmb_remarks.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_remarks.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Remarks", "Excellent", "Very Good", "Good", "Credit", "Pass", "Fail", "Highest", "Higher", "High", "High Average", "Average", "Low Average", "Low", "Lower", "Lowest" }));

        jLabel22.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 51));
        jLabel22.setText("Low");

        jLabel25.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 51));
        jLabel25.setText("Remarks");

        txt_hmarks.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_hmarks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_hmarksKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_hmarksKeyTyped(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 51));
        jLabel23.setText("High");

        cmb_gradeTwo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_gradeTwo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Grade", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "A", "B", "C", "D", "E", "F", "G", "A+", "B+", "C+", "D+", "E+", "F+", "G+" }));
        cmb_gradeTwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_gradeTwoActionPerformed(evt);
            }
        });
        cmb_gradeTwo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_gradeTwoKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/plus.png"))); // NOI18N
        jButton1.setToolTipText("Click to add more");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 0, 51));
        jLabel26.setText("eg. 80           eg. 100         eg. 1 or A                 eg. Excellent");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_lmarks)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_hmarks)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmb_gradeTwo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmb_remarks, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23)
                        .addComponent(jLabel24)
                        .addComponent(jLabel25)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_lmarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_hmarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_gradeTwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_remarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        remarks_table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        remarks_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Low Score", "High Score", "Grade", "Remarks"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        remarks_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                remarks_tableMouseClicked(evt);
            }
        });
        remarks_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                remarks_tableKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(remarks_table);
        if (remarks_table.getColumnModel().getColumnCount() > 0) {
            remarks_table.getColumnModel().getColumn(0).setPreferredWidth(9);
        }

        lbl_remarksID.setText("43");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator14)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_remarksID, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmd_gradingSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmd_gradingSystem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_remarksID, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(667, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPane.addTab("Enter Grading System", jPanel4);

        jPanel13.setBackground(new java.awt.Color(102, 102, 102));

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter School Infomation", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(51, 0, 0))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 0, 0));
        jLabel27.setText("School Name");

        txt_schoolLocation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 0, 0));
        jLabel30.setText("Location");

        txt_schoolName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 0, 0));
        jLabel31.setText("Resume Date");

        txt_schoolAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_schoolAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_schoolAddressActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 0, 0));
        jLabel29.setText("Vacation Date");

        jLabel28.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 0, 0));
        jLabel28.setText("Address");

        jLabel37.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(51, 0, 0));
        jLabel37.setText("Contact");

        txt_schoolContact.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_schoolContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_schoolContactActionPerformed(evt);
            }
        });

        buttonGroup1.add(rd_posandgrade);
        rd_posandgrade.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rd_posandgrade.setText("Positions & Grades");
        rd_posandgrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_posandgradeActionPerformed(evt);
            }
        });

        buttonGroup1.add(rd_pos);
        rd_pos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rd_pos.setText("Positions Only");
        rd_pos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_posActionPerformed(evt);
            }
        });

        buttonGroup1.add(rd_grade);
        rd_grade.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rd_grade.setText("Grades Only");
        rd_grade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_gradeActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel38.setText("Select one to be indicated");

        jLabel41.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel41.setText("on student report card");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(lbl_sid, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(rd_pos, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rd_grade))
                            .addComponent(rd_posandgrade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_schoolLocation)
                            .addComponent(txt_schoolContact)
                            .addComponent(txt_schoolAddress)
                            .addComponent(txt_schoolName)
                            .addComponent(txt_schoolVac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_schoolResume, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_schoolName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_schoolAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_schoolContact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_schoolLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_schoolVac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_schoolResume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rd_posandgrade)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rd_pos)
                        .addComponent(rd_grade)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(lbl_sid))
        );

        table_schoolInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Schoolname", "Address", "Contact", "Location", "Vac Date", "Resume Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_schoolInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_schoolInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_schoolInfoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_schoolInfo);
        if (table_schoolInfo.getColumnModel().getColumnCount() > 0) {
            table_schoolInfo.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(552, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPane.addTab("Enter School Details", jPanel13);

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter New Academic Year", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 15), new java.awt.Color(52, 168, 83))); // NOI18N

        txt_Academic.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_Academic.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_AcademicFocusGained(evt);
            }
        });
        txt_Academic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_AcademicKeyPressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel15.setText("Enter Academic Year");

        btn_SaveAcademic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save1.png"))); // NOI18N
        btn_SaveAcademic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveAcademicActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel10.setText("Academic");

        academic.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_SaveAcademic, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(academic, 0, 157, Short.MAX_VALUE)
                    .addComponent(txt_Academic))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(academic, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Academic, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_SaveAcademic, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Year", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 15), new java.awt.Color(52, 168, 83))); // NOI18N

        txt_Year.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_Year.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_YearFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_YearFocusLost(evt);
            }
        });
        txt_Year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_YearActionPerformed(evt);
            }
        });
        txt_Year.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_YearKeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel16.setText("Enter New Year");

        btn_SaveYear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save1.png"))); // NOI18N
        btn_SaveYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveYearActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel1.setText("Years");

        yearCombo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        yearCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_SaveYear, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearCombo, 0, 158, Short.MAX_VALUE)
                    .addComponent(txt_Year))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yearCombo, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Year, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_SaveYear, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Term", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 15), new java.awt.Color(52, 168, 83))); // NOI18N

        txt_Term.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_Term.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_TermFocusGained(evt);
            }
        });
        txt_Term.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_TermKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel18.setText("Enter New Term");

        btn_SaveSemester.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save1.png"))); // NOI18N
        btn_SaveSemester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveSemesterActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel5.setText("Term");

        Cmd_term.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cmd_term, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(btn_SaveSemester, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 187, Short.MAX_VALUE))
                    .addComponent(txt_Term))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cmd_term, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Term, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_SaveSemester, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Class", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 15), new java.awt.Color(52, 168, 83))); // NOI18N

        txt_Classess.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_Classess.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_ClassessFocusGained(evt);
            }
        });
        txt_Classess.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ClassessKeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel17.setText("Enter Class");

        btn_SaveAcademic1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save1.png"))); // NOI18N
        btn_SaveAcademic1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveAcademic1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel11.setText("Classes");

        classess.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(btn_SaveAcademic1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Classess)
                            .addComponent(classess, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16))))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classess, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Classess, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_SaveAcademic1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(509, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Add Class", jPanel6);

        jPanel18.setBackground(new java.awt.Color(102, 102, 102));

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Interest", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        btn_saveAttitude.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_saveAttitude.setText("Save");
        btn_saveAttitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveAttitudeActionPerformed(evt);
            }
        });

        cmb_allAttitude.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_allAttitude.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_allAttitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_allAttitudeActionPerformed(evt);
            }
        });

        btn_DeleteAttitude.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_DeleteAttitude.setText("Delete");
        btn_DeleteAttitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteAttitudeActionPerformed(evt);
            }
        });

        txt_attitude.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel35.setText("All Attitude");

        jLabel36.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel36.setText("Enter New Attitude");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                            .addComponent(btn_saveAttitude)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_DeleteAttitude))
                        .addComponent(cmb_allAttitude, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_attitude, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_attitude, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_allAttitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_attitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_saveAttitude)
                    .addComponent(btn_DeleteAttitude))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_attitude, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add  Conduct", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        btn_SaveConduct.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_SaveConduct.setText("Save");
        btn_SaveConduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveConductActionPerformed(evt);
            }
        });

        cmb_allConduct.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_allConduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_allConduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_allConductActionPerformed(evt);
            }
        });

        btn_DeleteConduct.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_DeleteConduct.setText("Delete");
        btn_DeleteConduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteConductActionPerformed(evt);
            }
        });

        txt_conduct.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel39.setText("All Conduct");

        jLabel40.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel40.setText("Enter New Conduct");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_conduct)
                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                            .addComponent(btn_SaveConduct)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_DeleteConduct))
                        .addComponent(cmb_allConduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_conduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_allConduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_conduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_SaveConduct)
                    .addComponent(btn_DeleteConduct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_conduct, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Interest", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        btn_SaveInterest.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_SaveInterest.setText("Save");
        btn_SaveInterest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveInterestActionPerformed(evt);
            }
        });

        cmb_allInterest.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_allInterest.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_allInterest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_allInterestActionPerformed(evt);
            }
        });

        btn_DeleteInterest.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_DeleteInterest.setText("Delete");
        btn_DeleteInterest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteInterestActionPerformed(evt);
            }
        });

        txt_interest.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel43.setText("All Interest");

        jLabel44.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel44.setText("Enter New Interest");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                            .addComponent(btn_SaveInterest)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_DeleteInterest))
                        .addComponent(cmb_allInterest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_interest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_interest, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_allInterest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_interest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_SaveInterest)
                    .addComponent(btn_DeleteInterest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_interest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Bill", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        btn_SaveBill.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_SaveBill.setText("Save");
        btn_SaveBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveBillActionPerformed(evt);
            }
        });

        cmb_allBills.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_allBills.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_allBills.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_allBillsActionPerformed(evt);
            }
        });

        btn_DeleteBill.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_DeleteBill.setText("Delete");
        btn_DeleteBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteBillActionPerformed(evt);
            }
        });

        txt_billItem.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel45.setText("All Bill Item");

        jLabel46.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel46.setText("Enter New Bill Item");

        jLabel47.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel47.setText("Enter Amount");

        txt_amount.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(lbl_bill, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_allBills, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addComponent(btn_SaveBill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(btn_DeleteBill))
                    .addComponent(txt_billItem)
                    .addComponent(txt_amount))
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_allBills, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_billItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_SaveBill)
                        .addComponent(btn_DeleteBill))
                    .addComponent(lbl_bill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Add Student Bill", jPanel18);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

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

        btn_Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/empty.png"))); // NOI18N
        btn_Clear.setToolTipText("Click to Clear all Records");
        btn_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(lbl_Back)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_Clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Update, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(btn_Delete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_New, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbl_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_New, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btn_Clear, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TabbedPane)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(TabbedPane))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        jMenu1.setText("File");

        menuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newnew.png"))); // NOI18N
        menuNew.setText("New");
        menuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewActionPerformed(evt);
            }
        });
        jMenu1.add(menuNew);
        jMenu1.add(jSeparator4);

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newsave.png"))); // NOI18N
        menuSave.setText("Save");
        menuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSaveActionPerformed(evt);
            }
        });
        jMenu1.add(menuSave);
        jMenu1.add(jSeparator5);

        menuUpdate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        menuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newupdate.png"))); // NOI18N
        menuUpdate.setText("Update");
        menuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUpdateActionPerformed(evt);
            }
        });
        jMenu1.add(menuUpdate);
        jMenu1.add(jSeparator6);

        menuDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        menuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newdelete.png"))); // NOI18N
        menuDelete.setText("Delete");
        menuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDeleteActionPerformed(evt);
            }
        });
        jMenu1.add(menuDelete);
        jMenu1.add(jSeparator7);
        jMenu1.add(jSeparator8);

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/exit.png"))); // NOI18N
        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("View");

        menuStudent.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newstu.png"))); // NOI18N
        menuStudent.setText("Student tab");
        menuStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStudentActionPerformed(evt);
            }
        });
        jMenu2.add(menuStudent);
        jMenu2.add(jSeparator9);

        menuProgram.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuProgram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/program.png"))); // NOI18N
        menuProgram.setText("Subject tab");
        menuProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProgramActionPerformed(evt);
            }
        });
        jMenu2.add(menuProgram);
        jMenu2.add(jSeparator10);

        menuCourse.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuCourse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/course.png"))); // NOI18N
        menuCourse.setText("Grade tab");
        menuCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCourseActionPerformed(evt);
            }
        });
        jMenu2.add(menuCourse);
        jMenu2.add(jSeparator11);
        jMenu2.add(jSeparator12);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 785, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_BackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_BackMouseClicked
        Desktop dp = new Desktop();
        dp.setVisible(true);
        dispose();
    }//GEN-LAST:event_lbl_BackMouseClicked

    private void btn_SaveYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveYearActionPerformed
        saveYear();
    }//GEN-LAST:event_btn_SaveYearActionPerformed

    private void btn_SaveAcademicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveAcademicActionPerformed
        saveAcademic();
    }//GEN-LAST:event_btn_SaveAcademicActionPerformed

    private void btn_SaveSemesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveSemesterActionPerformed
        saveTerm();
    }//GEN-LAST:event_btn_SaveSemesterActionPerformed

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        saveAll();
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void btn_NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NewActionPerformed
        newAll();
    }//GEN-LAST:event_btn_NewActionPerformed

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        updateAll();
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void txt_SubNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SubNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            saveSubject();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_SubCode.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            btn_Save.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_SubNameKeyPressed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        deleteAll();
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void txt_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDActionPerformed
        txt_FName.requestFocusInWindow();
    }//GEN-LAST:event_txt_IDActionPerformed

    private void txt_FNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_FNameActionPerformed
        txt_MName.requestFocusInWindow();
    }//GEN-LAST:event_txt_FNameActionPerformed

    private void txt_MNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MNameActionPerformed
        txt_SName.requestFocusInWindow();
    }//GEN-LAST:event_txt_MNameActionPerformed

    private void txt_SNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SNameActionPerformed
        cmb_YearBatch.requestFocusInWindow();
    }//GEN-LAST:event_txt_SNameActionPerformed

    private void cmb_YearBatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_YearBatchKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_Contact.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txt_Contact.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_SName.requestFocusInWindow();
        }
    }//GEN-LAST:event_cmb_YearBatchKeyPressed

    private void txt_ContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ContactActionPerformed

    }//GEN-LAST:event_txt_ContactActionPerformed

    private void txt_HTownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_HTownActionPerformed
        btn_Save.requestFocusInWindow();
    }//GEN-LAST:event_txt_HTownActionPerformed

    private void txt_IDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_IDKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txt_FName.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {

        }
    }//GEN-LAST:event_txt_IDKeyPressed

    private void txt_FNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_FNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txt_MName.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_ID.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_FNameKeyPressed

    private void txt_MNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_MNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txt_SName.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_FName.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_MNameKeyPressed

    private void txt_SNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            cmb_YearBatch.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_MName.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_SNameKeyPressed

    private void txt_ContactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ContactKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_HTown.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txt_HTown.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            cmb_YearBatch.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_ContactKeyPressed

    private void txt_HTownKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_HTownKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            btn_Save.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_Contact.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_HTownKeyPressed

    private void txt_SubCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SubCodeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_SubName.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            cmd_Subjects.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txt_SubName.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_SubCodeKeyPressed

    private void menuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveActionPerformed
        saveAll();
    }//GEN-LAST:event_menuSaveActionPerformed

    private void menuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewActionPerformed
        newAll();
    }//GEN-LAST:event_menuNewActionPerformed

    private void menuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUpdateActionPerformed
        updateAll();
    }//GEN-LAST:event_menuUpdateActionPerformed

    private void menuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteActionPerformed
        deleteAll();
    }//GEN-LAST:event_menuDeleteActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuStudentActionPerformed
        TabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_menuStudentActionPerformed

    private void menuProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProgramActionPerformed
        TabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_menuProgramActionPerformed

    private void menuCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCourseActionPerformed
        TabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_menuCourseActionPerformed

    private void btn_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearActionPerformed
        int ask = JOptionPane.showConfirmDialog(null, "Do you want to Delete All Records?", "Delete", JOptionPane.YES_NO_OPTION);
        if (ask == 0) {
            clearAll();
        }
    }//GEN-LAST:event_btn_ClearActionPerformed

    private void txt_YearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_YearKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            saveYear();
        }
    }//GEN-LAST:event_txt_YearKeyPressed

    private void txt_AcademicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AcademicKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            saveAcademic();
        }
    }//GEN-LAST:event_txt_AcademicKeyPressed

    private void txt_TermKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TermKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            saveTerm();
        }
    }//GEN-LAST:event_txt_TermKeyPressed

    private void txt_ClassessKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ClassessKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            saveClasses();
        }
    }//GEN-LAST:event_txt_ClassessKeyPressed

    private void btn_SaveAcademic1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveAcademic1ActionPerformed
        saveClasses();
    }//GEN-LAST:event_btn_SaveAcademic1ActionPerformed

    private void txt_YearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_YearFocusGained
        txt_Year.setText(" ");
        txt_Year.setBackground(Color.white);
    }//GEN-LAST:event_txt_YearFocusGained

    private void yearComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboActionPerformed

    }//GEN-LAST:event_yearComboActionPerformed

    private void txt_YearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_YearFocusLost

    }//GEN-LAST:event_txt_YearFocusLost

    private void txt_AcademicFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AcademicFocusGained
        txt_Academic.setText(" ");
        txt_Academic.setBackground(Color.white);
    }//GEN-LAST:event_txt_AcademicFocusGained

    private void txt_YearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_YearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_YearActionPerformed

    private void txt_TermFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_TermFocusGained
        txt_Term.setText(" ");
        txt_Term.setBackground(Color.white);
    }//GEN-LAST:event_txt_TermFocusGained

    private void txt_ClassessFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_ClassessFocusGained
        txt_Classess.setText(" ");
        txt_Classess.setBackground(Color.white);
    }//GEN-LAST:event_txt_ClassessFocusGained

    private void cmb_YearBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_YearBatchActionPerformed
        showTotalStu(cmb_YearBatch);
    }//GEN-LAST:event_cmb_YearBatchActionPerformed

    private void btn_ImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ImportActionPerformed
        try {
            ImportExcel importexcel = new ImportExcel(this, true);
            importexcel.setVisible(true);
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btn_ImportActionPerformed

    private void txt_SubCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SubCodeFocusGained
        txt_SubCode.setText(" ");
        txt_SubCode.setBackground(Color.white);
    }//GEN-LAST:event_txt_SubCodeFocusGained

    private void txt_SubNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SubNameFocusGained
        txt_SubName.setText(" ");
        txt_SubName.setBackground(Color.white);
    }//GEN-LAST:event_txt_SubNameFocusGained

    private void cmb_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_SearchActionPerformed
        showStudent();
    }//GEN-LAST:event_cmb_SearchActionPerformed

    private void txt_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_SearchMouseClicked
        listTableClick();
    }//GEN-LAST:event_txt_SearchMouseClicked

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            listTableClick();
        }
    }//GEN-LAST:event_txt_SearchKeyReleased

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        if (cmb_Search.getSelectedIndex() <= 0) {

        } else {
            btnNext();
        }
    }//GEN-LAST:event_btn_nextActionPerformed

    private void btn_prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prevActionPerformed
        if (cmb_Search.getSelectedIndex() <= 0) {

        } else {
            btnPrevious();
        }
    }//GEN-LAST:event_btn_prevActionPerformed

    private void cmd_SubjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_SubjectsActionPerformed
        subPopulate();
    }//GEN-LAST:event_cmd_SubjectsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txt_lmarks.getText().isEmpty() || txt_hmarks.getText().isEmpty() || cmb_gradeTwo.getSelectedIndex() == 0 || cmb_remarks.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Enter Range");
        } else if (mylogics.returnRemarks(cmb_gradeTwo.getSelectedItem().toString()) == true) {
            JOptionPane.showMessageDialog(null, "Grade already exist");
        } else {
            addRemarks();
            newRemarks();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_lmarksKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_lmarksKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_PERIOD) || (c == KeyEvent.VK_ENTER))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_lmarksKeyTyped

    private void txt_hmarksKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hmarksKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_PERIOD) || (c == KeyEvent.VK_ENTER))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_hmarksKeyTyped

    private void txt_lmarksKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_lmarksKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_hmarks.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txt_hmarks.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            txt_hmarks.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_lmarksKeyReleased

    private void txt_hmarksKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hmarksKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cmb_gradeTwo.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            cmb_gradeTwo.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            cmb_gradeTwo.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            txt_lmarks.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_hmarksKeyReleased

    private void cmb_gradeTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_gradeTwoActionPerformed

    }//GEN-LAST:event_cmb_gradeTwoActionPerformed

    private void cmb_gradeTwoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_gradeTwoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cmb_remarks.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            cmb_remarks.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            cmb_remarks.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            cmb_gradeTwo.requestFocusInWindow();
        }
    }//GEN-LAST:event_cmb_gradeTwoKeyPressed

    private void table_schoolInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_schoolInfoMouseClicked
        schoolTableClick();
    }//GEN-LAST:event_table_schoolInfoMouseClicked

    private void remarks_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remarks_tableMouseClicked
        remarksTableClick();
    }//GEN-LAST:event_remarks_tableMouseClicked

    private void remarks_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_remarks_tableKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            remarksTableClick();
        }
    }//GEN-LAST:event_remarks_tableKeyReleased

    private void btn_SaveConductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveConductActionPerformed
        saveConduct();
    }//GEN-LAST:event_btn_SaveConductActionPerformed

    private void btn_SaveInterestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveInterestActionPerformed
        saveInterest();
    }//GEN-LAST:event_btn_SaveInterestActionPerformed

    private void btn_saveAttitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveAttitudeActionPerformed
        saveAttitude();
    }//GEN-LAST:event_btn_saveAttitudeActionPerformed

    private void btn_SaveBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveBillActionPerformed
        saveBill();
    }//GEN-LAST:event_btn_SaveBillActionPerformed

    private void txt_schoolAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_schoolAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_schoolAddressActionPerformed

    private void txt_schoolContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_schoolContactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_schoolContactActionPerformed

    private void cmb_allConductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_allConductActionPerformed
        showConduct();
    }//GEN-LAST:event_cmb_allConductActionPerformed

    private void btn_DeleteConductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteConductActionPerformed
        deleteConduct();
        allConduct();
    }//GEN-LAST:event_btn_DeleteConductActionPerformed

    private void cmb_allInterestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_allInterestActionPerformed
        showInterst();
    }//GEN-LAST:event_cmb_allInterestActionPerformed

    private void btn_DeleteInterestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteInterestActionPerformed
        deleteInterest();
        allInterest();
    }//GEN-LAST:event_btn_DeleteInterestActionPerformed

    private void cmb_allAttitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_allAttitudeActionPerformed
        showAttitude();
    }//GEN-LAST:event_cmb_allAttitudeActionPerformed

    private void btn_DeleteAttitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteAttitudeActionPerformed
        deleteAttitude();
        allAttitude();
    }//GEN-LAST:event_btn_DeleteAttitudeActionPerformed

    private void cmb_allBillsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_allBillsActionPerformed
        showBill();
    }//GEN-LAST:event_cmb_allBillsActionPerformed

    private void btn_DeleteBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteBillActionPerformed
        deleteBill();
        allBills();
    }//GEN-LAST:event_btn_DeleteBillActionPerformed

    private void rd_posandgradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_posandgradeActionPerformed
        status = 0;
    }//GEN-LAST:event_rd_posandgradeActionPerformed

    private void rd_posActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_posActionPerformed
        status = 1;
    }//GEN-LAST:event_rd_posActionPerformed

    private void rd_gradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_gradeActionPerformed
        status = 2;
    }//GEN-LAST:event_rd_gradeActionPerformed

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
            java.util.logging.Logger.getLogger(AddNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Cmd_term;
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JComboBox<String> academic;
    private javax.swing.JButton btn_Clear;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_DeleteAttitude;
    private javax.swing.JButton btn_DeleteBill;
    private javax.swing.JButton btn_DeleteConduct;
    private javax.swing.JButton btn_DeleteInterest;
    private javax.swing.JButton btn_Import;
    private javax.swing.JButton btn_New;
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_SaveAcademic;
    private javax.swing.JButton btn_SaveAcademic1;
    private javax.swing.JButton btn_SaveBill;
    private javax.swing.JButton btn_SaveConduct;
    private javax.swing.JButton btn_SaveInterest;
    private javax.swing.JButton btn_SaveSemester;
    private javax.swing.JButton btn_SaveYear;
    private javax.swing.JButton btn_Update;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_prev;
    private javax.swing.JButton btn_saveAttitude;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> classess;
    private javax.swing.JComboBox<String> cmb_Search;
    private javax.swing.JComboBox<String> cmb_YearBatch;
    private javax.swing.JComboBox<String> cmb_allAttitude;
    private javax.swing.JComboBox<String> cmb_allBills;
    private javax.swing.JComboBox<String> cmb_allConduct;
    private javax.swing.JComboBox<String> cmb_allInterest;
    private javax.swing.JComboBox<String> cmb_gradeTwo;
    private javax.swing.JComboBox<String> cmb_remarks;
    private javax.swing.JComboBox<String> cmd_Subjects;
    private javax.swing.JComboBox<String> cmd_gradingSystem;
    private javax.swing.JLabel getID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JLabel lbl_Back;
    private javax.swing.JLabel lbl_StuID;
    private javax.swing.JLabel lbl_attitude;
    private javax.swing.JLabel lbl_bill;
    private javax.swing.JLabel lbl_conduct;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_interest;
    private javax.swing.JLabel lbl_remarksID;
    private javax.swing.JLabel lbl_sid;
    private javax.swing.JMenuItem menuCourse;
    private javax.swing.JMenuItem menuDelete;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuNew;
    private javax.swing.JMenuItem menuProgram;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JMenuItem menuStudent;
    private javax.swing.JMenuItem menuUpdate;
    private javax.swing.JRadioButton rd_grade;
    private javax.swing.JRadioButton rd_pos;
    private javax.swing.JRadioButton rd_posandgrade;
    private javax.swing.JTable remarks_table;
    private javax.swing.JTextField stuIdtxt;
    private javax.swing.JTable table_schoolInfo;
    private javax.swing.JTextField txt_Academic;
    private javax.swing.JTextField txt_Classess;
    private javax.swing.JFormattedTextField txt_Contact;
    private javax.swing.JTextField txt_FName;
    private javax.swing.JTextField txt_HTown;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_MName;
    private javax.swing.JTextField txt_SName;
    private javax.swing.JList<String> txt_Search;
    private javax.swing.JTextField txt_SubCode;
    private javax.swing.JTextField txt_SubName;
    private javax.swing.JTextField txt_Term;
    private javax.swing.JTextField txt_Year;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_attitude;
    private javax.swing.JTextField txt_billItem;
    private javax.swing.JTextField txt_conduct;
    private javax.swing.JTextField txt_hmarks;
    private javax.swing.JTextField txt_interest;
    private javax.swing.JTextField txt_lmarks;
    private javax.swing.JTextField txt_schoolAddress;
    private javax.swing.JTextField txt_schoolContact;
    private javax.swing.JTextField txt_schoolLocation;
    private javax.swing.JTextField txt_schoolName;
    private com.toedter.calendar.JDateChooser txt_schoolResume;
    private com.toedter.calendar.JDateChooser txt_schoolVac;
    private javax.swing.JComboBox<String> yearCombo;
    // End of variables declaration//GEN-END:variables

    String filename = null;
    int s = 0;
    byte[] school_logo = null;

    private int status;
}

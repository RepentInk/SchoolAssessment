package Designs;

import Classes.Academic;
import Classes.Assessment;
import Classes.Exams;
import Classes.Result;
import Classes.School;
import Classes.SchoolRemarks;
import Classes.Subject;
import Classes.Term;
import Classes.Year;
import Connects.ProgramDAO;
import Connects.myCon;
import Connects.myLogics;
import static Designs.ReceiptForm.pane;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Receipt extends javax.swing.JFrame {

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    ProgramDAO pro = new ProgramDAO();
    myLogics mylogics = new myLogics();

    public Receipt() {
        initComponents();
        conn = myCon.ConnecrDb();
        setIconImage(mets.myImage("/Icons/globe.png"));

        mets.StartTim(lbl_Date, lbl_Time);
        allClassess();

        lbl_Date.setVisible(false);
        lbl_Time.setVisible(false);
    }

    public void allClassess() {
        mylogics.setClassToCombo(cmd_class, "Select Class");
        mylogics.setAcademicToCombo(cmb_Academic, "Select Academic Year");
        mylogics.setTermToCombo(cmd_term, "Select Term");
        mylogics.setYearToCombo(cmd_Batch, "Select Year Batch");
        mylogics.setSubjectToCombo(cmb_Subject, "Select Subject");
    }

    public void setPageExamination() {

        String term = cmd_term.getSelectedItem().toString().toUpperCase().trim();
        String academic = cmb_Academic.getSelectedItem().toString().toUpperCase().trim();
        String batch = cmd_Batch.getSelectedItem().toString().toUpperCase().trim();
        String subject = mylogics.subjectName(cmb_Subject.getSelectedItem().toString());

        int ter = mylogics.termID(cmd_term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int sub = mylogics.subjectID(cmb_Subject.getSelectedItem().toString().trim());
        int yer = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_class.getSelectedItem().toString().trim());

        String studentClass = cmd_class.getSelectedItem().toString().trim();
        String date = lbl_Date.getText();
        String time = lbl_Time.getText();

        // get location of the code source
        URL url = Designs.Receipt.class.getProtectionDomain().getCodeSource().getLocation();
        String schoolName = "";
        String address = "";
        String schoolLocation = "";

        byte[] imagedata = null;

        int count = 1, pos = 0;
        double total, tot = 0;

        List<School> school = pro.findAllSchoolDetails();

        for (School res : school) {
            schoolName = res.getSchoolName();
            address = res.getSchoolAddress();
            schoolLocation = res.getSchoolLocation();
            imagedata = res.getSchoolLogo();
        }

        try {
            // extract directory from code source url
            String root = (new File(url.toURI())).getParentFile().getPath();
            File doc = new File(root, "newhtml.html");
            // create htm file contents for testing
            FileWriter writer = new FileWriter(doc);
            writer.write("<h1 style='text-align: center; margin-bottom:0px;'>" + schoolName.toUpperCase() + "</h1>"
                    + "<h3 style='text-align: center;'>" + address.toUpperCase() + " " + schoolLocation.toUpperCase() + "</h3>"
                    + "<h3 style='text-align: center;'>END OF " + term + " " + academic + " EXAMINATION RESULT</h3><hr>"
                    + "<table><tbody>"
                    + "<tr>"
                    + "<td style='text-align:left; width:240px'><b>SUBJECT : </b>" + subject.toUpperCase() + "</td>"
                    + "<td style=''><b>CLASS : </b>" + studentClass.toUpperCase() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='text-align:left; width:240px'><b>DATE :</b> " + date.toUpperCase() + " " + time + "</td>"
                    + "<td style=''><b>BATCH : </b>" + batch + "</td>"
                    + "</tr>"
                    + "</tbody></table>"
                    + "</b>");
            String val = "";

            val += "<hr><table style='font-size:9px;'>"
                    + "<thead style='border-bottom: 1px dotted black'>"
                    + "<tr>"
                    + "<th style='text-align: left;'>Id</th>"
                    + "<th style='text-align: left;'>Fullname</th>"
                    + "<th style='text-align: left;'>Class</th>"
                    + "<th style='text-align: left;'>Exams</th>"
                    + "<th style='text-align: left;'>Total</th>"
                    + "<th style='text-align: left;'>Grd</th>"
                    + "<th style='text-align: left;'>Pos</th>"
                    + "<th style='text-align: left;'>Remarks</th>"
                    + "</tr><hr>"
                    + "</thead><tbody style='font-size:7px>";

            List<Result> result = pro.findAllResultSpecific(ter, aca, sub, yer, clas);
            for (Result res : result) {
                total = tot;

                tot = res.getTotalResult();

                if (tot > total) {
                    pos = count;
                } else if (tot == total) {
                    pos = pos;
                    count = pos + 1;
                } else {
                    pos = count;
                }

                val += "<tr>"
                        + "<td style='text-align: left; width:40px'><b>" + mylogics.studentIDAssigned(res.getStuId(), res.getYear_id()) + "</b></td>"
                        + "<td style='text-align: left; width:300px'>" + mylogics.findStudentByIDName(res.getStuId(), res.getYear_id()) + "</td>"
                        + "<td style='text-align: left; color:blue; width:20px'>" + res.getAssessment() + "</td>"
                        + "<td style='text-align: left; color:blue; width:20px'>" + res.getExams() + "</td>"
                        + "<td style='text-align: left; color:red; width:20px'>" + res.getTotalResult() + "</td>"
                        + "<td style='text-align: left; width:9px'>" + res.getGrade() + "</td>"
                        + "<td style='text-align: left; color:red; width:9px'>" + pos + "</td>"
                        + "<td style='text-align: left; color:blue; width:60px'>" + mylogics.returnRemarksOnID(res.getRemarks()) + "</td>"
                        + "</tr>";
                count++;
            };

            val += "</tbody></table><hr>"
                    + "<i style='text-align:center'>@https://www.trinitysoftwarecenter.com || Contacts : 0500383888 or 0544474706</i>";

            writer.write(val);
            writer.close();
            // open it in the editor
            pane.setPage(doc.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPageAssessment() {

        String term = cmd_term.getSelectedItem().toString().toUpperCase().trim();
        String academic = cmb_Academic.getSelectedItem().toString().toUpperCase().trim();
        String batch = cmd_Batch.getSelectedItem().toString().toUpperCase().trim();
        String subject = mylogics.subjectName(cmb_Subject.getSelectedItem().toString());

        int ter = mylogics.termID(cmd_term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int sub = mylogics.subjectID(cmb_Subject.getSelectedItem().toString().trim());
        int yer = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_class.getSelectedItem().toString().trim());

        String studentClass = cmd_class.getSelectedItem().toString().trim();
        String date = lbl_Date.getText();
        String time = lbl_Time.getText();

        // get location of the code source
        URL url = Designs.Receipt.class.getProtectionDomain().getCodeSource().getLocation();
        String schoolName = "";
        String address = "";
        String schoolLocation = "";

        byte[] imagedata = null;

        List<School> school = pro.findAllSchoolDetails();

        for (School res : school) {
            schoolName = res.getSchoolName();
            address = res.getSchoolAddress();
            schoolLocation = res.getSchoolLocation();
            imagedata = res.getSchoolLogo();
        }

        try {
            // extract directory from code source url
            String root = (new File(url.toURI())).getParentFile().getPath();
            File doc = new File(root, "newhtml.html");
            // create htm file contents for testing
            FileWriter writer = new FileWriter(doc);
            writer.write("<h1 style='text-align: center; margin-bottom:0px;'>" + schoolName.toUpperCase() + "</h1>"
                    + "<h3 style='text-align: center;'>" + address.toUpperCase() + " " + schoolLocation.toUpperCase() + "</h3>"
                    + "<h3 style='text-align: center;'>END OF " + term + " " + academic + " EXAMINATION RESULT</h3><hr>"
                    + "<table><tbody>"
                    + "<tr>"
                    + "<td style='text-align:left; width:240px'><b>SUBJECT : </b>" + subject.toUpperCase() + "</td>"
                    + "<td style=''><b>CLASS : </b>" + studentClass.toUpperCase() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='text-align:left; width:240px'><b>DATE :</b> " + date.toUpperCase() + " " + time + "</td>"
                    + "<td style=''><b>BATCH : </b>" + batch + "</td>"
                    + "</tr>"
                    + "</tbody></table>"
                    + "</b>");
            String val = "";

            val += "<hr><table style='font-size:9px;'>"
                    + "<thead style='border-bottom: 1px dotted black'>"
                    + "<tr>"
                    + "<th style='text-align: left;'>Id</th>"
                    + "<th style='text-align: left;'>Fullname</th>"
                    + "<th style='text-align: left;'>Test</th>"
                    + "<th style='text-align: left;'>Others</th>"
                    + "<th style='text-align: left;'>Total</th>"
                    + "<th style='text-align: left;'>Percent</th>"
                    + "</tr><hr>"
                    + "</thead><tbody style='font-size:7px>";

            //List<Result> result = pro.findAllResultSpecific(ter, aca, sub, yer, clas);
            List<Assessment> assess = pro.findAllAssessment(ter, aca, sub, yer, clas);

            for (Assessment assessment : assess) {

                val += "<tr>"
                        + "<td style='text-align: left; width:40px'><b>" + mylogics.studentIDAssigned(assessment.getStuId(), assessment.getYear_id()) + "</b></td>"
                        + "<td style='text-align: left; width:260px'>" + mylogics.findStudentByIDName(assessment.getStuId(), assessment.getYear_id()) + "</td>"
                        + "<td style='text-align: left; color:blue; width:20px'>" + assessment.getClassTest() + "</td>"
                        + "<td style='text-align: left; color:blue; width:20px'>" + assessment.getOthers() + "</td>"
                        + "<td style='text-align: left; color:red; width:20px'>" + assessment.getTotal() + "</td>"
                        + "<td style='text-align: left; width:9px'>" + assessment.getFiftyPercentAssessment() + "</td>";

            };

            val += "</tbody></table><hr>"
                    + "<i style='text-align:center'>@https://www.trinitysoftwarecenter.com || Contacts : 0500383888 or 0544474706</i>";

            writer.write(val);
            writer.close();
            // open it in the editor
            pane.setPage(doc.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPageExams() {

        String term = cmd_term.getSelectedItem().toString().toUpperCase().trim();
        String academic = cmb_Academic.getSelectedItem().toString().toUpperCase().trim();
        String batch = cmd_Batch.getSelectedItem().toString().toUpperCase().trim();
        String subject = mylogics.subjectName(cmb_Subject.getSelectedItem().toString());

        int ter = mylogics.termID(cmd_term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int sub = mylogics.subjectID(cmb_Subject.getSelectedItem().toString().trim());
        int yer = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_class.getSelectedItem().toString().trim());

        String studentClass = cmd_class.getSelectedItem().toString().trim();
        String date = lbl_Date.getText();
        String time = lbl_Time.getText();

        // get location of the code source
        URL url = Designs.Receipt.class.getProtectionDomain().getCodeSource().getLocation();
        String schoolName = "";
        String address = "";
        String schoolLocation = "";

        byte[] imagedata = null;

        List<School> school = pro.findAllSchoolDetails();

        for (School res : school) {
            schoolName = res.getSchoolName();
            address = res.getSchoolAddress();
            schoolLocation = res.getSchoolLocation();
            imagedata = res.getSchoolLogo();
        }

        try {
            // extract directory from code source url
            String root = (new File(url.toURI())).getParentFile().getPath();
            File doc = new File(root, "newhtml.html");
            // create htm file contents for testing
            FileWriter writer = new FileWriter(doc);
            writer.write("<h1 style='text-align: center; margin-bottom:0px;'>" + schoolName.toUpperCase() + "</h1>"
                    + "<h3 style='text-align: center;'>" + address.toUpperCase() + " " + schoolLocation.toUpperCase() + "</h3>"
                    + "<h3 style='text-align: center;'>END OF " + term + " " + academic + " EXAMINATION RESULT</h3><hr>"
                    + "<table><tbody>"
                    + "<tr>"
                    + "<td style='text-align:left; width:240px'><b>SUBJECT : </b>" + subject.toUpperCase() + "</td>"
                    + "<td style=''><b>CLASS : </b>" + studentClass.toUpperCase() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='text-align:left; width:240px'><b>DATE :</b> " + date.toUpperCase() + " " + time + "</td>"
                    + "<td style=''><b>BATCH : </b>" + batch + "</td>"
                    + "</tr>"
                    + "</tbody></table>"
                    + "</b>");
            String val = "";

            val += "<hr><table style='font-size:9px;'>"
                    + "<thead style='border-bottom: 1px dotted black'>"
                    + "<tr>"
                    + "<th style='text-align: left;'>Id</th>"
                    + "<th style='text-align: left;'>Fullname</th>"
                    + "<th style='text-align: left;'>Obj</th>"
                    + "<th style='text-align: left;'>Theory</th>"
                    + "<th style='text-align: left;'>Total</th>"
                    + "<th style='text-align: left;'>Percent</th>"
                    + "</tr><hr>"
                    + "</thead><tbody style='font-size:7px>";

            List<Exams> exams = pro.findAllExams(ter, aca, sub, yer, clas);

            for (Exams exe : exams) {

                val += "<tr>"
                        + "<td style='text-align: left; width:40px'><b>" + mylogics.studentIDAssigned(exe.getStuId(), exe.getYear_id()) + "</b></td>"
                        + "<td style='text-align: left; width:260px'>" + mylogics.findStudentByIDName(exe.getStuId(), exe.getYear_id()) + "</td>"
                        + "<td style='text-align: left; color:blue; width:20px'>" + exe.getObjectives() + "</td>"
                        + "<td style='text-align: left; color:blue; width:20px'>" + exe.getTheory() + "</td>"
                        + "<td style='text-align: left; color:red; width:20px'>" + exe.getTotalExams() + "</td>"
                        + "<td style='text-align: left; width:9px'>" + exe.getFiftyPercentExams() + "</td>";

            };

            val += "</tbody></table><hr>"
                    + "<i style='text-align:center'>@https://www.trinitysoftwarecenter.com || Contacts : 0500383888 or 0544474706</i>";

            writer.write(val);
            writer.close();
            // open it in the editor
            pane.setPage(doc.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cmd_Batch = new javax.swing.JComboBox<>();
        btn_Result = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmb_Academic = new javax.swing.JComboBox<>();
        btn_Quiz = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmb_Subject = new javax.swing.JComboBox<>();
        cmd_class = new javax.swing.JComboBox<>();
        lbl_Date = new javax.swing.JLabel();
        btn_Report = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbl_Time = new javax.swing.JLabel();
        lbl_back = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmd_term = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RECEIPT FORM");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Requirement Before Printing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(153, 0, 0))); // NOI18N

        cmd_Batch.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Batch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_BatchActionPerformed(evt);
            }
        });

        btn_Result.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/result.png"))); // NOI18N
        btn_Result.setToolTipText("Click to Print Assessment Report");
        btn_Result.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResultActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Agency FB", 1, 16)); // NOI18N
        jLabel3.setText("Subject");

        jLabel2.setFont(new java.awt.Font("Agency FB", 1, 16)); // NOI18N
        jLabel2.setText("Academic Year");

        cmb_Academic.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_Academic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_AcademicActionPerformed(evt);
            }
        });

        btn_Quiz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quiz.png"))); // NOI18N
        btn_Quiz.setToolTipText("Click to Print Quiz");
        btn_Quiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QuizActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Agency FB", 1, 16)); // NOI18N
        jLabel4.setText("Year Batch");

        cmb_Subject.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_Subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_SubjectActionPerformed(evt);
            }
        });

        cmd_class.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_class.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_classActionPerformed(evt);
            }
        });

        lbl_Date.setText("Date");

        btn_Report.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/report.png"))); // NOI18N
        btn_Report.setToolTipText("Click to Print Examination Report");
        btn_Report.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ReportActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Agency FB", 1, 16)); // NOI18N
        jLabel1.setText("Class");

        lbl_Time.setText("Time");

        lbl_back.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        lbl_back.setForeground(new java.awt.Color(0, 0, 204));
        lbl_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/back.png"))); // NOI18N
        lbl_back.setToolTipText("Click to go back");
        lbl_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_backMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Agency FB", 1, 16)); // NOI18N
        jLabel5.setText("Term");

        cmd_term.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_term.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_termActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_back, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_Quiz, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_Result, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Report, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmb_Academic, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_Subject, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_Batch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_class, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Time, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_term, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmd_class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmd_term, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmb_Academic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmb_Subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmd_Batch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Date)
                    .addComponent(lbl_Time))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btn_Report)
                        .addComponent(btn_Result, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Quiz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lbl_back, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmd_classActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_classActionPerformed
        //txt_Q1.setText("" + semID());
    }//GEN-LAST:event_cmd_classActionPerformed

    private void cmb_AcademicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_AcademicActionPerformed
        // txt_Q2.setText("" + academicID());
    }//GEN-LAST:event_cmb_AcademicActionPerformed

    private void cmb_SubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_SubjectActionPerformed
        // txt_EOBJ.setText("" + courseID());
    }//GEN-LAST:event_cmb_SubjectActionPerformed

    private void cmd_BatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_BatchActionPerformed
        // txt_EQ1.setText("" + batchID());
    }//GEN-LAST:event_cmd_BatchActionPerformed

    private void btn_ReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ReportActionPerformed

        if (cmd_class.getSelectedIndex() == 0 || cmb_Academic.getSelectedIndex() == 0 || cmb_Subject.getSelectedIndex() == 0 || cmd_Batch.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the following Semester, Academic Year, Subject and Batch Before Printing");
        } else {

            ReceiptForm rcp = new ReceiptForm();
            setPageExamination();
            rcp.setLocationRelativeTo(this);
            rcp.setAlwaysOnTop(true);
            rcp.setVisible(true);

        }
    }//GEN-LAST:event_btn_ReportActionPerformed

    private void btn_ResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResultActionPerformed

        if (cmd_class.getSelectedIndex() == 0 || cmb_Academic.getSelectedIndex() == 0 || cmb_Subject.getSelectedIndex() == 0 || cmd_Batch.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the following Semester, Academic Year, Subject and Batch Before Printing");
        } else {
            ReceiptForm rcp = new ReceiptForm();
            setPageAssessment();
            rcp.setLocationRelativeTo(this);
            rcp.setAlwaysOnTop(true);
            rcp.setVisible(true);
        }
    }//GEN-LAST:event_btn_ResultActionPerformed

    private void btn_QuizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QuizActionPerformed

        if (cmd_class.getSelectedIndex() == 0 || cmb_Academic.getSelectedIndex() == 0 || cmb_Subject.getSelectedIndex() == 0 || cmd_Batch.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the following Semester, Academic Year, Subject and Batch Before Printing");
        } else {
            ReceiptForm rcp = new ReceiptForm();
            setPageExams();
            rcp.setLocationRelativeTo(this);
            rcp.setAlwaysOnTop(true);
            rcp.setVisible(true);
        }
    }//GEN-LAST:event_btn_QuizActionPerformed

    private void lbl_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_backMouseClicked
        Decide dec = new Decide();
        dec.setVisible(true);
        dispose();
    }//GEN-LAST:event_lbl_backMouseClicked

    private void cmd_termActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_termActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmd_termActionPerformed

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
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Receipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Quiz;
    private javax.swing.JButton btn_Report;
    private javax.swing.JButton btn_Result;
    public static javax.swing.JComboBox<String> cmb_Academic;
    public static javax.swing.JComboBox<String> cmb_Subject;
    public static javax.swing.JComboBox<String> cmd_Batch;
    public static javax.swing.JComboBox<String> cmd_class;
    public static javax.swing.JComboBox<String> cmd_term;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_Date;
    private javax.swing.JLabel lbl_Time;
    private javax.swing.JLabel lbl_back;
    // End of variables declaration//GEN-END:variables
}

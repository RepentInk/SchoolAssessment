package Designs;

import Classes.Bill;
import Classes.Exams;
import Classes.Result;
import Classes.School;
import Classes.TermInfo;
import Connects.ProgramDAO;
import Connects.billMethods;
import Connects.logicHandler;
import Connects.myCon;
import Connects.myLogics;
import static Designs.AssessmentForm.lbl_Date;
import static Designs.Receipt.cmd_class;
import static Designs.ReceiptForm.pane;
import static com.sun.javafx.animation.TickCalculation.sub;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StudentForm extends javax.swing.JFrame {

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int count = 0;

    logicHandler lhand = new logicHandler();
    billMethods bmet = new billMethods();

    ProgramDAO pro = new ProgramDAO();
    myLogics mylogics = new myLogics();
    DecimalFormat df2 = new DecimalFormat("####.#");

    public StudentForm() {
        initComponents();
        setIconImage(mets.myImage("/icons/globe.png"));

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        allClassess();
        cmd_StudentID.setVisible(false);
        PerformanceColors();
        BillTableColors();

        allConduct();
        allAttitude();
        allInterest();
        allBill();
        sumupBills();

        lbl_id.setVisible(false);
        txt_totalMarks.setVisible(false);

    }

    public void allClassess() {
        mylogics.setClassToCombo(cmd_Class, "Select Class");
        mylogics.setPromotedToCombo(cmb_promoteTo, "Select Promoted To Class");
        mylogics.setAcademicToCombo(cmb_Academic, "Select Academic Year");
        mylogics.setTermToCombo(cmd_Term, "Select Term");
        mylogics.setYearToCombo(cmd_Batch, "Select Year Batch");
    }

    public void setEmpty() {
        cmb_attitude.setSelectedIndex(0);
        cmb_conduct.setSelectedIndex(0);
        cmb_interest.setSelectedIndex(0);
        cmb_attendance.setSelectedIndex(0);
        cmb_outof.setSelectedIndex(0);
        cmb_promoteTo.setSelectedIndex(0);
        txt_teachersRemarks.setText("");
        txt_HeadTeachersRemarks.setText("");
        txt_balance.setText("");
        txt_totalFees.setText("");
    }

    public void studentName(JComboBox myComboNam) {
        if (myComboNam.getSelectedIndex() <= 0) {

        } else {
            mylogics.setStudentNameToCombo(cmd_StudentName, "--Select--", mylogics.yearID(myComboNam.getSelectedItem().toString()), cmd_StudentID);
        }
    }

    public void setStudentPerformance(JComboBox myComboNam) {
        int stuid = mylogics.findStudentReport(cmd_StudentName.getSelectedItem().toString().trim(), mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim()));
        int clas = mylogics.classID(cmd_Class.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int term = mylogics.termID(cmd_Term.getSelectedItem().toString().trim());
        int year = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());

        if (myComboNam.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Please Select Student");
        } else if (cmd_StudentName.getSelectedIndex() <= 0 || cmd_Class.getSelectedIndex() <= 0 || cmb_Academic.getSelectedIndex() <= 0 || cmd_Term.getSelectedIndex() <= 0 || cmd_Batch.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Please Select All term Info");
        } else {
            mylogics.setStudentPerformanceTable(studentPerformanceTable, stuid, clas, aca, term, year);
        }
    }

    public void PerformanceColors() {
        studentPerformanceTable.getColumnModel().getColumn(0).setCellRenderer(new myCon.CustomRenderer());
        studentPerformanceTable.getColumnModel().getColumn(1).setCellRenderer(new myCon.CustomRenderer1());
        studentPerformanceTable.getColumnModel().getColumn(2).setCellRenderer(new myCon.CustomRenderer2());
        studentPerformanceTable.getColumnModel().getColumn(3).setCellRenderer(new myCon.CustomRenderer3());
        studentPerformanceTable.getColumnModel().getColumn(4).setCellRenderer(new myCon.CustomRenderer4());
        studentPerformanceTable.getColumnModel().getColumn(5).setCellRenderer(new myCon.CustomRenderer5());
        studentPerformanceTable.getTableHeader().setDefaultRenderer(new myCon.HeaderColor());
    }

    public void BillTableColors() {
        bill_table.getColumnModel().getColumn(0).setCellRenderer(new myCon.CustomRenderer());
        bill_table.getColumnModel().getColumn(1).setCellRenderer(new myCon.CustomRenderer1());
        bill_table.getColumnModel().getColumn(2).setCellRenderer(new myCon.CustomRenderer2());
        bill_table.getTableHeader().setDefaultRenderer(new myCon.HeaderColor());
    }

    public void allConduct() {
        lhand.setConductToCombo(cmb_conduct, "See All Conduct");
    }

    public void allAttitude() {
        lhand.setAttitudeToCombo(cmb_attitude, "See All Attitude");
    }

    public void allInterest() {
        lhand.setInterestToCombo(cmb_interest, "See All Interest");
    }

    public void allBill() {
        lhand.billToTable(bill_table);
    }

    public void sumupBills() {
        DecimalFormat df2 = new DecimalFormat("####.###");
        double total = 0;

        int rowcount = bill_table.getRowCount();
        double sum = 0;
        for (int i = 0; i < rowcount; i++) {
            sum = sum + Double.parseDouble(bill_table.getValueAt(i, 2).toString());
        }
        total = Double.valueOf(df2.format(sum));
        txt_totalbill.setText(Double.toString(total));
    }

    public void studentTotalMarks() {
        DecimalFormat df2 = new DecimalFormat("####.###");
        double total = 0;

        int rowcount = studentPerformanceTable.getRowCount();
        double sum = 0;
        for (int i = 0; i < rowcount; i++) {
            sum = sum + Double.parseDouble(studentPerformanceTable.getValueAt(i, 3).toString());
        }
        total = Double.valueOf(df2.format(sum));
        txt_totalMarks.setText(Double.toString(total));
    }

    public void sumupTermFees() {
        DecimalFormat df2 = new DecimalFormat("####.###");
        double total = 0;

        double billFees = Double.parseDouble(txt_totalbill.getText().toString());
        double feesBalance = 0.0;

        if (txt_balance.getText().isEmpty()) {
            feesBalance = 0.0;
        } else {
            feesBalance = Double.parseDouble(txt_balance.getText().toString());
        }

        double sum = billFees + feesBalance;

        total = Double.valueOf(df2.format(sum));
        txt_totalFees.setText(Double.toString(total));
    }

    public void saveTermInfo() {
        lhand.saveTermInfo(cmd_Class, cmb_Academic, cmd_Batch, cmd_StudentName, cmb_attitude, cmb_conduct, cmb_interest, cmd_Term, txt_teachersRemarks, txt_HeadTeachersRemarks, txt_balance, txt_totalFees, cmb_attendance, cmb_outof, cmb_promoteTo, txt_totalMarks);
    }

    public void updateTermInfo() {
        lhand.updateTermInfo(lbl_id, cmb_attitude, cmb_conduct, cmb_interest, txt_teachersRemarks, txt_HeadTeachersRemarks, txt_balance, txt_totalFees, cmb_attendance, cmb_outof, cmb_promoteTo, txt_totalMarks);
    }

    public void aStudentTermInfo() {
        int cd = mylogics.classID(cmd_Class.getSelectedItem().toString());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString());
        int yd = mylogics.yearID(cmd_Batch.getSelectedItem().toString());
        int sid = mylogics.findStudentReport(cmd_StudentName.getSelectedItem().toString().trim(), mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim()));
        int term = mylogics.termID(cmd_Term.getSelectedItem().toString());

        List<TermInfo> terminfo = bmet.returnTermInfo(cd, aca, yd, sid, term);

        for (TermInfo info : terminfo) {
            cmb_attitude.setSelectedItem(bmet.returnAttitudeName(info.getAttiID()));
            cmb_conduct.setSelectedItem(bmet.returnConductName(info.getConID()));
            cmb_interest.setSelectedItem(bmet.returnInterestName(info.getIntID()));
            txt_teachersRemarks.setText("" + info.getTeacherRemarks());
            txt_HeadTeachersRemarks.setText("" + info.getHeadRemarks());
            txt_balance.setText("" + info.getBalance());
            txt_totalFees.setText("" + info.getTotalfees());
            lbl_id.setText("" + info.getId());
            cmb_attendance.setSelectedItem("" + info.getAttendance());
            cmb_outof.setSelectedItem("" + info.getOutof());
            cmb_promoteTo.setSelectedItem(mylogics.returnClassName(info.getPromoted()));
        }
    }

    public String positionStu() {
        int count = 1, pos = 0;
        double total, tot = 0;

        String rpos = "";

        int ter = mylogics.termID(cmd_Term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int yer = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_Class.getSelectedItem().toString().trim());
        int idd = Integer.parseInt(lbl_id.getText());

        List<TermInfo> term = bmet.returnStudentTotalMarks(clas, aca, yer, ter);

        for (TermInfo te : term) {

            total = tot;

            int id = te.getId();
            tot = te.getTotalMarks();

            if (tot > total) {
                pos = count;
            } else if (tot == total) {
                pos = pos;
                count = pos + 1;
            } else {
                pos = count;
            }

            if (id == idd) {
                if (pos % 10 == 1) {
                    rpos = pos + "st";
                } else if (pos % 10 == 2) {
                    rpos = pos + "nd";
                } else if (pos % 10 == 3) {
                    rpos = pos + "rd";
                } else {
                    rpos = pos + "th";
                }
            }
            count++;
        }
        return rpos;
    }

    public String getingMarks(int stuID, int subj) {
        int ter = mylogics.termID(cmd_Term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int yer = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_Class.getSelectedItem().toString().trim());

        double total = 0, tot = 0;
        int id = 0;
        int sub = 0, pos = 0, count = 1;

        List<Result> result = bmet.returnStudentPosition(clas, aca, yer, ter);

        String rpos = "";

        for (Result res : result) {
            sub = res.getSubject_id();

            List<Result> subject = bmet.returnStudentSubject(clas, aca, yer, ter, sub);
            for (Result r : subject) {
                total = tot;

                id = r.getStuId();

                tot = r.getTotalResult();

                if (tot > total) {
                    pos = count;
                } else if (tot == total) {
                    pos = pos;
                    count = pos + 1;
                } else {
                    pos = count;
                }

                if (id == stuID && sub == subj) {

                    if (pos % 10 == 1 && pos != 11) {
                        rpos = pos + "st";
                    } else if (pos % 10 == 2) {
                        rpos = pos + "nd";
                    } else if (pos % 10 == 3) {
                        rpos = pos + "rd";
                    } else {
                        rpos = pos + "th";
                    }

                }
                count++;
            }
            count = 0;
            return rpos;
        }
        return null;
    }

    public void setReportToPrint() {
        String term = cmd_Term.getSelectedItem().toString().toUpperCase().trim();
        String academic = cmb_Academic.getSelectedItem().toString().toUpperCase().trim();
        String batch = cmd_Batch.getSelectedItem().toString().toUpperCase().trim();
        String stuName = cmd_StudentName.getSelectedItem().toString().toUpperCase().trim();
        String stuClass = mets.capitalizer(cmd_Class.getSelectedItem().toString().trim());

        int ter = mylogics.termID(cmd_Term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int yer = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_Class.getSelectedItem().toString().trim());
        int sid = mylogics.findStudentReport(cmd_StudentName.getSelectedItem().toString().trim(), mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim()));
        count = mylogics.totalStudentCount2(mylogics.yearID(cmd_Batch.getSelectedItem().toString()));

        // get location of the code source
        URL url = Designs.StudentForm.class.getProtectionDomain().getCodeSource().getLocation();
        String schoolName = "";
        String address = "";
        String schoolLocation = "";
        String schvac = "";
        String schresume = "";
        String contact = "";

        double totClass = 0, totExam = 0, totalTot = 0;
        String conduct = "", attitude = "", interest = "", tRemarks = "", hRemarks = "";
        double arrears = 0.0, totalFees = 0.0;
        int aten = 0, outof = 0;
        String promoted = "", prom = "", con = "", att = "", inti = "", teacher = "", head = "";

        byte[] imagedata = null;

        List<School> school = pro.findAllSchoolDetails();

        for (School res : school) {
            schoolName = res.getSchoolName();
            address = res.getSchoolAddress();
            contact = res.getSchoolContact();
            schoolLocation = res.getSchoolLocation();
            schvac = res.getSchoolVac();
            schresume = res.getSchoolResume();
        }

        List<TermInfo> terminfo = bmet.returnTermInfo(clas, aca, yer, sid, ter);

        for (TermInfo info : terminfo) {
            attitude = bmet.returnAttitudeName(info.getAttiID());
            conduct = bmet.returnConductName(info.getConID());
            interest = bmet.returnInterestName(info.getIntID());
            tRemarks = info.getTeacherRemarks();
            hRemarks = info.getHeadRemarks();
            arrears = info.getBalance();
            totalFees = info.getTotalfees();
            aten = info.getAttendance();
            outof = info.getOutof();
            promoted = mylogics.returnClassName(info.getPromoted());

            if (attitude == null) {
                att = "----";
            } else {
                att = mets.capitalizer(attitude);
            }

            if (conduct == null) {
                con = "----";
            } else {
                con = mets.capitalizer(conduct);
            }

            if (interest == null) {
                inti = "----";
            } else {
                inti = mets.capitalizer(interest);
            }

            if (tRemarks == null) {
                teacher = "----";
            } else {
                teacher = mets.capitalizer(tRemarks);
            }

            if (hRemarks == null) {
                head = "----";
            } else {
                head = mets.capitalizer(hRemarks);
            }

            if (promoted == null) {
                prom = "----";
            } else {
                prom = mets.capitalizer(promoted);
            }
        }

        try {
            // extract directory from code source url
            String root = (new File(url.toURI())).getParentFile().getPath();
            File doc = new File(root, "newhtml.html");
            // create htm file contents for testing
            //String imgsrc = StudentForm.class.getClassLoader().getSystemResource("book.jpg").toString();
            String img = (new File(url.toURI())).getParentFile().getPath();
            //String myImg = new File("Icons/book.jpg").getAbsolutePath();
            File myImg = new File(img, "Icons/book.jpg");

            FileWriter writer = new FileWriter(doc);

            writer.write("<html><body><img src=\"" + myImg + "\">"
                    + "<img src='" + myImg + "' width='50px' height='50px' />"
                    + "<h2 style='text-align: center; margin-bottom:-20px; margin-top:-20px;'>" + schoolName.toUpperCase() + "</h2>"
                    + "<h4 style='text-align: center;  margin-bottom:-20px; margin-top:-20px;'>" + address.toUpperCase() + " " + schoolLocation.toUpperCase() + "</h4>"
                    + "<h4 style='text-align: center;  margin-bottom:-20px; margin-top:-20px;'>" + contact + "</h4>"
                    + "<h4 style='text-align: center; margin-bottom:-20px; margin-top:-20px;'>END OF " + term + " " + academic + " TERMINAL REPORT</h4><hr>"
                    + "<table><tbody>"
                    + "<tr style='margin-bottom:-20px; margin-top:-20px;'>"
                    + "<td><b>Name : </b>" + stuName + "</td>"
                    + "<td><b>Class : </b>" + stuClass + "</td>"
                    + "<td><b>Next Term Begins : </b>" + schresume + "</td>"
                    + "</tr>"
                    + "</tbody></table>"
                    + "<table><tbody>"
                    + "<tr style='margin-bottom:-20px; margin-top:-20px;'>"
                    + "<td>No. on Roll :</td>"
                    + "<td style='text-align:center;'><b>" + count + "</b></td>"
                    + "<td>Position in Class :</td>"
                    + "<td style='text-align:center; color:red'><b>" + positionStu() + "</b></td>"
                    + "<td>Date : </td>"
                    + "<td style='text-align:center;'><b>" + schvac + "</b></td>"
                    + "<td>Total Marks : </td>"
                    + "<td style='text-align:center;'><b>" + txt_totalMarks.getText() + "</b></td>"
                    + "</tr>"
                    + "</tbody></table>"
                    + "</b>");
            String val = "";

            val += "<hr><table style='font-size:9px;'>"
                    + "<thead style='border-bottom: 1px dotted black'>"
                    + "<tr>"
                    + "<th style='text-align: left;'>Subject</th>"
                    + "<th style='text-align: left;' width='80px'>Class (50%)</th>"
                    + "<th style='text-align: left;' width='85px'>Exams (50%)</th>"
                    + "<th style='text-align: left;' width='85px'>Total (100%)</th>"
                    + "<th style='text-align: left;'>Grd</th>"
                    + "<th style='text-align: left;'>Pos</th>"
                    + "<th style='text-align: left;'>Remarks</th>"
                    + "</tr><hr>"
                    + "</thead><tbody style='font-size:9px>";

            List<Result> result = mylogics.reportSingleStudent(sid, clas, aca, ter, yer);

            for (Result res : result) {

                totClass += res.getAssessment();
                totExam += res.getExams();
                totalTot += res.getTotalResult();

                int subj = res.getSubject_id();

                double total = 0, tot = 0;
                int id = 0;
                int sub = 0, pos = 0, count = 1;

                List<Result> result1 = bmet.returnStudentPosition(clas, aca, yer, ter);

                String rpos = "";

                for (Result res1 : result1) {
                    sub = res1.getSubject_id();

                    List<Result> subject = bmet.returnStudentSubject(clas, aca, yer, ter, sub);
                    for (Result r : subject) {
                        total = tot;
                        id = r.getStuId();
                        tot = r.getTotalResult();

                        if (tot > total) {
                            pos = count;
                        } else if (tot == total) {
                            pos = pos;
                            count = pos + 1;
                        } else {
                            pos = count;
                        }

                        if (id == sid && sub == subj) {

                            if (pos % 10 == 1 && pos != 11) {
                                rpos = pos + "st";
                            } else if (pos % 10 == 2) {
                                rpos = pos + "nd";
                            } else if (pos % 10 == 3) {
                                rpos = pos + "rd";
                            } else {
                                rpos = pos + "th";
                            }

                        }
                        count++;
                    }
                    count = 1;
                }

                val += "<tr style='padding-top:-120px; padding-bottom:-120px;'>"
                        + "<td style='text-align: left; width:260px'>" + mets.capitalizer(mylogics.returnSubjectName(res.getSubject_id())) + "</td>"
                        + "<td style='text-align: left; width:20px; color:blue'>" + res.getAssessment() + "</td>"
                        + "<td style='text-align: left; color:blue; width:20px'>" + res.getExams() + "</td>"
                        + "<td style='text-align: left; color:red; width:20px'>" + res.getTotalResult() + "</td>"
                        + "<td style='text-align: left; color:red; width:10px'>" + res.getGrade() + "</td>"
                        + "<td style='text-align: left; color:blue; width:20px'>" + rpos + "</td>"
                        + "<td style='text-align: left; width:70px; color:blue'>" + mets.capitalizer(mylogics.returnRemarksOnID(res.getRemarks())) + "</td>";

            };

            val += "<tr style='border-top: 1px dotted black'>"
                    + "<td>" + "<b>TOTAL</b>" + "</td>"
                    + "<td><b>" + totClass + "</b></td>"
                    + "<td><b>" + totExam + "</b></td>"
                    + "<td><b>" + totalTot + "</b></td>"
                    + "</tr>";

            val += "</tbody></table><hr>"
                    + "<table>"
                    + "<tbody>"
                    + "<tr>"
                    + "<td> Attendance : </td>"
                    + "<td style='border-bottom:1px dotted black; width:60px; text-align:center; padding-bottom:-80px'><b>" + aten + "</b></td>"
                    + "<td> Out of : </td>"
                    + "<td style='border-bottom:1px dotted black; width:60px; text-align:center; padding-bottom:-80px'><b>" + outof + "</b></td>"
                    + "<td> Promoted to : </td> "
                    + "<td style='border-bottom:1px dotted black; text-align:center; padding-bottom:-80px'><b>" + prom + "</b></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td> Conduct : </td>"
                    + "<td style='border-bottom:1px dotted black; text-align:center; padding-bottom:-80px; color:blue'>" + con + "</td>"
                    + "<td> Interest : </td>"
                    + "<td style='border-bottom:1px dotted black; text-align:center; padding-bottom:-80px; width:50px; color:blue'>" + inti + "</td>"
                    + "<td> Attitude : </td> "
                    + "<td style='border-bottom:1px dotted black; text-align:center; padding-bottom:-80px; color:blue'>" + att + "</td>"
                    + "</tr>"
                    + "</tbody>"
                    + "</table>";

            val += "</tbody></table>"
                    + "<table>"
                    + "<tbody>"
                    + "<tr>"
                    + "<td> Class Teacher's Remarks : </td>"
                    + "<td style='border-bottom:1px dotted black; text-align:center; padding-bottom:-80px; color:blue'>" + teacher + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td> Head Teacher's Remarks : </td>"
                    + "<td style='border-bottom:1px dotted black; text-align:center; padding-bottom:-80px; color:blue'>" + head + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td> Headmaster's Signature : </td>"
                    + "<td style='border-bottom:1px dotted black; text-align:center; width:150px'>" + "" + "</td>"
                    + "</tr>"
                    + "</tbody>"
                    + "</table><hr>";

            val += "<table style='border:1px solid black'>"
                    + "<tbody"
                    + "<tr style='border-bottom:1px solid black; padding-top:-30px; padding-bottom:-10px;'>"
                    + "<td style='text-align:center' colspan='2'>Student's Bill</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>Arrears</td>"
                    + "<td>" + "GHC " + txt_balance.getText() + "</td>"
                    + "</tr>";

            List<Bill> bill = bmet.findAllBill();
            double schoolfees = 0.0;
            for (Bill bil : bill) {

                schoolfees += bil.getAmount();

                val += "<tr>"
                        + "<td>" + mets.capitalizer(bil.getBillItem()) + "</td>"
                        + "<td>" + "GHC " + bil.getAmount() + "</td>"
                        + "</tr>";
            };

            double totalbil = schoolfees + Double.parseDouble(txt_balance.getText().toString());
            val += "<tr style='border-top:1px solid black; border-bottom:1px solid black'>"
                    + "<td><b>" + "Total Fees" + "</b></td>"
                    + "<td><b>" + "GHC " + totalbil + "</b></td>"
                    + "</tr>"
                    + "</tbody>"
                    + "</table"
                    + "</body></html>";

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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        studentPerformanceTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        cmd_Batch = new javax.swing.JComboBox<>();
        cmb_Academic = new javax.swing.JComboBox<>();
        cmd_Class = new javax.swing.JComboBox<>();
        cmd_Term = new javax.swing.JComboBox<>();
        btn_back = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cmd_StudentName = new javax.swing.JComboBox<>();
        cmd_StudentID = new javax.swing.JComboBox<>();
        lbl_id = new javax.swing.JLabel();
        txt_totalMarks = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cmb_interest = new javax.swing.JComboBox<>();
        cmb_conduct = new javax.swing.JComboBox<>();
        cmb_attitude = new javax.swing.JComboBox<>();
        cmb_attendance = new javax.swing.JComboBox<>();
        cmb_outof = new javax.swing.JComboBox<>();
        cmb_promoteTo = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_teachersRemarks = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_HeadTeachersRemarks = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_totalbill = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        bill_table = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_totalFees = new javax.swing.JTextField();
        txt_balance = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btn_update = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_preview = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("STUDENT REPORT FORM");

        jScrollPane1.setAutoscrolls(true);

        studentPerformanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Subjects", "Class Score", "Exams Score", "Total", "Grd", "Pos", "Remarks"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(studentPerformanceTable);
        if (studentPerformanceTable.getColumnModel().getColumnCount() > 0) {
            studentPerformanceTable.getColumnModel().getColumn(1).setResizable(false);
            studentPerformanceTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            studentPerformanceTable.getColumnModel().getColumn(2).setResizable(false);
            studentPerformanceTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            studentPerformanceTable.getColumnModel().getColumn(3).setResizable(false);
            studentPerformanceTable.getColumnModel().getColumn(3).setPreferredWidth(30);
            studentPerformanceTable.getColumnModel().getColumn(4).setResizable(false);
            studentPerformanceTable.getColumnModel().getColumn(4).setPreferredWidth(30);
            studentPerformanceTable.getColumnModel().getColumn(5).setResizable(false);
            studentPerformanceTable.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Term Info", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 15), new java.awt.Color(51, 0, 0))); // NOI18N

        cmd_Batch.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Batch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmd_Batch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_BatchActionPerformed(evt);
            }
        });

        cmb_Academic.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_Academic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmd_Class.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Class.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmd_Term.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Term.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/back1.png"))); // NOI18N
        btn_back.setToolTipText("Click to go back");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmd_Class, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_Academic, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_Term, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_Batch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(cmd_Class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmb_Academic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmd_Term, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmd_Batch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Student", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 15), new java.awt.Color(51, 0, 51))); // NOI18N

        cmd_StudentName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_StudentName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmd_StudentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_StudentNameActionPerformed(evt);
            }
        });

        cmd_StudentID.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_StudentID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbl_id, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_totalMarks, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmd_StudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmd_StudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmd_StudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmd_StudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_totalMarks, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(lbl_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Attitude, Conduct and Interest", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(51, 0, 0))); // NOI18N

        cmb_interest.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_interest.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Interest", "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_conduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_conduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Conduct", "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_attitude.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_attitude.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Attitude", "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_attendance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_attendance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Attendance", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", " " }));

        cmb_outof.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_outof.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Out of", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));

        cmb_promoteTo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_promoteTo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_attitude, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_conduct, 0, 227, Short.MAX_VALUE)
                    .addComponent(cmb_interest, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_attendance, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_outof, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_promoteTo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmb_attitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_conduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_interest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_attendance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_outof, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_promoteTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Remarks", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(102, 0, 0))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Class Teacher's Remarks");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Headmaster's Remarks");

        txt_teachersRemarks.setColumns(20);
        txt_teachersRemarks.setLineWrap(true);
        txt_teachersRemarks.setRows(5);
        txt_teachersRemarks.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txt_teachersRemarks);

        txt_HeadTeachersRemarks.setColumns(20);
        txt_HeadTeachersRemarks.setLineWrap(true);
        txt_HeadTeachersRemarks.setRows(5);
        txt_HeadTeachersRemarks.setWrapStyleWord(true);
        jScrollPane4.setViewportView(txt_HeadTeachersRemarks);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Student Bill", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(102, 0, 0))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 0));
        jLabel4.setText("Enter Arrears");

        txt_totalbill.setEditable(false);
        txt_totalbill.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        bill_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1), "Library Fees",  new Double(32.0)},
                { new Integer(2), "Books",  new Double(90.0)},
                { new Integer(3), "Light",  new Double(22.0)},
                { new Integer(4), "Fees",  new Double(100.0)}
            },
            new String [] {
                "ID", "Bill Item", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(bill_table);
        if (bill_table.getColumnModel().getColumnCount() > 0) {
            bill_table.getColumnModel().getColumn(0).setResizable(false);
            bill_table.getColumnModel().getColumn(0).setPreferredWidth(15);
            bill_table.getColumnModel().getColumn(2).setResizable(false);
            bill_table.getColumnModel().getColumn(2).setPreferredWidth(20);
        }

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 0));
        jLabel5.setText("Total Fees");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 0));
        jLabel3.setText("Bill breakdown total");

        txt_totalFees.setEditable(false);
        txt_totalFees.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        txt_balance.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_balance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_balanceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_balanceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_totalbill)
                            .addComponent(txt_balance)
                            .addComponent(txt_totalFees, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_totalbill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_balance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_totalFees)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Action", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        btn_update.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_save.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_preview.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_preview.setText("Preview Print");
        btn_preview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_preview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btn_preview, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmd_BatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_BatchActionPerformed
        studentName(cmd_Batch);
    }//GEN-LAST:event_cmd_BatchActionPerformed

    private void cmd_StudentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_StudentNameActionPerformed
        if (cmd_StudentName.getSelectedIndex() <= 0) {
        } else {
            setEmpty();
            setStudentPerformance(cmd_StudentName);
            aStudentTermInfo();
            studentTotalMarks();
        }
    }//GEN-LAST:event_cmd_StudentNameActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        saveTermInfo();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void txt_balanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_balanceKeyPressed

    }//GEN-LAST:event_txt_balanceKeyPressed

    private void txt_balanceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_balanceKeyReleased
        sumupTermFees();
    }//GEN-LAST:event_txt_balanceKeyReleased

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        Desktop des = new Desktop();
        des.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        updateTermInfo();
        aStudentTermInfo();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_previewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previewActionPerformed
        ReceiptForm rcp = new ReceiptForm(this, true);
        setReportToPrint();
        rcp.setVisible(true);
        ReceiptForm.btn_Print.setVisible(true);
        ReceiptForm.print_me.setVisible(false);
    }//GEN-LAST:event_btn_previewActionPerformed

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
            java.util.logging.Logger.getLogger(StudentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bill_table;
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_preview;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> cmb_Academic;
    private javax.swing.JComboBox<String> cmb_attendance;
    private javax.swing.JComboBox<String> cmb_attitude;
    private javax.swing.JComboBox<String> cmb_conduct;
    private javax.swing.JComboBox<String> cmb_interest;
    private javax.swing.JComboBox<String> cmb_outof;
    private javax.swing.JComboBox<String> cmb_promoteTo;
    private javax.swing.JComboBox<String> cmd_Batch;
    private javax.swing.JComboBox<String> cmd_Class;
    private javax.swing.JComboBox<String> cmd_StudentID;
    private javax.swing.JComboBox<String> cmd_StudentName;
    private javax.swing.JComboBox<String> cmd_Term;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbl_id;
    public static javax.swing.JTable studentPerformanceTable;
    private javax.swing.JTextArea txt_HeadTeachersRemarks;
    private javax.swing.JTextField txt_balance;
    private javax.swing.JTextArea txt_teachersRemarks;
    private javax.swing.JTextField txt_totalFees;
    private javax.swing.JLabel txt_totalMarks;
    private javax.swing.JTextField txt_totalbill;
    // End of variables declaration//GEN-END:variables
}

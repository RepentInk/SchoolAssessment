package Connects;

import Classes.Assessment;
import Classes.Attitude;
import Classes.Bill;
import Classes.Conduct;
import Classes.Exams;
import Classes.Interest;
import Classes.Result;
import Classes.TermInfo;
import Classes.User;
import Classes.Register;
import Classes.Student;
import Designs.Desktop;
import Designs.RegisterForm;
import Designs.LoginPage;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class logicHandler {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    billMethods bmet = new billMethods();
    myLogics mylogics = new myLogics();
    ProgramDAO pro = new ProgramDAO();

    public logicHandler() {
        conn = myCon.ConnecrDb();
    }

    //======== Methods on Conducts ==================
    public void saveConduct(JTextField conduct) {
        if (conduct.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {
            boolean exist = bmet.exitConduct(conduct.getText().toLowerCase().trim());
            String txt = conduct.getText().toLowerCase().trim();

            if (exist == true) {
                conduct.setBackground(Color.LIGHT_GRAY);
                conduct.setText("Conduct Exist");
            } else {
                Conduct cond = new Conduct(txt);
                bmet.saveConduct(cond);
                //bmet.findAllConduct();
            }
        }
    }

    public void setConductToCombo(JComboBox cmb, String title) {
        List<Conduct> conduct = bmet.findAllConduct();
        DefaultComboBoxModel cond = (DefaultComboBoxModel) cmb.getModel();
        cond.removeAllElements();
        cond.addElement(title);

        for (Conduct con : conduct) {
            cond.addElement(con.getConductName());
        }
    }

    public void getSingleConduct(JTextField conName, JLabel id, String cname) {
        List<Conduct> conduct = bmet.findConduct(cname);
        for (Conduct con : conduct) {
            id.setText("" + con.getId());
            conName.setText(con.getConductName());
        }
    }

    public void deleteConduct(JLabel idd) {
        int id = Integer.parseInt(idd.getText());
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Select to delete");
        } else {
            bmet.deleteConduct(id);
        }
    }

    //======= Methods ends here =====================
    //======== Methods on Interest ==================
    public void saveInterest(JTextField interest) {
        if (interest.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {
            boolean exist = bmet.exitInterest(interest.getText().toLowerCase().trim());
            String txt = interest.getText().toLowerCase().trim();

            if (exist == true) {
                interest.setBackground(Color.LIGHT_GRAY);
                interest.setText("Interest Exist");
            } else {
                Interest inter = new Interest(txt);
                bmet.saveInterest(inter);
                //bmet.findAllConduct();
            }
        }
    }

    public void setInterestToCombo(JComboBox cmb, String title) {
        List<Interest> interest = bmet.findAllInterest();
        DefaultComboBoxModel intere = (DefaultComboBoxModel) cmb.getModel();
        intere.removeAllElements();
        intere.addElement(title);

        for (Interest inter : interest) {
            intere.addElement(inter.getInterestName());
        }
    }

    public void getSingleInterest(JTextField intName, JLabel id, String inname) {
        List<Interest> interest = bmet.findInterest(inname);
        for (Interest inter : interest) {
            id.setText("" + inter.getId());
            intName.setText(inter.getInterestName());
        }
    }

    public void deleteInterest(JLabel idd) {
        int id = Integer.parseInt(idd.getText());
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Select to delete");
        } else {
            bmet.deleteInterest(id);
        }
    }
    //======= Methods ends here =====================

    //======== Methods on Attitude ==================
    public void saveAttitude(JTextField attitude) {
        if (attitude.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {
            boolean exist = bmet.exitAttitude(attitude.getText().toLowerCase().trim());
            String txt = attitude.getText().toLowerCase().trim();

            if (exist == true) {
                attitude.setBackground(Color.LIGHT_GRAY);
                attitude.setText("Interest Exist");
            } else {
                Attitude atti = new Attitude(txt);
                bmet.saveAttitude(atti);
                //bmet.findAllConduct();
            }
        }
    }

    public void setAttitudeToCombo(JComboBox cmb, String title) {
        List<Attitude> attitude = bmet.findAllAttitude();
        DefaultComboBoxModel attit = (DefaultComboBoxModel) cmb.getModel();
        attit.removeAllElements();
        attit.addElement(title);

        for (Attitude atti : attitude) {
            attit.addElement(atti.getAttitudeName());
        }
    }

    public void getSingleAttitude(JTextField attiName, JLabel id, String attiname) {
        List<Attitude> attitude = bmet.findAttitude(attiname);
        for (Attitude atti : attitude) {
            id.setText("" + atti.getId());
            attiName.setText(atti.getAttitudeName());
        }
    }

    public void deleteAttitude(JLabel idd) {
        int id = Integer.parseInt(idd.getText());
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Select to delete");
        } else {
            bmet.deleteAttitude(id);
        }
    }

    //======= Methods ends here =====================
    //======== Methods on Bill ==================
    public void saveBill(JTextField amount, JTextField billItem) {
        if (billItem.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {
            boolean exist = bmet.exitBill(billItem.getText().toLowerCase().trim());
            String txt = billItem.getText().toLowerCase().trim();
            double txt1 = Double.parseDouble(amount.getText().trim());

            if (exist == true) {
                billItem.setBackground(Color.LIGHT_GRAY);
                billItem.setText("Interest Exist");
            } else {
                Bill bill = new Bill(txt1, txt);
                bmet.saveBill(bill);
                //bmet.findAllConduct();
            }
        }
    }

    public void setBillToCombo(JComboBox cmb, String title) {
        List<Bill> billme = bmet.findAllBill();
        DefaultComboBoxModel bill = (DefaultComboBoxModel) cmb.getModel();
        bill.removeAllElements();
        bill.addElement(title);

        for (Bill bil : billme) {
            bill.addElement(bil.getBillItem());
        }
    }

    public void billToTable(JTable assTable) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);
        Object[] object;
        int id = 0;

        List<Bill> bill = bmet.findAllBill();
        for (Bill bil : bill) {

            String billItem = bil.getBillItem();
            double amount = bil.getAmount();
            id++;

            object = new Object[]{id, billItem, amount};
            tmodel.addRow(object);
        }
    }

    public void getSingleBill(JTextField billItem, JTextField amount, JLabel id, String attiname) {
        List<Bill> bill = bmet.findBill(attiname);
        for (Bill bil : bill) {
            id.setText("" + bil.getId());
            billItem.setText(bil.getBillItem());
            amount.setText("" + bil.getAmount());
        }
    }

    public void deleteBill(JLabel idd) {
        int id = Integer.parseInt(idd.getText());
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Select to delete");
        } else {
            bmet.deleteBill(id);
        }
    }

    //======= Methods ends here =====================
    //======== Methods on TermInfo ==================
    public void saveTermInfo(JComboBox cid, JComboBox acaid, JComboBox yid, JComboBox stuid, JComboBox attiID, JComboBox conID, JComboBox intID, JComboBox terID, JTextArea tremarks, JTextArea hremark, JTextField balan, JTextField totalf, JComboBox attend, JComboBox outof, JComboBox promote, JLabel marks) {
        if (cid.getSelectedIndex() == 0 || acaid.getSelectedIndex() == 0 || yid.getSelectedIndex() == 0 || stuid.getSelectedIndex() == 0 || attiID.getSelectedIndex() == 0 || conID.getSelectedIndex() == 0 || intID.getSelectedIndex() == 0 || terID.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {

            int cd = mylogics.classID(cid.getSelectedItem().toString());
            int aca = mylogics.academicID(acaid.getSelectedItem().toString());
            int yd = mylogics.yearID(yid.getSelectedItem().toString());
            int sid = mylogics.findStudentReport(stuid.getSelectedItem().toString().trim(), mylogics.yearID(yid.getSelectedItem().toString().trim()));
            int term = mylogics.termID(terID.getSelectedItem().toString());

            int conduct = bmet.returnConductID(conID.getSelectedItem().toString());
            int atitude = bmet.returnAttitudeID(attiID.getSelectedItem().toString());
            int interest = bmet.returnInterestID(intID.getSelectedItem().toString());

            String tRemarks = tremarks.getText().trim();
            String hRemarks = hremark.getText().trim();
            double balance = Double.parseDouble(balan.getText().trim());
            double totalFees = Double.parseDouble(totalf.getText().toString());

            int aten = Integer.parseInt(attend.getSelectedItem().toString());
            int outf = Integer.parseInt(outof.getSelectedItem().toString());
            int prom = mylogics.classID(promote.getSelectedItem().toString());

            double mark = Double.parseDouble(marks.getText().trim());

            boolean exist = bmet.exitTermInfo(cd, aca, yd, sid, term);

            if (exist == true) {
                JOptionPane.showMessageDialog(null, "Term Info on select student already exist");
            } else {
                TermInfo terminfo = new TermInfo(cd, aca, yd, sid, atitude, conduct, interest, term, tRemarks, hRemarks, balance, totalFees, aten, outf, prom, mark);
                bmet.saveTermInfo(terminfo);
            }
        }
    }

    public void updateTermInfo(JLabel id, JComboBox attiID, JComboBox conID, JComboBox intID, JTextArea tremarks, JTextArea hremark, JTextField balan, JTextField totalf, JComboBox attend, JComboBox outF, JComboBox promoted, JLabel marks) {
        if (attiID.getSelectedIndex() == 0 || conID.getSelectedIndex() == 0 || intID.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Fill required field");
        } else {

            int conduct = bmet.returnConductID(conID.getSelectedItem().toString());
            int atitude = bmet.returnAttitudeID(attiID.getSelectedItem().toString());
            int interest = bmet.returnInterestID(intID.getSelectedItem().toString());

            String tRemarks = tremarks.getText().trim();
            String hRemarks = hremark.getText().trim();
            double balance = Double.parseDouble(balan.getText().trim());
            double totalFees = Double.parseDouble(totalf.getText().toString());
            int idd = Integer.parseInt(id.getText().toString());

            int atten = Integer.parseInt(attend.getSelectedItem().toString());
            int outof = Integer.parseInt(outF.getSelectedItem().toString());
            int promote = mylogics.classID(promoted.getSelectedItem().toString());

            double mark = Double.parseDouble(marks.getText().trim());

            TermInfo terminfo = new TermInfo(atitude, conduct, interest, tRemarks, hRemarks, balance, totalFees, atten, outof, promote, mark);
            bmet.updateTermInfo(terminfo, idd);

        }
    }

    public void showTermInfo(JComboBox cmb, String title) {
        List<Bill> billme = bmet.findAllBill();
        DefaultComboBoxModel bill = (DefaultComboBoxModel) cmb.getModel();
        bill.removeAllElements();
        bill.addElement(title);

        for (Bill bil : billme) {
            bill.addElement(bil.getBillItem() + " " + bil.getAmount());
        }
    }

    //======= Methods ends here =====================
    //========== Student search methods starts here ===============
    public void resultOfStudentSearch(JTable resTable, int sid, int tid, int aid, int subid, int yid, int cid) {
        DefaultTableModel tmodel = (DefaultTableModel) resTable.getModel();
        tmodel.setRowCount(0);

        int count = 1, pos = 0;
        double total, tot = 0;

        Object[] object;

        List<Result> result = mylogics.searchSpecificStudentResult(sid, tid, aid, subid, yid, cid);
        for (Result res : result) {
            total = tot;

            String studentid = mylogics.studentIDAssigned(res.getStuId(), res.getYear_id());
            String fullname = mylogics.findStudentByIDName(res.getStuId(), res.getYear_id());
            double assessment = res.getAssessment();
            double exams = res.getExams();
            tot = res.getTotalResult();
            String grade = res.getGrade();
            String remarks = mylogics.returnRemarksOnID(res.getRemarks());

            if (tot > total) {
                pos = count;
            } else if (tot == total) {
                pos = pos;
                count = pos + 1;
            } else {
                pos = count;
            }

            object = new Object[]{studentid, fullname, assessment, exams, tot, grade, pos, remarks};
            tmodel.addRow(object);

            count++;

        }
    }

    public void examsOfStudentSearch(JTable exeTable, int sid, int tid, int aid, int subid, int yid, int cid) {
        DefaultTableModel tmodel = (DefaultTableModel) exeTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;

        List<Exams> exams = mylogics.searchSpecificStudentExams(sid, tid, aid, subid, yid, cid);
        for (Exams exe : exams) {

            String studentid = mylogics.studentIDAssigned(exe.getStuId(), exe.getYear_id());
            String fullname = mylogics.findStudentByIDName(exe.getStuId(), exe.getYear_id());
            double obj = exe.getObjectives();
            double theory = exe.getTheory();
            double tot = exe.getTotalExams();
            double percent = exe.getFiftyPercentExams();

            object = new Object[]{studentid, fullname, obj, theory, tot, percent};
            tmodel.addRow(object);
        }
    }

    public void assessmentOfStudentSearch(JTable assTable, int sid, int tid, int aid, int subid, int yid, int cid) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;

        List<Assessment> assess = mylogics.searchSpecificStudentAssessment(sid, tid, aid, subid, yid, cid);
        for (Assessment ass : assess) {

            String studentid = mylogics.studentIDAssigned(ass.getStuId(), ass.getYear_id());
            String fullname = mylogics.findStudentByIDName(ass.getStuId(), ass.getYear_id());
            double clasTest = ass.getClassTest();
            double other = ass.getOthers();
            double total = ass.getTotal();
            double percent = ass.getFiftyPercentAssessment();

            object = new Object[]{studentid, fullname, clasTest, other, total, percent};
            tmodel.addRow(object);
        }
    }

    //=========== Ends here =======================================
    //=========== User Methods =================================
    public boolean confirmPassword(JPasswordField pwd, JPasswordField c_pwd) {
        boolean confirm = false;

        String password = pwd.getText();
        String c_passwoerd = c_pwd.getText();

        if (password.equalsIgnoreCase(c_passwoerd)) {
            confirm = true;
        }
        return confirm;
    }

    public void saveUser(JTextField fname, JTextField uname, JPasswordField pass, JPasswordField c_pass, String sdate, String edate, int delete) {
        if (confirmPassword(pass, c_pass) == true) {
            String fullname = fname.getText();
            String username = uname.getText();
            String password = pass.getText();
            String status = "";

            if (bmet.exitUser(fullname, username) == true) {
                JOptionPane.showMessageDialog(null, "Fullname or Username has already been taken", "Oooops!!! ACCOUNT EXIST", JOptionPane.ERROR_MESSAGE);
            } else {

                if (bmet.exitadmin() == true) {
                    status = "user";
                } else {
                    status = "admin";
                }

                User user = new User(fullname, username, password, sdate, edate, delete, status);
                bmet.saveUser(user);

            }

        } else {
            JOptionPane.showMessageDialog(null, "Please Check and confirm Password");
        }
    }

    public void getLogin(JTextField username, JPasswordField password, JFrame f1) {
        String uname = username.getText();
        String pass = password.getText();

        if (uname.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please provide username or password");
        } else {

            if (bmet.returnUserName(uname, pass) == true) {
                Desktop top = new Desktop();
                top.setVisible(true);
                f1.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Ooops!! Account does not exist");
            }

        }
    }

    //========== End Here ======================================
    //========= RegisterForm Method ================================
    public void saveRegister(JFormattedTextField contact, JTextField code, JFrame f1) {

        String con = contact.getText();
        String cod = code.getText();

        int registered = 0;

        if (cod.equalsIgnoreCase("XYS12-WSD34-VNH34-DMN10")) {
            registered = 1;
            Register reg = new Register(con, registered);
            bmet.saveRegister(reg);
            loginPage();
            f1.dispose();

        } else if (cod.equalsIgnoreCase("123DH-WEHD2-90D09-NMH23")) {
            registered = 1;
            Register reg = new Register(con, registered);
            bmet.saveRegister(reg);
            loginPage();
            f1.dispose();

        } else if (cod.equalsIgnoreCase("123DH-WEHD2-123WE-NMH23")) {
            registered = 1;
            Register reg = new Register(con, registered);
            bmet.saveRegister(reg);
            loginPage();
            f1.dispose();

        } else if (cod.equalsIgnoreCase("K23DH-WEHD2-90D09-NVH23")) {
            registered = 1;
            Register reg = new Register(con, registered);
            bmet.saveRegister(reg);
            loginPage();
            f1.dispose();

        } else {
            JOptionPane.showMessageDialog(null, "Check and Enter the right registration code", "WRONG REGISTRATION CODE", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loginPage() {
        LoginPage log = new LoginPage();
        log.setVisible(true);
        log.txt_username.setEnabled(true);
        log.txt_password.setEnabled(true);
        log.btn_login.setEnabled(true);
    }
    //======== End of RegisterForm =================================

    //========== Showing all student in table ======================
    //Showing result specified
    public void allStudent(JTable studentTable, int yer) {
        DefaultTableModel tmodel = (DefaultTableModel) studentTable.getModel();
        tmodel.setRowCount(0);
        Object[] object;

        List<Student> student = pro.findAllStudent(yer);

        if (student.size() <= 0) {
            JOptionPane.showMessageDialog(null, "No Record Exit for selected batch");
        }

        for (Student stu : student) {

            String studentid = stu.getStuID();
            String fullname = stu.getSurname() + " " + stu.getmName() + " " + stu.getfName();
            String contact = "0" + stu.getContact();
            String hometown = stu.gethTown();
            int decide = stu.getCompleteID();

            String status = "";

            if (decide == 0) {
                status = "STOPPED";
            } else if (decide == 1) {
                status = "PRESENT";
            }

            object = new Object[]{studentid, fullname.trim(), contact, hometown, status};
            tmodel.addRow(object);

        }
    }

    public void allStudentTableClick(int id, String stuid, JLabel sid, JLabel idd, JTextField surname, JTextField mName, JTextField fName, JTextField contact, JTextField hTown, JComboBox year, JRadioButton stoped, JRadioButton present) {

        List<Student> student = bmet.findOneStudent(id, stuid);

        if (student.size() <= 0) {
            JOptionPane.showMessageDialog(null, "No Record Exit for selected student");
        }

        for (Student stu : student) {
            idd.setText("" + stu.getId());
            sid.setText(stu.getStuID());
            surname.setText(stu.getSurname());
            mName.setText(stu.getmName());
            fName.setText(stu.getfName());
            contact.setText("0" + stu.getContact());
            hTown.setText(stu.gethTown());

            year.setSelectedItem(mylogics.yearName(stu.getCompleteID()));

            if (stu.getDeleted_at() == 0) {
                stoped.setSelected(true);
            } else {
                present.setSelected(true);
            }
        }
    }

    public void updateStudent(JLabel stid, JLabel idd, JTextField surname, JTextField mName, JTextField fName, JTextField contact, JTextField hTown, JComboBox year, JRadioButton stoped, JRadioButton present) {

        String stuid = stid.getText();
        int id = Integer.parseInt(idd.getText());
        String sname = surname.getText();
        String mname = mName.getText();
        String fname = fName.getText();
        String Contact = contact.getText();
        String htown = hTown.getText();
        int yer = mylogics.yearID(year.getSelectedItem().toString());
        int status = 0;

        if (stoped.isSelected()) {
            status = 0;
        } else if (present.isSelected()) {
            status = 1;
        }

        Student student = new Student(yer, fname, mname, sname, Contact, htown, status);
        
        bmet.updateStudent(student, id, stuid);
    }

    //=================End==========================================
}

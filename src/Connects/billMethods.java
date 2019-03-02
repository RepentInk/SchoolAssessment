package Connects;

import Classes.Attitude;
import Classes.Bill;
import Classes.Conduct;
import Classes.Interest;
import Classes.Result;
import Classes.TermInfo;
import Classes.User;
import Classes.Register;
import Classes.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class billMethods {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public billMethods() {
        conn = myCon.ConnecrDb();
    }

    //=========Methods on conduct starts here ==============
    public void saveConduct(Conduct conduct) {
        try {
            String sql = "INSERT INTO Conduct (conductName) VALUES (?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, conduct.getConductName().toLowerCase());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Conduct is saved");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public List<Conduct> findAllConduct() {
        List<Conduct> conductList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Conduct";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Conduct conduct = new Conduct();
                conduct.setId(rs.getInt("id"));
                conduct.setConductName(rs.getString("conductName"));
                conductList.add(conduct);
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
        return conductList;
    }

    public void updateConduct(Conduct conduct, int id) {

    }

    public void deleteConduct(int id) {
        try {
            String sql = "DELETE FROM Conduct WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Conduct is deleted");
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

    public boolean exitConduct(String txt) {
        boolean conduct = false;
        try {
            String year = "select conductName from Conduct where conductName='" + txt.toLowerCase() + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                conduct = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return conduct;
    }

    public String returnConductName(int txt) {
        String conduct = null;
        try {
            String year = "select conductName from Conduct where id='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                conduct = rs.getString("conductName");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return conduct;
    }

    public int returnConductID(String name) {
        int id = 0;
        try {
            String year = "select id from Conduct where conductName='" + name + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return id;
    }

    public List<Conduct> findConduct(String name) {
        List<Conduct> conductName = new ArrayList<>();
        try {
            String sql = "SELECT id,conductName FROM Conduct where conductName='" + name + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                Conduct conduct = new Conduct();
                conduct.setId(rs.getInt("id"));
                conduct.setConductName(rs.getString("conductName"));
                conductName.add(conduct);
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

        return conductName;
    }

    //==========End of conduct methods ======================
    //=========Methods on interest starts here ==============
    public void saveInterest(Interest interest) {
        try {
            String sql = "INSERT INTO Interest (interestName) VALUES (?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, interest.getInterestName());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Interest is saved");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public List<Interest> findAllInterest() {
        List<Interest> interestList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Interest";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Interest interest = new Interest();
                interest.setId(rs.getInt("id"));
                interest.setInterestName(rs.getString("interestName"));
                interestList.add(interest);
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
        return interestList;
    }

    public void updateInterest(Interest interest, int id) {

    }

    public void deleteInterest(int id) {
        try {
            String sql = "DELETE FROM Interest WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Interest is deleted");
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

    public boolean exitInterest(String txt) {
        boolean interest = false;
        try {
            String year = "select interestName from Interest where interestName='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                interest = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return interest;
    }

    public String returnInterestName(int txt) {
        String interest = null;
        try {
            String year = "select interestName from Interest where id='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                interest = rs.getString("interestName");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return interest;
    }

    public int returnInterestID(String name) {
        int id = 0;
        try {
            String year = "select id from Interest where interestName='" + name + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return id;
    }

    public List<Interest> findInterest(String name) {
        List<Interest> interestName = new ArrayList<>();
        try {
            String sql = "SELECT id,interestName FROM Interest where interestName='" + name + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                Interest interest = new Interest();
                interest.setId(rs.getInt("id"));
                interest.setInterestName(rs.getString("interestName"));
                interestName.add(interest);
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

        return interestName;
    }
    //==========End of interest methods ======================

    //=========Methods on Attitude starts here ==============
    public void saveAttitude(Attitude attitude) {
        try {
            String sql = "INSERT INTO Attitude (attitudeName) VALUES (?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, attitude.getAttitudeName());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Attitude is saved");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public List<Attitude> findAllAttitude() {
        List<Attitude> attitudeList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Attitude";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Attitude attitude = new Attitude();
                attitude.setId(rs.getInt("id"));
                attitude.setAttitudeName(rs.getString("attitudeName"));
                attitudeList.add(attitude);
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
        return attitudeList;
    }

    public void updateAttitude(Attitude attitude, int id) {

    }

    public void deleteAttitude(int id) {
        try {
            String sql = "DELETE FROM Attitude WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Attitude is deleted");
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

    public boolean exitAttitude(String txt) {
        boolean attitude = false;
        try {
            String year = "select attitudeName from Attitude where attitudeName='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                attitude = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return attitude;
    }

    public String returnAttitudeName(int txt) {
        String attitude = null;
        try {
            String year = "select attitudeName from Attitude where id='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                attitude = rs.getString("attitudeName");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return attitude;
    }

    public int returnAttitudeID(String name) {
        int id = 0;
        try {
            String year = "select id from Attitude where attitudeName='" + name + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return id;
    }

    public List<Attitude> findAttitude(String name) {
        List<Attitude> attitudeName = new ArrayList<>();
        try {
            String sql = "SELECT id,attitudeName FROM Attitude where attitudeName='" + name + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                Attitude attitude = new Attitude();
                attitude.setId(rs.getInt("id"));
                attitude.setAttitudeName(rs.getString("attitudeName"));
                attitudeName.add(attitude);
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

        return attitudeName;
    }
    //==========End of attitude methods ======================

    //=========Methods on Bill starts here ==============
    public void saveBill(Bill bill) {
        try {
            String sql = "INSERT INTO Bill (billItem,amount) VALUES (?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, bill.getBillItem());
            pst.setDouble(2, bill.getAmount());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Bill is saved");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public List<Bill> findAllBill() {
        List<Bill> billList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Bill";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBillItem(rs.getString("billItem"));
                bill.setAmount(rs.getDouble("amount"));
                billList.add(bill);
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
        return billList;
    }

    public void updateBill(Bill bill, int id) {

    }

    public void deleteBill(int id) {
        try {
            String sql = "DELETE FROM Bill WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Bill Item is deleted");
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

    public boolean exitBill(String txt) {
        boolean bill = false;
        try {
            String year = "select billItem from Bill where billItem='" + txt + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                bill = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return bill;
    }

    public List<Bill> findBill(String name) {
        List<Bill> billName = new ArrayList<>();
        try {
            String sql = "SELECT id,billItem,amount FROM Bill where billItem='" + name + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBillItem(rs.getString("billItem"));
                bill.setAmount(rs.getDouble("amount"));
                billName.add(bill);
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
        return billName;
    }
    //==========End of Bill methods ======================

    //=========Methods on termInfo starts here ==============
    public void saveTermInfo(TermInfo terminfo) {
        try {
            String sql = "INSERT INTO TermInfo (clasID,acaID,yerID,stuID,attiID,conID,intID,terID,teacherRemark,headRemarks,balance,totalfees,attendance,outof,promoted,totalMarks) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, terminfo.getClasID());
            pst.setInt(2, terminfo.getAcaID());
            pst.setInt(3, terminfo.getYerID());
            pst.setInt(4, terminfo.getStuID());
            pst.setInt(5, terminfo.getAttiID());
            pst.setInt(6, terminfo.getConID());
            pst.setInt(7, terminfo.getIntID());
            pst.setInt(8, terminfo.getTerID());
            pst.setString(9, terminfo.getTeacherRemarks());
            pst.setString(10, terminfo.getHeadRemarks());
            pst.setDouble(11, terminfo.getBalance());
            pst.setDouble(12, terminfo.getTotalfees());
            pst.setInt(13, terminfo.getAttendance());
            pst.setInt(14, terminfo.getOutof());
            pst.setInt(15, terminfo.getPromoted());
            pst.setDouble(16, terminfo.getTotalMarks());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Student term info is saved");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public List<TermInfo> findAllTermInfo() {
        List<TermInfo> termInfoList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM TermInfo";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                TermInfo terminfo = new TermInfo();
                terminfo.setId(rs.getInt("id"));
                terminfo.setAttiID(rs.getInt("attiID"));
                terminfo.setConID(rs.getInt("conID"));
                terminfo.setIntID(rs.getInt("intID"));
                terminfo.setTeacherRemarks(rs.getString("teacherRemark"));
                terminfo.setHeadRemarks(rs.getString("headRemarks"));
                terminfo.setBalance(rs.getDouble("balance"));
                terminfo.setTotalfees(rs.getDouble("totalfees"));
                terminfo.setAttendance(rs.getInt("attendance"));
                terminfo.setOutof(rs.getInt("outof"));
                terminfo.setPromoted(rs.getInt("promoted"));
                termInfoList.add(terminfo);
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
        return termInfoList;
    }

    public void updateTermInfo(TermInfo terminfo, int id) {
        try {
            String sql = "UPDATE TermInfo SET attiID='" + terminfo.getAttiID() + "', conID='" + terminfo.getConID() + "', intID='" + terminfo.getIntID() + "',"
                    + "teacherRemark='" + terminfo.getTeacherRemarks() + "', headRemarks='" + terminfo.getHeadRemarks() + "',balance='" + terminfo.getBalance() + "', totalfees='" + terminfo.getTotalfees() + "',"
                    + "attendance='" + terminfo.getAttendance() + "', outof='" + terminfo.getOutof() + "', promoted='" + terminfo.getPromoted() + "', totalMarks='" + terminfo.getTotalMarks() + "'"
                    + "WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Term Info Updated");
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

    public void deleteTermInfo(int id) {
        try {
            String sql = "DELETE FROM TermInfo WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Term Info is deleted");
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

    public boolean exitTermInfo(int clasID, int acaID, int yerID, int stuID, int terID) {
        boolean terminfo = false;
        try {
            String year = "select clasID,acaID,yerID,stuID,terID from TermInfo where clasID='" + clasID + "' and acaID='" + acaID + "' and yerID='" + yerID + "' and stuID='" + stuID + "' and terID='" + terID + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                terminfo = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return terminfo;
    }

    public List<TermInfo> returnTermInfo(int clas, int aca, int yid, int stuid, int terid) {
        List<TermInfo> termInfoList = new ArrayList<>();
        try {
            String sql = "SELECT id,attiID,conID,intID,teacherRemark,headRemarks,balance,totalfees,attendance,outof,promoted FROM TermInfo WHERE clasID='" + clas + "' and acaID='" + aca + "' and yerID='" + yid + "' and stuID='" + stuid + "' and terID='" + terid + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                TermInfo terminfo = new TermInfo();
                terminfo.setId(rs.getInt("id"));
                terminfo.setAttiID(rs.getInt("attiID"));
                terminfo.setConID(rs.getInt("conID"));
                terminfo.setIntID(rs.getInt("intID"));
                terminfo.setTeacherRemarks(rs.getString("teacherRemark"));
                terminfo.setHeadRemarks(rs.getString("headRemarks"));
                terminfo.setBalance(rs.getDouble("balance"));
                terminfo.setTotalfees(rs.getDouble("totalfees"));
                terminfo.setAttendance(rs.getInt("attendance"));
                terminfo.setOutof(rs.getInt("outof"));
                terminfo.setPromoted(rs.getInt("promoted"));
                termInfoList.add(terminfo);
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
        return termInfoList;
    }

    public List<TermInfo> returnStudentTotalMarks(int clas, int aca, int yid, int terid) {
        List<TermInfo> termInfoList = new ArrayList<>();
        try {
            String sql = "SELECT id,stuID,totalMarks FROM TermInfo WHERE clasID='" + clas + "' and acaID='" + aca + "' and yerID='" + yid + "' and terID='" + terid + "' ORDER BY totalMarks DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                TermInfo terminfo = new TermInfo();
                terminfo.setId(rs.getInt("id"));
                terminfo.setStuID(rs.getInt("stuID"));
                terminfo.setTotalMarks(rs.getDouble("totalMarks"));
                termInfoList.add(terminfo);
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
        return termInfoList;
    }

    public List<Result> returnStudentPosition(int clas, int aca, int yid, int terid) {
        List<Result> resultList = new ArrayList<>();
        try {
            String sql = "SELECT id,stuID,total,subject_id FROM Result WHERE class_id='" + clas + "' and academic_id='" + aca + "' and year_id='" + yid + "' and term_id='" + terid + "' GROUP BY subject_id";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                result.setRid(rs.getInt("id"));
                result.setStuId(rs.getInt("stuID"));
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
    
     public List<Result> returnStudentSubject(int clas, int aca, int yid, int terid, int sub) {
        List<Result> resultList = new ArrayList<>();
        try {
            String sql = "SELECT id,stuID,total FROM Result WHERE class_id='" + clas + "' and academic_id='" + aca + "' and year_id='" + yid + "' and term_id='" + terid + "' and subject_id='"+ sub +"' ORDER BY total DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result();
                result.setRid(rs.getInt("id"));
                result.setStuId(rs.getInt("stuID"));
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

    //==========End of Bill methods ======================
    //=========Methods on conduct starts here ==============
    public void saveUser(User user) {
        try {
            String sql = "INSERT INTO User (fullname,username,password,startdate,enddate,deleted_at,status) VALUES (?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getFullname());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getStartDate());
            pst.setString(5, user.getEndDate());
            pst.setInt(6, user.getDeleted_at());
            pst.setString(7, user.getStatus());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Account created successfully");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public List<User> findAllUser() {
        List<User> userList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM User";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullname(rs.getString("fullname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                userList.add(user);
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
        return userList;
    }

    public void updateUser(User user, int id) {
        try {
            String sql = "UPDATE User SET fullname='" + user.getFullname() + "', username='" + user.getUsername() + "', password='" + user.getPassword() + "',"
                    + "WHERE id='" + id + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "User Account Updated");
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

    public void deleteUser(int id) {

    }

    public boolean exitUser(String fullname, String username) {
        boolean user = false;
        try {
            String year = "select fullname,username from User where fullname='" + fullname + "' or username='" + username + "' and deleted_at = 0";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                user = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return user;
    }

    public boolean exitadmin() {
        boolean user = false;
        String admin = "admin";
        try {
            String year = "select status from User where status = '" + admin + "' and deleted_at = 0";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                user = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return user;
    }

    public boolean returnUserName(String username, String password) {
        boolean exist = false;
        try {
            String user = "select * from User where username='" + username + "' and password='" + password + "' and deleted_at = 0";
            pst = conn.prepareStatement(user);
            rs = pst.executeQuery();

            if (rs.next()) {
                exist = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return exist;
    }

    public String endDate() {
        String id = "";
        String admin = "admin";
        try {
            String year = "select enddate from User where status = '" + admin + "' and deleted_at = 0";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getString("enddate");
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return id;
    }

    public boolean existNot() {
        boolean id = false;
        String none = "none";
        try {
            String year = "select status from User where status = '" + none + "'";
            pst = conn.prepareStatement(year);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        return id;
    }
    //==========End of conduct methods ======================

    //======== Register methods ===========================
    public void saveRegister(Register register) {
        try {
            String sql = "INSERT INTO Register (contact,register) VALUES (?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, register.getContact());
            pst.setInt(2, register.getRegister());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Account is registered Successfully");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public int returnRegister() {
        int reg = 0;
        try {
            String sql = "SELECT register FROM Register where register = 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                reg = 1;
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
        return reg;
    }
    //======== Ends Here ==================================

    //========= All Student ==============================
    public List<Student> findOneStudent(int id, String stuid) {
        List<Student> studentList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Student where completedYear_id = '" + id + "' and stu_ID='" + stuid + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStuID(rs.getString("stu_ID"));
                student.setCompleteID(rs.getInt("completedYear_id"));
                student.setDeleted_at(rs.getInt("deleted_at"));
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
            String sql = "UPDATE Student SET completedYear_id='" + student.getCompleteID() + "',fName='" + student.getfName() + "',mName='" + student.getmName() + "',"
                    + "surname='" + student.getSurname() + "',contact='" + student.getContact() + "', hometown='" + student.gethTown() + "', deleted_at='" + student.getDeleted_at() + "'"
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

    //======== End =======================================
    
    
    
}

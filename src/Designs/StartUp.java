package Designs;

import Connects.ProgramDAO;
import Connects.billMethods;
import Connects.logicHandler;
import Connects.myCon;
import Connects.myLogics;
import Splash.SplashScreen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StartUp {

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    ProgramDAO pro = new ProgramDAO();
    myLogics mylogics = new myLogics();
    logicHandler lhand = new logicHandler();
    static billMethods bmet = new billMethods();

//    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
//        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
//        returnShow();
//    }
    public void returnShow() {
        LoginPage login = new LoginPage();

        DateFormat format = new SimpleDateFormat("MMMM EE dd, yyyy", Locale.ENGLISH);
        int reg = returnRegister();

        try {

            Date currentdate = format.parse(returnCurrentDate());
            Date enddate = format.parse(returnDate());

            if (enddate.after(currentdate) && reg == 1) {
                login.setVisible(true);
            } else if (enddate.after(currentdate) && reg == 0) {
                login.setVisible(true);
            } else if (enddate.before(currentdate) && reg == 0) {
                showLogin();
            } else if (enddate.before(currentdate) && reg == 1) {
                login.setVisible(true);
            } else if (enddate.equals(currentdate) && reg == 1) {
                login.setVisible(true);
            } else if (enddate.equals(currentdate) && reg == 0) {
                showLogin();
            } else if (enddate == null && reg == 0) {
                login.setVisible(true);
            }

        } catch (ParseException ex) {
            login.setVisible(true);
        }
    }

    public String returnDate() {
        String endDate = bmet.endDate();
        return endDate;
    }

    public int returnRegister() {
        int register = bmet.returnRegister();
        return register;
    }

    public String returnCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MMMM EE dd, yyyy");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        return curDate;
    }

    public String addMonth(int i) {
        DateFormat dateFormat = new SimpleDateFormat("MMMM EE dd, yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, i);
        String result = dateFormat.format(cal.getTime());
        return result;
    }

    public void showLogin() {
        LoginPage login = new LoginPage();
        login.setVisible(true);
        login.txt_username.setEnabled(false);
        login.txt_password.setEnabled(false);
        login.btn_login.setEnabled(false);
        login.lbl_account.setVisible(false);
        login.lbl_register.setVisible(true);
        login.lbl_reg.setVisible(true);
    }
}

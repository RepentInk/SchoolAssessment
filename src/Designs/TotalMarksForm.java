package Designs;

import Classes.School;
import Classes.TermInfo;
import Connects.ProgramDAO;
import Connects.billMethods;
import Connects.logicHandler;
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
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TotalMarksForm extends java.awt.Dialog {

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int count = 0;

    logicHandler lhand = new logicHandler();
    billMethods bmet = new billMethods();

    ProgramDAO pro = new ProgramDAO();
    myLogics mylogics = new myLogics();

    public TotalMarksForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        conn = myCon.ConnecrDb();
        setIconImage(mets.myImage("/icons/globe.png"));

        mets.StartTim(lbl_date, lbl_time);

        PerformanceColors();
        showTotalMarks();
        
        lbl_date.setVisible(false);
        lbl_time.setVisible(false);
    }

    public void showTotalMarks() {
        int clas = mylogics.classID(StudentForm.cmd_Class.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(StudentForm.cmb_Academic.getSelectedItem().toString().trim());
        int term = mylogics.termID(StudentForm.cmd_Term.getSelectedItem().toString().trim());
        int year = mylogics.yearID(StudentForm.cmd_Batch.getSelectedItem().toString().trim());

        if (StudentForm.cmd_Class.getSelectedIndex() <= 0 || StudentForm.cmb_Academic.getSelectedIndex() <= 0 || StudentForm.cmd_Term.getSelectedIndex() <= 0 || StudentForm.cmd_Batch.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Select the Class, Academic Year, Term and Year Batch");
        } else {
            lhand.studentMarks(totalMarksTable, term, aca, year, clas);
        }
    }

    public void PerformanceColors() {
        totalMarksTable.getColumnModel().getColumn(0).setCellRenderer(new myCon.CustomRenderer());
        totalMarksTable.getColumnModel().getColumn(1).setCellRenderer(new myCon.CustomRenderer1());
        totalMarksTable.getColumnModel().getColumn(2).setCellRenderer(new myCon.CustomRenderer2());
        totalMarksTable.getColumnModel().getColumn(3).setCellRenderer(new myCon.CustomRenderer3());
        totalMarksTable.getTableHeader().setDefaultRenderer(new myCon.HeaderColor());
    }

    public void setPagePosition() {

        String term = StudentForm.cmd_Term.getSelectedItem().toString().toUpperCase().trim();
        String academic = StudentForm.cmb_Academic.getSelectedItem().toString().toUpperCase().trim();
        String batch = StudentForm.cmd_Batch.getSelectedItem().toString().toUpperCase().trim();
        String currentClass = StudentForm.cmd_Class.getSelectedItem().toString().trim();

        int clas = mylogics.classID(StudentForm.cmd_Class.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(StudentForm.cmb_Academic.getSelectedItem().toString().trim());
        int term1 = mylogics.termID(StudentForm.cmd_Term.getSelectedItem().toString().trim());
        int year1 = mylogics.yearID(StudentForm.cmd_Batch.getSelectedItem().toString().trim());

        String date = lbl_date.getText().trim();
        String time = lbl_time.getText().trim();

        // get location of the code source
        URL url = Designs.Receipt.class.getProtectionDomain().getCodeSource().getLocation();
        String schoolName = "";
        String address = "";
        String schoolLocation = "";

        int count = 1, pos = 0;
        double total, tot = 0;
        String contact = "", position = "";

        List<School> school = pro.findAllSchoolDetails();

        for (School res : school) {
            schoolName = res.getSchoolName();
            address = res.getSchoolAddress();
            schoolLocation = res.getSchoolLocation();
            contact = res.getSchoolContact();
        }

        try {
            // extract directory from code source url
            String root = (new File(url.toURI())).getParentFile().getPath();
            File doc = new File(root, "newhtml.html");
            // create htm file contents for testing
            FileWriter writer = new FileWriter(doc);
            writer.write("<h2 style='text-align:center;margin-bottom:-30px; margin-top:-30px;'>" + schoolName.toUpperCase() + "</h2>"
                    + "<h4 style='text-align:center;margin-bottom:-30px; margin-top:-30px;'>" + address.toUpperCase() + " " + schoolLocation.toUpperCase() + "</h4>"
                    + "<h4 style='text-align:center;margin-bottom:-30px; margin-top:-30px;'>" + contact + "</h4>"
                    + "<h4 style='text-align:center; margin-bottom:-30px; margin-top:-30px;'>END OF " + term.toUpperCase() + " " + academic + " EXAMINATION OVERALL RESULT</h4><hr>"
                    + "<table><tbody>"
                    + "<tr>"
                    + "<td style='text-align:left; width:210px'><b>CLASS : </b>" + mets.capitalizer(currentClass) + "</td>"
                    + "<td ><b>DATE :</b> " + date.toUpperCase() + " " + time + "</td>"
                    + "</tr>"
                    + "</tbody></table>"
                    + "</b>");
            String val = "";

            val += "<hr><table style='font-size:9px;'>"
                    + "<thead style='border-bottom: 1px dotted black'>"
                    + "<tr>"
                    + "<th style='text-align: left;'>ID</th>"
                    + "<th style='text-align: left;'>FULLNAME</th>"
                    + "<th style='text-align: left;'>TOTAL</th>"
                    + "<th style='text-align: left;'>POS</th>"
                    + "</tr><hr>"
                    + "</thead><tbody style='font-size:7px>";

            List<TermInfo> info = bmet.studentTotalMarks(clas, aca, term1, year1);
            for (TermInfo inf : info) {
                total = tot;

                tot = inf.getTotalMarks();

                if (tot > total) {
                    pos = count;
                } else if (tot == total) {
                    pos = pos;
                    //count = pos + 1;
                } else {
                    pos = count;
                }

                if (pos % 10 == 1 && pos != 11) {
                    position = pos + "st";
                } else if (pos % 10 == 2 && pos != 12) {
                    position = pos + "nd";
                } else if (pos % 10 == 3 && pos != 13) {
                    position = pos + "rd";
                } else {
                    position = pos + "th";
                }

                val += "<tr>"
                        + "<td style='text-align: left; width:60px'>" + mylogics.studentIDAssigned(inf.getStuID(), inf.getYerID()) + "</td>"
                        + "<td style='text-align: left; width:300px'>" + mylogics.findStudentByIDName(inf.getStuID(), inf.getYerID()) + "</td>"
                        + "<td style='text-align: left; color:blue; width:30px;'><b>" + inf.getTotalMarks() + "</b></td>"
                        + "<td style='text-align: left; color:red; width:20px;'><b>" + position + "</b></td>"
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        totalMarksTable = new javax.swing.JTable();
        print_allTotal = new javax.swing.JButton();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();

        setResizable(false);
        setTitle("TOTAL MARKS FORM");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        totalMarksTable.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        totalMarksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Student Name", "Total Mark", "Position"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(totalMarksTable);
        if (totalMarksTable.getColumnModel().getColumnCount() > 0) {
            totalMarksTable.getColumnModel().getColumn(0).setMaxWidth(120);
            totalMarksTable.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        print_allTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/preview.png"))); // NOI18N
        print_allTotal.setToolTipText("Click to preview");
        print_allTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                print_allTotalActionPerformed(evt);
            }
        });

        lbl_date.setText("jLabel1");

        lbl_time.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_time, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(print_allTotal)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(print_allTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_time, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void print_allTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_print_allTotalActionPerformed

        if (StudentForm.cmd_Class.getSelectedIndex() <= 0 || StudentForm.cmb_Academic.getSelectedIndex() <= 0 || StudentForm.cmd_Term.getSelectedIndex() <= 0 || StudentForm.cmd_Batch.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Select the Class, Academic Year, Term and Year Batch");
        } else {
            ReceiptForm rcp = new ReceiptForm(null, true);
            setPagePosition();
            rcp.setLocationRelativeTo(this);
            rcp.setVisible(true);
        }
    }//GEN-LAST:event_print_allTotalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TotalMarksForm dialog = new TotalMarksForm(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JButton print_allTotal;
    private javax.swing.JTable totalMarksTable;
    // End of variables declaration//GEN-END:variables
}

package Designs;

import Classes.Student;
import Connects.ProgramDAO;
import Connects.myCon;
import Connects.myLogics;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportExcel extends java.awt.Dialog {

    ProgramDAO pro = new ProgramDAO();
    myLogics mylogics = new myLogics();

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public ImportExcel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(mets.myImage("/Icons/globe.png"));
        allYears();
        profress_loading.setVisible(false);
    }

    public void allYears() {
        mylogics.setYearToCombo(cmb_Admitted, "Select year Batch");
    }

    public String getFilePath() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        chooser.setDialogTitle("Select the Excel File that contains Student Details");
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();

        if (filename.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Select an excel file to import");
        } else {

            txt_import.setText(filename);
            profress_loading.setVisible(true);
            return filename;
        }
        return null;
    }

    public void saveImportDetails() {

        String path = txt_import.getText();

        if (path.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Select Excel Sheet of Details to Save");
        } else {

            try {

                new Thread(new Runnable() {

                    String Surname, MName, FName, Contact, HTown;
                    int val = 0;

                    FileInputStream file = new FileInputStream(new File(path));
                    XSSFWorkbook workbook = new XSSFWorkbook(file);
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    Row row;

                    @Override
                    public void run() {
                        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                            row = (Row) sheet.getRow(i); // Getting and casting the row from the sheet

                            String year = cmb_Admitted.getSelectedItem().toString().trim();
                            int id = Integer.parseInt(stuID.getText().toString());
                            int completeID = mylogics.yearID(cmb_Admitted.getSelectedItem().toString());
                            String idd = "";

                            val = i + i;

                            if (row.getCell(0) == null) {
                                Surname = " ";
                            } else {
                                Surname = row.getCell(0).getStringCellValue();
                            }

                            if (row.getCell(1) == null) {
                                MName = " ";
                            } else {
                                MName = row.getCell(1).toString();
                            }

                            if (row.getCell(2) == null) {
                                FName = " ";
                            } else {
                                FName = row.getCell(2).toString();
                            }

                            int con;
                            if (row.getCell(3) == null) {
                                Contact = "- -";
                            } else {
                                con = (int) row.getCell(3).getNumericCellValue();
                                Contact = String.valueOf(con);
                                //Contact = row.getCell(4).toString();
                            }

                            if (row.getCell(4) == null) {
                                HTown = " ";
                            } else {
                                HTown = row.getCell(4).toString();
                            }
                            id++;

                            if (id <= 9) {
                                idd = "00" + (id);
                            } else if (id >= 10) {
                                idd = "0" + (id);
                            } else if (id >= 100) {
                                idd = "" + id;
                            }

                            Student student = new Student(year + (idd), completeID, FName.trim(), MName.trim(), Surname.trim(), Contact.trim(), HTown.trim());
                            pro.saveStudent(student);

                            if (cmb_Admitted.getSelectedIndex() <= 0) {
                            } else {
                                mylogics.totalStudent(stuID, mylogics.yearID(cmb_Admitted.getSelectedItem().toString()));
                            }
                            profress_loading.setValue(i);
                            profress_loading.setMaximum(sheet.getLastRowNum());

                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }

                            if (i == sheet.getLastRowNum()) {
                                JOptionPane.showMessageDialog(null, "Students records is saved");
                                dispose();
                            }
                        }
                    }
                }).start();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_import = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btn_import = new javax.swing.JButton();
        btn_Save = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmb_Admitted = new javax.swing.JComboBox<>();
        profress_loading = new javax.swing.JProgressBar();
        stuID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(417, 370));
        setTitle("REGISTERING BULK STUDENTS");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("IMPORT STUDENT DETAILS TO SAVE");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setText("Path");

        txt_import.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btn_import.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_import.setText("Import");
        btn_import.setToolTipText("Click to Import Excel");
        btn_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importActionPerformed(evt);
            }
        });

        btn_Save.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btn_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save.png"))); // NOI18N
        btn_Save.setToolTipText("Click to Save");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setText("Program");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel6.setText("Year Batch");

        cmb_Admitted.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_Admitted.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year Batch" }));
        cmb_Admitted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_AdmittedActionPerformed(evt);
            }
        });
        cmb_Admitted.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_AdmittedKeyPressed(evt);
            }
        });

        profress_loading.setBackground(new java.awt.Color(51, 51, 51));
        profress_loading.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        profress_loading.setForeground(new java.awt.Color(255, 102, 102));

        stuID.setEditable(false);
        stuID.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        stuID.setBorder(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Note: The excel sheet should have the below columns in order ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(" Surname, Middle Name, First Name, Contact, HomeTown");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profress_loading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(stuID, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(9, 9, 9)
                                .addComponent(cmb_Admitted, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_import, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_import, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10))))
            .addComponent(jSeparator1)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(stuID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cmb_Admitted, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_import, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_import))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(profress_loading, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
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

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importActionPerformed
        getFilePath();
    }//GEN-LAST:event_btn_importActionPerformed

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if (cmb_Admitted.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select Year");
        } else if (txt_import.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Select Excel Sheet");
        } else {
            saveImportDetails();
        }
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void cmb_AdmittedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_AdmittedActionPerformed
        if (cmb_Admitted.getSelectedIndex() <= 0) {
        } else {
            mylogics.totalStudent(stuID, mylogics.yearID(cmb_Admitted.getSelectedItem().toString()));
        }
    }//GEN-LAST:event_cmb_AdmittedActionPerformed

    private void cmb_AdmittedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_AdmittedKeyPressed

    }//GEN-LAST:event_cmb_AdmittedKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImportExcel dialog = new ImportExcel(new java.awt.Frame(), true);
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
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_import;
    private javax.swing.JComboBox<String> cmb_Admitted;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JProgressBar profress_loading;
    private javax.swing.JTextField stuID;
    private javax.swing.JTextField txt_import;
    // End of variables declaration//GEN-END:variables
}

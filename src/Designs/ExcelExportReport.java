package Designs;

import Classes.Academic;
import Classes.Subject;
import Classes.Term;
import Classes.Year;
import Connects.ProgramDAO;
import Connects.myCon;
import Connects.myLogics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ExcelExportReport extends javax.swing.JFrame {

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    myLogics mylogics = new myLogics();

    ProgramDAO pro = new ProgramDAO();

    public ExcelExportReport() {
        initComponents();
        conn = myCon.ConnecrDb();
        setIconImage(mets.myImage("/Icons/globe.png"));

        allClassess();
    }

    public void allClassess() {
        mylogics.setClassToCombo(cmd_class, "Select Class");
        mylogics.setAcademicToCombo(cmb_Academic, "Select Academic Year");
        mylogics.setTermToCombo(cmb_term, "Select Term");
        mylogics.setYearToCombo(cmd_Batch, "Select Year Batch");
        mylogics.setSubjectToCombo(cmd_subject, "Select Subject");
    }

    public void allResultSpecific() {
        int term = mylogics.termID(cmb_term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int subj = mylogics.subjectID(cmd_subject.getSelectedItem().toString().trim());
        int year = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_class.getSelectedItem().toString().trim());
        mylogics.resultOfStudentReport(ExcelExport.Table_Export, term, aca, subj, year, clas);
    }

    public void assessmentScore() {
        int term = mylogics.termID(cmb_term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int subj = mylogics.subjectID(cmd_subject.getSelectedItem().toString().trim());
        int year = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_class.getSelectedItem().toString().trim());
        mylogics.assessmentOfStudentReport(ExcelExport.Table_Export, term, aca, subj, year, clas);
    }

    public void examsScore() {
        int term = mylogics.termID(cmb_term.getSelectedItem().toString().trim());
        int aca = mylogics.academicID(cmb_Academic.getSelectedItem().toString().trim());
        int subj = mylogics.subjectID(cmd_subject.getSelectedItem().toString().trim());
        int year = mylogics.yearID(cmd_Batch.getSelectedItem().toString().trim());
        int clas = mylogics.classID(cmd_class.getSelectedItem().toString().trim());
        mylogics.examsOfStudentReport(ExcelExport.Table_Export, term, aca, subj, year, clas);
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
        btn_Exams = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmb_Academic = new javax.swing.JComboBox<>();
        btn_Assessment = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmb_term = new javax.swing.JComboBox<>();
        cmd_class = new javax.swing.JComboBox<>();
        btn_Result = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbl_back = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmd_subject = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RECEIPT FORM");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Requirement and Click to Export to Excel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(153, 0, 0))); // NOI18N

        cmd_Batch.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_Batch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_BatchActionPerformed(evt);
            }
        });

        btn_Exams.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/result.png"))); // NOI18N
        btn_Exams.setToolTipText("Click to Export Exams");
        btn_Exams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ExamsActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setText("Term");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setText("Academic Year");

        cmb_Academic.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_Academic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_AcademicActionPerformed(evt);
            }
        });

        btn_Assessment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quiz.png"))); // NOI18N
        btn_Assessment.setToolTipText("Click to Export Assessment");
        btn_Assessment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AssessmentActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setText("Year Batch");

        cmb_term.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmb_term.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_termActionPerformed(evt);
            }
        });

        cmd_class.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_class.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_classActionPerformed(evt);
            }
        });

        btn_Result.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/report.png"))); // NOI18N
        btn_Result.setToolTipText("Click to Export Examination Result");
        btn_Result.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResultActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setText("Class");

        lbl_back.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        lbl_back.setForeground(new java.awt.Color(0, 0, 204));
        lbl_back.setText("Back");
        lbl_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_backMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setText("Subject");

        cmd_subject.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cmd_subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_subjectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(lbl_back, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmd_subject, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_Result, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmb_Academic, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_term, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_Batch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmd_class, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_Assessment, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Exams, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 59, Short.MAX_VALUE)))
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
                    .addComponent(jLabel2)
                    .addComponent(cmb_Academic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmb_term, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmd_Batch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmd_subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Exams, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Result, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Assessment)
                    .addComponent(lbl_back, javax.swing.GroupLayout.Alignment.TRAILING))
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

    private void cmb_termActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_termActionPerformed
        // txt_EOBJ.setText("" + courseID());
    }//GEN-LAST:event_cmb_termActionPerformed

    private void cmd_BatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_BatchActionPerformed
        // txt_EQ1.setText("" + batchID());
    }//GEN-LAST:event_cmd_BatchActionPerformed

    private void btn_ResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResultActionPerformed

        if (cmd_class.getSelectedIndex() == 0 || cmb_Academic.getSelectedIndex() == 0 || cmb_term.getSelectedIndex() == 0 || cmd_Batch.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the following Semester, Academic Year, Course and Batch Before Printing");
        } else {
            ExcelExport rcp = new ExcelExport();
            allResultSpecific();
            rcp.setVisible(true);
        }
    }//GEN-LAST:event_btn_ResultActionPerformed

    private void btn_ExamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ExamsActionPerformed

        if (cmd_class.getSelectedIndex() == 0 || cmb_Academic.getSelectedIndex() == 0 || cmb_term.getSelectedIndex() == 0 || cmd_Batch.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the following Semester, Academic Year, Course and Batch Before Printing");
        } else {
            ExcelExport rcp = new ExcelExport();
            examsScore();
            rcp.setVisible(true);
        }
    }//GEN-LAST:event_btn_ExamsActionPerformed

    private void btn_AssessmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AssessmentActionPerformed

        if (cmd_class.getSelectedIndex() == 0 || cmb_Academic.getSelectedIndex() == 0 || cmb_term.getSelectedIndex() == 0 || cmd_Batch.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the following Semester, Academic Year, Course and Batch Before Printing");
        } else {
            ExcelExport rcp = new ExcelExport();
            assessmentScore();
            rcp.setVisible(true);
        }
    }//GEN-LAST:event_btn_AssessmentActionPerformed

    private void lbl_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_backMouseClicked
        Decide dec = new Decide();
        dec.setVisible(true);
        dispose();
    }//GEN-LAST:event_lbl_backMouseClicked

    private void cmd_subjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_subjectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmd_subjectActionPerformed

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
            java.util.logging.Logger.getLogger(ExcelExportReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExcelExportReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExcelExportReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExcelExportReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExcelExportReport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Assessment;
    private javax.swing.JButton btn_Exams;
    private javax.swing.JButton btn_Result;
    public static javax.swing.JComboBox<String> cmb_Academic;
    public static javax.swing.JComboBox<String> cmb_term;
    public static javax.swing.JComboBox<String> cmd_Batch;
    public static javax.swing.JComboBox<String> cmd_class;
    public static javax.swing.JComboBox<String> cmd_subject;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_back;
    // End of variables declaration//GEN-END:variables
}
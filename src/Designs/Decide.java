package Designs;

import Classes.Student;
import Classes.Year;
import Connects.ProgramDAO;
import Connects.myCon;
import Connects.myLogics;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ink
 */
public class Decide extends javax.swing.JFrame {

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ProgramDAO pro = new ProgramDAO();
    myLogics mylogics = new myLogics();

    public Decide() {
        initComponents();
        conn = myCon.ConnecrDb();
        setIconImage(mets.myImage("/Icons/globe.png"));
        allYearCombo();
    }

    public void allYearCombo() {
        mylogics.setYearToCombo(cmd_yeargroup, "Select Year Batch");
    }

    public void Back() {
        Desktop des = new Desktop();
        des.setVisible(true);
        dispose();
    }

    public void exportExcel() {
        ExcelExportReport eep = new ExcelExportReport();
        eep.setVisible(true);
        dispose();
    }

    public void printReport() {
        Receipt rep = new Receipt();
        rep.setVisible(true);
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cmd_yeargroup = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btn_continue = new javax.swing.JButton();
        lbl_back = new javax.swing.JLabel();
        tspercent = new javax.swing.JRadioButton();
        ffpercent = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        back = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu2 = new javax.swing.JMenu();
        exportExcel = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        printreport = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MAKE A DECISION");
        setFocusTraversalPolicyProvider(true);
        setLocationByPlatform(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Provide the below Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(204, 0, 0))); // NOI18N

        cmd_yeargroup.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setText("Select Year Batch");

        btn_continue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Jog_Forward_48px.png"))); // NOI18N
        btn_continue.setToolTipText("Click to continue");
        btn_continue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_continueActionPerformed(evt);
            }
        });

        lbl_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/back1.png"))); // NOI18N
        lbl_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_backMouseClicked(evt);
            }
        });

        buttonGroup1.add(tspercent);
        tspercent.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        tspercent.setForeground(new java.awt.Color(153, 0, 0));
        tspercent.setText("30 / 70");
        tspercent.setToolTipText("Check if assessment format is 30/70");

        buttonGroup1.add(ffpercent);
        ffpercent.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        ffpercent.setForeground(new java.awt.Color(0, 0, 51));
        ffpercent.setText("50 / 50");
        ffpercent.setToolTipText("Check if assessment format is 50/50");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setText("Select assessment format");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_back, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_continue)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tspercent)
                        .addGap(18, 18, 18)
                        .addComponent(ffpercent, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmd_yeargroup, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmd_yeargroup)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tspercent)
                        .addComponent(ffpercent))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_back, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_continue, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("File");

        back.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/newback.png"))); // NOI18N
        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jMenu1.add(back);
        jMenu1.add(jSeparator1);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/exit.png"))); // NOI18N
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(exit);
        jMenu1.add(jSeparator2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        exportExcel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        exportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/excelSmall.png"))); // NOI18N
        exportExcel.setText("Export to Excel");
        exportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportExcelActionPerformed(evt);
            }
        });
        jMenu2.add(exportExcel);
        jMenu2.add(jSeparator3);

        printreport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printreport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/reportnew.png"))); // NOI18N
        printreport.setText("Print Report");
        printreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printreportActionPerformed(evt);
            }
        });
        jMenu2.add(printreport);
        jMenu2.add(jSeparator4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_continueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_continueActionPerformed
        if (cmd_yeargroup.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select Year");
            cmd_yeargroup.setBackground(Color.red);
        } else if (mylogics.totalStudentCount(mylogics.yearID(cmd_yeargroup.getSelectedItem().toString())) <= 0) {
            JOptionPane.showMessageDialog(null, "No students added yet");
        } else if (tspercent.isSelected() == false && ffpercent.isSelected() == false) {
            JOptionPane.showMessageDialog(null, "Please Select Assessment Format");
        } else {

            AssessmentR assr = new AssessmentR();

            if (tspercent.isSelected()) {
                assr.thirtypercent.setSelected(true);
                assr.jLabel16.setText("30%");
                assr.jLabel18.setText("70%");
            } else if (ffpercent.isSelected()) {
                assr.fiftypercent.setSelected(true);
                assr.jLabel16.setText("50%");
                assr.jLabel18.setText("50%");
            }
            assr.studentName(cmd_yeargroup);
            assr.cmd_Batch.setSelectedIndex(cmd_yeargroup.getSelectedIndex());
            assr.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btn_continueActionPerformed

    private void lbl_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_backMouseClicked
        Back();
    }//GEN-LAST:event_lbl_backMouseClicked

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        Back();
    }//GEN-LAST:event_backActionPerformed

    private void exportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportExcelActionPerformed
        exportExcel();
    }//GEN-LAST:event_exportExcelActionPerformed

    private void printreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printreportActionPerformed
        printReport();
    }//GEN-LAST:event_printreportActionPerformed

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
            java.util.logging.Logger.getLogger(Decide.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Decide.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Decide.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Decide.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Decide().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem back;
    private javax.swing.JButton btn_continue;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cmd_yeargroup;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenuItem exportExcel;
    private javax.swing.JRadioButton ffpercent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JLabel lbl_back;
    private javax.swing.JMenuItem printreport;
    private javax.swing.JRadioButton tspercent;
    // End of variables declaration//GEN-END:variables
}
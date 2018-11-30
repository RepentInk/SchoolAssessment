package Designs;

import Classes.Result;
import Classes.Subject;
import Connects.ProgramDAO;
import Connects.myCon;
import Connects.myLogics;
import static Designs.AssessmentR.Assessment_Table;
import com.sun.glass.events.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class Performance extends javax.swing.JFrame {

    myCon mets = new myCon();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ProgramDAO pro = new ProgramDAO();

    myLogics mylogics = new myLogics();

    public Performance() {
        initComponents();
        conn = myCon.ConnecrDb();
        setIconImage(mets.myImage("/Icons/globe.png"));

        AssessmentColors();
        yearBatch();
        
        cmd_StudentID.setVisible(false);

    }

    public void yearBatch() {
        mylogics.setYearToCombo(cmb_Performance, "Select Year Batch");
    }
    
    public void studentName(JComboBox myComboNam) {
        if (myComboNam.getSelectedIndex() <= 0) {

        } else {
            mylogics.setStudentNameToCombo(cmd_StudentName, "--Select--", mylogics.yearID(myComboNam.getSelectedItem().toString()), cmd_StudentID);
        }
    }

    //Showing assessment specified
    public void studentPerformanceDetails(JTable assTable) {
        DefaultTableModel tmodel = (DefaultTableModel) assTable.getModel();
        tmodel.setRowCount(0);

        Object[] object;

        List<Result> result = mylogics.studentPerformance(mylogics.findStudentReport(cmd_StudentName.getSelectedItem().toString(), mylogics.yearID(cmb_Performance.getSelectedItem().toString())));

        for (Result sch : result) {

            String subject = mylogics.returnSubjectName(sch.getSubject_id());
            Double classScore = sch.getAssessment();
            Double exams = sch.getExams();
            Double total = sch.getTotalResult();
            String grade = sch.getGrade();
            String term = mylogics.returnTermName(sch.getTerm_id());
            String stuClass = mylogics.returnClassName(sch.getClass_id());
            String year = mylogics.yearName(sch.getYear_id());

            String academic = mylogics.academicName(sch.getAcademic_id());
            String remarks = mylogics.returnRemarksOnID(sch.getRemarks());

            object = new Object[]{subject, classScore, exams, total, grade, remarks, term, stuClass, academic};
            tmodel.addRow(object);
        }

    }

//    //Returning a single student searching
//    public void returnSingle() {
//        String sid = txt_search.getText().trim();
//
//        if (txt_search.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Please enter ID");
//        } else {
//
//            List<Result> result = pro.resultPerformance(sid);
//            DefaultTableModel tmodel = (DefaultTableModel) resultTable.getModel();
//            tmodel.setRowCount(0);
//            Object[] object;
//            
//            try {
//                for (Result res : result) {
//                    String course = courseName(res.getSubject().toUpperCase());
//                    double quiz = res.getSclass();
//                    double exam = res.getExams();
//                    double total = res.getTotal();
//                    String grade = res.getGrade();
//                    String term = res.getTerm();
//                    String year = res.getAcademic();
//
//                    object = new Object[]{course, quiz, exam, total, grade, term, year};
//                    tmodel.addRow(object);
//                }
//
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);
//            }
//        }
//    }
    public void AssessmentColors() {
        resultTable.getColumnModel().getColumn(0).setCellRenderer(new myCon.CustomRenderer());
        resultTable.getColumnModel().getColumn(1).setCellRenderer(new myCon.CustomRenderer1());
        resultTable.getColumnModel().getColumn(2).setCellRenderer(new myCon.CustomRenderer2());
        resultTable.getColumnModel().getColumn(3).setCellRenderer(new myCon.CustomRenderer3());
        resultTable.getColumnModel().getColumn(4).setCellRenderer(new myCon.CustomRenderer4());
        resultTable.getColumnModel().getColumn(5).setCellRenderer(new myCon.CustomRenderer5());
        resultTable.getColumnModel().getColumn(6).setCellRenderer(new myCon.CustomRenderer6());
        resultTable.getColumnModel().getColumn(7).setCellRenderer(new myCon.CustomRenderer7());
        resultTable.getColumnModel().getColumn(8).setCellRenderer(new myCon.CustomRenderer8());
        resultTable.getTableHeader().setDefaultRenderer(new myCon.HeaderColor());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btn_Search = new javax.swing.JButton();
        cmb_Performance = new javax.swing.JComboBox<>();
        cmd_StudentName = new javax.swing.JComboBox<>();
        cmd_StudentID = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MONITORING A PARTICULAR STUDENT PERFORMANCE FORM");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Student Name to Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        btn_Search.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Search_20px.png"))); // NOI18N
        btn_Search.setText("   Search");
        btn_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchActionPerformed(evt);
            }
        });

        cmb_Performance.setFont(new java.awt.Font("Agency FB", 0, 24)); // NOI18N
        cmb_Performance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year" }));
        cmb_Performance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_PerformanceActionPerformed(evt);
            }
        });

        cmd_StudentName.setFont(new java.awt.Font("Agency FB", 0, 20)); // NOI18N
        cmd_StudentName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Name" }));
        cmd_StudentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_StudentNameActionPerformed(evt);
            }
        });

        cmd_StudentID.setFont(new java.awt.Font("Agency FB", 0, 20)); // NOI18N
        cmd_StudentID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select ID" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cmb_Performance, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmd_StudentID, 0, 231, Short.MAX_VALUE)
                    .addComponent(cmd_StudentName, 0, 231, Short.MAX_VALUE)
                    .addComponent(btn_Search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmb_Performance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(cmd_StudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmd_StudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btn_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        resultTable.setFont(new java.awt.Font("Agency FB", 0, 11)); // NOI18N
        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Subject", "Class Score", "Exams", "Total", "Grades", "Remarks", "Term", "Class", "Academic"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(resultTable);
        if (resultTable.getColumnModel().getColumnCount() > 0) {
            resultTable.getColumnModel().getColumn(0).setPreferredWidth(220);
            resultTable.getColumnModel().getColumn(1).setPreferredWidth(110);
            resultTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            resultTable.getColumnModel().getColumn(3).setPreferredWidth(60);
            resultTable.getColumnModel().getColumn(4).setPreferredWidth(70);
            resultTable.getColumnModel().getColumn(5).setPreferredWidth(130);
            resultTable.getColumnModel().getColumn(6).setPreferredWidth(130);
            resultTable.getColumnModel().getColumn(7).setPreferredWidth(100);
            resultTable.getColumnModel().getColumn(8).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchActionPerformed
      if(cmd_StudentName.getSelectedIndex() == 0 || cmb_Performance.getSelectedIndex() == 0){
        JOptionPane.showMessageDialog(null, "Select Required Fields");
      }else{
        studentPerformanceDetails(resultTable);
      }
    }//GEN-LAST:event_btn_SearchActionPerformed

    private void cmb_PerformanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_PerformanceActionPerformed
         studentName(cmb_Performance);
    }//GEN-LAST:event_cmb_PerformanceActionPerformed

    private void cmd_StudentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_StudentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmd_StudentNameActionPerformed

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
            java.util.logging.Logger.getLogger(Performance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Performance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Performance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Performance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Performance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Search;
    private javax.swing.JComboBox<String> cmb_Performance;
    private javax.swing.JComboBox<String> cmd_StudentID;
    private javax.swing.JComboBox<String> cmd_StudentName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable resultTable;
    // End of variables declaration//GEN-END:variables
}
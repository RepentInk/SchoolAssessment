package Connects;

import static Designs.ExcelExport.Table_Export;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class myCon {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public static Connection ConnecrDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Assessment.sqlite");
            //JOptionPane.showMessageDialog(null, "Connection Establish");     
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

// Method that set the icon
    public Image myImage(String img) {
        Image i = null;
        try {
            i = ImageIO.read(getClass().getResource(img));
        } catch (Exception e) {
        }
        return i;
    }

//Method that count to thousand      
    public void StartCount() {
        int num = 0;
        for (int i = 1; i <= 1000; i++) {
            num = i;
            System.out.println(num);
        }
    }

//method that convert password field value to words
    public void chkShow(JPasswordField Apass, JCheckBox Checkpass) {
        // Code that enable user to view password
        if (Checkpass.isSelected()) {
            Apass.setEchoChar((char) 0);
        } else {
            Apass.setEchoChar('*');
        }
    }

//method that print time and date
    public void StartTim(JLabel lbl_D, JLabel lbl_T) {
        Timer t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
                SimpleDateFormat sdf2 = new SimpleDateFormat("MMM d EE, yyy");
                lbl_D.setText(sdf2.format(new java.util.Date()).trim());
                lbl_T.setText(sdf.format(new java.util.Date()).trim());
            }
        });

        t.start();
    }

    // method that enable one to choose whether admin or user
    public void OpenClose(JRadioButton radio, JFrame frame) {
        if (radio.isSelected()) {
            frame.setVisible(true);
        }
    }

    //method that set frame to true
    public void backMe(JFrame frameBack) {
        frameBack.setVisible(true);
    }

    //Color of table methods
    public static class CustomRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 0) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.BLACK);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer1 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 1) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.BLACK);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer2 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 2) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.BLUE);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer3 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 3) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.BLUE);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer4 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 4) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.RED);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer5 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 5) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.BLUE);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer6 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 6) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.RED);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer7 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 7) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.BLUE);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer8 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 8) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.darkGray);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class CustomRenderer9 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 9) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.PINK);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }
    
    public static class CustomRenderer10 extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 10) {
                // cellComponent.setBackground(Color.magenta);
                cellComponent.setForeground(Color.darkGray);
                cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            }
            return cellComponent;
        }
    }

    public static class HeaderColor extends DefaultTableCellRenderer {

        public HeaderColor() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            cellComponent.setBackground(Color.YELLOW);
            cellComponent.setForeground(Color.BLACK);
            cellComponent.setFont(new java.awt.Font("Tahoma", 1, 15));
            return cellComponent;
        }
    }

    public static class HeaderColorOne extends DefaultTableCellRenderer {

        public HeaderColorOne() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            cellComponent.setForeground(Color.BLACK);
            cellComponent.setFont(new java.awt.Font("Tahoma", 1, 12));
            return cellComponent;
        }
    }

    public String getCellVal(int x, int y, JTable table) {
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        return dm.getValueAt(x, y).toString();
    }

    public void writeToExcel(JTable table) {

        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet ws = wb.createSheet();

        TreeMap<String, Object[]> data = new TreeMap<>();

        int colum = dm.getColumnCount();

        if (colum == 2) {
            data.put("-1", new Object[]{dm.getColumnName(0).toString(), dm.getColumnName(1).toString()});
            for (int i = 0; i < dm.getRowCount(); i++) {
                data.put(Integer.toString(i), new Object[]{getCellVal(i, 0, table).toString(), getCellVal(i, 1, table).toString()});
            }
        } else if (colum == 5) {
            data.put("-1", new Object[]{dm.getColumnName(0).toString(), dm.getColumnName(1).toString(), dm.getColumnName(2).toString(), dm.getColumnName(3).toString(), dm.getColumnName(4).toString()});
            for (int i = 0; i < dm.getRowCount(); i++) {
                data.put(Integer.toString(i), new Object[]{getCellVal(i, 0, table).toString(), Double.parseDouble(getCellVal(i, 1, table).toString()), getCellVal(i, 2, table).toString(), getCellVal(i, 3, table).toString(), getCellVal(i, 4, table).toString()});
            }
        }else if (colum == 6) {
            data.put("-1", new Object[]{dm.getColumnName(0).toString(), dm.getColumnName(1).toString(), dm.getColumnName(2).toString(), dm.getColumnName(3).toString(), dm.getColumnName(4).toString(), dm.getColumnName(5).toString()});
            for (int i = 0; i < dm.getRowCount(); i++) {
                data.put(Integer.toString(i), new Object[]{getCellVal(i, 0, table).toString(), Double.parseDouble(getCellVal(i, 1, table).toString()), getCellVal(i, 2, table).toString(), getCellVal(i, 3, table).toString(), getCellVal(i, 4, table).toString(), getCellVal(i, 5, table).toString()});
            }
        } else if (colum == 7) {
            data.put("-1", new Object[]{dm.getColumnName(0).toString(), dm.getColumnName(1).toString(), dm.getColumnName(2).toString(), dm.getColumnName(3).toString(), dm.getColumnName(4).toString(), dm.getColumnName(5).toString(), dm.getColumnName(6).toString()});
            for (int i = 0; i < dm.getRowCount(); i++) {
                data.put(Integer.toString(i), new Object[]{getCellVal(i, 0, table), getCellVal(i, 1, table).toString(), Double.parseDouble(getCellVal(i, 2, table)), Double.parseDouble(getCellVal(i, 3, table)), Double.parseDouble(getCellVal(i, 4, table)), getCellVal(i, 5, table), getCellVal(i, 6, table).toString()});
            }
        } else if (colum == 8) {
            data.put("-1", new Object[]{dm.getColumnName(0).toString(), dm.getColumnName(1).toString(), dm.getColumnName(2).toString(), dm.getColumnName(3).toString(), dm.getColumnName(4).toString(), dm.getColumnName(5).toString(), dm.getColumnName(6).toString(), dm.getColumnName(7).toString()});
            for (int i = 0; i < dm.getRowCount(); i++) {
                data.put(Integer.toString(i), new Object[]{getCellVal(i, 0, table), getCellVal(i, 1, table).toString(), getCellVal(i, 2, table).toString(), getCellVal(i, 3, table).toString(), getCellVal(i, 4, table).toString(), getCellVal(i, 5, table).toString(), getCellVal(i, 6, table).toString(), getCellVal(i, 7, table).toString()});
            }
        }

        //Writing the sheet
        Set<String> ids = data.keySet();
        XSSFRow row;
        int rowID = 0;

        for (String key : ids) {
            row = ws.createRow(rowID++);

            Object[] values = data.get(key);
            int cellID = 0;

            for (Object o : values) {
                // Cell cell = row.createCell(cellID++);
                XSSFCell cell = row.createCell(cellID++);
                cell.setCellValue(o.toString());
            }

        }

        //Writing to excel
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();

        try {
            FileOutputStream fos = new FileOutputStream(new File(filename + ".xlsx"));
            JOptionPane.showMessageDialog(null, "Exported");
            wb.write(fos);
            fos.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

}

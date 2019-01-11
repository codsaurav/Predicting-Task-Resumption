/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author satyam
 */
public class ToDoClass {
    JFrame myFrame;
    JPanel mainPanel;
    JTable table;
    ToDoTableModel toDoTableModel;
    ArrayList<ToDoRow> toDoList;
    SQLDatabaseHelper sqlDatabaseHelper;
    TableColumnModel tableColumnModel;
    TableColumn column;
    private float[] columnWidthPercentage = {20.0f,60.0f,10.0f,10.0f};
    
    
    public void go() {
        myFrame = new JFrame("To-Do List");
        mainPanel = new JPanel (new MigLayout (""));
        toDoList = new ArrayList<>();
        
        toDoTableModel = new ToDoTableModel (toDoList);
        table = new JTable(toDoTableModel);
        table.getColumn ("Launch").setCellRenderer(new JTableButtonRenderer());
        table.getColumn ("Delete").setCellRenderer(new JTableButtonRenderer());
        table.addMouseListener (new JTableButtonMouseListener(table));
        
        int tw = table.getWidth();
        tableColumnModel = table.getColumnModel();
        for (int i=0; i<4; i++) {
            column = tableColumnModel.getColumn(i);
            int pWidth = Math.round (columnWidthPercentage[i]*tw);
            column.setPreferredWidth(pWidth);
        }
        
        new Thread () {
            public void run () {
                toDoList.clear();
                sqlDatabaseHelper = SQLDatabaseHelper.getInstance();
                sqlDatabaseHelper.initialize();
                ArrayList<ToDoRow> arrayList = sqlDatabaseHelper.getDataForToDo();
                sqlDatabaseHelper.closeConnection();
                for (ToDoRow row : arrayList) {
                    toDoList.add (new ToDoRow (row.getApplication(),row.getTitle(),row.getLaunch(),row.getDelete(),row.getExecutable(),row.getFile()));
                }
                //toDoTableModel.fireTableDataChanged();
                table.revalidate();
                table.repaint();
            }
        }.start();
        
       JScrollPane scroller = new JScrollPane (table);
       scroller.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       
       mainPanel.add (scroller,"push, grow, wrap");
        
       myFrame.getContentPane().add (mainPanel);
       myFrame.setSize (700,500);
       myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       myFrame.setLocationRelativeTo(null);
       myFrame.setVisible (true);
    }
    
    public class JTableButtonRenderer implements TableCellRenderer {		
    @Override 
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = (JButton)value;
        if (isSelected) {
         button.setForeground(table.getSelectionForeground());
         button.setBackground(table.getSelectionBackground());
        } else {
          button.setForeground(table.getForeground());
          button.setBackground(UIManager.getColor("Button.background"));
        }
        return button;	
        }
    }
        
    public class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;
		
        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        @Override 
        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
            int row    = e.getY()/table.getRowHeight(); 

            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton)value).doClick();
                }
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.calendar.SingleDaySelectionModel;

/**
 *
 * @author satyam
 */
public class GuiComponent {
    public static GuiComponent guiComponent;
    JFrame mainFrame;
    JPanel mainPanel2;
    JPanel mainPanel;
    JTable table;
    CustomTableModel customTableModel;
    SQLDatabaseHelper sqlDatabaseHelper;
    TableColumnModel tableColumnModel;
    TableColumn column;
    GuiForDate guiForDate;
    public static String day = null;
    public static String date1 = null;
    public static String date2 = null;
    private float[] columnWidthPercentage = {20.0f,55.0f,10.0f,10.0f,5.0f};
    JButton chooseDate;
    JButton submit;
    JButton launchList;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("EEEE");
    ArrayList<TableData> temporaryList;
       
    private GuiComponent () {
        super();
    }
    
    public static GuiComponent getInstance() {
        if (guiComponent == null) {
            guiComponent = new GuiComponent();
        }
        return guiComponent;
    }
    
    public void go () {
        mainFrame = new JFrame ("Preview Action Pan");
        mainPanel = new JPanel (new MigLayout (""));
        mainPanel2 = new JPanel (new MigLayout("","[][]push[]",""));
        
        temporaryList = new ArrayList<>();
        
        customTableModel = new CustomTableModel(temporaryList);
        table = new JTable (customTableModel);
        
        chooseDate = new JButton ("Choose Date");
        chooseDate.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                new GuiForDate().go();
            }
        });
        submit = new JButton ("Submit");
        submit.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                sqlDatabaseHelper = SQLDatabaseHelper.getInstance();
                sqlDatabaseHelper.initialize();
                int size = temporaryList.size();
                String app = null;
                for (int i=0; i<size; i++) {
                    if (!temporaryList.get(i).getApplication().equals(""))
                        app = temporaryList.get(i).getApplication();
                    if (temporaryList.get(i).getResume()) {
                        sqlDatabaseHelper.updateSQL(app, temporaryList.get(i).getTitle(), temporaryList.get(i).getStart(), temporaryList.get(i).getEnd());
                    }
                }
                sqlDatabaseHelper.closeConnection();
                new Toast ("Done!",3000).setVisible(true);
            }
        });
        launchList = new JButton ("Launch List");
        launchList.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                new ToDoClass().go();
            }
        });
        
        
        JScrollPane scroller = new JScrollPane (table);
        scroller.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
          
        mainPanel.add (scroller,"push, grow, wrap");
        mainPanel2.add (chooseDate);
        mainPanel2.add (submit);
        mainPanel2.add (launchList);
        
        mainFrame.getContentPane().add (mainPanel,BorderLayout.CENTER);
        mainFrame.getContentPane().add (mainPanel2,BorderLayout.SOUTH);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLocationRelativeTo (null);
        //mainFrame.pack();
        //mainFrame.setResizable(false);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setMinimumSize (new Dimension (700,400));
        mainFrame.setVisible(true);
        
        guiForDate = new GuiForDate();
        guiForDate.go();
    }
    
    public void refreshData () {
        if (date1 != null && date2 != null && day != null) {
            sqlDatabaseHelper = SQLDatabaseHelper.getInstance();
            sqlDatabaseHelper.initialize();
            temporaryList.clear();
            ArrayList<PastRetrieval> arrayList = sqlDatabaseHelper.getDataForGui(date1, date2);
            if (day.equals ("All")) {
                for (PastRetrieval p : arrayList) {
                    temporaryList.add (new TableData (p.getProgram(),p.getTitle(),p.getStart(),p.getEnd(),false,p.getExecutable(),p.getFile()));
                }
            } else {
                for (PastRetrieval p : arrayList) {
                    try {
                        Date d = SQLDatabaseHelper.simpleDateFormat.parse (p.getStart());
                        if (day.equals(GuiComponent.simpleDateFormat.format (d))) {
                            temporaryList.add (new TableData (p.getProgram(),p.getTitle(),p.getStart(),p.getEnd(),false,p.getExecutable(),p.getFile()));
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(GuiComponent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            sqlDatabaseHelper.closeConnection();
            
            Collections.sort (temporaryList);
            
            int size = temporaryList.size();
            String repeat = null;
            
            if (size != 0) 
                repeat = temporaryList.get(0).getApplication();
            
            for (int i=1; i<size; i++) {
                if (temporaryList.get(i).getApplication().equals(repeat)) {
                    temporaryList.get(i).setApplication("");
                } else {
                    repeat = temporaryList.get(i).getApplication();
                }
            }
            
            int tw = table.getWidth();
            tableColumnModel = table.getColumnModel();
            for (int i=0; i<5; i++) {
                column = tableColumnModel.getColumn(i);
                int pWidth = Math.round (columnWidthPercentage[i]*tw);
                column.setPreferredWidth(pWidth);
            }
            table.revalidate();
            table.repaint();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author satyam
 */
public class GuiForPlaces {
    JFrame mainFrame;
    JPanel mainPanel;
    JButton clickButton;
    JLabel label;
    DateTimePicker startDate;
    DateTimePicker endDate;
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd/MM/yyyy hh:mm");
    JTable table;
    CustomTableModelPlaces customTableModel;
    TableColumn column;
    ArrayList<Places> temporaryData;
    TableColumnModel tableColumnModel;
    
    
    ArrayList<Places> arrayList;
    
    public GuiForPlaces () {
        arrayList = new ArrayList<>();
    }
    
    public void initiate () {
        String date = null;
        String name = null;
        
        try {
            Process process = Runtime.getRuntime().exec("cmd /c dir /O:D %USERPROFILE%\\AppData\\Roaming\\Microsoft\\Windows\\Recent\\*.lnk");
            BufferedReader reader = new BufferedReader (new InputStreamReader (process.getInputStream()));
            String line = null;
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            
            while ((line=reader.readLine()) != null) {
                int size = line.length();
                if (size == 0)
                    break;
                if (line.charAt(0) == ' ')
                    break;
                date = line.substring (0,17);
                int j = size-1;
                while (j>=0 && line.charAt(j) == ' ')
                    j--;
                j = j-4;
                name = line.substring (36,j+1);
                arrayList.add (new Places (name,date,GuiForPlaces.simpleDateFormat.parse(date)));
            }
            
            reader.close();
            
            new Thread() {
                public void run () {
                    try {
                        for (Places p : arrayList) {
                            Process pr = Runtime.getRuntime().exec ("cmd /c type %USERPROFILE%\\AppData\\Roaming\\Microsoft\\Windows\\Recent\\" + p.getName() + ".lnk | find \"\\\"");
                            BufferedReader reader2 = new BufferedReader (new InputStreamReader (pr.getInputStream()));
                            String l = reader2.readLine();
                            p.setLocation(l);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
            
            
            go();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void go() {
        mainFrame = new JFrame("Preview Places Pan");
        mainPanel = new JPanel (new MigLayout(""));
        clickButton = new JButton ("Submit");
        
        temporaryData = new ArrayList<>();
        customTableModel = new CustomTableModelPlaces (temporaryData);
        table = new JTable (customTableModel);
        
        float[] columnWidthPercentage = {60.0f,40.0f};
        
        JScrollPane scroller = new JScrollPane (table);
        scroller.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        clickButton.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                temporaryData.clear();
                temporaryData.trimToSize();
                Date startTime = null;
                Date endTime = null;

                try {
                    startTime = DateTimePicker.simpleDateFormat.parse (startDate.getDateAndTime());
                    endTime = DateTimePicker.simpleDateFormat.parse(endDate.getDateAndTime());
                    
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                for (Places p : arrayList) {
                    //System.out.println (p.getDateFormat().toString());
                    if ((p.getDateFormat().compareTo(startTime) >= 0) && (p.getDateFormat().compareTo(endTime) <= 0 )) {
                    //System.out.println (p.getDateFormat().toString() + "-" + startTime.toString());  
                    temporaryData.add (p);
                    System.out.println (p.getLocation());
                    }
                }
                int tw = table.getWidth();
                tableColumnModel = table.getColumnModel();
                for (int i=0; i<2; i++) {
                    column = tableColumnModel.getColumn(i);
                    int pWidth = Math.round (columnWidthPercentage[i]*tw);
                    column.setPreferredWidth(pWidth);
                }
                
             
                  
                table.revalidate();
                table.repaint();
            }
        });
       
        
        Date date = new Date();
        startDate = new DateTimePicker();
        startDate.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        startDate.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        
        endDate = new DateTimePicker();
        endDate.setFormats (DateFormat.getDateTimeInstance (DateFormat.SHORT, DateFormat.MEDIUM));
        endDate.setTimeFormat(DateFormat.getTimeInstance( DateFormat.MEDIUM));

        startDate.setDate(date);
        endDate.setDate (date);
        
        mainPanel.add(startDate,"split 3");
        mainPanel.add (endDate);
        mainPanel.add (clickButton,"wrap");
        mainPanel.add (scroller,"push, grow");
       
        mainFrame.getContentPane().add (mainPanel);
        
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLocationRelativeTo (null);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setMinimumSize (new Dimension (700,400));
        mainFrame.setVisible(true);
    }
    
}

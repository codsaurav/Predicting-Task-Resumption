/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author satyam
 */
public class GuiForDate extends JFrame {
    
    private JPanel mainPanel;
    private DateTimePicker startDate;
    private DateTimePicker endDate;
    private JLabel heading;
    private JButton submit;
    private JComboBox combo;
    private String[] dayOfWeek = {"All","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private String day;
    
    public void go () {
        mainPanel = new JPanel (new MigLayout (""));
        day = "All";
        heading = new JLabel ();
        heading.setFont (new Font ("Serif",Font.BOLD,18));
        heading.setText ("Choose:");
        mainPanel.add (heading,"wrap");
        
        Date date = new Date();
        startDate = new DateTimePicker();
        startDate.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        startDate.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        
        endDate = new DateTimePicker();
        endDate.setFormats (DateFormat.getDateTimeInstance (DateFormat.SHORT, DateFormat.MEDIUM));
        endDate.setTimeFormat(DateFormat.getTimeInstance( DateFormat.MEDIUM));

        startDate.setDate(date);
        endDate.setDate (date);
        
        submit = new JButton ("Submit");
        submit.addActionListener (new ActionListenerClass());
        
        combo = new JComboBox (dayOfWeek);
        combo.addItemListener (new DayListener());
        
        mainPanel.add (startDate,"split 3");
        mainPanel.add (endDate);
        mainPanel.add (combo);
        mainPanel.add (submit,"wrap");
        
        getContentPane().add (mainPanel);
        pack();
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo (null);
        setResizable(false);
        setVisible (true);
    }
    
    public void killProcess () {
        this.dispose();
    }
    
    public class DayListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == combo) {
                day = (String) combo.getSelectedItem();
            }
        }
    }
    
    public class ActionListenerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Date start = null;
            Date end = null;
            
            try {
                start = DateTimePicker.simpleDateFormat.parse(startDate.getDateAndTime());
                end = DateTimePicker.simpleDateFormat.parse (endDate.getDateAndTime());
            } catch (ParseException ex) {
                    ex.printStackTrace();
            }
            if (start.compareTo(end) > 0) {
                new Toast ("Invalid Interval!",3000).setVisible(true);
            } else {
            GuiComponent.day = day;
            GuiComponent.date1 = startDate.getDateAndTime();
            GuiComponent.date2 = endDate.getDateAndTime();
            GuiComponent.getInstance().refreshData();
            killProcess();
            }
        }
        
    }
}

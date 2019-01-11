/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author satyam
 */
public class SuggestionBox {
    
    JFrame myFrame;
    JButton runningButton;
    JButton placesButton;
    Font boldFont;
    JPanel mainPanel;
    
    private static final String[] running = {"Action.","Action..","Action..."};
    private static final String[] places = {"Places.","Places..","Places..."};
    
    public void go () {
        myFrame = new JFrame("Task Resumption");
        mainPanel = new JPanel (new MigLayout(""));
        runningButton = new JButton(SuggestionBox.running[0]);
        placesButton = new JButton (SuggestionBox.places[0]);
        boldFont = new Font ("Serif",Font.BOLD,20);
        runningButton.setFont (boldFont);
        placesButton.setFont (boldFont);
        runningButton.setFocusPainted(false);
        placesButton.setFocusPainted (false);
       
        
        runningButton.addMouseListener (new MouseAdapter () {
            public void mouseEntered (MouseEvent evt) {
                runningButton.setBackground(Color.darkGray);
                runningButton.setForeground (Color.WHITE);
            }
            
            public void mouseExited (MouseEvent evt) {
                runningButton.setBackground(Color.WHITE);
                runningButton.setForeground(Color.BLACK);
            }
        });
        
        placesButton.addMouseListener (new MouseAdapter () {
            public void mouseEntered (MouseEvent evt) {
                placesButton.setBackground(Color.darkGray);
                placesButton.setForeground (Color.WHITE);
            }
            
            public void mouseExited (MouseEvent evt) {
                placesButton.setBackground(Color.WHITE);
                placesButton.setForeground(Color.BLACK);
            }
        });
        
        
        runningButton.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                GuiComponent.getInstance().go();
            }
        });
        
        placesButton.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                new GuiForPlaces().initiate();
            }
        });
        
        mainPanel.add (runningButton,"split2, pushx, growx");
        mainPanel.add (placesButton, "pushx, growx");
        
        myFrame.getContentPane().add (mainPanel);
        myFrame.setSize(280,100);
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        
        myFrame.setLocation (width-300,height-150);
        
        myFrame.setVisible (true);
        
        Thread t = new Thread () {
            public void run () {
                try {
                    int count = 0;
                    while (true) {
                        Thread.sleep (1000);
                        runningButton.setText (running[count]);
                        count = (count+1)%3;
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        
        Thread t2 = new Thread () {
            public void run () {
                try {
                    int count = 0;
                    while (true) {
                        Thread.sleep (1000);
                        placesButton.setText (places[count]);
                        count = (count+1)%3;
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        
        t.setDaemon(true);
        t.start();
        
        t2.setDaemon (true);
        t2.start();
    }
    
}

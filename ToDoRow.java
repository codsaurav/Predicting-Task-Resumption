/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import javax.swing.JButton;

/**
 *
 * @author satyam
 */
public class ToDoRow {
    private String application;
    private String title;
    private JButton launch;
    private JButton delete;
    private String executable;
    private String file;
    
    public ToDoRow (String a, String t, JButton l, JButton d, String e, String f) {
        this.application = a;
        this.title = t;
        this.launch = l;
        this.launch.setText ("Launch");
        this.delete = d;
        this.delete.setText ("Delete");
        this.executable = e;
        this.file = f;
    }
    
    public String getApplication () {
        return this.application;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public JButton getLaunch () {
        return this.launch;
    }
    
    public JButton getDelete () {
        return this.delete;
    }
    
    public String getExecutable () {
        return this.executable;
    }
    
    public String getFile () {
        return this.file;
    }
}

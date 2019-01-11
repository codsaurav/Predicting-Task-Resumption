/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author satyam
 */
public class Action implements Serializable {
    
    private String program = null;
    private String title = null;
    private Date startDate = null;
    private String file = null;
    private String executable = null;
    private int id = 0;
    
    
    public Action (int id, String p, String t) {
        this.id = id;
        this.program = p;
        this.title = t;
    }
    
    
    public Action (int id, String p, String t, Date s, String f, String e) {
        this.id = id;
        this.program = p;
        this.title = t;
        this.startDate = s;
        this.file = f;
        this.executable = e;
    }
    
    public Action (int id, String p, String t, Date s) {
        this.id = id;
        this.program = p;
        this.title = t;
        this.startDate = s;
    }
    
    
    
    public void setProgram (String program) {
        this.program = program;
    }
    
    public String getProgram () {
        return this.program;
    }
    
    public void setTitle (String title) {
        this.title = title;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public void setStartDate (Date date) {
        this.startDate = date;
    }
    
    public Date getStartDate () {
        return this.startDate;
    }
    
    public void setFile (String f) {
        this.file = f;
    }
    
    public String getFile () {
        return this.file;
    }
    
    public void setExecutable (String e) {
        this.executable = e;
    }
    
    public String getExecutable () {
        return this.executable;
    }
    
    public int getId () {
        return this.id;
    }
    
    public void setId (int id) {
        this.id = id;
    }
}

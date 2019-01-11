/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

/**
 *
 * @author satyam
 */
public class TableData implements Comparable {
    
    private String application;
    private String title;
    private String start;
    private String end;
    private Boolean resume;
    private String executable;
    private String file;
    
    public TableData (String a, String t, String s, String e, Boolean r, String ex, String f) {
        this.application = a;
        this.title = t;
        this.start = s;
        this.end = e;
        this.resume = r;
        this.executable = e;
        this.file = f;
    }
    
    public String getApplication () {
        return this.application;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public boolean getResume () {
        return this.resume;
    }
    
    public String getStart () {
        return this.start;
    }
    
    public String getEnd () {
        return this.end;
    }
    
    public void setResume (boolean b) {
        this.resume = b;
    }
    
    public void setApplication (String a) {
        this.application = a;

    }

    @Override
    public int compareTo(Object o) {
        TableData t = (TableData)o;
        return this.getApplication().compareTo(t.getApplication());
    }
    
    public String getExecutable () {
        return this.executable;
    }
    
    public String getFile () {
        return this.file;
    }
}
